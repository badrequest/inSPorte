package br.com.badrequest.insporte.integration.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by gmarques on 3/18/14.
 */

@AllArgsConstructor
public @Getter @Setter class Survey implements Serializable {

    private Credentials auth;

    private String request = "Resposta";

    private ExtraSurveyInfo info;

    private AdministeredQuestionnaire resposta;

    private Comment comentario;

    public Survey() {
        comentario = new Comment();
    }

    public void setCredentials(Credentials credentials) {
        this.auth = credentials;
    }

}
