package br.com.badrequest.insporte.integration.bean;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * Created by gmarques on 3/20/14.
 */
public @Getter @Setter class SurveySubmitResponse implements Serializable {

    private String ans;

    public boolean success() {
        return StringUtils.isNotEmpty(this.ans);
    }
}
