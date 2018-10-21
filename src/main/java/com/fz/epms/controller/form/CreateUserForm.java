package com.fz.epms.controller.form;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pk.habsoft.demo.estore.enums.RoleType;

@Getter
@Setter
@NoArgsConstructor
public class CreateUserForm extends BaseForm {
    private String name;
    private String email;
    private String password;
    private RoleType roleType;

    @Override
    public void validate() {
        validateNotBlank("name ", name);
        validateNotBlank("email", email);
        validateNotBlank("password", password);
        validateNotNull("role", roleType);
    }
}
