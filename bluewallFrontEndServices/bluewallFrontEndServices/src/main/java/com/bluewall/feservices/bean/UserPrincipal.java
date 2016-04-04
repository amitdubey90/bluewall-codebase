package com.bluewall.feservices.bean;

import lombok.Data;

@Data
public class UserPrincipal {

    private static final long serialVersionUID = 1L;

    private String firstName;
    private String lastName;
    private String emailID;
    private int userID;

    public UserPrincipal(String emailID, String firstName,
                         String lastName, int userID) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.emailID = emailID;
        this.userID = userID;
    }
}
