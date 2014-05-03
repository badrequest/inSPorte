package br.com.badrequest.insporte.integration.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by gmarques on 4/25/14.
 */
@NoArgsConstructor
public @Getter @Setter class Like implements Serializable {

    private Credentials auth;

    private LikeData like;

    public Like(Credentials auth, String id, boolean status) {
        this.auth = auth;
        this.like = new LikeData(id, status);
    }

    @AllArgsConstructor(suppressConstructorProperties = true)
    private class LikeData implements Serializable {
        String id;
        boolean status;
    }
}
