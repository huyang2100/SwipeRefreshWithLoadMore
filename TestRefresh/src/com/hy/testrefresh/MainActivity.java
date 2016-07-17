package com.hy.testrefresh;

import java.util.ArrayList;
import java.util.Collections;

import com.hy.testrefresh.ListViewWithLoadMore.LoadMoreListener;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.widget.ArrayAdapter;

public class MainActivity extends Activity implements OnRefreshListener, LoadMoreListener {

	private ArrayList<String> datas = new ArrayList<String>();
	private SwipeRefreshLayout swiplayout;
	private ListViewWithLoadMore lv;
	private ArrayAdapter<String> adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		swiplayout = (SwipeRefreshLayout) findViewById(R.id.swiplayout);
		lv = (ListViewWithLoadMore) findViewById(R.id.lv);
		
		lv.setOnLoadMoreListener(this);
		
		lv.post(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				swiplayout.setRefreshing(true);
			}
		});
		
		
		getData();
		
		onRefresh();
		
		
		swiplayout.setOnRefreshListener(this);
	}

	private void updateDatas() {
		datas.clear();
		datas.addAll(getData());
		//更新数据
		Collections.shuffle(datas);
	}
	private ArrayList<String> getData() {
		ArrayList<String> d = new ArrayList<String>();
		for(int i=0;i<20;i++){
			d.add("item: "+i);
		}
		
		return d;
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		lv.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				updateDatas();
				
				adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, datas);
				lv.setAdapter(adapter);
				
				adapter.notifyDataSetChanged();
				swiplayout.setRefreshing(false);
			}
		}, 1000);
	}

	@Override
	public void onLoadMore() {
		lv.postDelayed(new Runnable() {
			
			@Override
			public void run() {
//				Toast.makeText(MainActivity.this, "load more", 0).show();
				datas.addAll(getData());
				adapter.notifyDataSetChanged();
				lv.setLoading(false);
			}
		}, 2000);
		
	}

	
}
