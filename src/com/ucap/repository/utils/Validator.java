/**
 * Copyright 2002-2010 the original author or authors.
 */
package com.ucap.repository.utils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 验证字符类
 * 
 * @author Zhang Jianhua
 *
 */
public class Validator {

	private static Pattern p;

	private static Matcher m;

	private static final String validEmail = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

	private static final String validYear = "^(\\d{2}|\\d{4})-(0?[1-9]|1[0-2])-((0?[1-9])|((1|2)[0-9])|30|31)";

	private static final String validTel = "\\d{3}-\\d{8}|\\d{4}-\\d{7}";

	private static final String validPost = "[1-9]\\d{5}(?!\\d)";

	private static final String validModel = "1(0|1|2|3|4|5|6|7|8|9)(\\d){9}";

	private static final String validCard = "\\d{18}|\\d{17}x";

	private static final String validChina = "^[\\u4e00-\\u9fa5]+$";

	private static final String validEnglish = "^[A-Za-z0-9_]+$";
	
	private static final String validEng = "^[a-zA-Z\\s]+$";//  英文名称和空格

	private static final String validNumber = "^[0-9]*$";

	/**
	 *验证Email 例如 xx@xx.xx
	 * 
	 * @param  str 传入的字符串
	 * @return     如果为邮箱的格式则返回true，否则返回false
	 */
	public static boolean isValidEmail(String str) {
		boolean flag = isEqualString(str, validEmail);
		return flag;
	}

	/**
	 *验证年月日 例如 xxxx-xx-xx 或者 xx-xx-xx
	 *
	 * @param  str 传入的字符串
	 * @return     如果满足一年中的月份返回true，否则返回false
	 */
	public static boolean isValidYear(String str) {
		boolean flag = isEqualString(str, validYear);
		return flag;
	}

	/**
	 *验证电话  例如 xxx-xxxxxxxx 或者xxxx-xxxxxxx
	 * 
	 * @param  str 传入的字符串
	 * @return     如果横线前为三位数字，横线后8为数字则为true，或者横线前4位数字，横线后7位数字
	 *             则为true，其余情况均为false
	 */
	public static boolean isValidTel(String str) {
		boolean flag = isEqualString(str, validTel);
		return flag;
	}

	/**
	 *验证邮编 例如 000000 6位数字
	 * 
	 * @param  str 传入的字符串
	 * @return     如果是6为数字则为true，否则为false
	 */
	public static boolean isValidPost(String str) {
		boolean flag = isEqualString(str, validPost);
		return flag;
	}

	/**
	 *判断两个字符串是否相等
	 * 
	 * @param  str 传入的字符串
	 * @return     如果两个字符串相等则为true，否则为false 
	 * //此方法持续占用过高CPU by weiminggui date 2014-1-22
	 */
	public static boolean isEqualString(String str, String val) {
		boolean flag = false;
		try {
			/*if (isNotNull(str)) {
				if(val!=null){
					flag = str.equals(val);
				}
				//p = Pattern.compile(val);// 设置比较模式 
				//m = p.matcher(str);
				//flag = m.matches();
			}*/
		    
		  p = Pattern.compile(val);// 设置比较模式 
          m = p.matcher(str);
          flag = m.matches();
		} catch (Exception e) {
		}
		return flag;
	}

	/**
	 *移动电话   1xxxxxxxxxx 11位数字
	 * 
	 * @param  str 传入的字符串
	 * @return     如果str满足手机的规则11位，并以1开头的则为true，否则返回false
	 */
	public static boolean isValidModel(String str) {
		boolean flag = isEqualString(str, validModel);
		return flag;
	}

	/**
	 *验证身份证   18位数字或者17位+x
	 * 
	 * @param  str 传入的字符串
	 * @return     如果str是身份号则为true，否则为false 
	 */
	public static boolean isValidIDcard(String str) {
		boolean flag = isEqualString(str, validCard);
		return flag;
	}

	/**
	 *中文匹配
	 * 
	 * @param  str 传入的字符串
	 * @return     如果str是中文则为true，否则为false 
	 */
	public static boolean isValidChinese(String str) {
		Pattern p = Pattern.compile(validChina);
		 Matcher m = p.matcher(str);
		 if (m.find()) {
		  return true;
		 }
		 return false;
	}

