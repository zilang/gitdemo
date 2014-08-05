package com.jany.phoneguard.app;

import android.app.Activity;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuActivity extends Activity {
	
	private static final String TAG = "MenuActivity";

	private LayoutInflater inflater;
	private Gallery mGallery;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.menu_activity_layout);

		inflater = LayoutInflater.from(this);

		mGallery = (Gallery) findViewById(R.id.gallery_navigation);
		final ImageAdapter imageAdapter = new ImageAdapter();
		mGallery.setAdapter(imageAdapter);

		mGallery.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

			}
		});
	}

	private final class ImageAdapter extends BaseAdapter {
		private String[] msgs = { "��������", "��������", "��λ����", "��������", "ϵͳ����", "������ʾ", "�����˳�" };	// �ַ�����Դ

		private int[] ids = { R.drawable.password_setting,
				R.drawable.back_setting, R.drawable.map_setting,
				R.drawable.sound_setting, R.drawable.system_settings, R.drawable.help,
				R.drawable.exit };// ͼƬ��Դ

		private CacheView cacheView;
		public ImageAdapter() {
			Log.i(TAG, "new ImageAdapter");
		}
		@Override
		public int getCount() {
//			return Integer.MAX_VALUE;
			return ids.length;
		}

		@Override
		public Object getItem(int position) {
			return msgs[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			View v = null;
			
			ImageView imageView = null;
			TextView textView = null;
			
			if(convertView == null) {
				v = inflater.inflate(R.layout.item_layout, null);
				
				imageView = (ImageView) v.findViewById(R.id.iv_item_img);
				textView = (TextView) v.findViewById(R.id.tv_item_name);
				
				cacheView = new CacheView();
				cacheView.imageView = imageView;
				cacheView.textView = textView;
				v.setTag(v);
			}else {
				v = convertView;
				cacheView = (CacheView) v.getTag();
				
				imageView = cacheView.imageView;
				textView = cacheView.textView;
			}
			
			textView.setText(msgs[position%msgs.length]);
			imageView.setImageResource(ids[position%ids.length]);
			imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);	// ����Ӧxy
			return v;
		}
		
		
		
		/**
		 * ����������ʵ�ʵĳ���
		 * @return
		 */
		public int getActualCount() {
			return ids.length;
		}
		
		private final class CacheView {
			public  ImageView imageView;
			public  TextView textView;
		}
	}
}
