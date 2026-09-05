package com.example.demobtvenha25_8.util;

import java.security.SecureRandom;

public class OtpUtil {

    private static final SecureRandom random = new SecureRandom();

    /**
     * Sinh mã OTP 6 chữ số ngẫu nhiên
     */
    public static String generate() {
        int code = 100000 + random.nextInt(900000); // 100000 – 999999
        return String.valueOf(code);
    }
}
