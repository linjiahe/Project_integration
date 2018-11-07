package com.blockchain.commune.custommodel;

import com.blockchain.commune.model.CommentReplay;

public class CustomCommentReplay extends CommentReplay {
    private int reportNum;

    public int getReportNum() {
        return reportNum;
    }

    public void setReportNum(int reportNum) {
        this.reportNum = reportNum;
    }
}
