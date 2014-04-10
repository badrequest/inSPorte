package br.com.badrequest.insporte.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import br.com.badrequest.insporte.R;
import br.com.badrequest.insporte.bean.Route;
import br.com.badrequest.insporte.bean.SurveyType;
import br.com.badrequest.insporte.integration.bean.Credentials;
import br.com.badrequest.insporte.integration.bean.ExtraSurveyInfo;
import br.com.badrequest.insporte.integration.bean.Survey;
import org.androidannotations.annotations.*;

import java.util.Date;

@EActivity(R.layout.menu_activity)
public class Menu extends ActionBarActivity {

    public static final String ROUTE_EXTRA = "ROUTE";
    public static final int SURVEY_INTENT = 2000;

    //FIXME: Nao usar bean de integration no projeto. Criar beans internos
    private Survey mSurvey = new Survey();

    @ViewById
    TextView route;

    @Extra(ROUTE_EXTRA)
    Route mRoute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mSurvey.setCredentials(new Credentials("giovannimarques33@gmail.com", ""));
        mSurvey.setInfo(new ExtraSurveyInfo(mRoute.getCod(), 0.0, 0.0, new Date()));
        super.onCreate(savedInstanceState);
    }

    @AfterViews
    void afterViews() {
        route.setText(mRoute.getName());
        //Util.sendNotification(this, "Devido a manifestações, sua linha sofrerá atraso de 25 minutos!");
    }

    @Click({R.id.imageButtonOnibus, R.id.imageButtonMotorista, R.id.imageButtonPonto, R.id.imageButtonIncidente})
    void survey(View v) {
        Intent intent = new Intent(this, SurveyActivity_.class);
        SurveyType surveyType = null;

        switch (v.getId()) {
            case R.id.imageButtonOnibus:
                surveyType = SurveyType.ONIBUS;
                break;
            case R.id.imageButtonMotorista:
                surveyType = SurveyType.MOTORISTA;
                break;
            case R.id.imageButtonPonto:
                surveyType = SurveyType.PONTO;
                break;
            case R.id.imageButtonIncidente:
                surveyType = SurveyType.INCIDENTE;
                break;
        }

        intent.putExtra(SurveyActivity.SURVEY_TYPE_EXTRA, surveyType);
        intent.putExtra(SurveyActivity.SURVEY_EXTRA, mSurvey);
        startActivity(intent);
    }

    @Click(R.id.btnComentario)
    void comment() {
        Intent intent = new Intent(this, Comment_.class);
        intent.putExtra(Comment.SURVEY_EXTRA, mSurvey);
        startActivityForResult(intent, SURVEY_INTENT);
    }

    @Click(R.id.imageButtonNotLike)
    void notLike() {
        ((ImageButton) findViewById(R.id.imageButtonNotLike)).setImageResource(R.drawable.ic_not_like_ativo);
        ((ImageButton) findViewById(R.id.imageButtonLike)).setImageResource(R.drawable.ic_like);
    }

    @Click(R.id.imageButtonLike)
    void like() {
        ((ImageButton) findViewById(R.id.imageButtonNotLike)).setImageResource(R.drawable.ic_not_like);
        ((ImageButton) findViewById(R.id.imageButtonLike)).setImageResource(R.drawable.ic_like_ativo);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SURVEY_INTENT && resultCode == RESULT_OK) {
            mSurvey = (Survey) data.getSerializableExtra(Comment.SURVEY_EXTRA);
        }
    }

}
