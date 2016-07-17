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
 * ���м��ظ��๦�ܵ�ListView
 * 
 * @author Yang
 *
 */
public class ListViewWithLoadMore extends ListView implements OnScrollListener {

	private static final String LOADING = "���ڼ���...";
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
	 * ��ʼ������ؼ�
	 * 
	 * @param context
	 *            ������
	 * @author huyang2100@163.com
	 */
	private void init(Context context) {
		if (footerView == null) {
			// �ײ�footView
			footerView = new LinearLayout(context);
			android.widget.LinearLayout.LayoutParams lpTvLoading = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			android.widget.LinearLayout.LayoutParams lpPb = new LinearLayout.LayoutParams(getDip(context, 15),
					getDip(context, 15));
			footerView.setOrientation(LinearLayout.HORIZONTAL);
			footerView.setGravity(Gravity.CENTER);

			// ������
			ProgressBar pb = new ProgressBar(context);
			lpPb.rightMargin = getDip(context, 10);
			footerView.addView(pb, lpPb);

			// ���ظ����˵������
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
	 * �õ���Ӧ��dpֵ
	 * 
	 * @param context
	 *            ������
	 * @param num
	 *            �����ֵ
	 * @return ��Ӧ��dpֵ
	 * @author huyang2100@163.com
	 */
	private int getDip(Context context, int num) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, num,
				context.getResources().getDisplayMetrics());
	}

	/**
	 * ���ظ���ļ�����
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

		// �ָ�loading״̬
		if (!isFooterViewVisible()) {
			setLoading(true);
		}
		// �������ײ���listview���ھ�ֹ״̬���������һ���ɼ���Ŀ����Ϊ����Ŀ��-1��������0��ʼ����
		if (OnScrollListener.SCROLL_STATE_IDLE == scrollState && getLastVisiblePosition() == getCount() - 1) {
			// �������ظ������ݣ��õ��˻ص�
			if (lm != null) {
				lm.onLoadMore();
			}
		}
	}

	/**
	 * footerView�Ƿ�ɼ�
	 * 
	 * @return true���ɼ���false�����ɼ�
	 * @author huyang2100@163.com
	 */
	public boolean isFooterViewVisible() {
		return footerView.getVisibility() == View.VISIBLE;
	}

	/**
	 * ����footerView�Ƿ�ɼ�
	 * 
	 * @param isLoading
	 *            true����Ϊ�ɼ���false����Ϊ���ɼ�
	 * @author huyang2100@163.com
	 */
	public void setLoading(boolean isLoading) {
		if (isLoading) {
			footerView.setVisibility(View.VISIBLE);
		} else {
			// ������ɣ�ȥ���ײ�����
			footerView.setVisibility(View.INVISIBLE);
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

	}

}
