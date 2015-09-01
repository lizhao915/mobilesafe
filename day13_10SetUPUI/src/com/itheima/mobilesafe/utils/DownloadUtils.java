package com.itheima.mobilesafe.utils;

import java.io.File;

import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

public class DownloadUtils {

	public static String progress;

	// ���̶߳ϵ�����
	public static void download(String url) {

		HttpUtils httpUtils = new HttpUtils();
		// url:ԭ�ļ���·�� target:Ŀ���ļ�·�� callback ���سɹ� ʧ�� ������ �ص�����

		httpUtils.download(url, "/sdcard/li.apk",
				new RequestCallBack<File>() {

					public void onLoading(long total, long current,
							boolean isUploading) {
						super.onLoading(total, current, isUploading);
						progress = (current / total) * 100 + "%";

					}

					public void onSuccess(ResponseInfo<File> arg0) {

					}

					public void onFailure(HttpException arg0, String arg1) {
						// �������ʧ�ܣ���ӡʧ����Ϣ
						System.out.println(arg1);
						arg0.printStackTrace();

					}
				});
	}

}
