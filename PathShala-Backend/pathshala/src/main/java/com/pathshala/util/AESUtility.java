package com.pathshala.util;

import com.pathshala.exception.BaseRuntimeException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

public class AESUtility {
    private static SecretKey setKey(String pKey) throws BaseRuntimeException {
        MessageDigest messageDigest;
        try {
            byte[] key = pKey.getBytes(StandardCharsets.UTF_8);
            messageDigest = MessageDigest.getInstance("SHA-1");
            key = messageDigest.digest(key);
            key = Arrays.copyOf(key, 16);
            return new SecretKeySpec(key, "AES");
        } catch (NoSuchAlgorithmException e) {
            throw new BaseRuntimeException("", "Error while creating secret key");
        }
    }

    public static String doEncrypt(String stringToEncrypt, String pSecretKey) throws BaseRuntimeException {
        try {
            SecretKey secretKey = setKey(pSecretKey);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            String encryptedString = Base64.getEncoder().encodeToString(cipher.doFinal(stringToEncrypt.getBytes(StandardCharsets.UTF_8)));
            String decryptedString = doDecrypt(encryptedString, pSecretKey);
            if (decryptedString.equalsIgnoreCase(stringToEncrypt)) {
                return encryptedString;
            } else {
                throw new BaseRuntimeException("", "");
            }
        } catch (Exception e) {
            throw new BaseRuntimeException("", "");
        }
    }

    public static String doDecrypt(String encryptedString, String pSecretKey) throws BaseRuntimeException {
        try {
            SecretKey secretKey = setKey(pSecretKey);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(encryptedString)));
        } catch (Exception e) {
            throw new BaseRuntimeException("", "");
        }
    }

}