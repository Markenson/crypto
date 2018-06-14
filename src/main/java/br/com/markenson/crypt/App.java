package br.com.markenson.crypt;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.*;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * This app decrypts encoded BASE64 data come from web page crypto.html using the same RSA keys.
 */
public class App 
{
    static final int PUB_K = 0;
    static final int PRIV_K = 1;

    //public key shared with web page (crypto.html)
    static String pemPubKey = "-----BEGIN PUBLIC KEY-----MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgr9Lpge9/90Cqmht8gdJ4Y3t3MAyUoZ9hzqzI6ZXGq09TsYw1nVBoG0ELQTSfHZCsvmdXdy+s/hBTDXDJ0pI5em6Lqsaw81dAszoobt5D4dzhUEFerDmPEmSfD/gBSSQKycnmaybypFGpLZ44CchluNX76fyLGPZK93WT447xVsunXujGoGqZbPILYdRDAzutfBAGI+M1MQHsZr9hp6vAtAcbrA1uxjpR/NP3Rj9YYt/FK7X1u1zudzU/Vp7REjUlFYvUOCuCPBy6TQ1qMIz/Fp1LUvUnImjWlqZQU7XKzd1XrN+OCDGq8trdlKXjwQk7P1t2Ws4DKjyc8a0lkmlcwIDAQAB-----END PUBLIC KEY-----";
    static String pemPrivKey = "-----BEGIN PRIVATE KEY-----MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCCv0umB73/3QKqaG3yB0nhje3cwDJShn2HOrMjplcarT1OxjDWdUGgbQQtBNJ8dkKy+Z1d3L6z+EFMNcMnSkjl6bouqxrDzV0CzOihu3kPh3OFQQV6sOY8SZJ8P+AFJJArJyeZrJvKkUaktnjgJyGW41fvp/IsY9kr3dZPjjvFWy6de6Magapls8gth1EMDO618EAYj4zUxAexmv2Gnq8C0BxusDW7GOlH80/dGP1hi38UrtfW7XO53NT9WntESNSUVi9Q4K4I8HLpNDWowjP8WnUtS9SciaNaWplBTtcrN3Ves344IMary2t2UpePBCTs/W3ZazgMqPJzxrSWSaVzAgMBAAECggEAFIZTKc0tVm0rbfTWzZe3UWEmZfEF9U6HhiMA3CfQpK9tYGBcak4d70Et1qNbJ46pCc+FlKaCiV3/YidOa2yN5Qm74HebzAEBCN+B5WG4r2dkCxcZ9RiwC+bAyCB+2k/TEfMjlEw0aKhFnrXGY+nArB8kPsruiWljPi+k5Pg/Ccv8G823O9Iaf1HIj9VtVHUDnySdoZYmYBpgu7E7n2IWq+L7zKYyEqKxgT06kzlzh3TmbMn8wHgm0ERbqmm8V1fr2P0cEYA3V3EchPd8MvyYaNp8uV53Qc91GuJRZB20ev7DDoDQH2vRobLr8xF10VT5dQISYTpwYUlFG0QepQftgQKBgQDdH8Dil0WwtEDCX7L9HFa1adlcv//iOX9uQQhvgvKId1nyHztFhDoMHzfykNyO9JPOOOir524uFZ2WgB2f9uUc5q/uHJv6OMpy3MP+vxHO4+jU4QK1V8iHcbZ/651CWU+ULfhmGJ+iMwnMewTIMo8XAPJO7yjuXKGWvfUBEqhZfQKBgQCXXm48GE1WBlgmzxLny9JKUEOPL3WW8T8BM5vtjRXnSrlCNSFcr2Lw98r+7cc2RCXtVTHUFf5+XX/XzSDRuGGlvVoV15h9FATbRN+ptoJTcSfVJ2D+nV7+/Fzs+1b9tfSrHLpVCB2VNI/prodRez492QtVxoGNFB5KekSD6JetrwKBgQCe/pz/kIvAoetKl+soSf8i1GhBY6DG9lblXQeGvt5LN55KFSDWPFxMqQnedUDR3ZSFnK/YI0E7C7nTJG2ovqtBG7J2gMW6pqkS7CBVhOd2HrQklWIQKH18vkveMPlTYspwnplUN/JYe2BZUj8+//OZM8oG7bo9ogdiT1XinprENQKBgQCCJTLqgIqd2D6av5glrF1Wf8RWrBuSeEIPqmv6xz956gl3H29b3YBYZvUmUxLhWZ6yfHmnPHHf/EwbI8eACeQLOM7n0MLPaBL05DbaGI3daIEzB/ShdnVjD2BmcYPOCFAhf0XGLoAg9Vx3MxQtvqU5UBAq484nmpCgwJeAQs+HCQKBgQDOX1KBX9LZTXXeQAT4IUypD1ivWzuaKM0Do80jhDjZ7BUsdfWYVP6eh48btwOTU174WjwGIfr2A/fl5TCCfs09T71XS7fZObjs5T7DScEALxNFvDHSyajDyONRLOZARCCiVXP38ilcJQRFfJzTSwSUW9k4vABw+yE+uEyOIu3YwA==-----END PRIVATE KEY-----";

