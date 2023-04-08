package com.aprilz.tiny.common.utils;


import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @author Aprilz
 * @date 2023/4/7-14:58
 * @description 密码加密解密
 *  TODO 防止登录密码泄露
 */
public class RsaUtils {
    // Rsa 私钥
    public static String privateKeyStr = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJjpmxihiuJzVRVagEheioeSw5Euxg46Bwn0faeYULmWppgR77HgnoCegmudk0SVJ8DjHFCLjQeupD9gWgCgLcdXnuIBOIYpGv2RSjegpn/v0076bjsPue8zHhyo+0qAuA1j/AL73MonKLLMLH9k6+2KRazjvVjriQGcicucdsBZAgMBAAECgYA+kL67+kT4WNZEUW5RrhUiWaOZQLka0xVUNmw/aawF86UgbZ5UeqJ/39tfKu85zYmhe4t3TVIcBZIAuGy6+s8fsL899tDvPq1sxWgBSYeI5NaSzo5uUNM24Sxr32FAWGObpD6hLqZkE39lzVgjGvVb1Y31D6MgBKuA4IEn5l2e8QJBAOMin10V374OGVqeCHA2Hg+MZ8OHx4bGztn4QsQYqfPN2QEN5vuCnKuVyscs/mu+KU5tJ3xuV8Erd7OuagXyAsUCQQCsWEzHhtM3RWoSe4H0qHTtmZecd6RHai3QsPLv/yV3O/upAuOOtchh1LXQmvOcj1Z5VGykjNfUUuOB05i8QxCFAkB4AVj105LNVaGrsQeAUfd7+5DvTBPtb6jmnTaZaPaSa9YVqoS5qf6g4ZmrtmgOAQDOAPn10k7nLtIlyycVURKJAkEAjmJlDHLXDVLLz3k6ZGbIM4QxPZOapWWTdFa8Xors6RUlVPyOA9krM6gIjc92dGH3j0WwfEHgDgps61VlWiQOAQJBAKoAe0OU2GF5UGPWjcRDWDzCVUKgDsfLIT5hiRoU2w87mxzV1JZVBj7mKBIUk/Q4LVxf2d1sOYFLMdHAqkXxs3o=";

    public static String publicKeyStr = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCY6ZsYoYric1UVWoBIXoqHksORLsYOOgcJ9H2nmFC5lqaYEe+x4J6AnoJrnZNElSfA4xxQi40HrqQ/YFoAoC3HV57iATiGKRr9kUo3oKZ/79NO+m47D7nvMx4cqPtKgLgNY/wC+9zKJyiyzCx/ZOvtikWs471Y64kBnInLnHbAWQIDAQAB";

    /**
     * 解码PublicKey
     *
     * @param key
     * @return
     */
    public static PublicKey getPublicKey(String key) {
        try {
            byte[] byteKey = Base64.getDecoder().decode(key);
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(byteKey);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePublic(x509EncodedKeySpec);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解码PrivateKey
     *
     * @param key
     * @return
     */
    public static PrivateKey getPrivateKey(String key) {
        try {
            byte[] byteKey = Base64.getDecoder().decode(key);
            PKCS8EncodedKeySpec x509EncodedKeySpec = new PKCS8EncodedKeySpec(byteKey);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePrivate(x509EncodedKeySpec);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 公钥加密
     *
     * @param content
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static String encrypt(String data, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return Base64.getEncoder().encodeToString(cipher.doFinal(data.getBytes()));
    }

    /**
     * 私钥解密
     *
     * @param content
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static String decrypt(String data, PrivateKey privateKey) throws Exception {
        byte[] bytes = Base64.getDecoder().decode(data);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return new String(cipher.doFinal(bytes));
    }

    /**
     * 生成密钥对
     *
     * @return
     * @throws Exception
     */
    public static KeyPair genKeyPair() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(1024);
        return keyPairGenerator.generateKeyPair();
    }


    public static void main(String[] args) throws Exception {

        String data = "hello world";
        KeyPair keyPair = genKeyPair();

        // 获取公钥，并以base64格式打印出来
        PublicKey publicKey = keyPair.getPublic();
        System.out.println("公钥：" + Base64.getEncoder().encodeToString(publicKey.getEncoded()));

        // 获取私钥，并以base64格式打印出来
        PrivateKey privateKey = keyPair.getPrivate();
        System.out.println("私钥：" + Base64.getEncoder().encodeToString(privateKey.getEncoded()));

        // 公钥加密
        String encryptedString = encrypt(data, publicKey);
        System.out.println("加密后：" + encryptedString);

        // 私钥解密
        String decryptedString = decrypt(encryptedString, privateKey);
        System.out.println("解密后：" + decryptedString);

        // 使用生成的Rsa
        PublicKey publicKey1 = getPublicKey(publicKeyStr);
        PrivateKey privateKey1 = getPrivateKey(privateKeyStr);

        encryptedString = encrypt(data, publicKey1);
        System.out.println("加密后：" + encryptedString);

        decryptedString = decrypt(encryptedString, privateKey1);
        System.out.println("解密后：" + decryptedString);

    }

}
