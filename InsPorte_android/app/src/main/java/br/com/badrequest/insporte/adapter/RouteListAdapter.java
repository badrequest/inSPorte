package br.com.badrequest.insporte.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import br.com.badrequest.insporte.R;
import br.com.badrequest.insporte.bean.Route;

import java.util.List;

public class RouteListAdapter extends ArrayAdapter<Route> {

    private Context mContext;
    private List<Route> mRoutes;
    private int mLayout;

    class ViewHolder {
        TextView name;
        TextView cod;
    }

    public RouteListAdapter(Context context, int layout, List<Route> routes) {
        super(context, layout, routes);

        this.mContext = context;
        this.mLayout = layout;
        this.mRoutes = routes;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mLayout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.cod = (TextView) convertView.findViewById(R.id.cod);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.name.setText(mRoutes.get(position).getName());
        viewHolder.cod.setText(mRoutes.get(position).getCod());
        return convertView;
    }

    @Override
    public int getCount() {
        return mRoutes.size();
    }

    public void setRoutes(List<Route> routes) {
        this.mRoutes = routes;
    }

}
