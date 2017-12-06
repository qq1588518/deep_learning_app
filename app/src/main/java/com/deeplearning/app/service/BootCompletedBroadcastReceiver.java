package com.deeplearning.app.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.deeplearning.app.activity.MainActivity;
/**
 * Created by qq1588518 on 2017/12/6.
 */

public class BootCompletedBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")){
            Intent i = new Intent(context, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
            Intent newBaseAccessibilityServiceIntent = new Intent(context,BaseAccessibilityService.class);
            context.startService(newBaseAccessibilityServiceIntent);
            Intent newBaseNotificationServiceIntent = new Intent(context,BaseNotificationService.class);
            context.startService(newBaseNotificationServiceIntent);
        }
    }

}
