package br.com.badrequest.insporte.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import android.widget.CompoundButton.OnCheckedChangeListener;
import br.com.badrequest.insporte.R;
import br.com.badrequest.insporte.activity.base.TranslucentActivity;
import br.com.badrequest.insporte.bean.Option;
import br.com.badrequest.insporte.bean.Question;
import br.com.badrequest.insporte.bean.SurveyType;
import br.com.badrequest.insporte.bean.database.SurveyJson;
import br.com.badrequest.insporte.database.datasource.SurveyDataSource;
import br.com.badrequest.insporte.integration.bean.AdministeredQuestionnaire;
import br.com.badrequest.insporte.integration.bean.QuestionOption;
import br.com.badrequest.insporte.integration.bean.Survey;
import br.com.badrequest.insporte.util.BitmapUtils;
import com.google.gson.GsonBuilder;
import org.androidannotations.annotations.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

@EActivity(R.layout.survey_activity)
public class SurveyActivity extends TranslucentActivity {

    public static final String SURVEY_TYPE_EXTRA = "SURVEY_TYPE";
    public static final String SURVEY_EXTRA = "SURVEY";
    private static final int CAMERA_INTENT = 1000;
    private static final int SHOW_PICTURE_INTENT = 2000;

    @ViewById
    LinearLayout questoesLayout;

    @Extra(SURVEY_TYPE_EXTRA)
    SurveyType surveyType;

    @Extra(SURVEY_EXTRA)
    Survey mSurvey;

    @SystemService
    LayoutInflater inflater;

    @NonConfigurationInstance
    Uri mImageUri;

    private SparseArray<LinearLayout> mMapQuestaoLayout;

    @AfterViews
    void afterViews() {
        mMapQuestaoLayout = new SparseArray<LinearLayout>();
        mSurvey.setResposta(new AdministeredQuestionnaire(surveyType.getId(), new ArrayList<br.com.badrequest.insporte.integration.bean.Question>()));
        SurveyDataSource surveyDataSource = new SurveyDataSource(this);

        for(final Question question : surveyDataSource.listSurvey(surveyType)) {
            LinearLayout questionLayout = (LinearLayout) inflater.inflate(R.layout.question_layout, null);
            ((TextView) questionLayout.findViewById(R.id.perguntaTextView)).setText(question.getQuestion());

            ImageButton cameraButton = (ImageButton) questionLayout.findViewById(R.id.cameraIcon);
            cameraButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    String fotoPath = getExternalFilesDir("photos").getAbsolutePath() + "/"
                            + String.valueOf(question.getId()) + "_"
                            + String.valueOf(System.currentTimeMillis()) + ".jpg";
                    File foto = new File(fotoPath);
                    foto.mkdirs();
                    foto.delete();
                    mImageUri = Uri.fromFile(foto);
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);

                    startActivityForResult(cameraIntent, CAMERA_INTENT + question.getId());
                }
            });

            LinearLayout checksLayout = (LinearLayout) questionLayout.findViewById(R.id.checksLayout);
            for(Option option : question.getOptions()) {
                CheckBox line = new CheckBox(this);
                line.setOnCheckedChangeListener(registerCallback(question.getId(), option.getId()));
                line.setText(option.getText());
                checksLayout.addView(line);
            }

            ImageButton visualizarButton = (ImageButton) questionLayout.findViewById(R.id.photoIcon);
            visualizarButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(SurveyActivity.this, PictureView_.class);
                    intent.putExtra(PictureView.PERGUNTA_INTENT_EXTRA, mSurvey.getResposta().getOrAddQuestion(question.getId()).getImgPath());
                    startActivityForResult(intent, SHOW_PICTURE_INTENT+question.getId());
                }
            });

            mMapQuestaoLayout.put(question.getId(), questionLayout);
            questoesLayout.addView(questionLayout);
        }

        surveyDataSource.close();
    }

    @Click(R.id.btnProximo)
    void submit() {
        (new SurveyJson()).setJson(new GsonBuilder().setDateFormat("dd/MM/yyyy HH:mm:ss").create().toJson(mSurvey)).save();
        Intent serviceIntent = new Intent(this, br.com.badrequest.insporte.service.SyncUserDataService_.class);
        startService(serviceIntent);

        Toast.makeText(this, "Questionário salvo com sucesso. Enviando.", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, Feed_.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }

    @Click(R.id.btnAnterior)
    void cancel() {
        new AlertDialog.Builder(this)
                .setMessage("Deseja sair e excluir o questionário?")
                .setPositiveButton("Sim", new DialogController())
                .setNegativeButton("Não", new DialogController())
                .show();
    }


    private OnCheckedChangeListener registerCallback(final int idQuestion, final int idOption) {

        OnCheckedChangeListener listener = new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                br.com.badrequest.insporte.integration.bean.Question question = mSurvey.getResposta().getOrAddQuestion(idQuestion);
                QuestionOption option = new QuestionOption(idOption);
                if(isChecked) {
                    question.getOpcoes().add(option);
                } else {
                    question.getOpcoes().remove(option);
                }
            }
        };

        return listener;
    }

    private final class DialogController implements DialogInterface.OnClickListener {

        @Override
        public void onClick(DialogInterface dialogInterface, int which) {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    deleteImages();
                    mSurvey.setResposta(null);
                    finish();
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    dialogInterface.dismiss();
                    break;
            }

        }

        private void deleteImages() {
            for(br.com.badrequest.insporte.integration.bean.Question question : mSurvey.getResposta().getPerguntas()) {
                if(question.hasImage()) {
                    BitmapUtils.deleteImage(question.getImgPath());
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        cancel();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode/CAMERA_INTENT == 1 && resultCode == RESULT_OK) {

            br.com.badrequest.insporte.integration.bean.Question question = mSurvey.getResposta().getOrAddQuestion(requestCode%CAMERA_INTENT);
            if(question.hasImage()) {
                BitmapUtils.deleteImage(question.getImgPath());
            }
            String newFileName = getExternalFilesDir("photos").getAbsolutePath() + "/"
                    + String.valueOf(question.getIdPergunta()) + "_"
                    + String.valueOf(System.currentTimeMillis()) + ".jpg";

            try {
                BitmapUtils.saveResizedImage(mImageUri.getPath(), newFileName);
                BitmapUtils.deleteImage(mImageUri.getPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
            question.setImgPath(newFileName);
            mMapQuestaoLayout.get(question.getIdPergunta()).findViewById(R.id.photoIcon).setVisibility(View.VISIBLE);

        } else if(requestCode/SHOW_PICTURE_INTENT == 1 && resultCode == PictureView.RESULT_IMAGE_DELETED) {
            int questionId = requestCode%SHOW_PICTURE_INTENT;
            mSurvey.getResposta().getOrAddQuestion(questionId).setImgPath(null);
            mMapQuestaoLayout.get(questionId).findViewById(R.id.photoIcon).setVisibility(View.GONE);
        }
    }
}
