package com.pockemon_review.pockemonAPI.exception;

public class ReviewNotFoundException extends RuntimeException {
    private static final long serialVerisionUID = 1;

    public ReviewNotFoundException(String message){
        super(message);
    }
}