	/**
	 *英文与数字组合
	 * 
	 * @param  str 传入的字符串
	 * @return     如果str是英文玉数字组合则为true，否则为false
	 */
	public static boolean isValidEnglishOrValNumber(String str) {
		boolean flag = isEqualString(str, validEnglish);
		return flag;
	}

	/**
	 *只能输入数字
	 * 
	 * @param  str 传入的字符串
	 * @return     如果str为数字则为true，否则为false 
	 */
	public static boolean isValidNumber(String str) {
		boolean flag = isEqualString(str, validNumber);
		return flag;
	}

	/**
	 * 限定字符串的最大位数
	 * 
	 * @param  str 输入的字符串
	 * @param  n   限定的位数
	 * @return     如果n等于str数字的位数则返回true，否则返回false
	 */
	public static boolean isValidMaxNumber(String str, int n) {
		boolean flag = false;
		try {
			if (isNotNull(str)) {
				String regEx = "^\\d{" + n + "}$";
				p = Pattern.compile(regEx);
				m = p.matcher(str);
				flag = m.matches();
			}
		} catch (Exception e) {
		}
		return flag;
	}

	/**
	 * 限定float    小数点后的位数
	 * 
	 * @param  str float 字符串
	 * @param  n   限定的位数
	 * @return     如果n等于str小数点后的位数则返回true，否则返回false
	 */
	public static boolean isValidFloat(String str, int n) {
		boolean flag = false;
		try {
			if (isNotNull(str)) {
				String regEx = "^[0-9]+(.[0-9]{" + n + "})?$";
				p = Pattern.compile(regEx);
				m = p.matcher(str);
				flag = m.matches();
			}
		} catch (Exception e) {
		}
		return flag;
	}

	/**
	 *上传图片的格式验证
	 * 
	 * @param  str 传入的字符串
	 * @return     如果str等于gif，jpg，bmp中的任意一个为true，否则为false
	 */
	public static boolean isValidUploadPhoto(String str) {
		boolean flag = false;
		try {
			if (isNotNull(str)) {
				int cc = str.length();
				str = str.substring(cc - 4);
				str = str.toLowerCase();
				String regEx = ".(gif|jpg|bmp)$";
				p = Pattern.compile(regEx);// 设置比较模式 
				m = p.matcher(str);
				flag = m.matches();
			}
		} catch (Exception e) {
		}
		return flag;
	}

	/**
	 *上传视频的格式验证
	 * 
	 * @param  str 传入的字符串
	 * @return     如果str等于avi，mpeg，wmv，rm，rmvb，mpg，wmp，flv里的任意一个为true，否则为false
	 */
	public static boolean isValidUploadVideo(String str) {
		boolean flag = false;
		try {
			if (isNotNull(str)) {
				int cc = str.lastIndexOf(".");
				int count = str.length();
				str = str.substring(count - (count - cc));
				str = str.toLowerCase();
				String regEx = ".(avi|mpeg|wmv|rm|rmvb|mpg|wmp|flv)$";
				p = Pattern.compile(regEx);// 设置比较模式 
				m = p.matcher(str);
				flag = m.matches();
			}
		} catch (Exception e) {
		}
		return flag;
	}

	/**
	 *判断单个字符否是字母
	 * 
	 * @param  c 传入的字符
	 * @return   如果c是字母返回true，否则返回false
	 */
	public static boolean isChar(char c) {
		return Character.isLetter(c);
	}

	/**
	 *判断字符串的字符是字母
	 * 
	 * @param  str 传入的字符串
	 * @return     如果str是字母返回true，否则返回false
	 */
	public static boolean isChar(String str) {
		Pattern p = Pattern.compile(validEng);
		 Matcher m = p.matcher(str);
		 if (m.find()) {
		  return true;
		 }
		 return false;
	}

	/**
	 *判断单个字符是数字
	 * 
	 * @param  c 传入的字符
	 * @return   如果c是数字则返回true，否则返回false
	 */
	public static boolean isDigit(char c) {
		int x = (int) c;

		if ((x >= 48) && (x <= 57)) {
			return true;
		}

		return false;
	}

