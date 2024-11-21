package com.ewallet.app.utils;

import java.util.Base64;

public class Base64EncodeDecode {

    public static String encode(String data) {
        return Base64.getEncoder().encodeToString(data.getBytes());
    }

    public static String decode(String data) {
        byte[] decodeData = Base64.getDecoder().decode(data);
        return new String(decodeData);
    }
}
