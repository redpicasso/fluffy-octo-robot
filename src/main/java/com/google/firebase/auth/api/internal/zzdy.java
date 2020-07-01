package com.google.firebase.auth.api.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.drew.metadata.exif.makernotes.NikonType2MakernoteDirectory;
import com.google.android.gms.internal.firebase_auth.zza;
import com.google.android.gms.internal.firebase_auth.zzbn;
import com.google.android.gms.internal.firebase_auth.zzbp;
import com.google.android.gms.internal.firebase_auth.zzbr;
import com.google.android.gms.internal.firebase_auth.zzbt;
import com.google.android.gms.internal.firebase_auth.zzbv;
import com.google.android.gms.internal.firebase_auth.zzbx;
import com.google.android.gms.internal.firebase_auth.zzbz;
import com.google.android.gms.internal.firebase_auth.zzcb;
import com.google.android.gms.internal.firebase_auth.zzcd;
import com.google.android.gms.internal.firebase_auth.zzcf;
import com.google.android.gms.internal.firebase_auth.zzch;
import com.google.android.gms.internal.firebase_auth.zzcj;
import com.google.android.gms.internal.firebase_auth.zzcl;
import com.google.android.gms.internal.firebase_auth.zzcn;
import com.google.android.gms.internal.firebase_auth.zzcp;
import com.google.android.gms.internal.firebase_auth.zzcr;
import com.google.android.gms.internal.firebase_auth.zzct;
import com.google.android.gms.internal.firebase_auth.zzcv;
import com.google.android.gms.internal.firebase_auth.zzcx;
import com.google.android.gms.internal.firebase_auth.zzcz;
import com.google.android.gms.internal.firebase_auth.zzd;
import com.google.android.gms.internal.firebase_auth.zzdb;
import com.google.android.gms.internal.firebase_auth.zzdd;
import com.google.android.gms.internal.firebase_auth.zzdf;
import com.google.android.gms.internal.firebase_auth.zzdh;
import com.google.android.gms.internal.firebase_auth.zzdj;
import com.google.android.gms.internal.firebase_auth.zzdl;
import com.google.android.gms.internal.firebase_auth.zzdn;
import com.google.android.gms.internal.firebase_auth.zzdp;
import com.google.android.gms.internal.firebase_auth.zzdr;
import com.google.android.gms.internal.firebase_auth.zzdt;
import com.google.android.gms.internal.firebase_auth.zzdv;
import com.google.android.gms.internal.firebase_auth.zzdx;
import com.google.android.gms.internal.firebase_auth.zzfe;
import com.google.android.gms.internal.firebase_auth.zzfm;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.UserProfileChangeRequest;

public abstract class zzdy extends zza implements zzdz {
    public zzdy() {
        super("com.google.firebase.auth.api.internal.IFirebaseAuthService");
    }

