package br.com.badrequest.insporte.beans.integration;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by gmarques on 3/18/14.
 */
public @Getter @Setter class Comment implements Serializable {

    String texto;

    boolean imagem;

    public Comment() {
    }

    public Comment(String texto, boolean imagem) {
        this.texto = texto;
        this.imagem = imagem;
    }

}
