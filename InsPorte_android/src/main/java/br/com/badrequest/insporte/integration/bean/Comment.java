package br.com.badrequest.insporte.integration.bean;

import lombok.*;

import java.io.Serializable;

/**
 * Created by gmarques on 3/18/14.
 */

@NoArgsConstructor
@AllArgsConstructor
public @Getter @Setter class Comment implements Serializable {

    private String texto;

    private boolean imagem;

}
