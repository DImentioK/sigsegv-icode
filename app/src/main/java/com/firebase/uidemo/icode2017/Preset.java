package com.firebase.uidemo.icode2017;

/**
 * Created by Xavier on 29/8/17.
 */

public class Preset {
    private Category category;
    private Integer distance;

    public Preset(boolean tourist, boolean heritage, boolean nature, String dis) {
        dis = dis.substring(0, dis.length() - 2);
        if (dis.equals("A"))
            dis = "50";
        this.distance = Integer.parseInt(dis);
        if (nature) {
            if (tourist && !heritage)
                category = Category.TOURIST_NATURE;
            else if (heritage && !tourist)
                category = Category.NATURE_HERITAGE;
            else if (!heritage && !tourist)
                category = Category.NATURE;
            else
                category = Category.ALL;
        } else if (tourist) {
            if (heritage)
                category = Category.TOURIST_HERITAGE;
            else
                category = Category.TOURIST;
        } else {
            category = Category.HERITAGE;
        }
    }

    public Preset(Category category, String distance) {
        this.category = category;
        this.distance = Integer.parseInt(distance);
    }

    public String getDistance() {
        return distance.toString();
    }

    public Category getCategory() {
        return category;
    }
}
