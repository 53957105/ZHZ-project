import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

public class RSACoder {

    public static final String KEY_ALGORITHM = "RSA";
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    private static final String PUBLIC_KEY = "RSAPublicKey";
    private static final String PRIVATE_KEY = "RSAPrivateKey";

    /**
     * ��ʼ����Կ
     * @return
     * @throws Exception
     */
    public Map<String, Key> initKey() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGenerator.initialize(1024);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        Map<String,Key> keyMap = new HashMap<>();
        keyMap.put(PUBLIC_KEY,keyPair.getPublic());
        keyMap.put(PRIVATE_KEY,keyPair.getPrivate());
        return keyMap;
    }

    /**
     * ��ù�Կ
     * @param keyMap
     * @return
     * @throws UnsupportedEncodingException 
     */
    public String getPublicKey(Map<String,Key> keyMap) throws UnsupportedEncodingException{
        Key key = keyMap.get(PUBLIC_KEY);
        return encryptBASE64(key.getEncoded());
    }

    /**
     * ���˽Կ
     * @param keyMap
     * @return
     * @throws UnsupportedEncodingException 
     */
    public static String getPrivateKey(Map<String, Key> keyMap) throws UnsupportedEncodingException{
        Key key = keyMap.get(PRIVATE_KEY);
        return encryptBASE64(key.getEncoded());
    }

    /**
     * ����Base64
     * @param key
     * @return
     */
    public static byte[] decryptBASE64(String key) {
        return Base64.getDecoder().decode(key);
    }

    /**
     * ����Base64
     * @param bytes
     * @return
     * @throws UnsupportedEncodingException 
     */
    public static String encryptBASE64(byte[] bytes) throws UnsupportedEncodingException {
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * ����<br>
     * ��˽Կ����
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(byte[] data, String key) throws Exception {
        // ����Կ����
        byte[] keyBytes = decryptBASE64(key);
        // ȡ��˽Կ
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        // �����ݼ���
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }

    /**
     * ���ܣ���Կ����
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(String data, String key) throws Exception {
        // �Թ�Կ����
        byte[] keyBytes = decryptBASE64(key);
        // ȡ�ù�Կ
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicKey = keyFactory.generatePublic(x509KeySpec);
        // �����ݼ���
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(data.getBytes());
    }

    /**
     * У������ǩ��
     * @param data
     * @param publicKey
     * @param sign
     * @return
     * @throws Exception
     */
    public static boolean verify(byte[] data, String publicKey, String sign) throws Exception {
        // ������base64����Ĺ�Կ
        byte[] keyBytes = decryptBASE64(publicKey);
        // ����X509EncodedKeySpec����
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        // KEY_ALGORITHM ָ���ļ����㷨
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        // ȡ��Կ�׶���
        PublicKey pubKey = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(pubKey);
        signature.update(data);
        // ��֤ǩ���Ƿ�����
        return signature.verify(decryptBASE64(sign));
    }

    /**
     * ��˽Կ����Ϣ��������ǩ��
     * @param data
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static String sign(byte[] data, String privateKey) throws Exception {
        // ������base64�����˽Կ
        byte[] keyBytes = decryptBASE64(privateKey);
        // ����PKCS8EncodedKeySpec����
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        // KEY_ALGORITHM ָ���ļ����㷨
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        // ȡ˽Կ�׶���
        PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
        // ��˽Կ����Ϣ��������ǩ��
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(priKey);
        signature.update(data);
        return encryptBASE64(signature.sign());
    }

    /**
     * ���ܣ���Կ����
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(byte[] data, String key) throws Exception {
        // ����Կ����
        byte[] keyBytes = decryptBASE64(key);
        // ȡ�ù�Կ
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicKey = keyFactory.generatePublic(x509KeySpec);
        // �����ݽ���
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        return cipher.doFinal(data);
    }

    /**
     * ���ܣ�˽Կ����
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] data, String key) throws Exception{
        // ����Կ����
        byte[] keyBytes = decryptBASE64(key);
        // ȡ��˽Կ
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        // �����ݽ���
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }


//    public static void main(String[] args) throws Exception {
//        Map<String, Key> keyMap = initKey();
//        String publicKey = getPublicKey(keyMap);
//        String privateKey = getPrivateKey(keyMap);
//
//        System.out.println(keyMap);
//        System.out.println("-----------------------------------");
//        System.out.println("publicKey��"+publicKey);
//        System.out.println("-----------------------------------");
//        System.out.println("privateKey��"+privateKey);
//        System.out.println("-----------------------------------");
//        byte[] encryptByPrivateKey = encryptByPrivateKey("123456".getBytes(),privateKey);
//        byte[] encryptByPublicKey = encryptByPublicKey("123456",publicKey);
//        System.out.println("encryptByPrivateKey��"+new String(encryptByPrivateKey));
//        System.out.println("-----------------------------------");
//        System.out.println("encryptByPublicKey"+new String(encryptByPublicKey));
//        System.out.println("-----------------------------------");
//        String sign = sign(encryptByPrivateKey,privateKey);
//        System.out.println("sign��"+sign);
//        System.out.println("-----------------------------------");
//        boolean verify = verify(encryptByPrivateKey,publicKey,sign);
//        System.out.println("verify��"+verify);
//        System.out.println("-----------------------------------");
//        byte[] decryptByPublicKey = decryptByPublicKey(encryptByPrivateKey,publicKey);
//        byte[] decryptByPrivateKey = decryptByPrivateKey(encryptByPublicKey,privateKey);
//        System.out.println("decryptByPublicKey��"+new String(decryptByPublicKey));
//        System.out.println("-----------------------------------");
//        System.out.println("decryptByPrivateKey��"+new String(decryptByPrivateKey));
//
//    }
}

