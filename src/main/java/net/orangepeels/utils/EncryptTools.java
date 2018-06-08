package net.orangepeels.utils;

import org.junit.Test;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class EncryptTools {
    private static final String KEY = "mother";

    /**
     * BASE64解密,用于传递参数
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptBASE64(String key) throws Exception {
        key = new String((new BASE64Decoder()).decodeBuffer(key));
        key = key.substring(KEY.length(), key.length());
        return key.getBytes();
    }

    /**
     * BASE64加密,用于传递参数
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static String encryptBASE64(byte[] key) throws Exception {
        String str = KEY + new String(key);
        return (new BASE64Encoder()).encodeBuffer(str.getBytes());
    }
}