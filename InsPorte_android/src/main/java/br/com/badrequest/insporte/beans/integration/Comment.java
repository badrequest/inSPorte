package br.com.badrequest.insporte.beans.integration;

import java.io.Serializable;

/**
 * Created by gmarques on 3/18/14.
 */
public class Comment implements Serializable {

    String texto;

    boolean imagem;

    public Comment() {
    }

    public Comment(String texto, boolean imagem) {
        this.texto = texto;
        this.imagem = imagem;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public boolean isImagem() {
        return imagem;
    }

    public void setImagem(boolean imagem) {
        this.imagem = imagem;
    }
}
