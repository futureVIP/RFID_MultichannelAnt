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

            holder.titleTv = (TextView) convertView.findViewById(R.id.textView_id);
            holder.descTv = (TextView) convertView.findViewById(R.id.textView_epc);
            holder.timeTv = (TextView) convertView.findViewById(R.id.textView_antenna);
            holder.phoneTv = (TextView) convertView.findViewById(R.id.textView_count);

            convertView.setTag(holder);
        } else {   //else����˵����convertView�Ѿ��������ˣ�˵��convertView���Ѿ����ù�tag�ˣ���holder
            holder = (ViewHolder) convertView.getTag();
        }

        EPC bean = mDatas.get(position);
        holder.titleTv.setText(String.valueOf(bean.getId()));
        holder.descTv.setText(bean.getEpc());
        holder.timeTv.setText(String.valueOf(bean.getAnt()));
        holder.phoneTv.setText(String.valueOf(bean.getCount()));

        return convertView;
    }

    //���ViewHolderֻ�ܷ����ڵ�ǰ����ض���adapter����ΪViewHolder���ָ��item�Ŀؼ�����ͬ��ListView��item���ܲ�ͬ������ViewHolderд��һ��˽�е���
    private class ViewHolder {
        TextView titleTv;
        TextView descTv;
        TextView timeTv;
        TextView phoneTv;
    }
}