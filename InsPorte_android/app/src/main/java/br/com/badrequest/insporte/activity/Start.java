
package br.com.badrequest.insporte.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Window;
import android.widget.Toast;
import br.com.badrequest.insporte.database.datasource.RouteDataSource;
import br.com.badrequest.insporte.preferences.LoginPrefs_;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.WindowFeature;
import org.androidannotations.annotations.sharedpreferences.Pref;

@WindowFeature(Window.FEATURE_NO_TITLE)
@EActivity
public class Start extends ActionBarActivity {

    private final int REQUEST_CODE_RECOVER_PLAY_SERVICES = 1;

    @Pref
    LoginPrefs_ loginPrefs;

    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RouteDataSource routeDataSource = new RouteDataSource(this);
        routeDataSource.getReadableDatabase();
        routeDataSource.close();

        checkPlayServices();

        if(!loginPrefs.uuid().exists() || !loginPrefs.pass().exists()) {
            startActivity(new Intent(this, WizardActivity_.class));
        } else {
            startActivity(new Intent(this, Feed_.class));
        }

        finish();
    }

    private boolean checkPlayServices() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (status != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(status)) {
                showErrorDialog(status);
            } else {
                Toast.makeText(this, "Dispositivo não suportado.",
                        Toast.LENGTH_LONG).show();
                finish();
            }
            return false;
        }
        return true;
    }

    void showErrorDialog(int code) {
        GooglePlayServicesUtil.getErrorDialog(code, this,
                REQUEST_CODE_RECOVER_PLAY_SERVICES).show();
    }

    @OnActivityResult(REQUEST_CODE_RECOVER_PLAY_SERVICES)
    void onResult(int resultCode) {
        if (resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "O Google Play Services é necessário.",
                    Toast.LENGTH_SHORT).show();
            finish();
        }

    }


}
