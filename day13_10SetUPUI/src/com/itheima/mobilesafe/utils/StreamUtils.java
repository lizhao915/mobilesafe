package com.itheima.mobilesafe.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 
 * 这个类的作用：将内存中的数据转换成字符串
 * 
 */
public class StreamUtils {

	public static String getString(InputStream in) throws IOException {

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] b = new byte[1024];
		int len;
		while ((len = in.read(b)) != -1) {
			bos.write(b);

		}
		in.close();
		bos.close();
		
		

		return bos.toString();

	}

}
