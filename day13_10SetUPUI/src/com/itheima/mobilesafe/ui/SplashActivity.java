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
 * 大写变小写：ctrl+shift+x 小写变大写:ctrl+shift+y 这个类的作用： 根据不用的状态码来处理。
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
				Toast.makeText(SplashActivity.this, "发生异常", 0);
				break;

			case SHOW_UPDATEDIALGO:

				MoblieInfo mb = (MoblieInfo) msg.obj;

				AlertDialog.Builder builder = new Builder(SplashActivity.this);
				// builder.setCancelable(false);

				builder.setTitle("更新提示");
				builder.setMessage(mb.description);
				// 当用户按返回键，直接进去主界面
				builder.setOnCancelListener(new OnCancelListener() {

					public void onCancel(DialogInterface dialog) {
						loadMainUi();

					}
				});

				builder.setPositiveButton("立刻更新", new OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {

						// 从服务下载最新应用到SD卡
						String url = "http://192.168.1.107:8080/li.apk";

						DownloadUtils.download(url);
						String progress = DownloadUtils.progress;
						tv_progress.setText(progress);
						System.out.println(tv_progress);
						// 打开安装应用
						Intent intent = new Intent();
						intent.setAction("android.intent.action.VIEW");
						intent.addCategory("android.intent.category.DEFAULT");
						intent.setDataAndType(Uri.fromFile(new File(Environment
								.getExternalStorageDirectory(), "li.apk")),
								"application/vnd.android.package-archive");
						startActivityForResult(intent, 0);

					}
				});

				builder.setNegativeButton("下次再说", new OnClickListener() {

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
		finish();// 销毁当前Activity
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		// 欢迎界面由透明度0到透明度1
		AlphaAnimation alph = new AlphaAnimation(0.0f, 1.0f);
		alph.setDuration(5000);
		findViewById(R.id.rl).startAnimation(alph);

		tv_progress = (TextView) findViewById(R.id.tv_progress);
		tv_versionName = (TextView) findViewById(R.id.tv_versionName);
		// 获取本地应用版本名称
		Map map = MobileService.getCurrentVision(SplashActivity.this);
		String versionName = (String) map.get("versionName");
		// 显示到界面上
		tv_versionName.setText(versionName);
		// 处理更新软件的逻辑
		MobileService.getServiceInfo(SplashActivity.this, handler);

	}

	/**
	 * 在欢迎界面时，当用户按返回键无效
	 */
	public void onBackPressed() {

		// super.onBackPressed();
	}

}
