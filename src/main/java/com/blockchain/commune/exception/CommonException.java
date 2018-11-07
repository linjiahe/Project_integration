package com.blockchain.commune.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

public class CommonException extends Exception {
    private int code;
    private String message;
    private String stackTrace;


    public CommonException(int code, String message) {

        this.code = code;
        this.message = message;

    }

    public CommonException(int code, String message, Exception e) {

        this.code = code;
        this.message = message;

        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw, true));
        String stackString = sw.toString();

        this.stackTrace = stackString;


    }


    @Override
    public String getMessage() {
        return this.message + (this.stackTrace == null ? "" : " stackTrace [" + this.stackTrace + "]");
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {

        return this.code;
    }
}
