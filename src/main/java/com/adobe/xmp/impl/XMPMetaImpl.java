package com.adobe.xmp.impl;

import com.adobe.xmp.XMPConst;
import com.adobe.xmp.XMPDateTime;
import com.adobe.xmp.XMPException;
import com.adobe.xmp.XMPIterator;
import com.adobe.xmp.XMPMeta;
import com.adobe.xmp.XMPPathFactory;
import com.adobe.xmp.XMPUtils;
import com.adobe.xmp.impl.xpath.XMPPath;
import com.adobe.xmp.impl.xpath.XMPPathParser;
import com.adobe.xmp.options.IteratorOptions;
import com.adobe.xmp.options.ParseOptions;
import com.adobe.xmp.options.PropertyOptions;
import com.adobe.xmp.properties.XMPProperty;
import java.util.Calendar;

public class XMPMetaImpl implements XMPMeta, XMPConst {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int VALUE_BASE64 = 7;
    private static final int VALUE_BOOLEAN = 1;
    private static final int VALUE_CALENDAR = 6;
    private static final int VALUE_DATE = 5;
    private static final int VALUE_DOUBLE = 4;
    private static final int VALUE_INTEGER = 2;
    private static final int VALUE_LONG = 3;
    private static final int VALUE_STRING = 0;
    private String packetHeader;
    private XMPNode tree;

    public XMPMetaImpl() {
        this.packetHeader = null;
        this.tree = new XMPNode(null, null, null);
    }

    public XMPMetaImpl(XMPNode xMPNode) {
        this.packetHeader = null;
        this.tree = xMPNode;
    }

    private void doSetArrayItem(XMPNode xMPNode, int i, String str, PropertyOptions propertyOptions, boolean z) throws XMPException {
        XMPNode xMPNode2 = new XMPNode(XMPConst.ARRAY_ITEM_NAME, null);
        propertyOptions = XMPNodeUtils.verifySetOptions(propertyOptions, str);
        int childrenLength = z ? xMPNode.getChildrenLength() + 1 : xMPNode.getChildrenLength();
        if (i == -1) {
            i = childrenLength;
        }
        if (1 > i || i > childrenLength) {
            throw new XMPException("Array index out of bounds", 104);
        }
        if (!z) {
            xMPNode.removeChild(i);
        }
        xMPNode.addChild(i, xMPNode2);
        setNode(xMPNode2, str, propertyOptions, false);
    }

    private Object evaluateNodeValue(int i, XMPNode xMPNode) throws XMPException {
        String value = xMPNode.getValue();
        switch (i) {
            case 1:
                return new Boolean(XMPUtils.convertToBoolean(value));
            case 2:
                return new Integer(XMPUtils.convertToInteger(value));
            case 3:
                return new Long(XMPUtils.convertToLong(value));
            case 4:
                return new Double(XMPUtils.convertToDouble(value));
            case 5:
                return XMPUtils.convertToDate(value);
            case 6:
                return XMPUtils.convertToDate(value).getCalendar();
            case 7:
                return XMPUtils.decodeBase64(value);
            default:
                if (value == null && !xMPNode.getOptions().isCompositeProperty()) {
                    value = "";
                }
                return value;
        }
    }

    public void appendArrayItem(String str, String str2, PropertyOptions propertyOptions, String str3, PropertyOptions propertyOptions2) throws XMPException {
        ParameterAsserts.assertSchemaNS(str);
        ParameterAsserts.assertArrayName(str2);
        if (propertyOptions == null) {
            propertyOptions = new PropertyOptions();
        }
        if (propertyOptions.isOnlyArrayOptions()) {
            propertyOptions = XMPNodeUtils.verifySetOptions(propertyOptions, null);
            XMPPath expandXPath = XMPPathParser.expandXPath(str, str2);
            XMPNode findNode = XMPNodeUtils.findNode(this.tree, expandXPath, false, null);
            if (findNode != null) {
                if (!findNode.getOptions().isArray()) {
                    throw new XMPException("The named property is not an array", 102);
                }
            } else if (propertyOptions.isArray()) {
                findNode = XMPNodeUtils.findNode(this.tree, expandXPath, true, propertyOptions);
                if (findNode == null) {
                    throw new XMPException("Failure creating array node", 102);
                }
            } else {
                throw new XMPException("Explicit arrayOptions required to create new array", 103);
            }
            doSetArrayItem(findNode, -1, str3, propertyOptions2, true);
            return;
        }
        throw new XMPException("Only array form flags allowed for arrayOptions", 103);
    }

