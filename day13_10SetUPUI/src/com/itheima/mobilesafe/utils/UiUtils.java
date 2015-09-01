package com.itheima.mobilesafe.utils;

import com.itheima.mobilesafe.MainActivity;
import com.itheima.mobilesafe.ui.SplashActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.ToneGenerator;
import android.widget.Toast;

public class UiUtils {

	public static void showToast(final Activity context, final String message) {
		if ("main".equals(Thread.currentThread().getName())) {
			Toast.makeText(context, message, 0).show();

		} else {

			context.runOnUiThread(new Runnable() {

				public void run() {

					Toast.makeText(context, message, 0).show();

				}
			});

		}

	}

}
