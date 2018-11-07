package com.blockchain.commune.custommodel;

import com.blockchain.commune.model.Comment;

public class CustomComment extends Comment {
    private int reportNum;

    public int getReportNum() {
        return reportNum;
    }

    public void setReportNum(int reportNum) {
        this.reportNum = reportNum;
    }
}
