package com.google.android.gms.internal.firebase_ml;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import com.google.android.gms.common.internal.Preconditions;
import com.google.firebase.ml.common.FirebaseMLException;
import com.google.firebase.ml.common.modeldownload.FirebaseLocalModel;
import java.io.FileInputStream;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

public class zzog {
    private final Context zzad;
    private MappedByteBuffer zzaqj;
    private final FirebaseLocalModel zzaqk;

    public zzog(@NonNull Context context, @NonNull FirebaseLocalModel firebaseLocalModel) {
        this.zzad = context;
        this.zzaqk = firebaseLocalModel;
    }

    public void zzlu() throws FirebaseMLException {
    }

    @WorkerThread
    @Nullable
    public MappedByteBuffer load() throws FirebaseMLException {
        Preconditions.checkNotNull(this.zzad, "Context can not be null");
        Preconditions.checkNotNull(this.zzaqk, "Model source can not be null");
        MappedByteBuffer mappedByteBuffer = this.zzaqj;
        if (mappedByteBuffer != null) {
            return mappedByteBuffer;
        }
        if (this.zzaqk.getFilePath() != null) {
            try {
                FileChannel channel = new RandomAccessFile(this.zzaqk.getFilePath(), "r").getChannel();
                this.zzaqj = channel.map(MapMode.READ_ONLY, 0, channel.size());
                return this.zzaqj;
            } catch (Throwable e) {
                String str = "Can not open the local file: ";
                String valueOf = String.valueOf(this.zzaqk.getFilePath());
                throw new FirebaseMLException(valueOf.length() != 0 ? str.concat(valueOf) : new String(str), 14, e);
            }
        } else if (this.zzaqk.getAssetFilePath() == null) {
            return null;
        } else {
            String assetFilePath = this.zzaqk.getAssetFilePath();
            try {
                AssetFileDescriptor openFd = this.zzad.getAssets().openFd(assetFilePath);
                this.zzaqj = new FileInputStream(openFd.getFileDescriptor()).getChannel().map(MapMode.READ_ONLY, openFd.getStartOffset(), openFd.getDeclaredLength());
                return this.zzaqj;
            } catch (Throwable e2) {
                StringBuilder stringBuilder = new StringBuilder(String.valueOf(assetFilePath).length() + 186);
                stringBuilder.append("Can not load the file from asset: ");
                stringBuilder.append(assetFilePath);
                stringBuilder.append(". Please double check your asset file name and ensure it's not compressed. See documentation for details how to use aaptOptions to skip file compression");
                throw new FirebaseMLException(stringBuilder.toString(), 14, e2);
            }
        }
    }
}
