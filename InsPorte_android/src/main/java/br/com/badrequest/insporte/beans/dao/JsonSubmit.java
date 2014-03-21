package br.com.badrequest.insporte.beans.dao;

import org.orman.mapper.Model;
import org.orman.mapper.annotation.Entity;
import org.orman.mapper.annotation.PrimaryKey;

import java.io.Serializable;

@Entity
public class JsonSubmit extends Model<JsonSubmit> implements Serializable {

    @PrimaryKey(autoIncrement = true)
    private int id;

    private String json;

    public int getId() {
        return id;
    }

    public JsonSubmit setId(int id) {
        this.id = id;
        return this;
    }

    public String getJson() {
        return json;
    }

    public JsonSubmit setJson(String json) {
        this.json = json;
        return this;
    }
}
