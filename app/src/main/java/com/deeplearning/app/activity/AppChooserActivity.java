package com.deeplearning.app.activity;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import com.deeplearning.app.adapter.ListAdapter;

import com.deeplearning.app.config.Config;
import com.deeplearning_app.R;

/**
 * Created by qq1588518 on 17/12/01.
 */
public class AppChooserActivity extends ListActivity {
    private static final String TAG = "AppChooserActivity";
    private PackageManager packageManager = null;
    private List<ApplicationInfo> applist = null;
    private ListAdapter listadapter = null;

    private String selectedApp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Config.DEBUG) {
            Log.d(TAG, "onCreate");
        }
//        setContentView(R.layout.list_main);
        packageManager = getPackageManager();
        new LoadApplications().execute();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        if(Config.DEBUG) {
            Log.d(TAG, "onListItemClick");
        }
        ApplicationInfo app = applist.get(position);
        try {
            selectedApp = app.packageName;
            finish();
        } catch (ActivityNotFoundException e) {
            Toast.makeText(AppChooserActivity.this, e.getMessage(),
                    Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(AppChooserActivity.this, e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void finish() {
        if(Config.DEBUG) {
            Log.d(TAG, "finish");
        }
        // Prepare data intent
        Intent data = new Intent();
        data.putExtra("appname", selectedApp);
        // Activity finished ok, return the data
        setResult(RESULT_OK, data);
        super.finish();
    }

    private List<ApplicationInfo> checkForLaunchIntent(List<ApplicationInfo> list) {
        if(Config.DEBUG) {
            Log.d(TAG, "checkForLaunchIntent");
        }
        ArrayList<ApplicationInfo> applist = new ArrayList<ApplicationInfo>();
        for (ApplicationInfo info : list) {
            try {
                if (null != packageManager.getLaunchIntentForPackage(info.packageName)) {
                    applist.add(info);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return applist;
    }


    private class LoadApplications extends AsyncTask<Void, Void, Void> {
        private ProgressDialog progress = null;

        @Override
        protected Void doInBackground(Void... params) {
            if(Config.DEBUG) {
                Log.d(TAG, "LoadApplications doInBackground");
            }
            applist = checkForLaunchIntent(packageManager.getInstalledApplications(PackageManager.GET_META_DATA));
            listadapter = new ListAdapter(AppChooserActivity.this,
                    R.layout.list_row, applist);

            return null;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            if(Config.DEBUG) {
                Log.d(TAG, "LoadApplications onCancelled");
            }
        }

        @Override
        protected void onPostExecute(Void result) {
            if(Config.DEBUG) {
                Log.d(TAG, "LoadApplications onPostExecute");
            }
            setListAdapter(listadapter);
            progress.dismiss();
            super.onPostExecute(result);
        }

        @Override
        protected void onPreExecute() {
            if(Config.DEBUG) {
                Log.d(TAG, "LoadApplications onPreExecute");
            }
            progress = ProgressDialog.show(AppChooserActivity.this, null,
                    "Loading application info...");
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            if(Config.DEBUG) {
                Log.d(TAG, "LoadApplications onProgressUpdate");
            }
        }
    }
}