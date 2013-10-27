package br.com.badrequest.insporte.activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import br.com.badrequest.insporte.R;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;

@EActivity(R.layout.login_activity)
public class Login extends ActionBarActivity {

    @AfterViews
    void afterViews() {

    }

    @Click(R.id.btnLogin)
    void login() {
        startActivity(new Intent(this, Feed_.class));
    }

}