package com.tang.exception;

public class DBException extends RuntimeException {

    private static final long serialVersionUID = 387954675604114633L;

    public DBException(Exception e) {
        super(e);
    }

    public DBException() {
    }
}
