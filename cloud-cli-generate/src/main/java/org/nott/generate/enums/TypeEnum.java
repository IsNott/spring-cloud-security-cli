package org.nott.generate.enums;


public enum TypeEnum {

    STANDALONE("0"),
    MICROSERVICE("1");

    private String val;

    TypeEnum(String val) {
        this.val = val;
    }

    public String getVal() {
        return val;
    }
}
