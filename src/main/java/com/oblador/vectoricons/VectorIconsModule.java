package com.oblador.vectoricons;

import android.graphics.Typeface;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import java.util.HashMap;
import java.util.Map;

public class VectorIconsModule extends ReactContextBaseJavaModule {
    public static final String REACT_CLASS = "RNVectorIconsModule";
    private static final Map<String, Typeface> sTypefaceCache = new HashMap();

    public String getName() {
        return REACT_CLASS;
    }

    public VectorIconsModule(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
    }

    /* JADX WARNING: Removed duplicated region for block: B:43:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x014c A:{SYNTHETIC, Splitter: B:25:0x014c} */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x0169 A:{SYNTHETIC, Splitter: B:36:0x0169} */
    @com.facebook.react.bridge.ReactMethod
    public void getImageForFont(java.lang.String r9, java.lang.String r10, java.lang.Integer r11, java.lang.Integer r12, com.facebook.react.bridge.Callback r13) {
        /*
        r8 = this;
        r0 = r8.access$100();
        r1 = r0.getCacheDir();
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r1 = r1.getAbsolutePath();
        r2.append(r1);
        r1 = "/";
        r2.append(r1);
        r1 = r2.toString();
        r2 = r0.getResources();
        r2 = r2.getDisplayMetrics();
        r2 = r2.density;
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r4 = "@";
        r3.append(r4);
        r4 = (int) r2;
        r5 = (float) r4;
        r5 = (r2 > r5 ? 1 : (r2 == r5 ? 0 : -1));
        if (r5 != 0) goto L_0x003c;
    L_0x0037:
        r4 = java.lang.Integer.toString(r4);
        goto L_0x0040;
    L_0x003c:
        r4 = java.lang.Float.toString(r2);
    L_0x0040:
        r3.append(r4);
        r4 = "x";
        r3.append(r4);
        r3 = r3.toString();
        r4 = r11.intValue();
        r4 = (float) r4;
        r4 = r4 * r2;
        r2 = java.lang.Math.round(r4);
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r4.append(r9);
        r5 = ":";
        r4.append(r5);
        r4.append(r10);
        r4.append(r5);
        r4.append(r12);
        r4 = r4.toString();
        r4 = r4.hashCode();
        r5 = 32;
        r4 = java.lang.Integer.toString(r4, r5);
        r5 = new java.lang.StringBuilder;
        r5.<init>();
        r5.append(r1);
        r5.append(r4);
        r1 = "_";
        r5.append(r1);
        r11 = r11.intValue();
        r11 = java.lang.Integer.toString(r11);
        r5.append(r11);
        r5.append(r3);
        r11 = ".png";
        r5.append(r11);
        r11 = r5.toString();
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r3 = "file://";
        r1.append(r3);
        r1.append(r11);
        r1 = r1.toString();
        r3 = new java.io.File;
        r3.<init>(r11);
        r11 = r3.exists();
        r4 = 2;
        r5 = 0;
        r6 = 1;
        r7 = 0;
        if (r11 == 0) goto L_0x00cd;
    L_0x00c2:
        r9 = new java.lang.Object[r4];
        r9[r7] = r5;
        r9[r6] = r1;
        r13.invoke(r9);
        goto L_0x0166;
    L_0x00cd:
        r11 = com.facebook.react.views.text.ReactFontManager.getInstance();
        r0 = r0.getAssets();
        r9 = r11.getTypeface(r9, r7, r0);
        r11 = new android.graphics.Paint;
        r11.<init>();
        r11.setTypeface(r9);
        r9 = r12.intValue();
        r11.setColor(r9);
        r9 = (float) r2;
        r11.setTextSize(r9);
        r11.setAntiAlias(r6);
        r9 = new android.graphics.Rect;
        r9.<init>();
        r12 = r10.length();
        r11.getTextBounds(r10, r7, r12, r9);
        r9 = r11.getFontMetrics();
        r9 = r9.bottom;
        r9 = (int) r9;
        r9 = r2 - r9;
        r12 = android.graphics.Bitmap.Config.ARGB_8888;
        r12 = android.graphics.Bitmap.createBitmap(r2, r2, r12);
        r0 = new android.graphics.Canvas;
        r0.<init>(r12);
        r2 = (float) r7;
        r9 = (float) r9;
        r0.drawText(r10, r2, r9, r11);
        r9 = new java.io.FileOutputStream;	 Catch:{ FileNotFoundException -> 0x0150, IOException -> 0x013e }
        r9.<init>(r3);	 Catch:{ FileNotFoundException -> 0x0150, IOException -> 0x013e }
        r10 = android.graphics.Bitmap.CompressFormat.PNG;	 Catch:{ FileNotFoundException -> 0x0138, IOException -> 0x0134, all -> 0x0130 }
        r11 = 100;
        r12.compress(r10, r11, r9);	 Catch:{ FileNotFoundException -> 0x0138, IOException -> 0x0134, all -> 0x0130 }
        r9.flush();	 Catch:{ FileNotFoundException -> 0x0138, IOException -> 0x0134, all -> 0x0130 }
        r9.close();	 Catch:{ FileNotFoundException -> 0x0138, IOException -> 0x0134, all -> 0x0130 }
        r9 = new java.lang.Object[r4];	 Catch:{ FileNotFoundException -> 0x0150, IOException -> 0x013e }
        r9[r7] = r5;	 Catch:{ FileNotFoundException -> 0x0150, IOException -> 0x013e }
        r9[r6] = r1;	 Catch:{ FileNotFoundException -> 0x0150, IOException -> 0x013e }
        r13.invoke(r9);	 Catch:{ FileNotFoundException -> 0x0150, IOException -> 0x013e }
        goto L_0x0166;
    L_0x0130:
        r10 = move-exception;
        r5 = r9;
        r9 = r10;
        goto L_0x0167;
    L_0x0134:
        r10 = move-exception;
        r5 = r9;
        r9 = r10;
        goto L_0x013f;
    L_0x0138:
        r10 = move-exception;
        r5 = r9;
        r9 = r10;
        goto L_0x0151;
    L_0x013c:
        r9 = move-exception;
        goto L_0x0167;
    L_0x013e:
        r9 = move-exception;
    L_0x013f:
        r10 = new java.lang.Object[r6];	 Catch:{ all -> 0x013c }
        r9 = r9.getMessage();	 Catch:{ all -> 0x013c }
        r10[r7] = r9;	 Catch:{ all -> 0x013c }
        r13.invoke(r10);	 Catch:{ all -> 0x013c }
        if (r5 == 0) goto L_0x0166;
    L_0x014c:
        r5.close();	 Catch:{ IOException -> 0x0162 }
        goto L_0x0166;
    L_0x0150:
        r9 = move-exception;
    L_0x0151:
        r10 = new java.lang.Object[r6];	 Catch:{ all -> 0x013c }
        r9 = r9.getMessage();	 Catch:{ all -> 0x013c }
        r10[r7] = r9;	 Catch:{ all -> 0x013c }
        r13.invoke(r10);	 Catch:{ all -> 0x013c }
        if (r5 == 0) goto L_0x0166;
    L_0x015e:
        r5.close();	 Catch:{ IOException -> 0x0162 }
        goto L_0x0166;
    L_0x0162:
        r9 = move-exception;
        r9.printStackTrace();
    L_0x0166:
        return;
    L_0x0167:
        if (r5 == 0) goto L_0x0171;
    L_0x0169:
        r5.close();	 Catch:{ IOException -> 0x016d }
        goto L_0x0171;
    L_0x016d:
        r10 = move-exception;
        r10.printStackTrace();
    L_0x0171:
        throw r9;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.oblador.vectoricons.VectorIconsModule.getImageForFont(java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer, com.facebook.react.bridge.Callback):void");
    }
}
