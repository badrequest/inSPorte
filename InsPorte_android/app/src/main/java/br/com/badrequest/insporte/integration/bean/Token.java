package br.com.badrequest.insporte.integration.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by gmarques on 3/20/14.
 */
@NoArgsConstructor
@AllArgsConstructor(suppressConstructorProperties = true)
public @Getter @Setter class Token implements Serializable {

    private String uuid;
    private String token;

    public Token(String uuid) {
        this.uuid = uuid;
    }
}
