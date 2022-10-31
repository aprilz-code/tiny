package com.aprilz.tiny.common.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @description: redis 字符太长，压缩util
 * @author: Aprilz
 * @since: 2022/10/31
 **/
public class GZIPUtil {

    /**
     * 使用gzip压缩字符串
     */
    public static String compress(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }

        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             GZIPOutputStream gzip = new GZIPOutputStream(out)) {
            gzip.write(str.getBytes());
            return new sun.misc.BASE64Encoder().encode(out.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;

    }

    /**
     * 使用gzip解压缩
     */
    public static String uncompress(String compressedStr) throws IOException {
        if (compressedStr == null || compressedStr.length() == 0) {
            return compressedStr;
        }
        byte[] compressed = new sun.misc.BASE64Decoder().decodeBuffer(compressedStr);

        String decompressed = null;
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             ByteArrayInputStream in = new ByteArrayInputStream(compressed);
             GZIPInputStream ginzip = new GZIPInputStream(in);) {
            byte[] buffer = new byte[1024];
            int offset = -1;
            while ((offset = ginzip.read(buffer)) != -1) {
                out.write(buffer, 0, offset);
            }
            decompressed = out.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return decompressed;
    }

}
