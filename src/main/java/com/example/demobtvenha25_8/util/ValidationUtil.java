package com.example.demobtvenha25_8.util;

import java.math.BigDecimal;
import java.util.regex.Pattern;

public class ValidationUtil {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    // Dinh dang so dien thoai Viet Nam: 10 chu so, bat dau bang 03, 05, 07, 08, 09
    private static final Pattern PHONE_PATTERN =
            Pattern.compile("^(03|05|07|08|09)\\d{8}$");

    // Username: 4-30 ky tu, chi gom chu cai, chu so va dau gach duoi
    private static final Pattern USERNAME_PATTERN =
            Pattern.compile("^[a-zA-Z0-9_]{4,30}$");

    private ValidationUtil() {}

    public static boolean isValidEmail(String email) {
        if (email == null || email.isBlank()) return false;
        return EMAIL_PATTERN.matcher(email.trim()).matches();
    }

    public static boolean isValidPhone(String phone) {
        if (phone == null || phone.isBlank()) return false;
        return PHONE_PATTERN.matcher(phone.trim()).matches();
    }

    public static boolean isValidUsername(String username) {
        if (username == null || username.isBlank()) return false;
        return USERNAME_PATTERN.matcher(username.trim()).matches();
    }

    public static boolean isValidPassword(String password) {
        return password != null && password.length() >= 6;
    }

    public static boolean isPositiveNumber(String str) {
        if (str == null || str.isBlank()) return false;
        try {
            BigDecimal val = new BigDecimal(str.trim());
            return val.compareTo(BigDecimal.ZERO) > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isNonNegativeInteger(String str) {
        if (str == null || str.isBlank()) return false;
        try {
            int val = Integer.parseInt(str.trim());
            return val >= 0;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isPositiveInteger(String str) {
        if (str == null || str.isBlank()) return false;
        try {
            int val = Integer.parseInt(str.trim());
            return val > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isValidImageExtension(String fileName) {
        if (fileName == null || fileName.isBlank()) return false;
        String lower = fileName.toLowerCase();
        return lower.endsWith(".jpg") || lower.endsWith(".jpeg")
                || lower.endsWith(".png") || lower.endsWith(".webp");
    }

    public static String safeTrim(String str) {
        return str == null ? "" : str.trim();
    }
}
