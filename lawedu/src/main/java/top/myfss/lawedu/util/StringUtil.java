package top.myfss.lawedu.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

	/**
	 * 大陆手机号判断
	 * @param mobiles
	 * @return
	 */
	public boolean isMobileNo(String mobiles) {
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9])|(19[0-9])|(17[0-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	/**
	 * 小写md5 编码
	 * @param str
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static String parseStrToMd5L32(String str) {
		String reStr = null;
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] bytes = md5.digest(str.getBytes());
			StringBuffer stringBuffer = new StringBuffer();
			for (byte b : bytes) {
				int bt = b & 0xff;
				if (bt < 16) {
					stringBuffer.append(0);
				}
				stringBuffer.append(Integer.toHexString(bt));
			}
			reStr = stringBuffer.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return reStr;
	}

	/**
	 * 将文件全部读取为String
	 * @param file
	 * @return
	 */
	public String readFileToString(File file) {
		Long filelength = file.length();// 获取文件长度
		byte[] filecontent = new byte[filelength.intValue()];
		try {
			FileInputStream in = new FileInputStream(file);
			in.read(filecontent);
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new String(filecontent);// 返回文件内容,默认编码
	}
	
	/**
	 * List<Map<String, Object>>特定字段转换为指定分隔符字符串
	 * @param list 输入列表
	 * @param field 字段名
	 * @param separator 分隔符
	 * @return
	 */
	public String convertListMapToString(List<Map<String, Object>> list,String field,String separator) {
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> temp = list.get(i);
			if(temp.containsKey(field)) {
				if(i == (list.size() -1)){
					result.append(temp.get(field));
					break;
				}
				result.append(temp.get(field) + separator);
			}
		}
		return result.toString();
	}

	/**
	 * 获取sha1编码
	 * @param str
	 * @return
	 */
	public static String getSha1(String str){
		/*if (null == str || 0 == str.length()){
			return null;
		}
		char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f'};
		try {
			MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
			mdTemp.update(str.getBytes("UTF-8"));

			byte[] md = mdTemp.digest();
			int j = md.length;
			char[] buf = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
				buf[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(buf);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";*/
		try {
			MessageDigest digest = MessageDigest
					.getInstance("SHA");
			digest.update(str.getBytes());
			byte messageDigest[] = digest.digest();
			// Create Hex String
			StringBuffer hexString = new StringBuffer();
			// 字节数组转换为 十六进制 数
			for (int i = 0; i < messageDigest.length; i++) {
				String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
				if (shaHex.length() < 2) {
					hexString.append(0);
				}
				hexString.append(shaHex);
			}
			return hexString.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static void main(String[] args) {
		StringUtil stringUtil = new StringUtil();
		System.out.println(stringUtil.getSha1("admin"));
	}

}
