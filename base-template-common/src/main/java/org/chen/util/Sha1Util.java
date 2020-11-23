package org.chen.util;


import org.chen.util.sm2.Base64;
import org.chen.util.sm2.SM2;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * sha1加密工具
 *  
 * @author YuChen
 * @date 2020/2/13 15:05
 **/
 
public class Sha1Util {



	public static String getSha1(String str) {

		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
			mdTemp.update(str.getBytes("UTF-8"));
			byte[] md = mdTemp.digest();
			int j = md.length;
			char buf[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
				buf[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(buf);
		} catch (Exception e) {
			return null;
		}
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		String appId = "8a81c1be6f19b11301712973080d0109";
		String appKey = "0574f036263a443b8116b20a4afa50d8";
		String timestamp = getTimeStamp();
		System.out.println(timestamp);
		String nonce = "10086";
	    System.out.println(getSha1(appId+timestamp+nonce+appKey));
		String text = "我是一段测试aaaa";

	/*	PublicKey pair = SecureUtil.generatePublicKey("SM2","0414bb7e9c3914bb65b85079b1dc6e1ca2a0ee04dd9bb55b2f8d31704a6dec0cca695739e128f9c931330e1c0f493be4d56244236434c9d344c4716ba64a4470ba".getBytes("UTF-8"));

		SM2 sm2 = SmUtil.sm2();
		sm2.setPublicKey(pair);
		// 公钥加密，私钥解密
		String encryptStr = sm2.encryptBcd(text, KeyType.PublicKey);*/
		String encryptStr = SM2.sm2Encrypt("\t{\"cardNo\":\"6222081809001166974\"\n" +
				"\t\t,\"personalMandate\": \"1\"\n" +
				"\t\t,\"sceneId\": \"15\"\n" +
				"\t\t,\"certType\": \"01\"\n" +
				"\t\t,\"certNo\": \"420821199410233013\"\n" +
				"\t\t,\"name\": \"余晨\"\n" +
				"\t\t,\"appName\": \"00缺省渠道\"\n" +
				"\t\t,\"ipType\": \"04\"\n" +
				"\t\t,\"sourceIp\": \"173.016.035.002\"\n" +
				"\t}", "0414bb7e9c3914bb65b85079b1dc6e1ca2a0ee04dd9bb55b2f8d31704a6dec0cca695739e128f9c931330e1c0f493be4d56244236434c9d344c4716ba64a4470ba");
		System.out.println(encryptStr);
		//String decryptStr = StrUtil.utf8Str(sm2.decryptFromBcd(encryptStr, KeyType.PrivateKey));

		String res = "BGGPEEeyknPTTQVkpobDUtiKCvpuz4jUPGekiOn8mpBnwAVp/A/zbYWotJuvRgOU0phPslM14M1WDlMWXF/9hofJDJC6Ru6zyYSGCkUHbVwX/w/HgiPml2LZdoNMXieywo08TXWEUTVLoLZC0+8+uYrxft2jTvf4iZZsLb/f08SP8l4Pb0+fnZW1UhjWpJJSDNjV4gjd1JjSKV5INYKiLdFvbGpnvYbzktjgIh+b4rj5Y2ktxetcWLVvEPkGl8ks8LpTlbdBt9UygmT1vi/IiBUMAiSJ//pG6wajtZM6W7WHBhQMQrr5um6xoT5nzUFrBF9ExnozTx90rpwapInzr3YBSFTGg2XqHvKCqwCflfUedJC4VWvcEaCDZ5QA1gFvFBpDiW4l7SS+5J5CawITU/5l9xekg1rqfGGClaxsNU63EZ/bDrKQhx+Wy8fsafWVf2HIYyaVOpLEOvr8eMdK83gK/1XMLWOXRqPCMKAtir2+";
		byte[] decode = Base64.decode(res);
		String decrypt = SM2.decrypt(decode, "00dcced56ee3003f878f9ce42d1d43e691d070e7d554884881b8926ebe1344ef92");
		System.out.println(decrypt);
	}

	private static String getTimeStamp() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        return format.format(date);
    }
}
