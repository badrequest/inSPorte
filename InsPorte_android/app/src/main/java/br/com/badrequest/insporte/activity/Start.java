
package br.com.badrequest.insporte.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import br.com.badrequest.insporte.database.datasource.RouteDataSource;
import br.com.badrequest.insporte.preferences.LoginPrefs_;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.sharedpreferences.Pref;

@EActivity
public class Start extends ActionBarActivity {

    @Pref
    LoginPrefs_ loginPrefs;

    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Forcar descompactar base
        RouteDataSource routeDataSource = new RouteDataSource(this);
        routeDataSource.getReadableDatabase();
        routeDataSource.close();

        if(loginPrefs.pass().exists()) {
            startActivity(new Intent(this, WizardActivity_.class));
        } else {
            startActivity(new Intent(this, Feed_.class));
        }
        finish();
    }


}
