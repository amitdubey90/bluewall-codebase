package com.bluewall.util.common;

public enum Gender {
    MALE("MALE", 5),
    FEMALE("FEMALE", -161);

    private int BRMConstant;
    private String name;

    Gender(String name, int BRMConstant) {
        this.name = name;
        this.BRMConstant = BRMConstant;
    }

    public String getName() {
        return name;
    }

    public int getBRMConstant() {
        return BRMConstant;
    }
}
