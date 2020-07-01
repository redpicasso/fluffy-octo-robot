package io.grpc.internal;

import com.facebook.react.modules.systeminfo.AndroidInfoHelpers;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs.CastExtraArgs;
import com.google.android.gms.measurement.api.AppMeasurementSdk.ConditionalUserProperty;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.base.Verify;
import io.grpc.Attributes;
import io.grpc.EquivalentAddressGroup;
import io.grpc.NameResolver;
import io.grpc.NameResolver.Factory;
import io.grpc.NameResolver.Listener;
import io.grpc.internal.SharedResourceHolder.Resource;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;

final class DnsNameResolver extends NameResolver {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final String GRPCLB_NAME_PREFIX = "_grpclb._tcp.";
    private static final String JNDI_LOCALHOST_PROPERTY;
    private static final String JNDI_PROPERTY = System.getProperty("io.grpc.internal.DnsNameResolverProvider.enable_jndi", "true");
    private static final String JNDI_SRV_PROPERTY;
    private static final String JNDI_TXT_PROPERTY;
    private static final String SERVICE_CONFIG_CHOICE_CLIENT_HOSTNAME_KEY = "clientHostname";
    private static final String SERVICE_CONFIG_CHOICE_CLIENT_LANGUAGE_KEY = "clientLanguage";
    private static final Set<String> SERVICE_CONFIG_CHOICE_KEYS = Collections.unmodifiableSet(new HashSet(Arrays.asList(new String[]{SERVICE_CONFIG_CHOICE_CLIENT_LANGUAGE_KEY, SERVICE_CONFIG_CHOICE_PERCENTAGE_KEY, SERVICE_CONFIG_CHOICE_CLIENT_HOSTNAME_KEY, SERVICE_CONFIG_CHOICE_SERVICE_CONFIG_KEY})));
    private static final String SERVICE_CONFIG_CHOICE_PERCENTAGE_KEY = "percentage";
    private static final String SERVICE_CONFIG_CHOICE_SERVICE_CONFIG_KEY = "serviceConfig";
    private static final String SERVICE_CONFIG_NAME_PREFIX = "_grpc_config.";
    static final String SERVICE_CONFIG_PREFIX = "_grpc_config=";
    @VisibleForTesting
    static boolean enableJndi = Boolean.parseBoolean(JNDI_PROPERTY);
    @VisibleForTesting
    static boolean enableJndiLocalhost = Boolean.parseBoolean(JNDI_LOCALHOST_PROPERTY);
    @VisibleForTesting
    static boolean enableSrv = Boolean.parseBoolean(JNDI_SRV_PROPERTY);
    @VisibleForTesting
    static boolean enableTxt = Boolean.parseBoolean(JNDI_TXT_PROPERTY);
    private static String localHostname;
    private static final Logger logger = Logger.getLogger(DnsNameResolver.class.getName());
    private static final ResourceResolverFactory resourceResolverFactory = getResourceResolverFactory(DnsNameResolver.class.getClassLoader());
    private volatile AddressResolver addressResolver = JdkAddressResolver.INSTANCE;
    private final String authority;
    @GuardedBy("this")
    private ExecutorService executor;
    private final Resource<ExecutorService> executorResource;
    private final String host;
    @GuardedBy("this")
    private Listener listener;
    private final int port;
    @VisibleForTesting
    final ProxyDetector proxyDetector;
    private final Random random = new Random();
    private final Runnable resolutionRunnable = new Runnable() {
        /* JADX WARNING: Removed duplicated region for block: B:55:0x0110 A:{Catch:{ RuntimeException -> 0x0102, all -> 0x01a3 }} */
        /* JADX WARNING: Removed duplicated region for block: B:60:0x0135 A:{SYNTHETIC} */
        /* JADX WARNING: Missing block: B:11:?, code:
            r2 = java.net.InetSocketAddress.createUnresolved(io.grpc.internal.DnsNameResolver.access$300(r12.this$0), io.grpc.internal.DnsNameResolver.access$400(r12.this$0));
     */
        /* JADX WARNING: Missing block: B:13:?, code:
            r4 = r12.this$0.proxyDetector.proxyFor(r2);
     */
        /* JADX WARNING: Missing block: B:14:0x0033, code:
            if (r4 == null) goto L_0x0055;
     */
        /* JADX WARNING: Missing block: B:16:?, code:
            r1.onAddresses(java.util.Collections.singletonList(new io.grpc.EquivalentAddressGroup(new io.grpc.internal.ProxySocketAddress(r2, r4))), io.grpc.Attributes.EMPTY);
     */
        /* JADX WARNING: Missing block: B:17:0x0048, code:
            r1 = r12.this$0;
     */
        /* JADX WARNING: Missing block: B:18:0x004a, code:
            monitor-enter(r1);
     */
        /* JADX WARNING: Missing block: B:20:?, code:
            io.grpc.internal.DnsNameResolver.access$202(r12.this$0, false);
     */
        /* JADX WARNING: Missing block: B:21:0x0050, code:
            monitor-exit(r1);
     */
        /* JADX WARNING: Missing block: B:22:0x0051, code:
            return;
     */
        /* JADX WARNING: Missing block: B:27:?, code:
            r4 = null;
     */
        /* JADX WARNING: Missing block: B:28:0x0064, code:
            if (io.grpc.internal.DnsNameResolver.shouldUseJndi(io.grpc.internal.DnsNameResolver.enableJndi, io.grpc.internal.DnsNameResolver.enableJndiLocalhost, io.grpc.internal.DnsNameResolver.access$300(r12.this$0)) == false) goto L_0x006d;
     */
        /* JADX WARNING: Missing block: B:29:0x0066, code:
            r2 = io.grpc.internal.DnsNameResolver.access$500(r12.this$0);
     */
        /* JADX WARNING: Missing block: B:30:0x006d, code:
            r2 = null;
     */
        /* JADX WARNING: Missing block: B:31:0x006e, code:
            r2 = io.grpc.internal.DnsNameResolver.resolveAll(io.grpc.internal.DnsNameResolver.access$600(r12.this$0), r2, io.grpc.internal.DnsNameResolver.enableSrv, io.grpc.internal.DnsNameResolver.enableTxt, io.grpc.internal.DnsNameResolver.access$300(r12.this$0));
     */
        /* JADX WARNING: Missing block: B:33:?, code:
            r5 = new java.util.ArrayList();
            r6 = r2.addresses.iterator();
     */
        /* JADX WARNING: Missing block: B:35:0x0091, code:
            if (r6.hasNext() == false) goto L_0x00ad;
     */
        /* JADX WARNING: Missing block: B:36:0x0093, code:
            r5.add(new io.grpc.EquivalentAddressGroup(new java.net.InetSocketAddress((java.net.InetAddress) r6.next(), io.grpc.internal.DnsNameResolver.access$400(r12.this$0))));
     */
        /* JADX WARNING: Missing block: B:37:0x00ad, code:
            r5.addAll(r2.balancerAddresses);
            r6 = io.grpc.Attributes.newBuilder();
     */
        /* JADX WARNING: Missing block: B:38:0x00bc, code:
            if (r2.txtRecords.isEmpty() != false) goto L_0x0116;
     */
        /* JADX WARNING: Missing block: B:40:?, code:
            r2 = io.grpc.internal.DnsNameResolver.parseTxtResults(r2.txtRecords).iterator();
     */
        /* JADX WARNING: Missing block: B:42:0x00cc, code:
            if (r2.hasNext() == false) goto L_0x010e;
     */
        /* JADX WARNING: Missing block: B:45:?, code:
            r3 = io.grpc.internal.DnsNameResolver.maybeChooseServiceConfig((java.util.Map) r2.next(), io.grpc.internal.DnsNameResolver.access$700(r12.this$0), io.grpc.internal.DnsNameResolver.access$800());
     */
        /* JADX WARNING: Missing block: B:46:0x00e2, code:
            r4 = r3;
     */
        /* JADX WARNING: Missing block: B:47:0x00e4, code:
            r7 = move-exception;
     */
        /* JADX WARNING: Missing block: B:49:?, code:
            r8 = io.grpc.internal.DnsNameResolver.access$900();
            r9 = java.util.logging.Level.WARNING;
            r10 = new java.lang.StringBuilder();
            r10.append("Bad service config choice ");
            r10.append(r3);
            r8.log(r9, r10.toString(), r7);
     */
        /* JADX WARNING: Missing block: B:51:0x0102, code:
            r2 = move-exception;
     */
        /* JADX WARNING: Missing block: B:53:?, code:
            io.grpc.internal.DnsNameResolver.access$900().log(java.util.logging.Level.WARNING, "Can't parse service Configs", r2);
     */
        /* JADX WARNING: Missing block: B:56:0x0116, code:
            io.grpc.internal.DnsNameResolver.access$900().log(java.util.logging.Level.FINE, "No TXT records found for {0}", new java.lang.Object[]{io.grpc.internal.DnsNameResolver.access$300(r12.this$0)});
     */
        /* JADX WARNING: Missing block: B:67:0x013f, code:
            r2 = move-exception;
     */
        /* JADX WARNING: Missing block: B:69:?, code:
            r3 = io.grpc.Status.UNAVAILABLE;
            r4 = new java.lang.StringBuilder();
            r4.append("Unable to resolve host ");
            r4.append(io.grpc.internal.DnsNameResolver.access$300(r12.this$0));
            r1.onError(r3.withDescription(r4.toString()).withCause(r2));
     */
        /* JADX WARNING: Missing block: B:71:0x0166, code:
            monitor-enter(r12.this$0);
     */
        /* JADX WARNING: Missing block: B:73:?, code:
            io.grpc.internal.DnsNameResolver.access$202(r12.this$0, false);
     */
        /* JADX WARNING: Missing block: B:75:0x016d, code:
            return;
     */
        /* JADX WARNING: Missing block: B:79:0x0171, code:
            r2 = move-exception;
     */
        /* JADX WARNING: Missing block: B:81:?, code:
            r3 = io.grpc.Status.UNAVAILABLE;
            r4 = new java.lang.StringBuilder();
            r4.append("Unable to resolve host ");
            r4.append(io.grpc.internal.DnsNameResolver.access$300(r12.this$0));
            r1.onError(r3.withDescription(r4.toString()).withCause(r2));
     */
        /* JADX WARNING: Missing block: B:83:0x0198, code:
            monitor-enter(r12.this$0);
     */
        /* JADX WARNING: Missing block: B:85:?, code:
            io.grpc.internal.DnsNameResolver.access$202(r12.this$0, false);
     */
        /* JADX WARNING: Missing block: B:87:0x019f, code:
            return;
     */
        /* JADX WARNING: Missing block: B:93:0x01a6, code:
            monitor-enter(r12.this$0);
     */
        /* JADX WARNING: Missing block: B:95:?, code:
            io.grpc.internal.DnsNameResolver.access$202(r12.this$0, false);
     */
        public void run() {
            /*
            r12 = this;
            r0 = io.grpc.internal.DnsNameResolver.this;
            monitor-enter(r0);
            r1 = io.grpc.internal.DnsNameResolver.this;	 Catch:{ all -> 0x01b1 }
            r1 = r1.shutdown;	 Catch:{ all -> 0x01b1 }
            if (r1 == 0) goto L_0x000d;
        L_0x000b:
            monitor-exit(r0);	 Catch:{ all -> 0x01b1 }
            return;
        L_0x000d:
            r1 = io.grpc.internal.DnsNameResolver.this;	 Catch:{ all -> 0x01b1 }
            r1 = r1.listener;	 Catch:{ all -> 0x01b1 }
            r2 = io.grpc.internal.DnsNameResolver.this;	 Catch:{ all -> 0x01b1 }
            r3 = 1;
            r2.resolving = r3;	 Catch:{ all -> 0x01b1 }
            monitor-exit(r0);	 Catch:{ all -> 0x01b1 }
            r0 = 0;
            r2 = io.grpc.internal.DnsNameResolver.this;	 Catch:{ all -> 0x01a3 }
            r2 = r2.host;	 Catch:{ all -> 0x01a3 }
            r4 = io.grpc.internal.DnsNameResolver.this;	 Catch:{ all -> 0x01a3 }
            r4 = r4.port;	 Catch:{ all -> 0x01a3 }
            r2 = java.net.InetSocketAddress.createUnresolved(r2, r4);	 Catch:{ all -> 0x01a3 }
            r4 = io.grpc.internal.DnsNameResolver.this;	 Catch:{ IOException -> 0x0171 }
            r4 = r4.proxyDetector;	 Catch:{ IOException -> 0x0171 }
            r4 = r4.proxyFor(r2);	 Catch:{ IOException -> 0x0171 }
            if (r4 == 0) goto L_0x0055;
        L_0x0035:
            r3 = new io.grpc.EquivalentAddressGroup;	 Catch:{ all -> 0x01a3 }
            r5 = new io.grpc.internal.ProxySocketAddress;	 Catch:{ all -> 0x01a3 }
            r5.<init>(r2, r4);	 Catch:{ all -> 0x01a3 }
            r3.<init>(r5);	 Catch:{ all -> 0x01a3 }
            r2 = java.util.Collections.singletonList(r3);	 Catch:{ all -> 0x01a3 }
            r3 = io.grpc.Attributes.EMPTY;	 Catch:{ all -> 0x01a3 }
            r1.onAddresses(r2, r3);	 Catch:{ all -> 0x01a3 }
            r1 = io.grpc.internal.DnsNameResolver.this;
            monitor-enter(r1);
            r2 = io.grpc.internal.DnsNameResolver.this;	 Catch:{ all -> 0x0052 }
            r2.resolving = r0;	 Catch:{ all -> 0x0052 }
            monitor-exit(r1);	 Catch:{ all -> 0x0052 }
            return;
        L_0x0052:
            r0 = move-exception;
            monitor-exit(r1);	 Catch:{ all -> 0x0052 }
            throw r0;
        L_0x0055:
            r2 = io.grpc.internal.DnsNameResolver.enableJndi;	 Catch:{ Exception -> 0x013f }
            r4 = io.grpc.internal.DnsNameResolver.enableJndiLocalhost;	 Catch:{ Exception -> 0x013f }
            r5 = io.grpc.internal.DnsNameResolver.this;	 Catch:{ Exception -> 0x013f }
            r5 = r5.host;	 Catch:{ Exception -> 0x013f }
            r2 = io.grpc.internal.DnsNameResolver.shouldUseJndi(r2, r4, r5);	 Catch:{ Exception -> 0x013f }
            r4 = 0;
            if (r2 == 0) goto L_0x006d;
        L_0x0066:
            r2 = io.grpc.internal.DnsNameResolver.this;	 Catch:{ Exception -> 0x013f }
            r2 = r2.getResourceResolver();	 Catch:{ Exception -> 0x013f }
            goto L_0x006e;
        L_0x006d:
            r2 = r4;
        L_0x006e:
            r5 = io.grpc.internal.DnsNameResolver.this;	 Catch:{ Exception -> 0x013f }
            r5 = r5.addressResolver;	 Catch:{ Exception -> 0x013f }
            r6 = io.grpc.internal.DnsNameResolver.enableSrv;	 Catch:{ Exception -> 0x013f }
            r7 = io.grpc.internal.DnsNameResolver.enableTxt;	 Catch:{ Exception -> 0x013f }
            r8 = io.grpc.internal.DnsNameResolver.this;	 Catch:{ Exception -> 0x013f }
            r8 = r8.host;	 Catch:{ Exception -> 0x013f }
            r2 = io.grpc.internal.DnsNameResolver.resolveAll(r5, r2, r6, r7, r8);	 Catch:{ Exception -> 0x013f }
            r5 = new java.util.ArrayList;	 Catch:{ all -> 0x01a3 }
            r5.<init>();	 Catch:{ all -> 0x01a3 }
            r6 = r2.addresses;	 Catch:{ all -> 0x01a3 }
            r6 = r6.iterator();	 Catch:{ all -> 0x01a3 }
        L_0x008d:
            r7 = r6.hasNext();	 Catch:{ all -> 0x01a3 }
            if (r7 == 0) goto L_0x00ad;
        L_0x0093:
            r7 = r6.next();	 Catch:{ all -> 0x01a3 }
            r7 = (java.net.InetAddress) r7;	 Catch:{ all -> 0x01a3 }
            r8 = new io.grpc.EquivalentAddressGroup;	 Catch:{ all -> 0x01a3 }
            r9 = new java.net.InetSocketAddress;	 Catch:{ all -> 0x01a3 }
            r10 = io.grpc.internal.DnsNameResolver.this;	 Catch:{ all -> 0x01a3 }
            r10 = r10.port;	 Catch:{ all -> 0x01a3 }
            r9.<init>(r7, r10);	 Catch:{ all -> 0x01a3 }
            r8.<init>(r9);	 Catch:{ all -> 0x01a3 }
            r5.add(r8);	 Catch:{ all -> 0x01a3 }
            goto L_0x008d;
        L_0x00ad:
            r6 = r2.balancerAddresses;	 Catch:{ all -> 0x01a3 }
            r5.addAll(r6);	 Catch:{ all -> 0x01a3 }
            r6 = io.grpc.Attributes.newBuilder();	 Catch:{ all -> 0x01a3 }
            r7 = r2.txtRecords;	 Catch:{ all -> 0x01a3 }
            r7 = r7.isEmpty();	 Catch:{ all -> 0x01a3 }
            if (r7 != 0) goto L_0x0116;
        L_0x00be:
            r2 = r2.txtRecords;	 Catch:{ RuntimeException -> 0x0102 }
            r2 = io.grpc.internal.DnsNameResolver.parseTxtResults(r2);	 Catch:{ RuntimeException -> 0x0102 }
            r2 = r2.iterator();	 Catch:{ RuntimeException -> 0x0102 }
        L_0x00c8:
            r3 = r2.hasNext();	 Catch:{ RuntimeException -> 0x0102 }
            if (r3 == 0) goto L_0x010e;
        L_0x00ce:
            r3 = r2.next();	 Catch:{ RuntimeException -> 0x0102 }
            r3 = (java.util.Map) r3;	 Catch:{ RuntimeException -> 0x0102 }
            r7 = io.grpc.internal.DnsNameResolver.this;	 Catch:{ RuntimeException -> 0x00e4 }
            r7 = r7.random;	 Catch:{ RuntimeException -> 0x00e4 }
            r8 = io.grpc.internal.DnsNameResolver.getLocalHostname();	 Catch:{ RuntimeException -> 0x00e4 }
            r3 = io.grpc.internal.DnsNameResolver.maybeChooseServiceConfig(r3, r7, r8);	 Catch:{ RuntimeException -> 0x00e4 }
            r4 = r3;
            goto L_0x00ff;
        L_0x00e4:
            r7 = move-exception;
            r8 = io.grpc.internal.DnsNameResolver.logger;	 Catch:{ RuntimeException -> 0x0102 }
            r9 = java.util.logging.Level.WARNING;	 Catch:{ RuntimeException -> 0x0102 }
            r10 = new java.lang.StringBuilder;	 Catch:{ RuntimeException -> 0x0102 }
            r10.<init>();	 Catch:{ RuntimeException -> 0x0102 }
            r11 = "Bad service config choice ";
            r10.append(r11);	 Catch:{ RuntimeException -> 0x0102 }
            r10.append(r3);	 Catch:{ RuntimeException -> 0x0102 }
            r3 = r10.toString();	 Catch:{ RuntimeException -> 0x0102 }
            r8.log(r9, r3, r7);	 Catch:{ RuntimeException -> 0x0102 }
        L_0x00ff:
            if (r4 == 0) goto L_0x00c8;
        L_0x0101:
            goto L_0x010e;
        L_0x0102:
            r2 = move-exception;
            r3 = io.grpc.internal.DnsNameResolver.logger;	 Catch:{ all -> 0x01a3 }
            r7 = java.util.logging.Level.WARNING;	 Catch:{ all -> 0x01a3 }
            r8 = "Can't parse service Configs";
            r3.log(r7, r8, r2);	 Catch:{ all -> 0x01a3 }
        L_0x010e:
            if (r4 == 0) goto L_0x012b;
        L_0x0110:
            r2 = io.grpc.internal.GrpcAttributes.NAME_RESOLVER_SERVICE_CONFIG;	 Catch:{ all -> 0x01a3 }
            r6.set(r2, r4);	 Catch:{ all -> 0x01a3 }
            goto L_0x012b;
        L_0x0116:
            r2 = io.grpc.internal.DnsNameResolver.logger;	 Catch:{ all -> 0x01a3 }
            r4 = java.util.logging.Level.FINE;	 Catch:{ all -> 0x01a3 }
            r7 = "No TXT records found for {0}";
            r3 = new java.lang.Object[r3];	 Catch:{ all -> 0x01a3 }
            r8 = io.grpc.internal.DnsNameResolver.this;	 Catch:{ all -> 0x01a3 }
            r8 = r8.host;	 Catch:{ all -> 0x01a3 }
            r3[r0] = r8;	 Catch:{ all -> 0x01a3 }
            r2.log(r4, r7, r3);	 Catch:{ all -> 0x01a3 }
        L_0x012b:
            r2 = r6.build();	 Catch:{ all -> 0x01a3 }
            r1.onAddresses(r5, r2);	 Catch:{ all -> 0x01a3 }
            r1 = io.grpc.internal.DnsNameResolver.this;
            monitor-enter(r1);
            r2 = io.grpc.internal.DnsNameResolver.this;	 Catch:{ all -> 0x013c }
            r2.resolving = r0;	 Catch:{ all -> 0x013c }
            monitor-exit(r1);	 Catch:{ all -> 0x013c }
            return;
        L_0x013c:
            r0 = move-exception;
            monitor-exit(r1);	 Catch:{ all -> 0x013c }
            throw r0;
        L_0x013f:
            r2 = move-exception;
            r3 = io.grpc.Status.UNAVAILABLE;	 Catch:{ all -> 0x01a3 }
            r4 = new java.lang.StringBuilder;	 Catch:{ all -> 0x01a3 }
            r4.<init>();	 Catch:{ all -> 0x01a3 }
            r5 = "Unable to resolve host ";
            r4.append(r5);	 Catch:{ all -> 0x01a3 }
            r5 = io.grpc.internal.DnsNameResolver.this;	 Catch:{ all -> 0x01a3 }
            r5 = r5.host;	 Catch:{ all -> 0x01a3 }
            r4.append(r5);	 Catch:{ all -> 0x01a3 }
            r4 = r4.toString();	 Catch:{ all -> 0x01a3 }
            r3 = r3.withDescription(r4);	 Catch:{ all -> 0x01a3 }
            r2 = r3.withCause(r2);	 Catch:{ all -> 0x01a3 }
            r1.onError(r2);	 Catch:{ all -> 0x01a3 }
            r1 = io.grpc.internal.DnsNameResolver.this;
            monitor-enter(r1);
            r2 = io.grpc.internal.DnsNameResolver.this;	 Catch:{ all -> 0x016e }
            r2.resolving = r0;	 Catch:{ all -> 0x016e }
            monitor-exit(r1);	 Catch:{ all -> 0x016e }
            return;
        L_0x016e:
            r0 = move-exception;
            monitor-exit(r1);	 Catch:{ all -> 0x016e }
            throw r0;
        L_0x0171:
            r2 = move-exception;
            r3 = io.grpc.Status.UNAVAILABLE;	 Catch:{ all -> 0x01a3 }
            r4 = new java.lang.StringBuilder;	 Catch:{ all -> 0x01a3 }
            r4.<init>();	 Catch:{ all -> 0x01a3 }
            r5 = "Unable to resolve host ";
            r4.append(r5);	 Catch:{ all -> 0x01a3 }
            r5 = io.grpc.internal.DnsNameResolver.this;	 Catch:{ all -> 0x01a3 }
            r5 = r5.host;	 Catch:{ all -> 0x01a3 }
            r4.append(r5);	 Catch:{ all -> 0x01a3 }
            r4 = r4.toString();	 Catch:{ all -> 0x01a3 }
            r3 = r3.withDescription(r4);	 Catch:{ all -> 0x01a3 }
            r2 = r3.withCause(r2);	 Catch:{ all -> 0x01a3 }
            r1.onError(r2);	 Catch:{ all -> 0x01a3 }
            r1 = io.grpc.internal.DnsNameResolver.this;
            monitor-enter(r1);
            r2 = io.grpc.internal.DnsNameResolver.this;	 Catch:{ all -> 0x01a0 }
            r2.resolving = r0;	 Catch:{ all -> 0x01a0 }
            monitor-exit(r1);	 Catch:{ all -> 0x01a0 }
            return;
        L_0x01a0:
            r0 = move-exception;
            monitor-exit(r1);	 Catch:{ all -> 0x01a0 }
            throw r0;
        L_0x01a3:
            r1 = move-exception;
            r2 = io.grpc.internal.DnsNameResolver.this;
            monitor-enter(r2);
            r3 = io.grpc.internal.DnsNameResolver.this;	 Catch:{ all -> 0x01ae }
            r3.resolving = r0;	 Catch:{ all -> 0x01ae }
            monitor-exit(r2);	 Catch:{ all -> 0x01ae }
            throw r1;
        L_0x01ae:
            r0 = move-exception;
            monitor-exit(r2);	 Catch:{ all -> 0x01ae }
            throw r0;
        L_0x01b1:
            r1 = move-exception;
            monitor-exit(r0);	 Catch:{ all -> 0x01b1 }
            throw r1;
            */
            throw new UnsupportedOperationException("Method not decompiled: io.grpc.internal.DnsNameResolver.1.run():void");
        }
    };
    @GuardedBy("this")
    private boolean resolving;
    private final AtomicReference<ResourceResolver> resourceResolver = new AtomicReference();
    @GuardedBy("this")
    private boolean shutdown;