    public void appendArrayItem(String str, String str2, String str3) throws XMPException {
        appendArrayItem(str, str2, null, str3, null);
    }

    public Object clone() {
        return new XMPMetaImpl((XMPNode) this.tree.clone());
    }

    public int countArrayItems(String str, String str2) throws XMPException {
        ParameterAsserts.assertSchemaNS(str);
        ParameterAsserts.assertArrayName(str2);
        XMPNode findNode = XMPNodeUtils.findNode(this.tree, XMPPathParser.expandXPath(str, str2), false, null);
        if (findNode == null) {
            return 0;
        }
        if (findNode.getOptions().isArray()) {
            return findNode.getChildrenLength();
        }
        throw new XMPException("The named property is not an array", 102);
    }

    public void deleteArrayItem(String str, String str2, int i) {
        try {
            ParameterAsserts.assertSchemaNS(str);
            ParameterAsserts.assertArrayName(str2);
            deleteProperty(str, XMPPathFactory.composeArrayItemPath(str2, i));
        } catch (XMPException unused) {
        }
    }

    public void deleteProperty(String str, String str2) {
        try {
            ParameterAsserts.assertSchemaNS(str);
            ParameterAsserts.assertPropName(str2);
            XMPNode findNode = XMPNodeUtils.findNode(this.tree, XMPPathParser.expandXPath(str, str2), false, null);
            if (findNode != null) {
                XMPNodeUtils.deleteNode(findNode);
            }
        } catch (XMPException unused) {
        }
    }

    public void deleteQualifier(String str, String str2, String str3, String str4) {
        try {
            ParameterAsserts.assertSchemaNS(str);
            ParameterAsserts.assertPropName(str2);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(str2);
            stringBuilder.append(XMPPathFactory.composeQualifierPath(str3, str4));
            deleteProperty(str, stringBuilder.toString());
        } catch (XMPException unused) {
        }
    }

    public void deleteStructField(String str, String str2, String str3, String str4) {
        try {
            ParameterAsserts.assertSchemaNS(str);
            ParameterAsserts.assertStructName(str2);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(str2);
            stringBuilder.append(XMPPathFactory.composeStructFieldPath(str3, str4));
            deleteProperty(str, stringBuilder.toString());
        } catch (XMPException unused) {
        }
    }

    public boolean doesArrayItemExist(String str, String str2, int i) {
        try {
            ParameterAsserts.assertSchemaNS(str);
            ParameterAsserts.assertArrayName(str2);
            return doesPropertyExist(str, XMPPathFactory.composeArrayItemPath(str2, i));
        } catch (XMPException unused) {
            return false;
        }
    }

    public boolean doesPropertyExist(String str, String str2) {
        boolean z = false;
        try {
            ParameterAsserts.assertSchemaNS(str);
            ParameterAsserts.assertPropName(str2);
            if (XMPNodeUtils.findNode(this.tree, XMPPathParser.expandXPath(str, str2), false, null) != null) {
                z = true;
            }
        } catch (XMPException unused) {
            return z;
        }
    }

    public boolean doesQualifierExist(String str, String str2, String str3, String str4) {
        try {
            ParameterAsserts.assertSchemaNS(str);
            ParameterAsserts.assertPropName(str2);
            str3 = XMPPathFactory.composeQualifierPath(str3, str4);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(str2);
            stringBuilder.append(str3);
            return doesPropertyExist(str, stringBuilder.toString());
        } catch (XMPException unused) {
            return false;
        }
    }

