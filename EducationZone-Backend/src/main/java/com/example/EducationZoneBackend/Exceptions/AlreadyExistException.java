package com.example.EducationZoneBackend.Exceptions;

public class AlreadyExistException extends Exception {
    public AlreadyExistException(String errorMessage) {
        super(errorMessage);
    }
}

