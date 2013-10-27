package br.com.badrequest.insporte.beans;

import android.graphics.Bitmap;

import java.io.Serializable;

public class FeedLine implements Serializable {

    private Bitmap image;

    private String line;

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }
}