    public boolean doesStructFieldExist(String str, String str2, String str3, String str4) {
        try {
            ParameterAsserts.assertSchemaNS(str);
            ParameterAsserts.assertStructName(str2);
            str3 = XMPPathFactory.composeStructFieldPath(str3, str4);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(str2);
            stringBuilder.append(str3);
            return doesPropertyExist(str, stringBuilder.toString());
        } catch (XMPException unused) {
            return false;
        }
    }

    public String dumpObject() {
        return getRoot().dumpNode(true);
    }

    public XMPProperty getArrayItem(String str, String str2, int i) throws XMPException {
        ParameterAsserts.assertSchemaNS(str);
        ParameterAsserts.assertArrayName(str2);
        return getProperty(str, XMPPathFactory.composeArrayItemPath(str2, i));
    }

    public XMPProperty getLocalizedText(String str, String str2, String str3, String str4) throws XMPException {
        ParameterAsserts.assertSchemaNS(str);
        ParameterAsserts.assertArrayName(str2);
        ParameterAsserts.assertSpecificLang(str4);
        str3 = str3 != null ? Utils.normalizeLangValue(str3) : null;
        str4 = Utils.normalizeLangValue(str4);
        XMPNode findNode = XMPNodeUtils.findNode(this.tree, XMPPathParser.expandXPath(str, str2), false, null);
        if (findNode == null) {
            return null;
        }
        Object[] chooseLocalizedText = XMPNodeUtils.chooseLocalizedText(findNode, str3, str4);
        int intValue = ((Integer) chooseLocalizedText[0]).intValue();
        findNode = (XMPNode) chooseLocalizedText[1];
        return intValue != 0 ? new XMPProperty() {
            public String getLanguage() {
                return findNode.getQualifier(1).getValue();
            }

            public PropertyOptions getOptions() {
                return findNode.getOptions();
            }

            public String getValue() {
                return findNode.getValue();
            }

            public String toString() {
                return findNode.getValue().toString();
            }
        } : null;
    }

    public String getObjectName() {
        return this.tree.getName() != null ? this.tree.getName() : "";
    }

    public String getPacketHeader() {
        return this.packetHeader;
    }

    public XMPProperty getProperty(String str, String str2) throws XMPException {
        return getProperty(str, str2, 0);
    }

    protected XMPProperty getProperty(String str, String str2, int i) throws XMPException {
        ParameterAsserts.assertSchemaNS(str);
        ParameterAsserts.assertPropName(str2);
        final XMPNode findNode = XMPNodeUtils.findNode(this.tree, XMPPathParser.expandXPath(str, str2), false, null);
        if (findNode == null) {
            return null;
        }
        if (i == 0 || !findNode.getOptions().isCompositeProperty()) {
            final Object evaluateNodeValue = evaluateNodeValue(i, findNode);
            return new XMPProperty() {
                public String getLanguage() {
                    return null;
                }

                public PropertyOptions getOptions() {
                    return findNode.getOptions();
                }

                public String getValue() {
                    Object obj = evaluateNodeValue;
                    return obj != null ? obj.toString() : null;
                }

                public String toString() {
                    return evaluateNodeValue.toString();
                }
            };
        }
        throw new XMPException("Property must be simple when a value type is requested", 102);
    }

    public byte[] getPropertyBase64(String str, String str2) throws XMPException {
        return (byte[]) getPropertyObject(str, str2, 7);
    }

    public Boolean getPropertyBoolean(String str, String str2) throws XMPException {
        return (Boolean) getPropertyObject(str, str2, 1);
    }

    public Calendar getPropertyCalendar(String str, String str2) throws XMPException {
        return (Calendar) getPropertyObject(str, str2, 6);
    }

