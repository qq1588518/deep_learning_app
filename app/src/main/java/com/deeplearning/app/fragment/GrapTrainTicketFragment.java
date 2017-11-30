package com.deeplearning.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.deeplearning_app.R;

/**
 * User:Shine
 * Date:2015-10-20
 * Description:
 */
public class GrapTrainTicketFragment extends BaseRefreshFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_city, container, false);
    }
}
