package com.deeplearning.app.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.deeplearning_app.R;
import com.deeplearning.app.fragment.GrapRedEnvelopeFragment;
import com.deeplearning.app.fragment.GrapTrainTicketFragment;
import com.deeplearning.app.fragment.SeckillLowPriceFragment;
import com.deeplearning.app.fragment.AutoPlayGameFragment;
import com.deeplearning.app.fragment.PersonSettingFragment;
import com.deeplearning.app.widget.MainNavigateTabBar;


public class MainActivity extends AppCompatActivity {

    private static final String TAG_Page_GrapRedEnvelope = "抢红包";
    private static final String TAG_Page_GrapTrainTicket = "抢车票";
    private static final String TAG_Page_PersonSetting = "训练学习";
    private static final String TAG_Page_SeckillLowPrice = "秒低价";
    private static final String TAG_PAGE_AutoPlayGame = "玩游戏";


    private MainNavigateTabBar mNavigateTabBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigateTabBar = (MainNavigateTabBar) findViewById(R.id.mainTabBar);

        mNavigateTabBar.onRestoreInstanceState(savedInstanceState);

        mNavigateTabBar.addTab(GrapRedEnvelopeFragment.class, new MainNavigateTabBar.TabParam(R.drawable.comui_tab_home, R.drawable.comui_tab_home_selected, TAG_Page_GrapRedEnvelope));
        mNavigateTabBar.addTab(GrapTrainTicketFragment.class, new MainNavigateTabBar.TabParam(R.drawable.comui_tab_city, R.drawable.comui_tab_city_selected, TAG_Page_GrapTrainTicket));
        //mNavigateTabBar.addTab(PersonSettingFragment.class, new MainNavigateTabBar.TabParam(0, 0, TAG_Page_PersonSetting);
        mNavigateTabBar.addTab(SeckillLowPriceFragment.class, new MainNavigateTabBar.TabParam(R.drawable.comui_tab_message, R.drawable.comui_tab_message_selected, TAG_Page_SeckillLowPrice));
        mNavigateTabBar.addTab(AutoPlayGameFragment.class, new MainNavigateTabBar.TabParam(R.drawable.comui_tab_person, R.drawable.comui_tab_person_selected, TAG_PAGE_AutoPlayGame));
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mNavigateTabBar.onSaveInstanceState(outState);
    }


    public void onClickPublish(View v) {
        Toast.makeText(this, "发布", Toast.LENGTH_LONG).show();
    }
}
