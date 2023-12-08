package com.pathshala.util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptionUtility {
    public static String makeSHA1Hash(String input)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA1");
        md.reset();
        byte[] buffer = input.getBytes(StandardCharsets.UTF_8);
        md.update(buffer);
        byte[] digest = md.digest();

        StringBuilder hexStr = new StringBuilder();
        for (byte b : digest) {
            hexStr.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        }
        return hexStr.toString();
    }
}
