package com.pathshala.util;

import org.apache.commons.lang3.RandomStringUtils;

public class PasswordGenerateUtil {
    public static String generateRandomPassword(int length) {
        // You can customize the characters used in the generated password
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        // Generate a random password using the specified length and characters
        return RandomStringUtils.random(length, characters);
    }
}
