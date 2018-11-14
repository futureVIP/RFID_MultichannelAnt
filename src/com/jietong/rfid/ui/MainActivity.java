package com.jietong.rfid.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
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
import com.jietong.rfid.uhf.service.ReaderService;
import com.jietong.rfid.uhf.service.impl.ReaderServiceImpl;
import com.jietong.rfid.uhf.tool.CallBack;
import com.jietong.rfid.uhf.tool.CallBackStopReadCard;
import com.jietong.rfid.uhf.tool.ReaderUtil;
import com.jietong.rfid.ui.basic_operation.ListViewAdapterWithViewHolder;
import com.jietong.rfid.ui.other_operation.AdjacentDiscrimnantActivity;
import com.jietong.rfid.ui.other_operation.BuzzerSetActivity;
import com.jietong.rfid.ui.other_operation.UsbDeliveryOutletActivity;
import com.jietong.rfid.ui.params_set.Antenna16ChannelActivity;
import com.jietong.rfid.ui.params_set.Antenna32ChannelActivity;
import com.jietong.rfid.ui.params_set.Antenna4ChannelActivity;
import com.jietong.rfid.ui.params_set.CommunicationModeActivity;
import com.jietong.rfid.ui.params_set.DeviceNumberActivity;
import com.jietong.rfid.ui.params_set.FrequencySetActivity;
import com.jietong.rfid.ui.params_set.PowerSetActivity;
import com.jietong.rfid.ui.params_set.ReadTagPatternActivity;
import com.jietong.rfid.ui.params_set.WorkPatternActivity;
import com.jietong.rfid.ui.system.Exit;
import com.jietong.rfid.ui.system.ExitFromSettings;
import com.jietong.rfid.ui.system.MainTopRightDialog;
import com.jietong.rfid.ui.system.ShakeActivity;
import com.jietong.rfid.ui.tag_operation.DesignatedAreaReadAndWriteActivity;
import com.jietong.rfid.ui.tag_operation.TagDestroyActivity;
import com.jietong.rfid.ui.tag_operation.TagLockUnlockActivity;
import com.jietong.rfid.util.Toasts;

public class MainActivity extends Activity implements OnClickListener {

