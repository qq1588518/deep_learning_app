package com.deeplearning.app.activity;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.deeplearning.app.DLApplication;
import com.deeplearning.app.config.Config;
import com.deeplearning.app.job.WechatAccessbilityJob;
import com.deeplearning.app.util.BitmapUtils;
import com.deeplearning_app.R;

import java.io.File;

/**
 * Created by qq1588518 on 17/12/01.
 */
public class AboutMeActivity extends BaseActivity {
    private static final String TAG = "AboutMeActivity";
    private WebView mWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Config.DEBUG) {
            Log.d(TAG, "onCreate");
        }
        setContentView(R.layout.activity_about);

        mWebView = (WebView) findViewById(R.id.webview);
        WebSettings settings = mWebView.getSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if(url.startsWith("http")) {
                    view.loadUrl(url);
                    return true;
                }
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return false;
            }
        });

        mWebView.loadUrl(getString(R.string.about_url));

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        if(Config.DEBUG) {
            Log.d(TAG, "onSupportNavigateUp");
        }
        finish();
        return true;
    }

    /** 显示捐赠的对话框*/
    public void showDonateDialog() {
        if(Config.DEBUG) {
            Log.i(TAG, "showDonateDialog");
        }
        final Dialog dialog = new Dialog(this, R.style.QR_Dialog_Theme);
        View view = getLayoutInflater().inflate(R.layout.donate_dialog_layout, null);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        view.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                File output = new File(android.os.Environment.getExternalStorageDirectory(), "codeboy_wechatpay_qr.jpg");
                if(!output.exists()) {
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.wechatpay_qr);
                    BitmapUtils.saveBitmap(AboutMeActivity.this, output, bitmap);
                }
                Toast.makeText(AboutMeActivity.this, "已保存到:" + output.getAbsolutePath(), Toast.LENGTH_LONG).show();
                return true;
            }
        });
        dialog.setContentView(view);
        dialog.show();
    }

    /** 显示二维码的对话框*/
    public void showQrDialog() {
        if(Config.DEBUG) {
            Log.i(TAG, "showQrDialog");
        }
        final Dialog dialog = new Dialog(this, R.style.QR_Dialog_Theme);
        View view = getLayoutInflater().inflate(R.layout.qr_dialog_layout, null);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = getString(R.string.qr_wx_id);
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("label", id);
                clipboardManager.setPrimaryClip(clip);

                //跳到微信
                Intent wxIntent = getPackageManager().getLaunchIntentForPackage(
                        WechatAccessbilityJob.WECHAT_PACKAGENAME);
                if(wxIntent != null) {
                    try {
                        startActivity(wxIntent);
                    } catch (Exception e){}
                }

                Toast.makeText(getApplicationContext(), "已复制到粘贴板", Toast.LENGTH_LONG).show();
                DLApplication.eventStatistics(AboutMeActivity.this, "copy_qr");
                dialog.dismiss();
            }
        });
        dialog.setContentView(view);
        dialog.show();
    }

    /** 分享*/
    public void showShareDialog() {
        if(Config.DEBUG) {
            Log.i(TAG, "showShareDialog");
        }
        DLApplication.showShare(this);
    }
}
