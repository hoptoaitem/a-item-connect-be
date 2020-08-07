package com.aitem.connect.helper;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CryptData {

    private String applicationSecret;
    private byte[] salt;
    private byte[] iv;
    private String cipherText;
}
