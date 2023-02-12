package com.util.tools;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.Cipher;

import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class ParseKsy {
    public static String SEED_16_CHARACTER = "zG2nSeEfSHfvTCHy5LCcqtBbQehKNLXn";
    private final SecretKeySpec key;
    private final Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
    private final AlgorithmParameterSpec spec = getIV();

    public ParseKsy() throws Exception {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(SEED_16_CHARACTER.getBytes(StandardCharsets.UTF_8));
        byte[] bArr = new byte[32];
        System.arraycopy(messageDigest.digest(), 0, bArr, 0, 32);
        this.key = new SecretKeySpec(bArr, "AES");
    }

    public static String bytesToHexString(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        if (bArr == null || bArr.length == 0) {
            return null;
        }
        for (byte b : bArr) {
            String hexString = Integer.toHexString(b & 255);
            if (hexString.length() < 2) {
                sb.append(0);
            }
            sb.append(hexString);
        }
        return sb.toString();
    }

    public static String getMD5111(String str) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(str.getBytes(StandardCharsets.UTF_8));
            return bytesToHexString(messageDigest.digest());
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getMd5Value(String str) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(str.getBytes(StandardCharsets.UTF_8));
            StringBuilder stringBuffer = new StringBuilder();
            byte[] digest = messageDigest.digest();
            for (int b : digest) {
                int i2 = b;
                if (i2 < 0) {
                    i2 += 256;
                }
                if (i2 < 16) {
                    stringBuffer.append("0");
                }
                stringBuffer.append(Integer.toHexString(i2));
            }
            return stringBuffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String md5Code(String str) {
        try {
            byte[] digest = MessageDigest.getInstance("MD5").digest(str.getBytes());
            StringBuilder stringBuffer = new StringBuilder();
            for (byte b : digest) {
                String hexString = Integer.toHexString(b & 255);
                if (hexString.length() < 2) {
                    hexString = "0" + hexString;
                }
                stringBuffer.append(hexString);
            }
            return stringBuffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String stringToMD5(String str) {
        try {
            byte[] digest = MessageDigest.getInstance("MD5").digest(str.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder(digest.length * 2);
            for (byte b : digest) {
                int i = b & 255;
                if (i < 16) {
                    sb.append("0");
                }
                sb.append(Integer.toHexString(i));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String decrypt(String str) throws Exception {
        this.cipher.init(2, this.key, this.spec);
        return new String(this.cipher.doFinal(Base64.getMimeDecoder().decode(str.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
    }

    public String decryptContent(String str, String chapterKey) throws Exception {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(chapterKey.getBytes(StandardCharsets.UTF_8));
        byte[] bArr = new byte[32];
        System.arraycopy(messageDigest.digest(), 0, bArr, 0, 32);
        SecretKeySpec Key = new SecretKeySpec(bArr, "AES");
        this.cipher.init(2, Key, this.spec);
        return new String(this.cipher.doFinal(Base64.getMimeDecoder().decode(str.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
    }

    public String encrypt(String str) throws Exception {
        this.cipher.init(1, this.key, this.spec);
        return new String(Base64.getMimeEncoder().encode(this.cipher.doFinal(str.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
    }

    public AlgorithmParameterSpec getIV() {
        return new IvParameterSpec(new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
    }
}
