package com.jietong.rfid.ui.basic_operation;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

public class ViewPagerAdatper extends PagerAdapter {
    private List<View> mViewList ;
	protected Context mContext;
	protected LayoutInflater mInflater;
	private int layoutId;

    public ViewPagerAdatper(Context context,List<View> mViewList ) {
    	this.mContext = context;
        this.mViewList = mViewList;
    }

    @Override
    public int getCount() {
        return mViewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mViewList.get(position));
        return mViewList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViewList.get(position));
    }
}
