package com.google.firebase.messaging;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.ArrayMap;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Reserved;
import com.lwansbrough.RCTCamera.RCTCameraModule;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Map;
import java.util.Map.Entry;

@Class(creator = "RemoteMessageCreator")
@Reserved({1})
/* compiled from: com.google.firebase:firebase-messaging@@20.0.0 */
public final class RemoteMessage extends AbstractSafeParcelable {
    public static final Creator<RemoteMessage> CREATOR = new zzm();
    public static final int PRIORITY_HIGH = 1;
    public static final int PRIORITY_NORMAL = 2;
    public static final int PRIORITY_UNKNOWN = 0;
    @Field(id = 2)
    Bundle zza;
    private Map<String, String> zzb;
    private Notification zzc;

    /* compiled from: com.google.firebase:firebase-messaging@@20.0.0 */
    public static class Builder {
        private final Bundle zza = new Bundle();
        private final Map<String, String> zzb = new ArrayMap();

        public Builder(@NonNull String str) {
            if (TextUtils.isEmpty(str)) {
                String str2 = "Invalid to: ";
                str = String.valueOf(str);
                throw new IllegalArgumentException(str.length() != 0 ? str2.concat(str) : new String(str2));
            }
            this.zza.putString("google.to", str);
        }

        @NonNull
        public RemoteMessage build() {
            Bundle bundle = new Bundle();
            for (Entry entry : this.zzb.entrySet()) {
                bundle.putString((String) entry.getKey(), (String) entry.getValue());
            }
            bundle.putAll(this.zza);
            this.zza.remove("from");
            return new RemoteMessage(bundle);
        }

        @NonNull
        public Builder addData(@NonNull String str, @Nullable String str2) {
            this.zzb.put(str, str2);
            return this;
        }

        @NonNull
        public Builder setData(@NonNull Map<String, String> map) {
            this.zzb.clear();
            this.zzb.putAll(map);
            return this;
        }

        @NonNull
        public Builder clearData() {
            this.zzb.clear();
            return this;
        }

        @NonNull
        public Builder setMessageId(@NonNull String str) {
            this.zza.putString("google.message_id", str);
            return this;
        }

        @NonNull
        public Builder setMessageType(@Nullable String str) {
            this.zza.putString("message_type", str);
            return this;
        }

        @NonNull
        public Builder setTtl(@IntRange(from = 0, to = 86400) int i) {
            this.zza.putString("google.ttl", String.valueOf(i));
            return this;
        }

