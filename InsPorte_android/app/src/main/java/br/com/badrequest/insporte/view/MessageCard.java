package br.com.badrequest.insporte.view;

import android.view.View;
import android.widget.TextView;
import br.com.badrequest.insporte.R;
import com.fima.cardsui.objects.RecyclableCard;

/**
 * Created by gmarques on 5/6/14.
 */
public class MessageCard extends RecyclableCard {

    private String message;

    public MessageCard(String title, String message){
        super(title);
        this.message = message;
    }

    @Override
    protected void applyTo(View convertView) {
        ((TextView) convertView.findViewById(R.id.title)).setText(title);
        ((TextView) convertView.findViewById(R.id.message)).setText(message);
        convertView.findViewById(R.id.message).setVisibility(View.VISIBLE);
    }

    @Override
    protected int getCardLayoutId() {
        return R.layout.card_history;
    }
}
