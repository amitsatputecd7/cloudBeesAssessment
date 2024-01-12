package com.model;


public class User {
    private String firstName;
    private String lastName;
    private String email;

    public User(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }
    

    public String getEmail() {
        return email;
    }
}
