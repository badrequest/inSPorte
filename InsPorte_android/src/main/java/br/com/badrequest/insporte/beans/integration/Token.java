package br.com.badrequest.insporte.beans.integration;

import java.io.Serializable;

/**
 * Created by gmarques on 3/20/14.
 */
public class Token implements Serializable {

    String token;

    public Token() {
    }

    public Token(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
