package br.com.badrequest.insporte.integration.bean;

import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gmarques on 3/18/14.
 */

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "idPergunta")
public @Getter @Setter class Question implements Serializable {

    private Integer idPergunta;

    private List<QuestionOption> opcoes;

    private boolean imagem = false;

    public Question(Integer idPergunta) {
        this.idPergunta = idPergunta;
    }

    public Question(Integer idPergunta, List<QuestionOption> opcoes) {
        this.idPergunta = idPergunta;
        this.opcoes = opcoes;
    }

}
