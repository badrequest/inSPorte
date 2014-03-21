package br.com.badrequest.insporte.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import br.com.badrequest.insporte.R;
import br.com.badrequest.insporte.adapter.RouteListAdapter;
import br.com.badrequest.insporte.beans.Route;
import br.com.badrequest.insporte.database.datasource.RouteDataSource;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.OptionsMenu;
import com.googlecode.androidannotations.annotations.ViewById;

import java.util.List;

@OptionsMenu(R.menu.routes_menu)
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
    protected void onNewIntent(Intent intent) {
//        setIntent(intent);
//        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
//            String query = intent.getStringExtra(SearchManager.QUERY);
//
//            RouteDataSource routeDataSource = new RouteDataSource(this);
//            mRoutes = routeDataSource.listRoutes(query);
//
//            list.setAdapter(new RouteListAdapter(this, R.layout.route_list_line, mRoutes));
//            list.invalidate();
//        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, Menu_.class);
        intent.putExtra(Menu.ROUTE_EXTRA, mRoutes.get(position));
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                RouteDataSource routeDataSource = new RouteDataSource(Routes.this);
                mRoutes = routeDataSource.listRoutes(s);

                list.setAdapter(new RouteListAdapter(Routes.this, R.layout.route_list_line, mRoutes));
                list.invalidate();

                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

}
