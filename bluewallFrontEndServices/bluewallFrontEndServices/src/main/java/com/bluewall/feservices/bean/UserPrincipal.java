package com.bluewall.feservices.bean;

import lombok.Data;

@Data
public class UserPrincipal {

    private static final long serialVersionUID = 1L;

    private String firstName;
    private String lastName;
    private String emailID;
    private int userID;
    private int age;
    private double height;
    private double weight;

    public UserPrincipal(String emailID, String firstName,
                         String lastName, int userID, int age, double weight, double height) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.emailID = emailID;
        this.userID = userID;
        this.age = age;
        this.height = height;
        this.weight = weight;
    }
}