    protected final boolean dispatchTransaction(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        zzdu zzdu = null;
        String str = "com.google.firebase.auth.api.internal.IFirebaseAuthCallbacks";
        String readString;
        IBinder readStrongBinder;
        IInterface queryLocalInterface;
        String readString2;
        ActionCodeSettings actionCodeSettings;
        switch (i) {
            case 1:
                readString = parcel.readString();
                readStrongBinder = parcel.readStrongBinder();
                if (readStrongBinder != null) {
                    queryLocalInterface = readStrongBinder.queryLocalInterface(str);
                    if (queryLocalInterface instanceof zzdu) {
                        zzdu = (zzdu) queryLocalInterface;
                    } else {
                        zzdu = new zzdw(readStrongBinder);
                    }
                }
                zza(readString, zzdu);
                break;
            case 2:
                readString = parcel.readString();
                readStrongBinder = parcel.readStrongBinder();
                if (readStrongBinder != null) {
                    queryLocalInterface = readStrongBinder.queryLocalInterface(str);
                    if (queryLocalInterface instanceof zzdu) {
                        zzdu = (zzdu) queryLocalInterface;
                    } else {
                        zzdu = new zzdw(readStrongBinder);
                    }
                }
                zzb(readString, zzdu);
                break;
            case 3:
                zzfm zzfm = (zzfm) zzd.zza(parcel, zzfm.CREATOR);
                readStrongBinder = parcel.readStrongBinder();
                if (readStrongBinder != null) {
                    queryLocalInterface = readStrongBinder.queryLocalInterface(str);
                    if (queryLocalInterface instanceof zzdu) {
                        zzdu = (zzdu) queryLocalInterface;
                    } else {
                        zzdu = new zzdw(readStrongBinder);
                    }
                }
                zza(zzfm, zzdu);
                break;
            case 4:
                readString = parcel.readString();
                UserProfileChangeRequest userProfileChangeRequest = (UserProfileChangeRequest) zzd.zza(parcel, UserProfileChangeRequest.CREATOR);
                readStrongBinder = parcel.readStrongBinder();
                if (readStrongBinder != null) {
                    queryLocalInterface = readStrongBinder.queryLocalInterface(str);
                    if (queryLocalInterface instanceof zzdu) {
                        zzdu = (zzdu) queryLocalInterface;
                    } else {
                        zzdu = new zzdw(readStrongBinder);
                    }
                }
                zza(readString, userProfileChangeRequest, zzdu);
                break;
            case 5:
                readString = parcel.readString();
                readString2 = parcel.readString();
                readStrongBinder = parcel.readStrongBinder();
                if (readStrongBinder != null) {
                    queryLocalInterface = readStrongBinder.queryLocalInterface(str);
                    if (queryLocalInterface instanceof zzdu) {
                        zzdu = (zzdu) queryLocalInterface;
                    } else {
                        zzdu = new zzdw(readStrongBinder);
                    }
                }
                zza(readString, readString2, zzdu);
                break;
            case 6:
                readString = parcel.readString();
                readString2 = parcel.readString();
                readStrongBinder = parcel.readStrongBinder();
                if (readStrongBinder != null) {
                    queryLocalInterface = readStrongBinder.queryLocalInterface(str);
                    if (queryLocalInterface instanceof zzdu) {
                        zzdu = (zzdu) queryLocalInterface;
                    } else {
                        zzdu = new zzdw(readStrongBinder);
                    }
                }
                zzb(readString, readString2, zzdu);
                break;
            case 7:
                readString = parcel.readString();
                readString2 = parcel.readString();
                readStrongBinder = parcel.readStrongBinder();
                if (readStrongBinder != null) {
                    queryLocalInterface = readStrongBinder.queryLocalInterface(str);
                    if (queryLocalInterface instanceof zzdu) {
                        zzdu = (zzdu) queryLocalInterface;
                    } else {
                        zzdu = new zzdw(readStrongBinder);
                    }
                }
                zzc(readString, readString2, zzdu);
                break;
            case 8:
                readString = parcel.readString();
                readString2 = parcel.readString();
                readStrongBinder = parcel.readStrongBinder();
                if (readStrongBinder != null) {
                    queryLocalInterface = readStrongBinder.queryLocalInterface(str);
                    if (queryLocalInterface instanceof zzdu) {
                        zzdu = (zzdu) queryLocalInterface;
                    } else {
                        zzdu = new zzdw(readStrongBinder);
                    }
                }
                zzd(readString, readString2, zzdu);
                break;
            case 9:
                readString = parcel.readString();
                readStrongBinder = parcel.readStrongBinder();
                if (readStrongBinder != null) {
                    queryLocalInterface = readStrongBinder.queryLocalInterface(str);
                    if (queryLocalInterface instanceof zzdu) {
                        zzdu = (zzdu) queryLocalInterface;
                    } else {
                        zzdu = new zzdw(readStrongBinder);
                    }
                }
                zzc(readString, zzdu);
                break;
            case 10:
                readString = parcel.readString();
                readStrongBinder = parcel.readStrongBinder();
                if (readStrongBinder != null) {
                    queryLocalInterface = readStrongBinder.queryLocalInterface(str);
                    if (queryLocalInterface instanceof zzdu) {
                        zzdu = (zzdu) queryLocalInterface;
                    } else {
                        zzdu = new zzdw(readStrongBinder);
                    }
                }
                zzd(readString, zzdu);
                break;
            case 11:
                readString = parcel.readString();
                readString2 = parcel.readString();
                String readString3 = parcel.readString();
                readStrongBinder = parcel.readStrongBinder();
                if (readStrongBinder != null) {
                    queryLocalInterface = readStrongBinder.queryLocalInterface(str);
                    if (queryLocalInterface instanceof zzdu) {
                        zzdu = (zzdu) queryLocalInterface;
                    } else {
                        zzdu = new zzdw(readStrongBinder);
                    }
                }
                zza(readString, readString2, readString3, zzdu);
                break;
            case 12:
                readString = parcel.readString();
                zzfm zzfm2 = (zzfm) zzd.zza(parcel, zzfm.CREATOR);
                readStrongBinder = parcel.readStrongBinder();
                if (readStrongBinder != null) {
                    queryLocalInterface = readStrongBinder.queryLocalInterface(str);
                    if (queryLocalInterface instanceof zzdu) {
                        zzdu = (zzdu) queryLocalInterface;
                    } else {
                        zzdu = new zzdw(readStrongBinder);
                    }
                }
                zza(readString, zzfm2, zzdu);
                break;
            case 13:
                readString = parcel.readString();
                readStrongBinder = parcel.readStrongBinder();
                if (readStrongBinder != null) {
                    queryLocalInterface = readStrongBinder.queryLocalInterface(str);
                    if (queryLocalInterface instanceof zzdu) {
                        zzdu = (zzdu) queryLocalInterface;
                    } else {
                        zzdu = new zzdw(readStrongBinder);
                    }
                }
                zze(readString, zzdu);
                break;
            case 14:
                readString = parcel.readString();
                readString2 = parcel.readString();
                readStrongBinder = parcel.readStrongBinder();
                if (readStrongBinder != null) {
                    queryLocalInterface = readStrongBinder.queryLocalInterface(str);
                    if (queryLocalInterface instanceof zzdu) {
                        zzdu = (zzdu) queryLocalInterface;
                    } else {
                        zzdu = new zzdw(readStrongBinder);
                    }
                }
                zze(readString, readString2, zzdu);
                break;
            case 15:
                readString = parcel.readString();
                readStrongBinder = parcel.readStrongBinder();
                if (readStrongBinder != null) {
                    queryLocalInterface = readStrongBinder.queryLocalInterface(str);
                    if (queryLocalInterface instanceof zzdu) {
                        zzdu = (zzdu) queryLocalInterface;
                    } else {
                        zzdu = new zzdw(readStrongBinder);
                    }
                }
                zzf(readString, zzdu);
                break;
            case 16:
                IBinder readStrongBinder2 = parcel.readStrongBinder();
                if (readStrongBinder2 != null) {
                    IInterface queryLocalInterface2 = readStrongBinder2.queryLocalInterface(str);
                    if (queryLocalInterface2 instanceof zzdu) {
                        zzdu = (zzdu) queryLocalInterface2;
                    } else {
                        zzdu = new zzdw(readStrongBinder2);
                    }
                }
                zza(zzdu);
                break;
            case 17:
                readString = parcel.readString();
                readStrongBinder = parcel.readStrongBinder();
                if (readStrongBinder != null) {
                    queryLocalInterface = readStrongBinder.queryLocalInterface(str);
                    if (queryLocalInterface instanceof zzdu) {
                        zzdu = (zzdu) queryLocalInterface;
                    } else {
                        zzdu = new zzdw(readStrongBinder);
                    }
                }
                zzg(readString, zzdu);
                break;
            case 18:
                readString = parcel.readString();
                readStrongBinder = parcel.readStrongBinder();
                if (readStrongBinder != null) {
                    queryLocalInterface = readStrongBinder.queryLocalInterface(str);
                    if (queryLocalInterface instanceof zzdu) {
                        zzdu = (zzdu) queryLocalInterface;
                    } else {
                        zzdu = new zzdw(readStrongBinder);
                    }
                }
                zzh(readString, zzdu);
                break;
            case 19:
                readString = parcel.readString();
                readStrongBinder = parcel.readStrongBinder();
                if (readStrongBinder != null) {
                    queryLocalInterface = readStrongBinder.queryLocalInterface(str);
                    if (queryLocalInterface instanceof zzdu) {
                        zzdu = (zzdu) queryLocalInterface;
                    } else {
                        zzdu = new zzdw(readStrongBinder);
                    }
                }
                zzi(readString, zzdu);
                break;
            case 20:
                readString = parcel.readString();
                readStrongBinder = parcel.readStrongBinder();
                if (readStrongBinder != null) {
                    queryLocalInterface = readStrongBinder.queryLocalInterface(str);
                    if (queryLocalInterface instanceof zzdu) {
                        zzdu = (zzdu) queryLocalInterface;
                    } else {
                        zzdu = new zzdw(readStrongBinder);
                    }
                }
                zzj(readString, zzdu);
                break;
            case 21:
                readString = parcel.readString();
                readString2 = parcel.readString();
                readStrongBinder = parcel.readStrongBinder();
                if (readStrongBinder != null) {
                    queryLocalInterface = readStrongBinder.queryLocalInterface(str);
                    if (queryLocalInterface instanceof zzdu) {
                        zzdu = (zzdu) queryLocalInterface;
                    } else {
                        zzdu = new zzdw(readStrongBinder);
                    }
                }
                zzf(readString, readString2, zzdu);
                break;
            case 22:
                zzfe zzfe = (zzfe) zzd.zza(parcel, zzfe.CREATOR);
                readStrongBinder = parcel.readStrongBinder();
                if (readStrongBinder != null) {
                    queryLocalInterface = readStrongBinder.queryLocalInterface(str);
                    if (queryLocalInterface instanceof zzdu) {
                        zzdu = (zzdu) queryLocalInterface;
                    } else {
                        zzdu = new zzdw(readStrongBinder);
                    }
                }
                zza(zzfe, zzdu);
                break;
            case 23:
                PhoneAuthCredential phoneAuthCredential = (PhoneAuthCredential) zzd.zza(parcel, PhoneAuthCredential.CREATOR);
                readStrongBinder = parcel.readStrongBinder();
                if (readStrongBinder != null) {
                    queryLocalInterface = readStrongBinder.queryLocalInterface(str);
                    if (queryLocalInterface instanceof zzdu) {
                        zzdu = (zzdu) queryLocalInterface;
                    } else {
                        zzdu = new zzdw(readStrongBinder);
                    }
                }
                zza(phoneAuthCredential, zzdu);
                break;
            case 24:
                readString = parcel.readString();
                PhoneAuthCredential phoneAuthCredential2 = (PhoneAuthCredential) zzd.zza(parcel, PhoneAuthCredential.CREATOR);
                readStrongBinder = parcel.readStrongBinder();
                if (readStrongBinder != null) {
                    queryLocalInterface = readStrongBinder.queryLocalInterface(str);
                    if (queryLocalInterface instanceof zzdu) {
                        zzdu = (zzdu) queryLocalInterface;
                    } else {
                        zzdu = new zzdw(readStrongBinder);
                    }
                }
                zza(readString, phoneAuthCredential2, zzdu);
                break;
            case 25:
                readString = parcel.readString();
                actionCodeSettings = (ActionCodeSettings) zzd.zza(parcel, ActionCodeSettings.CREATOR);
                readStrongBinder = parcel.readStrongBinder();
                if (readStrongBinder != null) {
                    queryLocalInterface = readStrongBinder.queryLocalInterface(str);
                    if (queryLocalInterface instanceof zzdu) {
                        zzdu = (zzdu) queryLocalInterface;
                    } else {
                        zzdu = new zzdw(readStrongBinder);
                    }
                }
                zza(readString, actionCodeSettings, zzdu);
                break;
            case 26:
                readString = parcel.readString();
                actionCodeSettings = (ActionCodeSettings) zzd.zza(parcel, ActionCodeSettings.CREATOR);
                readStrongBinder = parcel.readStrongBinder();
                if (readStrongBinder != null) {
                    queryLocalInterface = readStrongBinder.queryLocalInterface(str);
                    if (queryLocalInterface instanceof zzdu) {
                        zzdu = (zzdu) queryLocalInterface;
                    } else {
                        zzdu = new zzdw(readStrongBinder);
                    }
                }
                zzb(readString, actionCodeSettings, zzdu);
                break;
            case 27:
                readString = parcel.readString();
                readStrongBinder = parcel.readStrongBinder();
                if (readStrongBinder != null) {
                    queryLocalInterface = readStrongBinder.queryLocalInterface(str);
                    if (queryLocalInterface instanceof zzdu) {
                        zzdu = (zzdu) queryLocalInterface;
                    } else {
                        zzdu = new zzdw(readStrongBinder);
                    }
                }
                zzk(readString, zzdu);
                break;
            case 28:
                readString = parcel.readString();
                actionCodeSettings = (ActionCodeSettings) zzd.zza(parcel, ActionCodeSettings.CREATOR);
                readStrongBinder = parcel.readStrongBinder();
                if (readStrongBinder != null) {
                    queryLocalInterface = readStrongBinder.queryLocalInterface(str);
                    if (queryLocalInterface instanceof zzdu) {
                        zzdu = (zzdu) queryLocalInterface;
                    } else {
                        zzdu = new zzdw(readStrongBinder);
                    }
                }
                zzc(readString, actionCodeSettings, zzdu);
                break;
            case 29:
                EmailAuthCredential emailAuthCredential = (EmailAuthCredential) zzd.zza(parcel, EmailAuthCredential.CREATOR);
                readStrongBinder = parcel.readStrongBinder();
                if (readStrongBinder != null) {
                    queryLocalInterface = readStrongBinder.queryLocalInterface(str);
                    if (queryLocalInterface instanceof zzdu) {
                        zzdu = (zzdu) queryLocalInterface;
                    } else {
                        zzdu = new zzdw(readStrongBinder);
                    }
                }
                zza(emailAuthCredential, zzdu);
                break;
            default:
                switch (i) {
                    case 101:
                        zzcf zzcf = (zzcf) zzd.zza(parcel, zzcf.CREATOR);
                        readStrongBinder = parcel.readStrongBinder();
                        if (readStrongBinder != null) {
                            queryLocalInterface = readStrongBinder.queryLocalInterface(str);
                            if (queryLocalInterface instanceof zzdu) {
                                zzdu = (zzdu) queryLocalInterface;
                            } else {
                                zzdu = new zzdw(readStrongBinder);
                            }
                        }
                        zza(zzcf, zzdu);
                        break;
                    case 102:
                        zzdd zzdd = (zzdd) zzd.zza(parcel, zzdd.CREATOR);
                        readStrongBinder = parcel.readStrongBinder();
                        if (readStrongBinder != null) {
                            queryLocalInterface = readStrongBinder.queryLocalInterface(str);
                            if (queryLocalInterface instanceof zzdu) {
                                zzdu = (zzdu) queryLocalInterface;
                            } else {
                                zzdu = new zzdw(readStrongBinder);
                            }
                        }
                        zza(zzdd, zzdu);
                        break;
                    case 103:
                        zzdb zzdb = (zzdb) zzd.zza(parcel, zzdb.CREATOR);
                        readStrongBinder = parcel.readStrongBinder();
                        if (readStrongBinder != null) {
                            queryLocalInterface = readStrongBinder.queryLocalInterface(str);
                            if (queryLocalInterface instanceof zzdu) {
                                zzdu = (zzdu) queryLocalInterface;
                            } else {
                                zzdu = new zzdw(readStrongBinder);
                            }
                        }
                        zza(zzdb, zzdu);
                        break;
                    case 104:
                        zzdv zzdv = (zzdv) zzd.zza(parcel, zzdv.CREATOR);
                        readStrongBinder = parcel.readStrongBinder();
                        if (readStrongBinder != null) {
                            queryLocalInterface = readStrongBinder.queryLocalInterface(str);
                            if (queryLocalInterface instanceof zzdu) {
                                zzdu = (zzdu) queryLocalInterface;
                            } else {
                                zzdu = new zzdw(readStrongBinder);
                            }
                        }
                        zza(zzdv, zzdu);
                        break;
                    case 105:
                        zzbp zzbp = (zzbp) zzd.zza(parcel, zzbp.CREATOR);
                        readStrongBinder = parcel.readStrongBinder();
                        if (readStrongBinder != null) {
                            queryLocalInterface = readStrongBinder.queryLocalInterface(str);
                            if (queryLocalInterface instanceof zzdu) {
                                zzdu = (zzdu) queryLocalInterface;
                            } else {
                                zzdu = new zzdw(readStrongBinder);
                            }
                        }
                        zza(zzbp, zzdu);
                        break;
                    case 106:
                        zzbr zzbr = (zzbr) zzd.zza(parcel, zzbr.CREATOR);
                        readStrongBinder = parcel.readStrongBinder();
                        if (readStrongBinder != null) {
                            queryLocalInterface = readStrongBinder.queryLocalInterface(str);
                            if (queryLocalInterface instanceof zzdu) {
                                zzdu = (zzdu) queryLocalInterface;
                            } else {
                                zzdu = new zzdw(readStrongBinder);
                            }
                        }
                        zza(zzbr, zzdu);
                        break;
                    case 107:
                        zzbx zzbx = (zzbx) zzd.zza(parcel, zzbx.CREATOR);
                        readStrongBinder = parcel.readStrongBinder();
                        if (readStrongBinder != null) {
                            queryLocalInterface = readStrongBinder.queryLocalInterface(str);
                            if (queryLocalInterface instanceof zzdu) {
                                zzdu = (zzdu) queryLocalInterface;
                            } else {
                                zzdu = new zzdw(readStrongBinder);
                            }
                        }
                        zza(zzbx, zzdu);
                        break;
                    case 108:
                        zzdf zzdf = (zzdf) zzd.zza(parcel, zzdf.CREATOR);
                        readStrongBinder = parcel.readStrongBinder();
                        if (readStrongBinder != null) {
                            queryLocalInterface = readStrongBinder.queryLocalInterface(str);
                            if (queryLocalInterface instanceof zzdu) {
                                zzdu = (zzdu) queryLocalInterface;
                            } else {
                                zzdu = new zzdw(readStrongBinder);
                            }
                        }
                        zza(zzdf, zzdu);
                        break;
                    case 109:
                        zzch zzch = (zzch) zzd.zza(parcel, zzch.CREATOR);
                        readStrongBinder = parcel.readStrongBinder();
                        if (readStrongBinder != null) {
                            queryLocalInterface = readStrongBinder.queryLocalInterface(str);
                            if (queryLocalInterface instanceof zzdu) {
                                zzdu = (zzdu) queryLocalInterface;
                            } else {
                                zzdu = new zzdw(readStrongBinder);
                            }
                        }
                        zza(zzch, zzdu);
                        break;
                    default:
                        switch (i) {
                            case 111:
                                zzcj zzcj = (zzcj) zzd.zza(parcel, zzcj.CREATOR);
                                readStrongBinder = parcel.readStrongBinder();
                                if (readStrongBinder != null) {
                                    queryLocalInterface = readStrongBinder.queryLocalInterface(str);
                                    if (queryLocalInterface instanceof zzdu) {
                                        zzdu = (zzdu) queryLocalInterface;
                                    } else {
                                        zzdu = new zzdw(readStrongBinder);
                                    }
                                }
                                zza(zzcj, zzdu);
                                break;
                            case 112:
                                zzcl zzcl = (zzcl) zzd.zza(parcel, zzcl.CREATOR);
                                readStrongBinder = parcel.readStrongBinder();
                                if (readStrongBinder != null) {
                                    queryLocalInterface = readStrongBinder.queryLocalInterface(str);
                                    if (queryLocalInterface instanceof zzdu) {
                                        zzdu = (zzdu) queryLocalInterface;
                                    } else {
                                        zzdu = new zzdw(readStrongBinder);
                                    }
                                }
                                zza(zzcl, zzdu);
                                break;
                            case 113:
                                zzdr zzdr = (zzdr) zzd.zza(parcel, zzdr.CREATOR);
                                readStrongBinder = parcel.readStrongBinder();
                                if (readStrongBinder != null) {
                                    queryLocalInterface = readStrongBinder.queryLocalInterface(str);
                                    if (queryLocalInterface instanceof zzdu) {
                                        zzdu = (zzdu) queryLocalInterface;
                                    } else {
                                        zzdu = new zzdw(readStrongBinder);
                                    }
                                }
                                zza(zzdr, zzdu);
                                break;
                            case 114:
                                zzdt zzdt = (zzdt) zzd.zza(parcel, zzdt.CREATOR);
                                readStrongBinder = parcel.readStrongBinder();
                                if (readStrongBinder != null) {
                                    queryLocalInterface = readStrongBinder.queryLocalInterface(str);
                                    if (queryLocalInterface instanceof zzdu) {
                                        zzdu = (zzdu) queryLocalInterface;
                                    } else {
                                        zzdu = new zzdw(readStrongBinder);
                                    }
                                }
                                zza(zzdt, zzdu);
                                break;
                            case 115:
                                zzcp zzcp = (zzcp) zzd.zza(parcel, zzcp.CREATOR);
                                readStrongBinder = parcel.readStrongBinder();
                                if (readStrongBinder != null) {
                                    queryLocalInterface = readStrongBinder.queryLocalInterface(str);
                                    if (queryLocalInterface instanceof zzdu) {
                                        zzdu = (zzdu) queryLocalInterface;
                                    } else {
                                        zzdu = new zzdw(readStrongBinder);
                                    }
                                }
                                zza(zzcp, zzdu);
                                break;
                            case 116:
                                zzcz zzcz = (zzcz) zzd.zza(parcel, zzcz.CREATOR);
                                readStrongBinder = parcel.readStrongBinder();
                                if (readStrongBinder != null) {
                                    queryLocalInterface = readStrongBinder.queryLocalInterface(str);
                                    if (queryLocalInterface instanceof zzdu) {
                                        zzdu = (zzdu) queryLocalInterface;
                                    } else {
                                        zzdu = new zzdw(readStrongBinder);
                                    }
                                }
                                zza(zzcz, zzdu);
                                break;
                            case 117:
                                zzbz zzbz = (zzbz) zzd.zza(parcel, zzbz.CREATOR);
                                readStrongBinder = parcel.readStrongBinder();
                                if (readStrongBinder != null) {
                                    queryLocalInterface = readStrongBinder.queryLocalInterface(str);
                                    if (queryLocalInterface instanceof zzdu) {
                                        zzdu = (zzdu) queryLocalInterface;
                                    } else {
                                        zzdu = new zzdw(readStrongBinder);
                                    }
                                }
                                zza(zzbz, zzdu);
                                break;
                            default:
                                switch (i) {
                                    case 119:
                                        zzbt zzbt = (zzbt) zzd.zza(parcel, zzbt.CREATOR);
                                        readStrongBinder = parcel.readStrongBinder();
                                        if (readStrongBinder != null) {
                                            queryLocalInterface = readStrongBinder.queryLocalInterface(str);
                                            if (queryLocalInterface instanceof zzdu) {
                                                zzdu = (zzdu) queryLocalInterface;
                                            } else {
                                                zzdu = new zzdw(readStrongBinder);
                                            }
                                        }
                                        zza(zzbt, zzdu);
                                        break;
                                    case 120:
                                        zzbn zzbn = (zzbn) zzd.zza(parcel, zzbn.CREATOR);
                                        readStrongBinder = parcel.readStrongBinder();
                                        if (readStrongBinder != null) {
                                            queryLocalInterface = readStrongBinder.queryLocalInterface(str);
                                            if (queryLocalInterface instanceof zzdu) {
                                                zzdu = (zzdu) queryLocalInterface;
                                            } else {
                                                zzdu = new zzdw(readStrongBinder);
                                            }
                                        }
                                        zza(zzbn, zzdu);
                                        break;
                                    case 121:
                                        zzbv zzbv = (zzbv) zzd.zza(parcel, zzbv.CREATOR);
                                        readStrongBinder = parcel.readStrongBinder();
                                        if (readStrongBinder != null) {
                                            queryLocalInterface = readStrongBinder.queryLocalInterface(str);
                                            if (queryLocalInterface instanceof zzdu) {
                                                zzdu = (zzdu) queryLocalInterface;
                                            } else {
                                                zzdu = new zzdw(readStrongBinder);
                                            }
                                        }
                                        zza(zzbv, zzdu);
                                        break;
                                    case 122:
                                        zzcv zzcv = (zzcv) zzd.zza(parcel, zzcv.CREATOR);
                                        readStrongBinder = parcel.readStrongBinder();
                                        if (readStrongBinder != null) {
                                            queryLocalInterface = readStrongBinder.queryLocalInterface(str);
                                            if (queryLocalInterface instanceof zzdu) {
                                                zzdu = (zzdu) queryLocalInterface;
                                            } else {
                                                zzdu = new zzdw(readStrongBinder);
                                            }
                                        }
                                        zza(zzcv, zzdu);
                                        break;
                                    case 123:
                                        zzdj zzdj = (zzdj) zzd.zza(parcel, zzdj.CREATOR);
                                        readStrongBinder = parcel.readStrongBinder();
                                        if (readStrongBinder != null) {
                                            queryLocalInterface = readStrongBinder.queryLocalInterface(str);
                                            if (queryLocalInterface instanceof zzdu) {
                                                zzdu = (zzdu) queryLocalInterface;
                                            } else {
                                                zzdu = new zzdw(readStrongBinder);
                                            }
                                        }
                                        zza(zzdj, zzdu);
                                        break;
                                    case 124:
                                        zzcn zzcn = (zzcn) zzd.zza(parcel, zzcn.CREATOR);
                                        readStrongBinder = parcel.readStrongBinder();
                                        if (readStrongBinder != null) {
                                            queryLocalInterface = readStrongBinder.queryLocalInterface(str);
                                            if (queryLocalInterface instanceof zzdu) {
                                                zzdu = (zzdu) queryLocalInterface;
                                            } else {
                                                zzdu = new zzdw(readStrongBinder);
                                            }
                                        }
                                        zza(zzcn, zzdu);
                                        break;
                                    default:
                                        switch (i) {
                                            case 126:
                                                zzcr zzcr = (zzcr) zzd.zza(parcel, zzcr.CREATOR);
                                                readStrongBinder = parcel.readStrongBinder();
                                                if (readStrongBinder != null) {
                                                    queryLocalInterface = readStrongBinder.queryLocalInterface(str);
                                                    if (queryLocalInterface instanceof zzdu) {
                                                        zzdu = (zzdu) queryLocalInterface;
                                                    } else {
                                                        zzdu = new zzdw(readStrongBinder);
                                                    }
                                                }
                                                zza(zzcr, zzdu);
                                                break;
                                            case 127:
                                                zzcx zzcx = (zzcx) zzd.zza(parcel, zzcx.CREATOR);
                                                readStrongBinder = parcel.readStrongBinder();
                                                if (readStrongBinder != null) {
                                                    queryLocalInterface = readStrongBinder.queryLocalInterface(str);
                                                    if (queryLocalInterface instanceof zzdu) {
                                                        zzdu = (zzdu) queryLocalInterface;
                                                    } else {
                                                        zzdu = new zzdw(readStrongBinder);
                                                    }
                                                }
                                                zza(zzcx, zzdu);
                                                break;
                                            case 128:
                                                zzct zzct = (zzct) zzd.zza(parcel, zzct.CREATOR);
                                                readStrongBinder = parcel.readStrongBinder();
                                                if (readStrongBinder != null) {
                                                    queryLocalInterface = readStrongBinder.queryLocalInterface(str);
                                                    if (queryLocalInterface instanceof zzdu) {
                                                        zzdu = (zzdu) queryLocalInterface;
                                                    } else {
                                                        zzdu = new zzdw(readStrongBinder);
                                                    }
                                                }
                                                zza(zzct, zzdu);
                                                break;
                                            case 129:
                                                zzdh zzdh = (zzdh) zzd.zza(parcel, zzdh.CREATOR);
                                                readStrongBinder = parcel.readStrongBinder();
                                                if (readStrongBinder != null) {
                                                    queryLocalInterface = readStrongBinder.queryLocalInterface(str);
                                                    if (queryLocalInterface instanceof zzdu) {
                                                        zzdu = (zzdu) queryLocalInterface;
                                                    } else {
                                                        zzdu = new zzdw(readStrongBinder);
                                                    }
                                                }
                                                zza(zzdh, zzdu);
                                                break;
                                            case NikonType2MakernoteDirectory.TAG_ADAPTER /*130*/:
                                                zzdl zzdl = (zzdl) zzd.zza(parcel, zzdl.CREATOR);
                                                readStrongBinder = parcel.readStrongBinder();
                                                if (readStrongBinder != null) {
                                                    queryLocalInterface = readStrongBinder.queryLocalInterface(str);
                                                    if (queryLocalInterface instanceof zzdu) {
                                                        zzdu = (zzdu) queryLocalInterface;
                                                    } else {
                                                        zzdu = new zzdw(readStrongBinder);
                                                    }
                                                }
                                                zza(zzdl, zzdu);
                                                break;
                                            case 131:
                                                zzdp zzdp = (zzdp) zzd.zza(parcel, zzdp.CREATOR);
                                                readStrongBinder = parcel.readStrongBinder();
                                                if (readStrongBinder != null) {
                                                    queryLocalInterface = readStrongBinder.queryLocalInterface(str);
                                                    if (queryLocalInterface instanceof zzdu) {
                                                        zzdu = (zzdu) queryLocalInterface;
                                                    } else {
                                                        zzdu = new zzdw(readStrongBinder);
                                                    }
                                                }
                                                zza(zzdp, zzdu);
                                                break;
                                            case NikonType2MakernoteDirectory.TAG_LENS /*132*/:
                                                zzcb zzcb = (zzcb) zzd.zza(parcel, zzcb.CREATOR);
                                                readStrongBinder = parcel.readStrongBinder();
                                                if (readStrongBinder != null) {
                                                    queryLocalInterface = readStrongBinder.queryLocalInterface(str);
                                                    if (queryLocalInterface instanceof zzdu) {
                                                        zzdu = (zzdu) queryLocalInterface;
                                                    } else {
                                                        zzdu = new zzdw(readStrongBinder);
                                                    }
                                                }
                                                zza(zzcb, zzdu);
                                                break;
                                            case NikonType2MakernoteDirectory.TAG_MANUAL_FOCUS_DISTANCE /*133*/:
                                                zzdn zzdn = (zzdn) zzd.zza(parcel, zzdn.CREATOR);
                                                readStrongBinder = parcel.readStrongBinder();
                                                if (readStrongBinder != null) {
                                                    queryLocalInterface = readStrongBinder.queryLocalInterface(str);
                                                    if (queryLocalInterface instanceof zzdu) {
                                                        zzdu = (zzdu) queryLocalInterface;
                                                    } else {
                                                        zzdu = new zzdw(readStrongBinder);
                                                    }
                                                }
                                                zza(zzdn, zzdu);
                                                break;
                                            case NikonType2MakernoteDirectory.TAG_DIGITAL_ZOOM /*134*/:
                                                zzcd zzcd = (zzcd) zzd.zza(parcel, zzcd.CREATOR);
                                                readStrongBinder = parcel.readStrongBinder();
                                                if (readStrongBinder != null) {
                                                    queryLocalInterface = readStrongBinder.queryLocalInterface(str);
                                                    if (queryLocalInterface instanceof zzdu) {
                                                        zzdu = (zzdu) queryLocalInterface;
                                                    } else {
                                                        zzdu = new zzdw(readStrongBinder);
                                                    }
                                                }
                                                zza(zzcd, zzdu);
                                                break;
                                            case NikonType2MakernoteDirectory.TAG_FLASH_USED /*135*/:
                                                zzdx zzdx = (zzdx) zzd.zza(parcel, zzdx.CREATOR);
                                                readStrongBinder = parcel.readStrongBinder();
                                                if (readStrongBinder != null) {
                                                    queryLocalInterface = readStrongBinder.queryLocalInterface(str);
                                                    if (queryLocalInterface instanceof zzdu) {
                                                        zzdu = (zzdu) queryLocalInterface;
                                                    } else {
                                                        zzdu = new zzdw(readStrongBinder);
                                                    }
                                                }
                                                zza(zzdx, zzdu);
                                                break;
                                            default:
                                                return false;
                                        }
                                }
                        }
                }
        }
        parcel2.writeNoException();
        return true;
    }
}
