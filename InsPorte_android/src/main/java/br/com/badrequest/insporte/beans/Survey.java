package br.com.badrequest.insporte.beans;

import org.orman.mapper.Model;
import org.orman.mapper.annotation.Entity;
import org.orman.mapper.annotation.Index;
import org.orman.mapper.annotation.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity
public class Survey extends Model<Survey> implements Serializable {

    @PrimaryKey(autoIncrement = true)
    private int id;

    private Date data;

    private String comment;

    private String pathPictureComment;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPathPictureComment() {
        return pathPictureComment;
    }

    public void setPathPictureComment(String pathPictureComment) {
        this.pathPictureComment = pathPictureComment;
    }
}
