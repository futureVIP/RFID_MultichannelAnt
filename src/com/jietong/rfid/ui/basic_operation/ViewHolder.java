package com.jietong.rfid.ui.basic_operation;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class ViewHolder {
	private SparseArray<View> mViews;
	private int mPosition;
	private View mConvertView;

	public ViewHolder(Context context, ViewGroup parent, int layoutId,
			int position) {
		this.mPosition = position;
		this.mViews = new SparseArray<View>();

		mConvertView = LayoutInflater.from(context).inflate(layoutId, parent,false);

		mConvertView.setTag(this);

	}

	public static ViewHolder get(Context context, View convertView,	ViewGroup parent, int layoutId, int position) {
		if (convertView == null) {
			return new ViewHolder(context, parent, layoutId, position);
		} else {
			ViewHolder holder = (ViewHolder) convertView.getTag();
			holder.mPosition = position; // ��ʹViewHolder�Ǹ��õģ�����position�ǵø���һ��
			return holder;
		}
	}

	/*
	 * ͨ��viewId��ȡ�ؼ�
	 */
	// ʹ�õ��Ƿ���T,���ص���View������
	@SuppressWarnings("unchecked")
	public <T extends View> T getView(int viewId) {
		View view = mViews.get(viewId);

		if (view == null) {
			view = mConvertView.findViewById(viewId);
			mViews.put(viewId, view);
		}
		return (T) view;
	}

	public View getConvertView() {
		return mConvertView;
	}
}