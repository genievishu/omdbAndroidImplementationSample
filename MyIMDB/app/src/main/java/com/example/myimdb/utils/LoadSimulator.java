package com.example.myimdb.utils;

import android.os.AsyncTask;

/**
 * Created by Vishu on 02-09-2019
 */
public class LoadSimulator extends AsyncTask<Void, Void, Void> {

    private PostLoadExecutor mPostLoadExecutor;

    public LoadSimulator(PostLoadExecutor postLoadExecutor) {
        this.mPostLoadExecutor = postLoadExecutor;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            // TODO : Implement logs
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (null!=mPostLoadExecutor) {
            mPostLoadExecutor.handlePostExecute();
        }
        super.onPostExecute(aVoid);
    }
}
