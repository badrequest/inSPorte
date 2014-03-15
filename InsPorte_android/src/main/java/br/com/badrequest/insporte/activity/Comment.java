package br.com.badrequest.insporte.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import br.com.badrequest.insporte.R;
import br.com.badrequest.insporte.beans.Survey;
import br.com.badrequest.insporte.util.BitmapUtils;
import br.com.badrequest.insporte.util.Constants;
import com.googlecode.androidannotations.annotations.*;

import java.io.IOException;

@EActivity(R.layout.coment_activity)
@OptionsMenu(R.menu.comment)
public class Comment extends ActionBarActivity {

    private static final int CAMERA_INTENT = 1001;

    public static final String SURVEY_EXTRA = "SURVEY";

    private Uri mImageUri;

    @ViewById
    EditText commentEditText;

    @ViewById
    ImageView pictureImageView;

    @ViewById
    View pictureLayout;

    @Extra(SURVEY_EXTRA)
    @NonConfigurationInstance
    Survey mSurvey;

    @AfterViews
    void afterViews() {
        if (mSurvey.getPathPictureComment() != null) {
            pictureLayout.setVisibility(View.VISIBLE);
            pictureImageView.setImageBitmap(BitmapFactory.decodeFile(mSurvey.getPathPictureComment()));
        } else {
            pictureLayout.setVisibility(View.GONE);
            pictureImageView.setImageBitmap(null);
        }

        commentEditText.setText(mSurvey.getComment());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void finish () {
        Intent data = new Intent();
        data.putExtra(Comment.SURVEY_EXTRA, mSurvey);
        setResult(RESULT_OK, data);

        super.finish();
    }

    @OptionsItem(R.id.action_photo)
    void takePicture() {
        Toast.makeText(this, "Fotos estarão disponíveis apartir da próxima versão.", Toast.LENGTH_LONG).show();

//        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//
//        String fotoPath = getExternalFilesDir(Constants.PICTURES_PATH).getAbsolutePath() + "/"
//                + "comentario_"
//                + String.valueOf(System.currentTimeMillis()) + ".jpg";
//        File picture = new File(fotoPath);
//        picture.mkdirs();
//        picture.delete();
//        mImageUri = Uri.fromFile(picture);
//        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
//        startActivityForResult(cameraIntent, CAMERA_INTENT);
    }

    @Click(R.id.btnOK)
    void close() {
        finish();
    }

    @Click(R.id.btnDelete)
    void delete() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        pictureLayout.setVisibility(View.GONE);
                        BitmapUtils.deleteImage(mSurvey.getPathPictureComment());
                        mSurvey.setPathPictureComment(null);
                        pictureImageView.setImageBitmap(null);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Deseja excluir a foto?")
                .setPositiveButton("Sim", dialogClickListener)
                .setNegativeButton("Não", dialogClickListener)
                .show();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_INTENT && resultCode == RESULT_OK) {

            //Se ja tiver foto, exclui a anterior
            if(mSurvey.getPathPictureComment() != null) {
                BitmapUtils.deleteImage(mSurvey.getPathPictureComment());
            }

            String newFileName = getExternalFilesDir(Constants.PICTURES_PATH).getAbsolutePath() + "/"
                    + "comentario_"
                    + String.valueOf(System.currentTimeMillis()) + ".jpg";

            try {
                BitmapUtils.saveResizedImage(mImageUri.getPath(), newFileName);
                BitmapUtils.deleteImage(mImageUri.getPath());
            } catch (IOException e) {
                e.printStackTrace();
            }

            mSurvey.setPathPictureComment(newFileName);
            pictureLayout.setVisibility(View.VISIBLE);
            pictureImageView.setImageBitmap(BitmapFactory.decodeFile(mSurvey.getPathPictureComment()));
        }
    }


}
