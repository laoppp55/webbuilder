package com.ucap.repository.utils;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.Key;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


/**
 * @Description TODO(描述) AES: Advanced Encryption Standard 高级加密标准
 * @author xiaoG 2018年6月4日上午10:47:09
 */
public class DESUtil {
	
	private static Logger logger = LoggerFactory.getLogger(DESUtil.class);

	/**
     * 加密算法AES
     */
    public static final String KEY_ALGORITHM = "DES";
	/**
	 * 分隔符
	 */
	private static final String  AT ="&";
    
    /** 默认密钥 */
    private static String STR_DEFAULT_KEY = "seeyonssokey";

	public static BASE64Decoder base64Decoder= new BASE64Decoder();
	public static final BASE64Encoder BASE64_ENCODER = new BASE64Encoder();
	/**
	 * @Description TODO(描述)  解密
	 * @param str
	 * @param pw
	 * @return
	 * @throws Exception
	 * @author xiaoG 2018年6月4日上午10:48:23
	 */
	public static String decode(String str,String pw) throws Exception{
		
		byte[] bs = parseHexStr2Byte(str);
		KeyGenerator generator  = KeyGenerator.getInstance(KEY_ALGORITHM);
		generator.init(128, new SecureRandom(pw.getBytes()));
		SecretKey secretKey = generator.generateKey();
		byte[] keyByte = secretKey.getEncoded();
		SecretKeySpec keySpec = new SecretKeySpec(keyByte, KEY_ALGORITHM);
		Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, keySpec);
		byte[] bs2 = cipher.doFinal(bs);
		str = new String(bs2,"UTF-8");
		logger.info("解密后的结果为："+str);
		return str;
	}
	
	
	/**
	 * @Description TODO(描述)  加密
	 * @param content
	 * @param pw
	 * @return
	 * @throws Exception
	 * @author xiaoG 2018年6月4日上午10:48:34
	 */
	public static String encode(String content,String pw) throws Exception{
		KeyGenerator generator  = KeyGenerator.getInstance(KEY_ALGORITHM);
		generator.init(128, new SecureRandom(pw.getBytes()));
		SecretKey secretKey = generator.generateKey();
		byte[] keyByte = secretKey.getEncoded();
		// 生成秘钥  ， byte[]: 秘钥，固定长度，需要特殊生成
		SecretKeySpec key = new SecretKeySpec(keyByte, KEY_ALGORITHM);
		// 获得加密
		Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
		//对加密器用秘钥进行初始化,key:秘钥
		cipher.init(Cipher.ENCRYPT_MODE,key);
		byte[] bs = cipher.doFinal(content.getBytes());
		
		String str =parseByte2HexStr(bs);
		
		logger.info("加密后的结果为："+str);
		return str;
	}
	
    /**
     * @Description TODO(描述)  将二进制转换成16进制 
     * @param buf
     * @return
     * @author xiaoG 2018年6月4日上午11:12:29
     */
    public static String parseByte2HexStr(byte buf[]){  
        StringBuffer sb = new StringBuffer();  
        for(int i = 0; i < buf.length; i++){  
            String hex = Integer.toHexString(buf[i] & 0xFF);  
            if (hex.length() == 1) {  
                hex = '0' + hex;  
            }  
            sb.append(hex.toUpperCase());  
        }  
        return sb.toString();  
    }  
    
    
    /**
     * 加密字符串
     * @param strIn 需加密的字符串
     * @return 加密后的字符串
     * @throws Exception
     */
    public String encrypt(String strIn) throws Exception {
    	Key key = getKey(DESUtil.STR_DEFAULT_KEY.getBytes("utf-8"));
		Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, key);
        return byteArr2HexStr(cipher.doFinal(strIn.getBytes()));
    }

    /**
     * 解密字符串
     * @param strIn 需解密的字符串
     * @return 解密后的字符串
     * @throws Exception
     */
    public String decrypt(String strIn) throws Exception {
    	Key key = getKey(DESUtil.STR_DEFAULT_KEY.getBytes("utf-8"));
		Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, key);
        return new String(cipher.doFinal(hexStr2ByteArr(strIn)));
    }

    /**
     * 将 byte 数组转换为表示 16 进制值的字符串， 如： byte[]{8,18} 转换为： 0813 ， 和 public static
     * byte[] hexStr2ByteArr(String strIn) 互为可逆的转换过程
     * @param arrB 需要转换的 byte 数组
     * @return 转换后的字符串
     * @throws Exception 本方法不处理任何异常，所有异常全部抛出
     */
    public static String byteArr2HexStr(byte[] arrB) throws Exception {
        int iLen = arrB.length;
        // 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
        StringBuffer sb = new StringBuffer(iLen * 2);
        for(int i = 0; i < iLen; i++) {
            int intTmp = arrB[i];
            // 把负数转换为正数
            while(intTmp < 0) {
                intTmp = intTmp + 256;
            }
            // 小于0F的数需要在前面补0
            if(intTmp < 16) {
                sb.append("0");
            }
            sb.append(Integer.toString(intTmp, 16));
        }
        return sb.toString();
    }

    /**
     * 将 表 示 16 进 制 值 的 字 符 串 转 换 为 byte 数 组 ， 和 public static String
     * byteArr2HexStr(byte[] arrB) 互为可逆的转换过程
     * @param strIn 需要转换的字符串
     * @return 转换后的 byte 数组
     * @throws Exception 本方法不处理任何异常，所有异常全部抛出
     * @author <a href="mailto:leo841001@163.com">LiGuoQing</a>
     */
    public static byte[] hexStr2ByteArr(String strIn) throws Exception {
        byte[] arrB = strIn.getBytes();
        int iLen = arrB.length;
        // 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
        byte[] arrOut = new byte[iLen / 2];
        for(int i = 0; i < iLen; i = i + 2) {
            String strTmp = new String(arrB, i, 2);
            arrOut[i / 2] = (byte)Integer.parseInt(strTmp, 16);
        }
        return arrOut;
    }

    /**
     * 从指定字符串生成密钥，密钥所需的字节数组长度为 8 位 不足 8 位时后面补 0 ， 超出 8 位只取前 8 位
     * @param arrBTmp 构成该字符串的字节数组
     * @return 生成的密钥
     * @throws Exception
     */
    private static Key getKey(byte[] arrBTmp) throws Exception {
        // 创建一个空的8位字节数组（默认值为0）
        byte[] arrB = new byte[8];
        // 将原始字节数组转换为8位
        for(int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
            arrB[i] = arrBTmp[i];
        }
        // 生成密钥
        Key key = new SecretKeySpec(arrB, KEY_ALGORITHM);
        return key;
    }

	public static String encryptNew(String strIn, String secretkey)
			throws Exception {
		Key key = getKey(secretkey.getBytes("utf-8"));
		Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, key);
		return byteArr2HexStr(cipher.doFinal(strIn.getBytes("utf-8")));
	}
	public static String encrypt(String strIn, String secretkey)
			throws Exception {
		return buildData(strIn,secretkey);
	}

	public static String decryptNew(String strIn, String secretkey)
			throws Exception {
		Key key = getKey(secretkey.getBytes("utf-8"));
		Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] param = cipher.doFinal(hexStr2ByteArr(strIn));
		return new String(param, "utf-8");
	}
	public static String decrypt(String strIn, String secretkey)
			throws Exception {
		Map<String, String> stringMap = decodeData(strIn, secretkey);
		String decodeData = stringMap.get("decodeData");
		String flag = stringMap.get("flag");
		if(flag.equals("0")){//解密成功
			return decodeData;
		}
		return null;
	}

    /**
     * @Description TODO(描述)将16进制转换为二进制 
     * @param hexStr
     * @return
     * @author xiaoG 2018年6月4日上午11:12:42
     */
    public static byte[] parseHexStr2Byte(String hexStr){  
        if(hexStr.length() < 1)  
            return null;  
        byte[] result = new byte[hexStr.length()/2];  
        for (int i = 0;i< hexStr.length()/2; i++) {  
            int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);  
            int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);  
            result[i] = (byte) (high * 16 + low);  
        }  
        return result;  
    }  
    
    
   /* public static void main(String[] args) throws MalformedURLException {
    	//"{   "dirId": "0b41d6b9e808476b8f7a5f6743b6dc46" }"
//      输出Ascii格式
        Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder()
                .withMarkupLanguage(MarkupLanguage.ASCIIDOC)
                .build();
        Swagger2MarkupConverter.from(new URL("http://192.168.2.178:7112/v2/api-docs"))//http://192.168.2.178:7112/v2/api-docs
                .withConfig(config)
                .build()
                .toFile(Paths.get("src/docs/asciidoc/generated/all"));
	}*/
    
    /**
	 * 解密客户端提交的数据
	 * @param data
	 *        待解密的数据
	 * @param secretKey
	 * 		       密钥
	 * @return Map&lt;String,String&gt;<br/>
	 *     flag:编码；
	 *     <ul>
	 *     <li>0:解密成功！</li>
	 *     <li>-1:data参数为空！</li>
	 *     <li>-2:secretKey参数为空！</li>
	 *     <li>-3:数据格式不正确！</li>
	 *     <li>-4:生成的摘要与响应结果中的摘要是否一致！</li>
	 *     </ul><br/>
	 *     msg:描述信息。
	 *     <ul>
	 *     <li>0:解密成功！</li>
	 *     <li>-1:data参数为空！</li>
	 *     <li>-2:secretKey参数为空！</li>
	 *     <li>-3:数据格式不正确！</li>
	 *     <li>-4:生成的摘要与响应结果中的摘要是否一致！</li>
	 *     </ul><br/>
	 *     digestOfRequest:请求中的摘要；<br/>
	 *     digestOfAgent:服务器生成的摘要；<br/>
	 *     decodeData:解密后的请求数据。<br/>
	 * @throws Exception
	 */
	public static Map<String,String> decodeData(String data,String secretKey) throws Exception{
		Map<String,String> map= new HashMap<String, String>();
		data = URLDecoder.decode(data);
		if(StringUtils.isBlank(data)){
			map.put("flag", "-1");
			map.put("msg", "data参数为空！");
			return map;
		}
		if(StringUtils.isBlank(secretKey)){
			map.put("flag", "-2");
			map.put("msg", "secretKey为空！");
			return map;
		}
		//拆分摘要和结果信息
		String[] digestAndResult = data.split(AT);
		if(digestAndResult==null||digestAndResult.length==0){
			map.put("flag", "-3");
			map.put("msg", "数据格式不正确！");
			return map;
		}
		String digestOfServer = digestAndResult[0];
		String result = digestAndResult[1];
		//解密响应结果
		String afterDESResult = DESUtil.decryptNew(result, secretKey);
		String afterBase64Decode =new String(base64Decoder.decodeBuffer(afterDESResult),"UTF-8");
		MessageDigest sd = MessageDigest.getInstance("MD5");
		sd.update(afterBase64Decode.getBytes("UTF-8"));
		String digestOfAgent = DESUtil.byteArr2HexStr(sd.digest());
		// 比较生成的摘要与响应结果中的摘要是否一致
		if(!digestOfServer.equals(digestOfAgent)){
			map.put("flag", "-4");
			map.put("msg", "生成的摘要与响应结果中的摘要是否一致！");
			return map;
		}
		map.put("flag", "0");
		map.put("msg", "解密成功！");
		map.put("digestOfRequest", digestOfServer);
		map.put("digestOfAgent", digestOfAgent);
		map.put("decodeData", afterBase64Decode);
		return map;
	}

	public static String buildData(String data,String secretKey) throws Exception {
		// 2、根据整串生成摘要签名
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(data.getBytes("UTF-8"));
		byte[] digestByte = md.digest();
		// 2-1） 将生成的摘要转成16进制数表示
		String digest = DESUtil.byteArr2HexStr(digestByte);
		// 3、对整串进行加密处理Base64
		// 3-1）对整串做base64编码（去编码化）
		String base64En = new String(BASE64_ENCODER.encode(data.getBytes("UTF-8")));
		// 3-3） 对data进行加密处理 并将结果转换成16进制表示
		String dataAfterDESEn= DESUtil.encryptNew(base64En,secretKey);
		// 4、将摘要签名和加密后的参数拼接成最终提交IDS的参数
		String finalData = URLEncoder.encode(digest + AT + dataAfterDESEn, "UTF-8");
		return finalData;
	}
    
    
    
}
