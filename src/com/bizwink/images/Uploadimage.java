package com.bizwink.images;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 18-6-3.
 */
public class Uploadimage implements Serializable{
    private int imageno;
    private String brief;
    private String filepath;
    private List<Map> smallimages;

    public int getImageno() {
        return imageno;
    }

    public void setImageno(int imageno) {
        this.imageno = imageno;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public List<Map> getSmallimages() {
        return smallimages;
    }

    public void setSmallimages(List<Map> smallimages) {
        this.smallimages = smallimages;
    }
}
