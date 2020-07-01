package com.rnfingerprint;

import android.annotation.TargetApi;
import android.security.keystore.KeyGenParameterSpec.Builder;
import java.security.KeyStore;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

@TargetApi(23)
public class FingerprintCipher {
    private static final String CIPHER_ALGO = "AES/CBC/PKCS7Padding";
    private static final String KEY_NAME = "example_key";
    private Cipher cipher;

    public Cipher getCipher() {
        Cipher cipher = this.cipher;
        if (cipher != null) {
            return cipher;
        }
        try {
            KeyStore generateKey = generateKey();
            this.cipher = Cipher.getInstance(CIPHER_ALGO);
            generateKey.load(null);
            this.cipher.init(1, generateKey.getKey(KEY_NAME, null));
        } catch (Exception unused) {
            return this.cipher;
        }
    }

    private KeyStore generateKey() throws Exception {
        String str = "AndroidKeyStore";
        KeyStore instance = KeyStore.getInstance(str);
        KeyGenerator instance2 = KeyGenerator.getInstance("AES", str);
        instance.load(null);
        instance2.init(new Builder(KEY_NAME, 3).setBlockModes(new String[]{"CBC"}).setUserAuthenticationRequired(true).setEncryptionPaddings(new String[]{"PKCS7Padding"}).build());
        instance2.generateKey();
        return instance;
    }
}
