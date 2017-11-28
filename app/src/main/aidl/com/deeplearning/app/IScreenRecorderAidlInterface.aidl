// IMyAidlInterface.aidl
package com.deeplearning.app;

// Declare any non-default types here with import statements
import com.deeplearning.app.model.DanmakuBean;

interface IScreenRecorderAidlInterface {

    void sendDanmaku(in List<DanmakuBean> danmakuBean);

    void startScreenRecord(in Intent bundleData);

}
