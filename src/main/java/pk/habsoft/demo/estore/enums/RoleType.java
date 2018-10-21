package pk.habsoft.demo.estore.enums;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.Getter;

@Getter
public enum RoleType {

    /**
     * Role ids will determine precedence of roles
     */
    SUPER_ADMIN(1L, "SUPER_ADMIN", "Super Admin", true), // Super admin
    ADMIN(2L, "ADMIN", "Admin", false), // Admin
    MANAGER(4L, "MANAGER", "Manager", true), // Manager
    EMPLOYEE(8L, "EMPLOYEE", "Employee", false); // Employee

    private long id;
    private String code;
    private String label;
    private boolean isInternal;

    private RoleType(long id, String code, String label, boolean isInternal) {
        this.id = id;
        this.code = code;
        this.label = label;
        this.isInternal = isInternal;
    }

    @JsonCreator
    public static RoleType fromText(String text) {
        if (text == null) {
            throw new IllegalArgumentException();
        }
        for (RoleType v : values()) {
            System.err.println(v.getCode());
            if (text.equalsIgnoreCase(v.getCode())) {
                return v;
            }
        }
        throw new IllegalArgumentException("Custom binding failed for : " + text
                + ". Input type: String. Expected type of value to be set: " + Arrays.toString(values()));
    }

}
