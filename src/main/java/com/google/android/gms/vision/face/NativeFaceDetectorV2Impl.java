package com.google.android.gms.vision.face;

import android.content.Context;
import android.graphics.PointF;
import android.os.RemoteException;
import android.os.SystemClock;
import com.google.android.gms.common.internal.GmsLogger;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.ObjectWrapper;
import com.google.android.gms.internal.vision.zzbl;
import com.google.android.gms.internal.vision.zzbo;
import com.google.android.gms.internal.vision.zzbq;
import com.google.android.gms.internal.vision.zzca;
import com.google.android.gms.internal.vision.zzca.zzc;
import com.google.android.gms.internal.vision.zzca.zzd;
import com.google.android.gms.internal.vision.zzca.zzf;
import com.google.android.gms.internal.vision.zzca.zzg;
import com.google.android.gms.internal.vision.zzcc;
import com.google.android.gms.internal.vision.zzch;
import com.google.android.gms.internal.vision.zzck;
import com.google.android.gms.internal.vision.zzfy;
import com.google.android.gms.internal.vision.zzfy.zza;
import com.google.android.gms.internal.vision.zzjx;
import com.google.android.gms.internal.vision.zzjx.zzb;
import com.google.android.gms.internal.vision.zzkf;
import com.google.android.gms.internal.vision.zzl;
import com.google.android.gms.internal.vision.zzn;
import com.google.android.gms.vision.clearcut.DynamiteClearcutLogger;
import com.google.android.gms.vision.face.internal.client.FaceParcel;
import com.google.android.gms.vision.face.internal.client.LandmarkParcel;
import com.google.android.gms.vision.face.internal.client.zze;
import com.google.android.gms.vision.face.internal.client.zzh;
import java.io.File;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

final class NativeFaceDetectorV2Impl extends zzh {
    private static final GmsLogger zzcs = new GmsLogger("NativeFaceDetectorV2Imp", "");
    private final long zzct;
    private final DynamiteClearcutLogger zzcu;
    private final zzd zzcv;
    private final FaceDetectorV2Jni zzcw;

    NativeFaceDetectorV2Impl(Context context, DynamiteClearcutLogger dynamiteClearcutLogger, File file, zze zze, FaceDetectorV2Jni faceDetectorV2Jni) {
        zzg zzg = (zzg) ((zzfy) zzg.zzbo().zzj("models").zzfg());
        zza zzf = zzd.zzbi().zzb((zzca.zze) ((zzfy) zzca.zze.zzbk().zzh(zzg).zzi(zzg).zzj(zzg).zzfg())).zzb((zzca.zza) ((zzfy) zzca.zza.zzaw().zzc(zzg).zzd(zzg).zzfg())).zzb((zzf) ((zzfy) zzf.zzbm().zzo(zzg).zzp(zzg).zzq(zzg).zzr(zzg).zzfg())).zzd(zze.zzcm).zze(zze.trackingEnabled).zze(zze.proportionalMinFaceSize).zzf(true);
        int i = zze.mode;
        if (i == 0) {
            zzf.zzb(zzck.FAST);
        } else if (i == 1) {
            zzf.zzb(zzck.ACCURATE);
        } else if (i == 2) {
            zzf.zzb(zzck.SELFIE);
        }
        i = zze.landmarkType;
        if (i == 0) {
            zzf.zzb(zzch.NO_LANDMARK);
        } else if (i == 1) {
            zzf.zzb(zzch.ALL_LANDMARKS);
        } else if (i == 2) {
            zzf.zzb(zzch.CONTOUR_LANDMARKS);
        }
        int i2 = zze.zzcn;
        if (i2 == 0) {
            zzf.zzb(zzcc.NO_CLASSIFICATION);
        } else if (i2 == 1) {
            zzf.zzb(zzcc.ALL_CLASSIFICATIONS);
        }
        this.zzcv = (zzd) ((zzfy) zzf.zzfg());
        this.zzct = faceDetectorV2Jni.zza(this.zzcv, context.getAssets());
        this.zzcu = dynamiteClearcutLogger;
        this.zzcw = faceDetectorV2Jni;
    }

