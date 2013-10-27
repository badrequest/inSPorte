
package br.com.badrequest.insporte.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import br.com.badrequest.insporte.R;
import br.com.badrequest.insporte.database.datasource.RouteDataSource;
import br.com.badrequest.insporte.util.Constants;
import br.com.badrequest.insporte.util.Util;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.EActivity;

@EActivity
public class Start extends ActionBarActivity {

    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Forcar descompactar base
        RouteDataSource routeDataSource = new RouteDataSource(this);
        routeDataSource.getReadableDatabase();
        routeDataSource.close();

        if(Util.getUser(this).getBusCard() == null) {
            startActivity(new Intent(this, WizardActivity_.class));
        } else {
            startActivity(new Intent(this, Menu_.class));
        }
        finish();
    }


}