        @NonNull
        public Builder setCollapseKey(@Nullable String str) {
            this.zza.putString("collapse_key", str);
            return this;
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    /* compiled from: com.google.firebase:firebase-messaging@@20.0.0 */
    public @interface MessagePriority {
    }

    /* compiled from: com.google.firebase:firebase-messaging@@20.0.0 */
    public static class Notification {
        private final String zza;
        private final String zzb;
        private final String[] zzc;
        private final String zzd;
        private final String zze;
        private final String[] zzf;
        private final String zzg;
        private final String zzh;
        private final String zzi;
        private final String zzj;
        private final String zzk;
        private final String zzl;
        private final String zzm;
        private final Uri zzn;
        private final String zzo;
        private final Integer zzp;
        private final Integer zzq;
        private final Integer zzr;
        private final int[] zzs;
        private final Long zzt;
        private final boolean zzu;
        private final boolean zzv;
        private final boolean zzw;
        private final boolean zzx;
        private final boolean zzy;
        private final long[] zzz;

        private Notification(zzk zzk) {
            String str = "gcm.n.title";
            this.zza = zzk.zza(str);
            this.zzb = zzk.zze(str);
            this.zzc = zza(zzk, str);
            str = "gcm.n.body";
            this.zzd = zzk.zza(str);
            this.zze = zzk.zze(str);
            this.zzf = zza(zzk, str);
            this.zzg = zzk.zza("gcm.n.icon");
            this.zzi = zzk.zzb();
            this.zzj = zzk.zza("gcm.n.tag");
            this.zzk = zzk.zza("gcm.n.color");
            this.zzl = zzk.zza("gcm.n.click_action");
            this.zzm = zzk.zza("gcm.n.android_channel_id");
            this.zzn = zzk.zza();
            this.zzh = zzk.zza("gcm.n.image");
            this.zzo = zzk.zza("gcm.n.ticker");
            this.zzp = zzk.zzc("gcm.n.notification_priority");
            this.zzq = zzk.zzc("gcm.n.visibility");
            this.zzr = zzk.zzc("gcm.n.notification_count");
            this.zzu = zzk.zzb("gcm.n.sticky");
            this.zzv = zzk.zzb("gcm.n.local_only");
            this.zzw = zzk.zzb("gcm.n.default_sound");
            this.zzx = zzk.zzb("gcm.n.default_vibrate_timings");
            this.zzy = zzk.zzb("gcm.n.default_light_settings");
            this.zzt = zzk.zzd("gcm.n.event_time");
            this.zzs = zzk.zzd();
            this.zzz = zzk.zzc();
        }

        private static String[] zza(zzk zzk, String str) {
            Object[] zzf = zzk.zzf(str);
            if (zzf == null) {
                return null;
            }
            String[] strArr = new String[zzf.length];
            for (int i = 0; i < zzf.length; i++) {
                strArr[i] = String.valueOf(zzf[i]);
            }
            return strArr;
        }

        @Nullable
        public String getTitle() {
            return this.zza;
        }

        @Nullable
        public String getTitleLocalizationKey() {
            return this.zzb;
        }

        @Nullable
        public String[] getTitleLocalizationArgs() {
            return this.zzc;
        }

        @Nullable
        public String getBody() {
            return this.zzd;
        }

        @Nullable
        public String getBodyLocalizationKey() {
            return this.zze;
        }

        @Nullable
        public String[] getBodyLocalizationArgs() {
            return this.zzf;
        }

        @Nullable
        public String getIcon() {
            return this.zzg;
        }

        @Nullable
        public Uri getImageUrl() {
            String str = this.zzh;
            return str != null ? Uri.parse(str) : null;
        }

        @Nullable
        public String getSound() {
            return this.zzi;
        }

        @Nullable
        public String getTag() {
            return this.zzj;
        }

        @Nullable
        public String getColor() {
            return this.zzk;
        }

        @Nullable
        public String getClickAction() {
            return this.zzl;
        }

        @Nullable
        public String getChannelId() {
            return this.zzm;
        }

        @Nullable
        public Uri getLink() {
            return this.zzn;
        }

        @Nullable
        public String getTicker() {
            return this.zzo;
        }

        public boolean getSticky() {
            return this.zzu;
        }

        public boolean getLocalOnly() {
            return this.zzv;
        }

        public boolean getDefaultSound() {
            return this.zzw;
        }

        public boolean getDefaultVibrateSettings() {
            return this.zzx;
        }

        public boolean getDefaultLightSettings() {
            return this.zzy;
        }

        @Nullable
        public Integer getNotificationPriority() {
            return this.zzp;
        }

        @Nullable
        public Integer getVisibility() {
            return this.zzq;
        }

        @Nullable
        public Integer getNotificationCount() {
            return this.zzr;
        }

        @Nullable
        public Long getEventTime() {
            return this.zzt;
        }

        @Nullable
        public int[] getLightSettings() {
            return this.zzs;
        }

        @Nullable
        public long[] getVibrateTimings() {
            return this.zzz;
        }

        /* synthetic */ Notification(zzk zzk, zzl zzl) {
            this(zzk);
        }
    }

    @Constructor
    public RemoteMessage(@Param(id = 2) Bundle bundle) {
        this.zza = bundle;
    }

    public final void writeToParcel(@NonNull Parcel parcel, int i) {
        i = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeBundle(parcel, 2, this.zza, false);
        SafeParcelWriter.finishObjectHeader(parcel, i);
    }

    @Nullable
    public final String getFrom() {
        return this.zza.getString("from");
    }

    @Nullable
    public final String getTo() {
        return this.zza.getString("google.to");
    }

    @NonNull
    public final Map<String, String> getData() {
        if (this.zzb == null) {
            Bundle bundle = this.zza;
            Map arrayMap = new ArrayMap();
            for (String str : bundle.keySet()) {
                Object obj = bundle.get(str);
                if (obj instanceof String) {
                    String str2 = (String) obj;
                    if (!(str.startsWith("google.") || str.startsWith("gcm.") || str.equals("from") || str.equals("message_type") || str.equals("collapse_key"))) {
                        arrayMap.put(str, str2);
                    }
                }
            }
            this.zzb = arrayMap;
        }
        return this.zzb;
    }

    @Nullable
    public final String getCollapseKey() {
        return this.zza.getString("collapse_key");
    }

    @Nullable
    public final String getMessageId() {
        String string = this.zza.getString("google.message_id");
        return string == null ? this.zza.getString("message_id") : string;
    }

    @Nullable
    public final String getMessageType() {
        return this.zza.getString("message_type");
    }

    public final long getSentTime() {
        Object obj = this.zza.get("google.sent_time");
        if (obj instanceof Long) {
            return ((Long) obj).longValue();
        }
        if (obj instanceof String) {
            try {
                obj = Long.parseLong((String) obj);
                return obj;
            } catch (NumberFormatException unused) {
                String valueOf = String.valueOf(obj);
                StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 19);
                stringBuilder.append("Invalid sent time: ");
                stringBuilder.append(valueOf);
                Log.w("FirebaseMessaging", stringBuilder.toString());
            }
        }
        return 0;
    }

    public final int getTtl() {
        Object obj = this.zza.get("google.ttl");
        if (obj instanceof Integer) {
            return ((Integer) obj).intValue();
        }
        if (obj instanceof String) {
            try {
                obj = Integer.parseInt((String) obj);
                return obj;
            } catch (NumberFormatException unused) {
                String valueOf = String.valueOf(obj);
                StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 13);
                stringBuilder.append("Invalid TTL: ");
                stringBuilder.append(valueOf);
                Log.w("FirebaseMessaging", stringBuilder.toString());
            }
        }
        return 0;
    }

    public final int getOriginalPriority() {
        String string = this.zza.getString("google.original_priority");
        if (string == null) {
            string = this.zza.getString("google.priority");
        }
        return zza(string);
    }

    public final int getPriority() {
        String string = this.zza.getString("google.delivered_priority");
        if (string == null) {
            if ("1".equals(this.zza.getString("google.priority_reduced"))) {
                return 2;
            }
            string = this.zza.getString("google.priority");
        }
        return zza(string);
    }

    private static int zza(String str) {
        if (RCTCameraModule.RCT_CAMERA_CAPTURE_QUALITY_HIGH.equals(str)) {
            return 1;
        }
        return "normal".equals(str) ? 2 : 0;
    }

    @Nullable
    public final Notification getNotification() {
        if (this.zzc == null && zzk.zza(this.zza)) {
            this.zzc = new Notification(new zzk(this.zza), null);
        }
        return this.zzc;
    }

    @KeepForSdk
    public final Intent toIntent() {
        Intent intent = new Intent();
        intent.putExtras(this.zza);
        return intent;
    }
}
