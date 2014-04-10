package br.com.badrequest.insporte.integration.bean;

import lombok.*;

import java.io.Serializable;

/**
 * Created by gmarques on 3/18/14.
 */

@NoArgsConstructor
public @Getter @Setter class Comment implements Serializable {

    private String texto;

    private boolean imagem;

    public Comment(String texto, boolean imagem) {
        this.texto = texto;
        this.imagem = imagem;
    }
}
