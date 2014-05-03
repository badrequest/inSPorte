package br.com.badrequest.insporte.integration.service.mapper;

import br.com.badrequest.insporte.integration.bean.Image;
import br.com.badrequest.insporte.integration.bean.Like;
import br.com.badrequest.insporte.integration.bean.Survey;
import br.com.badrequest.insporte.integration.bean.Token;
import br.com.badrequest.insporte.integration.bean.response.DefaultResponse;
import br.com.badrequest.insporte.integration.bean.response.LoginResponse;
import br.com.badrequest.insporte.integration.bean.response.SurveyResponse;
import org.androidannotations.annotations.rest.Post;
import org.androidannotations.annotations.rest.Rest;
import org.androidannotations.api.rest.RestClientErrorHandling;

/**
 * Created by gmarques on 3/26/14.
 */

@Rest(rootUrl = "http://www.insporte.com.br:88/rest", converters = {CustomHttpMessageConverter.class})
public interface InsporteServiceMapper extends RestClientErrorHandling {

    @Post("/auth/add")
    LoginResponse registerGoogle(Token token);

    @Post("/auth/simple")
    LoginResponse registerAnonymous(Token token);

    @Post("/answer")
    SurveyResponse survey(Survey survey);

    @Post("/likes/add")
    DefaultResponse addLike(Like like);

    @Post("/image/add")
    DefaultResponse sendImage(Image image);
}
