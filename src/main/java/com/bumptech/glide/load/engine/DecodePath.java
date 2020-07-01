package com.bumptech.glide.load.engine;

import androidx.annotation.NonNull;
import androidx.core.util.Pools.Pool;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.data.DataRewinder;
import com.bumptech.glide.load.resource.transcode.ResourceTranscoder;
import com.bumptech.glide.util.Preconditions;
import java.util.List;

public class DecodePath<DataType, ResourceType, Transcode> {
    private static final String TAG = "DecodePath";
    private final Class<DataType> dataClass;
    private final List<? extends ResourceDecoder<DataType, ResourceType>> decoders;
    private final String failureMessage;
    private final Pool<List<Throwable>> listPool;
    private final ResourceTranscoder<ResourceType, Transcode> transcoder;

    interface DecodeCallback<ResourceType> {
        @NonNull
        Resource<ResourceType> onResourceDecoded(@NonNull Resource<ResourceType> resource);
    }

    public DecodePath(Class<DataType> cls, Class<ResourceType> cls2, Class<Transcode> cls3, List<? extends ResourceDecoder<DataType, ResourceType>> list, ResourceTranscoder<ResourceType, Transcode> resourceTranscoder, Pool<List<Throwable>> pool) {
        this.dataClass = cls;
        this.decoders = list;
        this.transcoder = resourceTranscoder;
        this.listPool = pool;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Failed DecodePath{");
        stringBuilder.append(cls.getSimpleName());
        String str = "->";
        stringBuilder.append(str);
        stringBuilder.append(cls2.getSimpleName());
        stringBuilder.append(str);
        stringBuilder.append(cls3.getSimpleName());
        stringBuilder.append("}");
        this.failureMessage = stringBuilder.toString();
    }

    public Resource<Transcode> decode(DataRewinder<DataType> dataRewinder, int i, int i2, @NonNull Options options, DecodeCallback<ResourceType> decodeCallback) throws GlideException {
        return this.transcoder.transcode(decodeCallback.onResourceDecoded(decodeResource(dataRewinder, i, i2, options)), options);
    }

    @NonNull
    private Resource<ResourceType> decodeResource(DataRewinder<DataType> dataRewinder, int i, int i2, @NonNull Options options) throws GlideException {
        List list = (List) Preconditions.checkNotNull(this.listPool.acquire());
        try {
            Resource<ResourceType> decodeResourceWithList = decodeResourceWithList(dataRewinder, i, i2, options, list);
            return decodeResourceWithList;
        } finally {
            this.listPool.release(list);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x004d A:{LOOP_END, LOOP:0: B:1:0x0008->B:15:0x004d} */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0050 A:{SYNTHETIC, EDGE_INSN: B:21:0x0050->B:16:0x0050 ?: BREAK  } */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0033  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0050 A:{SYNTHETIC, EDGE_INSN: B:21:0x0050->B:16:0x0050 ?: BREAK  } */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x004d A:{LOOP_END, LOOP:0: B:1:0x0008->B:15:0x004d} */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0033  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x004d A:{LOOP_END, LOOP:0: B:1:0x0008->B:15:0x004d} */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0050 A:{SYNTHETIC, EDGE_INSN: B:21:0x0050->B:16:0x0050 ?: BREAK  } */
    @androidx.annotation.NonNull
    private com.bumptech.glide.load.engine.Resource<ResourceType> decodeResourceWithList(com.bumptech.glide.load.data.DataRewinder<DataType> r9, int r10, int r11, @androidx.annotation.NonNull com.bumptech.glide.load.Options r12, java.util.List<java.lang.Throwable> r13) throws com.bumptech.glide.load.engine.GlideException {
        /*
        r8 = this;
        r0 = r8.decoders;
        r0 = r0.size();
        r1 = 0;
        r2 = 0;
    L_0x0008:
        if (r2 >= r0) goto L_0x0050;
    L_0x000a:
        r3 = r8.decoders;
        r3 = r3.get(r2);
        r3 = (com.bumptech.glide.load.ResourceDecoder) r3;
        r4 = r9.rewindAndGet();	 Catch:{ IOException -> 0x0029, RuntimeException -> 0x0027, OutOfMemoryError -> 0x0025 }
        r4 = r3.handles(r4, r12);	 Catch:{ IOException -> 0x0029, RuntimeException -> 0x0027, OutOfMemoryError -> 0x0025 }
        if (r4 == 0) goto L_0x004a;
    L_0x001c:
        r4 = r9.rewindAndGet();	 Catch:{ IOException -> 0x0029, RuntimeException -> 0x0027, OutOfMemoryError -> 0x0025 }
        r1 = r3.decode(r4, r10, r11, r12);	 Catch:{ IOException -> 0x0029, RuntimeException -> 0x0027, OutOfMemoryError -> 0x0025 }
        goto L_0x004a;
    L_0x0025:
        r4 = move-exception;
        goto L_0x002a;
    L_0x0027:
        r4 = move-exception;
        goto L_0x002a;
    L_0x0029:
        r4 = move-exception;
    L_0x002a:
        r5 = 2;
        r6 = "DecodePath";
        r5 = android.util.Log.isLoggable(r6, r5);
        if (r5 == 0) goto L_0x0047;
    L_0x0033:
        r5 = new java.lang.StringBuilder;
        r5.<init>();
        r7 = "Failed to decode data for ";
        r5.append(r7);
        r5.append(r3);
        r3 = r5.toString();
        android.util.Log.v(r6, r3, r4);
    L_0x0047:
        r13.add(r4);
    L_0x004a:
        if (r1 == 0) goto L_0x004d;
    L_0x004c:
        goto L_0x0050;
    L_0x004d:
        r2 = r2 + 1;
        goto L_0x0008;
    L_0x0050:
        if (r1 == 0) goto L_0x0053;
    L_0x0052:
        return r1;
    L_0x0053:
        r9 = new com.bumptech.glide.load.engine.GlideException;
        r10 = r8.failureMessage;
        r11 = new java.util.ArrayList;
        r11.<init>(r13);
        r9.<init>(r10, r11);
        throw r9;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.load.engine.DecodePath.decodeResourceWithList(com.bumptech.glide.load.data.DataRewinder, int, int, com.bumptech.glide.load.Options, java.util.List):com.bumptech.glide.load.engine.Resource<ResourceType>");
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DecodePath{ dataClass=");
        stringBuilder.append(this.dataClass);
        stringBuilder.append(", decoders=");
        stringBuilder.append(this.decoders);
        stringBuilder.append(", transcoder=");
        stringBuilder.append(this.transcoder);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }
}
