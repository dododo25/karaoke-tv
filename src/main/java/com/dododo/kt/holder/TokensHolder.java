package com.dododo.kt.holder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.HexFormat;
import java.util.Map;

public class TokensHolder {

    private static final String SALT = "-$alTe4";

    public static final HexFormat FORMAT = HexFormat.of();

    private final Map<String, String> tokenMap;

    private final MessageDigest digest;

    public TokensHolder() throws NoSuchAlgorithmException {
        this.tokenMap = new HashMap<>();
        this.digest = MessageDigest.getInstance("SHA-256");
    }

    public String generateAndSave(String code) {
        String generated = prepareHex(digest.digest((code + SALT).getBytes()));
        tokenMap.put(code, generated);
        return generated;
    }

    public String get(String code) {
        return tokenMap.remove(code);
    }

    private String prepareHex(byte[] arr) {
        StringBuilder builder = new StringBuilder();

        for (byte b : arr) {
            builder.append(FORMAT.toHexDigits(b));
        }

        return builder.toString();
    }
}
