package br.com.badrequest.insporte.integration.bean.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by gmarques on 4/25/14.
 */
public @Getter @Setter class DefaultResponse implements Serializable {

    private String ans;

    public boolean success() {
        return ans != null && !"fail".equalsIgnoreCase(ans);
    }

}
