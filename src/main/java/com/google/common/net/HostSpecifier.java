package com.google.common.net;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import java.net.InetAddress;
import java.text.ParseException;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtIncompatible
@Beta
public final class HostSpecifier {
    private final String canonicalForm;

    private HostSpecifier(String str) {
        this.canonicalForm = str;
    }

    public static HostSpecifier fromValid(String str) {
        HostAndPort fromString = HostAndPort.fromString(str);
        Preconditions.checkArgument(fromString.hasPort() ^ 1);
        str = fromString.getHost();
        InetAddress inetAddress = null;
        try {
            inetAddress = InetAddresses.forString(str);
        } catch (IllegalArgumentException unused) {
            if (inetAddress != null) {
                return new HostSpecifier(InetAddresses.toUriString(inetAddress));
            }
            InternetDomainName from = InternetDomainName.from(str);
            if (from.hasPublicSuffix()) {
                return new HostSpecifier(from.toString());
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Domain name does not have a recognized public suffix: ");
            stringBuilder.append(str);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
    }

    public static HostSpecifier from(String str) throws ParseException {
        try {
            str = fromValid(str);
            return str;
        } catch (Throwable e) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid host specifier: ");
            stringBuilder.append(str);
            ParseException parseException = new ParseException(stringBuilder.toString(), 0);
            parseException.initCause(e);
            throw parseException;
        }
    }

    public static boolean isValid(String str) {
        try {
            fromValid(str);
            return true;
        } catch (IllegalArgumentException unused) {
            return false;
        }
    }

    public boolean equals(@NullableDecl Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof HostSpecifier)) {
            return false;
        }
        return this.canonicalForm.equals(((HostSpecifier) obj).canonicalForm);
    }

    public int hashCode() {
        return this.canonicalForm.hashCode();
    }

    public String toString() {
        return this.canonicalForm;
    }
}
