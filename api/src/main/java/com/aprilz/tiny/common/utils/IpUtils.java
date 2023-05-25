package com.aprilz.tiny.common.utils;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Random;

/**
 * IpUtils
 *
 * @author aprilz
 * @version v1.0
 * 2020-12-08 15:32
 */
@Slf4j
public class IpUtils {

    /**
     * 获取本机IP
     *
     * @return ip
     */
    public static String getLocalIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.error("获取本机IP错误", e);
            return null;
        }
    }

    /**
     * 获取客户端IP地址
     *
     * @param request 请求
     * @return
     */
    public static String getIpAddress(HttpServletRequest request) {

        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ip != null && ip.length() > 15) {
            if (ip.indexOf(",") > 0) {
                ip = ip.substring(0, ip.indexOf(","));
            }
        }
        if ("0:0:0:0:0:0:0:1".equals(ip)) {
            ip = "127.0.0.1";
        }
        return ip;
    }


//    public static void main(String[] args) throws IOException {
//        System.out.println(IpUtils.getLocalIp());
////
//        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 7890));
//        String result1 = HttpRequest.get("https://www.google.com").setProxy(proxy).execute().body();
//        System.out.println(result1);
//
//    }

    /**
     * finalshell密码
     * @param args
     * @throws Exception
     */
    public static void main(String[] args)throws Exception {
        System.out.println(decodePass("123"));
    }
    public static byte[] desDecode(byte[] data, byte[] head) throws Exception {
        SecureRandom sr = new SecureRandom();
        DESKeySpec dks = new DESKeySpec(head);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey securekey = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(2, securekey, sr);
        return cipher.doFinal(data);
    }
    public static String decodePass(String data) throws Exception {
        if (data == null) {
            return null;
        } else {
            String rs = "";
            byte[] buf = Base64.getDecoder().decode(data);
            byte[] head = new byte[8];
            System.arraycopy(buf, 0, head, 0, head.length);
            byte[] d = new byte[buf.length - head.length];
            System.arraycopy(buf, head.length, d, 0, d.length);
            byte[] bt = desDecode(d, ranDomKey(head));
            rs = new String(bt);

            return rs;
        }
    }
    static byte[] ranDomKey(byte[] head) {
        long ks = 3680984568597093857L / (long)(new Random((long)head[5])).nextInt(127);
        Random random = new Random(ks);
        int t = head[0];

        for(int i = 0; i < t; ++i) {
            random.nextLong();
        }

        long n = random.nextLong();
        Random r2 = new Random(n);
        long[] ld = new long[]{(long)head[4], r2.nextLong(), (long)head[7], (long)head[3], r2.nextLong(), (long)head[1], random.nextLong(), (long)head[2]};
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);
        long[] var15 = ld;
        int var14 = ld.length;

        for(int var13 = 0; var13 < var14; ++var13) {
            long l = var15[var13];

            try {
                dos.writeLong(l);
            } catch (IOException var18) {
                var18.printStackTrace();
            }
        }

        try {
            dos.close();
        } catch (IOException var17) {
            var17.printStackTrace();
        }

        byte[] keyData = bos.toByteArray();
        keyData = md5(keyData);
        return keyData;
    }
    public static byte[] md5(byte[] data) {
        String ret = null;
        byte[] res=null;

        try {
            MessageDigest m;
            m = MessageDigest.getInstance("MD5");
            m.update(data, 0, data.length);
            res=m.digest();
            ret = new BigInteger(1, res).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return res;
    }
}
