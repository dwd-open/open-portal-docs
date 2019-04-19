package com.dianwoda.open.api.gw.util;

import com.dianwoba.universe.commons.utils.StringUtils;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * create by 山人二小 on 2018/11/29
 */
public class OpenApiSignUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(OpenApiSignUtil.class);

	/**
	 * 生成签名
	 *
	 * @param paramMap    系统级参数
	 * @param bodyContent 应用级参数
	 * @param secret      秘钥
	 * @return 签名
	 */
	public static String sign(Map <String, String> paramMap, String bodyContent, String secret) {
		paramMap.remove("sign");
		StringBuilder sb = new StringBuilder();
		List <String> paramNames = new ArrayList <>();
		paramMap.forEach((k, v) -> paramNames.add(k));
		// 对key值按字典序
		Collections.sort(paramNames);
		// 按参数名1=参数值1&参数名2=参数值2的格式拼接得到新字符串A
		paramNames.forEach(k -> sb.append(String.valueOf(k)).append("=").append(paramMap.get(k)).append("&"));
		// 字符串A拼接&body=,再拼接body中的字符串内容
		sb.append("body=").append(StringUtils.isBlank(bodyContent) ? "" : bodyContent).append("&secret=").append(secret);
		LOGGER.info("参与签名内容：{}", sb);
		return getSHA1Str(sb.toString());
	}

	/**
	 * 利用Apache的工具类实现SHA-1加密
	 *
	 * @param signContent 需要加密的内容
	 * @return 密文
	 */
	private static String getSHA1Str(String signContent) {
		MessageDigest messageDigest;
		String encodeStr = "";
		try {
			messageDigest = MessageDigest.getInstance("SHA-1");
			byte[] hash = messageDigest.digest(signContent.getBytes("UTF-8"));
			encodeStr = Hex.encodeHexString(hash);
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			LOGGER.error("生成签名失败");
		}
		return encodeStr;
	}
}
