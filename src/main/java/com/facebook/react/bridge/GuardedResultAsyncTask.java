package com.facebook.react.bridge;

import android.os.AsyncTask;

public abstract class GuardedResultAsyncTask<Result> extends AsyncTask<Void, Void, Result> {
    private final ReactContext mReactContext;

    protected abstract Result doInBackgroundGuarded();

    protected abstract void onPostExecuteGuarded(Result result);

    protected GuardedResultAsyncTask(ReactContext reactContext) {
        this.mReactContext = reactContext;
    }

    protected final Result doInBackground(Void... voidArr) {
        try {
            return doInBackgroundGuarded();
        } catch (Exception e) {
            this.mReactContext.handleException(e);
            throw e;
        }
    }

    protected final void onPostExecute(Result result) {
        try {
            onPostExecuteGuarded(result);
        } catch (Exception e) {
            this.mReactContext.handleException(e);
        }
    }
}
