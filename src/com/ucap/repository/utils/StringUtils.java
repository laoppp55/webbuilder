package com.ucap.repository.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
	private static MessageDigest digest = null;

	//*********************字符常量***********************//
	public static final String FORWARD_SLASH = "/";
	public static final String NULL = "null";

	/**
	 * 描述: 判断字符串是否为空   作者 lxx 时间：2015-10-10下午03:30:44  
	 * 
	 * @param str
	 *            字符串  
	 * @return boolean
	 */
	public static boolean isNotNull(Object... str) {

		if (str == null) {
			return false;
		}

		for (Object s : str) {
			if (isEmptyObj(s)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 描述: 判断object是否为空  作者 lxx 时间：2015-10-10下午03:30:44  
	 * 
	 * @param o
	 *            字符串  
	 * @return boolean
	 */
	public static boolean isEmptyObj(final Object o) {
		if (o == null || o.toString().trim().length() == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断参数是否为空，NULL与""返回true，反之false
	 * 
	 * @param s
	 * @return boolean
	 */
	public static boolean isEmpty(final String s) {
		if (s == null || s.trim().length() == 0)
			return true;
		return false;
	}

	/**
	 * 判断参数是否为空，NULL与""返回false，反之true
	 * 
	 * @param s
	 * @return boolean
	 */
	public static boolean isNotEmpty(final String s) {
		return !isEmpty(s);
	}

	public static String join(final String... params) {
		StringBuilder sb = new StringBuilder();
		for (String s : params)
			sb.append(s);
		return sb.toString();
	}

	public static final String toHex(byte hash[]) {
		if (hash == null)
			return null;
		StringBuffer buf = new StringBuffer(hash.length * 2);
		int i;

		for (i = 0; i < hash.length; i++) {
			if (((int) hash[i] & 0xff) < 0x10) {
				buf.append("0");
			}
			buf.append(Long.toString((int) hash[i] & 0xff, 16));
		}
		return buf.toString();
	}

	/**
	 * rest 匹配为"_"的数据
	 */
	public static String toHorizontal(final String str) {
		if (isEmpty(str))
			return str;
		if (str.equals("_"))
			return null;
		return str;
	}

	/**
	 * 判断字符串是否为数字组成
	 * 
	 * @param str
	 *            字符串
	 * @return true 数字、 false 非数字
	 */
	public static boolean isNumber(final String str) {
		if (null == str)
			return false;
		String pattern = "^[0-9]*";
		return str.matches(pattern);
	}

	/**
	 * 根据字符串返回Long数组 EG：101,205,303 ==> {101,205,303}
	 * 
	 * @param str
	 *            字符串
	 * @return Long数组
	 */
	public static Long[] getNumberArray(final String str) {

		// 字符串数组
		String sIds[] = null;
		// Long串数组
		Long lIds[] = null;

		// 字符串不为NULL切割字符串
		if (str != null && !"".equals(str)) {

			sIds = str.split(",");
			int length = 0;

			// 确定lIds长度
			for (int i = 0; i < sIds.length; i++) {

				if (isNumber(sIds[i])) {
					length++;
				}
			}

			lIds = new Long[length];

			// 转换Str数组->Long数组
			for (int i = 0; i < sIds.length; i++) {

				if (isNumber(sIds[i])) {
					lIds[i] = Long.parseLong(sIds[i]);
				}
			}

			return lIds;

		} else {
			return new Long[0];
		}

	}

	public static String getStringsValue(String... str) {

		String result = "";

		if (str == null)
			return result;

		for (int i = 0; i < str.length; i++) {
			if (str[i] != null && !str[i].equals("")) {
				String s = str[i].replace("'", "\\'");
				if (i < str.length - 1) {
					result = result + "'" + s + "',";
				} else {
					result = result + "'" + s + "'";
				}
			}
		}

		return result;
	}

	/**
	 * convert String to String array with special phrase, such as",space,dash,omit"
	 * 
	 * @param str
	 * @return String[]
	 */
	public static String[] ConvertString2Array(String str) {
		return ConvertString2Array(str, null);
	}

	public static String[] ConvertString2Array(String str, String specialDash) {
		String[] result = new String[0];
		if (str == null)
			return result;
		String[] ret = null;
		if (specialDash == null) {
			ret = str.split("[" + " ,;|　" + "]");
		} else {
			ret = str.split(specialDash);
		}

		if (ret == null)
			return result;
		List<String> resultList = new ArrayList<String>();
		for (int i = 0; i < ret.length; i++) {
			if (ret[i] != null && !"".equals(ret[i])) {
				resultList.add(ret[i].trim());
			}
		}
		return resultList.toArray(result);
	}

	public static String full2HalfChange(String QJstr) throws UnsupportedEncodingException {
		StringBuffer outStrBuf = new StringBuffer("");
		String Tstr = "";
		byte[] b = null;
		for (int i = 0; i < QJstr.length(); i++) {
			Tstr = QJstr.substring(i, i + 1);
			if (Tstr.equals("　")) {
				outStrBuf.append(" ");
				continue;
			}
			b = Tstr.getBytes("unicode");
			if (b[2] == -1) {
				// 表示全角？
				b[3] = (byte) (b[3] + 32);
				b[2] = 0;
				outStrBuf.append(new String(b, "unicode"));
			} else {
				outStrBuf.append(Tstr);
			}
		} // end for.
		return outStrBuf.toString();
	}

	public static String half2Fullchange(String QJstr) throws UnsupportedEncodingException {
		StringBuffer outStrBuf = new StringBuffer("");
		String Tstr = "";
		byte[] b = null;
		for (int i = 0; i < QJstr.length(); i++) {
			Tstr = QJstr.substring(i, i + 1);
			if (Tstr.equals(" ")) {
				// 半角空格
				outStrBuf.append(Tstr);
				continue;
			}
			b = Tstr.getBytes("unicode");
			if (b[2] == 0) {
				// 半角?
				b[3] = (byte) (b[3] - 32);
				b[2] = -1;
				outStrBuf.append(new String(b, "unicode"));
			} else {
				outStrBuf.append(Tstr);
			}
		}
		return outStrBuf.toString();
	}

	/**
	 * 
	 * @param reg
	 * @param content
	 * @return true 非法 false 合法
	 */
	public static boolean checklegal(String reg, String content) {
		Pattern p = Pattern.compile(reg); // 正则表达式
		Matcher m = p.matcher(content);// 操作的字符串
		return m.find();
	}

	/**
	 * 对原始字符串进行编码转换，如果失败，返回原始的字符串
	 * 
	 * @param s
	 *            原始字符串
	 * @param srcEncoding
	 *            源编码方式
	 * @param destEncoding
	 *            目标编码方式
	 * @return 转换编码后的字符串，失败返回原始字符串
	 */
	public static String getString(String s, String srcEncoding, String destEncoding) {
		try {
			return new String(s.getBytes(srcEncoding), destEncoding);
		} catch (UnsupportedEncodingException e) {
			return s;
		}
	}

	/**
	 * 根据某种编码方式将字节数组转换成字符串
	 * 
	 * @param b
	 *            字节数组
	 * @param encoding
	 *            编码方式
	 * @return 如果encoding不支持，返回一个缺省编码的字符串
	 */
	public static String getString(byte[] b, String encoding) {
		try {
			return new String(b, encoding);
		} catch (UnsupportedEncodingException e) {
			return new String(b);
		}
	}

	/**
	 * @param ip
	 *            ip的字节数组形式
	 * @return 字符串形式的ip
	 */
	public static String getIpStringFromBytes(byte[] ip) {
		StringBuffer sb = new StringBuffer();
		sb.append(ip[0] & 0xFF);
		sb.append('.');
		sb.append(ip[1] & 0xFF);
		sb.append('.');
		sb.append(ip[2] & 0xFF);
		sb.append('.');
		sb.append(ip[3] & 0xFF);
		return sb.toString();
	}

	/**
	 * 获取32位的uuid
	 * 
	 * @return String
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 32);
	}

	/**
	 * 
	 * 描述:格式化小数点位数
	 * 
	 * 作者：lxx 2015-11-9下午04:34:22
	 * 
	 * @param size
	 *            小数点后几位
	 * @param Obejct
	 *            需要格式的数字
	 * @return String
	 */
	public static String formatDouble(int size, Double Obejct) {
		String temp = "#0";

		if (size > 0) {
			String lenght = "";
			for (int i = 0; i < size; i++) {
				lenght = (lenght + "0");
			}
			temp = temp + "." + lenght;
		}
		java.text.DecimalFormat df = new java.text.DecimalFormat(temp);

		return df.format(Obejct);
	}

	/**
	 * 邮箱校验工具类
	 * 
	 * @param email
	 * @return true:验证通过 false:验证失败
	 */
	public static boolean checkEmail(String email) {
		String regex = "\\w+@\\w+\\.[a-z]+(\\.[a-z]+)?";
		return Pattern.matches(regex, email);
	}

	/**
	 * 
	 * 描述:取出末尾多余的0
	 * 
	 * 作者：lxx 2015-12-1上午09:16:04
	 * 
	 * @param number
	 * @return
	 */
	public static String getPrettyNumber(String number) {
		String plainString = BigDecimal.valueOf(Double.parseDouble(number)).stripTrailingZeros().toPlainString();
		if (plainString.equals("0.0")) {
			plainString = "0";
		}
		return plainString;
	}

	/**
	 * 作者：dxj 2018-03-29下午14:23:59
	 * 
	 * @param length
	 * @return
	 */
	public static String getRandomNumber(int length) {
		String allChar = "0123456789abcdefghijklmnopqrstuvwxyz";
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(allChar.charAt(random.nextInt(allChar.length())));
		}
		return sb.toString();
	}

	// 截取数字
	public static String getNumbers(String content) {
		Pattern pattern = Pattern.compile("\\d+");
		Matcher matcher = pattern.matcher(content);
		while (matcher.find()) {
			return matcher.group(0);
		}
		return "";
	}

	/**
	 * 根据id字符串返回id集合
	 * 
	 * @param ids
	 * @return
	 */
	public static List<Integer> splitStrIds(String ids) {
		String[] strIds = ids.split(",");
		List<Integer> intIds = new ArrayList();
		for (String id : strIds) {
			intIds.add(Integer.parseInt(id));
		}
		return intIds;
	}

	public static String createUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	/**
	 * 对字符串中html的特殊字符进行转义
	 * 
	 * @param str
	 * @return
	 */
	public static String replaceHtmlToStr(String str) {
		if (str == null || "null".equals(str)) {
			return "";
		}
		str = str.replace("&ldquo;", "“");
		str = str.replace("&rdquo;", "”");
		str = str.replace("&nbsp;", " ");
		str = str.replace("&", "&amp;");
		str = str.replace("&#39;", "'");
		str = str.replace("&rsquo;", "’");
		str = str.replace("&mdash;", "—");
		str = str.replace("&ndash;", "–");
		return str;
	}
	/*--------------------------------------session hash----------------------------------*/
	public static final String stringMd5(final String s) {
		if (s == null)
			return null;
		synchronized (StringUtils.class) {
			if (digest == null) {
				try {
					digest = MessageDigest.getInstance("MD5");
				} catch (NoSuchAlgorithmException nsae) {
					nsae.getStackTrace();
				}
			}
		}
		synchronized (StringUtils.class) {
			try {
				digest.update(s.getBytes());
			}catch (Exception e){
				e.printStackTrace();
			}
		}
		return toHex(digest.digest());
	}
	/**
	 * 去除内容中所有的样式 保留纯文本内容
	 * 
	 * @param inputString
	 * @return
	 */
	public static String Html2Text(String inputString) {
		String htmlStr = inputString; // 含html标签的字符串
		String textStr = "";
		java.util.regex.Pattern p_script;
		java.util.regex.Matcher m_script;
		java.util.regex.Pattern p_style;
		java.util.regex.Matcher m_style;
		java.util.regex.Pattern p_html;
		java.util.regex.Matcher m_html;
		try {
			String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
																										// }
			String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
																									// }
			String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式

			p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
			m_script = p_script.matcher(htmlStr);
			htmlStr = m_script.replaceAll(""); // 过滤script标签

			p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
			m_style = p_style.matcher(htmlStr);
			htmlStr = m_style.replaceAll(""); // 过滤style标签

			p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
			m_html = p_html.matcher(htmlStr);
			htmlStr = m_html.replaceAll(""); // 过滤html标签

			textStr = htmlStr;

		} catch (Exception e) {
			System.err.println("Html2Text: " + e.getMessage());
		}

		return textStr;// 返回文本字符串
	}

	// 首字母转小写
	public static String toLowerCaseFirstOne(String s) {
		if (Character.isLowerCase(s.charAt(0)))
			return s;
		else
			return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
	}

	// 首字母转大写
	public static String toUpperCaseFirstOne(String s) {
		if (Character.isUpperCase(s.charAt(0)))
			return s;
		else
			return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
	}

	public static void main(String args[]) {
		String urlparam = "common/preview_resource.action?id=69942fb915284b5a8530c12f53ba0922&type=doc&jcrVer=1.0";
		Map<String, String> map = new HashMap<String, String>();
		urlparam = urlparam.replace("?", "&");
		String[] param = urlparam.split("&");
		for (String keyvalue : param) {
			String[] pair = keyvalue.split("=");
			if (pair.length == 2) {
				map.put(pair[0], pair[1]);
			}
		}
		System.out.println(map);
	}

	/**
	 * 将空对象 空值 转换为 空字符串""
	 * 
	 * @return
	 */
	public static String trim(String str) {
		if (str==null||"null".equals(str)) {
			return "";
		}
		return str;
	}
	
	/**  
	 * 判断传入的字符串是否含有汉字。 
	 *   
	 * @param str 内容  
	 * @return    返回判断结果，true  含有 ;false 不含有
	 */
	public static boolean IsHaveChinese(String str) {
		boolean f = false;
		f = !(str.length() == str.getBytes().length);
		return f;

	}

	public static boolean compairList(List<String> inputLibIds,String[] libIds){
		boolean libIdFlag = false;
		for (String inputLibId : inputLibIds){
			for (String appLibId:libIds){
				if (inputLibId.equals(appLibId))
					libIdFlag=true;
			}
			if (!libIdFlag)
				return false;
		}
		return true;
	}

	public static boolean compairNodeList(String nodeId,String[] gatherDirIds){
		boolean nodeIdFlag = false;
		for (String gatherDirId : gatherDirIds){
				if (gatherDirId.equals(nodeId))
					nodeIdFlag=true;
			if (!nodeIdFlag)
				return false;
		}
		return true;
	}

	/**
	 * 判断字符串是否包含在数组中
	 * @param str
	 * @return true 包含 false 不包含
	 */
	public static boolean containsStr(String str,String[] strArray){
		boolean flag = false;
		for (String string : strArray){
			if (string.equals(str))
				flag=true;
		}
		return flag;
	}
	/**
	 * 签名生成算法
	 * @param <String,String> params 请求参数集，所有参数必须已转换为字符串类型
	 * @param secret 签名密钥
	 * @return 签名
	 * @throws IOException
	 */
	public static String getSignature(HashMap<String,String> params, String secret) throws IOException
	{
		// 先将参数以其参数名的字典序升序进行排序
		Map<String, String> sortedParams = new TreeMap<String, String>(params);
		Set<Map.Entry<String, String>> entrys = sortedParams.entrySet();

		// 遍历排序后的字典，将所有参数按"key=value"格式拼接在一起
		StringBuilder basestring = new StringBuilder();
		for (Map.Entry<String, String> param : entrys) {
			basestring.append(param.getKey()).append("=").append(param.getValue());
		}
		basestring.append(secret);

		// 使用MD5对待签名串求签
		byte[] bytes = null;
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			bytes = md5.digest(basestring.toString().getBytes("UTF-8"));
		} catch (GeneralSecurityException ex) {
			throw new IOException(ex);
		}

		// 将MD5输出的二进制结果转换为小写的十六进制
		StringBuilder sign = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			String hex = Integer.toHexString(bytes[i] & 0xFF);
			if (hex.length() == 1) {
				sign.append("0");
			}
			sign.append(hex);
		}
		return sign.toString();
	}
	/**
	 * 
	 * 获取随机N位纯数字
	 * 作者：yinsl
	 * 时间：2019年4月24日 16:40
	 * @param length
	 * @return
	 * 
	 */
	public static String getMathRandomNumber(int length){
		String sources  = "0123456789";
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(sources.charAt(random.nextInt(sources.length())));
		}
		return sb.toString();
	}
}
