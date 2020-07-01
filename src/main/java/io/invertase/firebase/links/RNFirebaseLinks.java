package io.invertase.firebase.links;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import com.google.firebase.appinvite.FirebaseAppInvite;
import com.google.firebase.dynamiclinks.DynamicLink.AndroidParameters;
import com.google.firebase.dynamiclinks.DynamicLink.Builder;
import com.google.firebase.dynamiclinks.DynamicLink.GoogleAnalyticsParameters;
import com.google.firebase.dynamiclinks.DynamicLink.IosParameters;
import com.google.firebase.dynamiclinks.DynamicLink.ItunesConnectAnalyticsParameters;
import com.google.firebase.dynamiclinks.DynamicLink.NavigationInfoParameters;
import com.google.firebase.dynamiclinks.DynamicLink.SocialMetaTagParameters;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
import com.google.firebase.dynamiclinks.ShortDynamicLink;
import io.invertase.firebase.Utils;
import javax.annotation.Nonnull;

public class RNFirebaseLinks extends ReactContextBaseJavaModule implements ActivityEventListener, LifecycleEventListener {
    private static final String TAG = "io.invertase.firebase.links.RNFirebaseLinks";
    private String mInitialLink = null;
    private boolean mInitialLinkInitialized = false;

    public String getName() {
        return "RNFirebaseLinks";
    }

    public void onActivityResult(Activity activity, int i, int i2, Intent intent) {
    }

    public void onHostPause() {
    }

    public void onHostResume() {
    }

    public RNFirebaseLinks(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
        access$700().addActivityEventListener(this);
        access$700().addLifecycleEventListener(this);
    }

    @ReactMethod
    public void createDynamicLink(ReadableMap readableMap, Promise promise) {
        String str;
        StringBuilder stringBuilder;
        try {
            String uri = getDynamicLinkBuilder(readableMap).buildDynamicLink().getUri().toString();
            str = TAG;
            stringBuilder = new StringBuilder();
            stringBuilder.append("created dynamic link: ");
            stringBuilder.append(uri);
            Log.d(str, stringBuilder.toString());
            promise.resolve(uri);
        } catch (Throwable e) {
            str = TAG;
            stringBuilder = new StringBuilder();
            stringBuilder.append("create dynamic link failure ");
            stringBuilder.append(e.getMessage());
            Log.e(str, stringBuilder.toString());
            promise.reject("links/failure", e.getMessage(), e);
        }
    }

    @ReactMethod
    public void createShortDynamicLink(ReadableMap readableMap, String str, final Promise promise) {
        try {
            Task buildShortDynamicLink;
            Builder dynamicLinkBuilder = getDynamicLinkBuilder(readableMap);
            if ("SHORT".equals(str)) {
                buildShortDynamicLink = dynamicLinkBuilder.buildShortDynamicLink(2);
            } else if ("UNGUESSABLE".equals(str)) {
                buildShortDynamicLink = dynamicLinkBuilder.buildShortDynamicLink(1);
            } else {
                buildShortDynamicLink = dynamicLinkBuilder.buildShortDynamicLink();
            }
            buildShortDynamicLink.addOnCompleteListener(new OnCompleteListener<ShortDynamicLink>() {
                public void onComplete(@Nonnull Task<ShortDynamicLink> task) {
                    String access$000;
                    StringBuilder stringBuilder;
                    if (task.isSuccessful()) {
                        String uri = ((ShortDynamicLink) task.getResult()).getShortLink().toString();
                        access$000 = RNFirebaseLinks.TAG;
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("created short dynamic link: ");
                        stringBuilder.append(uri);
                        Log.d(access$000, stringBuilder.toString());
                        promise.resolve(uri);
                        return;
                    }
                    access$000 = RNFirebaseLinks.TAG;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("create short dynamic link failure ");
                    stringBuilder.append(task.getException().getMessage());
                    Log.e(access$000, stringBuilder.toString());
                    promise.reject("links/failure", task.getException().getMessage(), task.getException());
                }
            });
        } catch (Throwable e) {
            str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("create short dynamic link failure ");
            stringBuilder.append(e.getMessage());
            Log.e(str, stringBuilder.toString());
            promise.reject("links/failure", e.getMessage(), e);
        }
    }

