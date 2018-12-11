package com.jietong.rfid.ui.basic_operation;

import android.content.Context;
import android.widget.TextView;
import java.util.List;
import com.jietong.rfid.uhf.entity.EPC;
import com.jietong.rfid.ui.R;

public class ListViewAdapterWithViewHolder extends ListViewAdapter<EPC> {

	// MyAdapter��Ҫһ��Context��ͨ��Context���Layout.inflater��Ȼ��ͨ��inflater����item�Ĳ���
	public ListViewAdapterWithViewHolder(Context context, List<EPC> datas) {
		super(context, datas, R.layout.listview_item);
	}

	@Override
	public void convert(ViewHolder holder, EPC bean) {
		((TextView) holder.getView(R.id.tv_id)).setText(String.valueOf(bean.getId()));
		((TextView) holder.getView(R.id.tv_epc)).setText(bean.getEpc());
		((TextView) holder.getView(R.id.tv_rssi)).setText(bean.getRssi());
		((TextView) holder.getView(R.id.tv_antenna)).setText(String.valueOf(bean.getAnt()));
		((TextView) holder.getView(R.id.tv_device_no)).setText(bean.getDeviceNo());
		((TextView) holder.getView(R.id.tv_direction)).setText(bean.getDirection());
		((TextView) holder.getView(R.id.tv_count)).setText(String.valueOf(bean.getCount()));
	}
}