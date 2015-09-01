package com.itheima.mobilesafe.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 
 * ���������ã����ڴ��е�����ת�����ַ���
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
