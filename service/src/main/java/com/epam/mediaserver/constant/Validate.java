package com.epam.mediaserver.constant;

/**
 * This class keeping constant with reg exp
 */

public final class Validate {
    private Validate(){}

    public static final String FIELD_LOGIN = "^[a-zA-Z0-9]+$";
    public static final String FIELD_PASSWORD = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s).*$";
    public static final String FIELD_NAME = "^[\\D]{3,20}$";
    public static final String FIELD_EMAIL = "^[-\\w.]+@([A-z0-9][-A-z0-9]+\\.)+[A-z]{2,4}$";
    public static final String FIELD_YEAR = "[0-9]{4}";
}
