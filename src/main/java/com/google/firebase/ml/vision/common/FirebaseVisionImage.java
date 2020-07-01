package com.google.firebase.ml.vision.common;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.Image;
import android.media.Image.Plane;
import android.net.Uri;
import android.os.SystemClock;
import android.util.Pair;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.firebase_ml.zzpv;
import com.google.android.gms.internal.firebase_ml.zzpx;
import com.google.android.gms.internal.firebase_ml.zzpy;
import com.google.android.gms.vision.Frame;
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata.Builder;
import java.io.IOException;
import java.nio.ByteBuffer;
import javax.annotation.concurrent.Immutable;

@Immutable
public class FirebaseVisionImage {
    private static zzpx zzaxa = zzpx.zznk();
    @Nullable
    private volatile Bitmap zzaxb;
    @Nullable
    private volatile ByteBuffer zzaxc;
    @Nullable
    private volatile FirebaseVisionImageMetadata zzaxd;
    @Nullable
    private volatile Frame zzaxe;
    @Nullable
    private volatile byte[] zzaxf;
    private final long zzaxg;

    public static FirebaseVisionImage fromByteBuffer(@NonNull ByteBuffer byteBuffer, @NonNull FirebaseVisionImageMetadata firebaseVisionImageMetadata) {
        return new FirebaseVisionImage(byteBuffer, firebaseVisionImageMetadata);
    }

    public static FirebaseVisionImage fromByteArray(@NonNull byte[] bArr, @NonNull FirebaseVisionImageMetadata firebaseVisionImageMetadata) {
        return new FirebaseVisionImage(bArr, firebaseVisionImageMetadata);
    }

    public static FirebaseVisionImage fromBitmap(@NonNull Bitmap bitmap) {
        return new FirebaseVisionImage(bitmap);
    }

    @RequiresApi(19)
    @TargetApi(19)
    public static FirebaseVisionImage fromMediaImage(@NonNull Image image, int i) {
        Preconditions.checkNotNull(image, "Please provide a valid image");
        boolean z = image.getFormat() == 256 || image.getFormat() == 35;
        Preconditions.checkArgument(z, "Only JPEG and YUV_420_888 are supported now");
        Plane[] planes = image.getPlanes();
        if (image.getFormat() != 256) {
            return new FirebaseVisionImage(zzpx.zza(planes, image.getWidth(), image.getHeight()), new Builder().setFormat(17).setWidth(image.getWidth()).setHeight(image.getHeight()).setRotation(i).build());
        }
        if (planes == null || planes.length != 1) {
            throw new IllegalArgumentException("Unexpected image format, JPEG should have exactly 1 image plane");
        }
        ByteBuffer buffer = planes[0].getBuffer();
        byte[] bArr = new byte[buffer.remaining()];
        buffer.get(bArr);
        if (i == 0) {
            return new FirebaseVisionImage(bArr);
        }
        return new FirebaseVisionImage(zza(BitmapFactory.decodeByteArray(bArr, 0, bArr.length), i));
    }

    public static FirebaseVisionImage fromFilePath(@NonNull Context context, @NonNull Uri uri) throws IOException {
        Preconditions.checkNotNull(context, "Please provide a valid Context");
        Preconditions.checkNotNull(uri, "Please provide a valid imageUri");
        zzpy.zznl();
        return new FirebaseVisionImage(zzpy.zza(context.getContentResolver(), uri, 1024));
    }

    private FirebaseVisionImage(@NonNull ByteBuffer byteBuffer, @NonNull FirebaseVisionImageMetadata firebaseVisionImageMetadata) {
        this.zzaxg = SystemClock.elapsedRealtime();
        this.zzaxc = (ByteBuffer) Preconditions.checkNotNull(byteBuffer);
        this.zzaxd = (FirebaseVisionImageMetadata) Preconditions.checkNotNull(firebaseVisionImageMetadata);
    }

    private FirebaseVisionImage(@NonNull byte[] bArr, @NonNull FirebaseVisionImageMetadata firebaseVisionImageMetadata) {
        this(ByteBuffer.wrap((byte[]) Preconditions.checkNotNull(bArr)), firebaseVisionImageMetadata);
    }

