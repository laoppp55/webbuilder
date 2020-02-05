package com.bizwink.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class SecurityUtil {
    public static String DES = "AES"; // optional value AES/DES/DESede

    public static String CIPHER_ALGORITHM = "AES"; // optional value AES/DES/DESede

    private static final String DES_ALGORITHM = "DES";

    private static String key = "edarongedarongedarongbizwinkbizwinkedarongedarongedarongbizwinkbizwink"; // optional value AES/DES/DESede

    private final static BASE64Encoder base64encoder = new BASE64Encoder();

    private final static BASE64Decoder base64decoder = new BASE64Decoder();

    private final static String encoding = "UTF-8";

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
     * 对称加密方法
     *
     * @param byteSource
     *            需要加密的数据
     * @return 经过加密的数据
     * @throws Exception
     */
    public static byte[] symmetricEncrypto(byte[] byteSource) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            int mode = Cipher.ENCRYPT_MODE;
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            byte[] keyData = key.getBytes();
            DESKeySpec keySpec = new DESKeySpec(keyData);
            Key key = keyFactory.generateSecret(keySpec);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(mode, key);
            byte[] result = cipher.doFinal(byteSource);
// int blockSize = cipher.getBlockSize();
// int position = 0;
// int length = byteSource.length;
// boolean more = true;
// while (more) {
// if (position + blockSize <= length) {
// baos.write(cipher.update(byteSource, position, blockSize));
// position += blockSize;
// } else {
// more = false;
// }
// }
// if (position < length) {
// baos.write(cipher.doFinal(byteSource, position, length
// - position));
// } else {
// baos.write(cipher.doFinal());
// }
// return baos.toByteArray();
            return result;
        } catch (Exception e) {
            throw e;
        } finally {
            baos.close();
        }
    }


    /**
     * 对称解密方法
     *
     * @param byteSource
     *            需要解密的数据
     * @return 经过解密的数据
     * @throws Exception
     */
    public static byte[] symmetricDecrypto(byte[] byteSource) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            int mode = Cipher.DECRYPT_MODE;
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            //byte[] keyData = { 1, 9, 8, 2, 0, 8, 2, 1 };
            byte[] keyData = key.getBytes();
            DESKeySpec keySpec = new DESKeySpec(keyData);
            Key key = keyFactory.generateSecret(keySpec);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(mode, key);
            byte[] result = cipher.doFinal(byteSource);
// int blockSize = cipher.getBlockSize();
// int position = 0;
// int length = byteSource.length;
// boolean more = true;
// while (more) {
// if (position + blockSize <= length) {
// baos.write(cipher.update(byteSource, position, blockSize));
// position += blockSize;
// } else {
// more = false;
// }
// }
// if (position < length) {
// baos.write(cipher.doFinal(byteSource, position, length
// - position));
// } else {
// baos.write(cipher.doFinal());
// }
// return baos.toByteArray();
            return result;
        } catch (Exception e) {
            throw e;
        } finally {
            baos.close();
        }
    }


    /**
     * 加密字符串
     */
    public static String Encrypto(String str) {
        String result = str;
        if (str != null && str.length() > 0) {
            try {
                byte[] encodeByte = symmetricEncrypto(str.getBytes(encoding));
                result = base64encoder.encode(encodeByte);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }


    /**
     * 解密字符串
     */
    public static String Decrypto(String str) {
        String result = str;
        if (str != null && str.length() > 0) {
            try {
                byte[] encodeByte = base64decoder.decodeBuffer(str);
                byte[] decoder = symmetricDecrypto(encodeByte);
                result = new String(decoder, encoding);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }


    /**
     * 获得秘密密钥
     *
     * @param secretKey
     * @return
     * @throws java.security.NoSuchAlgorithmException
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
