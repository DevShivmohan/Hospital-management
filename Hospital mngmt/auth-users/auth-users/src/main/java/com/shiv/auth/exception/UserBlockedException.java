package com.shiv.auth.exception;

public class UserBlockedException extends Exception{
    public UserBlockedException(String error){
        super(error);
    }
}
