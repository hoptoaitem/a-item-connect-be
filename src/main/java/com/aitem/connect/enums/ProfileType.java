package com.aitem.connect.enums;

import lombok.Getter;

@Getter
public enum ProfileType {
    DRIVER("DRIVER"),
    RETAILER("RETAILER"),
    CUSTOMER("CUSTOMER"),
    NOT_DECIDED("NOT_DECIDED");

    private String code;

    private ProfileType(String code) {
        this.code = code;
    }
}
