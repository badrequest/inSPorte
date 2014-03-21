package br.com.badrequest.insporte.beans.integration;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gmarques on 3/18/14.
 */
public class Question implements Serializable {

    Integer idPergunta;

    List<Option> opcoes;

    boolean imagem = false;

    public Question(Integer idPergunta) {
        this.idPergunta = idPergunta;
    }

    public Question(Integer idPergunta, List<Option> opcoes) {
        this.idPergunta = idPergunta;
        this.opcoes = opcoes;
    }

    public Question(Integer idPergunta, List<Option> opcoes, boolean imagem) {
        this.idPergunta = idPergunta;
        this.opcoes = opcoes;
        this.imagem = imagem;
    }

    public Integer getIdPergunta() {
        return idPergunta;
    }

    public void setIdPergunta(Integer idPergunta) {
        this.idPergunta = idPergunta;
    }

    public List<Option> getOpcoes() {
        return opcoes;
    }

    public void setOpcoes(List<Option> opcoes) {
        this.opcoes = opcoes;
    }

    public boolean isImagem() {
        return imagem;
    }

    public void setImagem(boolean imagem) {
        this.imagem = imagem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Question question = (Question) o;

        if (!idPergunta.equals(question.idPergunta)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return idPergunta.hashCode();
    }
}
