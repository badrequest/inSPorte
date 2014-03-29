package br.com.badrequest.insporte.integration.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gmarques on 3/18/14.
 */

@NoArgsConstructor
@AllArgsConstructor
public @Getter @Setter class AdministeredQuestionnaire implements Serializable {

    private Integer idQuestionario;

    private List<Question> perguntas;

    public AdministeredQuestionnaire(Integer idQuestionario) {
        this.idQuestionario = idQuestionario;
    }
}
