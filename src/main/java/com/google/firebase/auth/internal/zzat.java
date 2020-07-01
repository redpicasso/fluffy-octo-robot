package com.google.firebase.auth.internal;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import androidx.annotation.Nullable;
import androidx.exifinterface.media.ExifInterface;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.logging.Logger;
import com.google.android.gms.internal.firebase_auth.zzbl;
import com.google.android.gms.internal.firebase_auth.zzes;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.zzac;
import com.google.firebase.auth.zzx;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class zzat {
    private Logger zzjt = new Logger("StorageHelpers", new String[0]);
    private Context zzml;
    private String zzuy;
    private SharedPreferences zzuz = this.zzml.getSharedPreferences(String.format("com.google.firebase.auth.api.Store.%s", new Object[]{this.zzuy}), 0);

    public zzat(Context context, String str) {
        Preconditions.checkNotNull(context);
        this.zzuy = Preconditions.checkNotEmpty(str);
        this.zzml = context.getApplicationContext();
    }

    public final void zzg(FirebaseUser firebaseUser) {
        Preconditions.checkNotNull(firebaseUser);
        Object zzi = zzi(firebaseUser);
        if (!TextUtils.isEmpty(zzi)) {
            this.zzuz.edit().putString("com.google.firebase.auth.FIREBASE_USER", zzi).apply();
        }
    }

    @Nullable
    public final FirebaseUser zzfr() {
        String str = "type";
        Object string = this.zzuz.getString("com.google.firebase.auth.FIREBASE_USER", null);
        if (TextUtils.isEmpty(string)) {
            return null;
        }
        try {
            JSONObject jSONObject = new JSONObject(string);
            if (jSONObject.has(str)) {
                if ("com.google.firebase.auth.internal.DefaultFirebaseUser".equalsIgnoreCase(jSONObject.optString(str))) {
                    return zzd(jSONObject);
                }
            }
        } catch (Exception unused) {
            return null;
        }
    }

    public final void zza(FirebaseUser firebaseUser, zzes zzes) {
        Preconditions.checkNotNull(firebaseUser);
        Preconditions.checkNotNull(zzes);
        this.zzuz.edit().putString(String.format("com.google.firebase.auth.GET_TOKEN_RESPONSE.%s", new Object[]{firebaseUser.getUid()}), zzes.zzew()).apply();
    }

    public final zzes zzh(FirebaseUser firebaseUser) {
        Preconditions.checkNotNull(firebaseUser);
        String string = this.zzuz.getString(String.format("com.google.firebase.auth.GET_TOKEN_RESPONSE.%s", new Object[]{firebaseUser.getUid()}), null);
        if (string != null) {
            return zzes.zzcn(string);
        }
        return null;
    }

    public final void clear(String str) {
        this.zzuz.edit().remove(str).apply();
    }

    @Nullable
    private final String zzi(FirebaseUser firebaseUser) {
        JSONObject jSONObject = new JSONObject();
        if (!zzm.class.isAssignableFrom(firebaseUser.getClass())) {
            return null;
        }
        zzm zzm = (zzm) firebaseUser;
        try {
            JSONArray jSONArray;
            jSONObject.put("cachedTokenState", zzm.zzcz());
            jSONObject.put("applicationName", zzm.zzcu().getName());
            jSONObject.put("type", "com.google.firebase.auth.internal.DefaultFirebaseUser");
            if (zzm.zzff() != null) {
                jSONArray = new JSONArray();
                List zzff = zzm.zzff();
                for (int i = 0; i < zzff.size(); i++) {
                    jSONArray.put(((zzi) zzff.get(i)).zzew());
                }
                jSONObject.put("userInfos", jSONArray);
            }
            jSONObject.put("anonymous", zzm.isAnonymous());
            jSONObject.put("version", ExifInterface.GPS_MEASUREMENT_2D);
            if (zzm.getMetadata() != null) {
                jSONObject.put("userMetadata", ((zzo) zzm.getMetadata()).zzfg());
            }
            List zzdc = ((zzq) zzm.zzdb()).zzdc();
            if (!(zzdc == null || zzdc.isEmpty())) {
                jSONArray = new JSONArray();
                for (int i2 = 0; i2 < zzdc.size(); i2++) {
                    jSONArray.put(((zzx) zzdc.get(i2)).toJson());
                }
                jSONObject.put("userMultiFactorInfo", jSONArray);
            }
            return jSONObject.toString();
        } catch (Throwable e) {
            this.zzjt.wtf("Failed to turn object into JSON", e, new Object[0]);
            throw new zzbl(e);
        }
    }

    private final zzm zzd(JSONObject jSONObject) {
        Throwable e;
        String str = "userMultiFactorInfo";
        String str2 = "userMetadata";
        try {
            Object string = jSONObject.getString("cachedTokenState");
            String string2 = jSONObject.getString("applicationName");
            boolean z = jSONObject.getBoolean("anonymous");
            String str3 = ExifInterface.GPS_MEASUREMENT_2D;
            String string3 = jSONObject.getString("version");
            if (string3 != null) {
                str3 = string3;
            }
            JSONArray jSONArray = jSONObject.getJSONArray("userInfos");
            int length = jSONArray.length();
            List arrayList = new ArrayList(length);
            for (int i = 0; i < length; i++) {
                arrayList.add(zzi.zzda(jSONArray.getString(i)));
            }
            FirebaseUser zzm = new zzm(FirebaseApp.getInstance(string2), arrayList);
            if (!TextUtils.isEmpty(string)) {
                zzm.zza(zzes.zzcn(string));
            }
            if (!z) {
                zzm.zzcx();
            }
            zzm.zzdb(str3);
            if (jSONObject.has(str2)) {
                zzo zzb = zzo.zzb(jSONObject.getJSONObject(str2));
                if (zzb != null) {
                    zzm.zza(zzb);
                }
            }
            if (jSONObject.has(str)) {
                JSONArray jSONArray2 = jSONObject.getJSONArray(str);
                if (jSONArray2 != null) {
                    List arrayList2 = new ArrayList();
                    for (int i2 = 0; i2 < jSONArray2.length(); i2++) {
                        JSONObject jSONObject2 = new JSONObject(jSONArray2.getString(i2));
                        arrayList2.add("phone".equals(jSONObject2.optString("factorIdKey")) ? zzac.zza(jSONObject2) : null);
                    }
                    zzm.zzb(arrayList2);
                }
            }
            return zzm;
        } catch (JSONException e2) {
            e = e2;
            this.zzjt.wtf(e);
            return null;
        } catch (ArrayIndexOutOfBoundsException e3) {
            e = e3;
            this.zzjt.wtf(e);
            return null;
        } catch (IllegalArgumentException e4) {
            e = e4;
            this.zzjt.wtf(e);
            return null;
        } catch (zzbl e5) {
            e = e5;
            this.zzjt.wtf(e);
            return null;
        }
    }
}
