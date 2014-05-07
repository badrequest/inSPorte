package br.com.badrequest.insporte.activity;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;
import br.com.badrequest.insporte.R;
import br.com.badrequest.insporte.activity.base.FullTranslucentActivity;
import br.com.badrequest.insporte.business.LoginBusiness;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.nvanbenschoten.motion.ParallaxImageView;
import org.androidannotations.annotations.*;

@WindowFeature(Window.FEATURE_NO_TITLE)
@EActivity(R.layout.login_activity)
public class Login extends FullTranslucentActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    @Bean
    LoginBusiness loginBusiness;

    @ViewById(R.id.sign_in_button)
    SignInButton signInButton;

    @ViewById
    ParallaxImageView background;

    private static final int RC_SIGN_IN = 0;
    private static final int REQUEST_CODE_RECOVER_PLAY_SERVICES = 1;

    private GoogleApiClient mGoogleApiClient;

    private boolean mIntentInProgress;

    private boolean mSignInClicked;

    private ConnectionResult mConnectionResult;

    @Override
    protected void onStart() {
        super.onStart();
        if(background != null) background.registerSensorManager();
    }

    @Override
    protected void onStop() {
        super.onStop();
        background.unregisterSensorManager();

        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();

        }
    }

    @AfterViews
    void afterViews() {
        background.registerSensorManager();

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

    @Click(R.id.signInAnonymous)
    void signInAnonymous() {
        authAnonymous();
    }

    @Override
    public void onConnected(Bundle bundle) {
        authWithGoogle();
    }

    @Background
    void authWithGoogle() {
        if(loginBusiness.googleLogin(Plus.AccountApi.getAccountName(mGoogleApiClient))) {
            startFeedActivity();
        } else {
            showToast("Erro ao registrar");
        }
    }

    @Background
    void authAnonymous() {
        if(loginBusiness.anonymousLogin()) {
            startFeedActivity();
        } else {
            showToast("Desculpe, ocorreu um erro ao fazer login.\nTente novamente!");
        }
    }

    @UiThread
    void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
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
        } else {
            GooglePlayServicesUtil.getErrorDialog(mConnectionResult.getErrorCode(), this,
                    REQUEST_CODE_RECOVER_PLAY_SERVICES).show();
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