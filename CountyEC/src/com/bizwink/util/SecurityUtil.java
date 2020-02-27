package com.bizwink.util;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class SecurityUtil {
    public static String DES = "AES"; // optional value AES/DES/DESede

    public static String CIPHER_ALGORITHM = "AES"; // optional value AES/DES/DESede

    private static final String DES_ALGORITHM = "DES";

    private String key = "edarongedarongedarongbizwinkbizwinkedarongedarongedarongbizwinkbizwink"; // optional value AES/DES/DESede

/*
    private Key getSecretKey(String key) throws NoSuchAlgorithmException {
        if (null == key || key.length() == 0) {
            throw new NullPointerException("key not is null");
        }
        SecretKeySpec key2 = null;
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(key.getBytes());

        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, random);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            key2 = new SecretKeySpec(enCodeFormat, "AES");
        } catch (NoSuchAlgorithmException ex) {
            throw new NoSuchAlgorithmException();
        }
        return key2;
    }
*/

    /**
     * 获得秘密密钥
     *
     * @param secretKey
     * @return
     * @throws NoSuchAlgorithmException
     */
    private SecretKey getSecretKey(String secretKey) throws NoSuchAlgorithmException{
        if (secretKey == null) secretKey = this.key;
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(secretKey.getBytes());
        // 为我们选择的DES算法生成一个KeyGenerator对象
        KeyGenerator kg = null;
        try {
            kg = KeyGenerator.getInstance(DES);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        kg.init(secureRandom);
        //kg.init(56, secureRandom);

        // 生成密钥
        return kg.generateKey();
    }

    public String encrypt(String data,String key) throws Exception
    {
        if (key == null) key = this.key;
        SecureRandom sr = new SecureRandom();
        Key securekey = getSecretKey(key);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
        byte[] bt = cipher.doFinal(data.getBytes());
        String strs = new BASE64Encoder().encode(bt);
        return strs;
    }


    public  String detrypt(String message,String key) throws Exception{
        if (key == null) key = this.key;
        SecureRandom sr = new SecureRandom();
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        Key securekey = getSecretKey(key);
        cipher.init(Cipher.DECRYPT_MODE, securekey,sr);
        byte[] res = new BASE64Decoder().decodeBuffer(message);
        res = cipher.doFinal(res);
        return new String(res);
    }

    public static void main(String[] args)throws Exception{
        SecurityUtil securityUtil = new SecurityUtil();
        String message = "password";
        String key = "";
        String entryptedMsg = securityUtil.encrypt(message, null);
        System.out.println("encrypted message is below :");
        System.out.println(entryptedMsg);

        String decryptedMsg = securityUtil.detrypt(entryptedMsg, null);
        System.out.println("decrypted message is below :");
        System.out.println(decryptedMsg);
    }
}
