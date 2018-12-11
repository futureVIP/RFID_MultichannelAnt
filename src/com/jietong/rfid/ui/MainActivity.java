package com.jietong.rfid.ui;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.jietong.rfid.uhf.entity.EPC;
import com.jietong.rfid.uhf.service.CallBack;
import com.jietong.rfid.uhf.service.CallBackStopReadCard;
import com.jietong.rfid.uhf.service.ReaderService;
import com.jietong.rfid.uhf.service.impl.ReaderServiceImpl;
import com.jietong.rfid.uhf.tool.ReaderUtil;
import com.jietong.rfid.ui.basic_operation.DataFilter;
import com.jietong.rfid.ui.basic_operation.ListViewAdapterWithViewHolder;
import com.jietong.rfid.ui.basic_operation.ViewPagerAdatper;
import com.jietong.rfid.ui.other_operation.AdjacentDiscrimnantActivity;
import com.jietong.rfid.ui.other_operation.BuzzerSetActivity;
import com.jietong.rfid.ui.other_operation.FactoryDataResetActivity;
import com.jietong.rfid.ui.other_operation.InvCardDataOutputFormatActivity;
import com.jietong.rfid.ui.other_operation.SpecialReadTagPatternActivity;
import com.jietong.rfid.ui.params_set.Antenna16ChannelActivity;
import com.jietong.rfid.ui.params_set.Antenna32ChannelActivity;
import com.jietong.rfid.ui.params_set.Antenna4ChannelActivity;
import com.jietong.rfid.ui.params_set.CommunicationModeActivity;
import com.jietong.rfid.ui.params_set.DeviceNumberActivity;
import com.jietong.rfid.ui.params_set.OperatingFrequencySetActivity;
import com.jietong.rfid.ui.params_set.VersionActivity;
import com.jietong.rfid.ui.params_set.WorkPatternActivity;
import com.jietong.rfid.ui.system.ExitFromSettings;
import com.jietong.rfid.ui.system.MainTopRightDialog;
import com.jietong.rfid.ui.tag_operation.DesignatedAreaReadAndWriteActivity;
import com.jietong.rfid.ui.tag_operation.TagDestroyActivity;
import com.jietong.rfid.ui.tag_operation.TagLockUnlockActivity;
import com.jietong.rfid.util.Toasts;

public class MainActivity extends Activity implements OnClickListener {
	
	public static MainActivity instance = null;
	private ViewPager mTabPager;
	private ImageView mTabImg;// 动画图片
	private ImageView mTab1, mTab2, mTab3, mTab4;
	private LinearLayout tab1, tab2, tab3, tab4;
	private TextView basicOperation,tagsOperate,parameterSetting,otherSetting;
	private int zero = 0;// 动画图片偏移量
	private int currIndex = 0;// 当前页卡编号
	private int one;// 单个水平动画位移
	private int two;
	private int three;
	private LinearLayout mClose;
	private LinearLayout mCloseBtn;
	private View layout;
	private boolean menu_display = false;
	private PopupWindow menuWindow;
	private LayoutInflater inflater;
	//新协议
	private Button btnInvOnce;
	private Button btnBeginInv;
	private Button btnBasicStop;
	//旧协议2018-12-07
	private Button btnOldInvOnce;
	private Button btnOldBeginInv;
	private Button btnOldBasicStop;
	