    public XMPDateTime getPropertyDate(String str, String str2) throws XMPException {
        return (XMPDateTime) getPropertyObject(str, str2, 5);
    }

    public Double getPropertyDouble(String str, String str2) throws XMPException {
        return (Double) getPropertyObject(str, str2, 4);
    }

    public Integer getPropertyInteger(String str, String str2) throws XMPException {
        return (Integer) getPropertyObject(str, str2, 2);
    }

    public Long getPropertyLong(String str, String str2) throws XMPException {
        return (Long) getPropertyObject(str, str2, 3);
    }

    protected Object getPropertyObject(String str, String str2, int i) throws XMPException {
        ParameterAsserts.assertSchemaNS(str);
        ParameterAsserts.assertPropName(str2);
        XMPNode findNode = XMPNodeUtils.findNode(this.tree, XMPPathParser.expandXPath(str, str2), false, null);
        if (findNode == null) {
            return null;
        }
        if (i == 0 || !findNode.getOptions().isCompositeProperty()) {
            return evaluateNodeValue(i, findNode);
        }
        throw new XMPException("Property must be simple when a value type is requested", 102);
    }

    public String getPropertyString(String str, String str2) throws XMPException {
        return (String) getPropertyObject(str, str2, 0);
    }

    public XMPProperty getQualifier(String str, String str2, String str3, String str4) throws XMPException {
        ParameterAsserts.assertSchemaNS(str);
        ParameterAsserts.assertPropName(str2);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(str2);
        stringBuilder.append(XMPPathFactory.composeQualifierPath(str3, str4));
        return getProperty(str, stringBuilder.toString());
    }

    public XMPNode getRoot() {
        return this.tree;
    }

    public XMPProperty getStructField(String str, String str2, String str3, String str4) throws XMPException {
        ParameterAsserts.assertSchemaNS(str);
        ParameterAsserts.assertStructName(str2);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(str2);
        stringBuilder.append(XMPPathFactory.composeStructFieldPath(str3, str4));
        return getProperty(str, stringBuilder.toString());
    }

    public void insertArrayItem(String str, String str2, int i, String str3) throws XMPException {
        insertArrayItem(str, str2, i, str3, null);
    }

    public void insertArrayItem(String str, String str2, int i, String str3, PropertyOptions propertyOptions) throws XMPException {
        ParameterAsserts.assertSchemaNS(str);
        ParameterAsserts.assertArrayName(str2);
        XMPNode findNode = XMPNodeUtils.findNode(this.tree, XMPPathParser.expandXPath(str, str2), false, null);
        if (findNode != null) {
            doSetArrayItem(findNode, i, str3, propertyOptions, true);
            return;
        }
        throw new XMPException("Specified array does not exist", 102);
    }

    public XMPIterator iterator() throws XMPException {
        return iterator(null, null, null);
    }

    public XMPIterator iterator(IteratorOptions iteratorOptions) throws XMPException {
        return iterator(null, null, iteratorOptions);
    }

    public XMPIterator iterator(String str, String str2, IteratorOptions iteratorOptions) throws XMPException {
        return new XMPIteratorImpl(this, str, str2, iteratorOptions);
    }

    public void normalize(ParseOptions parseOptions) throws XMPException {
        if (parseOptions == null) {
            parseOptions = new ParseOptions();
        }
        XMPNormalizer.process(this, parseOptions);
    }

    public void setArrayItem(String str, String str2, int i, String str3) throws XMPException {
        setArrayItem(str, str2, i, str3, null);
    }

    public void setArrayItem(String str, String str2, int i, String str3, PropertyOptions propertyOptions) throws XMPException {
        ParameterAsserts.assertSchemaNS(str);
        ParameterAsserts.assertArrayName(str2);
        XMPNode findNode = XMPNodeUtils.findNode(this.tree, XMPPathParser.expandXPath(str, str2), false, null);
        if (findNode != null) {
            doSetArrayItem(findNode, i, str3, propertyOptions, false);
            return;
        }
        throw new XMPException("Specified array does not exist", 102);
    }

