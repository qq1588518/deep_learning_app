package com.deeplearning.app.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.deeplearning.app.DLApplication;
import com.deeplearning.app.config.Config;
import com.deeplearning.app.fragment.Tab1PagerFragment;
import com.deeplearning.app.fragment.Tab2PagerFragment;
import com.deeplearning.app.fragment.Tab3PagerFragment;
import com.deeplearning.app.fragment.Tab4PagerFragment;
import com.deeplearning.app.adapter.MainFragmentPagerAdapter;
import com.jpeng.jptabbar.BadgeDismissListener;
import com.jpeng.jptabbar.JPTabBar;
import com.jpeng.jptabbar.OnTabSelectListener;
import com.jpeng.jptabbar.anno.NorIcons;
import com.jpeng.jptabbar.anno.SeleIcons;
import com.jpeng.jptabbar.anno.Titles;

import java.util.ArrayList;
import java.util.List;
import com.deeplearning_app.R;

public class MainActivity extends BaseActivity implements BadgeDismissListener, OnTabSelectListener{
    private static final String TAG = "MainActivity";
    @Titles
    private static final String[] mTitles = {"抢红包","抢车票","秒购物","玩游戏"};

    @SeleIcons
    private static final int[] mSeleIcons = {R.drawable.tab1_selected,R.drawable.tab2_selected,R.drawable.tab3_selected,R.drawable.tab4_selected};

    @NorIcons
    private static final int[] mNormalIcons = {R.drawable.tab1_normal, R.drawable.tab2_normal, R.drawable.tab3_normal, R.drawable.tab4_normal};

    private List<Fragment> list = new ArrayList<>();

    private ViewPager mPager;

    private JPTabBar mTabbar;

    private Tab1PagerFragment mTab1;

    private Tab2PagerFragment mTab2;

    private Tab3PagerFragment mTab3;

    private Tab4PagerFragment mTab4;

    public JPTabBar getTabbar() {
        return mTabbar;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTabbar = (JPTabBar) findViewById(R.id.tabbar);
        mPager = (ViewPager) findViewById(R.id.view_pager);
//        mTabbar.setTitles("qwe","asd","qwe","asdsa").setNormalIcons(R.drawable.tab1_normal,R.drawable.tab2_normal,R.drawable.tab3_normal,R.drawable.tab4_normal)
//                .setSelectedIcons(R.drawable.tab1_selected,R.drawable.tab2_selected,R.drawable.tab3_selected,R.drawable.tab4_selected).generate();
        mTab1 = new Tab1PagerFragment();
        mTab2 = new Tab2PagerFragment();
        mTab3 = new Tab3PagerFragment();
        mTab4 = new Tab4PagerFragment();
        mTabbar.setTabListener(this);

        list.add(mTab1);
        list.add(mTab2);
        list.add(mTab3);
        list.add(mTab4);
        mPager.setAdapter(new MainFragmentPagerAdapter(getSupportFragmentManager(),list));
        mTabbar.setContainer(mPager);
        mTabbar.setDismissListener(this);
        //显示圆点模式的徽章
        //设置容器
        //mTabbar.showBadge(1, 50);
        //设置Badge消失的代理
        mTabbar.setTabListener(this);
        mTabbar.setUseScrollAnimate(true);
        mTabbar.getMiddleView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"深度学习",Toast.LENGTH_SHORT).show();;
            }
        });

        DLApplication.activityStartMain(this);

        IntentFilter filter = new IntentFilter();
        filter.addAction(Config.ACTION_QIANGHONGBAO_SERVICE_CONNECT);
        filter.addAction(Config.ACTION_QIANGHONGBAO_SERVICE_DISCONNECT);
        filter.addAction(Config.ACTION_NOTIFY_LISTENER_SERVICE_DISCONNECT);
        filter.addAction(Config.ACTION_NOTIFY_LISTENER_SERVICE_CONNECT);
        registerReceiver(qhbConnectReceiver, filter);
    }

    @Override
    public void onDismiss(int position) {
        if (position == 0) {
            mTab2.clearCount();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(Config.DEBUG) {
            Log.i(TAG, "onDestroy");
        }
        try {
            unregisterReceiver(qhbConnectReceiver);
        } catch (Exception e) {}
    }

    @Override
    public void onTabSelect(int index) {
        switch (index) {
            case 0:
                //startActivity(new Intent(MainActivity.this, WechatRedenvelopeActivity.class));
                Toast.makeText(MainActivity.this,"Tab" + index,Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(MainActivity.this,"Tab" + index,Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(MainActivity.this, NotifySettingsActivity.class));
                break;
            case 3:
                Toast.makeText(MainActivity.this,"Tab" + index,Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(MainActivity.this, WechatSettingsActivity.class));
                break;
            case 4:
                Toast.makeText(MainActivity.this,"Tab" + index,Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(MainActivity.this, WechatSettingsActivity.class));
                break;
        }
    }

    private BroadcastReceiver qhbConnectReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(isFinishing()) {
                return;
            }
            String action = intent.getAction();
            Log.d(TAG, "receive-->" + action);
            if(Config.ACTION_QIANGHONGBAO_SERVICE_CONNECT.equals(action)) {
                mTab1.dismiss();
            }
            else if(Config.ACTION_QIANGHONGBAO_SERVICE_DISCONNECT.equals(action)) {
                mTab1.showOpenAccessibilityServiceDialog();
            }
            else if(Config.ACTION_NOTIFY_LISTENER_SERVICE_CONNECT.equals(action)) {
                if(mTab1 != null) {
                    mTab1.updateNotifyPreference();
                }
            }
            else if(Config.ACTION_NOTIFY_LISTENER_SERVICE_DISCONNECT.equals(action)) {
                if(mTab1 != null) {
                    mTab1.updateNotifyPreference();
                }
            }
        }
    };


}
