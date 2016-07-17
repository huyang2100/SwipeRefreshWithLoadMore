package com.hy.testrefresh;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * 带有加载更多功能的ListView
 * 
 * @author Yang
 *
 */
public class ListViewWithLoadMore extends ListView implements OnScrollListener {

	private static final String LOADING = "正在加载...";
	private static final String TAG = ListViewWithLoadMore.class.getSimpleName();
	private TextView tvLoading;
	private LoadMoreListener lm;
	private LinearLayout footerView;

	public ListViewWithLoadMore(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}

	public ListViewWithLoadMore(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public ListViewWithLoadMore(Context context) {
		super(context);
		init(context);
	}

	/**
	 * 初始化所需控件
	 * 
	 * @param context
	 *            上下文
	 * @author huyang2100@163.com
	 */
	private void init(Context context) {
		if (footerView == null) {
			// 底部footView
			footerView = new LinearLayout(context);
			android.widget.LinearLayout.LayoutParams lpTvLoading = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			android.widget.LinearLayout.LayoutParams lpPb = new LinearLayout.LayoutParams(getDip(context, 15),
					getDip(context, 15));
			footerView.setOrientation(LinearLayout.HORIZONTAL);
			footerView.setGravity(Gravity.CENTER);

			// 进度条
			ProgressBar pb = new ProgressBar(context);
			lpPb.rightMargin = getDip(context, 10);
			footerView.addView(pb, lpPb);

			// 加载更多的说明文字
			tvLoading = new TextView(context);
			tvLoading.setText(LOADING);
			tvLoading.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
			tvLoading.setPadding(0, getDip(context, 15), 0, getDip(context, 15));

			footerView.addView(tvLoading, lpTvLoading);
			addFooterView(footerView);
		}

		setOnScrollListener(this);
	}

	/**
	 * 得到对应的dp值
	 * 
	 * @param context
	 *            上下文
	 * @param num
	 *            输入的值
	 * @return 对应的dp值
	 * @author huyang2100@163.com
	 */
	private int getDip(Context context, int num) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, num,
				context.getResources().getDisplayMetrics());
	}

	/**
	 * 加载更多的监听器
	 * 
	 * @author Yang
	 *
	 */
	public interface LoadMoreListener {
		public void onLoadMore();
	}

	public void setOnLoadMoreListener(LoadMoreListener lm) {
		this.lm = lm;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		Log.i(TAG, "last vis position: " + getLastVisiblePosition());
		Log.i(TAG, "count: " + getCount());

		// 恢复loading状态
		if (!isFooterViewVisible()) {
			setLoading(true);
		}
		// 滑动到底部（listview处于静止状态，并且最后一个可见条目索引为总条目数-1（索引从0开始））
		if (OnScrollListener.SCROLL_STATE_IDLE == scrollState && getLastVisiblePosition() == getCount() - 1) {
			// 触发加载更多数据，用到了回调
			if (lm != null) {
				lm.onLoadMore();
			}
		}
	}

	/**
	 * footerView是否可见
	 * 
	 * @return true，可见；false，不可见
	 * @author huyang2100@163.com
	 */
	public boolean isFooterViewVisible() {
		return footerView.getVisibility() == View.VISIBLE;
	}

	/**
	 * 设置footerView是否可见
	 * 
	 * @param isLoading
	 *            true，设为可见；false，设为不可见
	 * @author huyang2100@163.com
	 */
	public void setLoading(boolean isLoading) {
		if (isLoading) {
			footerView.setVisibility(View.VISIBLE);
		} else {
			// 加载完成，去除底部布局
			footerView.setVisibility(View.INVISIBLE);
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

	}

}
