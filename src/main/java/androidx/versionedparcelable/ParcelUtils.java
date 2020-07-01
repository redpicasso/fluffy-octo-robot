package androidx.versionedparcelable;

import android.os.Bundle;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.RestrictTo.Scope;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ParcelUtils {
    private static final String INNER_BUNDLE_KEY = "a";

    private ParcelUtils() {
    }

    @RestrictTo({Scope.LIBRARY_GROUP_PREFIX})
    public static Parcelable toParcelable(VersionedParcelable versionedParcelable) {
        return new ParcelImpl(versionedParcelable);
    }

    @RestrictTo({Scope.LIBRARY_GROUP_PREFIX})
    public static <T extends VersionedParcelable> T fromParcelable(Parcelable parcelable) {
        if (parcelable instanceof ParcelImpl) {
            return ((ParcelImpl) parcelable).getVersionedParcel();
        }
        throw new IllegalArgumentException("Invalid parcel");
    }

    @RestrictTo({Scope.LIBRARY_GROUP_PREFIX})
    public static void toOutputStream(VersionedParcelable versionedParcelable, OutputStream outputStream) {
        VersionedParcelStream versionedParcelStream = new VersionedParcelStream(null, outputStream);
        versionedParcelStream.writeVersionedParcelable(versionedParcelable);
        versionedParcelStream.closeField();
    }

    @RestrictTo({Scope.LIBRARY_GROUP_PREFIX})
    public static <T extends VersionedParcelable> T fromInputStream(InputStream inputStream) {
        return new VersionedParcelStream(inputStream, null).readVersionedParcelable();
    }

    public static void putVersionedParcelable(@NonNull Bundle bundle, @NonNull String str, @Nullable VersionedParcelable versionedParcelable) {
        if (versionedParcelable != null) {
            Parcelable bundle2 = new Bundle();
            bundle2.putParcelable(INNER_BUNDLE_KEY, toParcelable(versionedParcelable));
            bundle.putParcelable(str, bundle2);
        }
    }

    @Nullable
    public static <T extends VersionedParcelable> T getVersionedParcelable(@NonNull Bundle bundle, @NonNull String str) {
        try {
            bundle = (Bundle) bundle.getParcelable(str);
            if (bundle == null) {
                return null;
            }
            bundle.setClassLoader(ParcelUtils.class.getClassLoader());
            return fromParcelable(bundle.getParcelable(INNER_BUNDLE_KEY));
        } catch (RuntimeException unused) {
            return null;
        }
    }

    public static void putVersionedParcelableList(@NonNull Bundle bundle, @NonNull String str, @NonNull List<? extends VersionedParcelable> list) {
        Parcelable bundle2 = new Bundle();
        ArrayList arrayList = new ArrayList();
        for (VersionedParcelable toParcelable : list) {
            arrayList.add(toParcelable(toParcelable));
        }
        bundle2.putParcelableArrayList(INNER_BUNDLE_KEY, arrayList);
        bundle.putParcelable(str, bundle2);
    }

    @Nullable
    public static <T extends VersionedParcelable> List<T> getVersionedParcelableList(Bundle bundle, String str) {
        List<T> arrayList = new ArrayList();
        try {
            bundle = (Bundle) bundle.getParcelable(str);
            bundle.setClassLoader(ParcelUtils.class.getClassLoader());
            Iterator it = bundle.getParcelableArrayList(INNER_BUNDLE_KEY).iterator();
            while (it.hasNext()) {
                arrayList.add(fromParcelable((Parcelable) it.next()));
            }
            return arrayList;
        } catch (RuntimeException unused) {
            return null;
        }
    }
}
