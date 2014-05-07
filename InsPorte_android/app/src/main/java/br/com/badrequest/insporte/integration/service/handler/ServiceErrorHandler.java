package br.com.badrequest.insporte.integration.service.handler;

import org.androidannotations.annotations.EBean;
import org.androidannotations.api.rest.RestErrorHandler;
import org.springframework.web.client.RestClientException;

@EBean
public class ServiceErrorHandler implements RestErrorHandler {

    @Override
    public void onRestClientExceptionThrown(RestClientException e) {
        //Log.e("RestClientException", e.getMessage());
        //TODO: Disparar broadcast informando o erro
    }

}
