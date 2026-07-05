package com.projectmanager.utils;

public class Validator {

    private Validator() {
    }

    public static void requireNonBlank(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " Khong dược de trong");
        }
    }

    public static void requireMinLength(String value, String fieldName, int min) {
        if (value != null && value.length() < min) {
            throw new IllegalArgumentException(fieldName + " phai co it nhat " + min + "ky tu");
        }
    }

    public static void requirePositive(double value, String field){
        if(value <= 0){
            throw new IllegalArgumentException(field + " phai la so duong");
        }
    }

    public static void requireRange(int value, String field, int min, int max) {
        if (value < min || value > max)
            throw new IllegalArgumentException(field + " phai tu " + min + " den " + max + ".");
    }

    public static void requireOneOf(String value, String field, String... options) {
        for (String opt : options) {
            if (opt.equalsIgnoreCase(value)) {
                return;
            }
        }
        throw new IllegalArgumentException(field + " phai la mot trong: " + String.join(", ", options));
    }

    // parse và validate cùng lúc:
    public static int parsePositiveInt(String raw, String field) {
        try {
            int v = Integer.parseInt(raw.trim());
            if (v <= 0) {
                throw new IllegalArgumentException(field + " phai lon hon 0.");
            }
            return v;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(field + " phai la so nguyen hop le.");
        }
    }
}
