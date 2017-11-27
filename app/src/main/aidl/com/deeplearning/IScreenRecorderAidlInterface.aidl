// IMyAidlInterface.aidl
package com.deeplearning;

// Declare any non-default types here with import statements
import com.deeplearning.model.DanmakuBean;

interface IScreenRecorderAidlInterface {

    void sendDanmaku(in List<DanmakuBean> danmakuBean);

    void startScreenRecord(in Intent bundleData);

}
