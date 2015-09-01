package com.itheima.mobilesafe.engine;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Handler;
import android.os.Message;

import com.itheima.mobilesafe.bean.MoblieInfo;
import com.itheima.mobilesafe.ui.SplashActivity;
import com.itheima.mobilesafe.utils.StreamUtils;
import com.itheima.mobilesafe.utils.UiUtils;

/**
 * �����������: �������������������ҵ���߼���Ȼ��ͨ��handler����״̬�뵽SplashActivity��
 * ��SplashActivity���������½���
 * 
 * 
 */
public class MobileService {
	private final static int EXCEPTION = 0;
	private final static int SHOW_UPDATEDIALGO = 1;
	private final static int LOAD_MAINUI = 2;

	public static Map getCurrentVision(Activity context) {
		// ��ȡ��������
		Map<String, Object> map = new HashMap<String, Object>();
		PackageManager pm = context.getPackageManager();
		try {
			PackageInfo info = pm.getPackageInfo("com.example.day13_01", 0);
			String versionName = info.versionName;
			int versionCode = info.versionCode;
			map.put("versionName", versionName);
			map.put("versionCode", versionCode);

			return map;

		} catch (NameNotFoundException e) {

			e.printStackTrace();
			UiUtils.showToast(context, "����008,·��������,����ϵ�ͷ�008");
		}
		return null;

	}

	public static MoblieInfo getServiceInfo(final Activity context,
			final Handler handler) {

		final MoblieInfo mb = new MoblieInfo();
		final Message msg = Message.obtain();

		new Thread() {

			private long startTime;
			private long endTime;

			public void run() {

				URL url;
				try {

					startTime = System.currentTimeMillis();
					String path = "http://192.168.1.107:8080/moblieVision.json";
					url = new URL(path);
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.setConnectTimeout(3000);
					conn.setRequestMethod("GET");

					if (conn.getResponseCode() == conn.HTTP_OK) {

						InputStream in = conn.getInputStream();
						String json = StreamUtils.getString(in);

						JSONObject jsonobj = new JSONObject(json);
						int versionCode = jsonobj.getInt("versionCode");
						String versionName = jsonobj.getString("versionName");
						String description = jsonobj.getString("description");
						String apkurl = jsonobj.getString("apkurl");
						mb.versionCode = versionCode;
						mb.versionName = versionName;
						mb.description = description;
						mb.apkurl = apkurl;
						// �жϱ���Ӧ�ð汾�źͷ�����Ӧ�ð汾���Ƿ�һ��
						int serviceVersionCode = mb.versionCode;
						Map map = MobileService.getCurrentVision(context);
						int clientVersionCode = (Integer) map
								.get("versionCode");
						// �汾��һ�£���ʾ���¡��汾һ�£�����������档
						if (clientVersionCode != serviceVersionCode) {
							Message msg = Message.obtain();
							msg.obj = mb;
							msg.what = SHOW_UPDATEDIALGO;
							handler.sendMessage(msg);

						} else {
							System.out.println("XXXXXXXXXXXXXXXXXXXXXXXX");

							msg.what = LOAD_MAINUI;

						}

					} else {
						UiUtils.showToast(context, "����007,������״̬�����,����ϵ�ͷ�007");
					}

				} catch (MalformedURLException e) {

					e.printStackTrace();
					UiUtils.showToast(context, "����001,url·������ȷ,����ϵ�ͷ�001");
					msg.what = LOAD_MAINUI;

				} catch (IOException e) {

					e.printStackTrace();
					UiUtils.showToast(context, "����002,IO�쳣,����ϵ�ͷ�002");
					msg.what = LOAD_MAINUI;

				} catch (JSONException e) {

					e.printStackTrace();
					UiUtils.showToast(context, "����003,url·������ȷ,����ϵ�ͷ�003");
					msg.what = LOAD_MAINUI;
				} finally {

					endTime = System.currentTimeMillis();
					long runTime = endTime - startTime;
					if (runTime < 3000) {
						try {
							Thread.sleep(3000 - runTime);
						} catch (InterruptedException e) {

							e.printStackTrace();
						}
					}

					handler.sendMessage(msg);
				}

			}

		}.start();

		return mb;

	}

}
