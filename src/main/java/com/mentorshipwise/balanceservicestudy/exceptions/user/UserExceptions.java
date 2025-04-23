package com.mentorshipwise.balanceservicestudy.exceptions.user;

public class UserExceptions {

    public static class UserNotFoundException extends RuntimeException {
        public UserNotFoundException(String entityName) {
            super(entityName + " not found");
        }
    }

    public static class InvalidCredentials extends RuntimeException {
        public InvalidCredentials() {
            super("Email or password doesn't exist");
        }
    }

}