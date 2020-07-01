package com.adobe.xmp.impl;

import com.adobe.xmp.XMPConst;
import com.adobe.xmp.XMPError;
import com.adobe.xmp.XMPException;
import com.adobe.xmp.XMPMetaFactory;
import com.adobe.xmp.XMPSchemaRegistry;
import com.adobe.xmp.options.PropertyOptions;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.w3c.dom.Attr;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class ParseRDF implements XMPError, XMPConst {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final String DEFAULT_PREFIX = "_dflt";
    public static final int RDFTERM_ABOUT = 3;
    public static final int RDFTERM_ABOUT_EACH = 10;
    public static final int RDFTERM_ABOUT_EACH_PREFIX = 11;
    public static final int RDFTERM_BAG_ID = 12;
    public static final int RDFTERM_DATATYPE = 7;
    public static final int RDFTERM_DESCRIPTION = 8;
    public static final int RDFTERM_FIRST_CORE = 1;
    public static final int RDFTERM_FIRST_OLD = 10;
    public static final int RDFTERM_FIRST_SYNTAX = 1;
    public static final int RDFTERM_ID = 2;
    public static final int RDFTERM_LAST_CORE = 7;
    public static final int RDFTERM_LAST_OLD = 12;
    public static final int RDFTERM_LAST_SYNTAX = 9;
    public static final int RDFTERM_LI = 9;
    public static final int RDFTERM_NODE_ID = 6;
    public static final int RDFTERM_OTHER = 0;
    public static final int RDFTERM_PARSE_TYPE = 4;
    public static final int RDFTERM_RDF = 1;
    public static final int RDFTERM_RESOURCE = 5;

    private static XMPNode addChildNode(XMPMetaImpl xMPMetaImpl, XMPNode xMPNode, Node node, String str, boolean z) throws XMPException {
        XMPSchemaRegistry schemaRegistry = XMPMetaFactory.getSchemaRegistry();
        String namespaceURI = node.getNamespaceURI();
        if (namespaceURI != null) {
            if (XMPConst.NS_DC_DEPRECATED.equals(namespaceURI)) {
                namespaceURI = "http://purl.org/dc/elements/1.1/";
            }
            String namespacePrefix = schemaRegistry.getNamespacePrefix(namespaceURI);
            String str2 = DEFAULT_PREFIX;
            if (namespacePrefix == null) {
                namespacePrefix = schemaRegistry.registerNamespace(namespaceURI, node.getPrefix() != null ? node.getPrefix() : str2);
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(namespacePrefix);
            stringBuilder.append(node.getLocalName());
            String stringBuilder2 = stringBuilder.toString();
            PropertyOptions propertyOptions = new PropertyOptions();
            boolean z2 = false;
            if (z) {
                xMPNode = XMPNodeUtils.findSchemaNode(xMPMetaImpl.getRoot(), namespaceURI, str2, true);
                xMPNode.setImplicit(false);
                if (schemaRegistry.findAlias(stringBuilder2) != null) {
                    xMPMetaImpl.getRoot().setHasAliases(true);
                    xMPNode.setHasAliases(true);
                    z2 = true;
                }
            }
            boolean equals = "rdf:li".equals(stringBuilder2);
            boolean equals2 = "rdf:value".equals(stringBuilder2);
            XMPNode xMPNode2 = new XMPNode(stringBuilder2, str, propertyOptions);
            xMPNode2.setAlias(z2);
            if (equals2) {
                xMPNode.addChild(1, xMPNode2);
            } else {
                xMPNode.addChild(xMPNode2);
            }
            if (equals2) {
                if (z || !xMPNode.getOptions().isStruct()) {
                    throw new XMPException("Misplaced rdf:value element", XMPError.BADRDF);
                }
                xMPNode.setHasValueChild(true);
            }
            if (equals) {
                if (xMPNode.getOptions().isArray()) {
                    xMPNode2.setName(XMPConst.ARRAY_ITEM_NAME);
                } else {
                    throw new XMPException("Misplaced rdf:li element", XMPError.BADRDF);
                }
            }
            return xMPNode2;
        }
        throw new XMPException("XML namespace required for all elements and attributes", XMPError.BADRDF);
    }

    private static XMPNode addQualifierNode(XMPNode xMPNode, String str, String str2) throws XMPException {
        if (XMPConst.XML_LANG.equals(str)) {
            str2 = Utils.normalizeLangValue(str2);
        }
        XMPNode xMPNode2 = new XMPNode(str, str2, null);
        xMPNode.addQualifier(xMPNode2);
        return xMPNode2;
    }

    private static void fixupQualifiedNode(XMPNode xMPNode) throws XMPException {
        int i = 1;
        XMPNode child = xMPNode.getChild(1);
        if (child.getOptions().getHasLanguage()) {
            if (xMPNode.getOptions().getHasLanguage()) {
                throw new XMPException("Redundant xml:lang for rdf:value element", XMPError.BADXMP);
            }
            XMPNode qualifier = child.getQualifier(1);
            child.removeQualifier(qualifier);
            xMPNode.addQualifier(qualifier);
        }
        while (i <= child.getQualifierLength()) {
            xMPNode.addQualifier(child.getQualifier(i));
            i++;
        }
        for (i = 2; i <= xMPNode.getChildrenLength(); i++) {
            xMPNode.addQualifier(xMPNode.getChild(i));
        }
        xMPNode.setHasValueChild(false);
        xMPNode.getOptions().setStruct(false);
        xMPNode.getOptions().mergeWith(child.getOptions());
        xMPNode.setValue(child.getValue());
        xMPNode.removeChildren();
        Iterator iterateChildren = child.iterateChildren();
        while (iterateChildren.hasNext()) {
            xMPNode.addChild((XMPNode) iterateChildren.next());
        }
    }

    private static int getRDFTermKind(Node node) {
        String localName = node.getLocalName();
        Object namespaceURI = node.getNamespaceURI();
        String str = "ID";
        String str2 = "about";
        String str3 = XMPConst.NS_RDF;
        if (namespaceURI == null && ((str2.equals(localName) || str.equals(localName)) && (node instanceof Attr) && str3.equals(((Attr) node).getOwnerElement().getNamespaceURI()))) {
            namespaceURI = str3;
        }
        if (str3.equals(namespaceURI)) {
            if ("li".equals(localName)) {
                return 9;
            }
            if ("parseType".equals(localName)) {
                return 4;
            }
            if ("Description".equals(localName)) {
                return 8;
            }
            if (str2.equals(localName)) {
                return 3;
            }
            if ("resource".equals(localName)) {
                return 5;
            }
            if ("RDF".equals(localName)) {
                return 1;
            }
            if (str.equals(localName)) {
                return 2;
            }
            if ("nodeID".equals(localName)) {
                return 6;
            }
            if ("datatype".equals(localName)) {
                return 7;
            }
            if ("aboutEach".equals(localName)) {
                return 10;
            }
            if ("aboutEachPrefix".equals(localName)) {
                return 11;
            }
            if ("bagID".equals(localName)) {
                return 12;
            }
        }
        return 0;
    }

    private static boolean isCoreSyntaxTerm(int i) {
        return 1 <= i && i <= 7;
    }

    private static boolean isOldTerm(int i) {
        return 10 <= i && i <= 12;
    }

    private static boolean isPropertyElementName(int i) {
        return (i == 8 || isOldTerm(i)) ? false : isCoreSyntaxTerm(i) ^ 1;
    }

    private static boolean isWhitespaceNode(Node node) {
        if (node.getNodeType() != (short) 3) {
            return false;
        }
        String nodeValue = node.getNodeValue();
        for (int i = 0; i < nodeValue.length(); i++) {
            if (!Character.isWhitespace(nodeValue.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    static XMPMetaImpl parse(Node node) throws XMPException {
        XMPMetaImpl xMPMetaImpl = new XMPMetaImpl();
        rdf_RDF(xMPMetaImpl, node);
        return xMPMetaImpl;
    }

    /* JADX WARNING: Removed duplicated region for block: B:59:0x00ee  */
    private static void rdf_EmptyPropertyElement(com.adobe.xmp.impl.XMPMetaImpl r16, com.adobe.xmp.impl.XMPNode r17, org.w3c.dom.Node r18, boolean r19) throws com.adobe.xmp.XMPException {
        /*
        r0 = r16;
        r1 = r18.hasChildNodes();
        r2 = 202; // 0xca float:2.83E-43 double:1.0E-321;
        if (r1 != 0) goto L_0x015b;
    L_0x000a:
        r3 = 0;
        r7 = r3;
        r3 = 0;
        r4 = 0;
        r5 = 0;
        r6 = 0;
        r8 = 0;
    L_0x0011:
        r9 = r18.getAttributes();
        r9 = r9.getLength();
        r10 = "Unrecognized attribute of empty property element";
        r11 = 6;
        r12 = 5;
        r13 = 2;
        r14 = "xml:lang";
        r15 = "xmlns";
        if (r3 >= r9) goto L_0x00b3;
    L_0x0024:
        r9 = r18.getAttributes();
        r9 = r9.item(r3);
        r1 = r9.getPrefix();
        r1 = r15.equals(r1);
        if (r1 != 0) goto L_0x00af;
    L_0x0036:
        r1 = r9.getPrefix();
        if (r1 != 0) goto L_0x0048;
    L_0x003c:
        r1 = r9.getNodeName();
        r1 = r15.equals(r1);
        if (r1 == 0) goto L_0x0048;
    L_0x0046:
        goto L_0x00af;
    L_0x0048:
        r1 = getRDFTermKind(r9);
        r15 = "Empty property element can't have both rdf:value and rdf:resource";
        if (r1 == 0) goto L_0x007f;
    L_0x0050:
        if (r1 == r13) goto L_0x00af;
    L_0x0052:
        r13 = "Empty property element can't have both rdf:resource and rdf:nodeID";
        if (r1 == r12) goto L_0x0068;
    L_0x0056:
        if (r1 != r11) goto L_0x0062;
    L_0x0058:
        if (r5 != 0) goto L_0x005c;
    L_0x005a:
        r8 = 1;
        goto L_0x00af;
    L_0x005c:
        r0 = new com.adobe.xmp.XMPException;
        r0.<init>(r13, r2);
        throw r0;
    L_0x0062:
        r0 = new com.adobe.xmp.XMPException;
        r0.<init>(r10, r2);
        throw r0;
    L_0x0068:
        if (r8 != 0) goto L_0x0079;
    L_0x006a:
        if (r4 != 0) goto L_0x0071;
    L_0x006c:
        if (r4 != 0) goto L_0x006f;
    L_0x006e:
        r7 = r9;
    L_0x006f:
        r5 = 1;
        goto L_0x00af;
    L_0x0071:
        r0 = new com.adobe.xmp.XMPException;
        r1 = 203; // 0xcb float:2.84E-43 double:1.003E-321;
        r0.<init>(r15, r1);
        throw r0;
    L_0x0079:
        r0 = new com.adobe.xmp.XMPException;
        r0.<init>(r13, r2);
        throw r0;
    L_0x007f:
        r1 = r9.getLocalName();
        r10 = "value";
        r1 = r10.equals(r1);
        if (r1 == 0) goto L_0x00a4;
    L_0x008b:
        r1 = r9.getNamespaceURI();
        r10 = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
        r1 = r10.equals(r1);
        if (r1 == 0) goto L_0x00a4;
    L_0x0097:
        if (r5 != 0) goto L_0x009c;
    L_0x0099:
        r7 = r9;
        r4 = 1;
        goto L_0x00af;
    L_0x009c:
        r0 = new com.adobe.xmp.XMPException;
        r1 = 203; // 0xcb float:2.84E-43 double:1.003E-321;
        r0.<init>(r15, r1);
        throw r0;
    L_0x00a4:
        r1 = r9.getNodeName();
        r1 = r14.equals(r1);
        if (r1 != 0) goto L_0x00af;
    L_0x00ae:
        r6 = 1;
    L_0x00af:
        r3 = r3 + 1;
        goto L_0x0011;
    L_0x00b3:
        r1 = "";
        r3 = r17;
        r8 = r18;
        r9 = r19;
        r3 = addChildNode(r0, r3, r8, r1, r9);
        if (r4 != 0) goto L_0x00cf;
    L_0x00c1:
        if (r5 == 0) goto L_0x00c4;
    L_0x00c3:
        goto L_0x00cf;
    L_0x00c4:
        if (r6 == 0) goto L_0x00e2;
    L_0x00c6:
        r1 = r3.getOptions();
        r5 = 1;
        r1.setStruct(r5);
        goto L_0x00e3;
    L_0x00cf:
        r5 = 1;
        if (r7 == 0) goto L_0x00d6;
    L_0x00d2:
        r1 = r7.getNodeValue();
    L_0x00d6:
        r3.setValue(r1);
        if (r4 != 0) goto L_0x00e2;
    L_0x00db:
        r1 = r3.getOptions();
        r1.setURI(r5);
    L_0x00e2:
        r5 = 0;
    L_0x00e3:
        r1 = 0;
    L_0x00e4:
        r4 = r18.getAttributes();
        r4 = r4.getLength();
        if (r1 >= r4) goto L_0x015a;
    L_0x00ee:
        r4 = r18.getAttributes();
        r4 = r4.item(r1);
        if (r4 == r7) goto L_0x0156;
    L_0x00f8:
        r6 = r4.getPrefix();
        r6 = r15.equals(r6);
        if (r6 != 0) goto L_0x0156;
    L_0x0102:
        r6 = r4.getPrefix();
        if (r6 != 0) goto L_0x0113;
    L_0x0108:
        r6 = r4.getNodeName();
        r6 = r15.equals(r6);
        if (r6 == 0) goto L_0x0113;
    L_0x0112:
        goto L_0x0156;
    L_0x0113:
        r6 = getRDFTermKind(r4);
        if (r6 == 0) goto L_0x0130;
    L_0x0119:
        if (r6 == r13) goto L_0x0156;
    L_0x011b:
        if (r6 == r12) goto L_0x0126;
    L_0x011d:
        if (r6 != r11) goto L_0x0120;
    L_0x011f:
        goto L_0x0156;
    L_0x0120:
        r0 = new com.adobe.xmp.XMPException;
        r0.<init>(r10, r2);
        throw r0;
    L_0x0126:
        r4 = r4.getNodeValue();
        r6 = "rdf:resource";
    L_0x012c:
        addQualifierNode(r3, r6, r4);
        goto L_0x0156;
    L_0x0130:
        if (r5 != 0) goto L_0x013b;
    L_0x0132:
        r6 = r4.getNodeName();
        r4 = r4.getNodeValue();
        goto L_0x012c;
    L_0x013b:
        r6 = r4.getNodeName();
        r6 = r14.equals(r6);
        if (r6 == 0) goto L_0x014d;
    L_0x0145:
        r4 = r4.getNodeValue();
        addQualifierNode(r3, r14, r4);
        goto L_0x0156;
    L_0x014d:
        r6 = r4.getNodeValue();
        r9 = 0;
        addChildNode(r0, r3, r4, r6, r9);
        goto L_0x0157;
    L_0x0156:
        r9 = 0;
    L_0x0157:
        r1 = r1 + 1;
        goto L_0x00e4;
    L_0x015a:
        return;
    L_0x015b:
        r0 = new com.adobe.xmp.XMPException;
        r1 = "Nested content not allowed with rdf:resource or property attributes";
        r0.<init>(r1, r2);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.adobe.xmp.impl.ParseRDF.rdf_EmptyPropertyElement(com.adobe.xmp.impl.XMPMetaImpl, com.adobe.xmp.impl.XMPNode, org.w3c.dom.Node, boolean):void");
    }

    private static void rdf_LiteralPropertyElement(XMPMetaImpl xMPMetaImpl, XMPNode xMPNode, Node node, boolean z) throws XMPException {
        Node item;
        XMPNode addChildNode = addChildNode(xMPMetaImpl, xMPNode, node, null, z);
        int i = 0;
        for (int i2 = 0; i2 < node.getAttributes().getLength(); i2++) {
            item = node.getAttributes().item(i2);
            String str = "xmlns";
            if (!(str.equals(item.getPrefix()) || (item.getPrefix() == null && str.equals(item.getNodeName())))) {
                String namespaceURI = item.getNamespaceURI();
                str = item.getLocalName();
                String nodeName = item.getNodeName();
                String str2 = XMPConst.XML_LANG;
                if (str2.equals(nodeName)) {
                    addQualifierNode(addChildNode, str2, item.getNodeValue());
                } else if (!(XMPConst.NS_RDF.equals(namespaceURI) && ("ID".equals(str) || "datatype".equals(str)))) {
                    throw new XMPException("Invalid attribute for literal property element", XMPError.BADRDF);
                }
            }
        }
        String str3 = "";
        while (i < node.getChildNodes().getLength()) {
            item = node.getChildNodes().item(i);
            if (item.getNodeType() == (short) 3) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(str3);
                stringBuilder.append(item.getNodeValue());
                str3 = stringBuilder.toString();
                i++;
            } else {
                throw new XMPException("Invalid child of literal property element", XMPError.BADRDF);
            }
        }
        addChildNode.setValue(str3);
    }

    private static void rdf_NodeElement(XMPMetaImpl xMPMetaImpl, XMPNode xMPNode, Node node, boolean z) throws XMPException {
        int rDFTermKind = getRDFTermKind(node);
        if (rDFTermKind != 8 && rDFTermKind != 0) {
            throw new XMPException("Node element must be rdf:Description or typed node", XMPError.BADRDF);
        } else if (z && rDFTermKind == 0) {
            throw new XMPException("Top level typed node not allowed", XMPError.BADXMP);
        } else {
            rdf_NodeElementAttrs(xMPMetaImpl, xMPNode, node, z);
            rdf_PropertyElementList(xMPMetaImpl, xMPNode, node, z);
        }
    }

    private static void rdf_NodeElementAttrs(XMPMetaImpl xMPMetaImpl, XMPNode xMPNode, Node node, boolean z) throws XMPException {
        int i = 0;
        for (int i2 = 0; i2 < node.getAttributes().getLength(); i2++) {
            Node item = node.getAttributes().item(i2);
            String str = "xmlns";
            if (!(str.equals(item.getPrefix()) || (item.getPrefix() == null && str.equals(item.getNodeName())))) {
                int rDFTermKind = getRDFTermKind(item);
                if (rDFTermKind == 0) {
                    addChildNode(xMPMetaImpl, xMPNode, item, item.getNodeValue(), z);
                } else if (rDFTermKind != 6 && rDFTermKind != 2 && rDFTermKind != 3) {
                    throw new XMPException("Invalid nodeElement attribute", XMPError.BADRDF);
                } else if (i <= 0) {
                    i++;
                    if (z && rDFTermKind == 3) {
                        if (xMPNode.getName() == null || xMPNode.getName().length() <= 0) {
                            xMPNode.setName(item.getNodeValue());
                        } else if (!xMPNode.getName().equals(item.getNodeValue())) {
                            throw new XMPException("Mismatched top level rdf:about values", XMPError.BADXMP);
                        }
                    }
                } else {
                    throw new XMPException("Mutally exclusive about, ID, nodeID attributes", XMPError.BADRDF);
                }
            }
        }
    }

    private static void rdf_NodeElementList(XMPMetaImpl xMPMetaImpl, XMPNode xMPNode, Node node) throws XMPException {
        for (int i = 0; i < node.getChildNodes().getLength(); i++) {
            Node item = node.getChildNodes().item(i);
            if (!isWhitespaceNode(item)) {
                rdf_NodeElement(xMPMetaImpl, xMPNode, item, true);
            }
        }
    }

    private static void rdf_ParseTypeCollectionPropertyElement() throws XMPException {
        throw new XMPException("ParseTypeCollection property element not allowed", XMPError.BADXMP);
    }

    private static void rdf_ParseTypeLiteralPropertyElement() throws XMPException {
        throw new XMPException("ParseTypeLiteral property element not allowed", XMPError.BADXMP);
    }

    private static void rdf_ParseTypeOtherPropertyElement() throws XMPException {
        throw new XMPException("ParseTypeOther property element not allowed", XMPError.BADXMP);
    }

    private static void rdf_ParseTypeResourcePropertyElement(XMPMetaImpl xMPMetaImpl, XMPNode xMPNode, Node node, boolean z) throws XMPException {
        xMPNode = addChildNode(xMPMetaImpl, xMPNode, node, "", z);
        xMPNode.getOptions().setStruct(true);
        for (int i = 0; i < node.getAttributes().getLength(); i++) {
            Node item = node.getAttributes().item(i);
            String str = "xmlns";
            if (!(str.equals(item.getPrefix()) || (item.getPrefix() == null && str.equals(item.getNodeName())))) {
                String localName = item.getLocalName();
                str = item.getNamespaceURI();
                String nodeName = item.getNodeName();
                String str2 = XMPConst.XML_LANG;
                if (str2.equals(nodeName)) {
                    addQualifierNode(xMPNode, str2, item.getNodeValue());
                } else if (!(XMPConst.NS_RDF.equals(str) && ("ID".equals(localName) || "parseType".equals(localName)))) {
                    throw new XMPException("Invalid attribute for ParseTypeResource property element", XMPError.BADRDF);
                }
            }
        }
        rdf_PropertyElementList(xMPMetaImpl, xMPNode, node, false);
        if (xMPNode.getHasValueChild()) {
            fixupQualifiedNode(xMPNode);
        }
    }

    private static void rdf_PropertyElement(XMPMetaImpl xMPMetaImpl, XMPNode xMPNode, Node node, boolean z) throws XMPException {
        if (isPropertyElementName(getRDFTermKind(node))) {
            int i;
            Node item;
            String str;
            NamedNodeMap attributes = node.getAttributes();
            List list = null;
            for (i = 0; i < attributes.getLength(); i++) {
                item = attributes.item(i);
                str = "xmlns";
                if (str.equals(item.getPrefix()) || (item.getPrefix() == null && str.equals(item.getNodeName()))) {
                    if (list == null) {
                        list = new ArrayList();
                    }
                    list.add(item.getNodeName());
                }
            }
            if (list != null) {
                for (String removeNamedItem : list) {
                    attributes.removeNamedItem(removeNamedItem);
                }
            }
            if (attributes.getLength() <= 3) {
                for (i = 0; i < attributes.getLength(); i++) {
                    item = attributes.item(i);
                    String localName = item.getLocalName();
                    str = item.getNamespaceURI();
                    String nodeValue = item.getNodeValue();
                    boolean equals = XMPConst.XML_LANG.equals(item.getNodeName());
                    String str2 = XMPConst.NS_RDF;
                    if (!equals || ("ID".equals(localName) && str2.equals(str))) {
                        if ("datatype".equals(localName) && str2.equals(str)) {
                            rdf_LiteralPropertyElement(xMPMetaImpl, xMPNode, node, z);
                        } else if (!"parseType".equals(localName) || !str2.equals(str)) {
                            rdf_EmptyPropertyElement(xMPMetaImpl, xMPNode, node, z);
                        } else if ("Literal".equals(nodeValue)) {
                            rdf_ParseTypeLiteralPropertyElement();
                        } else if ("Resource".equals(nodeValue)) {
                            rdf_ParseTypeResourcePropertyElement(xMPMetaImpl, xMPNode, node, z);
                        } else if ("Collection".equals(nodeValue)) {
                            rdf_ParseTypeCollectionPropertyElement();
                        } else {
                            rdf_ParseTypeOtherPropertyElement();
                        }
                        return;
                    }
                }
                if (node.hasChildNodes()) {
                    for (int i2 = 0; i2 < node.getChildNodes().getLength(); i2++) {
                        if (node.getChildNodes().item(i2).getNodeType() != (short) 3) {
                            rdf_ResourcePropertyElement(xMPMetaImpl, xMPNode, node, z);
                            return;
                        }
                    }
                    rdf_LiteralPropertyElement(xMPMetaImpl, xMPNode, node, z);
                    return;
                }
            }
            rdf_EmptyPropertyElement(xMPMetaImpl, xMPNode, node, z);
            return;
        }
        throw new XMPException("Invalid property element name", XMPError.BADRDF);
    }

    private static void rdf_PropertyElementList(XMPMetaImpl xMPMetaImpl, XMPNode xMPNode, Node node, boolean z) throws XMPException {
        for (int i = 0; i < node.getChildNodes().getLength(); i++) {
            Node item = node.getChildNodes().item(i);
            if (!isWhitespaceNode(item)) {
                if (item.getNodeType() == (short) 1) {
                    rdf_PropertyElement(xMPMetaImpl, xMPNode, item, z);
                } else {
                    throw new XMPException("Expected property element node not found", XMPError.BADRDF);
                }
            }
        }
    }

    static void rdf_RDF(XMPMetaImpl xMPMetaImpl, Node node) throws XMPException {
        if (node.hasAttributes()) {
            rdf_NodeElementList(xMPMetaImpl, xMPMetaImpl.getRoot(), node);
            return;
        }
        throw new XMPException("Invalid attributes of rdf:RDF element", XMPError.BADRDF);
    }

    private static void rdf_ResourcePropertyElement(XMPMetaImpl xMPMetaImpl, XMPNode xMPNode, Node node, boolean z) throws XMPException {
        if (z) {
            if ("iX:changes".equals(node.getNodeName())) {
                return;
            }
        }
        xMPNode = addChildNode(xMPMetaImpl, xMPNode, node, "", z);
        int i = 0;
        while (true) {
            int length = node.getAttributes().getLength();
            String str = XMPConst.NS_RDF;
            String str2;
            if (i < length) {
                Node item = node.getAttributes().item(i);
                str2 = "xmlns";
                if (!(str2.equals(item.getPrefix()) || (item.getPrefix() == null && str2.equals(item.getNodeName())))) {
                    String localName = item.getLocalName();
                    str2 = item.getNamespaceURI();
                    String nodeName = item.getNodeName();
                    String str3 = XMPConst.XML_LANG;
                    if (str3.equals(nodeName)) {
                        addQualifierNode(xMPNode, str3, item.getNodeValue());
                    } else if ("ID".equals(localName) && str.equals(str2)) {
                    }
                }
                i++;
            } else {
                Object obj = null;
                for (i = 0; i < node.getChildNodes().getLength(); i++) {
                    Node item2 = node.getChildNodes().item(i);
                    if (!isWhitespaceNode(item2)) {
                        if (item2.getNodeType() == (short) 1 && obj == null) {
                            boolean equals = str.equals(item2.getNamespaceURI());
                            str2 = item2.getLocalName();
                            if (equals && "Bag".equals(str2)) {
                                xMPNode.getOptions().setArray(true);
                            } else if (equals && "Seq".equals(str2)) {
                                xMPNode.getOptions().setArray(true).setArrayOrdered(true);
                            } else if (equals && "Alt".equals(str2)) {
                                xMPNode.getOptions().setArray(true).setArrayOrdered(true).setArrayAlternate(true);
                            } else {
                                xMPNode.getOptions().setStruct(true);
                                if (!(equals || "Description".equals(str2))) {
                                    String namespaceURI = item2.getNamespaceURI();
                                    if (namespaceURI != null) {
                                        StringBuilder stringBuilder = new StringBuilder();
                                        stringBuilder.append(namespaceURI);
                                        stringBuilder.append(':');
                                        stringBuilder.append(str2);
                                        addQualifierNode(xMPNode, XMPConst.RDF_TYPE, stringBuilder.toString());
                                    } else {
                                        throw new XMPException("All XML elements must be in a namespace", XMPError.BADXMP);
                                    }
                                }
                            }
                            rdf_NodeElement(xMPMetaImpl, xMPNode, item2, false);
                            if (xMPNode.getHasValueChild()) {
                                fixupQualifiedNode(xMPNode);
                            } else if (xMPNode.getOptions().isArrayAlternate()) {
                                XMPNodeUtils.detectAltText(xMPNode);
                            }
                            obj = 1;
                        } else if (obj != null) {
                            throw new XMPException("Invalid child of resource property element", XMPError.BADRDF);
                        } else {
                            throw new XMPException("Children of resource property element must be XML elements", XMPError.BADRDF);
                        }
                    }
                }
                if (obj == null) {
                    throw new XMPException("Missing child of resource property element", XMPError.BADRDF);
                }
                return;
            }
        }
        throw new XMPException("Invalid attribute for resource property element", XMPError.BADRDF);
    }
}
