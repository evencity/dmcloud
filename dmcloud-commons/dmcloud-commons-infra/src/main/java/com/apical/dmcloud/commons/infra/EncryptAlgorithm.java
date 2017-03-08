package com.apical.dmcloud.commons.infra;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

@SuppressWarnings("restriction")
public class EncryptAlgorithm
{
	public static final String KEY_SHA = "SHA";  
	public static final String KEY_MD5 = "MD5";  
	public static final String KEY_MAC = "HmacMD5";  
  
    /** 
     * MAC算法可选以下多种算法 
     *  
     * <pre> 
     * HmacMD5  
     * HmacSHA1  
     * HmacSHA256  
     * HmacSHA384  
     * HmacSHA512 
     * </pre> 
     */  
    
  
    /** 
     * BASE64解密 
     *  
     * @param key 需要解密的密文
     * @return 解密后的原文
     * @throws IOException 
     */  
    public static byte[] decryptBASE64(String key) throws IOException
    {
    	return (new BASE64Decoder()).decodeBuffer(key);
    }
  
    /** 
     * BASE64加密 
     *  
     * @param key 需要加密的原文
     * @return 加密后的密文
     */  
    public static String encryptBASE64(byte[] key)
    {
        return (new BASE64Encoder()).encodeBuffer(key);
    }
  
    /** 
     * MD5加密 
     *  
     * @param data 需要加密的原文
     * @return 加密后的密文
     * @throws NoSuchAlgorithmException 
     */  
    public static byte[] encryptMD5(byte[] data) throws NoSuchAlgorithmException
    {
        MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
        md5.update(data);

        return md5.digest();
    }  
  
    /** 
     * SHA加密 
     *  
     * @param data 需要加密的原文
     * @return 加密后的密文
     */  
    public static byte[] encryptSHA(byte[] data) throws NoSuchAlgorithmException
    {
        MessageDigest sha = MessageDigest.getInstance(KEY_SHA);
        sha.update(data);

        return sha.digest();
    }
  
    /** 
     * 初始化HMAC密钥 
     *  
     * @return HMAC密钥
     */  
    public static String initMacKey() throws NoSuchAlgorithmException
    {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_MAC);

        SecretKey secretKey = keyGenerator.generateKey();
        return encryptBASE64(secretKey.getEncoded());
    }
  
    /** 
     * HMAC加密 
     *  
     * @param data 需要加密的原文
     * @param key HMAC密钥 
     * @return 加密后的密文
     * @throws IOException 
     * @throws NoSuchAlgorithmException 
     * @throws InvalidKeyException 
     */
    public static byte[] encryptHMAC(byte[] data, String key)
    		throws IOException, NoSuchAlgorithmException, InvalidKeyException
    {
        SecretKey secretKey = new SecretKeySpec(decryptBASE64(key), KEY_MAC);
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());
        mac.init(secretKey);

        return mac.doFinal(data);
    }
}
