
package br.com.badrequest.insporte.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import br.com.badrequest.insporte.database.datasource.RouteDataSource;
import br.com.badrequest.insporte.util.Util;
import com.googlecode.androidannotations.annotations.EActivity;

@EActivity
public class Start extends ActionBarActivity {

    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Forcar descompactar base
        RouteDataSource routeDataSource = new RouteDataSource(this);
        routeDataSource.getReadableDatabase();
        routeDataSource.close();

        if(Util.getUser(this) == null) {
            startActivity(new Intent(this, WizardActivity_.class));
        } else {
            startActivity(new Intent(this, Feed_.class));
        }
        finish();
    }


}