    interface AddressResolver {
        List<InetAddress> resolveAddress(String str) throws Exception;
    }

    @VisibleForTesting
    static final class ResolutionResults {
        final List<? extends InetAddress> addresses;
        final List<EquivalentAddressGroup> balancerAddresses;
        final List<String> txtRecords;

        ResolutionResults(List<? extends InetAddress> list, List<String> list2, List<EquivalentAddressGroup> list3) {
            this.addresses = Collections.unmodifiableList((List) Preconditions.checkNotNull(list, "addresses"));
            this.txtRecords = Collections.unmodifiableList((List) Preconditions.checkNotNull(list2, "txtRecords"));
            this.balancerAddresses = Collections.unmodifiableList((List) Preconditions.checkNotNull(list3, "balancerAddresses"));
        }
    }

    interface ResourceResolver {
        List<EquivalentAddressGroup> resolveSrv(AddressResolver addressResolver, String str) throws Exception;

        List<String> resolveTxt(String str) throws Exception;
    }

    interface ResourceResolverFactory {
        @Nullable
        ResourceResolver newResourceResolver();

        @Nullable
        Throwable unavailabilityCause();
    }

    private enum JdkAddressResolver implements AddressResolver {
        INSTANCE;

        public List<InetAddress> resolveAddress(String str) throws UnknownHostException {
            return Collections.unmodifiableList(Arrays.asList(InetAddress.getAllByName(str)));
        }
    }

