package com.itheima.mobilesafe.utils;

import java.io.File;

import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

public class DownloadUtils {

	public static String progress;

	// 多线程断点下载
	public static void download(String url) {

		HttpUtils httpUtils = new HttpUtils();
		// url:原文件的路径 target:目的文件路径 callback 下载成功 失败 下载中 回调方法

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
						// 如果下载失败，打印失败信息
						System.out.println(arg1);
						arg0.printStackTrace();

					}
				});
	}

}
