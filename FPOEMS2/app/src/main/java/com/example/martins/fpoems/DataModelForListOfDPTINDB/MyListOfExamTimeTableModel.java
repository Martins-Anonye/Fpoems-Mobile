package com.example.martins.fpoems.DataModelForListOfDPTINDB;

import android.widget.TextView;

/**
 * Created by Martins on 2/1/2020.
 */

public class MyListOfExamTimeTableModel {
    Integer dptexamrowid;
    String dptexamsn, dptexamcoscod, dptexamcostitle, dptexamcosLecturer,dptexamdpt,dptexamclass, dptexamdate;
    String  dptexamstarttime, examendtime, dptexamvenue, dptexamclasssize, exammode;


    public MyListOfExamTimeTableModel(Integer dptexamrowid, String dptexamsn, String dptexamcoscod, String dptexamcostitle, String dptexamcosLecturer, String dptexamdpt, String dptexamclass, String dptexamdate, String dptexamstarttime, String examendtime, String dptexamvenue, String dptexamclasssize, String exammode) {
        this.dptexamrowid = dptexamrowid;
        this.dptexamsn = dptexamsn;
        this.dptexamcoscod = dptexamcoscod;
        this.dptexamcostitle = dptexamcostitle;
        this.dptexamcosLecturer = dptexamcosLecturer;
        this.dptexamdpt = dptexamdpt;
        this.dptexamclass = dptexamclass;
        this.dptexamdate = dptexamdate;
        this.dptexamstarttime = dptexamstarttime;
        this.examendtime = examendtime;
        this.dptexamvenue = dptexamvenue;
        this.dptexamclasssize = dptexamclasssize;
        this.exammode = exammode;
    }

    public String getDptexamcostitle() {
        return dptexamcostitle;
    }

    public void setDptexamcostitle(String dptexamcostitle) {
        this.dptexamcostitle = dptexamcostitle;
    }

    public String getDptexamcosLecturer() {
        return dptexamcosLecturer;
    }

    public void setDptexamcosLecturer(String dptexamcosLecturer) {
        this.dptexamcosLecturer = dptexamcosLecturer;
    }

    public String getDptexamstarttime() {
        return dptexamstarttime;
    }

    public void setDptexamstarttime(String dptexamstarttime) {
        this.dptexamstarttime = dptexamstarttime;
    }

    public String getExamendtime() {
        return examendtime;
    }

    public void setExamendtime(String examendtime) {
        this.examendtime = examendtime;
    }

    public String getDptexamdpt() {
        return dptexamdpt;
    }

    public void setDptexamdpt(String dptexamdpt) {
        this.dptexamdpt = dptexamdpt;
    }

    public String getDptexamclass() {
        return dptexamclass;
    }

    public void setDptexamclass(String dptexamclass) {
        this.dptexamclass = dptexamclass;
    }




    public String getDptexamsn() {
        return dptexamsn;
    }

    public void setDptexamsn(String dptexamsn) {
        this.dptexamsn = dptexamsn;
    }

    public String getExammode() {
        return exammode;
    }

    public void setExammode(String exammode) {
        this.exammode = exammode;
    }

    public Integer getDptexamrowid() {
        return dptexamrowid;
    }

    public void setDptexamrowid(Integer dptexamrowid) {
        this.dptexamrowid = dptexamrowid;
    }

    public String getDptexamcoscod() {
        return dptexamcoscod;
    }

    public void setDptexamcoscod(String dptexamcoscod) {
        this.dptexamcoscod = dptexamcoscod;
    }




    public String getDptexamdate() {
        return dptexamdate;
    }

    public void setDptexamdate(String dptexamdate) {
        this.dptexamdate = dptexamdate;
    }




    public String getDptexamvenue() {
        return dptexamvenue;
    }

    public void setDptexamvenue(String dptexamvenue) {
        this.dptexamvenue = dptexamvenue;
    }

    public String getDptexamclasssize() {
        return dptexamclasssize;
    }

    public void setDptexamclasssize(String dptexamclasssize) {
        this.dptexamclasssize = dptexamclasssize;
    }
}
