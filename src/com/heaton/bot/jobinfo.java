package com.heaton.bot;

/**
 * Created by IntelliJ IDEA.
 * User: EricDu
 * Date: 2007-11-26
 * Time: 10:25:18
 */
public class jobinfo {
    private int id;
    private String joburl;
    private String title;

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

   public String getJoburl() {
        return joburl;
    }

    public void setJoburl(String url) {
        this.joburl = url;
    }

    public String getTitle() {
         return title;
     }

     public void setTitle(String title) {
         this.title = title;
    }
}
