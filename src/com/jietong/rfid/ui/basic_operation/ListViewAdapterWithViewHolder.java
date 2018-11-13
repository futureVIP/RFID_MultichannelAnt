package com.jietong.rfid.ui.basic_operation;

import android.content.Context;
import android.widget.TextView;
import java.util.List;
import com.jietong.rfid.uhf.entity.EPC;
import com.jietong.rfid.ui.R;

public class ListViewAdapterWithViewHolder extends ListViewAdapter<EPC> {

	// MyAdapter需要一个Context，通过Context获得Layout.inflater，然后通过inflater加载item的布局
	public ListViewAdapterWithViewHolder(Context context, List<EPC> datas) {
		super(context, datas, R.layout.listview_item);
	}

	@Override
	public void convert(ViewHolder holder, EPC bean) {
		((TextView) holder.getView(R.id.textView_id)).setText(String.valueOf(bean.getId()));
		((TextView) holder.getView(R.id.textView_epc)).setText(bean.getEpc());
		((TextView) holder.getView(R.id.textView_antenna)).setText(String.valueOf(bean.getAnt()));
		((TextView) holder.getView(R.id.textView_count)).setText(String.valueOf(bean.getCount()));
	}
}