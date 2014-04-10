package br.com.badrequest.insporte.business;

import android.content.Context;
import br.com.badrequest.insporte.integration.bean.SurveySubmitResponse;
import br.com.badrequest.insporte.integration.bean.Token;
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

/**
 * Created by gmarques on 3/28/14.
 */
@EBean
public class LoginBusiness {

    //TODO: Avaliar colocar isso em um metodo rest
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
            SurveySubmitResponse surveySubmitResponse = insporteServiceMapper.register(new Token(GoogleAuthUtil.getToken(context, email, googleScope)));

            if(surveySubmitResponse.success()) {
                loginPrefs.userId().put(email);
                loginPrefs.pass().put(surveySubmitResponse.getAns());
                return true;
            }

        } catch (IOException e) {
            //TODO: Tratamento de exceptions
        } catch (GoogleAuthException e) {
            //TODO: Tratamento de exceptions
        }

        return false;
    }

}
