package com.itheima.mobilesafe;

import java.util.ArrayList;
import java.util.List;

import com.example.day13_01.R;
import com.itheima.mobilesafe.bean.ItemsInfo;
import com.lidroid.xutils.view.annotation.event.OnItemClick;

import android.app.Activity;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

/**
 * 需求1：Splash界面的UI界面 分析：由一张背景图，一个控件Prograss,一个TextView组成
 * 
 * 需求2：通过解析服务器JSON数据获取该应用最新版本号 步骤： 1.在子线程中，发送Http请求，读取服务器的一个JSON文件数据
 * 
 * 
 * 
 * 
 */
public class MainActivity extends Activity implements OnItemClickListener {

	private GridView gv;
	private String[] names = { "手机防盗", "通讯卫士", "软件管家", "进程管理", "流量统计", "手机杀毒",
			"缓存清理", "高级工具", "设置中心" };
	private int[] resId = { R.drawable.app_selector,
			R.drawable.home_callmsgsafe, R.drawable.home_netmanager,
			R.drawable.home_safe, R.drawable.home_settings,
			R.drawable.home_sysoptimize, R.drawable.home_taskmanager,
			R.drawable.home_tools, R.drawable.home_trojan };

	private List<ItemsInfo> itemlist = new ArrayList<ItemsInfo>();

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_main);
		gv = (GridView) findViewById(R.id.gv);

		// 给GridView的每个条目设置监听事件
		gv.setOnItemClickListener(this);

		gv.setAdapter(new MyAdapter());

	}

	class MyAdapter extends BaseAdapter {

		public int getCount() {

			return names.length;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			View view;
			if (convertView != null) {
				view = convertView;
			} else {
				view = View.inflate(MainActivity.this, R.layout.gridview_main,
						null);
			}

			ImageView iv = (ImageView) view.findViewById(R.id.iv);
			TextView tv = (TextView) view.findViewById(R.id.tv);
			iv.setImageResource(resId[position]);
			tv.setText(names[position]);

			return view;
		}

		public Object getItem(int position) {

			return itemlist.get(position);
		}

		public long getItemId(int position) {

			return position;
		}

	}

	// 将条目信息保存在list集合中
	public List<ItemsInfo> getItemInfo() {
		ItemsInfo info;
		for (String itemName : names) {
			for (int itemId : resId) {
				info = new ItemsInfo(itemName, itemId);
				itemlist.add(info);

			}
		}

		return itemlist;

	}

	/*
	 * 写每个条目的点击事件
	 */

	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		switch (position) {
		case 0:
			showSetupDialog();

			break;

		default:
			break;
		}

	}

	// 显示设置密码对话框
	public void showSetupDialog() {
		AlertDialog.Builder builder = new Builder(MainActivity.this);
		View view = View.inflate(MainActivity.this, R.layout.dialog_setup_pwd,
				null);
		builder.setView(view);
		AlertDialog create = builder.create();

		create.show();

	}

	public void showEnterDialog() {

	}

}
