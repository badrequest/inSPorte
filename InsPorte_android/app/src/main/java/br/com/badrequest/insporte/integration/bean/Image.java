package br.com.badrequest.insporte.integration.bean;

import br.com.badrequest.insporte.util.BitmapUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by gmarques on 4/27/14.
 */
@NoArgsConstructor
public @Getter @Setter class Image implements Serializable {

    private Credentials auth;
    private ImageData imagem;

    public Image(Credentials credentials, int idImagem, String imgPath) {
        this.auth = credentials;
        this.imagem = new ImageData(idImagem, BitmapUtils.openAsBase64(imgPath));
    }

    @AllArgsConstructor(suppressConstructorProperties = true)
    private class ImageData {
        int idImagem;
        String img;
    }
}