    public void setLocalizedText(String str, String str2, String str3, String str4, String str5) throws XMPException {
        setLocalizedText(str, str2, str3, str4, str5, null);
    }

    /* JADX WARNING: Removed duplicated region for block: B:102:? A:{SYNTHETIC, RETURN, SKIP} */
    /* JADX WARNING: Removed duplicated region for block: B:87:0x015f  */
    /* JADX WARNING: Removed duplicated region for block: B:87:0x015f  */
    /* JADX WARNING: Removed duplicated region for block: B:102:? A:{SYNTHETIC, RETURN, SKIP} */
    /* JADX WARNING: Removed duplicated region for block: B:102:? A:{SYNTHETIC, RETURN, SKIP} */
    /* JADX WARNING: Removed duplicated region for block: B:87:0x015f  */
    /* JADX WARNING: Removed duplicated region for block: B:87:0x015f  */
    /* JADX WARNING: Removed duplicated region for block: B:102:? A:{SYNTHETIC, RETURN, SKIP} */
    /* JADX WARNING: Missing block: B:44:0x00ce, code:
            if (r3 != false) goto L_0x015c;
     */
    /* JADX WARNING: Missing block: B:53:0x00ef, code:
            if (r3 != false) goto L_0x015c;
     */
    /* JADX WARNING: Missing block: B:58:0x0105, code:
            if (r2.getValue().equals(r10.getValue()) != false) goto L_0x0107;
     */
    /* JADX WARNING: Missing block: B:66:0x0122, code:
            if (r2.getValue().equals(r10.getValue()) != false) goto L_0x0107;
     */
    public void setLocalizedText(java.lang.String r8, java.lang.String r9, java.lang.String r10, java.lang.String r11, java.lang.String r12, com.adobe.xmp.options.PropertyOptions r13) throws com.adobe.xmp.XMPException {
        /*
        r7 = this;
        com.adobe.xmp.impl.ParameterAsserts.assertSchemaNS(r8);
        com.adobe.xmp.impl.ParameterAsserts.assertArrayName(r9);
        com.adobe.xmp.impl.ParameterAsserts.assertSpecificLang(r11);
        r13 = 0;
        if (r10 == 0) goto L_0x0011;
    L_0x000c:
        r10 = com.adobe.xmp.impl.Utils.normalizeLangValue(r10);
        goto L_0x0012;
    L_0x0011:
        r10 = r13;
    L_0x0012:
        r11 = com.adobe.xmp.impl.Utils.normalizeLangValue(r11);
        r8 = com.adobe.xmp.impl.xpath.XMPPathParser.expandXPath(r8, r9);
        r9 = r7.tree;
        r0 = new com.adobe.xmp.options.PropertyOptions;
        r1 = 7680; // 0x1e00 float:1.0762E-41 double:3.7944E-320;
        r0.<init>(r1);
        r1 = 1;
        r8 = com.adobe.xmp.impl.XMPNodeUtils.findNode(r9, r8, r1, r0);
        r9 = 102; // 0x66 float:1.43E-43 double:5.04E-322;
        if (r8 == 0) goto L_0x0169;
    L_0x002c:
        r0 = r8.getOptions();
        r0 = r0.isArrayAltText();
        if (r0 != 0) goto L_0x0056;
    L_0x0036:
        r0 = r8.hasChildren();
        if (r0 != 0) goto L_0x004e;
    L_0x003c:
        r0 = r8.getOptions();
        r0 = r0.isArrayAlternate();
        if (r0 == 0) goto L_0x004e;
    L_0x0046:
        r0 = r8.getOptions();
        r0.setArrayAltText(r1);
        goto L_0x0056;
    L_0x004e:
        r8 = new com.adobe.xmp.XMPException;
        r10 = "Specified property is no alt-text array";
        r8.<init>(r10, r9);
        throw r8;
    L_0x0056:
        r0 = r8.iterateChildren();
    L_0x005a:
        r2 = r0.hasNext();
        r3 = 0;
        r4 = "x-default";
        if (r2 == 0) goto L_0x0097;
    L_0x0063:
        r2 = r0.next();
        r2 = (com.adobe.xmp.impl.XMPNode) r2;
        r5 = r2.hasQualifier();
        if (r5 == 0) goto L_0x008f;
    L_0x006f:
        r5 = r2.getQualifier(r1);
        r5 = r5.getName();
        r6 = "xml:lang";
        r5 = r6.equals(r5);
        if (r5 == 0) goto L_0x008f;
    L_0x007f:
        r5 = r2.getQualifier(r1);
        r5 = r5.getValue();
        r5 = r4.equals(r5);
        if (r5 == 0) goto L_0x005a;
    L_0x008d:
        r9 = 1;
        goto L_0x0099;
    L_0x008f:
        r8 = new com.adobe.xmp.XMPException;
        r10 = "Language qualifier must be first";
        r8.<init>(r10, r9);
        throw r8;
    L_0x0097:
        r2 = r13;
        r9 = 0;
    L_0x0099:
        if (r2 == 0) goto L_0x00a7;
    L_0x009b:
        r0 = r8.getChildrenLength();
        if (r0 <= r1) goto L_0x00a7;
    L_0x00a1:
        r8.removeChild(r2);
        r8.addChild(r1, r2);
    L_0x00a7:
        r10 = com.adobe.xmp.impl.XMPNodeUtils.chooseLocalizedText(r8, r10, r11);
        r0 = r10[r3];
        r0 = (java.lang.Integer) r0;
        r0 = r0.intValue();
        r10 = r10[r1];
        r10 = (com.adobe.xmp.impl.XMPNode) r10;
        r3 = r4.equals(r11);
        if (r0 == 0) goto L_0x0154;
    L_0x00bd:
        if (r0 == r1) goto L_0x010e;
    L_0x00bf:
        r13 = 2;
        if (r0 == r13) goto L_0x00f3;
    L_0x00c2:
        r10 = 3;
        if (r0 == r10) goto L_0x00ec;
    L_0x00c5:
        r10 = 4;
        if (r0 == r10) goto L_0x00dc;
    L_0x00c8:
        r10 = 5;
        if (r0 != r10) goto L_0x00d2;
    L_0x00cb:
        com.adobe.xmp.impl.XMPNodeUtils.appendLangItem(r8, r11, r12);
        if (r3 == 0) goto L_0x015d;
    L_0x00d0:
        goto L_0x015c;
    L_0x00d2:
        r8 = new com.adobe.xmp.XMPException;
        r9 = 9;
        r10 = "Unexpected result from ChooseLocalizedText";
        r8.<init>(r10, r9);
        throw r8;
    L_0x00dc:
        if (r2 == 0) goto L_0x00e7;
    L_0x00de:
        r10 = r8.getChildrenLength();
        if (r10 != r1) goto L_0x00e7;
    L_0x00e4:
        r2.setValue(r12);
    L_0x00e7:
        com.adobe.xmp.impl.XMPNodeUtils.appendLangItem(r8, r11, r12);
        goto L_0x015d;
    L_0x00ec:
        com.adobe.xmp.impl.XMPNodeUtils.appendLangItem(r8, r11, r12);
        if (r3 == 0) goto L_0x015d;
    L_0x00f1:
        goto L_0x015c;
    L_0x00f3:
        if (r9 == 0) goto L_0x010a;
    L_0x00f5:
        if (r2 == r10) goto L_0x010a;
    L_0x00f7:
        if (r2 == 0) goto L_0x010a;
    L_0x00f9:
        r11 = r2.getValue();
        r13 = r10.getValue();
        r11 = r11.equals(r13);
        if (r11 == 0) goto L_0x010a;
    L_0x0107:
        r2.setValue(r12);
    L_0x010a:
        r10.setValue(r12);
        goto L_0x015d;
    L_0x010e:
        if (r3 != 0) goto L_0x0125;
    L_0x0110:
        if (r9 == 0) goto L_0x010a;
    L_0x0112:
        if (r2 == r10) goto L_0x010a;
    L_0x0114:
        if (r2 == 0) goto L_0x010a;
    L_0x0116:
        r11 = r2.getValue();
        r13 = r10.getValue();
        r11 = r11.equals(r13);
        if (r11 == 0) goto L_0x010a;
    L_0x0124:
        goto L_0x0107;
    L_0x0125:
        r10 = r8.iterateChildren();
    L_0x0129:
        r11 = r10.hasNext();
        if (r11 == 0) goto L_0x014e;
    L_0x012f:
        r11 = r10.next();
        r11 = (com.adobe.xmp.impl.XMPNode) r11;
        if (r11 == r2) goto L_0x0129;
    L_0x0137:
        r0 = r11.getValue();
        if (r2 == 0) goto L_0x0142;
    L_0x013d:
        r3 = r2.getValue();
        goto L_0x0143;
    L_0x0142:
        r3 = r13;
    L_0x0143:
        r0 = r0.equals(r3);
        if (r0 != 0) goto L_0x014a;
    L_0x0149:
        goto L_0x0129;
    L_0x014a:
        r11.setValue(r12);
        goto L_0x0129;
    L_0x014e:
        if (r2 == 0) goto L_0x015d;
    L_0x0150:
        r2.setValue(r12);
        goto L_0x015d;
    L_0x0154:
        com.adobe.xmp.impl.XMPNodeUtils.appendLangItem(r8, r4, r12);
        if (r3 != 0) goto L_0x015c;
    L_0x0159:
        com.adobe.xmp.impl.XMPNodeUtils.appendLangItem(r8, r11, r12);
    L_0x015c:
        r9 = 1;
    L_0x015d:
        if (r9 != 0) goto L_0x0168;
    L_0x015f:
        r9 = r8.getChildrenLength();
        if (r9 != r1) goto L_0x0168;
    L_0x0165:
        com.adobe.xmp.impl.XMPNodeUtils.appendLangItem(r8, r4, r12);
    L_0x0168:
        return;
    L_0x0169:
        r8 = new com.adobe.xmp.XMPException;
        r10 = "Failed to find or create array node";
        r8.<init>(r10, r9);
        throw r8;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.adobe.xmp.impl.XMPMetaImpl.setLocalizedText(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, com.adobe.xmp.options.PropertyOptions):void");
    }

    void setNode(XMPNode xMPNode, Object obj, PropertyOptions propertyOptions, boolean z) throws XMPException {
        if (z) {
            xMPNode.clear();
        }
        xMPNode.getOptions().mergeWith(propertyOptions);
        if (!xMPNode.getOptions().isCompositeProperty()) {
            XMPNodeUtils.setNodeValue(xMPNode, obj);
        } else if (obj == null || obj.toString().length() <= 0) {
            xMPNode.removeChildren();
        } else {
            throw new XMPException("Composite nodes can't have values", 102);
        }
    }

    public void setObjectName(String str) {
        this.tree.setName(str);
    }

    public void setPacketHeader(String str) {
        this.packetHeader = str;
    }

    public void setProperty(String str, String str2, Object obj) throws XMPException {
        setProperty(str, str2, obj, null);
    }

    public void setProperty(String str, String str2, Object obj, PropertyOptions propertyOptions) throws XMPException {
        ParameterAsserts.assertSchemaNS(str);
        ParameterAsserts.assertPropName(str2);
        propertyOptions = XMPNodeUtils.verifySetOptions(propertyOptions, obj);
        XMPNode findNode = XMPNodeUtils.findNode(this.tree, XMPPathParser.expandXPath(str, str2), true, propertyOptions);
        if (findNode != null) {
            setNode(findNode, obj, propertyOptions, false);
            return;
        }
        throw new XMPException("Specified property does not exist", 102);
    }

    public void setPropertyBase64(String str, String str2, byte[] bArr) throws XMPException {
        setProperty(str, str2, bArr, null);
    }

    public void setPropertyBase64(String str, String str2, byte[] bArr, PropertyOptions propertyOptions) throws XMPException {
        setProperty(str, str2, bArr, propertyOptions);
    }

    public void setPropertyBoolean(String str, String str2, boolean z) throws XMPException {
        setProperty(str, str2, z ? XMPConst.TRUESTR : XMPConst.FALSESTR, null);
    }

    public void setPropertyBoolean(String str, String str2, boolean z, PropertyOptions propertyOptions) throws XMPException {
        setProperty(str, str2, z ? XMPConst.TRUESTR : XMPConst.FALSESTR, propertyOptions);
    }

    public void setPropertyCalendar(String str, String str2, Calendar calendar) throws XMPException {
        setProperty(str, str2, calendar, null);
    }

    public void setPropertyCalendar(String str, String str2, Calendar calendar, PropertyOptions propertyOptions) throws XMPException {
        setProperty(str, str2, calendar, propertyOptions);
    }

    public void setPropertyDate(String str, String str2, XMPDateTime xMPDateTime) throws XMPException {
        setProperty(str, str2, xMPDateTime, null);
    }

    public void setPropertyDate(String str, String str2, XMPDateTime xMPDateTime, PropertyOptions propertyOptions) throws XMPException {
        setProperty(str, str2, xMPDateTime, propertyOptions);
    }

    public void setPropertyDouble(String str, String str2, double d) throws XMPException {
        setProperty(str, str2, new Double(d), null);
    }

    public void setPropertyDouble(String str, String str2, double d, PropertyOptions propertyOptions) throws XMPException {
        setProperty(str, str2, new Double(d), propertyOptions);
    }

    public void setPropertyInteger(String str, String str2, int i) throws XMPException {
        setProperty(str, str2, new Integer(i), null);
    }

    public void setPropertyInteger(String str, String str2, int i, PropertyOptions propertyOptions) throws XMPException {
        setProperty(str, str2, new Integer(i), propertyOptions);
    }

    public void setPropertyLong(String str, String str2, long j) throws XMPException {
        setProperty(str, str2, new Long(j), null);
    }

    public void setPropertyLong(String str, String str2, long j, PropertyOptions propertyOptions) throws XMPException {
        setProperty(str, str2, new Long(j), propertyOptions);
    }

    public void setQualifier(String str, String str2, String str3, String str4, String str5) throws XMPException {
        setQualifier(str, str2, str3, str4, str5, null);
    }

    public void setQualifier(String str, String str2, String str3, String str4, String str5, PropertyOptions propertyOptions) throws XMPException {
        ParameterAsserts.assertSchemaNS(str);
        ParameterAsserts.assertPropName(str2);
        if (doesPropertyExist(str, str2)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(str2);
            stringBuilder.append(XMPPathFactory.composeQualifierPath(str3, str4));
            setProperty(str, stringBuilder.toString(), str5, propertyOptions);
            return;
        }
        throw new XMPException("Specified property does not exist!", 102);
    }

    public void setStructField(String str, String str2, String str3, String str4, String str5) throws XMPException {
        setStructField(str, str2, str3, str4, str5, null);
    }

    public void setStructField(String str, String str2, String str3, String str4, String str5, PropertyOptions propertyOptions) throws XMPException {
        ParameterAsserts.assertSchemaNS(str);
        ParameterAsserts.assertStructName(str2);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(str2);
        stringBuilder.append(XMPPathFactory.composeStructFieldPath(str3, str4));
        setProperty(str, stringBuilder.toString(), str5, propertyOptions);
    }

    public void sort() {
        this.tree.sort();
    }
}
