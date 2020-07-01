package com.facebook.react;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import cl.json.RNSharePackage;
import co.apptailor.googlesignin.RNGoogleSigninPackage;
import com.AlexanderZaytsev.RNI18n.RNI18nPackage;
import com.BV.LinearGradient.LinearGradientPackage;
import com.RNFetchBlob.RNFetchBlobPackage;
import com.actionsheet.ActionSheetPackage;
import com.airbnb.android.react.lottie.LottiePackage;
import com.airbnb.android.react.maps.MapsPackage;
import com.brentvatne.react.ReactVideoPackage;
import com.como.RNTShadowView.ShadowViewPackage;
import com.dylanvann.fastimage.FastImageViewPackage;
import com.facebook.react.shell.MainReactPackage;
import com.github.wumke.RNExitApp.RNExitAppPackage;
import com.horcrux.svg.SvgPackage;
import com.inprogress.reactnativeyoutube.ReactNativeYouTube;
import com.learnium.RNDeviceInfo.RNDeviceInfo;
import com.oblador.vectoricons.VectorIconsPackage;
import com.reactcommunity.rndatetimepicker.RNDateTimePickerPackage;
import com.reactcommunity.rnlocalize.RNLocalizePackage;
import com.reactnative.ivpusic.imagepicker.PickerPackage;
import com.reactnativecommunity.asyncstorage.AsyncStoragePackage;
import com.reactnativecommunity.geolocation.GeolocationPackage;
import com.reactnativecommunity.netinfo.NetInfoPackage;
import com.rnfingerprint.FingerprintAuthPackage;
import com.rnim.rn.audio.ReactNativeAudioPackage;
import com.swmansion.gesturehandler.react.RNGestureHandlerPackage;
import com.swmansion.reanimated.ReanimatedPackage;
import com.swmansion.rnscreens.RNScreensPackage;
import com.vinzscam.reactnativefileviewer.RNFileViewerPackage;
import com.zmxv.RNSound.RNSoundPackage;
import fr.bamlab.rnimageresizer.ImageResizerPackage;
import io.github.elyx0.reactnativedocumentpicker.DocumentPickerPackage;
import io.invertase.firebase.RNFirebasePackage;
import java.util.ArrayList;
import java.util.Arrays;
import net.zubricky.AndroidKeyboardAdjust.AndroidKeyboardAdjustPackage;
import org.devio.rn.splashscreen.SplashScreenReactPackage;
import org.reactnative.camera.RNCameraPackage;
import org.wonday.orientation.OrientationPackage;

public class PackageList {
    private Application application;
    private ReactNativeHost reactNativeHost;

    public PackageList(ReactNativeHost reactNativeHost) {
        this.reactNativeHost = reactNativeHost;
    }

    public PackageList(Application application) {
        this.reactNativeHost = null;
        this.application = application;
    }

    private ReactNativeHost getReactNativeHost() {
        return this.reactNativeHost;
    }

    private Resources getResources() {
        return getApplication().getResources();
    }

    private Application getApplication() {
        ReactNativeHost reactNativeHost = this.reactNativeHost;
        if (reactNativeHost == null) {
            return this.application;
        }
        return reactNativeHost.getApplication();
    }

    private Context getApplicationContext() {
        return getApplication().getApplicationContext();
    }

    public ArrayList<ReactPackage> getPackages() {
        return new ArrayList(Arrays.asList(new ReactPackage[]{new MainReactPackage(), new AsyncStoragePackage(), new RNDateTimePickerPackage(), new GeolocationPackage(), new RNGoogleSigninPackage(), new NetInfoPackage(), new LottiePackage(), new ActionSheetPackage(), new AndroidKeyboardAdjustPackage(), new ReactNativeAudioPackage(), new RNCameraPackage(), new RNDeviceInfo(), new DocumentPickerPackage(), new RNExitAppPackage(), new FastImageViewPackage(), new RNFileViewerPackage(), new RNFirebasePackage(), new RNGestureHandlerPackage(), new RNI18nPackage(), new PickerPackage(), new ImageResizerPackage(), new LinearGradientPackage(), new RNLocalizePackage(), new MapsPackage(), new OrientationPackage(), new ReanimatedPackage(), new RNScreensPackage(), new RNSharePackage(), new ShadowViewPackage(), new RNSoundPackage(), new SplashScreenReactPackage(), new SvgPackage(), new FingerprintAuthPackage(), new VectorIconsPackage(), new ReactVideoPackage(), new ReactNativeYouTube(), new RNFetchBlobPackage()}));
    }
}
