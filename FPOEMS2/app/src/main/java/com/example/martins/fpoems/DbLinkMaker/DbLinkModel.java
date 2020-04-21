package com.example.martins.fpoems.DbLinkMaker;

/**
 * Created by Martins on 2/18/2020.
 */

public class DbLinkModel {
    Integer id;
    String dblinks;
    String dbname;
    String tablename;

    public DbLinkModel(Integer id, String dblinks, String dbname, String tablename) {
        this.id = id;
        this.dblinks = dblinks;
        this.dbname = dbname;
        this.tablename = tablename;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDblinks() {
        return dblinks;
    }

    public void setDblinks(String dblinks) {
        this.dblinks = dblinks;
    }

    public String getDbname() {
        return dbname;
    }

    public void setDbname(String dbname) {
        this.dbname = dbname;
    }

    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

}
