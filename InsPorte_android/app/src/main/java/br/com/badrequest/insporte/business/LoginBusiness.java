package br.com.badrequest.insporte.business;

import android.content.Context;
import br.com.badrequest.insporte.integration.bean.Token;
import br.com.badrequest.insporte.integration.bean.response.LoginResponse;
import br.com.badrequest.insporte.integration.service.handler.ServiceErrorHandler;
import br.com.badrequest.insporte.integration.service.mapper.InsporteServiceMapper;
import br.com.badrequest.insporte.preferences.LoginPrefs_;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.rest.RestService;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by gmarques on 3/28/14.
 */
@EBean
public class LoginBusiness {

    private static final String googleScope = "audience:server:client_id:549088494676-ukvqmup4o945c2memdcgudc631nm1mdk.apps.googleusercontent.com";

    @RootContext
    Context context;

    @RestService
    InsporteServiceMapper insporteServiceMapper;

    @Bean
    ServiceErrorHandler serviceErrorHandler;

    @Pref
    LoginPrefs_ loginPrefs;

    @AfterInject
    void setServiceErrorHandler() {
        insporteServiceMapper.setRestErrorHandler(serviceErrorHandler);
    }

    public boolean googleLogin(String email) {

        try {
            String uuid = uuid();
            LoginResponse loginResponse = insporteServiceMapper.registerGoogle(new Token(uuid, GoogleAuthUtil.getToken(context, email, googleScope)));

            if(loginResponse.success()) {
                loginPrefs.edit()
                        .uuid().put(uuid)
                        .email().put(email)
                        .pass().put(loginResponse.getAns())
                        .apply();

                return true;
            } else {
                return false;
            }

        } catch (IOException e) {
            //TODO: Tratamento de exceptions
        } catch (GoogleAuthException e) {
            //TODO: Tratamento de exceptions
        }

        return false;
    }

    public boolean anonymousLogin() {
        String uuid = uuid();

        LoginResponse loginResponse = insporteServiceMapper.registerAnonymous(new Token(uuid));
        if(loginResponse.success()) {
            loginPrefs.edit()
                    .uuid().put(uuid)
                    .pass().put(loginResponse.getAns())
                    .apply();

            return true;
        } else {
            return false;
        }

    }

    private String uuid(){
        return UUID.randomUUID().toString().replaceAll("[\\s\\-()]", "");
    }

}
