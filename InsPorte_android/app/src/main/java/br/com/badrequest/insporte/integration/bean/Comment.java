package br.com.badrequest.insporte.integration.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by gmarques on 3/18/14.
 */

@NoArgsConstructor
@AllArgsConstructor(suppressConstructorProperties = true)
public @Getter @Setter class Comment implements Serializable {

    private String texto;
    private boolean imagem = false;
    private String imgPath;

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
        imagem = imgPath != null;
    }

    public boolean hasImage() {
        return imagem;
    }

}
