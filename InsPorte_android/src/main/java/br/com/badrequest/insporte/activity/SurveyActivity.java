package br.com.badrequest.insporte.activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import android.widget.CompoundButton.OnCheckedChangeListener;
import br.com.badrequest.insporte.R;
import br.com.badrequest.insporte.beans.Option;
import br.com.badrequest.insporte.beans.Question;
import br.com.badrequest.insporte.beans.SurveyType;
import br.com.badrequest.insporte.beans.dao.JsonSubmit;
import br.com.badrequest.insporte.beans.integration.RestBean;
import br.com.badrequest.insporte.beans.integration.SurveyResult;
import br.com.badrequest.insporte.database.datasource.SurveyDataSource;
import com.google.gson.GsonBuilder;
import com.googlecode.androidannotations.annotations.*;

import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.survey_activity)
public class SurveyActivity extends ActionBarActivity {

    public static final String SURVEY_TYPE_EXTRA = "SURVEY_TYPE";
    public static final String SURVEY_EXTRA = "SURVEY";

    @ViewById
    LinearLayout questoesLayout;

    @Extra(SURVEY_TYPE_EXTRA)
    SurveyType surveyType;

    //FIXME: Nao usar bean de integration no projeto. Criar beans internos
    @Extra(SURVEY_EXTRA)
    RestBean mSurvey;

    @SystemService
    LayoutInflater inflater;

    @AfterViews
    void afterViews() {
        mSurvey.setResposta(new SurveyResult(surveyType.getId(), new ArrayList<br.com.badrequest.insporte.beans.integration.Question>()));

        SurveyDataSource surveyDataSource = new SurveyDataSource(this);
        List<Question> questionList = surveyDataSource.listSurvey(surveyType);

        for(Question question : questionList) {
            LinearLayout questionLayout = (LinearLayout) inflater.inflate(R.layout.question_layout, null);
            ((TextView) questionLayout.findViewById(R.id.perguntaTextView)).setText(question.getQuestion());

            ImageButton cameraButton = (ImageButton) questionLayout.findViewById(R.id.cameraIcon);
            cameraButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(SurveyActivity.this, "Fotos estarão disponíveis apartir da próxima versão.", Toast.LENGTH_LONG).show();
                }
            });

            LinearLayout checksLayout = (LinearLayout) questionLayout.findViewById(R.id.checksLayout);
            for(Option option : question.getOptions()) {
                CheckBox line = new CheckBox(this);
                line.setOnCheckedChangeListener(registra(question.getId(), option.getId()));
                line.setText(option.getText());
                checksLayout.addView(line);
            }

            questoesLayout.addView(questionLayout);
        }
    }

    @Click(R.id.btnAnterior)
    void cancel() {
        mSurvey.setResposta(null);
        finish();
    }

    @Click(R.id.btnProximo)
    void submit() {
        (new JsonSubmit()).setJson((new GsonBuilder().setDateFormat("dd/MM/yyyy HH:mm:ss").create()).toJson(mSurvey)).insert();
        Intent serviceIntent = new Intent(this, br.com.badrequest.insporte.service.SyncUserDataService_.class);
        startService(serviceIntent);

        Toast.makeText(this, "Questionário salvo com sucesso. Enviando.", Toast.LENGTH_LONG);
        Intent intent = new Intent(this, Feed_.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }

    private OnCheckedChangeListener registra(final int idQuestion, final int idOption) {

        OnCheckedChangeListener listener = new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                List<br.com.badrequest.insporte.beans.integration.Question> questions = mSurvey.getResposta().getPerguntas();

                br.com.badrequest.insporte.beans.integration.Question question = new br.com.badrequest.insporte.beans.integration.Question(idQuestion);

                if(questions.contains(question)){
                    question = questions.get(questions.indexOf(question));
                } else {
                    question.setOpcoes(new ArrayList<br.com.badrequest.insporte.beans.integration.Option>());
                    questions.add(question);
                }

                br.com.badrequest.insporte.beans.integration.Option option = new br.com.badrequest.insporte.beans.integration.Option(idOption);
                if(isChecked) {
                    question.getOpcoes().add(option);
                } else {
                    question.getOpcoes().remove(option);
                }
            }
        };

        return listener;
    }

}
