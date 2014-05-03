package br.com.badrequest.insporte.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import br.com.badrequest.insporte.R;
import br.com.badrequest.insporte.activity.base.FullTranslucentActivity;
import br.com.badrequest.insporte.bean.Route;
import br.com.badrequest.insporte.bean.SurveyType;
import br.com.badrequest.insporte.bean.database.Like;
import br.com.badrequest.insporte.integration.bean.Survey;
import br.com.badrequest.insporte.preferences.LoginPrefs_;
import org.androidannotations.annotations.*;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.List;

@EActivity(R.layout.menu_activity)
public class Menu extends FullTranslucentActivity {

    public static final String ROUTE_EXTRA = "ROUTE";
    public static final int SURVEY_INTENT = 2000;

    private Survey mSurvey = new Survey();

    @Pref
    LoginPrefs_ loginPrefs;

    @ViewById
    TextView route;

    @Extra(ROUTE_EXTRA)
    Route mRoute;

    @AfterViews
    void afterViews() {
        super.systemBarTint();

        route.setText(mRoute.getName());
        Like like = Like.getLikeByRoute(mRoute.getCod());

        if(like != null) {
            if(like.isLike()) like();
            else notLike();
        }
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
        updateLike(false);
    }

    @Click(R.id.imageButtonLike)
    void like() {
        ((ImageButton) findViewById(R.id.imageButtonNotLike)).setImageResource(R.drawable.ic_not_like);
        ((ImageButton) findViewById(R.id.imageButtonLike)).setImageResource(R.drawable.ic_like_ativo);
        updateLike(true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SURVEY_INTENT && resultCode == RESULT_OK) {
            mSurvey = (Survey) data.getSerializableExtra(Comment.SURVEY_EXTRA);
        }
    }

    private void updateLike(boolean likeState) {
        List<Like> likes = Like.find(Like.class, "route_id = ?", String.valueOf(mRoute.getCod()));
        if(!likes.isEmpty()) {
            Like like = likes.get(0);
            if (like.isLike() != likeState) {
                like.setLike(likeState).save();
            }
        } else {
            new Like(likeState, mRoute.getCod()).save();
        }

        startService(new Intent(this, br.com.badrequest.insporte.service.SyncUserDataService_.class));
    }

}
