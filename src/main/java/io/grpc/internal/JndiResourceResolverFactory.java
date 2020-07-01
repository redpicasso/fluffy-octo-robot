package io.grpc.internal;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Verify;
import io.grpc.Attributes;
import io.grpc.EquivalentAddressGroup;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement;

final class JndiResourceResolverFactory implements ResourceResolverFactory {
    @Nullable
    private static final Throwable JNDI_UNAVAILABILITY_CAUSE = initJndi();

    @VisibleForTesting
    static final class JndiResourceResolver implements ResourceResolver {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private static final Logger logger = Logger.getLogger(JndiResourceResolver.class.getName());
        private static final Pattern whitespace = Pattern.compile("\\s+");

        @VisibleForTesting
        static final class SrvRecord {
            final String host;
            final int port;

            SrvRecord(String str, int i) {
                this.host = str;
                this.port = i;
            }
        }

        static {
            Class cls = JndiResourceResolverFactory.class;
        }

        JndiResourceResolver() {
        }

        public List<String> resolveTxt(String str) throws NamingException {
            checkAvailable();
            if (logger.isLoggable(Level.FINER)) {
                logger.log(Level.FINER, "About to query TXT records for {0}", new Object[]{str});
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("dns:///");
            stringBuilder.append(str);
            List<String> allRecords = getAllRecords("TXT", stringBuilder.toString());
            if (logger.isLoggable(Level.FINER)) {
                logger.log(Level.FINER, "Found {0} TXT records", new Object[]{Integer.valueOf(allRecords.size())});
            }
            List arrayList = new ArrayList(allRecords.size());
            for (String unquote : allRecords) {
                arrayList.add(unquote(unquote));
            }
            return Collections.unmodifiableList(arrayList);
        }

        public List<EquivalentAddressGroup> resolveSrv(AddressResolver addressResolver, String str) throws Exception {
            Throwable e;
            Logger logger;
            StringBuilder stringBuilder;
            checkAvailable();
            if (logger.isLoggable(Level.FINER)) {
                logger.log(Level.FINER, "About to query SRV records for {0}", new Object[]{str});
            }
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("dns:///");
            stringBuilder2.append(str);
            List<String> allRecords = getAllRecords("SRV", stringBuilder2.toString());
            if (logger.isLoggable(Level.FINER)) {
                logger.log(Level.FINER, "Found {0} SRV records", new Object[]{Integer.valueOf(allRecords.size())});
            }
            List arrayList = new ArrayList(allRecords.size());
            Throwable th = null;
            Level level = Level.WARNING;
            for (String str2 : allRecords) {
                try {
                    SrvRecord parseSrvRecord = parseSrvRecord(str2);
                    List<InetAddress> resolveAddress = addressResolver.resolveAddress(parseSrvRecord.host);
                    List arrayList2 = new ArrayList(resolveAddress.size());
                    for (InetAddress inetSocketAddress : resolveAddress) {
                        arrayList2.add(new InetSocketAddress(inetSocketAddress, parseSrvRecord.port));
                    }
                    arrayList.add(new EquivalentAddressGroup(Collections.unmodifiableList(arrayList2), Attributes.newBuilder().set(GrpcAttributes.ATTR_LB_ADDR_AUTHORITY, parseSrvRecord.host).build()));
                } catch (UnknownHostException e2) {
                    e = e2;
                    logger = logger;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("Can't find address for SRV record ");
                    stringBuilder.append(str2);
                    logger.log(level, stringBuilder.toString(), e);
                    if (th == null) {
                        level = Level.FINE;
                        th = e;
                    }
                } catch (RuntimeException e3) {
                    e = e3;
                    logger = logger;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("Failed to construct SRV record ");
                    stringBuilder.append(str2);
                    logger.log(level, stringBuilder.toString(), e);
                    if (th == null) {
                        level = Level.FINE;
                        th = e;
                    }
                }
            }
            if (!arrayList.isEmpty() || th == null) {
                return Collections.unmodifiableList(arrayList);
            }
            throw th;
        }

        @VisibleForTesting
        static SrvRecord parseSrvRecord(String str) {
            String[] split = whitespace.split(str);
            Verify.verify(split.length == 4, "Bad SRV Record: %s", (Object) str);
            return new SrvRecord(split[3], Integer.parseInt(split[2]));
        }

        @IgnoreJRERequirement
        private static List<String> getAllRecords(String str, String str2) throws NamingException {
            String[] strArr = new String[]{str};
            List<String> arrayList = new ArrayList();
            Hashtable hashtable = new Hashtable();
            String str3 = "5000";
            hashtable.put("com.sun.jndi.ldap.connect.timeout", str3);
            hashtable.put("com.sun.jndi.ldap.read.timeout", str3);
            DirContext initialDirContext = new InitialDirContext(hashtable);
            try {
                NamingEnumeration all = initialDirContext.getAttributes(str2, strArr).getAll();
                while (all.hasMore()) {
                    try {
                        NamingEnumeration all2 = ((Attribute) all.next()).getAll();
                        while (all2.hasMore()) {
                            try {
                                arrayList.add(String.valueOf(all2.next()));
                            } catch (NamingException e) {
                                closeThenThrow(all2, e);
                            }
                        }
                        all2.close();
                    } catch (NamingException e2) {
                        closeThenThrow(all, e2);
                    }
                }
                all.close();
            } catch (NamingException e3) {
                closeThenThrow(initialDirContext, e3);
            }
            initialDirContext.close();
            return arrayList;
        }

        @IgnoreJRERequirement
        private static void closeThenThrow(NamingEnumeration<?> namingEnumeration, NamingException namingException) throws NamingException {
            try {
                namingEnumeration.close();
            } catch (NamingException unused) {
                throw namingException;
            }
        }

        @IgnoreJRERequirement
        private static void closeThenThrow(DirContext dirContext, NamingException namingException) throws NamingException {
            try {
                dirContext.close();
            } catch (NamingException unused) {
                throw namingException;
            }
        }

        @VisibleForTesting
        static String unquote(String str) {
            StringBuilder stringBuilder = new StringBuilder(str.length());
            int i = 0;
            Object obj = null;
            while (i < str.length()) {
                char charAt = str.charAt(i);
                if (obj == null) {
                    if (charAt != ' ') {
                        if (charAt == '\"') {
                            obj = 1;
                        }
                    }
                    i++;
                } else if (charAt == '\"') {
                    obj = null;
                    i++;
                } else if (charAt == '\\') {
                    i++;
                    charAt = str.charAt(i);
                }
                stringBuilder.append(charAt);
                i++;
            }
            return stringBuilder.toString();
        }

        private static void checkAvailable() {
            if (JndiResourceResolverFactory.JNDI_UNAVAILABILITY_CAUSE != null) {
                throw new UnsupportedOperationException("JNDI is not currently available", JndiResourceResolverFactory.JNDI_UNAVAILABILITY_CAUSE);
            }
        }
    }

    @Nullable
    private static Throwable initJndi() {
        if (GrpcUtil.IS_RESTRICTED_APPENGINE) {
            return new UnsupportedOperationException("Currently running in an AppEngine restricted environment");
        }
        try {
            Class.forName("javax.naming.directory.InitialDirContext");
            Class.forName("com.sun.jndi.dns.DnsContextFactory");
            return null;
        } catch (Throwable e) {
            return e;
        } catch (Throwable e2) {
            return e2;
        } catch (Throwable e22) {
            return e22;
        }
    }

    @Nullable
    public ResourceResolver newResourceResolver() {
        if (unavailabilityCause() != null) {
            return null;
        }
        return new JndiResourceResolver();
    }

    @Nullable
    public Throwable unavailabilityCause() {
        return JNDI_UNAVAILABILITY_CAUSE;
    }
}
