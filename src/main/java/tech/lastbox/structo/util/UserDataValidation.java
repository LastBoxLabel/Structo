package tech.lastbox.structo.util;

import java.util.regex.Pattern;

public class UserDataValidation {
    public static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        String emailRegex = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return Pattern.matches(emailRegex, email);
    }

    public static boolean isValidPassword(String rawPassword) {
        return rawPassword != null && rawPassword.length() >= 8;
    }

    public static boolean isValidUsername(String username) {
        return !(username == null || username.isEmpty());
    }

    public static boolean isValidName(String name) {
        return !(name == null || name.isEmpty());
    }
}
