package br.com.badrequest.insporte.beans.integration;

import java.io.Serializable;

/**
 * Created by gmarques on 3/20/14.
 */
public class Response implements Serializable {

    String ans;

    public Response() {
    }

    public Response(String ans) {
        this.ans = ans;
    }

    public String getAns() {
        return ans;
    }

    public void setAns(String ans) {
        this.ans = ans;
    }
}
