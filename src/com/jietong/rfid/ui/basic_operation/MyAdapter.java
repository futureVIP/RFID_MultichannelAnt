package com.jietong.rfid.ui.basic_operation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;
import com.jietong.rfid.uhf.entity.EPC;
import com.jietong.rfid.ui.R;

public class MyAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<EPC> mDatas;

    //MyAdapter需要一个Context，通过Context获得Layout.inflater，然后通过inflater加载item的布局
    public MyAdapter(Context context, List<EPC> datas) {
        mInflater = LayoutInflater.from(context);
        mDatas = datas;
    }

    //返回数据集的长度
    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //这个方法才是重点，我们要为它编写一个ViewHolder
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.listview_item, parent, false); //加载布局
            holder = new ViewHolder();

            holder.titleTv = (TextView) convertView.findViewById(R.id.textView_id);
            holder.descTv = (TextView) convertView.findViewById(R.id.textView_epc);
            holder.timeTv = (TextView) convertView.findViewById(R.id.textView_antenna);
            holder.phoneTv = (TextView) convertView.findViewById(R.id.textView_count);

            convertView.setTag(holder);
        } else {   //else里面说明，convertView已经被复用了，说明convertView中已经设置过tag了，即holder
            holder = (ViewHolder) convertView.getTag();
        }

        EPC bean = mDatas.get(position);
        holder.titleTv.setText(String.valueOf(bean.getId()));
        holder.descTv.setText(bean.getEpc());
        holder.timeTv.setText(String.valueOf(bean.getAnt()));
        holder.phoneTv.setText(String.valueOf(bean.getCount()));

        return convertView;
    }

    //这个ViewHolder只能服务于当前这个特定的adapter，因为ViewHolder里会指定item的控件，不同的ListView，item可能不同，所以ViewHolder写成一个私有的类
    private class ViewHolder {
        TextView titleTv;
        TextView descTv;
        TextView timeTv;
        TextView phoneTv;
    }
}