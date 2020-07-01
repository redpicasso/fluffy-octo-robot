package com.rnfingerprint;

import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.hardware.fingerprint.FingerprintManager.CryptoObject;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.facebook.react.bridge.ReadableMap;
import com.rnfingerprint.FingerprintHandler.Callback;

public class FingerprintDialog extends DialogFragment implements Callback {
    private String authReason;
    private String cancelText;
    private DialogResultListener dialogCallback;
    private String dialogTitle;
    private String errorText;
    private int imageColor = 0;
    private int imageErrorColor = 0;
    private boolean isAuthInProgress;
    private CryptoObject mCryptoObject;
    private TextView mFingerprintError;
    private FingerprintHandler mFingerprintHandler;
    private ImageView mFingerprintImage;
    private TextView mFingerprintSensorDescription;
    private String sensorDescription;
    private String sensorErrorDescription;

    public interface DialogResultListener {
        void onAuthenticated();

        void onCancelled();

        void onError(String str, int i);
    }

    public FingerprintDialog() {
        String str = "";
        this.dialogTitle = str;
        this.cancelText = str;
        this.sensorDescription = str;
        this.sensorErrorDescription = str;
        this.errorText = str;
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        this.mFingerprintHandler = new FingerprintHandler(context, this);
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setStyle(0, 16974393);
        setCancelable(false);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fingerprint_dialog, viewGroup, false);
        ((TextView) inflate.findViewById(R.id.fingerprint_description)).setText(this.authReason);
        this.mFingerprintImage = (ImageView) inflate.findViewById(R.id.fingerprint_icon);
        int i = this.imageColor;
        if (i != 0) {
            this.mFingerprintImage.setColorFilter(i);
        }
        this.mFingerprintSensorDescription = (TextView) inflate.findViewById(R.id.fingerprint_sensor_description);
        this.mFingerprintSensorDescription.setText(this.sensorDescription);
        this.mFingerprintError = (TextView) inflate.findViewById(R.id.fingerprint_error);
        this.mFingerprintError.setText(this.errorText);
        Button button = (Button) inflate.findViewById(R.id.cancel_button);
        button.setText(this.cancelText);
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                FingerprintDialog.this.onCancelled();
            }
        });
        getDialog().setTitle(this.dialogTitle);
        getDialog().setOnKeyListener(new OnKeyListener() {
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                if (i != 4 || FingerprintDialog.this.mFingerprintHandler == null) {
                    return false;
                }
                FingerprintDialog.this.onCancelled();
                return true;
            }
        });
        return inflate;
    }

    public void onResume() {
        super.onResume();
        if (!this.isAuthInProgress) {
            this.isAuthInProgress = true;
            this.mFingerprintHandler.startAuth(this.mCryptoObject);
        }
    }

    public void onPause() {
        super.onPause();
        if (this.isAuthInProgress) {
            this.mFingerprintHandler.endAuth();
            this.isAuthInProgress = false;
        }
    }

    public void setCryptoObject(CryptoObject cryptoObject) {
        this.mCryptoObject = cryptoObject;
    }

    public void setDialogCallback(DialogResultListener dialogResultListener) {
        this.dialogCallback = dialogResultListener;
    }

    public void setReasonForAuthentication(String str) {
        this.authReason = str;
    }

    public void setAuthConfig(ReadableMap readableMap) {
        if (readableMap != null) {
            String str = "title";
            if (readableMap.hasKey(str)) {
                this.dialogTitle = readableMap.getString(str);
            }
            str = "cancelText";
            if (readableMap.hasKey(str)) {
                this.cancelText = readableMap.getString(str);
            }
            str = "sensorDescription";
            if (readableMap.hasKey(str)) {
                this.sensorDescription = readableMap.getString(str);
            }
            str = "sensorErrorDescription";
            if (readableMap.hasKey(str)) {
                this.sensorErrorDescription = readableMap.getString(str);
            }
            str = "imageColor";
            if (readableMap.hasKey(str)) {
                this.imageColor = readableMap.getInt(str);
            }
            str = "imageErrorColor";
            if (readableMap.hasKey(str)) {
                this.imageErrorColor = readableMap.getInt(str);
            }
        }
    }

    public void onAuthenticated() {
        this.isAuthInProgress = false;
        this.dialogCallback.onAuthenticated();
        dismiss();
    }

    public void onError(String str, int i) {
        this.mFingerprintError.setText(str);
        this.mFingerprintImage.setColorFilter(this.imageErrorColor);
        this.mFingerprintSensorDescription.setText(this.sensorErrorDescription);
    }

    public void onCancelled() {
        this.isAuthInProgress = false;
        this.mFingerprintHandler.endAuth();
        this.dialogCallback.onCancelled();
        dismiss();
    }
}
