package br.com.badrequest.insporte.integration.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by gmarques on 3/20/14.
 */

public @Getter @Setter class Token implements Serializable {

    String token;

    public Token() {
    }

    public Token(String token) {
        this.token = token;
    }
}
