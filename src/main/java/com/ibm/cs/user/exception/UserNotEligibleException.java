package com.ibm.cs.user.exception;

public class UserNotEligibleException extends RuntimeException{
    public UserNotEligibleException(String message){
        super(message);
    }
}