	public static MainActivity instance = null;
	private ViewPager mTabPager;
	private ImageView mTabImg;// 动画图片
	private ImageView mTab1, mTab2, mTab3, mTab4;
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
	private Button btnBeginInv;
	private Button btnBasicStop;
	private Button btnBasicClear;
	private ArrayList<Map<String, Object>> listMap;
	private int tagAmount = 0;
	private int tagCount = 0;
	public TextView tvBasicAmount;
	public TextView tvBasicTime;
	// ListView
	private ListView listViewData;
	private List<EPC> listEPC;
	// private Button mRightBtn;
	RelativeLayout rlAntenna4channel;
	RelativeLayout rlAntenna16channel;
	RelativeLayout rlAntenna32channel;
	RelativeLayout rlAdjacentDiscriminant;
	private ListViewAdapterWithViewHolder listViewAdapterWithViewHolder;
	ReaderService readerService = new ReaderServiceImpl();

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
		Display currDisplay = getWindowManager().getDefaultDisplay();
		int displayWidth = currDisplay.getWidth();
		int displayHeight = currDisplay.getHeight();
		one = displayWidth / 4; 
		two = one * 2;
		three = one * 3;
		btnBeginInv.setOnClickListener(this);
		btnBasicStop.setOnClickListener(this);
		btnBasicClear.setOnClickListener(this);
	}

	private void controlInital() {
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		instance = this;
		listEPC = new ArrayList<EPC>();
		mTabPager = (ViewPager) findViewById(R.id.vp_main_tab_pager);
		mTab1 = (ImageView) findViewById(R.id.img_weixin);
		mTab2 = (ImageView) findViewById(R.id.img_address);
		mTab3 = (ImageView) findViewById(R.id.img_friends);
		mTab4 = (ImageView) findViewById(R.id.img_settings);
		mTabImg = (ImageView) findViewById(R.id.img_tab_now);
		LayoutInflater mLi = LayoutInflater.from(this);
		View view1 = mLi.inflate(R.layout.amain_tab_basic_opera, null);
		View view2 = mLi.inflate(R.layout.amain_tab_tag_opera, null);
		View view3 = mLi.inflate(R.layout.amain_tab_params_settings, null);
		View view4 = mLi.inflate(R.layout.amain_tab_other_settings, null);
		basicOpera(view1);
		tagOpera(view2);
		paramsSet(view3);
		otherOpera(view4);
		final ArrayList<View> views = new ArrayList<View>();
		views.add(view1);
		views.add(view2);
		views.add(view3);
		views.add(view4);
		PagerAdapter mPagerAdapter = new PagerAdapter() {
			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1;
			}

			@Override
			public int getCount() {
				return views.size();
			}

			@Override
			public void destroyItem(View container, int position, Object object) {
				((ViewPager) container).removeView(views.get(position));
			}

			// @Override
			// public CharSequence getPageTitle(int position) {
			// return titles.get(position);
			// }

			@Override
			public Object instantiateItem(View container, int position) {
				((ViewPager) container).addView(views.get(position));
				return views.get(position);
			}
		};
		mTabPager.setAdapter(mPagerAdapter);
	}

	private void basicOpera(View view1) {
		listViewData = (ListView) view1.findViewById(R.id.listView_data);
		tvBasicAmount = (TextView) view1.findViewById(R.id.tv_basic_amount);
		tvBasicTime = (TextView) view1.findViewById(R.id.tv_basic_time);
		btnBeginInv = (Button) view1.findViewById(R.id.btn_basic_beginInv);
		btnBasicStop = (Button) view1.findViewById(R.id.btn_basic_stop);
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
	};

	public void antennaShowChannel(){
//		rlAntenna4channel.setVisibility(View.GONE);
//		rlAntenna16channel.setVisibility(View.GONE);
//		rlAntenna32channel.setVisibility(View.GONE);
		rlAntenna4channel.setVisibility(View.VISIBLE);
		rlAntenna16channel.setVisibility(View.VISIBLE);
		rlAntenna32channel.setVisibility(View.VISIBLE);
		if (null != ReaderUtil.readers) {
			if (ReaderUtil.readers.getChannel() == 4
					|| ReaderUtil.readers.getChannel() == 6) {
				rlAntenna4channel.setVisibility(View.VISIBLE);
			} else if (ReaderUtil.readers.getChannel() == 16) {
				rlAntenna16channel.setVisibility(View.VISIBLE);
			} else if (ReaderUtil.readers.getChannel() == 32) {
				rlAntenna32channel.setVisibility(View.VISIBLE);
			}
		}
	}
	
	public class MyOnPageChangeListener implements OnPageChangeListener {
		@Override
		public void onPageSelected(int arg0) {
			Animation animation = null;
			antennaShowChannel();
			switch (arg0) {
			case 0:
				int tab_weixin_pressed = R.drawable.tab_weixin_pressed;
				mTab1.setImageDrawable(getResources().getDrawable(tab_weixin_pressed));
				if (currIndex == 1) {
					animation = new TranslateAnimation(one, 0, 0, 0);
					int tab_address_normal = R.drawable.tab_address_normal;
					mTab2.setImageDrawable(getResources().getDrawable(tab_address_normal));
				} else if (currIndex == 2) {
					animation = new TranslateAnimation(two, 0, 0, 0);
					mTab3.setImageDrawable(getResources().getDrawable(R.drawable.tab_find_frd_normal));
				} else if (currIndex == 3) {
					animation = new TranslateAnimation(three, 0, 0, 0);
					mTab4.setImageDrawable(getResources().getDrawable(R.drawable.tab_settings_normal));
				}
				break;
			case 1:
				mTab2.setImageDrawable(getResources().getDrawable(
						R.drawable.tab_address_pressed));
				if (currIndex == 0) {
					animation = new TranslateAnimation(zero, one, 0, 0);
					mTab1.setImageDrawable(getResources().getDrawable(
							R.drawable.tab_weixin_normal));
				} else if (currIndex == 2) {
					animation = new TranslateAnimation(two, one, 0, 0);
					mTab3.setImageDrawable(getResources().getDrawable(
							R.drawable.tab_find_frd_normal));
				} else if (currIndex == 3) {
					animation = new TranslateAnimation(three, one, 0, 0);
					mTab4.setImageDrawable(getResources().getDrawable(
							R.drawable.tab_settings_normal));
				}
				break;
			case 2:
				mTab3.setImageDrawable(getResources().getDrawable(
						R.drawable.tab_find_frd_pressed));
				if (currIndex == 0) {
					animation = new TranslateAnimation(zero, two, 0, 0);
					mTab1.setImageDrawable(getResources().getDrawable(
							R.drawable.tab_weixin_normal));
				} else if (currIndex == 1) {
					animation = new TranslateAnimation(one, two, 0, 0);
					mTab2.setImageDrawable(getResources().getDrawable(
							R.drawable.tab_address_normal));
				} else if (currIndex == 3) {
					animation = new TranslateAnimation(three, two, 0, 0);
					mTab4.setImageDrawable(getResources().getDrawable(
							R.drawable.tab_settings_normal));
				}
				break;
			case 3:
				mTab4.setImageDrawable(getResources().getDrawable(R.drawable.tab_settings_pressed));
				if (currIndex == 0) {
					animation = new TranslateAnimation(zero, three, 0, 0);
					mTab1.setImageDrawable(getResources().getDrawable(
							R.drawable.tab_weixin_normal));
				} else if (currIndex == 1) {
					animation = new TranslateAnimation(one, three, 0, 0);
					mTab2.setImageDrawable(getResources().getDrawable(
							R.drawable.tab_address_normal));
				} else if (currIndex == 2) {
					animation = new TranslateAnimation(two, three, 0, 0);
					mTab3.setImageDrawable(getResources().getDrawable(
							R.drawable.tab_find_frd_normal));
				}
				break;
			}
			currIndex = arg0;
			animation.setFillAfter(true);// True:图片停在动画结束位置
			animation.setDuration(150);
			mTabImg.startAnimation(animation);
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
				intent.setClass(MainActivity.this, Exit.class);
				startActivity(intent);
			}
		} else if (keyCode == KeyEvent.KEYCODE_MENU) { // 获取 Menu键
			if (!menu_display) {
				// 获取LayoutInflater实例
				inflater = (LayoutInflater) this
						.getSystemService(LAYOUT_INFLATER_SERVICE);
				// 这里的main布局是在inflate中加入的哦，以前都是直接this.setContentView()的吧？呵呵
				// 该方法返回的是一个View的对象，是布局中的根
				layout = inflater.inflate(R.layout.main_menu, null);
				// 下面我们要考虑了，我怎样将我的layout加入到PopupWindow中呢？？？很简单
				menuWindow = new PopupWindow(layout, LayoutParams.FILL_PARENT,
						LayoutParams.WRAP_CONTENT); // 后两个参数是width和height
				// menuWindow.showAsDropDown(layout); //设置弹出效果
				// menuWindow.showAsDropDown(null, 0, layout.getHeight());
				menuWindow.showAtLocation(this.findViewById(R.id.mainweixin),
						Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); // 设置layout在PopupWindow中显示的位置
				// 如何获取我们main中的控件呢？也很简单
				mClose = (LinearLayout) layout.findViewById(R.id.menu_close);
				mCloseBtn = (LinearLayout) layout
						.findViewById(R.id.menu_close_btn);

				// 下面对每一个Layout进行单击事件的注册吧。。。
				// 比如单击某个MenuItem的时候，他的背景色改变
				// 事先准备好一些背景图片或者颜色
				mCloseBtn.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View arg0) {
						// Toast.makeText(Main.this, "退出",
						// Toast.LENGTH_LONG).show();
						Intent intent = new Intent();
						intent.setClass(MainActivity.this, Exit.class);
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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_basic_beginInv:
			beginInv();
			break;
		case R.id.btn_basic_stop:
			stop();
			break;
		case R.id.btn_basic_clear:
			clearData();
			break;
		default:
			break;
		}
	}

	private void clearData() {
		tvBasicAmount.setText("");
		tvBasicTime.setText("");
		listViewData.setAdapter(null);
		listEPC.removeAll(listEPC);
		showCount();
	}

	private void stop() {
		if (null == ReaderUtil.readers) {
			return;
		}
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				readerService.stopInv(ReaderUtil.readers, new StopReadData());
			}
		});
	}

	private void beginInv() {
		if (null != ReaderUtil.readers) {
			readerService.beginInv(ReaderUtil.readers, new ReadData());
		}else{
			return;
		}
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

	public void dataFilter(List<EPC> listEPC, String epc, int ant) {
		if (listEPC.isEmpty()) {
			EPC epcTag = new EPC();
			epcTag.setId(1);
			epcTag.setEpc(epc);
			epcTag.setAnt(ant);
			epcTag.setCount(1);
			listEPC.add(epcTag);
		} else {
			for (int i = 0; i < listEPC.size(); i++) {
				int count = listEPC.size();
				EPC mEPC = listEPC.get(i);
				if (epc.equals(mEPC.getEpc()) && ant == mEPC.getAnt()) {
					mEPC.setCount(mEPC.getCount() + 1);
					listEPC.set(i, mEPC);
					break;
				} else if (i == (listEPC.size() - 1)) {
					count++;
					EPC newEPC = new EPC();
					newEPC.setId(count);
					newEPC.setEpc(epc);
					newEPC.setAnt(ant);
					newEPC.setCount(1);
					listEPC.add(newEPC);
				}
			}
		}
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

	private void addToList(final List<EPC> listEPC2, final String epc,final int ant) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				dataFilter(listEPC2, epc, ant);
				listViewAdapterWithViewHolder = new ListViewAdapterWithViewHolder(getApplicationContext(), listEPC);
				listViewData.setAdapter(listViewAdapterWithViewHolder);
			}
		});
		showCount();
	}

	class ReadData implements CallBack {
		@Override
		public void getReadData(String data, int antNo) {
			addToList(listEPC, data, antNo);
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

	public void btnmainright(View v) {
		Intent intent = new Intent(MainActivity.this, MainTopRightDialog.class);
		startActivity(intent);
	}

	public void exit_settings(View v) { 
		Intent intent = new Intent(MainActivity.this, ExitFromSettings.class);
		startActivity(intent);
	}

	public void btn_shake(View v) { 
		Intent intent = new Intent(MainActivity.this, ShakeActivity.class);
		startActivity(intent);
	}

	// tag operation start
	public void btn_designated_area_read_and_write(View v) {
		Intent intent = new Intent(MainActivity.this,
				DesignatedAreaReadAndWriteActivity.class);
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
		Intent intent = new Intent(MainActivity.this,
				CommunicationModeActivity.class);
		startActivity(intent);
	}

	public void btn_antenna4_channel(View v) {
		Intent intent = new Intent(MainActivity.this,
				Antenna4ChannelActivity.class);
		startActivity(intent);
	}

	public void btn_antenna16_channel(View v) {
		Intent intent = new Intent(MainActivity.this,
				Antenna16ChannelActivity.class);
		startActivity(intent);
	}

	public void btn_antenna32_channel(View v) {
		Intent intent = new Intent(MainActivity.this,
				Antenna32ChannelActivity.class);
		startActivity(intent);
	}

	public void btn_frequency_set(View v) {
		Intent intent = new Intent(MainActivity.this,
				FrequencySetActivity.class);
		startActivity(intent);
	}

	public void btn_device_number(View v) {
		Intent intent = new Intent(MainActivity.this,
				DeviceNumberActivity.class);
		startActivity(intent);
	}

	public void btn_power_set(View v) {
		Intent intent = new Intent(MainActivity.this, PowerSetActivity.class);
		startActivity(intent);
	}

	public void btn_read_tag_pattern(View v) {
		Intent intent = new Intent(MainActivity.this,
				ReadTagPatternActivity.class);
		startActivity(intent);
	}

	// params set end
	public void btn_usb_delivery_outlet(View v) {
		Intent intent = new Intent(MainActivity.this,
				UsbDeliveryOutletActivity.class);
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
}