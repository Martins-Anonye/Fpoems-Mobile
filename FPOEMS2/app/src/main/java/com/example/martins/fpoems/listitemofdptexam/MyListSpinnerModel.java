package com.example.martins.fpoems.listitemofdptexam;

/**
 * Created by Martins on 2/2/2020.
 */

public class MyListSpinnerModel {
    String examclass,examdpt;

    public MyListSpinnerModel(String examclass, String examdpt) {
        this.examclass = examclass;
        this.examdpt = examdpt;
    }

    public String getExamclass() {
        return examclass;
    }

    public void setExamclass(String examclass) {
        this.examclass = examclass;
    }

    public String getExamdpt() {
        return examdpt;
    }

    public void setExamdpt(String examdpt) {
        this.examdpt = examdpt;
    }
}