    static final String password = "teste";

    public static void main( String[] args ) throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, InvalidKeySpecException, IOException {
        App.testEncryptDecryptAllInJava();
        App.testSharedKeyBetweenWebPageAndJava();
    }

    private static void testSharedKeyBetweenWebPageAndJava() throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, IOException {
        String privateKeyContent = pemPrivKey.replaceAll("\\n", "").replace("-----BEGIN PRIVATE KEY-----", "").replace("-----END PRIVATE KEY-----", "");

        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWITHSHA1ANDMGF1PADDING");

        KeyFactory kf = KeyFactory.getInstance("RSA");

        BASE64Decoder dec = new BASE64Decoder();
        PKCS8EncodedKeySpec keySpecPKCS8 = new PKCS8EncodedKeySpec(dec.decodeBuffer(privateKeyContent));
        PrivateKey privKey = kf.generatePrivate(keySpecPKCS8);
        cipher.init(Cipher.DECRYPT_MODE, privKey);

        //Encrypted data from web
        String fromWeb = "f4aOPojwqch3twYWdz8SLtXon8CNYiG/Yj6LukmcyszK8NO+ipy/7t2QlOdDPK+qLzLtn5MEjUiismvXZyBWH2LXfzlHi3NeOQlDHguqvjZCQpfii8au377ZyXlTuTHFNIuGvX1wRjIwPvzF6liLuy8DjdMk5S/pi6R+v9MnU1Z168erHC8OKShuMo//KIl+YEa4iFppuFrZpowj4l93tZkTxBTuauGFa1CjN+6D4NEKkkj+mwQFMDOOBuKJSVv9MECNL4Kfsu1YVtAHeyLZ0rS9cNtDbvAfk5r6nItVidt5JMazqVIwipo829AFhokigc08W4Hgvr46gQe0jiOqJQ==";
        byte[] decriptFromWeb = cipher.doFinal(dec.decodeBuffer(fromWeb));
        System.out.println("teste123".equals(new String(decriptFromWeb)));

    }

    private static void testEncryptDecryptAllInJava() throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, IOException {

        //Create a random key pair
        String[]  keys = App.createKeys();
        pemPubKey = keys[PUB_K];
        pemPrivKey = keys[PRIV_K];

        String privateKeyContent = pemPubKey.replaceAll("\\n", "").replace("-----BEGIN PRIVATE KEY-----", "").replace("-----END PRIVATE KEY-----", "");
        String publicKeyContent = pemPubKey.replaceAll("\\n", "").replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", "");;

        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWITHSHA1ANDMGF1PADDING");

        KeyFactory kf = KeyFactory.getInstance("RSA");

        BASE64Encoder enc = new BASE64Encoder();
        BASE64Decoder dec = new BASE64Decoder();

        PKCS8EncodedKeySpec keySpecPKCS8 = new PKCS8EncodedKeySpec(dec.decodeBuffer(privateKeyContent));
        PrivateKey privKey = kf.generatePrivate(keySpecPKCS8);

        X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(dec.decodeBuffer(publicKeyContent));
        RSAPublicKey pubKey = (RSAPublicKey) kf.generatePublic(keySpecX509);

        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        byte[] encryptedData = cipher.doFinal(password.getBytes());
        String b64Enc = enc.encode(encryptedData);
        System.out.println("Encrypted:" + b64Enc);

        cipher.init(Cipher.DECRYPT_MODE, privKey);
        byte[] decryptedData = cipher.doFinal(dec.decodeBuffer(b64Enc));

        System.out.println("Password equals decrypted one:" +  password.equals(new String(decryptedData)));

    }

    private static String[] createKeys() {
        String[] keys = new String[2];
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(2048);
            KeyPair kp = kpg.generateKeyPair();
            Key pub = kp.getPublic();
            Key pvt = kp.getPrivate();

            BASE64Encoder enc = new BASE64Encoder();

            StringBuffer sbPvt = new StringBuffer();
            sbPvt.append("-----BEGIN PRIVATE KEY-----\n");
            sbPvt.append(enc.encode(pvt.getEncoded()));
            sbPvt.append("\n-----END PRIVATE KEY-----\n");

            StringBuffer sbPub = new StringBuffer();
            sbPub.append("-----BEGIN PUBLIC KEY-----\n");
            sbPub.append(enc.encode(pub.getEncoded()));
            sbPub.append("\n-----END PUBLIC KEY-----\n");

            keys[PRIV_K] = sbPvt.toString();
            keys[PUB_K] = sbPub.toString();

            System.out.println("Private:\n" + keys[PRIV_K]);
            System.out.println("Public:\n" + keys[PUB_K]);

        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return keys;
    }

}
