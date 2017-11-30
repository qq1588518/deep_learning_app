package com.deeplearning.app.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;
import com.jpeng.jptabbar.BadgeDismissListener;
import com.jpeng.jptabbar.JPTabBar;
import com.jpeng.jptabbar.OnTabSelectListener;
import com.jpeng.jptabbar.anno.NorIcons;
import com.jpeng.jptabbar.anno.SeleIcons;
import com.jpeng.jptabbar.anno.Titles;

import java.util.ArrayList;
import java.util.List;
import com.deeplearning_app.R;

public class MainActivity extends AppCompatActivity implements BadgeDismissListener, OnTabSelectListener{

    @Titles
    private static final String[] mTitles = {"页面一","页面二","页面三","页面四"};

    @SeleIcons
    private static final int[] mSeleIcons = {R.drawable.tab1_selected,R.drawable.tab2_selected,R.drawable.tab3_selected,R.drawable.tab4_selected};

    @NorIcons
    private static final int[] mNormalIcons = {R.drawable.tab1_normal, R.drawable.tab2_normal, R.drawable.tab3_normal, R.drawable.tab4_normal};

    private List<Fragment> list = new ArrayList<>();

    private ViewPager mPager;

    private JPTabBar mTabbar;

    private Tab1Pager mTab1;

    private Tab2Pager mTab2;

    private Tab3Pager mTab3;

    private Tab4Pager mTab4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTabbar = (JPTabBar) findViewById(R.id.tabbar);
        mPager = (ViewPager) findViewById(R.id.view_pager);
//        mTabbar.setTitles("qwe","asd","qwe","asdsa").setNormalIcons(R.drawable.tab1_normal,R.drawable.tab2_normal,R.drawable.tab3_normal,R.drawable.tab4_normal)
//                .setSelectedIcons(R.drawable.tab1_selected,R.drawable.tab2_selected,R.drawable.tab3_selected,R.drawable.tab4_selected).generate();
        mTab1 = new Tab1Pager();
        mTab2 = new Tab2Pager();
        mTab3 = new Tab3Pager();
        mTabbar.setTabListener(this);
        mTab4 = new Tab4Pager();
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
                Toast.makeText(MainActivity.this,"中间点击",Toast.LENGTH_SHORT).show();;
            }
        });
    }

    @Override
    public void onDismiss(int position) {
        if (position == 0) {
            mTab1.clearCount();
        }
    }


    @Override
    public void onTabSelect(int index) {

    }


    public JPTabBar getTabbar() {
        return mTabbar;
    }


}
