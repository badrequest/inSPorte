package br.com.badrequest.insporte.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import br.com.badrequest.insporte.R;
import br.com.badrequest.insporte.beans.FeedLine;
import twitter4j.Status;

import java.util.List;

public class FeedListAdapter extends ArrayAdapter<Status> {

    private Context mContext;
    private List<Status> mFeeds;
    private int mLayout;

    class ViewHolder {
        TextView line;
        Bitmap image;
    }

    public FeedListAdapter(Context context, int layout, List<Status> feeds) {
        super(context, layout, feeds);

        this.mContext = context;
        this.mLayout = layout;
        this.mFeeds = feeds;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mLayout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.line = (TextView) convertView.findViewById(R.id.line);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.line.setText(mFeeds.get(position).getText());
        return convertView;
    }

}
