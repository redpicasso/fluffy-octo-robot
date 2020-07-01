package com.google.android.gms.measurement.internal;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteFullException;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import androidx.annotation.WorkerThread;
import androidx.exifinterface.media.ExifInterface;
import com.google.android.gms.common.util.VisibleForTesting;

public final class zzeb extends zzg {
    private final zzea zzjv = new zzea(this, getContext(), "google_app_measurement_local.db");
    private boolean zzjw;

    zzeb(zzfj zzfj) {
        super(zzfj);
    }

    protected final boolean zzbk() {
        return false;
    }

    @WorkerThread
    public final void resetAnalyticsData() {
        zzm();
        zzo();
        try {
            int delete = getWritableDatabase().delete("messages", null, null) + 0;
            if (delete > 0) {
                zzab().zzgs().zza("Reset local analytics data. records", Integer.valueOf(delete));
            }
        } catch (SQLiteException e) {
            zzab().zzgk().zza("Error resetting local analytics data. error", e);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:72:0x010c  */
    /* JADX WARNING: Removed duplicated region for block: B:103:0x0130 A:{SYNTHETIC} */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x0111  */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x00dd A:{SYNTHETIC, Splitter: B:55:0x00dd} */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x00f6  */
    /* JADX WARNING: Removed duplicated region for block: B:101:0x0130 A:{SYNTHETIC} */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x00fb  */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x00cc A:{ExcHandler: android.database.sqlite.SQLiteDatabaseLockedException (unused android.database.sqlite.SQLiteDatabaseLockedException), Splitter: B:9:0x0032} */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x00dd A:{SYNTHETIC, Splitter: B:55:0x00dd} */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x00f6  */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x00fb  */
    /* JADX WARNING: Removed duplicated region for block: B:101:0x0130 A:{SYNTHETIC} */
    /* JADX WARNING: Removed duplicated region for block: B:80:0x0128  */
    /* JADX WARNING: Removed duplicated region for block: B:105:0x0130 A:{SYNTHETIC} */
    /* JADX WARNING: Removed duplicated region for block: B:82:0x012d  */
    /* JADX WARNING: Removed duplicated region for block: B:72:0x010c  */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x0111  */
    /* JADX WARNING: Removed duplicated region for block: B:103:0x0130 A:{SYNTHETIC} */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x00dd A:{SYNTHETIC, Splitter: B:55:0x00dd} */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x00f6  */
    /* JADX WARNING: Removed duplicated region for block: B:101:0x0130 A:{SYNTHETIC} */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x00fb  */
    /* JADX WARNING: Removed duplicated region for block: B:87:0x013a  */
    /* JADX WARNING: Removed duplicated region for block: B:89:0x013f  */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing block: B:14:0x003a, code:
            r0 = e;
     */
    /* JADX WARNING: Missing block: B:15:0x003b, code:
            r13 = null;
     */
    /* JADX WARNING: Missing block: B:16:0x003e, code:
            r0 = e;
     */
    /* JADX WARNING: Missing block: B:47:0x00cc, code:
            r8 = null;
     */
    /* JADX WARNING: Missing block: B:57:0x00e1, code:
            if (r8.inTransaction() != false) goto L_0x00e3;
     */
    /* JADX WARNING: Missing block: B:58:0x00e3, code:
            r8.endTransaction();
     */
    /* JADX WARNING: Missing block: B:61:0x00f6, code:
            r13.close();
     */
    /* JADX WARNING: Missing block: B:63:0x00fb, code:
            r8.close();
     */
    /* JADX WARNING: Missing block: B:64:0x00ff, code:
            r0 = th;
     */
    /* JADX WARNING: Missing block: B:65:0x0100, code:
            r10 = r8;
     */
    /* JADX WARNING: Missing block: B:72:0x010c, code:
            r8.close();
     */
    /* JADX WARNING: Missing block: B:74:0x0111, code:
            r10.close();
     */
    /* JADX WARNING: Missing block: B:87:0x013a, code:
            r13.close();
     */
    /* JADX WARNING: Missing block: B:89:0x013f, code:
            r10.close();
     */
    @androidx.annotation.WorkerThread
    private final boolean zza(int r18, byte[] r19) {
        /*
        r17 = this;
        r1 = r17;
        r2 = "Error writing entry to local database";
        r17.zzm();
        r17.zzo();
        r0 = r1.zzjw;
        r3 = 0;
        if (r0 == 0) goto L_0x0010;
    L_0x000f:
        return r3;
    L_0x0010:
        r4 = new android.content.ContentValues;
        r4.<init>();
        r0 = java.lang.Integer.valueOf(r18);
        r5 = "type";
        r4.put(r5, r0);
        r0 = "entry";
        r5 = r19;
        r4.put(r0, r5);
        r5 = 5;
        r6 = 0;
        r7 = 5;
    L_0x0028:
        if (r6 >= r5) goto L_0x0143;
    L_0x002a:
        r8 = 0;
        r9 = 1;
        r10 = r17.getWritableDatabase();	 Catch:{ SQLiteFullException -> 0x0115, SQLiteDatabaseLockedException -> 0x0102, SQLiteException -> 0x00d8, all -> 0x00d2 }
        if (r10 != 0) goto L_0x0041;
    L_0x0032:
        r1.zzjw = r9;	 Catch:{ SQLiteFullException -> 0x003e, SQLiteDatabaseLockedException -> 0x00cc, SQLiteException -> 0x003a }
        if (r10 == 0) goto L_0x0039;
    L_0x0036:
        r10.close();
    L_0x0039:
        return r3;
    L_0x003a:
        r0 = move-exception;
        r13 = r8;
        goto L_0x00ca;
    L_0x003e:
        r0 = move-exception;
        goto L_0x0118;
    L_0x0041:
        r10.beginTransaction();	 Catch:{ SQLiteFullException -> 0x00cf, SQLiteDatabaseLockedException -> 0x00cc, SQLiteException -> 0x00c7, all -> 0x00c2 }
        r11 = 0;
        r0 = "select count(1) from messages";
        r13 = r10.rawQuery(r0, r8);	 Catch:{ SQLiteFullException -> 0x00cf, SQLiteDatabaseLockedException -> 0x00cc, SQLiteException -> 0x00c7, all -> 0x00c2 }
        if (r13 == 0) goto L_0x0063;
    L_0x004e:
        r0 = r13.moveToFirst();	 Catch:{ SQLiteFullException -> 0x005f, SQLiteDatabaseLockedException -> 0x00c0, SQLiteException -> 0x005c, all -> 0x0059 }
        if (r0 == 0) goto L_0x0063;
    L_0x0054:
        r11 = r13.getLong(r3);	 Catch:{ SQLiteFullException -> 0x005f, SQLiteDatabaseLockedException -> 0x00c0, SQLiteException -> 0x005c, all -> 0x0059 }
        goto L_0x0063;
    L_0x0059:
        r0 = move-exception;
        goto L_0x0138;
    L_0x005c:
        r0 = move-exception;
        goto L_0x00ca;
    L_0x005f:
        r0 = move-exception;
        r8 = r13;
        goto L_0x0118;
    L_0x0063:
        r0 = "messages";
        r14 = 100000; // 0x186a0 float:1.4013E-40 double:4.94066E-319;
        r16 = (r11 > r14 ? 1 : (r11 == r14 ? 0 : -1));
        if (r16 < 0) goto L_0x00aa;
    L_0x006c:
        r16 = r17.zzab();	 Catch:{ SQLiteFullException -> 0x005f, SQLiteDatabaseLockedException -> 0x00c0, SQLiteException -> 0x005c, all -> 0x0059 }
        r5 = r16.zzgk();	 Catch:{ SQLiteFullException -> 0x005f, SQLiteDatabaseLockedException -> 0x00c0, SQLiteException -> 0x005c, all -> 0x0059 }
        r8 = "Data loss, local db full";
        r5.zzao(r8);	 Catch:{ SQLiteFullException -> 0x005f, SQLiteDatabaseLockedException -> 0x00c0, SQLiteException -> 0x005c, all -> 0x0059 }
        r14 = r14 - r11;
        r11 = 1;
        r14 = r14 + r11;
        r5 = "rowid in (select rowid from messages order by rowid asc limit ?)";
        r8 = new java.lang.String[r9];	 Catch:{ SQLiteFullException -> 0x005f, SQLiteDatabaseLockedException -> 0x00c0, SQLiteException -> 0x005c, all -> 0x0059 }
        r11 = java.lang.Long.toString(r14);	 Catch:{ SQLiteFullException -> 0x005f, SQLiteDatabaseLockedException -> 0x00c0, SQLiteException -> 0x005c, all -> 0x0059 }
        r8[r3] = r11;	 Catch:{ SQLiteFullException -> 0x005f, SQLiteDatabaseLockedException -> 0x00c0, SQLiteException -> 0x005c, all -> 0x0059 }
        r5 = r10.delete(r0, r5, r8);	 Catch:{ SQLiteFullException -> 0x005f, SQLiteDatabaseLockedException -> 0x00c0, SQLiteException -> 0x005c, all -> 0x0059 }
        r11 = (long) r5;	 Catch:{ SQLiteFullException -> 0x005f, SQLiteDatabaseLockedException -> 0x00c0, SQLiteException -> 0x005c, all -> 0x0059 }
        r5 = (r11 > r14 ? 1 : (r11 == r14 ? 0 : -1));
        if (r5 == 0) goto L_0x00aa;
    L_0x0090:
        r5 = r17.zzab();	 Catch:{ SQLiteFullException -> 0x005f, SQLiteDatabaseLockedException -> 0x00c0, SQLiteException -> 0x005c, all -> 0x0059 }
        r5 = r5.zzgk();	 Catch:{ SQLiteFullException -> 0x005f, SQLiteDatabaseLockedException -> 0x00c0, SQLiteException -> 0x005c, all -> 0x0059 }
        r8 = "Different delete count than expected in local db. expected, received, difference";
        r3 = java.lang.Long.valueOf(r14);	 Catch:{ SQLiteFullException -> 0x005f, SQLiteDatabaseLockedException -> 0x00c0, SQLiteException -> 0x005c, all -> 0x0059 }
        r9 = java.lang.Long.valueOf(r11);	 Catch:{ SQLiteFullException -> 0x005f, SQLiteDatabaseLockedException -> 0x00c0, SQLiteException -> 0x005c, all -> 0x0059 }
        r14 = r14 - r11;
        r11 = java.lang.Long.valueOf(r14);	 Catch:{ SQLiteFullException -> 0x005f, SQLiteDatabaseLockedException -> 0x00c0, SQLiteException -> 0x005c, all -> 0x0059 }
        r5.zza(r8, r3, r9, r11);	 Catch:{ SQLiteFullException -> 0x005f, SQLiteDatabaseLockedException -> 0x00c0, SQLiteException -> 0x005c, all -> 0x0059 }
    L_0x00aa:
        r3 = 0;
        r10.insertOrThrow(r0, r3, r4);	 Catch:{ SQLiteFullException -> 0x005f, SQLiteDatabaseLockedException -> 0x00c0, SQLiteException -> 0x005c, all -> 0x0059 }
        r10.setTransactionSuccessful();	 Catch:{ SQLiteFullException -> 0x005f, SQLiteDatabaseLockedException -> 0x00c0, SQLiteException -> 0x005c, all -> 0x0059 }
        r10.endTransaction();	 Catch:{ SQLiteFullException -> 0x005f, SQLiteDatabaseLockedException -> 0x00c0, SQLiteException -> 0x005c, all -> 0x0059 }
        if (r13 == 0) goto L_0x00b9;
    L_0x00b6:
        r13.close();
    L_0x00b9:
        if (r10 == 0) goto L_0x00be;
    L_0x00bb:
        r10.close();
    L_0x00be:
        r2 = 1;
        return r2;
    L_0x00c0:
        r8 = r13;
        goto L_0x0104;
    L_0x00c2:
        r0 = move-exception;
        r3 = r8;
        r13 = r3;
        goto L_0x0138;
    L_0x00c7:
        r0 = move-exception;
        r3 = r8;
        r13 = r3;
    L_0x00ca:
        r8 = r10;
        goto L_0x00db;
    L_0x00cc:
        r3 = r8;
        r8 = r3;
        goto L_0x0104;
    L_0x00cf:
        r0 = move-exception;
        r3 = r8;
        goto L_0x0118;
    L_0x00d2:
        r0 = move-exception;
        r3 = r8;
        r10 = r3;
        r13 = r10;
        goto L_0x0138;
    L_0x00d8:
        r0 = move-exception;
        r3 = r8;
        r13 = r8;
    L_0x00db:
        if (r8 == 0) goto L_0x00e6;
    L_0x00dd:
        r3 = r8.inTransaction();	 Catch:{ all -> 0x00ff }
        if (r3 == 0) goto L_0x00e6;
    L_0x00e3:
        r8.endTransaction();	 Catch:{ all -> 0x00ff }
    L_0x00e6:
        r3 = r17.zzab();	 Catch:{ all -> 0x00ff }
        r3 = r3.zzgk();	 Catch:{ all -> 0x00ff }
        r3.zza(r2, r0);	 Catch:{ all -> 0x00ff }
        r3 = 1;
        r1.zzjw = r3;	 Catch:{ all -> 0x00ff }
        if (r13 == 0) goto L_0x00f9;
    L_0x00f6:
        r13.close();
    L_0x00f9:
        if (r8 == 0) goto L_0x0130;
    L_0x00fb:
        r8.close();
        goto L_0x0130;
    L_0x00ff:
        r0 = move-exception;
        r10 = r8;
        goto L_0x0138;
    L_0x0102:
        r3 = r8;
        r10 = r8;
    L_0x0104:
        r11 = (long) r7;
        android.os.SystemClock.sleep(r11);	 Catch:{ all -> 0x0136 }
        r7 = r7 + 20;
        if (r8 == 0) goto L_0x010f;
    L_0x010c:
        r8.close();
    L_0x010f:
        if (r10 == 0) goto L_0x0130;
    L_0x0111:
        r10.close();
        goto L_0x0130;
    L_0x0115:
        r0 = move-exception;
        r3 = r8;
        r10 = r8;
    L_0x0118:
        r3 = r17.zzab();	 Catch:{ all -> 0x0136 }
        r3 = r3.zzgk();	 Catch:{ all -> 0x0136 }
        r3.zza(r2, r0);	 Catch:{ all -> 0x0136 }
        r3 = 1;
        r1.zzjw = r3;	 Catch:{ all -> 0x0136 }
        if (r8 == 0) goto L_0x012b;
    L_0x0128:
        r8.close();
    L_0x012b:
        if (r10 == 0) goto L_0x0130;
    L_0x012d:
        r10.close();
    L_0x0130:
        r6 = r6 + 1;
        r3 = 0;
        r5 = 5;
        goto L_0x0028;
    L_0x0136:
        r0 = move-exception;
        r13 = r8;
    L_0x0138:
        if (r13 == 0) goto L_0x013d;
    L_0x013a:
        r13.close();
    L_0x013d:
        if (r10 == 0) goto L_0x0142;
    L_0x013f:
        r10.close();
    L_0x0142:
        throw r0;
    L_0x0143:
        r0 = r17.zzab();
        r0 = r0.zzgn();
        r2 = "Failed to write entry to local database";
        r0.zzao(r2);
        r2 = 0;
        return r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzeb.zza(int, byte[]):boolean");
    }

    public final boolean zza(zzai zzai) {
        Parcel obtain = Parcel.obtain();
        zzai.writeToParcel(obtain, 0);
        byte[] marshall = obtain.marshall();
        obtain.recycle();
        if (marshall.length <= 131072) {
            return zza(0, marshall);
        }
        zzab().zzgn().zzao("Event is too long for local database. Sending event directly to service");
        return false;
    }

    public final boolean zza(zzjn zzjn) {
        Parcel obtain = Parcel.obtain();
        zzjn.writeToParcel(obtain, 0);
        byte[] marshall = obtain.marshall();
        obtain.recycle();
        if (marshall.length <= 131072) {
            return zza(1, marshall);
        }
        zzab().zzgn().zzao("User property too long for local database. Sending directly to service");
        return false;
    }

    public final boolean zzc(zzq zzq) {
        zzz();
        byte[] zza = zzjs.zza((Parcelable) zzq);
        if (zza.length <= 131072) {
            return zza(2, zza);
        }
        zzab().zzgn().zzao("Conditional user property too long for local database. Sending directly to service");
        return false;
    }

    /* JADX WARNING: Removed duplicated region for block: B:170:0x024a  */
    /* JADX WARNING: Removed duplicated region for block: B:203:0x0252 A:{SYNTHETIC} */
    /* JADX WARNING: Removed duplicated region for block: B:172:0x024f  */
    /* JADX WARNING: Removed duplicated region for block: B:147:0x0204 A:{SYNTHETIC, Splitter: B:147:0x0204} */
    /* JADX WARNING: Removed duplicated region for block: B:153:0x021c  */
    /* JADX WARNING: Removed duplicated region for block: B:199:0x0252 A:{SYNTHETIC} */
    /* JADX WARNING: Removed duplicated region for block: B:155:0x0221  */
    /* JADX WARNING: Removed duplicated region for block: B:170:0x024a  */
    /* JADX WARNING: Removed duplicated region for block: B:172:0x024f  */
    /* JADX WARNING: Removed duplicated region for block: B:203:0x0252 A:{SYNTHETIC} */
    /* JADX WARNING: Removed duplicated region for block: B:147:0x0204 A:{SYNTHETIC, Splitter: B:147:0x0204} */
    /* JADX WARNING: Removed duplicated region for block: B:153:0x021c  */
    /* JADX WARNING: Removed duplicated region for block: B:155:0x0221  */
    /* JADX WARNING: Removed duplicated region for block: B:199:0x0252 A:{SYNTHETIC} */
    /* JADX WARNING: Removed duplicated region for block: B:170:0x024a  */
    /* JADX WARNING: Removed duplicated region for block: B:203:0x0252 A:{SYNTHETIC} */
    /* JADX WARNING: Removed duplicated region for block: B:172:0x024f  */
    /* JADX WARNING: Removed duplicated region for block: B:137:0x01f4 A:{ExcHandler: android.database.sqlite.SQLiteDatabaseLockedException (unused android.database.sqlite.SQLiteDatabaseLockedException), Splitter: B:12:0x0029} */
    /* JADX WARNING: Removed duplicated region for block: B:147:0x0204 A:{SYNTHETIC, Splitter: B:147:0x0204} */
    /* JADX WARNING: Removed duplicated region for block: B:153:0x021c  */
    /* JADX WARNING: Removed duplicated region for block: B:199:0x0252 A:{SYNTHETIC} */
    /* JADX WARNING: Removed duplicated region for block: B:155:0x0221  */
    /* JADX WARNING: Removed duplicated region for block: B:137:0x01f4 A:{ExcHandler: android.database.sqlite.SQLiteDatabaseLockedException (unused android.database.sqlite.SQLiteDatabaseLockedException), Splitter: B:12:0x0029} */
    /* JADX WARNING: Removed duplicated region for block: B:147:0x0204 A:{SYNTHETIC, Splitter: B:147:0x0204} */
    /* JADX WARNING: Removed duplicated region for block: B:153:0x021c  */
    /* JADX WARNING: Removed duplicated region for block: B:155:0x0221  */
    /* JADX WARNING: Removed duplicated region for block: B:199:0x0252 A:{SYNTHETIC} */
    /* JADX WARNING: Removed duplicated region for block: B:170:0x024a  */
    /* JADX WARNING: Removed duplicated region for block: B:172:0x024f  */
    /* JADX WARNING: Removed duplicated region for block: B:203:0x0252 A:{SYNTHETIC} */
    /* JADX WARNING: Removed duplicated region for block: B:147:0x0204 A:{SYNTHETIC, Splitter: B:147:0x0204} */
    /* JADX WARNING: Removed duplicated region for block: B:153:0x021c  */
    /* JADX WARNING: Removed duplicated region for block: B:199:0x0252 A:{SYNTHETIC} */
    /* JADX WARNING: Removed duplicated region for block: B:155:0x0221  */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing block: B:39:0x0091, code:
            r0 = th;
     */
    /* JADX WARNING: Missing block: B:40:0x0092, code:
            r24 = r15;
     */
    /* JADX WARNING: Missing block: B:41:0x0096, code:
            r0 = e;
     */
    /* JADX WARNING: Missing block: B:42:0x0097, code:
            r24 = r15;
     */
    /* JADX WARNING: Missing block: B:43:0x009b, code:
            r0 = e;
     */
    /* JADX WARNING: Missing block: B:44:0x009c, code:
            r24 = r15;
     */
    /* JADX WARNING: Missing block: B:62:?, code:
            zzab().zzgk().zzao("Failed to load event from local database");
     */
    /* JADX WARNING: Missing block: B:64:?, code:
            r11.recycle();
     */
    /* JADX WARNING: Missing block: B:77:?, code:
            zzab().zzgk().zzao(r13);
     */
    /* JADX WARNING: Missing block: B:79:?, code:
            r11.recycle();
            r0 = null;
     */
    /* JADX WARNING: Missing block: B:92:?, code:
            zzab().zzgk().zzao(r13);
     */
    /* JADX WARNING: Missing block: B:94:?, code:
            r11.recycle();
            r0 = null;
     */
    /* JADX WARNING: Missing block: B:131:0x01eb, code:
            r0 = th;
     */
    /* JADX WARNING: Missing block: B:132:0x01ec, code:
            r13 = r15;
     */
    /* JADX WARNING: Missing block: B:134:0x01f0, code:
            r0 = e;
     */
    /* JADX WARNING: Missing block: B:135:0x01f1, code:
            r13 = r15;
     */
    /* JADX WARNING: Missing block: B:137:0x01f4, code:
            r13 = r15;
     */
    /* JADX WARNING: Missing block: B:139:0x01f7, code:
            r0 = e;
     */
    /* JADX WARNING: Missing block: B:140:0x01f8, code:
            r13 = r15;
     */
    public final java.util.List<com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable> zzc(int r24) {
        /*
        r23 = this;
        r1 = r23;
        r2 = "Error reading entries from local database";
        r23.zzo();
        r23.zzm();
        r0 = r1.zzjw;
        r3 = 0;
        if (r0 == 0) goto L_0x0010;
    L_0x000f:
        return r3;
    L_0x0010:
        r4 = new java.util.ArrayList;
        r4.<init>();
        r0 = r23.zzcg();
        if (r0 != 0) goto L_0x001c;
    L_0x001b:
        return r4;
    L_0x001c:
        r5 = 5;
        r6 = 0;
        r7 = 0;
        r8 = 5;
    L_0x0020:
        if (r7 >= r5) goto L_0x0262;
    L_0x0022:
        r9 = 1;
        r15 = r23.getWritableDatabase();	 Catch:{ SQLiteFullException -> 0x0238, SQLiteDatabaseLockedException -> 0x0225, SQLiteException -> 0x01ff, all -> 0x01fb }
        if (r15 != 0) goto L_0x0040;
    L_0x0029:
        r1.zzjw = r9;	 Catch:{ SQLiteFullException -> 0x003b, SQLiteDatabaseLockedException -> 0x01f4, SQLiteException -> 0x0036, all -> 0x0031 }
        if (r15 == 0) goto L_0x0030;
    L_0x002d:
        r15.close();
    L_0x0030:
        return r3;
    L_0x0031:
        r0 = move-exception;
        r10 = r3;
        r13 = r15;
        goto L_0x0257;
    L_0x0036:
        r0 = move-exception;
        r10 = r3;
        r13 = r15;
        goto L_0x0202;
    L_0x003b:
        r0 = move-exception;
        r10 = r3;
        r13 = r15;
        goto L_0x023b;
    L_0x0040:
        r15.beginTransaction();	 Catch:{ SQLiteFullException -> 0x01f7, SQLiteDatabaseLockedException -> 0x01f4, SQLiteException -> 0x01f0, all -> 0x01eb }
        r0 = r23.zzad();	 Catch:{ SQLiteFullException -> 0x01f7, SQLiteDatabaseLockedException -> 0x01f4, SQLiteException -> 0x01f0, all -> 0x01eb }
        r10 = com.google.android.gms.measurement.internal.zzak.zzjd;	 Catch:{ SQLiteFullException -> 0x01f7, SQLiteDatabaseLockedException -> 0x01f4, SQLiteException -> 0x01f0, all -> 0x01eb }
        r0 = r0.zza(r10);	 Catch:{ SQLiteFullException -> 0x01f7, SQLiteDatabaseLockedException -> 0x01f4, SQLiteException -> 0x01f0, all -> 0x01eb }
        r10 = 100;
        r11 = "entry";
        r12 = "type";
        r13 = "rowid";
        r19 = -1;
        if (r0 == 0) goto L_0x00a0;
    L_0x0059:
        r16 = zza(r15);	 Catch:{ SQLiteFullException -> 0x009b, SQLiteDatabaseLockedException -> 0x01f4, SQLiteException -> 0x0096, all -> 0x0091 }
        r0 = (r16 > r19 ? 1 : (r16 == r19 ? 0 : -1));
        if (r0 == 0) goto L_0x006c;
    L_0x0061:
        r0 = "rowid<?";
        r14 = new java.lang.String[r9];	 Catch:{ SQLiteFullException -> 0x003b, SQLiteDatabaseLockedException -> 0x01f4, SQLiteException -> 0x0036, all -> 0x0031 }
        r16 = java.lang.String.valueOf(r16);	 Catch:{ SQLiteFullException -> 0x003b, SQLiteDatabaseLockedException -> 0x01f4, SQLiteException -> 0x0036, all -> 0x0031 }
        r14[r6] = r16;	 Catch:{ SQLiteFullException -> 0x003b, SQLiteDatabaseLockedException -> 0x01f4, SQLiteException -> 0x0036, all -> 0x0031 }
        goto L_0x006e;
    L_0x006c:
        r0 = r3;
        r14 = r0;
    L_0x006e:
        r16 = "messages";
        r12 = new java.lang.String[]{r13, r12, r11};	 Catch:{ SQLiteFullException -> 0x009b, SQLiteDatabaseLockedException -> 0x01f4, SQLiteException -> 0x0096, all -> 0x0091 }
        r17 = 0;
        r18 = 0;
        r21 = "rowid asc";
        r22 = java.lang.Integer.toString(r10);	 Catch:{ SQLiteFullException -> 0x009b, SQLiteDatabaseLockedException -> 0x01f4, SQLiteException -> 0x0096, all -> 0x0091 }
        r10 = r15;
        r11 = r16;
        r13 = r0;
        r24 = r15;
        r15 = r17;
        r16 = r18;
        r17 = r21;
        r18 = r22;
        r0 = r10.query(r11, r12, r13, r14, r15, r16, r17, r18);	 Catch:{ SQLiteFullException -> 0x01e7, SQLiteDatabaseLockedException -> 0x01e4, SQLiteException -> 0x01e0, all -> 0x01dc }
        goto L_0x00ba;
    L_0x0091:
        r0 = move-exception;
        r24 = r15;
        goto L_0x01dd;
    L_0x0096:
        r0 = move-exception;
        r24 = r15;
        goto L_0x01e1;
    L_0x009b:
        r0 = move-exception;
        r24 = r15;
        goto L_0x01e8;
    L_0x00a0:
        r24 = r15;
        r0 = "messages";
        r12 = new java.lang.String[]{r13, r12, r11};	 Catch:{ SQLiteFullException -> 0x01e7, SQLiteDatabaseLockedException -> 0x01e4, SQLiteException -> 0x01e0, all -> 0x01dc }
        r13 = 0;
        r14 = 0;
        r15 = 0;
        r16 = 0;
        r17 = "rowid asc";
        r18 = java.lang.Integer.toString(r10);	 Catch:{ SQLiteFullException -> 0x01e7, SQLiteDatabaseLockedException -> 0x01e4, SQLiteException -> 0x01e0, all -> 0x01dc }
        r10 = r24;
        r11 = r0;
        r0 = r10.query(r11, r12, r13, r14, r15, r16, r17, r18);	 Catch:{ SQLiteFullException -> 0x01e7, SQLiteDatabaseLockedException -> 0x01e4, SQLiteException -> 0x01e0, all -> 0x01dc }
    L_0x00ba:
        r10 = r0;
    L_0x00bb:
        r0 = r10.moveToNext();	 Catch:{ SQLiteFullException -> 0x01d7, SQLiteDatabaseLockedException -> 0x01d3, SQLiteException -> 0x01cf, all -> 0x01ca }
        if (r0 == 0) goto L_0x018f;
    L_0x00c1:
        r19 = r10.getLong(r6);	 Catch:{ SQLiteFullException -> 0x01d7, SQLiteDatabaseLockedException -> 0x01d3, SQLiteException -> 0x01cf, all -> 0x01ca }
        r0 = r10.getInt(r9);	 Catch:{ SQLiteFullException -> 0x01d7, SQLiteDatabaseLockedException -> 0x01d3, SQLiteException -> 0x01cf, all -> 0x01ca }
        r11 = 2;
        r12 = r10.getBlob(r11);	 Catch:{ SQLiteFullException -> 0x01d7, SQLiteDatabaseLockedException -> 0x01d3, SQLiteException -> 0x01cf, all -> 0x01ca }
        if (r0 != 0) goto L_0x0103;
    L_0x00d0:
        r11 = android.os.Parcel.obtain();	 Catch:{ SQLiteFullException -> 0x01d7, SQLiteDatabaseLockedException -> 0x01d3, SQLiteException -> 0x01cf, all -> 0x01ca }
        r0 = r12.length;	 Catch:{ ParseException -> 0x00ee }
        r11.unmarshall(r12, r6, r0);	 Catch:{ ParseException -> 0x00ee }
        r11.setDataPosition(r6);	 Catch:{ ParseException -> 0x00ee }
        r0 = com.google.android.gms.measurement.internal.zzai.CREATOR;	 Catch:{ ParseException -> 0x00ee }
        r0 = r0.createFromParcel(r11);	 Catch:{ ParseException -> 0x00ee }
        r0 = (com.google.android.gms.measurement.internal.zzai) r0;	 Catch:{ ParseException -> 0x00ee }
        r11.recycle();	 Catch:{ SQLiteFullException -> 0x01d7, SQLiteDatabaseLockedException -> 0x01d3, SQLiteException -> 0x01cf, all -> 0x01ca }
        if (r0 == 0) goto L_0x00bb;
    L_0x00e8:
        r4.add(r0);	 Catch:{ SQLiteFullException -> 0x01d7, SQLiteDatabaseLockedException -> 0x01d3, SQLiteException -> 0x01cf, all -> 0x01ca }
        goto L_0x00bb;
    L_0x00ec:
        r0 = move-exception;
        goto L_0x00ff;
    L_0x00ee:
        r0 = r23.zzab();	 Catch:{ all -> 0x00ec }
        r0 = r0.zzgk();	 Catch:{ all -> 0x00ec }
        r12 = "Failed to load event from local database";
        r0.zzao(r12);	 Catch:{ all -> 0x00ec }
        r11.recycle();	 Catch:{ SQLiteFullException -> 0x01d7, SQLiteDatabaseLockedException -> 0x01d3, SQLiteException -> 0x01cf, all -> 0x01ca }
        goto L_0x00bb;
    L_0x00ff:
        r11.recycle();	 Catch:{ SQLiteFullException -> 0x01d7, SQLiteDatabaseLockedException -> 0x01d3, SQLiteException -> 0x01cf, all -> 0x01ca }
        throw r0;	 Catch:{ SQLiteFullException -> 0x01d7, SQLiteDatabaseLockedException -> 0x01d3, SQLiteException -> 0x01cf, all -> 0x01ca }
    L_0x0103:
        r13 = "Failed to load user property from local database";
        if (r0 != r9) goto L_0x0139;
    L_0x0107:
        r11 = android.os.Parcel.obtain();	 Catch:{ SQLiteFullException -> 0x01d7, SQLiteDatabaseLockedException -> 0x01d3, SQLiteException -> 0x01cf, all -> 0x01ca }
        r0 = r12.length;	 Catch:{ ParseException -> 0x0120 }
        r11.unmarshall(r12, r6, r0);	 Catch:{ ParseException -> 0x0120 }
        r11.setDataPosition(r6);	 Catch:{ ParseException -> 0x0120 }
        r0 = com.google.android.gms.measurement.internal.zzjn.CREATOR;	 Catch:{ ParseException -> 0x0120 }
        r0 = r0.createFromParcel(r11);	 Catch:{ ParseException -> 0x0120 }
        r0 = (com.google.android.gms.measurement.internal.zzjn) r0;	 Catch:{ ParseException -> 0x0120 }
        r11.recycle();	 Catch:{ SQLiteFullException -> 0x01d7, SQLiteDatabaseLockedException -> 0x01d3, SQLiteException -> 0x01cf, all -> 0x01ca }
        goto L_0x012f;
    L_0x011e:
        r0 = move-exception;
        goto L_0x0135;
    L_0x0120:
        r0 = r23.zzab();	 Catch:{ all -> 0x011e }
        r0 = r0.zzgk();	 Catch:{ all -> 0x011e }
        r0.zzao(r13);	 Catch:{ all -> 0x011e }
        r11.recycle();	 Catch:{ SQLiteFullException -> 0x01d7, SQLiteDatabaseLockedException -> 0x01d3, SQLiteException -> 0x01cf, all -> 0x01ca }
        r0 = r3;
    L_0x012f:
        if (r0 == 0) goto L_0x00bb;
    L_0x0131:
        r4.add(r0);	 Catch:{ SQLiteFullException -> 0x01d7, SQLiteDatabaseLockedException -> 0x01d3, SQLiteException -> 0x01cf, all -> 0x01ca }
        goto L_0x00bb;
    L_0x0135:
        r11.recycle();	 Catch:{ SQLiteFullException -> 0x01d7, SQLiteDatabaseLockedException -> 0x01d3, SQLiteException -> 0x01cf, all -> 0x01ca }
        throw r0;	 Catch:{ SQLiteFullException -> 0x01d7, SQLiteDatabaseLockedException -> 0x01d3, SQLiteException -> 0x01cf, all -> 0x01ca }
    L_0x0139:
        if (r0 != r11) goto L_0x016e;
    L_0x013b:
        r11 = android.os.Parcel.obtain();	 Catch:{ SQLiteFullException -> 0x01d7, SQLiteDatabaseLockedException -> 0x01d3, SQLiteException -> 0x01cf, all -> 0x01ca }
        r0 = r12.length;	 Catch:{ ParseException -> 0x0154 }
        r11.unmarshall(r12, r6, r0);	 Catch:{ ParseException -> 0x0154 }
        r11.setDataPosition(r6);	 Catch:{ ParseException -> 0x0154 }
        r0 = com.google.android.gms.measurement.internal.zzq.CREATOR;	 Catch:{ ParseException -> 0x0154 }
        r0 = r0.createFromParcel(r11);	 Catch:{ ParseException -> 0x0154 }
        r0 = (com.google.android.gms.measurement.internal.zzq) r0;	 Catch:{ ParseException -> 0x0154 }
        r11.recycle();	 Catch:{ SQLiteFullException -> 0x01d7, SQLiteDatabaseLockedException -> 0x01d3, SQLiteException -> 0x01cf, all -> 0x01ca }
        goto L_0x0163;
    L_0x0152:
        r0 = move-exception;
        goto L_0x016a;
    L_0x0154:
        r0 = r23.zzab();	 Catch:{ all -> 0x0152 }
        r0 = r0.zzgk();	 Catch:{ all -> 0x0152 }
        r0.zzao(r13);	 Catch:{ all -> 0x0152 }
        r11.recycle();	 Catch:{ SQLiteFullException -> 0x01d7, SQLiteDatabaseLockedException -> 0x01d3, SQLiteException -> 0x01cf, all -> 0x01ca }
        r0 = r3;
    L_0x0163:
        if (r0 == 0) goto L_0x00bb;
    L_0x0165:
        r4.add(r0);	 Catch:{ SQLiteFullException -> 0x01d7, SQLiteDatabaseLockedException -> 0x01d3, SQLiteException -> 0x01cf, all -> 0x01ca }
        goto L_0x00bb;
    L_0x016a:
        r11.recycle();	 Catch:{ SQLiteFullException -> 0x01d7, SQLiteDatabaseLockedException -> 0x01d3, SQLiteException -> 0x01cf, all -> 0x01ca }
        throw r0;	 Catch:{ SQLiteFullException -> 0x01d7, SQLiteDatabaseLockedException -> 0x01d3, SQLiteException -> 0x01cf, all -> 0x01ca }
    L_0x016e:
        r11 = 3;
        if (r0 != r11) goto L_0x0180;
    L_0x0171:
        r0 = r23.zzab();	 Catch:{ SQLiteFullException -> 0x01d7, SQLiteDatabaseLockedException -> 0x01d3, SQLiteException -> 0x01cf, all -> 0x01ca }
        r0 = r0.zzgn();	 Catch:{ SQLiteFullException -> 0x01d7, SQLiteDatabaseLockedException -> 0x01d3, SQLiteException -> 0x01cf, all -> 0x01ca }
        r11 = "Skipping app launch break";
        r0.zzao(r11);	 Catch:{ SQLiteFullException -> 0x01d7, SQLiteDatabaseLockedException -> 0x01d3, SQLiteException -> 0x01cf, all -> 0x01ca }
        goto L_0x00bb;
    L_0x0180:
        r0 = r23.zzab();	 Catch:{ SQLiteFullException -> 0x01d7, SQLiteDatabaseLockedException -> 0x01d3, SQLiteException -> 0x01cf, all -> 0x01ca }
        r0 = r0.zzgk();	 Catch:{ SQLiteFullException -> 0x01d7, SQLiteDatabaseLockedException -> 0x01d3, SQLiteException -> 0x01cf, all -> 0x01ca }
        r11 = "Unknown record type in local database";
        r0.zzao(r11);	 Catch:{ SQLiteFullException -> 0x01d7, SQLiteDatabaseLockedException -> 0x01d3, SQLiteException -> 0x01cf, all -> 0x01ca }
        goto L_0x00bb;
    L_0x018f:
        r0 = "messages";
        r11 = "rowid <= ?";
        r12 = new java.lang.String[r9];	 Catch:{ SQLiteFullException -> 0x01d7, SQLiteDatabaseLockedException -> 0x01d3, SQLiteException -> 0x01cf, all -> 0x01ca }
        r13 = java.lang.Long.toString(r19);	 Catch:{ SQLiteFullException -> 0x01d7, SQLiteDatabaseLockedException -> 0x01d3, SQLiteException -> 0x01cf, all -> 0x01ca }
        r12[r6] = r13;	 Catch:{ SQLiteFullException -> 0x01d7, SQLiteDatabaseLockedException -> 0x01d3, SQLiteException -> 0x01cf, all -> 0x01ca }
        r13 = r24;
        r0 = r13.delete(r0, r11, r12);	 Catch:{ SQLiteFullException -> 0x01c7, SQLiteDatabaseLockedException -> 0x0227, SQLiteException -> 0x01c5 }
        r11 = r4.size();	 Catch:{ SQLiteFullException -> 0x01c7, SQLiteDatabaseLockedException -> 0x0227, SQLiteException -> 0x01c5 }
        if (r0 >= r11) goto L_0x01b4;
    L_0x01a7:
        r0 = r23.zzab();	 Catch:{ SQLiteFullException -> 0x01c7, SQLiteDatabaseLockedException -> 0x0227, SQLiteException -> 0x01c5 }
        r0 = r0.zzgk();	 Catch:{ SQLiteFullException -> 0x01c7, SQLiteDatabaseLockedException -> 0x0227, SQLiteException -> 0x01c5 }
        r11 = "Fewer entries removed from local database than expected";
        r0.zzao(r11);	 Catch:{ SQLiteFullException -> 0x01c7, SQLiteDatabaseLockedException -> 0x0227, SQLiteException -> 0x01c5 }
    L_0x01b4:
        r13.setTransactionSuccessful();	 Catch:{ SQLiteFullException -> 0x01c7, SQLiteDatabaseLockedException -> 0x0227, SQLiteException -> 0x01c5 }
        r13.endTransaction();	 Catch:{ SQLiteFullException -> 0x01c7, SQLiteDatabaseLockedException -> 0x0227, SQLiteException -> 0x01c5 }
        if (r10 == 0) goto L_0x01bf;
    L_0x01bc:
        r10.close();
    L_0x01bf:
        if (r13 == 0) goto L_0x01c4;
    L_0x01c1:
        r13.close();
    L_0x01c4:
        return r4;
    L_0x01c5:
        r0 = move-exception;
        goto L_0x0202;
    L_0x01c7:
        r0 = move-exception;
        goto L_0x023b;
    L_0x01ca:
        r0 = move-exception;
        r13 = r24;
        goto L_0x0257;
    L_0x01cf:
        r0 = move-exception;
        r13 = r24;
        goto L_0x0202;
    L_0x01d3:
        r13 = r24;
        goto L_0x0227;
    L_0x01d7:
        r0 = move-exception;
        r13 = r24;
        goto L_0x023b;
    L_0x01dc:
        r0 = move-exception;
    L_0x01dd:
        r13 = r24;
        goto L_0x01ed;
    L_0x01e0:
        r0 = move-exception;
    L_0x01e1:
        r13 = r24;
        goto L_0x01f2;
    L_0x01e4:
        r13 = r24;
        goto L_0x01f5;
    L_0x01e7:
        r0 = move-exception;
    L_0x01e8:
        r13 = r24;
        goto L_0x01f9;
    L_0x01eb:
        r0 = move-exception;
        r13 = r15;
    L_0x01ed:
        r10 = r3;
        goto L_0x0257;
    L_0x01f0:
        r0 = move-exception;
        r13 = r15;
    L_0x01f2:
        r10 = r3;
        goto L_0x0202;
    L_0x01f4:
        r13 = r15;
    L_0x01f5:
        r10 = r3;
        goto L_0x0227;
    L_0x01f7:
        r0 = move-exception;
        r13 = r15;
    L_0x01f9:
        r10 = r3;
        goto L_0x023b;
    L_0x01fb:
        r0 = move-exception;
        r10 = r3;
        r13 = r10;
        goto L_0x0257;
    L_0x01ff:
        r0 = move-exception;
        r10 = r3;
        r13 = r10;
    L_0x0202:
        if (r13 == 0) goto L_0x020d;
    L_0x0204:
        r11 = r13.inTransaction();	 Catch:{ all -> 0x0256 }
        if (r11 == 0) goto L_0x020d;
    L_0x020a:
        r13.endTransaction();	 Catch:{ all -> 0x0256 }
    L_0x020d:
        r11 = r23.zzab();	 Catch:{ all -> 0x0256 }
        r11 = r11.zzgk();	 Catch:{ all -> 0x0256 }
        r11.zza(r2, r0);	 Catch:{ all -> 0x0256 }
        r1.zzjw = r9;	 Catch:{ all -> 0x0256 }
        if (r10 == 0) goto L_0x021f;
    L_0x021c:
        r10.close();
    L_0x021f:
        if (r13 == 0) goto L_0x0252;
    L_0x0221:
        r13.close();
        goto L_0x0252;
    L_0x0225:
        r10 = r3;
        r13 = r10;
    L_0x0227:
        r11 = (long) r8;
        android.os.SystemClock.sleep(r11);	 Catch:{ all -> 0x0256 }
        r8 = r8 + 20;
        if (r10 == 0) goto L_0x0232;
    L_0x022f:
        r10.close();
    L_0x0232:
        if (r13 == 0) goto L_0x0252;
    L_0x0234:
        r13.close();
        goto L_0x0252;
    L_0x0238:
        r0 = move-exception;
        r10 = r3;
        r13 = r10;
    L_0x023b:
        r11 = r23.zzab();	 Catch:{ all -> 0x0256 }
        r11 = r11.zzgk();	 Catch:{ all -> 0x0256 }
        r11.zza(r2, r0);	 Catch:{ all -> 0x0256 }
        r1.zzjw = r9;	 Catch:{ all -> 0x0256 }
        if (r10 == 0) goto L_0x024d;
    L_0x024a:
        r10.close();
    L_0x024d:
        if (r13 == 0) goto L_0x0252;
    L_0x024f:
        r13.close();
    L_0x0252:
        r7 = r7 + 1;
        goto L_0x0020;
    L_0x0256:
        r0 = move-exception;
    L_0x0257:
        if (r10 == 0) goto L_0x025c;
    L_0x0259:
        r10.close();
    L_0x025c:
        if (r13 == 0) goto L_0x0261;
    L_0x025e:
        r13.close();
    L_0x0261:
        throw r0;
    L_0x0262:
        r0 = r23.zzab();
        r0 = r0.zzgn();
        r2 = "Failed to read events from database in reasonable time";
        r0.zzao(r2);
        return r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzeb.zzc(int):java.util.List<com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable>");
    }

    @WorkerThread
    public final boolean zzgh() {
        return zza(3, new byte[0]);
    }

    @WorkerThread
    public final boolean zzgi() {
        String str = "Error deleting app launch break from local database";
        zzo();
        zzm();
        if (this.zzjw || !zzcg()) {
            return false;
        }
        int i = 0;
        int i2 = 5;
        while (i < 5) {
            SQLiteDatabase sQLiteDatabase = null;
            try {
                sQLiteDatabase = getWritableDatabase();
                if (sQLiteDatabase == null) {
                    this.zzjw = true;
                    if (sQLiteDatabase != null) {
                        sQLiteDatabase.close();
                    }
                    return false;
                }
                sQLiteDatabase.beginTransaction();
                sQLiteDatabase.delete("messages", "type == ?", new String[]{Integer.toString(3)});
                sQLiteDatabase.setTransactionSuccessful();
                sQLiteDatabase.endTransaction();
                if (sQLiteDatabase != null) {
                    sQLiteDatabase.close();
                }
                return true;
            } catch (SQLiteFullException e) {
                zzab().zzgk().zza(str, e);
                this.zzjw = true;
                if (sQLiteDatabase != null) {
                    sQLiteDatabase.close();
                }
                i++;
            } catch (SQLiteDatabaseLockedException unused) {
                SystemClock.sleep((long) i2);
                i2 += 20;
                if (sQLiteDatabase != null) {
                    sQLiteDatabase.close();
                }
                i++;
            } catch (SQLiteException e2) {
                if (sQLiteDatabase != null) {
                    if (sQLiteDatabase.inTransaction()) {
                        sQLiteDatabase.endTransaction();
                    }
                }
                zzab().zzgk().zza(str, e2);
                this.zzjw = true;
                if (sQLiteDatabase != null) {
                    sQLiteDatabase.close();
                }
                i++;
            } catch (Throwable th) {
                if (sQLiteDatabase != null) {
                    sQLiteDatabase.close();
                }
            }
        }
        zzab().zzgn().zzao("Error deleting app launch break from local database in reasonable time");
        return false;
    }

    private static long zza(SQLiteDatabase sQLiteDatabase) {
        Cursor cursor = null;
        try {
            cursor = sQLiteDatabase.query("messages", new String[]{"rowid"}, "type=?", new String[]{ExifInterface.GPS_MEASUREMENT_3D}, null, null, "rowid desc", "1");
            if (cursor.moveToFirst()) {
                long j = cursor.getLong(0);
                return j;
            }
            if (cursor != null) {
                cursor.close();
            }
            return -1;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    @WorkerThread
    @VisibleForTesting
    private final SQLiteDatabase getWritableDatabase() throws SQLiteException {
        if (this.zzjw) {
            return null;
        }
        SQLiteDatabase writableDatabase = this.zzjv.getWritableDatabase();
        if (writableDatabase != null) {
            return writableDatabase;
        }
        this.zzjw = true;
        return null;
    }

    @VisibleForTesting
    private final boolean zzcg() {
        return getContext().getDatabasePath("google_app_measurement_local.db").exists();
    }
}
