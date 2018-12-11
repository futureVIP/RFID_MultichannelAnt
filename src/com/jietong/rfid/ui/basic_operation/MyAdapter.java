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

    //MyAdapter��Ҫһ��Context��ͨ��Context���Layout.inflater��Ȼ��ͨ��inflater����item�Ĳ���
    public MyAdapter(Context context, List<EPC> datas) {
        mInflater = LayoutInflater.from(context);
        mDatas = datas;
    }

    //�������ݼ��ĳ���
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

    //������������ص㣬����ҪΪ����дһ��ViewHolder
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.listview_item, parent, false); //���ز���
            holder = new ViewHolder();

            holder.tvId = (TextView) convertView.findViewById(R.id.tv_id);
            holder.tvEPC = (TextView) convertView.findViewById(R.id.tv_epc);
            holder.tvRssi = (TextView) convertView.findViewById(R.id.tv_rssi);
            holder.tvAntenna = (TextView) convertView.findViewById(R.id.tv_antenna);
            holder.tvDeviceNo = (TextView) convertView.findViewById(R.id.tv_device_no);
            holder.tvDirection = (TextView) convertView.findViewById(R.id.tv_direction);
            holder.tvCount = (TextView) convertView.findViewById(R.id.tv_count);

            convertView.setTag(holder);
        } else {   //else����˵����convertView�Ѿ��������ˣ�˵��convertView���Ѿ����ù�tag�ˣ���holder
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

    //���ViewHolderֻ�ܷ����ڵ�ǰ����ض���adapter����ΪViewHolder���ָ��item�Ŀؼ�����ͬ��ListView��item���ܲ�ͬ������ViewHolderд��һ��˽�е���
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