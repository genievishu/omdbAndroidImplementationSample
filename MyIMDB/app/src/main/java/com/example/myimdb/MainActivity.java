package com.example.myimdb;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myimdb.utils.LoadSimulator;
import com.example.myimdb.utils.PostLoadExecutor;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {

    // Load simulator
    private WeakReference<LoadSimulator> mLoadSimulatorReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Loading app resources if required
        handleAppResourceLoad();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void handleAppResourceLoad() {
        // TODO : Implement any on splash loads
        mLoadSimulatorReference = new WeakReference<>(new LoadSimulator(new PostLoadExecutor() {
            @Override
            public void handlePostExecute() {
                Intent homePageIntent = new Intent(MainActivity.this, HomePageActivity.class);
                startActivity(homePageIntent);
                finish();
            }
        }));
        mLoadSimulatorReference.get().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Void) null);
    }

    @Override
    protected void onDestroy() {
        if (null!=mLoadSimulatorReference) {
            mLoadSimulatorReference.clear();
        }
        super.onDestroy();
    }
}