    static {
        String str = "false";
        JNDI_LOCALHOST_PROPERTY = System.getProperty("io.grpc.internal.DnsNameResolverProvider.enable_jndi_localhost", str);
        JNDI_SRV_PROPERTY = System.getProperty("io.grpc.internal.DnsNameResolverProvider.enable_grpclb", str);
        JNDI_TXT_PROPERTY = System.getProperty("io.grpc.internal.DnsNameResolverProvider.enable_service_config", str);
    }

    DnsNameResolver(@Nullable String str, String str2, Attributes attributes, Resource<ExecutorService> resource, ProxyDetector proxyDetector) {
        this.executorResource = resource;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("//");
        stringBuilder.append((String) Preconditions.checkNotNull(str2, ConditionalUserProperty.NAME));
        Object create = URI.create(stringBuilder.toString());
        Preconditions.checkArgument(create.getHost() != null, "Invalid DNS name: %s", (Object) str2);
        this.authority = (String) Preconditions.checkNotNull(create.getAuthority(), "nameUri (%s) doesn't have an authority", create);
        this.host = create.getHost();
        if (create.getPort() == -1) {
            Integer num = (Integer) attributes.get(Factory.PARAMS_DEFAULT_PORT);
            if (num != null) {
                this.port = num.intValue();
            } else {
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("name '");
                stringBuilder2.append(str2);
                stringBuilder2.append("' doesn't contain a port, and default port is not set in params");
                throw new IllegalArgumentException(stringBuilder2.toString());
            }
        }
        this.port = create.getPort();
        this.proxyDetector = proxyDetector;
    }

