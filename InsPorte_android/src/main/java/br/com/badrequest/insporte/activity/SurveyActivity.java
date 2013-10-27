package br.com.badrequest.insporte.activity;

import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import br.com.badrequest.insporte.R;
import br.com.badrequest.insporte.beans.Option;
import br.com.badrequest.insporte.beans.Question;
import br.com.badrequest.insporte.beans.SurveyType;
import br.com.badrequest.insporte.database.datasource.SurveyDataSource;
import com.googlecode.androidannotations.annotations.*;

import java.util.List;

@EActivity(R.layout.survey_activity)
public class SurveyActivity extends ActionBarActivity {

    public static final String SURVEY_EXTRA = "SURVEY";

    @ViewById
    LinearLayout questoesLayout;

    @Extra(SURVEY_EXTRA)
    SurveyType surveyType;

    @SystemService
    LayoutInflater inflater;

    @AfterViews
    void afterViews() {
        SurveyDataSource surveyDataSource = new SurveyDataSource(this);
        List<Question> questionList = surveyDataSource.listSurvey(surveyType);

        for(Question question : questionList) {
            LinearLayout questionLayout = (LinearLayout) inflater.inflate(R.layout.question_layout, null);
            ((TextView) questionLayout.findViewById(R.id.perguntaTextView)).setText(question.getQuestion());

            LinearLayout checksLayout = (LinearLayout) questionLayout.findViewById(R.id.checksLayout);
            for(Option option : question.getOptions()) {
                CheckBox line = new CheckBox(this);
                line.setText(option.getText());
                checksLayout.addView(line);
            }

            questoesLayout.addView(questionLayout);
        }
    }

    @Click(R.id.btnAnterior)
    void cancel() {
        finish();
    }

    @Click(R.id.btnProximo)
    void submit() {
        finish();
    }
}
