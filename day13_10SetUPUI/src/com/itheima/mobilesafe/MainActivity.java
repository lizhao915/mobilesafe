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
 * ����1��Splash�����UI���� ��������һ�ű���ͼ��һ���ؼ�Prograss,һ��TextView���
 * 
 * ����2��ͨ������������JSON���ݻ�ȡ��Ӧ�����°汾�� ���裺 1.�����߳��У�����Http���󣬶�ȡ��������һ��JSON�ļ�����
 * 
 * 
 * 
 * 
 */
public class MainActivity extends Activity implements OnItemClickListener {

	private GridView gv;
	private String[] names = { "�ֻ�����", "ͨѶ��ʿ", "����ܼ�", "���̹���", "����ͳ��", "�ֻ�ɱ��",
			"��������", "�߼�����", "��������" };
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

		// ��GridView��ÿ����Ŀ���ü����¼�
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

	// ����Ŀ��Ϣ������list������
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
	 * дÿ����Ŀ�ĵ���¼�
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

	// ��ʾ��������Ի���
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