    public final String getServiceAuthority() {
        return this.authority;
    }

    public final synchronized void start(Listener listener) {
        Preconditions.checkState(this.listener == null, "already started");
        this.executor = (ExecutorService) SharedResourceHolder.get(this.executorResource);
        this.listener = (Listener) Preconditions.checkNotNull(listener, CastExtraArgs.LISTENER);
        resolve();
    }

    public final synchronized void refresh() {
        Preconditions.checkState(this.listener != null, "not started");
        resolve();
    }

    @GuardedBy("this")
    private void resolve() {
        if (!this.resolving && !this.shutdown) {
            this.executor.execute(this.resolutionRunnable);
        }
    }

    /* JADX WARNING: Missing block: B:12:0x001b, code:
            return;
     */
    public final synchronized void shutdown() {
        /*
        r2 = this;
        monitor-enter(r2);
        r0 = r2.shutdown;	 Catch:{ all -> 0x001c }
        if (r0 == 0) goto L_0x0007;
    L_0x0005:
        monitor-exit(r2);
        return;
    L_0x0007:
        r0 = 1;
        r2.shutdown = r0;	 Catch:{ all -> 0x001c }
        r0 = r2.executor;	 Catch:{ all -> 0x001c }
        if (r0 == 0) goto L_0x001a;
    L_0x000e:
        r0 = r2.executorResource;	 Catch:{ all -> 0x001c }
        r1 = r2.executor;	 Catch:{ all -> 0x001c }
        r0 = io.grpc.internal.SharedResourceHolder.release(r0, r1);	 Catch:{ all -> 0x001c }
        r0 = (java.util.concurrent.ExecutorService) r0;	 Catch:{ all -> 0x001c }
        r2.executor = r0;	 Catch:{ all -> 0x001c }
    L_0x001a:
        monitor-exit(r2);
        return;
    L_0x001c:
        r0 = move-exception;
        monitor-exit(r2);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.grpc.internal.DnsNameResolver.shutdown():void");
    }

    final int getPort() {
        return this.port;
    }

    /* JADX WARNING: Missing block: B:31:0x006c, code:
            if (r1.isEmpty() == false) goto L_0x0095;
     */
    @com.google.common.annotations.VisibleForTesting
    static io.grpc.internal.DnsNameResolver.ResolutionResults resolveAll(io.grpc.internal.DnsNameResolver.AddressResolver r7, @javax.annotation.Nullable io.grpc.internal.DnsNameResolver.ResourceResolver r8, boolean r9, boolean r10, java.lang.String r11) {
        /*
        r0 = java.util.Collections.emptyList();
        r1 = java.util.Collections.emptyList();
        r2 = java.util.Collections.emptyList();
        r3 = 0;
        r0 = r7.resolveAddress(r11);	 Catch:{ Exception -> 0x0013 }
        r4 = r3;
        goto L_0x0014;
    L_0x0013:
        r4 = move-exception;
    L_0x0014:
        if (r8 == 0) goto L_0x005d;
    L_0x0016:
        if (r9 == 0) goto L_0x0030;
    L_0x0018:
        r5 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x002e }
        r5.<init>();	 Catch:{ Exception -> 0x002e }
        r6 = "_grpclb._tcp.";
        r5.append(r6);	 Catch:{ Exception -> 0x002e }
        r5.append(r11);	 Catch:{ Exception -> 0x002e }
        r5 = r5.toString();	 Catch:{ Exception -> 0x002e }
        r1 = r8.resolveSrv(r7, r5);	 Catch:{ Exception -> 0x002e }
        goto L_0x0030;
    L_0x002e:
        r7 = move-exception;
        goto L_0x0031;
    L_0x0030:
        r7 = r3;
    L_0x0031:
        if (r10 == 0) goto L_0x005e;
    L_0x0033:
        r10 = 0;
        r5 = 1;
        if (r9 == 0) goto L_0x003c;
    L_0x0037:
        if (r7 == 0) goto L_0x003a;
    L_0x0039:
        goto L_0x003c;
    L_0x003a:
        r9 = 0;
        goto L_0x003d;
    L_0x003c:
        r9 = 1;
    L_0x003d:
        if (r4 == 0) goto L_0x0042;
    L_0x003f:
        if (r9 == 0) goto L_0x0042;
    L_0x0041:
        r10 = 1;
    L_0x0042:
        if (r10 != 0) goto L_0x005e;
    L_0x0044:
        r9 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x005a }
        r9.<init>();	 Catch:{ Exception -> 0x005a }
        r10 = "_grpc_config.";
        r9.append(r10);	 Catch:{ Exception -> 0x005a }
        r9.append(r11);	 Catch:{ Exception -> 0x005a }
        r9 = r9.toString();	 Catch:{ Exception -> 0x005a }
        r2 = r8.resolveTxt(r9);	 Catch:{ Exception -> 0x005a }
        goto L_0x005e;
    L_0x005a:
        r8 = move-exception;
        r3 = r8;
        goto L_0x005e;
    L_0x005d:
        r7 = r3;
    L_0x005e:
        r8 = "ServiceConfig resolution failure";
        r9 = "Balancer resolution failure";
        r10 = "Address resolution failure";
        if (r4 == 0) goto L_0x0095;
    L_0x0066:
        if (r7 != 0) goto L_0x006f;
    L_0x0068:
        r11 = r1.isEmpty();	 Catch:{ all -> 0x0078 }
        if (r11 != 0) goto L_0x006f;
    L_0x006e:
        goto L_0x0095;
    L_0x006f:
        com.google.common.base.Throwables.throwIfUnchecked(r4);	 Catch:{ all -> 0x0078 }
        r11 = new java.lang.RuntimeException;	 Catch:{ all -> 0x0078 }
        r11.<init>(r4);	 Catch:{ all -> 0x0078 }
        throw r11;	 Catch:{ all -> 0x0078 }
    L_0x0078:
        r11 = move-exception;
        if (r4 == 0) goto L_0x0082;
    L_0x007b:
        r0 = logger;
        r1 = java.util.logging.Level.FINE;
        r0.log(r1, r10, r4);
    L_0x0082:
        if (r7 == 0) goto L_0x008b;
    L_0x0084:
        r10 = logger;
        r0 = java.util.logging.Level.FINE;
        r10.log(r0, r9, r7);
    L_0x008b:
        if (r3 == 0) goto L_0x0094;
    L_0x008d:
        r7 = logger;
        r9 = java.util.logging.Level.FINE;
        r7.log(r9, r8, r3);
    L_0x0094:
        throw r11;
    L_0x0095:
        if (r4 == 0) goto L_0x009e;
    L_0x0097:
        r11 = logger;
        r5 = java.util.logging.Level.FINE;
        r11.log(r5, r10, r4);
    L_0x009e:
        if (r7 == 0) goto L_0x00a7;
    L_0x00a0:
        r10 = logger;
        r11 = java.util.logging.Level.FINE;
        r10.log(r11, r9, r7);
    L_0x00a7:
        if (r3 == 0) goto L_0x00b0;
    L_0x00a9:
        r7 = logger;
        r9 = java.util.logging.Level.FINE;
        r7.log(r9, r8, r3);
    L_0x00b0:
        r7 = new io.grpc.internal.DnsNameResolver$ResolutionResults;
        r7.<init>(r0, r2, r1);
        return r7;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.grpc.internal.DnsNameResolver.resolveAll(io.grpc.internal.DnsNameResolver$AddressResolver, io.grpc.internal.DnsNameResolver$ResourceResolver, boolean, boolean, java.lang.String):io.grpc.internal.DnsNameResolver$ResolutionResults");
    }

    @VisibleForTesting
    static List<Map<String, Object>> parseTxtResults(List<String> list) {
        List<Map<String, Object>> arrayList = new ArrayList();
        for (String str : list) {
            if (str.startsWith(SERVICE_CONFIG_PREFIX)) {
                try {
                    Object parse = JsonParser.parse(str.substring(13));
                    StringBuilder stringBuilder;
                    if (parse instanceof List) {
                        List<Object> list2 = (List) parse;
                        for (Object obj : list2) {
                            if (!(obj instanceof Map)) {
                                stringBuilder = new StringBuilder();
                                stringBuilder.append("wrong element type ");
                                stringBuilder.append(parse);
                                throw new IOException(stringBuilder.toString());
                            }
                        }
                        arrayList.addAll(list2);
                    } else {
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("wrong type ");
                        stringBuilder.append(parse);
                        throw new IOException(stringBuilder.toString());
                    }
                } catch (Throwable e) {
                    Logger logger = logger;
                    Level level = Level.WARNING;
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("Bad service config: ");
                    stringBuilder2.append(str);
                    logger.log(level, stringBuilder2.toString(), e);
                }
            } else {
                logger.log(Level.FINE, "Ignoring non service config {0}", new Object[]{str});
            }
        }
        return arrayList;
    }

    @Nullable
    private static final Double getPercentageFromChoice(Map<String, Object> map) {
        String str = SERVICE_CONFIG_CHOICE_PERCENTAGE_KEY;
        if (map.containsKey(str)) {
            return ServiceConfigUtil.getDouble(map, str);
        }
        return null;
    }

    @Nullable
    private static final List<String> getClientLanguagesFromChoice(Map<String, Object> map) {
        String str = SERVICE_CONFIG_CHOICE_CLIENT_LANGUAGE_KEY;
        if (map.containsKey(str)) {
            return ServiceConfigUtil.checkStringList(ServiceConfigUtil.getList(map, str));
        }
        return null;
    }

    @Nullable
    private static final List<String> getHostnamesFromChoice(Map<String, Object> map) {
        String str = SERVICE_CONFIG_CHOICE_CLIENT_HOSTNAME_KEY;
        if (map.containsKey(str)) {
            return ServiceConfigUtil.checkStringList(ServiceConfigUtil.getList(map, str));
        }
        return null;
    }

    @Nullable
    @VisibleForTesting
    static Map<String, Object> maybeChooseServiceConfig(Map<String, Object> map, Random random, String str) {
        Object obj;
        for (Object obj2 : map.entrySet()) {
            Verify.verify(SERVICE_CONFIG_CHOICE_KEYS.contains(obj2.getKey()), "Bad key: %s", obj2);
        }
        List<String> clientLanguagesFromChoice = getClientLanguagesFromChoice(map);
        Object obj3 = 1;
        if (!(clientLanguagesFromChoice == null || clientLanguagesFromChoice.isEmpty())) {
            for (String equalsIgnoreCase : clientLanguagesFromChoice) {
                if ("java".equalsIgnoreCase(equalsIgnoreCase)) {
                    obj = 1;
                    break;
                }
            }
            obj = null;
            if (obj == null) {
                return null;
            }
        }
        obj = getPercentageFromChoice(map);
        if (obj != null) {
            int intValue = obj.intValue();
            boolean z = intValue >= 0 && intValue <= 100;
            Verify.verify(z, "Bad percentage: %s", obj);
            if (random.nextInt(100) >= intValue) {
                return null;
            }
        }
        List<String> hostnamesFromChoice = getHostnamesFromChoice(map);
        if (!(hostnamesFromChoice == null || hostnamesFromChoice.isEmpty())) {
            for (String equals : hostnamesFromChoice) {
                if (equals.equals(str)) {
                    break;
                }
            }
            obj3 = null;
            if (obj3 == null) {
                return null;
            }
        }
        return ServiceConfigUtil.getObject(map, SERVICE_CONFIG_CHOICE_SERVICE_CONFIG_KEY);
    }

    @VisibleForTesting
    void setAddressResolver(AddressResolver addressResolver) {
        this.addressResolver = addressResolver;
    }

    @Nullable
    private ResourceResolver getResourceResolver() {
        ResourceResolver resourceResolver = (ResourceResolver) this.resourceResolver.get();
        if (resourceResolver != null) {
            return resourceResolver;
        }
        ResourceResolverFactory resourceResolverFactory = resourceResolverFactory;
        return resourceResolverFactory != null ? resourceResolverFactory.newResourceResolver() : resourceResolver;
    }

    @Nullable
    @VisibleForTesting
    static ResourceResolverFactory getResourceResolverFactory(ClassLoader classLoader) {
        try {
            try {
                try {
                    ResourceResolverFactory resourceResolverFactory = (ResourceResolverFactory) Class.forName("io.grpc.internal.JndiResourceResolverFactory", true, classLoader).asSubclass(ResourceResolverFactory.class).getConstructor(new Class[0]).newInstance(new Object[0]);
                    if (resourceResolverFactory.unavailabilityCause() != null) {
                        logger.log(Level.FINE, "JndiResourceResolverFactory not available, skipping.", resourceResolverFactory.unavailabilityCause());
                    }
                    return resourceResolverFactory;
                } catch (Throwable e) {
                    logger.log(Level.FINE, "Can't construct JndiResourceResolverFactory, skipping.", e);
                    return null;
                }
            } catch (Throwable e2) {
                logger.log(Level.FINE, "Can't find JndiResourceResolverFactory ctor, skipping.", e2);
                return null;
            }
        } catch (Throwable e22) {
            logger.log(Level.FINE, "Unable to find JndiResourceResolverFactory, skipping.", e22);
            return null;
        }
    }

    private static String getLocalHostname() {
        if (localHostname == null) {
            try {
                localHostname = InetAddress.getLocalHost().getHostName();
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }
        return localHostname;
    }

    @VisibleForTesting
    static boolean shouldUseJndi(boolean z, boolean z2, String str) {
        if (!z) {
            return false;
        }
        if (AndroidInfoHelpers.DEVICE_LOCALHOST.equalsIgnoreCase(str)) {
            return z2;
        }
        if (str.contains(":")) {
            return false;
        }
        z = true;
        int i = 1;
        for (int i2 = 0; i2 < str.length(); i2++) {
            char charAt = str.charAt(i2);
            if (charAt != '.') {
                int i3 = (charAt < '0' || charAt > '9') ? 0 : 1;
                i &= i3;
            }
        }
        if (i != 0) {
            z = false;
        }
        return z;
    }
}
