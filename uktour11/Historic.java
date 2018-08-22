package com.example.omar.uktour11;

import android.media.Image;
import android.widget.ImageView;
import android.widget.RatingBar;

/**
 * Created by omar on 1/6/2018.
 */

public class Historic {
    private String image;
    private String nameOfHistoric;
    private int rate;
    private String id;

    public Historic() {
    }

    public Historic(String image, String nameOfHistoric, int rate, String id) {
        this.image = image;
        this.nameOfHistoric = nameOfHistoric;
        this.rate = rate;
        this.id = id;

    }

    public String getImage() {
        return image;
    }

    public String getNameOfHistoric() {
        return nameOfHistoric;
    }

    public int getRate() {
        return rate;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setNameOfHistoric(String nameOfHistoric) {
        this.nameOfHistoric = nameOfHistoric;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
