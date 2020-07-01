package fpcs.immigration.gov.kh;

import android.app.Application;
import android.content.Context;
import com.facebook.react.PackageList;
import com.facebook.react.ReactApplication;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.soloader.SoLoader;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import io.invertase.firebase.auth.RNFirebaseAuthPackage;
import io.invertase.firebase.database.RNFirebaseDatabasePackage;
import io.invertase.firebase.firestore.RNFirebaseFirestorePackage;
import io.invertase.firebase.functions.RNFirebaseFunctionsPackage;
import io.invertase.firebase.instanceid.RNFirebaseInstanceIdPackage;
import io.invertase.firebase.messaging.RNFirebaseMessagingPackage;
import io.invertase.firebase.notifications.RNFirebaseNotificationsPackage;
import io.invertase.firebase.storage.RNFirebaseStoragePackage;
import java.util.List;

public class MainApplication extends Application implements ReactApplication {
    private final ReactNativeHost mReactNativeHost = new ReactNativeHost(this) {
        protected String getJSMainModuleName() {
            return Param.INDEX;
        }

        public boolean getUseDeveloperSupport() {
            return false;
        }

        protected List<ReactPackage> getPackages() {
            List<ReactPackage> packages = new PackageList((ReactNativeHost) this).getPackages();
            packages.add(new RNFirebaseFirestorePackage());
            packages.add(new RNFirebaseAuthPackage());
            packages.add(new RNFirebaseFunctionsPackage());
            packages.add(new RNFirebaseMessagingPackage());
            packages.add(new RNFirebaseDatabasePackage());
            packages.add(new RNFirebaseNotificationsPackage());
            packages.add(new RNFirebaseStoragePackage());
            packages.add(new RNFirebaseInstanceIdPackage());
            return packages;
        }
    };

    public ReactNativeHost getReactNativeHost() {
        return this.mReactNativeHost;
    }

    public void onCreate() {
        super.onCreate();
        SoLoader.init((Context) this, false);
    }
}
