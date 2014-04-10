package br.com.badrequest.insporte.integration.bean;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by gmarques on 3/18/14.
 */

@NoArgsConstructor
public @Getter @Setter class Credentials implements Serializable {

    private String email;

    private String uuid;

    private String senha;

    public Credentials(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    public Credentials(String email, String uuid, String senha) {
        this.email = email;
        this.uuid = uuid;
        this.senha = senha;
    }
}
