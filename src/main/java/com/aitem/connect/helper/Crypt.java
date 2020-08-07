package com.aitem.connect.helper;


import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Random;

@Component
public class Crypt {

    private final String applicationSecret = "9mX4w&=Z#q7Y";

    public CryptData encrypt(String strToEncrypt) {
        try {
            byte[] salt = getSalt();
            byte[] iv = getIv();

            IvParameterSpec ivspec = new IvParameterSpec(iv);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(applicationSecret.toCharArray(), salt, 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
            String cipherText =
                    Base64.getEncoder().encodeToString(cipher
                            .doFinal(strToEncrypt.getBytes("UTF-8")));
            CryptData data = new CryptData(applicationSecret,
                    getBase64Encode(salt), getBase64Encode(iv), cipherText);

            return data;
        } catch (Exception e) {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }

    public String decrypt(String iv, String salt, String password) {
        return decrypt(new CryptData(applicationSecret, salt.getBytes(), iv.getBytes(), password));
    }

    public String decrypt(CryptData cryptData) {
        try {

            IvParameterSpec ivspec = new IvParameterSpec(getBase64Decode(cryptData.getIv()));
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(applicationSecret.toCharArray(),
                    getBase64Decode(cryptData.getSalt()), 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);
            return new String(cipher.doFinal(Base64.getDecoder().decode(cryptData.getCipherText())));
        } catch (Exception e) {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }

    private byte[] getSalt() {
        final Random r = new SecureRandom();
        byte[] salt = new byte[32];
        r.nextBytes(salt);

        return salt;
    }

    private byte[] getIv() {
        final Random r = new SecureRandom();
        byte[] salt = new byte[16];
        r.nextBytes(salt);

        return salt;
    }

    private byte[] getBase64Encode(byte[] data) {
        return Base64.getEncoder().encode(data);
    }

    private byte[] getBase64Decode(byte[] data) {
        return Base64.getDecoder().decode(data);
    }
}
