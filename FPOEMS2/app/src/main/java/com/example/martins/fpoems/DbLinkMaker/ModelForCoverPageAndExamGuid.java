package com.example.martins.fpoems.DbLinkMaker;

/**
 * Created by Martins on 2/19/2020.
 */

public class ModelForCoverPageAndExamGuid {


    Integer id;
    String link;
            String linkname;

    public ModelForCoverPageAndExamGuid(Integer id, String link, String linkname) {
        this.id = id;
        this.link = link;
        this.linkname = linkname;
    }

    public String getLinkname() {
        return linkname;
    }

    public void setLinkname(String linkname) {
        this.linkname = linkname;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }


}
