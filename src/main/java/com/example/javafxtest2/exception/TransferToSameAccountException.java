package com.example.javafxtest2.exception;

public class TransferToSameAccountException extends RuntimeException {
    public TransferToSameAccountException(String message) {
        super(message);
    }
}