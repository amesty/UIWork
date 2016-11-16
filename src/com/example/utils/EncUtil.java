package com.example.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;



public class EncUtil {
	
	public static void main(String[] args) {
		System.out.println(getMD5("000000"));

	}
	
	public static String getMD5(String content) {
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			digest.update(content.getBytes());
			return getHashString(digest);			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
	
    private static String getHashString(MessageDigest digest) {
        StringBuilder builder = new StringBuilder();
        for (byte b : digest.digest()) {
            builder.append(Integer.toHexString((b >> 4) & 0xf));
            builder.append(Integer.toHexString(b & 0xf));
        }
        return builder.toString();
    }
    
 	/**
 	 * 功能：编码字符串
 	 * @param data 源字符串
 	 * @return String
 	 */
 	public static String Base64Encode(String data) {
 		return Base64Encryption.encode(data);
 	}

 	/**
 	 * 功能：解码字符串
 	 * @param data 源字符串
 	 * @return String
 	 * @throws Base64DecodingException 
 	 */
 	public static String Base64Decode(String data) {
 		return Base64Encryption.decode(data);
 	}
}
