package com.google.android.gms.internal.firebase_ml;

import com.adobe.xmp.XMPError;
import com.drew.metadata.exif.makernotes.OlympusRawInfoMakernoteDirectory;
import com.google.logging.type.LogSeverity;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;

public final class zzfk {
    private final int statusCode;
    private final zzfg zztm;
    private int zzuk;
    private boolean zzul;
    private InputStream zzux;
    private final String zzuy;
    private final String zzuz;
    private zzfq zzva;
    private final String zzvb;
    private final zzfh zzvc;
    private boolean zzvd;

    zzfk(zzfh zzfh, zzfq zzfq) throws IOException {
        StringBuilder stringBuilder;
        this.zzvc = zzfh;
        this.zzuk = zzfh.zzfc();
        this.zzul = zzfh.zzfd();
        this.zzva = zzfq;
        this.zzuy = zzfq.getContentEncoding();
        int statusCode = zzfq.getStatusCode();
        Object obj = null;
        if (statusCode < 0) {
            statusCode = 0;
        }
        this.statusCode = statusCode;
        String reasonPhrase = zzfq.getReasonPhrase();
        this.zzvb = reasonPhrase;
        Logger logger = zzfo.zzve;
        if (this.zzul && logger.isLoggable(Level.CONFIG)) {
            obj = 1;
        }
        zzfg zzfg = null;
        if (obj != null) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("-------------- RESPONSE --------------");
            stringBuilder.append(zzhz.zzaae);
            String zzfp = zzfq.zzfp();
            if (zzfp != null) {
                stringBuilder.append(zzfp);
            } else {
                stringBuilder.append(this.statusCode);
                if (reasonPhrase != null) {
                    stringBuilder.append(' ');
                    stringBuilder.append(reasonPhrase);
                }
            }
            stringBuilder.append(zzhz.zzaae);
        } else {
            stringBuilder = null;
        }
        zzfh.zzff().zza(zzfq, obj != null ? stringBuilder : null);
        String contentType = zzfq.getContentType();
        if (contentType == null) {
            contentType = zzfh.zzff().getContentType();
        }
        this.zzuz = contentType;
        if (contentType != null) {
            zzfg = new zzfg(contentType);
        }
        this.zztm = zzfg;
        if (obj != null) {
            logger.logp(Level.CONFIG, "com.google.api.client.http.HttpResponse", "<init>", stringBuilder.toString());
        }
    }

    public final String getContentType() {
        return this.zzuz;
    }

    public final zzfe zzfe() {
        return this.zzvc.zzff();
    }

    public final boolean zzfk() {
        int i = this.statusCode;
        return i >= LogSeverity.INFO_VALUE && i < 300;
    }

    public final int getStatusCode() {
        return this.statusCode;
    }

    public final String getStatusMessage() {
        return this.zzvb;
    }

    public final InputStream getContent() throws IOException {
        if (!this.zzvd) {
            InputStream content = this.zzva.getContent();
            if (content != null) {
                try {
                    String str = this.zzuy;
                    if (str != null && str.contains("gzip")) {
                        content = new GZIPInputStream(content);
                    }
                    Logger logger = zzfo.zzve;
                    if (this.zzul && logger.isLoggable(Level.CONFIG)) {
                        content = new zzhq(content, logger, Level.CONFIG, this.zzuk);
                    }
                    this.zzux = content;
                } catch (EOFException unused) {
                    content.close();
                } catch (Throwable th) {
                    content.close();
                }
            }
            this.zzvd = true;
        }
        return this.zzux;
    }

    public final void ignore() throws IOException {
        InputStream content = getContent();
        if (content != null) {
            content.close();
        }
    }

    public final void disconnect() throws IOException {
        ignore();
        this.zzva.disconnect();
    }

    public final <T> T zza(Class<T> cls) throws IOException {
        int i = this.statusCode;
        Object obj = 1;
        if (this.zzvc.getRequestMethod().equals("HEAD") || i / 100 == 1 || i == XMPError.BADSTREAM || i == OlympusRawInfoMakernoteDirectory.TagWbRbLevelsDaylightFluor) {
            ignore();
            obj = null;
        }
        if (obj == null) {
            return null;
        }
        return this.zzvc.zzfh().zza(getContent(), zzfm(), cls);
    }

    public final String zzfl() throws IOException {
        InputStream content = getContent();
        if (content == null) {
            return "";
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            zzhb.copy(content, byteArrayOutputStream);
            return byteArrayOutputStream.toString(zzfm().name());
        } finally {
            content.close();
        }
    }

    private final Charset zzfm() {
        zzfg zzfg = this.zztm;
        return (zzfg == null || zzfg.zzey() == null) ? zzhc.ISO_8859_1 : this.zztm.zzey();
    }
}
