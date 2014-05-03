package br.com.badrequest.insporte.view;

import android.view.View;
import android.widget.TextView;
import br.com.badrequest.insporte.R;
import com.fima.cardsui.objects.RecyclableCard;

/**
 * Created by gmarques on 5/2/14.
 */
public class HistoryCard extends RecyclableCard {

    public HistoryCard(String title){
        super(title);
    }

    @Override
    protected int getCardLayoutId() {
        return R.layout.card_history;
    }

    @Override
    protected void applyTo(View convertView) {
        ((TextView) convertView.findViewById(R.id.title)).setText(title);
    }
}
