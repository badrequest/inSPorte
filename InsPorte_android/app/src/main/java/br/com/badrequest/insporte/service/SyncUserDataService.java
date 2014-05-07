package br.com.badrequest.insporte.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import br.com.badrequest.insporte.bean.database.Like;
import br.com.badrequest.insporte.bean.database.Photo;
import br.com.badrequest.insporte.bean.database.SurveyJson;
import br.com.badrequest.insporte.integration.bean.Credentials;
import br.com.badrequest.insporte.integration.bean.Image;
import br.com.badrequest.insporte.integration.bean.Survey;
import br.com.badrequest.insporte.integration.bean.response.DefaultResponse;
import br.com.badrequest.insporte.integration.bean.response.SurveyResponse;
import br.com.badrequest.insporte.integration.service.handler.ServiceErrorHandler;
import br.com.badrequest.insporte.integration.service.mapper.InsporteServiceMapper;
import br.com.badrequest.insporte.preferences.LoginPrefs_;
import com.google.gson.GsonBuilder;
import org.androidannotations.annotations.*;
import org.androidannotations.annotations.rest.RestService;
import org.androidannotations.annotations.sharedpreferences.Pref;

@EService
public class SyncUserDataService extends Service {

    @RestService
    InsporteServiceMapper insporteServiceMapper;

    @Bean
    ServiceErrorHandler serviceErrorHandler;

    @Pref
    LoginPrefs_ loginPrefs;

    @Override
    public void onCreate() {
        syncData();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Background
    void syncData() {
        insporteServiceMapper.setRestErrorHandler(serviceErrorHandler);
        Credentials credentials = new Credentials(loginPrefs.uuid().get(), loginPrefs.pass().get());

        try {
            sendSurvey(credentials);
            sendLike(credentials);
            sendPhotos(credentials);
        } finally {
            pararServico();
        }
    }

    private void sendSurvey(Credentials credentials) {
        for(SurveyJson surveyToSend : SurveyJson.findAllToSend()) {
            Survey survey = new GsonBuilder().setDateFormat("dd/MM/yyyy HH:mm:ss").create().fromJson(surveyToSend.getJson(), Survey.class);
            survey.setCredentials(credentials);
            SurveyResponse response = insporteServiceMapper.survey(survey);
            if (response != null && response.success()) {
                surveyToSend.setSent(true).save();
                for(Photo photo : response.getIdImagens()) {
                    photo.setImgPath(survey.getResposta().getOrAddQuestion(photo.getIdPergunta()).getImgPath());
                    photo.save();
                }

                if(response.getIdImagemComentario() != null) {
                    new Photo(0, response.getIdImagemComentario(), survey.getComentario().getImgPath(), false).save();
                }
            }
        }
    }

    private void sendLike(Credentials credentials) {
        for (Like like : Like.findAllToSend()) {
            DefaultResponse response = insporteServiceMapper.addLike(new br.com.badrequest.insporte.integration.bean.Like(credentials, like.getRouteId(), like.isLike()));

            if(response != null && response.success()) {
                like.setSent(true).save();
            }
        }
    }

    @Trace
    void sendPhotos(Credentials credentials) {
        for (Photo photo : Photo.findAllToSend()) {
            DefaultResponse response = insporteServiceMapper.sendImage(new Image(credentials, photo.getIdImagem(), photo.getImgPath()));
            if(response != null && response.success()) {
                photo.setSent(true);
                photo.save();
            }
        }
    }

    @UiThread
    void pararServico() {
        stopSelf();
    }
}