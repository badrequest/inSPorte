package br.com.badrequest.insporte.beans.integration;

import java.io.Serializable;

/**
 * Created by gmarques on 3/18/14.
 */
public class Auth implements Serializable {

    String email;

    String senha;

    public Auth() {
    }

    public Auth(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
