package br.com.badrequest.insporte.beans.integration;

import java.io.Serializable;

/**
 * Created by gmarques on 3/18/14.
 */
public class RestBean implements Serializable {

    Auth auth;

    String request;

    Info info;

    SurveyResult resposta;

    Comment comentario;

    public RestBean() {
        this.request = "Resposta";
        comentario = new Comment();
    }

    public RestBean(Auth auth, String request, Info info, SurveyResult resposta, Comment comentario) {
        this.auth = auth;
        this.request = request;
        this.info = info;
        this.resposta = resposta;
        this.comentario = comentario;
    }

    public Auth getAuth() {
        return auth;
    }

    public void setAuth(Auth auth) {
        this.auth = auth;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public SurveyResult getResposta() {
        return resposta;
    }

    public void setResposta(SurveyResult resposta) {
        this.resposta = resposta;
    }

    public Comment getComentario() {
        return comentario;
    }

    public void setComentario(Comment comentario) {
        this.comentario = comentario;
    }
}