	/**
	 *判断字符串中是否都是数字
	 * 
	 * @param  str 传入的字符串
	 * @return     如果str为数字返回true，非数字返回false 
	 */
	public static boolean isDigit(String str) {
		if (isNull(str)) {
			return false;
		}

		char[] c = str.toCharArray();

		for (int i = 0; i < c.length; i++) {
			if (!isDigit(c[i])) {
				return false;
			}
		}

		return true;
	}

	/**
	 *判断对象数组是否为空
	 * 
	 * @param  object 对象数组
	 * @return        如果object为空返回true，不为空返回false 
	 */
	public static boolean isNull(Object[] object) {
		if (null == object || object.length == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 *判断一个字符串是否为空
	 * 
	 * @param  str 传入的字符串
	 * @return     如果str为空返回true，不为空返回false 
	 */
	public static boolean isNull(String str) {
		if (null == str || str.trim().isEmpty() || str.trim().equals(StringUtils.NULL)) {
			return true;
		}
		return false;
	}

	/**
	 *判断字符串不能为空
	 * 
	 * @param  str 传入的字符串
	 * @return     如果str不为空返回true，为空返回false   
	 */
	public static boolean isNotNull(String str) {
		return !isNull(str);
	}

	/**
	 *判断对象是否为空
	 * 
	 * @param  obj 传入的对象
	 * @return     如果obj为空返回true，不为空返回false  
	 */
	public static boolean isEmpty(Object obj) {
		return obj == null;
	}

	/**
	 *判断一个list集合是否为空
	 * 
	 * @param  list    传入的list
	 * @return         如果list为空或者长度为0返回true，不为空返回false  
	 */
	public static boolean isEmpty(List list) { //
		return list == null || list.size() == 0;
	}
	
	/**
	 *判断一个Map集合是否为空
	 * 
	 * @param  map    传入的map
	 * @return         如果map为空或者长度为0返回true，不为空返回false  
	 */
	public static boolean isEmpty(Map map) { //
		return map == null || map.size() == 0;
	}
	/**
	 *判断一个list集合是否为空
	 * 
	 * @param  collection 传入的集合
	 * @return            如果collection为空或者长度为0返回true，不为空返回false  
	 */
	public static boolean isEmpty(Collection collection) { //
		return collection == null || collection.isEmpty();
	}

	/**
	 *获取字符串字节长度
	 * 
	 * @param  str 传入的字符串
	 * @return     如果字符串为空返回长度为0，否则返回字符串的字节长度 
	 */
	public static int getStrByteLength(String str) {
		if (isNull(str)) {
			return 0;
		} else {
			return str.getBytes().length;
		}
	}

	/**
	 * 获取数据库对应的验证串
	 */
	public static String getValidationQuery(String driverClassName) {
		String result = "";
		if (driverClassName.indexOf("OracleDriver") != -1) {//orcale
			result = "select 1 from dual";
		} else if (driverClassName.indexOf("DB2Driver") != -1) {//DB2
			result = "SELECT 1 FROM SYSIBM.SYSDUMMY1";
		} else if ("com.mysql.jdbc.Driver".equalsIgnoreCase(driverClassName)) {//mysql
			result = "select 1 from dual where 1=1";
		} else if ("com.kingbase.Driver".equals(driverClassName)) {//人大金仓
			result = "select 1 from dual";
		} else {//ms sql
			result = "SELECT 1";
		}
		return result;
	}
	
	public static boolean isValidSeachKeyWordsPrameValue(String value) {
		if (Validator.isNull(value))
			return true;
		// 支持空格和,
		value = value.replaceAll(" ", "").replaceAll(" ", "")
				.replaceAll(",", "");
		String reg = "^[\\u4E00-\\u9FA5\\uF900-\\uFA2D\\w]{1,200}$";
		Pattern pattern = Pattern.compile(reg);
		return pattern.matcher(value).matches();
	}
	/**
     * 判断是否含有特殊字符
     *
     * @param str
     * @return true为包含，false为不包含
     */
    public static boolean isSpecialChar(String str) {
        String regEx = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.find();
    }
	public static void main(String[] args) {
		
		
		boolean b = isValidChinese("想ss");
		System.out.println(b);
		
		
    }
}
