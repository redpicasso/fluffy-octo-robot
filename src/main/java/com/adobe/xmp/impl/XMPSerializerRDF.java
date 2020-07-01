package com.adobe.xmp.impl;

import com.adobe.xmp.XMPConst;
import com.adobe.xmp.XMPError;
import com.adobe.xmp.XMPException;
import com.adobe.xmp.XMPMeta;
import com.adobe.xmp.XMPMetaFactory;
import com.adobe.xmp.XMPSchemaRegistry;
import com.adobe.xmp.options.SerializeOptions;
import com.drew.metadata.photoshop.PhotoshopDirectory;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class XMPSerializerRDF {
    private static final int DEFAULT_PAD = 2048;
    private static final String PACKET_HEADER = "<?xpacket begin=\"﻿\" id=\"W5M0MpCehiHzreSzNTczkc9d\"?>";
    private static final String PACKET_TRAILER = "<?xpacket end=\"";
    private static final String PACKET_TRAILER2 = "\"?>";
    static final Set RDF_ATTR_QUALIFIER = new HashSet(Arrays.asList(new String[]{XMPConst.XML_LANG, "rdf:resource", "rdf:ID", "rdf:bagID", "rdf:nodeID"}));
    private static final String RDF_EMPTY_STRUCT = "<rdf:Description/>";
    private static final String RDF_RDF_END = "</rdf:RDF>";
    private static final String RDF_RDF_START = "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\">";
    private static final String RDF_SCHEMA_END = "</rdf:Description>";
    private static final String RDF_SCHEMA_START = "<rdf:Description rdf:about=";
    private static final String RDF_STRUCT_END = "</rdf:Description>";
    private static final String RDF_STRUCT_START = "<rdf:Description";
    private static final String RDF_XMPMETA_END = "</x:xmpmeta>";
    private static final String RDF_XMPMETA_START = "<x:xmpmeta xmlns:x=\"adobe:ns:meta/\" x:xmptk=\"";
    private SerializeOptions options;
    private CountOutputStream outputStream;
    private int padding;
    private int unicodeSize = 1;
    private OutputStreamWriter writer;
    private XMPMetaImpl xmp;

    private void addPadding(int i) throws XMPException, IOException {
        int bytesWritten;
        if (this.options.getExactPacketLength()) {
            bytesWritten = this.outputStream.getBytesWritten() + (i * this.unicodeSize);
            i = this.padding;
            if (bytesWritten <= i) {
                this.padding = i - bytesWritten;
            } else {
                throw new XMPException("Can't fit into specified packet size", 107);
            }
        }
        this.padding /= this.unicodeSize;
        i = this.options.getNewline().length();
        bytesWritten = this.padding;
        if (bytesWritten >= i) {
            bytesWritten -= i;
            while (true) {
                this.padding = bytesWritten;
                bytesWritten = this.padding;
                int i2 = i + 100;
                if (bytesWritten >= i2) {
                    writeChars(100, ' ');
                    writeNewline();
                    bytesWritten = this.padding - i2;
                } else {
                    writeChars(bytesWritten, ' ');
                    writeNewline();
                    return;
                }
            }
        }
        writeChars(bytesWritten, ' ');
    }

    private void appendNodeValue(String str, boolean z) throws IOException {
        if (str == null) {
            str = "";
        }
        write(Utils.escapeXML(str, z, true));
    }

    private boolean canBeRDFAttrProp(XMPNode xMPNode) {
        if (!(xMPNode.hasQualifier() || xMPNode.getOptions().isURI() || xMPNode.getOptions().isCompositeProperty())) {
            if (!XMPConst.ARRAY_ITEM_NAME.equals(xMPNode.getName())) {
                return true;
            }
        }
        return false;
    }

    private void declareNamespace(String str, String str2, Set set, int i) throws IOException {
        if (str2 == null) {
            QName qName = new QName(str);
            if (qName.hasPrefix()) {
                str = qName.getPrefix();
                XMPSchemaRegistry schemaRegistry = XMPMetaFactory.getSchemaRegistry();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(str);
                stringBuilder.append(":");
                str2 = schemaRegistry.getNamespaceURI(stringBuilder.toString());
                declareNamespace(str, str2, set, i);
            } else {
                return;
            }
        }
        if (!set.contains(str)) {
            writeNewline();
            writeIndent(i);
            write("xmlns:");
            write(str);
            write("=\"");
            write(str2);
            write(34);
            set.add(str);
        }
    }

    private void declareUsedNamespaces(XMPNode xMPNode, Set set, int i) throws IOException {
        Iterator iterateChildren;
        if (xMPNode.getOptions().isSchemaNode()) {
            declareNamespace(xMPNode.getValue().substring(0, xMPNode.getValue().length() - 1), xMPNode.getName(), set, i);
        } else if (xMPNode.getOptions().isStruct()) {
            iterateChildren = xMPNode.iterateChildren();
            while (iterateChildren.hasNext()) {
                declareNamespace(((XMPNode) iterateChildren.next()).getName(), null, set, i);
            }
        }
        iterateChildren = xMPNode.iterateChildren();
        while (iterateChildren.hasNext()) {
            declareUsedNamespaces((XMPNode) iterateChildren.next(), set, i);
        }
        Iterator iterateQualifier = xMPNode.iterateQualifier();
        while (iterateQualifier.hasNext()) {
            XMPNode xMPNode2 = (XMPNode) iterateQualifier.next();
            declareNamespace(xMPNode2.getName(), null, set, i);
            declareUsedNamespaces(xMPNode2, set, i);
        }
    }

    private void emitRDFArrayTag(XMPNode xMPNode, boolean z, int i) throws IOException {
        if (z || xMPNode.hasChildren()) {
            writeIndent(i);
            write(z ? "<rdf:" : "</rdf:");
            String str = xMPNode.getOptions().isArrayAlternate() ? "Alt" : xMPNode.getOptions().isArrayOrdered() ? "Seq" : "Bag";
            write(str);
            String str2 = (!z || xMPNode.hasChildren()) ? ">" : "/>";
            write(str2);
            writeNewline();
        }
    }

    private void endOuterRDFDescription(int i) throws IOException {
        writeIndent(i + 1);
        write("</rdf:Description>");
        writeNewline();
    }

    private String serializeAsRDF() throws IOException, XMPException {
        int i = 0;
        if (!this.options.getOmitPacketWrapper()) {
            writeIndent(0);
            write(PACKET_HEADER);
            writeNewline();
        }
        if (!this.options.getOmitXmpMetaElement()) {
            writeIndent(0);
            write(RDF_XMPMETA_START);
            if (!this.options.getOmitVersionAttribute()) {
                write(XMPMetaFactory.getVersionInfo().getMessage());
            }
            write("\">");
            writeNewline();
            i = 1;
        }
        writeIndent(i);
        write(RDF_RDF_START);
        writeNewline();
        if (this.options.getUseCanonicalFormat()) {
            serializeCanonicalRDFSchemas(i);
        } else {
            serializeCompactRDFSchemas(i);
        }
        writeIndent(i);
        write(RDF_RDF_END);
        writeNewline();
        if (!this.options.getOmitXmpMetaElement()) {
            writeIndent(i - 1);
            write(RDF_XMPMETA_END);
            writeNewline();
        }
        String str = "";
        if (this.options.getOmitPacketWrapper()) {
            return str;
        }
        for (i = this.options.getBaseIndent(); i > 0; i--) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(str);
            stringBuilder.append(this.options.getIndent());
            str = stringBuilder.toString();
        }
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(str);
        stringBuilder2.append(PACKET_TRAILER);
        str = stringBuilder2.toString();
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(str);
        stringBuilder2.append(this.options.getReadOnlyPacket() ? 'r' : 'w');
        str = stringBuilder2.toString();
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(str);
        stringBuilder2.append(PACKET_TRAILER2);
        return stringBuilder2.toString();
    }

    /* JADX WARNING: Removed duplicated region for block: B:96:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:79:0x020a  */
    /* JADX WARNING: Removed duplicated region for block: B:79:0x020a  */
    /* JADX WARNING: Removed duplicated region for block: B:96:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:96:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:79:0x020a  */
    /* JADX WARNING: Missing block: B:28:0x00c3, code:
            if (r2 != false) goto L_0x00c5;
     */
    /* JADX WARNING: Missing block: B:69:0x01c0, code:
            if (r2 != false) goto L_0x00c5;
     */
    private void serializeCanonicalRDFProperty(com.adobe.xmp.impl.XMPNode r17, boolean r18, boolean r19, int r20) throws java.io.IOException, com.adobe.xmp.XMPException {
        /*
        r16 = this;
        r0 = r16;
        r1 = r17;
        r2 = r18;
        r3 = r20;
        r4 = r17.getName();
        if (r19 == 0) goto L_0x0011;
    L_0x000e:
        r4 = "rdf:value";
        goto L_0x001b;
    L_0x0011:
        r5 = "[]";
        r5 = r5.equals(r4);
        if (r5 == 0) goto L_0x001b;
    L_0x0019:
        r4 = "rdf:li";
    L_0x001b:
        r0.writeIndent(r3);
        r5 = 60;
        r0.write(r5);
        r0.write(r4);
        r5 = r17.iterateQualifier();
        r6 = 0;
        r7 = 0;
        r8 = 0;
    L_0x002d:
        r9 = r5.hasNext();
        r10 = 34;
        r11 = "=\"";
        r12 = 32;
        r13 = 1;
        if (r9 == 0) goto L_0x0072;
    L_0x003a:
        r9 = r5.next();
        r9 = (com.adobe.xmp.impl.XMPNode) r9;
        r14 = RDF_ATTR_QUALIFIER;
        r15 = r9.getName();
        r14 = r14.contains(r15);
        if (r14 != 0) goto L_0x004e;
    L_0x004c:
        r7 = 1;
        goto L_0x002d;
    L_0x004e:
        r8 = r9.getName();
        r14 = "rdf:resource";
        r8 = r14.equals(r8);
        if (r19 != 0) goto L_0x002d;
    L_0x005a:
        r0.write(r12);
        r12 = r9.getName();
        r0.write(r12);
        r0.write(r11);
        r9 = r9.getValue();
        r0.appendNodeValue(r9, r13);
        r0.write(r10);
        goto L_0x002d;
    L_0x0072:
        r5 = "</rdf:Description>";
        r9 = "<rdf:Description";
        r14 = " rdf:parseType=\"Resource\">";
        r15 = 202; // 0xca float:2.83E-43 double:1.0E-321;
        r10 = 62;
        r12 = ">";
        if (r7 == 0) goto L_0x00db;
    L_0x0080:
        if (r19 != 0) goto L_0x00db;
    L_0x0082:
        if (r8 != 0) goto L_0x00d3;
    L_0x0084:
        if (r2 == 0) goto L_0x0098;
    L_0x0086:
        r0.write(r12);
        r16.writeNewline();
        r3 = r3 + 1;
        r0.writeIndent(r3);
        r0.write(r9);
        r0.write(r12);
        goto L_0x009b;
    L_0x0098:
        r0.write(r14);
    L_0x009b:
        r16.writeNewline();
        r7 = r3 + 1;
        r0.serializeCanonicalRDFProperty(r1, r2, r13, r7);
        r1 = r17.iterateQualifier();
    L_0x00a7:
        r8 = r1.hasNext();
        if (r8 == 0) goto L_0x00c3;
    L_0x00ad:
        r8 = r1.next();
        r8 = (com.adobe.xmp.impl.XMPNode) r8;
        r9 = RDF_ATTR_QUALIFIER;
        r11 = r8.getName();
        r9 = r9.contains(r11);
        if (r9 != 0) goto L_0x00a7;
    L_0x00bf:
        r0.serializeCanonicalRDFProperty(r8, r2, r6, r7);
        goto L_0x00a7;
    L_0x00c3:
        if (r2 == 0) goto L_0x0167;
    L_0x00c5:
        r0.writeIndent(r3);
        r0.write(r5);
        r16.writeNewline();
        r1 = r3 + -1;
        r3 = r1;
        goto L_0x0167;
    L_0x00d3:
        r1 = new com.adobe.xmp.XMPException;
        r2 = "Can't mix rdf:resource and general qualifiers";
        r1.<init>(r2, r15);
        throw r1;
    L_0x00db:
        r7 = r17.getOptions();
        r7 = r7.isCompositeProperty();
        r15 = "/>";
        if (r7 != 0) goto L_0x012c;
    L_0x00e7:
        r2 = r17.getOptions();
        r2 = r2.isURI();
        if (r2 == 0) goto L_0x0109;
    L_0x00f1:
        r2 = " rdf:resource=\"";
        r0.write(r2);
        r1 = r17.getValue();
        r0.appendNodeValue(r1, r13);
        r1 = "\"/>";
        r0.write(r1);
    L_0x0102:
        r16.writeNewline();
        r6 = 1;
        r13 = 0;
        goto L_0x0208;
    L_0x0109:
        r2 = r17.getValue();
        if (r2 == 0) goto L_0x0128;
    L_0x010f:
        r2 = r17.getValue();
        r5 = "";
        r2 = r5.equals(r2);
        if (r2 == 0) goto L_0x011c;
    L_0x011b:
        goto L_0x0128;
    L_0x011c:
        r0.write(r10);
        r1 = r17.getValue();
        r0.appendNodeValue(r1, r6);
        goto L_0x0208;
    L_0x0128:
        r0.write(r15);
        goto L_0x0102;
    L_0x012c:
        r7 = r17.getOptions();
        r7 = r7.isArray();
        if (r7 == 0) goto L_0x016a;
    L_0x0136:
        r0.write(r10);
        r16.writeNewline();
        r5 = r3 + 1;
        r0.emitRDFArrayTag(r1, r13, r5);
        r7 = r17.getOptions();
        r7 = r7.isArrayAltText();
        if (r7 == 0) goto L_0x014e;
    L_0x014b:
        com.adobe.xmp.impl.XMPNodeUtils.normalizeLangArray(r17);
    L_0x014e:
        r7 = r17.iterateChildren();
    L_0x0152:
        r8 = r7.hasNext();
        if (r8 == 0) goto L_0x0164;
    L_0x0158:
        r8 = r7.next();
        r8 = (com.adobe.xmp.impl.XMPNode) r8;
        r9 = r3 + 2;
        r0.serializeCanonicalRDFProperty(r8, r2, r6, r9);
        goto L_0x0152;
    L_0x0164:
        r0.emitRDFArrayTag(r1, r6, r5);
    L_0x0167:
        r6 = 1;
        goto L_0x0208;
    L_0x016a:
        if (r8 != 0) goto L_0x01c4;
    L_0x016c:
        r7 = r17.hasChildren();
        if (r7 != 0) goto L_0x0190;
    L_0x0172:
        if (r2 == 0) goto L_0x0186;
    L_0x0174:
        r0.write(r12);
        r16.writeNewline();
        r1 = r3 + 1;
        r0.writeIndent(r1);
        r1 = "<rdf:Description/>";
        r0.write(r1);
        r6 = 1;
        goto L_0x018b;
    L_0x0186:
        r1 = " rdf:parseType=\"Resource\"/>";
        r0.write(r1);
    L_0x018b:
        r16.writeNewline();
        r13 = r6;
        goto L_0x0167;
    L_0x0190:
        if (r2 == 0) goto L_0x01a4;
    L_0x0192:
        r0.write(r12);
        r16.writeNewline();
        r3 = r3 + 1;
        r0.writeIndent(r3);
        r0.write(r9);
        r0.write(r12);
        goto L_0x01a7;
    L_0x01a4:
        r0.write(r14);
    L_0x01a7:
        r16.writeNewline();
        r1 = r17.iterateChildren();
    L_0x01ae:
        r7 = r1.hasNext();
        if (r7 == 0) goto L_0x01c0;
    L_0x01b4:
        r7 = r1.next();
        r7 = (com.adobe.xmp.impl.XMPNode) r7;
        r8 = r3 + 1;
        r0.serializeCanonicalRDFProperty(r7, r2, r6, r8);
        goto L_0x01ae;
    L_0x01c0:
        if (r2 == 0) goto L_0x0167;
    L_0x01c2:
        goto L_0x00c5;
    L_0x01c4:
        r1 = r17.iterateChildren();
    L_0x01c8:
        r2 = r1.hasNext();
        if (r2 == 0) goto L_0x0128;
    L_0x01ce:
        r2 = r1.next();
        r2 = (com.adobe.xmp.impl.XMPNode) r2;
        r5 = r0.canBeRDFAttrProp(r2);
        if (r5 == 0) goto L_0x01fe;
    L_0x01da:
        r16.writeNewline();
        r5 = r3 + 1;
        r0.writeIndent(r5);
        r5 = 32;
        r0.write(r5);
        r7 = r2.getName();
        r0.write(r7);
        r0.write(r11);
        r2 = r2.getValue();
        r0.appendNodeValue(r2, r13);
        r2 = 34;
        r0.write(r2);
        goto L_0x01c8;
    L_0x01fe:
        r1 = new com.adobe.xmp.XMPException;
        r2 = "Can't mix rdf:resource and complex fields";
        r3 = 202; // 0xca float:2.83E-43 double:1.0E-321;
        r1.<init>(r2, r3);
        throw r1;
    L_0x0208:
        if (r13 == 0) goto L_0x021d;
    L_0x020a:
        if (r6 == 0) goto L_0x020f;
    L_0x020c:
        r0.writeIndent(r3);
    L_0x020f:
        r1 = "</";
        r0.write(r1);
        r0.write(r4);
        r0.write(r10);
        r16.writeNewline();
    L_0x021d:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.adobe.xmp.impl.XMPSerializerRDF.serializeCanonicalRDFProperty(com.adobe.xmp.impl.XMPNode, boolean, boolean, int):void");
    }

    private void serializeCanonicalRDFSchema(XMPNode xMPNode, int i) throws IOException, XMPException {
        Iterator iterateChildren = xMPNode.iterateChildren();
        while (iterateChildren.hasNext()) {
            serializeCanonicalRDFProperty((XMPNode) iterateChildren.next(), this.options.getUseCanonicalFormat(), false, i + 2);
        }
    }

    private void serializeCanonicalRDFSchemas(int i) throws IOException, XMPException {
        if (this.xmp.getRoot().getChildrenLength() > 0) {
            startOuterRDFDescription(this.xmp.getRoot(), i);
            Iterator iterateChildren = this.xmp.getRoot().iterateChildren();
            while (iterateChildren.hasNext()) {
                serializeCanonicalRDFSchema((XMPNode) iterateChildren.next(), i);
            }
            endOuterRDFDescription(i);
            return;
        }
        writeIndent(i + 1);
        write(RDF_SCHEMA_START);
        writeTreeName();
        write("/>");
        writeNewline();
    }

    private void serializeCompactRDFArrayProp(XMPNode xMPNode, int i) throws IOException, XMPException {
        write(62);
        writeNewline();
        int i2 = i + 1;
        emitRDFArrayTag(xMPNode, true, i2);
        if (xMPNode.getOptions().isArrayAltText()) {
            XMPNodeUtils.normalizeLangArray(xMPNode);
        }
        serializeCompactRDFElementProps(xMPNode, i + 2);
        emitRDFArrayTag(xMPNode, false, i2);
    }

    private boolean serializeCompactRDFAttrProps(XMPNode xMPNode, int i) throws IOException {
        Iterator iterateChildren = xMPNode.iterateChildren();
        boolean z = true;
        while (iterateChildren.hasNext()) {
            XMPNode xMPNode2 = (XMPNode) iterateChildren.next();
            if (canBeRDFAttrProp(xMPNode2)) {
                writeNewline();
                writeIndent(i);
                write(xMPNode2.getName());
                write("=\"");
                appendNodeValue(xMPNode2.getValue(), true);
                write(34);
            } else {
                z = false;
            }
        }
        return z;
    }

    /* JADX WARNING: Removed duplicated region for block: B:34:0x0004 A:{SYNTHETIC} */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x00b5  */
    private void serializeCompactRDFElementProps(com.adobe.xmp.impl.XMPNode r11, int r12) throws java.io.IOException, com.adobe.xmp.XMPException {
        /*
        r10 = this;
        r11 = r11.iterateChildren();
    L_0x0004:
        r0 = r11.hasNext();
        if (r0 == 0) goto L_0x00cc;
    L_0x000a:
        r0 = r11.next();
        r0 = (com.adobe.xmp.impl.XMPNode) r0;
        r1 = r10.canBeRDFAttrProp(r0);
        if (r1 == 0) goto L_0x0017;
    L_0x0016:
        goto L_0x0004;
    L_0x0017:
        r1 = r0.getName();
        r2 = "[]";
        r2 = r2.equals(r1);
        if (r2 == 0) goto L_0x0025;
    L_0x0023:
        r1 = "rdf:li";
    L_0x0025:
        r10.writeIndent(r12);
        r2 = 60;
        r10.write(r2);
        r10.write(r1);
        r2 = r0.iterateQualifier();
        r3 = 0;
        r4 = 0;
        r5 = 0;
    L_0x0037:
        r6 = r2.hasNext();
        r7 = 1;
        if (r6 == 0) goto L_0x007a;
    L_0x003e:
        r6 = r2.next();
        r6 = (com.adobe.xmp.impl.XMPNode) r6;
        r8 = RDF_ATTR_QUALIFIER;
        r9 = r6.getName();
        r8 = r8.contains(r9);
        if (r8 != 0) goto L_0x0052;
    L_0x0050:
        r4 = 1;
        goto L_0x0037;
    L_0x0052:
        r5 = r6.getName();
        r8 = "rdf:resource";
        r5 = r8.equals(r5);
        r8 = 32;
        r10.write(r8);
        r8 = r6.getName();
        r10.write(r8);
        r8 = "=\"";
        r10.write(r8);
        r6 = r6.getValue();
        r10.appendNodeValue(r6, r7);
        r6 = 34;
        r10.write(r6);
        goto L_0x0037;
    L_0x007a:
        if (r4 == 0) goto L_0x0080;
    L_0x007c:
        r10.serializeCompactRDFGeneralQualifier(r12, r0);
        goto L_0x00ad;
    L_0x0080:
        r2 = r0.getOptions();
        r2 = r2.isCompositeProperty();
        if (r2 != 0) goto L_0x00a0;
    L_0x008a:
        r0 = r10.serializeCompactRDFSimpleProp(r0);
        r2 = r0[r3];
        r2 = (java.lang.Boolean) r2;
        r2 = r2.booleanValue();
        r0 = r0[r7];
        r0 = (java.lang.Boolean) r0;
        r7 = r0.booleanValue();
        r0 = r2;
        goto L_0x00b3;
    L_0x00a0:
        r2 = r0.getOptions();
        r2 = r2.isArray();
        if (r2 == 0) goto L_0x00af;
    L_0x00aa:
        r10.serializeCompactRDFArrayProp(r0, r12);
    L_0x00ad:
        r0 = 1;
        goto L_0x00b3;
    L_0x00af:
        r0 = r10.serializeCompactRDFStructProp(r0, r12, r5);
    L_0x00b3:
        if (r0 == 0) goto L_0x0004;
    L_0x00b5:
        if (r7 == 0) goto L_0x00ba;
    L_0x00b7:
        r10.writeIndent(r12);
    L_0x00ba:
        r0 = "</";
        r10.write(r0);
        r10.write(r1);
        r0 = 62;
        r10.write(r0);
        r10.writeNewline();
        goto L_0x0004;
    L_0x00cc:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.adobe.xmp.impl.XMPSerializerRDF.serializeCompactRDFElementProps(com.adobe.xmp.impl.XMPNode, int):void");
    }

    private void serializeCompactRDFGeneralQualifier(int i, XMPNode xMPNode) throws IOException, XMPException {
        write(" rdf:parseType=\"Resource\">");
        writeNewline();
        i++;
        serializeCanonicalRDFProperty(xMPNode, false, true, i);
        Iterator iterateQualifier = xMPNode.iterateQualifier();
        while (iterateQualifier.hasNext()) {
            serializeCanonicalRDFProperty((XMPNode) iterateQualifier.next(), false, false, i);
        }
    }

    private void serializeCompactRDFSchemas(int i) throws IOException, XMPException {
        String str;
        int i2 = i + 1;
        writeIndent(i2);
        write(RDF_SCHEMA_START);
        writeTreeName();
        Set hashSet = new HashSet();
        hashSet.add("xml");
        hashSet.add("rdf");
        Iterator iterateChildren = this.xmp.getRoot().iterateChildren();
        while (iterateChildren.hasNext()) {
            declareUsedNamespaces((XMPNode) iterateChildren.next(), hashSet, i + 3);
        }
        Iterator iterateChildren2 = this.xmp.getRoot().iterateChildren();
        int i3 = 1;
        while (iterateChildren2.hasNext()) {
            i3 &= serializeCompactRDFAttrProps((XMPNode) iterateChildren2.next(), i + 2);
        }
        if (i3 == 0) {
            write(62);
            writeNewline();
            iterateChildren2 = this.xmp.getRoot().iterateChildren();
            while (iterateChildren2.hasNext()) {
                serializeCompactRDFElementProps((XMPNode) iterateChildren2.next(), i + 2);
            }
            writeIndent(i2);
            str = "</rdf:Description>";
        } else {
            str = "/>";
        }
        write(str);
        writeNewline();
    }

    private Object[] serializeCompactRDFSimpleProp(XMPNode xMPNode) throws IOException {
        String str;
        Boolean bool = Boolean.TRUE;
        Boolean bool2 = Boolean.TRUE;
        if (xMPNode.getOptions().isURI()) {
            write(" rdf:resource=\"");
            appendNodeValue(xMPNode.getValue(), true);
            str = "\"/>";
        } else if (xMPNode.getValue() == null || xMPNode.getValue().length() == 0) {
            str = "/>";
        } else {
            write(62);
            appendNodeValue(xMPNode.getValue(), false);
            bool2 = Boolean.FALSE;
            return new Object[]{bool, bool2};
        }
        write(str);
        writeNewline();
        bool = Boolean.FALSE;
        return new Object[]{bool, bool2};
    }

    private boolean serializeCompactRDFStructProp(XMPNode xMPNode, int i, boolean z) throws XMPException, IOException {
        Iterator iterateChildren = xMPNode.iterateChildren();
        Object obj = null;
        Object obj2 = null;
        while (iterateChildren.hasNext()) {
            if (canBeRDFAttrProp((XMPNode) iterateChildren.next())) {
                obj = 1;
            } else {
                obj2 = 1;
            }
            if (obj != null && r3 != null) {
                break;
            }
        }
        if (!z || obj2 == null) {
            String str;
            if (!xMPNode.hasChildren()) {
                str = " rdf:parseType=\"Resource\"/>";
            } else if (obj2 == null) {
                serializeCompactRDFAttrProps(xMPNode, i + 1);
                str = "/>";
            } else {
                if (obj == null) {
                    write(" rdf:parseType=\"Resource\">");
                    writeNewline();
                    serializeCompactRDFElementProps(xMPNode, i + 1);
                } else {
                    write(62);
                    writeNewline();
                    int i2 = i + 1;
                    writeIndent(i2);
                    write(RDF_STRUCT_START);
                    serializeCompactRDFAttrProps(xMPNode, i + 2);
                    write(">");
                    writeNewline();
                    serializeCompactRDFElementProps(xMPNode, i2);
                    writeIndent(i2);
                    write("</rdf:Description>");
                    writeNewline();
                }
                return true;
            }
            write(str);
            writeNewline();
            return false;
        }
        throw new XMPException("Can't mix rdf:resource qualifier and element fields", XMPError.BADRDF);
    }

    private void startOuterRDFDescription(XMPNode xMPNode, int i) throws IOException {
        writeIndent(i + 1);
        write(RDF_SCHEMA_START);
        writeTreeName();
        Set hashSet = new HashSet();
        hashSet.add("xml");
        hashSet.add("rdf");
        declareUsedNamespaces(xMPNode, hashSet, i + 3);
        write(62);
        writeNewline();
    }

    private void write(int i) throws IOException {
        this.writer.write(i);
    }

    private void write(String str) throws IOException {
        this.writer.write(str);
    }

    private void writeChars(int i, char c) throws IOException {
        while (i > 0) {
            this.writer.write(c);
            i--;
        }
    }

    private void writeIndent(int i) throws IOException {
        for (int baseIndent = this.options.getBaseIndent() + i; baseIndent > 0; baseIndent--) {
            this.writer.write(this.options.getIndent());
        }
    }

    private void writeNewline() throws IOException {
        this.writer.write(this.options.getNewline());
    }

    private void writeTreeName() throws IOException {
        write(34);
        String name = this.xmp.getRoot().getName();
        if (name != null) {
            appendNodeValue(name, true);
        }
        write(34);
    }

    protected void checkOptionsConsistence() throws XMPException {
        if ((this.options.getEncodeUTF16BE() | this.options.getEncodeUTF16LE()) != 0) {
            this.unicodeSize = 2;
        }
        if (!this.options.getExactPacketLength()) {
            if (this.options.getReadOnlyPacket()) {
                if ((this.options.getOmitPacketWrapper() | this.options.getIncludeThumbnailPad()) != 0) {
                    throw new XMPException("Inconsistent options for read-only packet", 103);
                }
            } else if (!this.options.getOmitPacketWrapper()) {
                if (this.padding == 0) {
                    this.padding = this.unicodeSize * 2048;
                }
                if (this.options.getIncludeThumbnailPad() && !this.xmp.doesPropertyExist("http://ns.adobe.com/xap/1.0/", "Thumbnails")) {
                    this.padding += this.unicodeSize * PhotoshopDirectory.TAG_PRINT_FLAGS_INFO;
                    return;
                }
                return;
            } else if (this.options.getIncludeThumbnailPad()) {
                throw new XMPException("Inconsistent options for non-packet serialize", 103);
            }
            this.padding = 0;
        } else if ((this.options.getOmitPacketWrapper() | this.options.getIncludeThumbnailPad()) != 0) {
            throw new XMPException("Inconsistent options for exact size serialize", 103);
        } else if ((this.options.getPadding() & (this.unicodeSize - 1)) != 0) {
            throw new XMPException("Exact size must be a multiple of the Unicode element", 103);
        }
    }

    public void serialize(XMPMeta xMPMeta, OutputStream outputStream, SerializeOptions serializeOptions) throws XMPException {
        try {
            this.outputStream = new CountOutputStream(outputStream);
            this.writer = new OutputStreamWriter(this.outputStream, serializeOptions.getEncoding());
            this.xmp = (XMPMetaImpl) xMPMeta;
            this.options = serializeOptions;
            this.padding = serializeOptions.getPadding();
            this.writer = new OutputStreamWriter(this.outputStream, serializeOptions.getEncoding());
            checkOptionsConsistence();
            String serializeAsRDF = serializeAsRDF();
            this.writer.flush();
            addPadding(serializeAsRDF.length());
            write(serializeAsRDF);
            this.writer.flush();
            this.outputStream.close();
        } catch (IOException unused) {
            throw new XMPException("Error writing to the OutputStream", 0);
        }
    }
}
