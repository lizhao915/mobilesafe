package com.itheima.mobilesafe.ui;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import org.apache.http.HttpConnection;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.example.day13_01.R;
import com.itheima.mobilesafe.MainActivity;
import com.itheima.mobilesafe.bean.MoblieInfo;
import com.itheima.mobilesafe.engine.MobileService;
import com.itheima.mobilesafe.utils.DownloadUtils;
import com.itheima.mobilesafe.utils.StreamUtils;
import com.itheima.mobilesafe.utils.UiUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * ��д��Сд��ctrl+shift+x Сд���д:ctrl+shift+y ���������ã� ���ݲ��õ�״̬��������
 * 
 * 
 */
public class SplashActivity extends Activity {

	private TextView tv_versionName;
	private TextView tv_progress;

	private final int EXCEPTION = 0;
	private final int SHOW_UPDATEDIALGO = 1;
	private final int LOAD_MAINUI = 2;

	private Handler handler = new Handler() {

		public void handleMessage(android.os.Message msg) {

			switch (msg.what) {
			case EXCEPTION:
				Toast.makeText(SplashActivity.this, "�����쳣", 0);
				break;

			case SHOW_UPDATEDIALGO:

				MoblieInfo mb = (MoblieInfo) msg.obj;

				AlertDialog.Builder builder = new Builder(SplashActivity.this);
				// builder.setCancelable(false);

				builder.setTitle("������ʾ");
				builder.setMessage(mb.description);
				// ���û������ؼ���ֱ�ӽ�ȥ������
				builder.setOnCancelListener(new OnCancelListener() {

					public void onCancel(DialogInterface dialog) {
						loadMainUi();

					}
				});

				builder.setPositiveButton("���̸���", new OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {

						// �ӷ�����������Ӧ�õ�SD��
						String url = "http://192.168.1.107:8080/li.apk";

						DownloadUtils.download(url);
						String progress = DownloadUtils.progress;
						tv_progress.setText(progress);
						System.out.println(tv_progress);
						// �򿪰�װӦ��
						Intent intent = new Intent();
						intent.setAction("android.intent.action.VIEW");
						intent.addCategory("android.intent.category.DEFAULT");
						intent.setDataAndType(Uri.fromFile(new File(Environment
								.getExternalStorageDirectory(), "li.apk")),
								"application/vnd.android.package-archive");
						startActivityForResult(intent, 0);

					}
				});

				builder.setNegativeButton("�´���˵", new OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						loadMainUi();

					}
				});

				builder.show();

				break;
			case LOAD_MAINUI:

				loadMainUi();

				break;

			}

		}

	};

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);
		loadMainUi();

	}

	private void loadMainUi() {

		Intent intent = new Intent(SplashActivity.this, MainActivity.class);
		startActivity(intent);
		finish();// ���ٵ�ǰActivity
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		// ��ӭ������͸����0��͸����1
		AlphaAnimation alph = new AlphaAnimation(0.0f, 1.0f);
		alph.setDuration(5000);
		findViewById(R.id.rl).startAnimation(alph);

		tv_progress = (TextView) findViewById(R.id.tv_progress);
		tv_versionName = (TextView) findViewById(R.id.tv_versionName);
		// ��ȡ����Ӧ�ð汾����
		Map map = MobileService.getCurrentVision(SplashActivity.this);
		String versionName = (String) map.get("versionName");
		// ��ʾ��������
		tv_versionName.setText(versionName);
		// �������������߼�
		MobileService.getServiceInfo(SplashActivity.this, handler);

	}

	/**
	 * �ڻ�ӭ����ʱ�����û������ؼ���Ч
	 */
	public void onBackPressed() {

		// super.onBackPressed();
	}

}