    @ReactMethod
    public void getInitialLink(final Promise promise) {
        if (this.mInitialLinkInitialized) {
            promise.resolve(this.mInitialLink);
        } else if (access$700() != null) {
            FirebaseDynamicLinks.getInstance().getDynamicLink(access$700().getIntent()).addOnSuccessListener(new OnSuccessListener<PendingDynamicLinkData>() {
                public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                    if (!(pendingDynamicLinkData == null || RNFirebaseLinks.this.isInvitation(pendingDynamicLinkData))) {
                        RNFirebaseLinks.this.mInitialLink = pendingDynamicLinkData.getLink().toString();
                    }
                    String access$000 = RNFirebaseLinks.TAG;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("getInitialLink: link is: ");
                    stringBuilder.append(RNFirebaseLinks.this.mInitialLink);
                    Log.d(access$000, stringBuilder.toString());
                    RNFirebaseLinks.this.mInitialLinkInitialized = true;
                    promise.resolve(RNFirebaseLinks.this.mInitialLink);
                }
            }).addOnFailureListener(new OnFailureListener() {
                public void onFailure(@Nonnull Exception exception) {
                    Log.e(RNFirebaseLinks.TAG, "getInitialLink: failed to resolve link", exception);
                    promise.reject("link/initial-link-error", exception.getMessage(), (Throwable) exception);
                }
            });
        } else {
            Log.d(TAG, "getInitialLink: activity is null");
            promise.resolve(null);
        }
    }

    public void onNewIntent(Intent intent) {
        FirebaseDynamicLinks.getInstance().getDynamicLink(intent).addOnSuccessListener(new OnSuccessListener<PendingDynamicLinkData>() {
            public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                if (pendingDynamicLinkData != null && !RNFirebaseLinks.this.isInvitation(pendingDynamicLinkData)) {
                    Utils.sendEvent(RNFirebaseLinks.this.access$700(), "links_link_received", pendingDynamicLinkData.getLink().toString());
                }
            }
        });
    }

    public void onHostDestroy() {
        this.mInitialLink = null;
        this.mInitialLinkInitialized = false;
    }

    private boolean isInvitation(PendingDynamicLinkData pendingDynamicLinkData) {
        FirebaseAppInvite invitation = FirebaseAppInvite.getInvitation(pendingDynamicLinkData);
        return (invitation == null || invitation.getInvitationId() == null || invitation.getInvitationId().isEmpty()) ? false : true;
    }

    private Builder getDynamicLinkBuilder(ReadableMap readableMap) {
        Builder createDynamicLink = FirebaseDynamicLinks.getInstance().createDynamicLink();
        try {
            createDynamicLink.setLink(Uri.parse(readableMap.getString("link")));
            createDynamicLink.setDomainUriPrefix(readableMap.getString("domainURIPrefix"));
            setAnalyticsParameters(readableMap.getMap("analytics"), createDynamicLink);
            setAndroidParameters(readableMap.getMap("android"), createDynamicLink);
            setIosParameters(readableMap.getMap("ios"), createDynamicLink);
            setITunesParameters(readableMap.getMap("itunes"), createDynamicLink);
            setNavigationParameters(readableMap.getMap(NotificationCompat.CATEGORY_NAVIGATION), createDynamicLink);
            setSocialParameters(readableMap.getMap(NotificationCompat.CATEGORY_SOCIAL), createDynamicLink);
            return createDynamicLink;
        } catch (Exception e) {
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("error while building parameters ");
            stringBuilder.append(e.getMessage());
            Log.e(str, stringBuilder.toString());
            throw e;
        }
    }

    private void setAnalyticsParameters(ReadableMap readableMap, Builder builder) {
        GoogleAnalyticsParameters.Builder builder2 = new GoogleAnalyticsParameters.Builder();
        String str = Param.CAMPAIGN;
        if (readableMap.hasKey(str)) {
            builder2.setCampaign(readableMap.getString(str));
        }
        str = "content";
        if (readableMap.hasKey(str)) {
            builder2.setContent(readableMap.getString(str));
        }
        str = "medium";
        if (readableMap.hasKey(str)) {
            builder2.setMedium(readableMap.getString(str));
        }
        str = Param.SOURCE;
        if (readableMap.hasKey(str)) {
            builder2.setSource(readableMap.getString(str));
        }
        str = Param.TERM;
        if (readableMap.hasKey(str)) {
            builder2.setTerm(readableMap.getString(str));
        }
        builder.setGoogleAnalyticsParameters(builder2.build());
    }

    private void setAndroidParameters(ReadableMap readableMap, Builder builder) {
        String str = "packageName";
        if (readableMap.hasKey(str)) {
            AndroidParameters.Builder builder2 = new AndroidParameters.Builder(readableMap.getString(str));
            str = "fallbackUrl";
            if (readableMap.hasKey(str)) {
                builder2.setFallbackUrl(Uri.parse(readableMap.getString(str)));
            }
            str = "minimumVersion";
            if (readableMap.hasKey(str)) {
                builder2.setMinimumVersion(Integer.parseInt(readableMap.getString(str)));
            }
            builder.setAndroidParameters(builder2.build());
        }
    }

    private void setIosParameters(ReadableMap readableMap, Builder builder) {
        String str = "bundleId";
        if (readableMap.hasKey(str)) {
            IosParameters.Builder builder2 = new IosParameters.Builder(readableMap.getString(str));
            str = "appStoreId";
            if (readableMap.hasKey(str)) {
                builder2.setAppStoreId(readableMap.getString(str));
            }
            str = "customScheme";
            if (readableMap.hasKey(str)) {
                builder2.setCustomScheme(readableMap.getString(str));
            }
            str = "fallbackUrl";
            if (readableMap.hasKey(str)) {
                builder2.setFallbackUrl(Uri.parse(readableMap.getString(str)));
            }
            str = "iPadBundleId";
            if (readableMap.hasKey(str)) {
                builder2.setIpadBundleId(readableMap.getString(str));
            }
            str = "iPadFallbackUrl";
            if (readableMap.hasKey(str)) {
                builder2.setIpadFallbackUrl(Uri.parse(readableMap.getString(str)));
            }
            str = "minimumVersion";
            if (readableMap.hasKey(str)) {
                builder2.setMinimumVersion(readableMap.getString(str));
            }
            builder.setIosParameters(builder2.build());
        }
    }

    private void setITunesParameters(ReadableMap readableMap, Builder builder) {
        ItunesConnectAnalyticsParameters.Builder builder2 = new ItunesConnectAnalyticsParameters.Builder();
        String str = "affiliateToken";
        if (readableMap.hasKey(str)) {
            builder2.setAffiliateToken(readableMap.getString(str));
        }
        str = "campaignToken";
        if (readableMap.hasKey(str)) {
            builder2.setCampaignToken(readableMap.getString(str));
        }
        str = "providerToken";
        if (readableMap.hasKey(str)) {
            builder2.setProviderToken(readableMap.getString(str));
        }
        builder.setItunesConnectAnalyticsParameters(builder2.build());
    }

    private void setNavigationParameters(ReadableMap readableMap, Builder builder) {
        NavigationInfoParameters.Builder builder2 = new NavigationInfoParameters.Builder();
        String str = "forcedRedirectEnabled";
        if (readableMap.hasKey(str)) {
            builder2.setForcedRedirectEnabled(readableMap.getBoolean(str));
        }
        builder.setNavigationInfoParameters(builder2.build());
    }

    private void setSocialParameters(ReadableMap readableMap, Builder builder) {
        SocialMetaTagParameters.Builder builder2 = new SocialMetaTagParameters.Builder();
        String str = "descriptionText";
        if (readableMap.hasKey(str)) {
            builder2.setDescription(readableMap.getString(str));
        }
        str = "imageUrl";
        if (readableMap.hasKey(str)) {
            builder2.setImageUrl(Uri.parse(readableMap.getString(str)));
        }
        str = "title";
        if (readableMap.hasKey(str)) {
            builder2.setTitle(readableMap.getString(str));
        }
        builder.setSocialMetaTagParameters(builder2.build());
    }
}
