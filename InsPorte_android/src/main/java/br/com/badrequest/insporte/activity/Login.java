package br.com.badrequest.insporte.activity;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import br.com.badrequest.insporte.R;
import br.com.badrequest.insporte.business.LoginBusiness;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import org.androidannotations.annotations.*;

@EActivity(R.layout.login_activity)
public class Login extends TranslucentActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    @Bean
    LoginBusiness loginBusiness;

    @ViewById(R.id.sign_in_button)
    SignInButton signInButton;

    /* Request code used to invoke sign in user interactions. */
    private static final int RC_SIGN_IN = 0;

    private GoogleApiClient mGoogleApiClient;

    private boolean mIntentInProgress;

    private boolean mSignInClicked;

    private ConnectionResult mConnectionResult;

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

    @Override
    public void onConnected(Bundle bundle) {
        authWithServer();
    }

    @Background
    void authWithServer() {
        if(loginBusiness.googleLogin(Plus.AccountApi.getAccountName(mGoogleApiClient))) {
            startFeedActivity();
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