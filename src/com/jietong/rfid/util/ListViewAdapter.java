
package com.jietong.rfid.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.List;

/**
 * ͨ�õ�ListView��BaseAdapter�����е�ListView���Զ���adapter�����Լ̳������Ŷ
 */
public abstract class ListViewAdapter<T> extends BaseAdapter {

    //Ϊ����������ʣ����ǽ���������Ϊprotected
    protected Context mContext;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;
    private int layoutId; //��ͬ��ListView��item���ֿ��ܲ�ͬ������Ҫ�Ѳ��ֵ�����ȡ����

    public ListViewAdapter(Context context, List<T> datas, int layoutId) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        this.mDatas = datas;
        this.layoutId = layoutId;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //��ʼ��ViewHolder,ʹ��ͨ�õ�ViewHolder��һ�д���͸㶨ViewHolder�ĳ�ʼ����
        ViewHolder holder = ViewHolder.get(mContext, convertView, parent, layoutId, position);//layoutId���ǵ���item�Ĳ���

        convert(holder, getItem(position));
        return holder.getConvertView(); //��һ�еĴ���Ҫע����
    }

    //��convert����������ȥ
    public abstract void convert(ViewHolder holder, T t);

}