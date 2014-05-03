package br.com.badrequest.insporte.activity;

import br.com.badrequest.insporte.R;
import br.com.badrequest.insporte.activity.base.FullTranslucentActivity;
import br.com.badrequest.insporte.bean.database.SurveyJson;
import br.com.badrequest.insporte.integration.bean.Survey;
import br.com.badrequest.insporte.view.HistoryCard;
import com.fima.cardsui.objects.CardStack;
import com.fima.cardsui.views.CardUI;
import com.google.gson.GsonBuilder;
import com.orm.SugarRecord;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.history_activity)
public class History extends FullTranslucentActivity {

    @ViewById(R.id.cards)
    CardUI mCardView;

    @AfterViews
    void loadCards() {
        CardStack onibusStack = new CardStack("Ônibus");
        CardStack motoristaStack = new CardStack("Motorista");
        CardStack pontoStack = new CardStack("Ponto");
        CardStack incidenteStack = new CardStack("Incidente");

        mCardView.addStack(onibusStack);
        for(SurveyJson surveyDB : SugarRecord.listAll(SurveyJson.class) ) {
            Survey survey = new GsonBuilder().setDateFormat("dd/MM/yyyy HH:mm:ss").create().fromJson(surveyDB.getJson(), Survey.class);
            mCardView.addCard(new HistoryCard(survey.getResposta().getPerguntas().get(0).getIdPergunta() + ""));
        }

        mCardView.addStack(motoristaStack);
        mCardView.addStack(pontoStack);
        mCardView.addStack(incidenteStack);
        mCardView.refresh();
    }
}