    public final boolean zzd(int i) throws RemoteException {
        return true;
    }

    public final void zzn() throws RemoteException {
        this.zzcw.zza(this.zzct);
    }

    public final FaceParcel[] zzc(IObjectWrapper iObjectWrapper, zzn zzn) throws RemoteException {
        zzn zzn2 = zzn;
        String str = "NativeFaceDetectorV2Imp";
        long elapsedRealtime = SystemClock.elapsedRealtime();
        try {
            zzbq zzbq;
            zzc zza;
            ByteBuffer byteBuffer = (ByteBuffer) ObjectWrapper.unwrap(iObjectWrapper);
            zzbo.zza zzj = zzbo.zzai().zzi(zzn2.width).zzj(zzn2.height);
            int i = zzn2.rotation;
            if (i == 0) {
                zzbq = zzbq.ROTATION_0;
            } else if (i == 1) {
                zzbq = zzbq.ROTATION_270;
            } else if (i == 2) {
                zzbq = zzbq.ROTATION_180;
            } else if (i == 3) {
                zzbq = zzbq.ROTATION_90;
            } else {
                throw new IllegalArgumentException("Unsupported rotation degree.");
            }
            zza zzb = zzj.zzb(zzbq).zzb(zzbl.NV21);
            if (zzn2.zzat > 0) {
                zzb.zzc(zzn2.zzat);
            }
            zzbo zzbo = (zzbo) ((zzfy) zzb.zzfg());
            if (byteBuffer.isDirect()) {
                zza = this.zzcw.zza(this.zzct, byteBuffer, zzbo);
            } else if (byteBuffer.hasArray() && byteBuffer.arrayOffset() == 0) {
                zza = this.zzcw.zza(this.zzct, byteBuffer.array(), zzbo);
            } else {
                byte[] bArr = new byte[byteBuffer.remaining()];
                byteBuffer.get(bArr);
                zza = this.zzcw.zza(this.zzct, bArr, zzbo);
            }
            zzcc zzbh = this.zzcv.zzbh();
            zzch zzbg = this.zzcv.zzbg();
            zzkf zzbe = zza.zzbe();
            FaceParcel[] faceParcelArr = new FaceParcel[zzbe.zzip()];
            int i2 = 0;
            while (i2 < zzbe.zzip()) {
                float f;
                float f2;
                float f3;
                zzkf zzkf;
                zzcc zzcc;
                List list;
                LandmarkParcel[] landmarkParcelArr;
                com.google.android.gms.vision.face.internal.client.zza[] zzaArr;
                int i3;
                zzch zzch;
                zzfy.zzd zzcc2 = zzbe.zzcc(i2);
                zzb zzhw = zzcc2.zzhw();
                float zzig = zzhw.zzig() + ((zzhw.zzii() - zzhw.zzig()) / 2.0f);
                float zzih = zzhw.zzih() + ((zzhw.zzij() - zzhw.zzih()) / 2.0f);
                float zzii = zzhw.zzii() - zzhw.zzig();
                float zzij = zzhw.zzij() - zzhw.zzih();
                float f4 = -1.0f;
                if (zzbh == zzcc.ALL_CLASSIFICATIONS) {
                    float f5 = -1.0f;
                    float f6 = -1.0f;
                    for (zzjx.zza zza2 : zzcc2.zzib()) {
                        if (zza2.getName().equals("joy")) {
                            f6 = zza2.getConfidence();
                        } else if (zza2.getName().equals("left_eye_closed")) {
                            f4 = 1.0f - zza2.getConfidence();
                        } else if (zza2.getName().equals("right_eye_closed")) {
                            f5 = 1.0f - zza2.getConfidence();
                        }
                    }
                    f = f4;
                    f2 = f5;
                    f3 = f6;
                } else {
                    f = -1.0f;
                    f2 = -1.0f;
                    f3 = -1.0f;
                }
                if (zzbg == zzch.ALL_LANDMARKS) {
                    List zzhx = zzcc2.zzhx();
                    List arrayList = new ArrayList();
                    int i4 = 0;
                    while (i4 < zzhx.size()) {
                        int i5;
                        zzjx.zze zze = (zzjx.zze) zzhx.get(i4);
                        zzjx.zze.zzb zzin = zze.zzin();
                        switch (zzin) {
                            case LEFT_EYE:
                                zzkf = zzbe;
                                zzcc = zzbh;
                                list = zzhx;
                                i5 = 4;
                                break;
                            case RIGHT_EYE:
                                zzkf = zzbe;
                                zzcc = zzbh;
                                list = zzhx;
                                i5 = 10;
                                break;
                            case NOSE_TIP:
                                zzkf = zzbe;
                                zzcc = zzbh;
                                list = zzhx;
                                i5 = 6;
                                break;
                            case LOWER_LIP:
                                zzkf = zzbe;
                                zzcc = zzbh;
                                list = zzhx;
                                i5 = 0;
                                break;
                            case MOUTH_LEFT:
                                zzkf = zzbe;
                                zzcc = zzbh;
                                list = zzhx;
                                i5 = 5;
                                break;
                            case MOUTH_RIGHT:
                                zzkf = zzbe;
                                zzcc = zzbh;
                                list = zzhx;
                                i5 = 11;
                                break;
                            case LEFT_EAR_TRAGION:
                                zzkf = zzbe;
                                zzcc = zzbh;
                                list = zzhx;
                                i5 = 3;
                                break;
                            case RIGHT_EAR_TRAGION:
                                zzkf = zzbe;
                                zzcc = zzbh;
                                list = zzhx;
                                i5 = 9;
                                break;
                            case LEFT_CHEEK_CENTER:
                                zzkf = zzbe;
                                zzcc = zzbh;
                                list = zzhx;
                                i5 = 1;
                                break;
                            case RIGHT_CHEEK_CENTER:
                                zzkf = zzbe;
                                zzcc = zzbh;
                                list = zzhx;
                                i5 = 7;
                                break;
                            case LEFT_EAR_TOP:
                                zzkf = zzbe;
                                zzcc = zzbh;
                                list = zzhx;
                                i5 = 2;
                                break;
                            case RIGHT_EAR_TOP:
                                zzkf = zzbe;
                                zzcc = zzbh;
                                list = zzhx;
                                i5 = 8;
                                break;
                            default:
                                GmsLogger gmsLogger = zzcs;
                                zzkf = zzbe;
                                String valueOf = String.valueOf(zzin);
                                zzcc = zzbh;
                                list = zzhx;
                                StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 23);
                                stringBuilder.append("Unknown landmark type: ");
                                stringBuilder.append(valueOf);
                                gmsLogger.d(str, stringBuilder.toString());
                                i5 = -1;
                                break;
                        }
                        if (i5 >= 0) {
                            arrayList.add(new LandmarkParcel(-1, zze.getX(), zze.getY(), i5));
                        }
                        i4++;
                        zzhx = list;
                        zzbe = zzkf;
                        zzbh = zzcc;
                    }
                    zzkf = zzbe;
                    zzcc = zzbh;
                    landmarkParcelArr = (LandmarkParcel[]) arrayList.toArray(new LandmarkParcel[0]);
                } else {
                    zzkf = zzbe;
                    zzcc = zzbh;
                    landmarkParcelArr = new LandmarkParcel[0];
                }
                if (zzbg == zzch.CONTOUR_LANDMARKS) {
                    List list2 = (List) zzcc2.zzc(zzca.zziv);
                    zzaArr = new com.google.android.gms.vision.face.internal.client.zza[list2.size()];
                    i3 = 0;
                    while (i3 < list2.size()) {
                        int i6;
                        zzca.zzb zzb2 = (zzca.zzb) list2.get(i3);
                        PointF[] pointFArr = new PointF[zzb2.zzba()];
                        int i7 = 0;
                        while (i7 < zzb2.zzba()) {
                            zzca.zzb.zzb zzb3 = (zzca.zzb.zzb) zzb2.zzaz().get(i7);
                            list = list2;
                            zzch = zzbg;
                            pointFArr[i7] = new PointF(zzb3.getX(), zzb3.getY());
                            i7++;
                            list2 = list;
                            zzbg = zzch;
                        }
                        list = list2;
                        zzch = zzbg;
                        zzca.zzb.zzc zzay = zzb2.zzay();
                        GmsLogger gmsLogger2;
                        String valueOf2;
                        StringBuilder stringBuilder2;
                        switch (zzay) {
                            case FACE_OVAL:
                                i6 = 1;
                                break;
                            case LEFT_EYEBROW_TOP:
                                i6 = 2;
                                break;
                            case LEFT_EYEBROW_BOTTOM:
                                i6 = 3;
                                break;
                            case RIGHT_EYEBROW_TOP:
                                i6 = 4;
                                break;
                            case RIGHT_EYEBROW_BOTTOM:
                                i6 = 5;
                                break;
                            case LEFT_EYE:
                                i6 = 6;
                                break;
                            case RIGHT_EYE:
                                i6 = 7;
                                break;
                            case UPPER_LIP_TOP:
                                i6 = 8;
                                break;
                            case UPPER_LIP_BOTTOM:
                                i6 = 9;
                                break;
                            case LOWER_LIP_TOP:
                                i6 = 10;
                                break;
                            case LOWER_LIP_BOTTOM:
                                i6 = 11;
                                break;
                            case NOSE_BRIDGE:
                                i6 = 12;
                                break;
                            case NOSE_BOTTOM:
                                i6 = 13;
                                break;
                            case LEFT_CHEEK_CENTER:
                            case RIGHT_CHEEK_CENTER:
                                gmsLogger2 = zzcs;
                                valueOf2 = String.valueOf(zzay);
                                stringBuilder2 = new StringBuilder(String.valueOf(valueOf2).length() + 35);
                                stringBuilder2.append("Intentionally ignore contour type: ");
                                stringBuilder2.append(valueOf2);
                                gmsLogger2.d(str, stringBuilder2.toString());
                                break;
                            default:
                                gmsLogger2 = zzcs;
                                valueOf2 = String.valueOf(zzay);
                                stringBuilder2 = new StringBuilder(String.valueOf(valueOf2).length() + 22);
                                stringBuilder2.append("Unknown contour type: ");
                                stringBuilder2.append(valueOf2);
                                gmsLogger2.e(str, stringBuilder2.toString());
                                break;
                        }
                        i6 = -1;
                        zzaArr[i3] = new com.google.android.gms.vision.face.internal.client.zza(pointFArr, i6);
                        i3++;
                        list2 = list;
                        zzbg = zzch;
                    }
                    zzch = zzbg;
                } else {
                    zzch = zzbg;
                    zzaArr = new com.google.android.gms.vision.face.internal.client.zza[0];
                }
                com.google.android.gms.vision.face.internal.client.zza[] zzaArr2 = zzaArr;
                i3 = (int) zzcc2.zzic();
                int i8 = i3;
                faceParcelArr[i2] = new FaceParcel(3, i8, zzig, zzih, zzii, zzij, zzcc2.zzhz(), -zzcc2.zzhy(), zzcc2.zzia(), landmarkParcelArr, f, f2, f3, zzaArr2);
                i2++;
                zzbe = zzkf;
                zzbh = zzcc;
                zzbg = zzch;
            }
            DynamiteClearcutLogger dynamiteClearcutLogger = this.zzcu;
            long elapsedRealtime2 = SystemClock.elapsedRealtime() - elapsedRealtime;
            if (zzn2.id <= 2 || faceParcelArr.length != 0) {
                dynamiteClearcutLogger.zza(3, zzl.zza(zzn2, faceParcelArr, null, elapsedRealtime2));
            }
            return faceParcelArr;
        } catch (Throwable e) {
            zzcs.e(str, "Native face detection v2 failed", e);
            return new FaceParcel[0];
        }
    }
}
