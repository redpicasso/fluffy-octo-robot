package androidx.appcompat.widget;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.database.DataSetObservable;
import android.os.AsyncTask;
import android.text.TextUtils;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ActivityChooserModel extends DataSetObservable {
    static final String ATTRIBUTE_ACTIVITY = "activity";
    static final String ATTRIBUTE_TIME = "time";
    static final String ATTRIBUTE_WEIGHT = "weight";
    static final boolean DEBUG = false;
    private static final int DEFAULT_ACTIVITY_INFLATION = 5;
    private static final float DEFAULT_HISTORICAL_RECORD_WEIGHT = 1.0f;
    public static final String DEFAULT_HISTORY_FILE_NAME = "activity_choser_model_history.xml";
    public static final int DEFAULT_HISTORY_MAX_LENGTH = 50;
    private static final String HISTORY_FILE_EXTENSION = ".xml";
    private static final int INVALID_INDEX = -1;
    static final String LOG_TAG = "ActivityChooserModel";
    static final String TAG_HISTORICAL_RECORD = "historical-record";
    static final String TAG_HISTORICAL_RECORDS = "historical-records";
    private static final Map<String, ActivityChooserModel> sDataModelRegistry = new HashMap();
    private static final Object sRegistryLock = new Object();
    private final List<ActivityResolveInfo> mActivities = new ArrayList();
    private OnChooseActivityListener mActivityChoserModelPolicy;
    private ActivitySorter mActivitySorter = new DefaultSorter();
    boolean mCanReadHistoricalData = true;
    final Context mContext;
    private final List<HistoricalRecord> mHistoricalRecords = new ArrayList();
    private boolean mHistoricalRecordsChanged = true;
    final String mHistoryFileName;
    private int mHistoryMaxSize = 50;
    private final Object mInstanceLock = new Object();
    private Intent mIntent;
    private boolean mReadShareHistoryCalled = false;
    private boolean mReloadActivities = false;

    public interface ActivityChooserModelClient {
        void setActivityChooserModel(ActivityChooserModel activityChooserModel);
    }

    public static final class ActivityResolveInfo implements Comparable<ActivityResolveInfo> {
        public final ResolveInfo resolveInfo;
        public float weight;

        public ActivityResolveInfo(ResolveInfo resolveInfo) {
            this.resolveInfo = resolveInfo;
        }

        public int hashCode() {
            return Float.floatToIntBits(this.weight) + 31;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            return Float.floatToIntBits(this.weight) == Float.floatToIntBits(((ActivityResolveInfo) obj).weight);
        }

        public int compareTo(ActivityResolveInfo activityResolveInfo) {
            return Float.floatToIntBits(activityResolveInfo.weight) - Float.floatToIntBits(this.weight);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("[");
            stringBuilder.append("resolveInfo:");
            stringBuilder.append(this.resolveInfo.toString());
            stringBuilder.append("; weight:");
            stringBuilder.append(new BigDecimal((double) this.weight));
            stringBuilder.append("]");
            return stringBuilder.toString();
        }
    }

    public interface ActivitySorter {
        void sort(Intent intent, List<ActivityResolveInfo> list, List<HistoricalRecord> list2);
    }

    public static final class HistoricalRecord {
        public final ComponentName activity;
        public final long time;
        public final float weight;

        public HistoricalRecord(String str, long j, float f) {
            this(ComponentName.unflattenFromString(str), j, f);
        }

        public HistoricalRecord(ComponentName componentName, long j, float f) {
            this.activity = componentName;
            this.time = j;
            this.weight = f;
        }

        public int hashCode() {
            ComponentName componentName = this.activity;
            int hashCode = ((componentName == null ? 0 : componentName.hashCode()) + 31) * 31;
            long j = this.time;
            return ((hashCode + ((int) (j ^ (j >>> 32)))) * 31) + Float.floatToIntBits(this.weight);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            HistoricalRecord historicalRecord = (HistoricalRecord) obj;
            ComponentName componentName = this.activity;
            if (componentName == null) {
                if (historicalRecord.activity != null) {
                    return false;
                }
            } else if (!componentName.equals(historicalRecord.activity)) {
                return false;
            }
            return this.time == historicalRecord.time && Float.floatToIntBits(this.weight) == Float.floatToIntBits(historicalRecord.weight);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("[");
            stringBuilder.append("; activity:");
            stringBuilder.append(this.activity);
            stringBuilder.append("; time:");
            stringBuilder.append(this.time);
            stringBuilder.append("; weight:");
            stringBuilder.append(new BigDecimal((double) this.weight));
            stringBuilder.append("]");
            return stringBuilder.toString();
        }
    }

    public interface OnChooseActivityListener {
        boolean onChooseActivity(ActivityChooserModel activityChooserModel, Intent intent);
    }

    private final class PersistHistoryAsyncTask extends AsyncTask<Object, Void, Void> {
        PersistHistoryAsyncTask() {
        }

        /* JADX WARNING: Missing block: B:10:0x006d, code:
            if (r15 != null) goto L_0x006f;
     */
        /* JADX WARNING: Missing block: B:12:?, code:
            r15.close();
     */
        /* JADX WARNING: Missing block: B:18:0x0092, code:
            if (r15 == null) goto L_0x00d5;
     */
        /* JADX WARNING: Missing block: B:23:0x00b2, code:
            if (r15 == null) goto L_0x00d5;
     */
        /* JADX WARNING: Missing block: B:28:0x00d2, code:
            if (r15 == null) goto L_0x00d5;
     */
        /* JADX WARNING: Missing block: B:29:0x00d5, code:
            return null;
     */
        public java.lang.Void doInBackground(java.lang.Object... r15) {
            /*
            r14 = this;
            r0 = "historical-record";
            r1 = "historical-records";
            r2 = "Error writing historical record file: ";
            r3 = 0;
            r4 = r15[r3];
            r4 = (java.util.List) r4;
            r5 = 1;
            r15 = r15[r5];
            r15 = (java.lang.String) r15;
            r6 = 0;
            r7 = androidx.appcompat.widget.ActivityChooserModel.this;	 Catch:{ FileNotFoundException -> 0x00e0 }
            r7 = r7.mContext;	 Catch:{ FileNotFoundException -> 0x00e0 }
            r15 = r7.openFileOutput(r15, r3);	 Catch:{ FileNotFoundException -> 0x00e0 }
            r7 = android.util.Xml.newSerializer();
            r7.setOutput(r15, r6);	 Catch:{ IllegalArgumentException -> 0x00b5, IllegalStateException -> 0x0095, IOException -> 0x0075 }
            r8 = "UTF-8";
            r9 = java.lang.Boolean.valueOf(r5);	 Catch:{ IllegalArgumentException -> 0x00b5, IllegalStateException -> 0x0095, IOException -> 0x0075 }
            r7.startDocument(r8, r9);	 Catch:{ IllegalArgumentException -> 0x00b5, IllegalStateException -> 0x0095, IOException -> 0x0075 }
            r7.startTag(r6, r1);	 Catch:{ IllegalArgumentException -> 0x00b5, IllegalStateException -> 0x0095, IOException -> 0x0075 }
            r8 = r4.size();	 Catch:{ IllegalArgumentException -> 0x00b5, IllegalStateException -> 0x0095, IOException -> 0x0075 }
            r9 = 0;
        L_0x0031:
            if (r9 >= r8) goto L_0x0063;
        L_0x0033:
            r10 = r4.remove(r3);	 Catch:{ IllegalArgumentException -> 0x00b5, IllegalStateException -> 0x0095, IOException -> 0x0075 }
            r10 = (androidx.appcompat.widget.ActivityChooserModel.HistoricalRecord) r10;	 Catch:{ IllegalArgumentException -> 0x00b5, IllegalStateException -> 0x0095, IOException -> 0x0075 }
            r7.startTag(r6, r0);	 Catch:{ IllegalArgumentException -> 0x00b5, IllegalStateException -> 0x0095, IOException -> 0x0075 }
            r11 = "activity";
            r12 = r10.activity;	 Catch:{ IllegalArgumentException -> 0x00b5, IllegalStateException -> 0x0095, IOException -> 0x0075 }
            r12 = r12.flattenToString();	 Catch:{ IllegalArgumentException -> 0x00b5, IllegalStateException -> 0x0095, IOException -> 0x0075 }
            r7.attribute(r6, r11, r12);	 Catch:{ IllegalArgumentException -> 0x00b5, IllegalStateException -> 0x0095, IOException -> 0x0075 }
            r11 = "time";
            r12 = r10.time;	 Catch:{ IllegalArgumentException -> 0x00b5, IllegalStateException -> 0x0095, IOException -> 0x0075 }
            r12 = java.lang.String.valueOf(r12);	 Catch:{ IllegalArgumentException -> 0x00b5, IllegalStateException -> 0x0095, IOException -> 0x0075 }
            r7.attribute(r6, r11, r12);	 Catch:{ IllegalArgumentException -> 0x00b5, IllegalStateException -> 0x0095, IOException -> 0x0075 }
            r11 = "weight";
            r10 = r10.weight;	 Catch:{ IllegalArgumentException -> 0x00b5, IllegalStateException -> 0x0095, IOException -> 0x0075 }
            r10 = java.lang.String.valueOf(r10);	 Catch:{ IllegalArgumentException -> 0x00b5, IllegalStateException -> 0x0095, IOException -> 0x0075 }
            r7.attribute(r6, r11, r10);	 Catch:{ IllegalArgumentException -> 0x00b5, IllegalStateException -> 0x0095, IOException -> 0x0075 }
            r7.endTag(r6, r0);	 Catch:{ IllegalArgumentException -> 0x00b5, IllegalStateException -> 0x0095, IOException -> 0x0075 }
            r9 = r9 + 1;
            goto L_0x0031;
        L_0x0063:
            r7.endTag(r6, r1);	 Catch:{ IllegalArgumentException -> 0x00b5, IllegalStateException -> 0x0095, IOException -> 0x0075 }
            r7.endDocument();	 Catch:{ IllegalArgumentException -> 0x00b5, IllegalStateException -> 0x0095, IOException -> 0x0075 }
            r0 = androidx.appcompat.widget.ActivityChooserModel.this;
            r0.mCanReadHistoricalData = r5;
            if (r15 == 0) goto L_0x00d5;
        L_0x006f:
            r15.close();	 Catch:{ IOException -> 0x00d5 }
            goto L_0x00d5;
        L_0x0073:
            r0 = move-exception;
            goto L_0x00d6;
        L_0x0075:
            r0 = move-exception;
            r1 = androidx.appcompat.widget.ActivityChooserModel.LOG_TAG;	 Catch:{ all -> 0x0073 }
            r3 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0073 }
            r3.<init>();	 Catch:{ all -> 0x0073 }
            r3.append(r2);	 Catch:{ all -> 0x0073 }
            r2 = androidx.appcompat.widget.ActivityChooserModel.this;	 Catch:{ all -> 0x0073 }
            r2 = r2.mHistoryFileName;	 Catch:{ all -> 0x0073 }
            r3.append(r2);	 Catch:{ all -> 0x0073 }
            r2 = r3.toString();	 Catch:{ all -> 0x0073 }
            android.util.Log.e(r1, r2, r0);	 Catch:{ all -> 0x0073 }
            r0 = androidx.appcompat.widget.ActivityChooserModel.this;
            r0.mCanReadHistoricalData = r5;
            if (r15 == 0) goto L_0x00d5;
        L_0x0094:
            goto L_0x006f;
        L_0x0095:
            r0 = move-exception;
            r1 = androidx.appcompat.widget.ActivityChooserModel.LOG_TAG;	 Catch:{ all -> 0x0073 }
            r3 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0073 }
            r3.<init>();	 Catch:{ all -> 0x0073 }
            r3.append(r2);	 Catch:{ all -> 0x0073 }
            r2 = androidx.appcompat.widget.ActivityChooserModel.this;	 Catch:{ all -> 0x0073 }
            r2 = r2.mHistoryFileName;	 Catch:{ all -> 0x0073 }
            r3.append(r2);	 Catch:{ all -> 0x0073 }
            r2 = r3.toString();	 Catch:{ all -> 0x0073 }
            android.util.Log.e(r1, r2, r0);	 Catch:{ all -> 0x0073 }
            r0 = androidx.appcompat.widget.ActivityChooserModel.this;
            r0.mCanReadHistoricalData = r5;
            if (r15 == 0) goto L_0x00d5;
        L_0x00b4:
            goto L_0x006f;
        L_0x00b5:
            r0 = move-exception;
            r1 = androidx.appcompat.widget.ActivityChooserModel.LOG_TAG;	 Catch:{ all -> 0x0073 }
            r3 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0073 }
            r3.<init>();	 Catch:{ all -> 0x0073 }
            r3.append(r2);	 Catch:{ all -> 0x0073 }
            r2 = androidx.appcompat.widget.ActivityChooserModel.this;	 Catch:{ all -> 0x0073 }
            r2 = r2.mHistoryFileName;	 Catch:{ all -> 0x0073 }
            r3.append(r2);	 Catch:{ all -> 0x0073 }
            r2 = r3.toString();	 Catch:{ all -> 0x0073 }
            android.util.Log.e(r1, r2, r0);	 Catch:{ all -> 0x0073 }
            r0 = androidx.appcompat.widget.ActivityChooserModel.this;
            r0.mCanReadHistoricalData = r5;
            if (r15 == 0) goto L_0x00d5;
        L_0x00d4:
            goto L_0x006f;
        L_0x00d5:
            return r6;
        L_0x00d6:
            r1 = androidx.appcompat.widget.ActivityChooserModel.this;
            r1.mCanReadHistoricalData = r5;
            if (r15 == 0) goto L_0x00df;
        L_0x00dc:
            r15.close();	 Catch:{ IOException -> 0x00df }
        L_0x00df:
            throw r0;
        L_0x00e0:
            r0 = move-exception;
            r1 = androidx.appcompat.widget.ActivityChooserModel.LOG_TAG;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r3.append(r2);
            r3.append(r15);
            r15 = r3.toString();
            android.util.Log.e(r1, r15, r0);
            return r6;
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.ActivityChooserModel.PersistHistoryAsyncTask.doInBackground(java.lang.Object[]):java.lang.Void");
        }
    }

    private static final class DefaultSorter implements ActivitySorter {
        private static final float WEIGHT_DECAY_COEFFICIENT = 0.95f;
        private final Map<ComponentName, ActivityResolveInfo> mPackageNameToActivityMap = new HashMap();

        DefaultSorter() {
        }

        public void sort(Intent intent, List<ActivityResolveInfo> list, List<HistoricalRecord> list2) {
            Map map = this.mPackageNameToActivityMap;
            map.clear();
            int size = list.size();
            for (int i = 0; i < size; i++) {
                ActivityResolveInfo activityResolveInfo = (ActivityResolveInfo) list.get(i);
                activityResolveInfo.weight = 0.0f;
                map.put(new ComponentName(activityResolveInfo.resolveInfo.activityInfo.packageName, activityResolveInfo.resolveInfo.activityInfo.name), activityResolveInfo);
            }
            float f = ActivityChooserModel.DEFAULT_HISTORICAL_RECORD_WEIGHT;
            for (size = list2.size() - 1; size >= 0; size--) {
                HistoricalRecord historicalRecord = (HistoricalRecord) list2.get(size);
                ActivityResolveInfo activityResolveInfo2 = (ActivityResolveInfo) map.get(historicalRecord.activity);
                if (activityResolveInfo2 != null) {
                    activityResolveInfo2.weight += historicalRecord.weight * f;
                    f *= WEIGHT_DECAY_COEFFICIENT;
                }
            }
            Collections.sort(list);
        }
    }

    public static ActivityChooserModel get(Context context, String str) {
        ActivityChooserModel activityChooserModel;
        synchronized (sRegistryLock) {
            activityChooserModel = (ActivityChooserModel) sDataModelRegistry.get(str);
            if (activityChooserModel == null) {
                activityChooserModel = new ActivityChooserModel(context, str);
                sDataModelRegistry.put(str, activityChooserModel);
            }
        }
        return activityChooserModel;
    }

    private ActivityChooserModel(Context context, String str) {
        this.mContext = context.getApplicationContext();
        if (!TextUtils.isEmpty(str)) {
            String str2 = HISTORY_FILE_EXTENSION;
            if (!str.endsWith(str2)) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(str);
                stringBuilder.append(str2);
                this.mHistoryFileName = stringBuilder.toString();
                return;
            }
        }
        this.mHistoryFileName = str;
    }

    public void setIntent(Intent intent) {
        synchronized (this.mInstanceLock) {
            if (this.mIntent == intent) {
                return;
            }
            this.mIntent = intent;
            this.mReloadActivities = true;
            ensureConsistentState();
        }
    }

    public Intent getIntent() {
        Intent intent;
        synchronized (this.mInstanceLock) {
            intent = this.mIntent;
        }
        return intent;
    }

    public int getActivityCount() {
        int size;
        synchronized (this.mInstanceLock) {
            ensureConsistentState();
            size = this.mActivities.size();
        }
        return size;
    }

    public ResolveInfo getActivity(int i) {
        ResolveInfo resolveInfo;
        synchronized (this.mInstanceLock) {
            ensureConsistentState();
            resolveInfo = ((ActivityResolveInfo) this.mActivities.get(i)).resolveInfo;
        }
        return resolveInfo;
    }

    public int getActivityIndex(ResolveInfo resolveInfo) {
        synchronized (this.mInstanceLock) {
            ensureConsistentState();
            List list = this.mActivities;
            int size = list.size();
            for (int i = 0; i < size; i++) {
                if (((ActivityResolveInfo) list.get(i)).resolveInfo == resolveInfo) {
                    return i;
                }
            }
            return -1;
        }
    }

    public Intent chooseActivity(int i) {
        synchronized (this.mInstanceLock) {
            if (this.mIntent == null) {
                return null;
            }
            ensureConsistentState();
            ActivityResolveInfo activityResolveInfo = (ActivityResolveInfo) this.mActivities.get(i);
            ComponentName componentName = new ComponentName(activityResolveInfo.resolveInfo.activityInfo.packageName, activityResolveInfo.resolveInfo.activityInfo.name);
            Intent intent = new Intent(this.mIntent);
            intent.setComponent(componentName);
            if (this.mActivityChoserModelPolicy != null) {
                if (this.mActivityChoserModelPolicy.onChooseActivity(this, new Intent(intent))) {
                    return null;
                }
            }
            addHistoricalRecord(new HistoricalRecord(componentName, System.currentTimeMillis(), (float) DEFAULT_HISTORICAL_RECORD_WEIGHT));
            return intent;
        }
    }

    public void setOnChooseActivityListener(OnChooseActivityListener onChooseActivityListener) {
        synchronized (this.mInstanceLock) {
            this.mActivityChoserModelPolicy = onChooseActivityListener;
        }
    }

    public ResolveInfo getDefaultActivity() {
        synchronized (this.mInstanceLock) {
            ensureConsistentState();
            if (this.mActivities.isEmpty()) {
                return null;
            }
            ResolveInfo resolveInfo = ((ActivityResolveInfo) this.mActivities.get(0)).resolveInfo;
            return resolveInfo;
        }
    }

    public void setDefaultActivity(int i) {
        synchronized (this.mInstanceLock) {
            ensureConsistentState();
            ActivityResolveInfo activityResolveInfo = (ActivityResolveInfo) this.mActivities.get(i);
            ActivityResolveInfo activityResolveInfo2 = (ActivityResolveInfo) this.mActivities.get(0);
            addHistoricalRecord(new HistoricalRecord(new ComponentName(activityResolveInfo.resolveInfo.activityInfo.packageName, activityResolveInfo.resolveInfo.activityInfo.name), System.currentTimeMillis(), activityResolveInfo2 != null ? (activityResolveInfo2.weight - activityResolveInfo.weight) + 5.0f : DEFAULT_HISTORICAL_RECORD_WEIGHT));
        }
    }

    private void persistHistoricalDataIfNeeded() {
        if (!this.mReadShareHistoryCalled) {
            throw new IllegalStateException("No preceding call to #readHistoricalData");
        } else if (this.mHistoricalRecordsChanged) {
            this.mHistoricalRecordsChanged = false;
            if (!TextUtils.isEmpty(this.mHistoryFileName)) {
                new PersistHistoryAsyncTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Object[]{new ArrayList(this.mHistoricalRecords), this.mHistoryFileName});
            }
        }
    }

    /* JADX WARNING: Missing block: B:11:0x0015, code:
            return;
     */
    public void setActivitySorter(androidx.appcompat.widget.ActivityChooserModel.ActivitySorter r3) {
        /*
        r2 = this;
        r0 = r2.mInstanceLock;
        monitor-enter(r0);
        r1 = r2.mActivitySorter;	 Catch:{ all -> 0x0016 }
        if (r1 != r3) goto L_0x0009;
    L_0x0007:
        monitor-exit(r0);	 Catch:{ all -> 0x0016 }
        return;
    L_0x0009:
        r2.mActivitySorter = r3;	 Catch:{ all -> 0x0016 }
        r3 = r2.sortActivitiesIfNeeded();	 Catch:{ all -> 0x0016 }
        if (r3 == 0) goto L_0x0014;
    L_0x0011:
        r2.notifyChanged();	 Catch:{ all -> 0x0016 }
    L_0x0014:
        monitor-exit(r0);	 Catch:{ all -> 0x0016 }
        return;
    L_0x0016:
        r3 = move-exception;
        monitor-exit(r0);	 Catch:{ all -> 0x0016 }
        throw r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.ActivityChooserModel.setActivitySorter(androidx.appcompat.widget.ActivityChooserModel$ActivitySorter):void");
    }

    /* JADX WARNING: Missing block: B:11:0x0018, code:
            return;
     */
    public void setHistoryMaxSize(int r3) {
        /*
        r2 = this;
        r0 = r2.mInstanceLock;
        monitor-enter(r0);
        r1 = r2.mHistoryMaxSize;	 Catch:{ all -> 0x0019 }
        if (r1 != r3) goto L_0x0009;
    L_0x0007:
        monitor-exit(r0);	 Catch:{ all -> 0x0019 }
        return;
    L_0x0009:
        r2.mHistoryMaxSize = r3;	 Catch:{ all -> 0x0019 }
        r2.pruneExcessiveHistoricalRecordsIfNeeded();	 Catch:{ all -> 0x0019 }
        r3 = r2.sortActivitiesIfNeeded();	 Catch:{ all -> 0x0019 }
        if (r3 == 0) goto L_0x0017;
    L_0x0014:
        r2.notifyChanged();	 Catch:{ all -> 0x0019 }
    L_0x0017:
        monitor-exit(r0);	 Catch:{ all -> 0x0019 }
        return;
    L_0x0019:
        r3 = move-exception;
        monitor-exit(r0);	 Catch:{ all -> 0x0019 }
        throw r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.ActivityChooserModel.setHistoryMaxSize(int):void");
    }

    public int getHistoryMaxSize() {
        int i;
        synchronized (this.mInstanceLock) {
            i = this.mHistoryMaxSize;
        }
        return i;
    }

    public int getHistorySize() {
        int size;
        synchronized (this.mInstanceLock) {
            ensureConsistentState();
            size = this.mHistoricalRecords.size();
        }
        return size;
    }

    private void ensureConsistentState() {
        int loadActivitiesIfNeeded = loadActivitiesIfNeeded() | readHistoricalDataIfNeeded();
        pruneExcessiveHistoricalRecordsIfNeeded();
        if (loadActivitiesIfNeeded != 0) {
            sortActivitiesIfNeeded();
            notifyChanged();
        }
    }

    private boolean sortActivitiesIfNeeded() {
        if (this.mActivitySorter == null || this.mIntent == null || this.mActivities.isEmpty() || this.mHistoricalRecords.isEmpty()) {
            return false;
        }
        this.mActivitySorter.sort(this.mIntent, this.mActivities, Collections.unmodifiableList(this.mHistoricalRecords));
        return true;
    }

    private boolean loadActivitiesIfNeeded() {
        int i = 0;
        if (!this.mReloadActivities || this.mIntent == null) {
            return false;
        }
        this.mReloadActivities = false;
        this.mActivities.clear();
        List queryIntentActivities = this.mContext.getPackageManager().queryIntentActivities(this.mIntent, 0);
        int size = queryIntentActivities.size();
        while (i < size) {
            this.mActivities.add(new ActivityResolveInfo((ResolveInfo) queryIntentActivities.get(i)));
            i++;
        }
        return true;
    }

    private boolean readHistoricalDataIfNeeded() {
        if (!this.mCanReadHistoricalData || !this.mHistoricalRecordsChanged || TextUtils.isEmpty(this.mHistoryFileName)) {
            return false;
        }
        this.mCanReadHistoricalData = false;
        this.mReadShareHistoryCalled = true;
        readHistoricalDataImpl();
        return true;
    }

    private boolean addHistoricalRecord(HistoricalRecord historicalRecord) {
        boolean add = this.mHistoricalRecords.add(historicalRecord);
        if (add) {
            this.mHistoricalRecordsChanged = true;
            pruneExcessiveHistoricalRecordsIfNeeded();
            persistHistoricalDataIfNeeded();
            sortActivitiesIfNeeded();
            notifyChanged();
        }
        return add;
    }

    private void pruneExcessiveHistoricalRecordsIfNeeded() {
        int size = this.mHistoricalRecords.size() - this.mHistoryMaxSize;
        if (size > 0) {
            this.mHistoricalRecordsChanged = true;
            for (int i = 0; i < size; i++) {
                HistoricalRecord historicalRecord = (HistoricalRecord) this.mHistoricalRecords.remove(0);
            }
        }
    }

    /* JADX WARNING: Missing block: B:15:0x0036, code:
            if (r1 == null) goto L_0x00bb;
     */
    /* JADX WARNING: Missing block: B:35:0x009d, code:
            if (r1 == null) goto L_0x00bb;
     */
    /* JADX WARNING: Missing block: B:38:0x00b7, code:
            if (r1 == null) goto L_0x00bb;
     */
    private void readHistoricalDataImpl() {
        /*
        r10 = this;
        r0 = "Error reading historical recrod file: ";
        r1 = r10.mContext;	 Catch:{ FileNotFoundException -> 0x00c2 }
        r2 = r10.mHistoryFileName;	 Catch:{ FileNotFoundException -> 0x00c2 }
        r1 = r1.openFileInput(r2);	 Catch:{ FileNotFoundException -> 0x00c2 }
        r2 = android.util.Xml.newPullParser();	 Catch:{ XmlPullParserException -> 0x00a0, IOException -> 0x0086 }
        r3 = "UTF-8";
        r2.setInput(r1, r3);	 Catch:{ XmlPullParserException -> 0x00a0, IOException -> 0x0086 }
        r3 = 0;
    L_0x0014:
        r4 = 1;
        if (r3 == r4) goto L_0x001f;
    L_0x0017:
        r5 = 2;
        if (r3 == r5) goto L_0x001f;
    L_0x001a:
        r3 = r2.next();	 Catch:{ XmlPullParserException -> 0x00a0, IOException -> 0x0086 }
        goto L_0x0014;
    L_0x001f:
        r3 = "historical-records";
        r5 = r2.getName();	 Catch:{ XmlPullParserException -> 0x00a0, IOException -> 0x0086 }
        r3 = r3.equals(r5);	 Catch:{ XmlPullParserException -> 0x00a0, IOException -> 0x0086 }
        if (r3 == 0) goto L_0x007c;
    L_0x002b:
        r3 = r10.mHistoricalRecords;	 Catch:{ XmlPullParserException -> 0x00a0, IOException -> 0x0086 }
        r3.clear();	 Catch:{ XmlPullParserException -> 0x00a0, IOException -> 0x0086 }
    L_0x0030:
        r5 = r2.next();	 Catch:{ XmlPullParserException -> 0x00a0, IOException -> 0x0086 }
        if (r5 != r4) goto L_0x003d;
    L_0x0036:
        if (r1 == 0) goto L_0x00bb;
    L_0x0038:
        r1.close();	 Catch:{ IOException -> 0x00bb }
        goto L_0x00bb;
    L_0x003d:
        r6 = 3;
        if (r5 == r6) goto L_0x0030;
    L_0x0040:
        r6 = 4;
        if (r5 != r6) goto L_0x0044;
    L_0x0043:
        goto L_0x0030;
    L_0x0044:
        r5 = r2.getName();	 Catch:{ XmlPullParserException -> 0x00a0, IOException -> 0x0086 }
        r6 = "historical-record";
        r5 = r6.equals(r5);	 Catch:{ XmlPullParserException -> 0x00a0, IOException -> 0x0086 }
        if (r5 == 0) goto L_0x0074;
    L_0x0050:
        r5 = "activity";
        r6 = 0;
        r5 = r2.getAttributeValue(r6, r5);	 Catch:{ XmlPullParserException -> 0x00a0, IOException -> 0x0086 }
        r7 = "time";
        r7 = r2.getAttributeValue(r6, r7);	 Catch:{ XmlPullParserException -> 0x00a0, IOException -> 0x0086 }
        r7 = java.lang.Long.parseLong(r7);	 Catch:{ XmlPullParserException -> 0x00a0, IOException -> 0x0086 }
        r9 = "weight";
        r6 = r2.getAttributeValue(r6, r9);	 Catch:{ XmlPullParserException -> 0x00a0, IOException -> 0x0086 }
        r6 = java.lang.Float.parseFloat(r6);	 Catch:{ XmlPullParserException -> 0x00a0, IOException -> 0x0086 }
        r9 = new androidx.appcompat.widget.ActivityChooserModel$HistoricalRecord;	 Catch:{ XmlPullParserException -> 0x00a0, IOException -> 0x0086 }
        r9.<init>(r5, r7, r6);	 Catch:{ XmlPullParserException -> 0x00a0, IOException -> 0x0086 }
        r3.add(r9);	 Catch:{ XmlPullParserException -> 0x00a0, IOException -> 0x0086 }
        goto L_0x0030;
    L_0x0074:
        r2 = new org.xmlpull.v1.XmlPullParserException;	 Catch:{ XmlPullParserException -> 0x00a0, IOException -> 0x0086 }
        r3 = "Share records file not well-formed.";
        r2.<init>(r3);	 Catch:{ XmlPullParserException -> 0x00a0, IOException -> 0x0086 }
        throw r2;	 Catch:{ XmlPullParserException -> 0x00a0, IOException -> 0x0086 }
    L_0x007c:
        r2 = new org.xmlpull.v1.XmlPullParserException;	 Catch:{ XmlPullParserException -> 0x00a0, IOException -> 0x0086 }
        r3 = "Share records file does not start with historical-records tag.";
        r2.<init>(r3);	 Catch:{ XmlPullParserException -> 0x00a0, IOException -> 0x0086 }
        throw r2;	 Catch:{ XmlPullParserException -> 0x00a0, IOException -> 0x0086 }
    L_0x0084:
        r0 = move-exception;
        goto L_0x00bc;
    L_0x0086:
        r2 = move-exception;
        r3 = LOG_TAG;	 Catch:{ all -> 0x0084 }
        r4 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0084 }
        r4.<init>();	 Catch:{ all -> 0x0084 }
        r4.append(r0);	 Catch:{ all -> 0x0084 }
        r0 = r10.mHistoryFileName;	 Catch:{ all -> 0x0084 }
        r4.append(r0);	 Catch:{ all -> 0x0084 }
        r0 = r4.toString();	 Catch:{ all -> 0x0084 }
        android.util.Log.e(r3, r0, r2);	 Catch:{ all -> 0x0084 }
        if (r1 == 0) goto L_0x00bb;
    L_0x009f:
        goto L_0x0038;
    L_0x00a0:
        r2 = move-exception;
        r3 = LOG_TAG;	 Catch:{ all -> 0x0084 }
        r4 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0084 }
        r4.<init>();	 Catch:{ all -> 0x0084 }
        r4.append(r0);	 Catch:{ all -> 0x0084 }
        r0 = r10.mHistoryFileName;	 Catch:{ all -> 0x0084 }
        r4.append(r0);	 Catch:{ all -> 0x0084 }
        r0 = r4.toString();	 Catch:{ all -> 0x0084 }
        android.util.Log.e(r3, r0, r2);	 Catch:{ all -> 0x0084 }
        if (r1 == 0) goto L_0x00bb;
    L_0x00b9:
        goto L_0x0038;
    L_0x00bb:
        return;
    L_0x00bc:
        if (r1 == 0) goto L_0x00c1;
    L_0x00be:
        r1.close();	 Catch:{ IOException -> 0x00c1 }
    L_0x00c1:
        throw r0;
    L_0x00c2:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.ActivityChooserModel.readHistoricalDataImpl():void");
    }
}
