package com.linchtech.fileservice.util;

import org.apache.commons.codec.binary.Hex;

import java.io.InputStream;
import java.security.MessageDigest;

/**
 * @author: 107
 * @date: 2019-05-26 16:52
 * @description:
 **/
public class MD5Util {

    /**
     * 文件流的MD5
     * @return
     * @throws Exception
     */
    public static String md5File(InputStream  inputStream) throws Exception {
        MessageDigest MD5 = MessageDigest.getInstance("MD5");
        byte[] buffer = new byte[8192];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            MD5.update(buffer, 0, length);
        }
        return new String(Hex.encodeHex(MD5.digest()));
    }
}
