package com.deeplearning.app.job;

import android.content.Context;

import com.deeplearning.app.config.Config;
import com.deeplearning.app.service.QiangHongBaoService;

/**
 * Created by qq1588518 on 17/12/01.
 */
public abstract class BaseAccessbilityJob implements AccessbilityJob {

    private QiangHongBaoService service;

    @Override
    public void onCreateJob(QiangHongBaoService service) {
        this.service = service;
    }

    public Context getContext() {
        return service.getApplicationContext();
    }

    public Config getConfig() {
        return service.getConfig();
    }

    public QiangHongBaoService getService() {
        return service;
    }
}
