package com.fz.epms.controller.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordForm extends BaseForm {

    private String oldPassword;
    private String newPassword;

    @Override
    public void validate() {
        validateNotBlank("old password", oldPassword);
        validateNotBlank("new password", newPassword);
    }

}
