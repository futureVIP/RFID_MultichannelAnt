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

            holder.tvId = (TextView) convertView.findViewById(R.id.tv_id);
            holder.tvEPC = (TextView) convertView.findViewById(R.id.tv_epc);
            holder.tvRssi = (TextView) convertView.findViewById(R.id.tv_rssi);
            holder.tvAntenna = (TextView) convertView.findViewById(R.id.tv_antenna);
            holder.tvDeviceNo = (TextView) convertView.findViewById(R.id.tv_device_no);
            holder.tvDirection = (TextView) convertView.findViewById(R.id.tv_direction);
            holder.tvCount = (TextView) convertView.findViewById(R.id.tv_count);

            convertView.setTag(holder);
        } else {   //else里面说明，convertView已经被复用了，说明convertView中已经设置过tag了，即holder
            holder = (ViewHolder) convertView.getTag();
        }

        EPC bean = mDatas.get(position);
        holder.tvId.setText(String.valueOf(bean.getId()));
        holder.tvEPC.setText(bean.getEpc());
        holder.tvRssi.setText(String.valueOf(bean.getRssi()));
        holder.tvAntenna.setText(String.valueOf(bean.getAnt()));
        holder.tvDeviceNo.setText(String.valueOf(bean.getDeviceNo()));
        holder.tvDirection.setText(String.valueOf(bean.getDirection()));
        holder.tvCount.setText(String.valueOf(bean.getCount()));

        return convertView;
    }

    //这个ViewHolder只能服务于当前这个特定的adapter，因为ViewHolder里会指定item的控件，不同的ListView，item可能不同，所以ViewHolder写成一个私有的类
    private class ViewHolder {
        TextView tvId;
        TextView tvEPC;
        TextView tvRssi;
        TextView tvAntenna;
        TextView tvDeviceNo;
        TextView tvDirection;
        TextView tvCount;
    }
}