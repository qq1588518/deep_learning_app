package com.deeplearning.app.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.deeplearning.app.fragment.Tab1PagerFragment;
import com.deeplearning.app.fragment.Tab2PagerFragment;
import com.deeplearning.app.fragment.Tab3PagerFragment;
import com.deeplearning.app.fragment.Tab4PagerFragment;
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
        mPager.setAdapter(new Adapter(getSupportFragmentManager(),list));
        mTabbar.setContainer(mPager);
        mTabbar.setDismissListener(this);
        //显示圆点模式的徽章
        //设置容器
        mTabbar.showBadge(0, 50);
        //设置Badge消失的代理
        mTabbar.setTabListener(this);
        mTabbar.setUseScrollAnimate(true);
        mTabbar.getMiddleView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"深度学习",Toast.LENGTH_SHORT).show();;
            }
        });
    }

    @Override
    public void onDismiss(int position) {
        if (position == 0) {
            mTab2.clearCount();
        }
    }


    @Override
    public void onTabSelect(int index) {
        Toast.makeText(MainActivity.this,"Tab" + index,Toast.LENGTH_SHORT).show();;
    }


    public JPTabBar getTabbar() {
        return mTabbar;
    }


}
