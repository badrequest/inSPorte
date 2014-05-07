package br.com.badrequest.insporte.view;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import br.com.badrequest.insporte.R;
import com.fima.cardsui.objects.RecyclableCard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by gmarques on 5/2/14.
 */
public class HistoryCard extends RecyclableCard {

    private List<Pair<String, List<String>>> questions = new ArrayList<Pair<String, List<String>>>();

    private Context context;

    public HistoryCard(Context context, String title){
        super(title);
        this.context = context;
    }

    public void addQuestion(String question, List<String> options) {
        questions.add(new Pair<String, List<String>>(question, options));
    }

    public void addQuestion(String question, String option) {
        questions.add(new Pair<String, List<String>>(question, Arrays.asList(new String[] {option})));
    }

    public boolean isEmpty() {
        return questions.isEmpty();
    }

    @Override
    protected int getCardLayoutId() {
        return R.layout.card_history;
    }

    protected void applyTo(View convertView) {
        LayoutInflater inflater = LayoutInflater.from(context);

        ((TextView) convertView.findViewById(R.id.title)).setText(title);
        LinearLayout questionsLayout = (LinearLayout) convertView.findViewById(R.id.questions);

        for (Pair<String, List<String>> question : questions) {
            LinearLayout questionHistory = (LinearLayout) inflater.inflate(R.layout.question_history, null);
            ((TextView) questionHistory.findViewById(R.id.question)).setText(question.first);

            String options = new String();
            for (String option : question.second) {
                options += (option + "\n");
            }

            ((TextView) questionHistory.findViewById(R.id.options)).setText(options.substring(0, options.lastIndexOf("\n")));

            questionsLayout.addView(questionHistory);
        }
    }
}
