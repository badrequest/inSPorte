package br.com.badrequest.insporte.activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import br.com.badrequest.insporte.R;
import br.com.badrequest.insporte.adapter.RouteListAdapter;
import br.com.badrequest.insporte.beans.Route;
import br.com.badrequest.insporte.database.datasource.RouteDataSource;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

import java.util.List;

@EActivity(R.layout.routes_activity)
public class Routes extends ActionBarActivity implements AdapterView.OnItemClickListener {

    @ViewById
    ListView list;

    List<Route> mRoutes;

    @AfterViews
    void afterViews() {
        RouteDataSource routeDataSource = new RouteDataSource(this);
        mRoutes = routeDataSource.listRoutes();

        list.setAdapter(new RouteListAdapter(this, R.layout.route_list_line, mRoutes));
        list.setOnItemClickListener(this);

        routeDataSource.close();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, Menu_.class);
        intent.putExtra(Menu.ROUTE_EXTRA, mRoutes.get(position));
        startActivity(intent);
    }
}
