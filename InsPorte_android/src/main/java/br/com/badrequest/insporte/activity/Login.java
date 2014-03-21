package br.com.badrequest.insporte.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import br.com.badrequest.insporte.R;
import br.com.badrequest.insporte.beans.User;
import br.com.badrequest.insporte.beans.integration.Response;
import br.com.badrequest.insporte.beans.integration.Token;
import br.com.badrequest.insporte.util.Constants;
import br.com.badrequest.insporte.util.ServiceRequest;
import br.com.badrequest.insporte.util.Util;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.gson.Gson;
import com.googlecode.androidannotations.annotations.*;

import java.io.IOException;

@EActivity(R.layout.login_activity)
public class Login extends Activity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    @ViewById(R.id.sign_in_button)
    SignInButton signInButton;

    /* Request code used to invoke sign in user interactions. */
    private static final int RC_SIGN_IN = 0;

    private GoogleApiClient mGoogleApiClient;

    private boolean mIntentInProgress;

    private boolean mSignInClicked;

    private ConnectionResult mConnectionResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        int id = getResources().getIdentifier("config_enableTranslucentDecor", "bool", "android");
        if (id != 0) { //KitKat or higher
            boolean enabled = getResources().getBoolean(id);
            // enabled = are translucent bars supported on this device
            if (enabled) {
                Window w = getWindow();
                w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    protected void onStop() {
        super.onStop();

        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @AfterViews
    void afterViews() {

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API, null)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .build();
    }

    @Click(R.id.sign_in_button)
    void signInGoogle() {
        mSignInClicked = true;
        mGoogleApiClient.connect();
    }

    @Background
    void teste() {
        try {
            String token = GoogleAuthUtil.getToken(this, Plus.AccountApi.getAccountName(mGoogleApiClient), "audience:server:client_id:549088494676-ukvqmup4o945c2memdcgudc631nm1mdk.apps.googleusercontent.com");

            Log.d("INSPORTE", token);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (GoogleAuthException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onConnected(Bundle bundle) {
        authWithServer();
    }

    @Background
    void authWithServer(){
        try {
            String token = GoogleAuthUtil.getToken(this, Plus.AccountApi.getAccountName(mGoogleApiClient), "audience:server:client_id:549088494676-ukvqmup4o945c2memdcgudc631nm1mdk.apps.googleusercontent.com");
            Response response = (new Gson()).fromJson(ServiceRequest.makeJSONRequest(Constants.URL_WEBSERVICE + "/rest/auth/add", (new Gson()).toJson(new Token(token))), Response.class);

            if(!response.getAns().equalsIgnoreCase("err")) {
                Util.saveUser(this, new User(Plus.AccountApi.getAccountName(mGoogleApiClient), response.getAns()));
                startFeedActivity();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (GoogleAuthException e) {
            e.printStackTrace();
        }
    }

    @UiThread
    void startFeedActivity() {
        startActivity(new Intent(this, Feed_.class));
        finish();
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if(!mIntentInProgress) {
            mConnectionResult = result;

            if(mSignInClicked) {
                resolveSignInError();
            }
        }
    }

    private void resolveSignInError() {
        if (mConnectionResult.hasResolution()) {
            try {
                mIntentInProgress = true;
                mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
            } catch (IntentSender.SendIntentException e) {
                mIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        }
    }


    protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
        if (requestCode == RC_SIGN_IN) {

            if (responseCode != RESULT_OK) {
                mSignInClicked = false;
            }

            mIntentInProgress = false;

            if (!mGoogleApiClient.isConnecting()) {
                mGoogleApiClient.connect();
            }
        }
    }

}