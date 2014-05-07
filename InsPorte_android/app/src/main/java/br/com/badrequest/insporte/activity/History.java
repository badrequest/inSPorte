package br.com.badrequest.insporte.activity;

import br.com.badrequest.insporte.R;
import br.com.badrequest.insporte.activity.base.FullTranslucentActivity;
import br.com.badrequest.insporte.bean.database.SurveyJson;
import br.com.badrequest.insporte.database.datasource.RouteDataSource;
import br.com.badrequest.insporte.database.datasource.SurveyDataSource;
import br.com.badrequest.insporte.integration.bean.Question;
import br.com.badrequest.insporte.integration.bean.QuestionOption;
import br.com.badrequest.insporte.integration.bean.Survey;
import br.com.badrequest.insporte.view.HistoryCard;
import br.com.badrequest.insporte.view.MessageCard;
import com.fima.cardsui.views.CardUI;
import com.google.gson.GsonBuilder;
import com.orm.SugarRecord;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.history_activity)
public class History extends FullTranslucentActivity {

    @ViewById(R.id.cards)
    CardUI mCardView;

    @AfterViews
    void loadCards() {
        boolean hasCards = false;
        systemBarTint();
        SurveyDataSource surveyDataSource = new SurveyDataSource(this);
        RouteDataSource routeDataSource = new RouteDataSource(this);

        for(SurveyJson surveyDB : SugarRecord.listAll(SurveyJson.class)) {

            Survey survey = new GsonBuilder().setDateFormat("dd/MM/yyyy HH:mm:ss").create().fromJson(surveyDB.getJson(), Survey.class);
            HistoryCard card = new HistoryCard(getApplicationContext(), routeDataSource.listRoutes(survey.getInfo().getId()).get(0).getName());
            for(Question question : survey.getResposta().getPerguntas()) {
                List<String> options = new ArrayList<String>();
                for(QuestionOption option : question.getOpcoes()) {
                    options.add(surveyDataSource.getOption(option.getIdOpcao()).getText());
                }
                card.addQuestion(surveyDataSource.getQuestion(question.getIdPergunta()).getQuestion(), options);
            }
            if(StringUtils.isNotBlank(survey.getComentario().getTexto())) {
                card.addQuestion("Comentário", survey.getComentario().getTexto());
            }

            if(!card.isEmpty()) {
                mCardView.addCard(card);
                hasCards = true;
            }
        }

        if(!hasCards) {
            mCardView.addCard(new MessageCard("Nenhuma avaliação", "Para visualizar o histórico, é necessario já ter enviado alguma avaliação."));
        }

        mCardView.refresh();

        surveyDataSource.close();
        routeDataSource.close();
    }
}
