package br.com.badrequest.insporte.integration.service.mapper;

import br.com.badrequest.insporte.integration.bean.SurveySubmitResponse;
import br.com.badrequest.insporte.integration.bean.Token;
import org.androidannotations.annotations.rest.Post;
import org.androidannotations.annotations.rest.Rest;
import org.androidannotations.api.rest.RestClientErrorHandling;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

/**
 * Created by gmarques on 3/26/14.
 */

@Rest(rootUrl = "http://www.insporte.com.br/transplot-rest/rest", converters = {GsonHttpMessageConverter.class})
public interface InsporteServiceMapper extends RestClientErrorHandling {

    @Post("/rest/auth/add")
    SurveySubmitResponse register(Token token);
}
