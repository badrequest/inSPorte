package br.com.badrequest.insporte.integration.bean.response;

import br.com.badrequest.insporte.bean.database.Photo;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gmarques on 3/20/14.
 */
public @Getter @Setter class SurveyResponse implements Serializable {

    private String ans;
    private List<Photo> idImagens;
    private Integer idImagemComentario;

    public boolean success() {
        return "ok".equals(ans);
    }

}
