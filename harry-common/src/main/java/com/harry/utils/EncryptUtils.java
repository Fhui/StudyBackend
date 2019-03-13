package com.harry.utils;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;

/**
 * @author harry
 * @create 2019-03-12 16:17
 **/
public class EncryptUtils {

    private final static Logger logger = LoggerFactory.getLogger(EncryptUtils.class);

    public EncryptUtils() {

    }

    public static String encryptAES(String data, String sKey) {
        try {
            KeyGenerator keygen = KeyGenerator.getInstance("AES");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(sKey.getBytes());
            keygen.init(128, random);
            SecretKey original_key = keygen.generateKey();
            byte[] raw = original_key.getEncoded();
            SecretKey key = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(1, key);
            byte[] bytes = data.getBytes();
            byte[] result = cipher.doFinal(bytes);
            return bytesToHex(result);
        } catch (Throwable var10) {
            return null;
        }
    }

    public static String decryptAES(String src, String sKey) throws Exception {
        KeyGenerator keygen = KeyGenerator.getInstance("AES");
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(sKey.getBytes());
        keygen.init(128, random);
        SecretKey original_key = keygen.generateKey();
        byte[] raw = original_key.getEncoded();
        SecretKey key = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(2, key);
        byte[] bytes = hexToBytes(src);
        byte[] result = cipher.doFinal(bytes);
        return new String(result);
    }

    public static String getSHA1String(String s) throws Exception {
        if (StringUtils.isEmpty(s)) {
            return null;
        } else {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
            messageDigest.update(s.getBytes());
            return bytesToHex(messageDigest.digest());
        }
    }

    public static String getMD5String(String s) throws Exception {
        return getMD5String(s.getBytes());
    }

    public static String getMD5String(byte[] bytes) throws Exception {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(bytes);
            return bytesToHex(messageDigest.digest());
        } catch (NoSuchAlgorithmException var2) {
            throw var2;
        }
    }

    public static String privateDecryptRSA(String data) throws Exception {
        byte[] key = Base64.decodeBase64("MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCW3Ngsb2Pu29nwfZ7ArX668EDJclGLjlgHmVrXSRrRHczQMRT2HxbUXFuKrH67T3N+6i6NP+bgH1iuO7mhGcTyJyFYyZIQFSXfECvSVvHUVJXOYqbgJlezkXVrAjfPwqmKAizCOOM/lzS2KcfXYzlxPAPeY1KIz9Mnhn/iHkPAPmyA2w4ogr5s1oTdRayIyhjd2SV5akpf4wribNKYYnVAuFG/u5WU7AJ1fXNE8NqS359qVV+BC8gqdnVJkk47moOy9KA1y/Qb5HlnZ2dE2iaiqbkwBDPY1Db0gS506fFzM1sWqjVsPGYYHSQFWsGmRuR8jnzKmueYceY73Dfb8fIbAgMBAAECggEAMNHWwsppNGrA5CpCrWd3k1ccIBR2fwrZQdxGoVRu8S0LknPPrLXy1VXt6bpYaJUVcwSle+Ap/m0mbXu2XoPQT+yJxgm00q+FSeIZzD+Gkoes0tTp26+MwfdYYACfvwPniVma0l9GatWroSd0HCdn91TMi7Loio12b1v/FlVk51jM4pGDzhnx9GA4tAh3AYyO3btkupkwFP9sQHCJ+M3SPwCbg/P4KeH2K/HxGNGVwYDi8QrPa0jF/K18nsj+CiMCU6rF6Hh59yd94ZwM/eLYzgTVH5lk02Hp64+aVbaJN0xXZft8eZFqorl5zGm+l8leq9mzFyaa2difkJzuipHoQQKBgQDFRlzOZdz6ptseAp7umz1MXKtYdzieyG7yuqttQPFaDU4eU8MpaZ1cFasSIF4+55GM0UeafhrJ1MS/s8M0Qkvspg+wqclP7Xl8wJPQiKeIS+F4dbU8fTDqFaQRUgK0bf4GXqnbchtx3zQq0AcYXf2SX0J0jnTo5QZ4NVFJ/wZ4lQKBgQDDxZHGefWgEFAHrxXarkFf7254ognBcA9cCzXTs6qG6WjqQ+azdjfDRfC8wH8eYlEDOGrCIzFKwBHHNJgxZuX0KEIB874+BH13kY7yQfIH4GtRL/ID2qOfXpqatM5WQCTVajrXdB5keumYR7p4HHH+hUuon37oxk4OaLKILv8j7wKBgQC3lO8ws+Kc70Hh/5Zffd/hfCuIPApY2TVGdSiql1EC+HbGCjUICN0DoLZ+O9j4uQ7d+LOltczAHlyp+WOi8j8tjqvzP020L85qpdvoqAMf911veDqce/RVIqZ359XUirdmvhlbuiiII6qsXML9jho3aKazSQ7K3LbUjumC7uzasQKBgQDDZfqt4ySp8h4uzS4+6hYOLuhQN0sNa0+pSvcaqgTWSzmQON2W2bOmj7RMcMVwCilZ0YGc64tTVIlqDrCo/k8eduALGsETHWAta3cFjZSJEBGNIxGnjx4o/z/IkhYwOC2LZU/9o8SB/bwRZeZT4/BTXa+6ShddNjDKaO1ox5+YuQKBgCJXaeRDYCkwl6uRdYCjiz3fdzitxLhy91Xcmp9bnlvEp8K5TdBFRvE+LOo23u+cdI5ErSjP3jL3N3Wiyr4Wqbf4XjDgPYou1bO7jNtp2uCfDeXLR+KJfca6yP7iRVPY6vuXg/FrN9xUTxAqfz1H/uE2icKYjlKMfey7+Av/zwE9");
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(key);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(2, privateKey);
        byte[] r = cipher.doFinal(Base64.decodeBase64(data));
        return new String(r);
    }

    public static String getPublicKey() {
        return "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAltzYLG9j7tvZ8H2ewK1+uvBAyXJRi45YB5la10ka0R3M0DEU9h8W1Fxbiqx+u09zfuoujT/m4B9Yrju5oRnE8ichWMmSEBUl3xAr0lbx1FSVzmKm4CZXs5F1awI3z8KpigIswjjjP5c0tinH12M5cTwD3mNSiM/TJ4Z/4h5DwD5sgNsOKIK+bNaE3UWsiMoY3dkleWpKX+MK4mzSmGJ1QLhRv7uVlOwCdX1zRPDakt+falVfgQvIKnZ1SZJOO5qDsvSgNcv0G+R5Z2dnRNomoqm5MAQz2NQ29IEudOnxczNbFqo1bDxmGB0kBVrBpkbkfI58yprnmHHmO9w32/HyGwIDAQAB";
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (bytes != null && bytes.length > 0) {
            for(int i = 0; i < bytes.length; ++i) {
                int v = bytes[i] & 255;
                String hv = Integer.toHexString(v);
                if (hv.length() < 2) {
                    stringBuilder.append(0);
                }

                stringBuilder.append(hv);
            }

            return stringBuilder.toString();
        } else {
            return null;
        }
    }

    private static byte[] hexToBytes(String hexStr) {
        if (hexStr.length() < 1) {
            return null;
        } else {
            byte[] result = new byte[hexStr.length() / 2];

            for(int i = 0; i < hexStr.length() / 2; ++i) {
                int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
                int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
                result[i] = (byte)(high * 16 + low);
            }

            return result;
        }
    }

}
