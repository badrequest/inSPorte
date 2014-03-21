package br.com.badrequest.insporte.beans.integration;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by gmarques on 3/18/14.
 */
public class Info implements Serializable {

    String id;

    Double lat;

    Double lon;

    Date data;

    public Info() {
    }

    public Info(String id, Double lat, Double lon, Date data) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
}
