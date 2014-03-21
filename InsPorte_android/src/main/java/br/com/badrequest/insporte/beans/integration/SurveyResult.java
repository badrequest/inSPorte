package br.com.badrequest.insporte.beans.integration;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gmarques on 3/18/14.
 */
public class SurveyResult implements Serializable {

    Integer idQuestionario;

    List<Question> perguntas;

    public SurveyResult() {
    }

    public SurveyResult(Integer idQuestionario) {
        this.idQuestionario = idQuestionario;
    }

    public SurveyResult(Integer idQuestionario, List<Question> perguntas) {
        this.idQuestionario = idQuestionario;
        this.perguntas = perguntas;
    }

    public Integer getIdQuestionario() {
        return idQuestionario;
    }

    public void setIdQuestionario(Integer idQuestionario) {
        this.idQuestionario = idQuestionario;
    }

    public List<Question> getPerguntas() {
        return perguntas;
    }

    public void setPerguntas(List<Question> perguntas) {
        this.perguntas = perguntas;
    }
}
