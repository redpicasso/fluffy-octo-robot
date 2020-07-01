package com.google.android.gms.common.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import com.google.android.gms.common.api.Api.SimpleClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;

/* compiled from: com.google.android.gms:play-services-base@@17.1.0 */
public class SimpleClientAdapter<T extends IInterface> extends GmsClient<T> {
    private final SimpleClient<T> zapu;

    public SimpleClientAdapter(Context context, Looper looper, int i, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener, ClientSettings clientSettings, SimpleClient<T> simpleClient) {
        super(context, looper, i, clientSettings, connectionCallbacks, onConnectionFailedListener);
        this.zapu = simpleClient;
    }

    protected String getStartServiceAction() {
        return this.zapu.getStartServiceAction();
    }

    protected String getServiceDescriptor() {
        return this.zapu.getServiceDescriptor();
    }

    protected T createServiceInterface(IBinder iBinder) {
        return this.zapu.createServiceInterface(iBinder);
    }

    protected void onSetConnectState(int i, T t) {
        this.zapu.setState(i, t);
    }

    public SimpleClient<T> getClient() {
        return this.zapu;
    }
}
