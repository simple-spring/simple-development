package com.chexin.simple.development.core.utils;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * httpclient util
 */
public class HttpRequestUtil {
	private static final Logger logger = LogManager.getLogger(HttpRequestUtil.class);

	/**
	 * 读取参数
	 * @param request
	 * @return
	 */
	public static String readStringFromRequestBody(HttpServletRequest request) throws IOException {
		InputStream stream = request.getInputStream();
		if(stream == null){
			return null;
		}
		StringBuffer sb = new StringBuffer();
		char[] buf = new char[1024];
		int len = -1;
		try {
			InputStreamReader reader = new InputStreamReader(stream, "UTF-8");
			while ((len = reader.read(buf)) != -1) {
				sb.append(new String(buf, 0, len));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(stream);
		}
		return sb.toString();
	}


	/**
	 * 读取参数
	 * @param request
	 * @return
	 */
	public static String readStringFromRequestBodyNoClose(HttpServletRequest request) throws IOException {
		InputStream stream = request.getInputStream();
		if(stream == null){
			return null;
		}
		StringBuffer sb = new StringBuffer();
		char[] buf = new char[1024];
		int len = -1;
		try {
			InputStreamReader reader = new InputStreamReader(stream, "UTF-8");
			while ((len = reader.read(buf)) != -1) {
				sb.append(new String(buf, 0, len));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

}