	private Button btnBasicClear;
	private int tagAmount = 0;
	private int tagCount = 0;
	public TextView tvBasicAmount;
	public TextView tvBasicTime;
	private ListView listViewData;
	private List<EPC> listEPC;
	private RelativeLayout rlAntenna4channel;
	private RelativeLayout rlAntenna16channel;
	private RelativeLayout rlAntenna32channel;
	private RelativeLayout rlAdjacentDiscriminant;
	private ListViewAdapterWithViewHolder listViewAdapterWithViewHolder;
	private ReaderService readerService = new ReaderServiceImpl();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		inital();
	}

	private void inital() {
		controlInital();
		eventInital();
	}

	private void eventInital() {
		mTabPager.setOnPageChangeListener(new MyOnPageChangeListener());
		
		mTab1.setOnClickListener(new MyOnClickListener(0));
		mTab2.setOnClickListener(new MyOnClickListener(1));
		mTab3.setOnClickListener(new MyOnClickListener(2));
		mTab4.setOnClickListener(new MyOnClickListener(3));
		
		tab1.setOnClickListener(new MyOnClickListener(0));
		tab2.setOnClickListener(new MyOnClickListener(1));
		tab3.setOnClickListener(new MyOnClickListener(2));
		tab4.setOnClickListener(new MyOnClickListener(3));
		
		basicOperation.setOnClickListener(new MyOnClickListener(0));
		tagsOperate.setOnClickListener(new MyOnClickListener(1));
		parameterSetting.setOnClickListener(new MyOnClickListener(2));
		otherSetting.setOnClickListener(new MyOnClickListener(3));
		
		btnOldInvOnce.setOnClickListener(this);
		btnOldBeginInv.setOnClickListener(this);
		btnOldBasicStop.setOnClickListener(this);
		
		btnInvOnce.setOnClickListener(this);
		btnBeginInv.setOnClickListener(this);
		btnBasicStop.setOnClickListener(this);
		btnBasicClear.setOnClickListener(this);
	}

	private void controlInital() {
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		instance = this;
		listEPC = new ArrayList<EPC>();
		
		Display currDisplay = getWindowManager().getDefaultDisplay();
		int displayWidth = currDisplay.getWidth();
		int displayHeight = currDisplay.getHeight();
		one = displayWidth / 4; 
		two = one * 2;
		three = one * 3;
		
		mTabPager = (ViewPager) findViewById(R.id.vp_main_tab_pager);
		basicOperation = (TextView) findViewById(R.id.tv_basic_opera);
		tagsOperate = (TextView) findViewById(R.id.tv_tags_operate);
		parameterSetting = (TextView) findViewById(R.id.tv_parameter_setting);
		otherSetting = (TextView) findViewById(R.id.tv_other_setting);
		
		mTab1 = (ImageView) findViewById(R.id.img_weixin);
		mTab2 = (ImageView) findViewById(R.id.img_address);
		mTab3 = (ImageView) findViewById(R.id.img_friends);
		mTab4 = (ImageView) findViewById(R.id.img_settings);
		
		tab1 = (LinearLayout) findViewById(R.id.ll_basic_operation);
		tab2 = (LinearLayout) findViewById(R.id.ll_tags_operate);
		tab3 = (LinearLayout) findViewById(R.id.ll_parameter_setting);
		tab4 = (LinearLayout) findViewById(R.id.ll_other_setting);
		
		mTabImg = (ImageView) findViewById(R.id.img_tab_now);
		basicOperation.setTextColor(Color.GREEN);
		
		LayoutInflater mLi = LayoutInflater.from(this);
		View view1 = mLi.inflate(R.layout.amain_tab_basic_opera, null);
		View view2 = mLi.inflate(R.layout.amain_tab_tag_opera, null);
		View view3 = mLi.inflate(R.layout.amain_tab_params_settings, null);
		View view4 = mLi.inflate(R.layout.amain_tab_other_settings, null);
		
		basicOpera(view1);
		tagOpera(view2);
		paramsSet(view3);
		otherOpera(view4);
		
		final List<View> views = new ArrayList<View>();
		views.add(view1);
		views.add(view2);
		views.add(view3);
		views.add(view4);
		
		mTabPager.setAdapter(new ViewPagerAdatper(this,views));
	}

	private void basicOpera(View view1) {
		listViewData = (ListView) view1.findViewById(R.id.listView_data);
		tvBasicAmount = (TextView) view1.findViewById(R.id.tv_basic_amount);
		tvBasicTime = (TextView) view1.findViewById(R.id.tv_basic_time);
		
		btnInvOnce = (Button) view1.findViewById(R.id.btn_basic_inv_once);
		btnBeginInv = (Button) view1.findViewById(R.id.btn_basic_beginInv);
		btnBasicStop = (Button) view1.findViewById(R.id.btn_basic_stop);
		
		btnOldInvOnce = (Button) view1.findViewById(R.id.btn_old_basic_inv_once);
		btnOldBeginInv = (Button) view1.findViewById(R.id.btn_old_basic_beginInv);
		btnOldBasicStop = (Button) view1.findViewById(R.id.btn_old_basic_stop);
		
		btnBasicClear = (Button) view1.findViewById(R.id.btn_basic_clear);
	}
	
	private void tagOpera(View view2) {
		
	}

	private void paramsSet(View view3) {
		rlAntenna4channel = (RelativeLayout) view3.findViewById(R.id.rl_antenna_4channel);
		rlAntenna16channel = (RelativeLayout) view3.findViewById(R.id.rl_antenna_16channel);
		rlAntenna32channel = (RelativeLayout) view3	.findViewById(R.id.rl_antenna_32channel);
	}

	private void otherOpera(View view4) {
		rlAdjacentDiscriminant = (RelativeLayout) view4.findViewById(R.id.rl_adjacent_discriminant);
	}

	/**
	 * 头标点击监听
	 */
	public class MyOnClickListener implements View.OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}

		@Override
		public void onClick(View v) {
			mTabPager.setCurrentItem(index);
		}
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_basic_inv_once:
			invOnce();
			break;
		case R.id.btn_basic_beginInv:
			beginInv();
			break;
		case R.id.btn_basic_stop:
			stop();
			break;
		case R.id.btn_basic_clear:
			clearData();
			break;
		// 旧协议
		case R.id.btn_old_basic_inv_once:
			oldInvOnce();
			break;
		case R.id.btn_old_basic_beginInv:
			oldBeginInv();
			break;
		case R.id.btn_old_basic_stop:
			oldStop();
			break;
		default:
			break;
		}
	}

	/**********************************tab pager start*************************************************************/
	private Animation tabBasicOperation(){
		Animation animation = new TranslateAnimation(zero, one, 0, 0);
		mTab1.setImageDrawable(getResources().getDrawable(R.drawable.tab_rfid_normal));
		basicOperation.setTextColor(Color.WHITE);
		return animation;
	}
	
	private Animation tabTagsOperate(){
		Animation animation = new TranslateAnimation(one, 0, 0, 0);
		int tab_address_normal = R.drawable.tab_address_normal;
		mTab2.setImageDrawable(getResources().getDrawable(tab_address_normal));
		tagsOperate.setTextColor(Color.WHITE);
		return animation;
	}
	
	private Animation tabParameterSetting(){
		Animation animation = new TranslateAnimation(two, 0, 0, 0);
		mTab3.setImageDrawable(getResources().getDrawable(R.drawable.tab_find_frd_normal));
		parameterSetting.setTextColor(Color.WHITE);
		return animation;
	}
	
	private Animation tabOtherSetting(){
		Animation animation = new TranslateAnimation(three, 0, 0, 0);
		mTab4.setImageDrawable(getResources().getDrawable(R.drawable.tab_settings_normal));
		otherSetting.setTextColor(Color.WHITE);
		return animation;
	}
	/**************************************tab pager end*********************************************************/
	
	
	/**************************************choose current pager start*********************************************************/
	private Animation basicOperation(){
		Animation animation = null;
		int tab_weixin_pressed = R.drawable.tab_rfid_pressed;
		mTab1.setImageDrawable(getResources().getDrawable(tab_weixin_pressed));
		basicOperation.setTextColor(Color.GREEN);
		if (currIndex == 1) {
			animation = tabTagsOperate();
		} else if (currIndex == 2) {
			animation = tabParameterSetting();
		} else if (currIndex == 3) {
			animation = tabOtherSetting();
		}
		return animation;
	}
	
	private Animation tagOperate(){
		Animation animation = null;
		mTab2.setImageDrawable(getResources().getDrawable(R.drawable.tab_address_pressed));
		tagsOperate.setTextColor(Color.GREEN);
		if (currIndex == 0) {
			animation = tabBasicOperation();
		} else if (currIndex == 2) {
			animation = tabParameterSetting();
		} else if (currIndex == 3) {
			animation = tabOtherSetting();
		}
		return animation;
	}
	
	private Animation parameterSetting(){
		Animation animation = null;
		mTab3.setImageDrawable(getResources().getDrawable(R.drawable.tab_find_frd_pressed));
		parameterSetting.setTextColor(Color.GREEN);
		if (currIndex == 0) {
			animation = tabBasicOperation();
		} else if (currIndex == 1) {
			animation = tabTagsOperate();
		} else if (currIndex == 3) {
			animation = tabOtherSetting();
		}
		return animation;
	}
	
	private Animation otherSetting(){
		Animation animation = null;
		mTab4.setImageDrawable(getResources().getDrawable(R.drawable.tab_settings_pressed));
		otherSetting.setTextColor(Color.GREEN);
		if (currIndex == 0) {
			animation = tabBasicOperation();
		} else if (currIndex == 1) {
			animation = tabTagsOperate();
		} else if (currIndex == 2) {
			animation = tabParameterSetting();
		}
		return animation;
	}
	
	/**************************************choose current pager end*********************************************************/
	
	public void antennaShowChannel(){
		rlAntenna4channel.setVisibility(View.GONE);
		rlAntenna16channel.setVisibility(View.GONE);
		rlAntenna32channel.setVisibility(View.GONE);
		
		if (null != ReaderUtil.readers) {
			int channel = ReaderUtil.readers.getChannel();
			if (channel == 4 || channel == 6) {
				rlAntenna4channel.setVisibility(View.VISIBLE);
			} else if (channel == 16) {
				rlAntenna16channel.setVisibility(View.VISIBLE);
			} else if (channel == 32) {
				rlAntenna32channel.setVisibility(View.VISIBLE);
			}
		}
	}
	
	private void tabPage(int pager){
		Animation animation = null;
		switch (pager) {
		case 0:
			animation = basicOperation();
			break;
		case 1:
			animation = tagOperate();
			break;
		case 2:
			animation = parameterSetting();
			break;
		case 3:
			animation = otherSetting();
			break;
		default:
			break;
		}
		currIndex = pager;
		animation.setFillAfter(true);// True:图片停在动画结束位置
		animation.setDuration(150);
		mTabImg.startAnimation(animation);
	}
	
	public class MyOnPageChangeListener implements OnPageChangeListener {
		@Override
		public void onPageSelected(int pager) {
			antennaShowChannel();
			tabPage(pager);
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) { // 获取
			if (menu_display) { // 如果 Menu已经打开 ，先关闭Menu
				menuWindow.dismiss();
				menu_display = false;
			} else {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, ExitFromSettings.class);
				startActivity(intent);
			}
		} else if (keyCode == KeyEvent.KEYCODE_MENU) { // 获取 Menu键
			if (!menu_display) {
				// 获取LayoutInflater实例
				inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
				// 这里的main布局是在inflate中加入的哦，以前都是直接this.setContentView()的吧？呵呵
				// 该方法返回的是一个View的对象，是布局中的根
				layout = inflater.inflate(R.layout.main_menu, null);
				// 下面我们要考虑了，我怎样将我的layout加入到PopupWindow中呢？？？很简单
				menuWindow = new PopupWindow(layout, LayoutParams.FILL_PARENT,
						LayoutParams.WRAP_CONTENT); // 后两个参数是width和height
				 menuWindow.showAsDropDown(layout); //设置弹出效果
				// menuWindow.showAsDropDown(null, 0, layout.getHeight());
				menuWindow.showAtLocation(this.findViewById(R.id.mainweixin),
						Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); // 设置layout在PopupWindow中显示的位置
				// 如何获取我们main中的控件呢？也很简单
				mClose = (LinearLayout) layout.findViewById(R.id.menu_close);
				mCloseBtn = (LinearLayout) layout.findViewById(R.id.menu_close_btn);
				// 下面对每一个Layout进行单击事件的注册吧。。。
				// 比如单击某个MenuItem的时候，他的背景色改变
				// 事先准备好一些背景图片或者颜色
				mCloseBtn.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View arg0) {
						Intent intent = new Intent();
						intent.setClass(MainActivity.this, ExitFromSettings.class);
						startActivity(intent);
						menuWindow.dismiss(); // 响应点击事件之后关闭Menu
					}
				});
				menu_display = true;
			} else {
				// 如果当前已经为显示状态，则隐藏起来
				menuWindow.dismiss();
				menu_display = false;
			}
			return false;
		}
		return false;
	}

	private void invOnce() {
		if(null == ReaderUtil.readers){
			Toasts.makeTextShort(getApplicationContext(), R.string.msg_please_connect_the_device);
			return;
		}
		readerService.invOnceV2(ReaderUtil.readers, new ReadData());
	}
	
	private void clearData() {
		tvBasicAmount.setText("");
		tvBasicTime.setText("");
		listViewData.setAdapter(null);
		listEPC.removeAll(listEPC);
		showCount();
	}

	private void stop() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if(null == ReaderUtil.readers){
					Toasts.makeTextShort(getApplicationContext(), R.string.msg_please_connect_the_device);
					return;
				}
				readerService.stopInvV2(ReaderUtil.readers, new StopReadData());
			}
		});
	}

	private void beginInv() {
		if(null == ReaderUtil.readers){
			Toasts.makeTextShort(getApplicationContext(), R.string.msg_please_connect_the_device);
			return;
		}
		readerService.beginInvV2(ReaderUtil.readers, new ReadData());
	}
	
	private void oldInvOnce() {
		if(null == ReaderUtil.readers){
			Toasts.makeTextShort(this, R.string.msg_please_connect_the_device);
			return;
		}
		readerService.invOnce(ReaderUtil.readers, new ReadData());
	}
	
	private void oldBeginInv() {
		if(null == ReaderUtil.readers){
			Toasts.makeTextShort(this, R.string.msg_please_connect_the_device);
			return;
		}
		readerService.beginInv(ReaderUtil.readers, new ReadData());
	}
	
	private void oldStop() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if(null == ReaderUtil.readers){
					Toasts.makeTextShort(getApplicationContext(), R.string.msg_please_connect_the_device);
					return;
				}
				readerService.stopInv(ReaderUtil.readers, new StopReadData());
			}
		});
	}

	private void showCount() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				tagAmount = 0;
				tagCount = 0;
				for (int i = 0; i < listEPC.size(); i++) {
					EPC epc = listEPC.get(i);
					tagAmount += epc.getCount();
					for (int j = 0; j < i && i > 0; j++) {
						EPC epc2 = listEPC.get(j);
						if (epc.getEpc().equals(epc2.getEpc())) {
							tagCount++;
						}
					}
				}
				tvBasicAmount.setText(listEPC.size() - tagCount + "");
				tvBasicTime.setText(tagAmount + "");
			}
		});
	}

	

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onStop() {
		stop();
		super.onStop();
	}

	private void addToList(final List<EPC> listEPC2, final String epc,final String ant) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				DataFilter.dataFilter(listEPC2, epc, ant);
				listViewAdapterWithViewHolder = new ListViewAdapterWithViewHolder(getApplicationContext(), listEPC);
				listViewData.setAdapter(listViewAdapterWithViewHolder);
			}
		});
		showCount();
	}
	
	private void addToList(final List<EPC> listEPC2, final String epc,final String rssi,final String ant,final String deviceNo,final String direction) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				DataFilter.dataFilter(listEPC2, epc,rssi, ant,deviceNo,direction);
				listViewAdapterWithViewHolder = new ListViewAdapterWithViewHolder(getApplicationContext(), listEPC);
				listViewData.setAdapter(listViewAdapterWithViewHolder);
			}
		});
		showCount();
	}

	class ReadData implements CallBack {
		@Override
		public void readData(String data, String antNo) {
			addToList(listEPC, data, antNo);
		}

		@Override
		public void readData(String data, String rssi, String antNo,String deviceNo,String direction) {
			addToList(listEPC, data,rssi, antNo,deviceNo,direction);
		}
	}

	class StopReadData implements CallBackStopReadCard {
		@Override
		public void stopReadCard(final boolean result) {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					if (result) {
						Toasts.makeTextShort(getApplicationContext(), R.string.msg_stop_read_tag_succeed);
					} else {
						Toasts.makeTextShort(getApplicationContext(), R.string.msg_stop_read_tag_failure);
					}
				}
			});
		}
	}
	
	/*****************************************************jump pager*************************************************************/

	public void btn_main_right(View v) {
		Intent intent = new Intent(MainActivity.this, MainTopRightDialog.class);
		startActivity(intent);
	}

	public void exit_settings(View v) { 
		Intent intent = new Intent(MainActivity.this, ExitFromSettings.class);
		startActivity(intent);
	}

	// tag operation start
	public void btn_designated_area_read_and_write(View v) {
		Intent intent = new Intent(MainActivity.this,DesignatedAreaReadAndWriteActivity.class);
		startActivity(intent);
	}

	public void btn_tag_lock_and_unlock(View v) {
		Intent intent = new Intent(MainActivity.this,
				TagLockUnlockActivity.class);
		startActivity(intent);
	}

	public void btn_tag_destroy(View v) {
		Intent intent = new Intent(MainActivity.this, TagDestroyActivity.class);
		startActivity(intent);
	}
	//  tag operation end
	
	//  params set start
	public void btn_work_pattern(View v) {
		Intent intent = new Intent(MainActivity.this, WorkPatternActivity.class);
		startActivity(intent);
	}

	public void btn_communication_mode_r2k(View v) {
		Intent intent = new Intent(MainActivity.this,CommunicationModeActivity.class);
		startActivity(intent);
	}

	public void btn_antenna4_channel(View v) {
		Intent intent = new Intent(MainActivity.this,Antenna4ChannelActivity.class);
		startActivity(intent);
	}

	public void btn_antenna16_channel(View v) {
		Intent intent = new Intent(MainActivity.this,Antenna16ChannelActivity.class);
		startActivity(intent);
	}

	public void btn_antenna32_channel(View v) {
		Intent intent = new Intent(MainActivity.this,Antenna32ChannelActivity.class);
		startActivity(intent);
	}

	public void btn_device_number(View v) {
		Intent intent = new Intent(MainActivity.this,DeviceNumberActivity.class);
		startActivity(intent);
	}
	
	public void btn_get_version(View v){
		Intent intent = new Intent(MainActivity.this,VersionActivity.class);
		startActivity(intent);
	}

	public void btn_buzzer_set(View v) {
		Intent intent = new Intent(MainActivity.this, BuzzerSetActivity.class);
		startActivity(intent);
	}

	public void btn_adjacent_discriminant(View v) {
		Intent intent = new Intent(MainActivity.this,AdjacentDiscrimnantActivity.class);
		startActivity(intent);
	}
	
	public void btn_special_read_tag_pattern(View v) {
		Intent intent = new Intent(MainActivity.this,SpecialReadTagPatternActivity.class);
		startActivity(intent);
	}
	
	public void btn_factory_data_reset(View v) {
		Intent intent = new Intent(MainActivity.this,FactoryDataResetActivity.class);
		startActivity(intent);
	}
	
	public void btn_inv_card_data_output_format(View v) {
		Intent intent = new Intent(MainActivity.this,InvCardDataOutputFormatActivity.class);
		startActivity(intent);
	}
	
	public void btn_operating_frequency(View v) {
		Intent intent = new Intent(MainActivity.this,OperatingFrequencySetActivity.class);
		startActivity(intent);
	}
	
}