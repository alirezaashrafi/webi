package com.ashrafi.webi.classes;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class HashKey {
    private static final String TAG = "HashKey";
    private final String MD5 = "MD5";
    private final String SHA1 = "SHA-1";
    private final String SHA256 = "SHA-256";
    private final String SHA384 = "SHA-384";
    private final String SHA512 = "SHA-512";
    private String key = "";
    private String HashType = MD5;

    public HashKey(String key) {
        this.key = key;
        build();
    }

    public String build() {
        try {
            MessageDigest digest = java.security.MessageDigest.getInstance(HashType);
            digest.update(key.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }

            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }

    protected void md5() {
        HashType = MD5;
    }

    protected void sha1() {
        HashType = SHA1;
    }

    protected void sha256() {
        HashType = SHA256;
    }

    protected void sha384() {
        HashType = SHA384;
    }

    protected void sha512() {
        HashType = SHA512;
    }


}
