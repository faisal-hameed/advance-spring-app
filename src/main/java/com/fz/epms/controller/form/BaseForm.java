package com.fz.epms.controller.form;

import pk.habsoft.demo.estore.exceptions.ValidationFailedException;

public abstract class BaseForm {

    public abstract void validate();

    public void validateId(String field, long id) {
        if (id <= 0) {
            throw new ValidationFailedException(String.format("Value of %s should be positive integer", field));
        }
    }

    public void validateEmail(String field, String email) {
        // TODO
        validateNotBlank(field, email);
    }

    public void validateNotBlank(String field, String value) {
        if (value == null || value.isEmpty()) {
            throw new ValidationFailedException(String.format("%s can't be null/empty.", field));
        }
    }

    public void validateNotNull(String field, Object value) {
        if (value == null) {
            throw new ValidationFailedException(String.format("%s can't be null/empty.", field));
        }
    }

    public void validateLength(String field, String value, int minLength) {
        validateNotBlank(field, value);
        if (value.length() < minLength) {
            throw new ValidationFailedException(String.format("Minimum length of %s is %d.", field, minLength));
        }
    }

    public void validateLength(String field, String value, int minLength, int maxLength) {
        validateLength(field, value, minLength);
        if (value.length() > maxLength) {
            throw new ValidationFailedException(String.format("Maximum length of %s is %d.", field, minLength));

        }
    }
}
