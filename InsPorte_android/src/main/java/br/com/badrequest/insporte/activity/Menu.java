package br.com.badrequest.insporte.activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;
import br.com.badrequest.insporte.R;
import br.com.badrequest.insporte.beans.Route;
import br.com.badrequest.insporte.beans.Survey;
import br.com.badrequest.insporte.beans.SurveyType;
import br.com.badrequest.insporte.util.Util;
import com.googlecode.androidannotations.annotations.*;

@EActivity(R.layout.menu_activity)
public class Menu extends ActionBarActivity {

    public static final String ROUTE_EXTRA = "ROUTE";
    public static final int SURVEY_INTENT = 2000;

    private Survey mSurvey = new Survey();

    @ViewById
    TextView route;

    @Extra(ROUTE_EXTRA)
    Route mRoute;

    @AfterViews
    void afterViews() {
        route.setText(mRoute.getName());
        Util.sendNotification(this, "Devido a manifestações, sua linha sofrerá atraso de 25 minutos!");
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

        intent.putExtra(SurveyActivity.SURVEY_EXTRA, surveyType);
        startActivity(intent);
    }

    @Click(R.id.btnComentario)
    void comment() {
        Intent intent = new Intent(this, Comment_.class);
        intent.putExtra(Comment.SURVEY_EXTRA, mSurvey);
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SURVEY_INTENT && resultCode == RESULT_OK) {
            mSurvey = (Survey) data.getSerializableExtra(Comment.SURVEY_EXTRA);
        }
    }

}
