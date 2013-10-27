package br.com.badrequest.insporte.beans;

import java.io.Serializable;

public class User implements Serializable {

    private String id;
    private String busCard;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBusCard() {
        return busCard;
    }

    public void setBusCard(String busCard) {
        this.busCard = busCard;
    }
}