    private FirebaseVisionImage(@NonNull Bitmap bitmap) {
        this.zzaxg = SystemClock.elapsedRealtime();
        this.zzaxb = (Bitmap) Preconditions.checkNotNull(bitmap);
    }

    private FirebaseVisionImage(byte[] bArr) {
        this.zzaxg = SystemClock.elapsedRealtime();
        this.zzaxf = (byte[]) Preconditions.checkNotNull(bArr);
    }

    @KeepForSdk
    public Bitmap getBitmapForDebugging() {
        return zznj();
    }

    private final Bitmap zznj() {
        if (this.zzaxb != null) {
            return this.zzaxb;
        }
        synchronized (this) {
            if (this.zzaxb == null) {
                byte[] zzae = zzae(false);
                Bitmap decodeByteArray = BitmapFactory.decodeByteArray(zzae, 0, zzae.length);
                if (this.zzaxd != null) {
                    decodeByteArray = zza(decodeByteArray, this.zzaxd.getRotation());
                }
                this.zzaxb = decodeByteArray;
            }
        }
        return this.zzaxb;
    }

    private static Bitmap zza(Bitmap bitmap, int i) {
        i = zzpv.zzbm(i);
        if (i == 0) {
            return bitmap;
        }
        Matrix matrix = new Matrix();
        matrix.postRotate((float) i);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public final Pair<byte[], Float> zze(int i, int i2) {
        int height;
        int width;
        Object zza;
        if (this.zzaxd != null) {
            Object obj = (this.zzaxd.getRotation() == 1 || this.zzaxd.getRotation() == 3) ? 1 : null;
            height = obj != null ? this.zzaxd.getHeight() : this.zzaxd.getWidth();
            width = obj != null ? this.zzaxd.getWidth() : this.zzaxd.getHeight();
        } else {
            height = zznj().getWidth();
            width = zznj().getHeight();
        }
        float min = Math.min(((float) i) / ((float) height), ((float) i2) / ((float) width));
        float f = 1.0f;
        if (min < 1.0f) {
            Bitmap zznj = zznj();
            Matrix matrix = new Matrix();
            matrix.postScale(min, min);
            f = min;
            zza = zzpx.zza(Bitmap.createBitmap(zznj, 0, 0, this.zzaxb.getWidth(), this.zzaxb.getHeight(), matrix, true));
        } else {
            zza = zzae(true);
        }
        return Pair.create(zza, Float.valueOf(f));
    }

    /* JADX WARNING: Missing block: B:27:0x005b, code:
            return r3;
     */
    private final byte[] zzae(boolean r3) {
        /*
        r2 = this;
        r0 = r2.zzaxf;
        if (r0 == 0) goto L_0x0007;
    L_0x0004:
        r3 = r2.zzaxf;
        return r3;
    L_0x0007:
        monitor-enter(r2);
        r0 = r2.zzaxf;	 Catch:{ all -> 0x0068 }
        if (r0 == 0) goto L_0x0010;
    L_0x000c:
        r3 = r2.zzaxf;	 Catch:{ all -> 0x0068 }
        monitor-exit(r2);	 Catch:{ all -> 0x0068 }
        return r3;
    L_0x0010:
        r0 = r2.zzaxc;	 Catch:{ all -> 0x0068 }
        if (r0 == 0) goto L_0x005c;
    L_0x0014:
        if (r3 == 0) goto L_0x001e;
    L_0x0016:
        r3 = r2.zzaxd;	 Catch:{ all -> 0x0068 }
        r3 = r3.getRotation();	 Catch:{ all -> 0x0068 }
        if (r3 != 0) goto L_0x005c;
    L_0x001e:
        r3 = r2.zzaxc;	 Catch:{ all -> 0x0068 }
        r3 = com.google.android.gms.internal.firebase_ml.zzpx.zza(r3);	 Catch:{ all -> 0x0068 }
        r0 = r2.zzaxd;	 Catch:{ all -> 0x0068 }
        r0 = r0.getFormat();	 Catch:{ all -> 0x0068 }
        r1 = 17;
        if (r0 == r1) goto L_0x0040;
    L_0x002e:
        r1 = 842094169; // 0x32315659 float:1.0322389E-8 double:4.160497995E-315;
        if (r0 != r1) goto L_0x0038;
    L_0x0033:
        r3 = com.google.android.gms.internal.firebase_ml.zzpx.zzf(r3);	 Catch:{ all -> 0x0068 }
        goto L_0x0040;
    L_0x0038:
        r3 = new java.lang.IllegalStateException;	 Catch:{ all -> 0x0068 }
        r0 = "Must be one of: IMAGE_FORMAT_NV21, IMAGE_FORMAT_YV12";
        r3.<init>(r0);	 Catch:{ all -> 0x0068 }
        throw r3;	 Catch:{ all -> 0x0068 }
    L_0x0040:
        r0 = r2.zzaxd;	 Catch:{ all -> 0x0068 }
        r0 = r0.getWidth();	 Catch:{ all -> 0x0068 }
        r1 = r2.zzaxd;	 Catch:{ all -> 0x0068 }
        r1 = r1.getHeight();	 Catch:{ all -> 0x0068 }
        r3 = com.google.android.gms.internal.firebase_ml.zzpx.zza(r3, r0, r1);	 Catch:{ all -> 0x0068 }
        r0 = r2.zzaxd;	 Catch:{ all -> 0x0068 }
        r0 = r0.getRotation();	 Catch:{ all -> 0x0068 }
        if (r0 != 0) goto L_0x005a;
    L_0x0058:
        r2.zzaxf = r3;	 Catch:{ all -> 0x0068 }
    L_0x005a:
        monitor-exit(r2);	 Catch:{ all -> 0x0068 }
        return r3;
    L_0x005c:
        r3 = r2.zznj();	 Catch:{ all -> 0x0068 }
        r3 = com.google.android.gms.internal.firebase_ml.zzpx.zza(r3);	 Catch:{ all -> 0x0068 }
        r2.zzaxf = r3;	 Catch:{ all -> 0x0068 }
        monitor-exit(r2);	 Catch:{ all -> 0x0068 }
        return r3;
    L_0x0068:
        r3 = move-exception;
        monitor-exit(r2);	 Catch:{ all -> 0x0068 }
        throw r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.ml.vision.common.FirebaseVisionImage.zzae(boolean):byte[]");
    }

    public final synchronized Frame zza(boolean z, boolean z2) {
        boolean z3 = (z && z2) ? false : true;
        Preconditions.checkArgument(z3, "Can't restrict to bitmap-only and NV21 byte buffer-only");
        if (this.zzaxe == null) {
            Frame.Builder builder = new Frame.Builder();
            if (this.zzaxc == null || z) {
                builder.setBitmap(zznj());
            } else {
                int i = FirebaseVisionImageMetadata.IMAGE_FORMAT_YV12;
                if (z2 && this.zzaxd.getFormat() != 17) {
                    if (this.zzaxd.getFormat() == FirebaseVisionImageMetadata.IMAGE_FORMAT_YV12) {
                        this.zzaxc = ByteBuffer.wrap(zzpx.zzf(zzpx.zza(this.zzaxc)));
                        this.zzaxd = new Builder().setFormat(17).setWidth(this.zzaxd.getWidth()).setHeight(this.zzaxd.getHeight()).setRotation(this.zzaxd.getRotation()).build();
                    } else {
                        throw new IllegalStateException("Must be one of: IMAGE_FORMAT_NV21, IMAGE_FORMAT_YV12");
                    }
                }
                ByteBuffer byteBuffer = this.zzaxc;
                int width = this.zzaxd.getWidth();
                int height = this.zzaxd.getHeight();
                int format = this.zzaxd.getFormat();
                if (format == 17) {
                    i = 17;
                } else if (format != FirebaseVisionImageMetadata.IMAGE_FORMAT_YV12) {
                    i = 0;
                }
                builder.setImageData(byteBuffer, width, height, i);
                i = this.zzaxd.getRotation();
                int i2 = 3;
                if (i == 0) {
                    i2 = 0;
                } else if (i == 1) {
                    i2 = 1;
                } else if (i == 2) {
                    i2 = 2;
                } else if (i != 3) {
                    StringBuilder stringBuilder = new StringBuilder(29);
                    stringBuilder.append("Invalid rotation: ");
                    stringBuilder.append(i);
                    throw new IllegalArgumentException(stringBuilder.toString());
                }
                builder.setRotation(i2);
            }
            builder.setTimestampMillis(this.zzaxg);
            this.zzaxe = builder.build();
        }
        return this.zzaxe;
    }
}
