package pk.habsoft.demo.estore.enums;

import lombok.Getter;

@Getter
public enum RoleType {

    /**
     * Role ids will determine precedence of roles
     */
    SUPER_ADMIN(1L, "ROLE_SYPER_ADMIN", "Super Admin", true), // Super admin
    ADMIN(2L, "ROLE_ADMIN", "Admin", false), // Admin
    MANAGER(4L, "ROLE_MANAGER", "Manager", true), // Manager
    EMPLOYEE(8L, "ROLE_EMPLOYEE", "Employee", false); // Employee

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

}
