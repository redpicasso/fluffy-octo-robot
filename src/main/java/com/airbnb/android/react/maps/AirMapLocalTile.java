package com.airbnb.android.react.maps;

import android.content.Context;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Tile;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.maps.model.TileProvider;

public class AirMapLocalTile extends AirMapFeature {
    private String pathTemplate;
    private TileOverlay tileOverlay;
    private TileOverlayOptions tileOverlayOptions;
    private AIRMapLocalTileProvider tileProvider;
    private float tileSize;
    private float zIndex;

    class AIRMapLocalTileProvider implements TileProvider {
        private static final int BUFFER_SIZE = 16384;
        private String pathTemplate;
        private int tileSize;

        public AIRMapLocalTileProvider(int i, String str) {
            this.tileSize = i;
            this.pathTemplate = str;
        }

        public Tile getTile(int i, int i2, int i3) {
            byte[] readTileImage = readTileImage(i, i2, i3);
            if (readTileImage == null) {
                return TileProvider.NO_TILE;
            }
            i3 = this.tileSize;
            return new Tile(i3, i3, readTileImage);
        }

        public void setPathTemplate(String str) {
            this.pathTemplate = str;
        }

        public void setTileSize(int i) {
            this.tileSize = i;
        }

        /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
            jadx.core.utils.exceptions.JadxRuntimeException: Exception block dominator not found, method:com.airbnb.android.react.maps.AirMapLocalTile.AIRMapLocalTileProvider.readTileImage(int, int, int):byte[], dom blocks: []
            	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:89)
            	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
            	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
            	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
            	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
            	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
            	at java.util.ArrayList.forEach(Unknown Source)
            	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
            	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$0(DepthTraversal.java:13)
            	at java.util.ArrayList.forEach(Unknown Source)
            	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:13)
            	at jadx.core.ProcessClass.process(ProcessClass.java:32)
            	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:51)
            	at java.lang.Iterable.forEach(Unknown Source)
            	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:51)
            	at jadx.core.ProcessClass.process(ProcessClass.java:37)
            	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:293)
            	at jadx.api.JavaClass.decompile(JavaClass.java:62)
            	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:201)
            */
        /* JADX WARNING: Removed duplicated region for block: B:44:0x0061 A:{SYNTHETIC, Splitter: B:44:0x0061} */
        /* JADX WARNING: Removed duplicated region for block: B:48:0x0068 A:{SYNTHETIC, Splitter: B:48:0x0068} */
        /* JADX WARNING: Removed duplicated region for block: B:32:0x004e A:{SYNTHETIC, Splitter: B:32:0x004e} */
        /* JADX WARNING: Removed duplicated region for block: B:36:0x0055 A:{SYNTHETIC, Splitter: B:36:0x0055} */
        /* JADX WARNING: Removed duplicated region for block: B:53:0x006f A:{SYNTHETIC, Splitter: B:53:0x006f} */
        /* JADX WARNING: Removed duplicated region for block: B:57:0x0076 A:{SYNTHETIC, Splitter: B:57:0x0076} */
        /* JADX WARNING: Removed duplicated region for block: B:44:0x0061 A:{SYNTHETIC, Splitter: B:44:0x0061} */
        /* JADX WARNING: Removed duplicated region for block: B:48:0x0068 A:{SYNTHETIC, Splitter: B:48:0x0068} */
        /* JADX WARNING: Removed duplicated region for block: B:32:0x004e A:{SYNTHETIC, Splitter: B:32:0x004e} */
        /* JADX WARNING: Removed duplicated region for block: B:36:0x0055 A:{SYNTHETIC, Splitter: B:36:0x0055} */
        /* JADX WARNING: Removed duplicated region for block: B:53:0x006f A:{SYNTHETIC, Splitter: B:53:0x006f} */
        /* JADX WARNING: Removed duplicated region for block: B:57:0x0076 A:{SYNTHETIC, Splitter: B:57:0x0076} */
        private byte[] readTileImage(int r7, int r8, int r9) {
            /*
            r6 = this;
            r0 = new java.io.File;
            r7 = r6.getTileFilename(r7, r8, r9);
            r0.<init>(r7);
            r7 = 0;
            r8 = new java.io.FileInputStream;	 Catch:{ IOException -> 0x0059, OutOfMemoryError -> 0x0046, all -> 0x0041 }
            r8.<init>(r0);	 Catch:{ IOException -> 0x0059, OutOfMemoryError -> 0x0046, all -> 0x0041 }
            r9 = new java.io.ByteArrayOutputStream;	 Catch:{ IOException -> 0x003e, OutOfMemoryError -> 0x003b, all -> 0x0036 }
            r9.<init>();	 Catch:{ IOException -> 0x003e, OutOfMemoryError -> 0x003b, all -> 0x0036 }
            r0 = 16384; // 0x4000 float:2.2959E-41 double:8.0948E-320;
            r1 = new byte[r0];	 Catch:{ IOException -> 0x0034, OutOfMemoryError -> 0x0032 }
        L_0x0018:
            r2 = 0;	 Catch:{ IOException -> 0x0034, OutOfMemoryError -> 0x0032 }
            r3 = r8.read(r1, r2, r0);	 Catch:{ IOException -> 0x0034, OutOfMemoryError -> 0x0032 }
            r4 = -1;	 Catch:{ IOException -> 0x0034, OutOfMemoryError -> 0x0032 }
            if (r3 == r4) goto L_0x0024;	 Catch:{ IOException -> 0x0034, OutOfMemoryError -> 0x0032 }
        L_0x0020:
            r9.write(r1, r2, r3);	 Catch:{ IOException -> 0x0034, OutOfMemoryError -> 0x0032 }
            goto L_0x0018;	 Catch:{ IOException -> 0x0034, OutOfMemoryError -> 0x0032 }
        L_0x0024:
            r9.flush();	 Catch:{ IOException -> 0x0034, OutOfMemoryError -> 0x0032 }
            r7 = r9.toByteArray();	 Catch:{ IOException -> 0x0034, OutOfMemoryError -> 0x0032 }
            r8.close();	 Catch:{ Exception -> 0x002e }
        L_0x002e:
            r9.close();	 Catch:{ Exception -> 0x0031 }
        L_0x0031:
            return r7;
        L_0x0032:
            r0 = move-exception;
            goto L_0x0049;
        L_0x0034:
            r0 = move-exception;
            goto L_0x005c;
        L_0x0036:
            r9 = move-exception;
            r5 = r9;
            r9 = r7;
            r7 = r5;
            goto L_0x006d;
        L_0x003b:
            r0 = move-exception;
            r9 = r7;
            goto L_0x0049;
        L_0x003e:
            r0 = move-exception;
            r9 = r7;
            goto L_0x005c;
        L_0x0041:
            r8 = move-exception;
            r9 = r7;
            r7 = r8;
            r8 = r9;
            goto L_0x006d;
        L_0x0046:
            r0 = move-exception;
            r8 = r7;
            r9 = r8;
        L_0x0049:
            r0.printStackTrace();	 Catch:{ all -> 0x006c }
            if (r8 == 0) goto L_0x0053;
        L_0x004e:
            r8.close();	 Catch:{ Exception -> 0x0052 }
            goto L_0x0053;
        L_0x0053:
            if (r9 == 0) goto L_0x0058;
        L_0x0055:
            r9.close();	 Catch:{ Exception -> 0x0058 }
        L_0x0058:
            return r7;
        L_0x0059:
            r0 = move-exception;
            r8 = r7;
            r9 = r8;
        L_0x005c:
            r0.printStackTrace();	 Catch:{ all -> 0x006c }
            if (r8 == 0) goto L_0x0066;
        L_0x0061:
            r8.close();	 Catch:{ Exception -> 0x0065 }
            goto L_0x0066;
        L_0x0066:
            if (r9 == 0) goto L_0x006b;
        L_0x0068:
            r9.close();	 Catch:{ Exception -> 0x006b }
        L_0x006b:
            return r7;
        L_0x006c:
            r7 = move-exception;
        L_0x006d:
            if (r8 == 0) goto L_0x0074;
        L_0x006f:
            r8.close();	 Catch:{ Exception -> 0x0073 }
            goto L_0x0074;
        L_0x0074:
            if (r9 == 0) goto L_0x0079;
        L_0x0076:
            r9.close();	 Catch:{ Exception -> 0x0079 }
        L_0x0079:
            throw r7;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.airbnb.android.react.maps.AirMapLocalTile.AIRMapLocalTileProvider.readTileImage(int, int, int):byte[]");
        }

        private String getTileFilename(int i, int i2, int i3) {
            CharSequence charSequence = "{y}";
            return this.pathTemplate.replace("{x}", Integer.toString(i)).replace(charSequence, Integer.toString(i2)).replace("{z}", Integer.toString(i3));
        }
    }

    public AirMapLocalTile(Context context) {
        super(context);
    }

    public void setPathTemplate(String str) {
        this.pathTemplate = str;
        AIRMapLocalTileProvider aIRMapLocalTileProvider = this.tileProvider;
        if (aIRMapLocalTileProvider != null) {
            aIRMapLocalTileProvider.setPathTemplate(str);
        }
        TileOverlay tileOverlay = this.tileOverlay;
        if (tileOverlay != null) {
            tileOverlay.clearTileCache();
        }
    }

    public void setZIndex(float f) {
        this.zIndex = f;
        TileOverlay tileOverlay = this.tileOverlay;
        if (tileOverlay != null) {
            tileOverlay.setZIndex(f);
        }
    }

    public void setTileSize(float f) {
        this.tileSize = f;
        AIRMapLocalTileProvider aIRMapLocalTileProvider = this.tileProvider;
        if (aIRMapLocalTileProvider != null) {
            aIRMapLocalTileProvider.setTileSize((int) f);
        }
    }

    public TileOverlayOptions getTileOverlayOptions() {
        if (this.tileOverlayOptions == null) {
            this.tileOverlayOptions = createTileOverlayOptions();
        }
        return this.tileOverlayOptions;
    }

    private TileOverlayOptions createTileOverlayOptions() {
        TileOverlayOptions tileOverlayOptions = new TileOverlayOptions();
        tileOverlayOptions.zIndex(this.zIndex);
        this.tileProvider = new AIRMapLocalTileProvider((int) this.tileSize, this.pathTemplate);
        tileOverlayOptions.tileProvider(this.tileProvider);
        return tileOverlayOptions;
    }

    public Object getFeature() {
        return this.tileOverlay;
    }

    public void addToMap(GoogleMap googleMap) {
        this.tileOverlay = googleMap.addTileOverlay(getTileOverlayOptions());
    }

    public void removeFromMap(GoogleMap googleMap) {
        this.tileOverlay.remove();
    }
}
