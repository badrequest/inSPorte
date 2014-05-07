package br.com.badrequest.insporte.integration.bean;

import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gmarques on 3/18/14.
 */

@NoArgsConstructor
@AllArgsConstructor(suppressConstructorProperties = true)
@EqualsAndHashCode(of = "idPergunta")
public @Getter @Setter class Question implements Serializable {

    private int idPergunta;
    private List<QuestionOption> opcoes;
    private boolean imagem = false;
    private String imgPath;


    public Question(Integer idPergunta) {
        this.idPergunta = idPergunta;
    }

    public Question(Integer idPergunta, List<QuestionOption> opcoes) {
        this.idPergunta = idPergunta;
        this.opcoes = opcoes;
    }

    public List<QuestionOption> getOpcoes() {
        if(this.opcoes == null) {
            this.opcoes = new ArrayList<QuestionOption>();
        }

        return this.opcoes;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
        imagem = imgPath != null;
    }

    public boolean hasImage() {
        return imagem;
    }
}
