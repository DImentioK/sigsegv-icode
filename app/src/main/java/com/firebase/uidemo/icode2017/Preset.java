package com.firebase.uidemo.icode2017;

/**
 * Created by Xavier on 27/8/17.
 */

public class Preset {
    private Category category;
    private Integer distance;

    public Preset(Category category, Integer distance) {
        this.category = category;
        this.distance = distance;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
