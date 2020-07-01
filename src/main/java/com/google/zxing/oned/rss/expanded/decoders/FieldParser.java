package com.google.zxing.oned.rss.expanded.decoders;

import com.google.zxing.NotFoundException;

final class FieldParser {
    private static final Object[][] FOUR_DIGIT_DATA_LENGTH;
    private static final Object[][] THREE_DIGIT_DATA_LENGTH;
    private static final Object[][] THREE_DIGIT_PLUS_DIGIT_DATA_LENGTH;
    private static final Object[][] TWO_DIGIT_DATA_LENGTH;
    private static final Object VARIABLE_LENGTH = new Object();

    static {
        r0 = new Object[24][];
        Object[] objArr = new Object[2];
        objArr[0] = "00";
        objArr[1] = Integer.valueOf(18);
        r0[0] = objArr;
        r0[1] = new Object[]{"01", Integer.valueOf(14)};
        r0[2] = new Object[]{"02", Integer.valueOf(14)};
        Integer valueOf = Integer.valueOf(3);
        Object[] objArr2 = new Object[3];
        objArr2[0] = "10";
        objArr2[1] = VARIABLE_LENGTH;
        objArr2[2] = Integer.valueOf(20);
        r0[3] = objArr2;
        objArr2 = new Object[2];
        objArr2[0] = "11";
        objArr2[1] = Integer.valueOf(6);
        r0[4] = objArr2;
        r0[5] = new Object[]{"12", r13};
        r0[6] = new Object[]{"13", r13};
        r0[7] = new Object[]{"15", r13};
        r0[8] = new Object[]{"17", r13};
        r0[9] = new Object[]{"20", Integer.valueOf(2)};
        r0[10] = new Object[]{"21", VARIABLE_LENGTH, r11};
        r0[11] = new Object[]{"22", VARIABLE_LENGTH, Integer.valueOf(29)};
        r0[12] = new Object[]{"30", VARIABLE_LENGTH, Integer.valueOf(8)};
        objArr2 = new Object[]{"37", VARIABLE_LENGTH, Integer.valueOf(8)};
        Integer valueOf2 = Integer.valueOf(13);
        r0[13] = objArr2;
        objArr2 = new Object[3];
        objArr2[0] = "90";
        objArr2[1] = VARIABLE_LENGTH;
        objArr2[2] = Integer.valueOf(30);
        r0[14] = objArr2;
        objArr2 = new Object[]{"91", VARIABLE_LENGTH, r20};
        Integer valueOf3 = Integer.valueOf(15);
        r0[15] = objArr2;
        r0[16] = new Object[]{"92", VARIABLE_LENGTH, r20};
        r0[17] = new Object[]{"93", VARIABLE_LENGTH, r20};
        r0[18] = new Object[]{"94", VARIABLE_LENGTH, r20};
        r0[19] = new Object[]{"95", VARIABLE_LENGTH, r20};
        r0[20] = new Object[]{"96", VARIABLE_LENGTH, r20};
        r0[21] = new Object[]{"97", VARIABLE_LENGTH, r20};
        r0[22] = new Object[]{"98", VARIABLE_LENGTH, r20};
        r0[23] = new Object[]{"99", VARIABLE_LENGTH, r20};
        TWO_DIGIT_DATA_LENGTH = r0;
        r0 = new Object[23][];
        r0[0] = new Object[]{"240", VARIABLE_LENGTH, r20};
        r0[1] = new Object[]{"241", VARIABLE_LENGTH, r20};
        r0[2] = new Object[]{"242", VARIABLE_LENGTH, r13};
        r0[3] = new Object[]{"250", VARIABLE_LENGTH, r20};
        r0[4] = new Object[]{"251", VARIABLE_LENGTH, r20};
        r0[5] = new Object[]{"253", VARIABLE_LENGTH, Integer.valueOf(17)};
        r0[6] = new Object[]{"254", VARIABLE_LENGTH, r11};
        r0[7] = new Object[]{"400", VARIABLE_LENGTH, r20};
        r0[8] = new Object[]{"401", VARIABLE_LENGTH, r20};
        r0[9] = new Object[]{"402", Integer.valueOf(17)};
        r0[10] = new Object[]{"403", VARIABLE_LENGTH, r20};
        r0[11] = new Object[]{"410", valueOf2};
        r0[12] = new Object[]{"411", valueOf2};
        r0[13] = new Object[]{"412", valueOf2};
        r0[14] = new Object[]{"413", valueOf2};
        r0[15] = new Object[]{"414", valueOf2};
        r0[16] = new Object[]{"420", VARIABLE_LENGTH, r11};
        r0[17] = new Object[]{"421", VARIABLE_LENGTH, valueOf3};
        r0[18] = new Object[]{"422", valueOf};
        r0[19] = new Object[]{"423", VARIABLE_LENGTH, valueOf3};
        r0[20] = new Object[]{"424", valueOf};
        r0[21] = new Object[]{"425", valueOf};
        r0[22] = new Object[]{"426", valueOf};
        THREE_DIGIT_DATA_LENGTH = r0;
        r0 = new Object[57][];
        r0[0] = new Object[]{"310", r13};
        r0[1] = new Object[]{"311", r13};
        r0[2] = new Object[]{"312", r13};
        r0[3] = new Object[]{"313", r13};
        r0[4] = new Object[]{"314", r13};
        r0[5] = new Object[]{"315", r13};
        r0[6] = new Object[]{"316", r13};
        r0[7] = new Object[]{"320", r13};
        r0[8] = new Object[]{"321", r13};
        r0[9] = new Object[]{"322", r13};
        r0[10] = new Object[]{"323", r13};
        r0[11] = new Object[]{"324", r13};
        r0[12] = new Object[]{"325", r13};
        r0[13] = new Object[]{"326", r13};
        r0[14] = new Object[]{"327", r13};
        r0[15] = new Object[]{"328", r13};
        r0[16] = new Object[]{"329", r13};
        r0[17] = new Object[]{"330", r13};
        r0[18] = new Object[]{"331", r13};
        r0[19] = new Object[]{"332", r13};
        r0[20] = new Object[]{"333", r13};
        r0[21] = new Object[]{"334", r13};
        r0[22] = new Object[]{"335", r13};
        r0[23] = new Object[]{"336", r13};
        r0[24] = new Object[]{"340", r13};
        r0[25] = new Object[]{"341", r13};
        r0[26] = new Object[]{"342", r13};
        r0[27] = new Object[]{"343", r13};
        r0[28] = new Object[]{"344", r13};
        r0[29] = new Object[]{"345", r13};
        r0[30] = new Object[]{"346", r13};
        r0[31] = new Object[]{"347", r13};
        r0[32] = new Object[]{"348", r13};
        r0[33] = new Object[]{"349", r13};
        r0[34] = new Object[]{"350", r13};
        r0[35] = new Object[]{"351", r13};
        r0[36] = new Object[]{"352", r13};
        r0[37] = new Object[]{"353", r13};
        r0[38] = new Object[]{"354", r13};
        r0[39] = new Object[]{"355", r13};
        r0[40] = new Object[]{"356", r13};
        r0[41] = new Object[]{"357", r13};
        r0[42] = new Object[]{"360", r13};
        r0[43] = new Object[]{"361", r13};
        r0[44] = new Object[]{"362", r13};
        r0[45] = new Object[]{"363", r13};
        r0[46] = new Object[]{"364", r13};
        r0[47] = new Object[]{"365", r13};
        r0[48] = new Object[]{"366", r13};
        r0[49] = new Object[]{"367", r13};
        r0[50] = new Object[]{"368", r13};
        r0[51] = new Object[]{"369", r13};
        r0[52] = new Object[]{"390", VARIABLE_LENGTH, valueOf3};
        r0[53] = new Object[]{"391", VARIABLE_LENGTH, r5};
        r0[54] = new Object[]{"392", VARIABLE_LENGTH, valueOf3};
        r0[55] = new Object[]{"393", VARIABLE_LENGTH, r5};
        r0[56] = new Object[]{"703", VARIABLE_LENGTH, r20};
        THREE_DIGIT_PLUS_DIGIT_DATA_LENGTH = r0;
        r0 = new Object[18][];
        r0[0] = new Object[]{"7001", valueOf2};
        r0[1] = new Object[]{"7002", VARIABLE_LENGTH, r20};
        r0[2] = new Object[]{"7003", Integer.valueOf(10)};
        r0[3] = new Object[]{"8001", Integer.valueOf(14)};
        r0[4] = new Object[]{"8002", VARIABLE_LENGTH, r11};
        r0[5] = new Object[]{"8003", VARIABLE_LENGTH, r20};
        r0[6] = new Object[]{"8004", VARIABLE_LENGTH, r20};
        r0[7] = new Object[]{"8005", r13};
        r0[8] = new Object[]{"8006", r5};
        r0[9] = new Object[]{"8007", VARIABLE_LENGTH, r20};
        r0[10] = new Object[]{"8008", VARIABLE_LENGTH, Integer.valueOf(12)};
        r0[11] = new Object[]{"8018", r5};
        r0[12] = new Object[]{"8020", VARIABLE_LENGTH, Integer.valueOf(25)};
        r0[13] = new Object[]{"8100", r13};
        r0[14] = new Object[]{"8101", Integer.valueOf(10)};
        r0[15] = new Object[]{"8102", Integer.valueOf(2)};
        r0[16] = new Object[]{"8110", VARIABLE_LENGTH, Integer.valueOf(70)};
        r0[17] = new Object[]{"8200", VARIABLE_LENGTH, Integer.valueOf(70)};
        FOUR_DIGIT_DATA_LENGTH = r0;
    }

    private FieldParser() {
    }

    static String parseFieldsInGeneralPurpose(String str) throws NotFoundException {
        if (str.isEmpty()) {
            return null;
        }
        if (str.length() >= 2) {
            String substring = str.substring(0, 2);
            Object[][] objArr = TWO_DIGIT_DATA_LENGTH;
            int length = objArr.length;
            int i = 0;
            while (i < length) {
                Object[] objArr2 = objArr[i];
                if (!objArr2[0].equals(substring)) {
                    i++;
                } else if (objArr2[1] == VARIABLE_LENGTH) {
                    return processVariableAI(2, ((Integer) objArr2[2]).intValue(), str);
                } else {
                    return processFixedAI(2, ((Integer) objArr2[1]).intValue(), str);
                }
            }
            if (str.length() >= 3) {
                Object[] objArr3;
                substring = str.substring(0, 3);
                Object[][] objArr4 = THREE_DIGIT_DATA_LENGTH;
                i = objArr4.length;
                int i2 = 0;
                while (i2 < i) {
                    objArr3 = objArr4[i2];
                    if (!objArr3[0].equals(substring)) {
                        i2++;
                    } else if (objArr3[1] == VARIABLE_LENGTH) {
                        return processVariableAI(3, ((Integer) objArr3[2]).intValue(), str);
                    } else {
                        return processFixedAI(3, ((Integer) objArr3[1]).intValue(), str);
                    }
                }
                objArr = THREE_DIGIT_PLUS_DIGIT_DATA_LENGTH;
                length = objArr.length;
                i = 0;
                while (i < length) {
                    objArr3 = objArr[i];
                    if (!objArr3[0].equals(substring)) {
                        i++;
                    } else if (objArr3[1] == VARIABLE_LENGTH) {
                        return processVariableAI(4, ((Integer) objArr3[2]).intValue(), str);
                    } else {
                        return processFixedAI(4, ((Integer) objArr3[1]).intValue(), str);
                    }
                }
                if (str.length() >= 4) {
                    substring = str.substring(0, 4);
                    objArr = FOUR_DIGIT_DATA_LENGTH;
                    length = objArr.length;
                    i = 0;
                    while (i < length) {
                        objArr3 = objArr[i];
                        if (!objArr3[0].equals(substring)) {
                            i++;
                        } else if (objArr3[1] == VARIABLE_LENGTH) {
                            return processVariableAI(4, ((Integer) objArr3[2]).intValue(), str);
                        } else {
                            return processFixedAI(4, ((Integer) objArr3[1]).intValue(), str);
                        }
                    }
                    throw NotFoundException.getNotFoundInstance();
                }
                throw NotFoundException.getNotFoundInstance();
            }
            throw NotFoundException.getNotFoundInstance();
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private static String processFixedAI(int i, int i2, String str) throws NotFoundException {
        if (str.length() >= i) {
            String substring = str.substring(0, i);
            i2 += i;
            if (str.length() >= i2) {
                String substring2 = str.substring(i, i2);
                String substring3 = str.substring(i2);
                StringBuilder stringBuilder = new StringBuilder("(");
                stringBuilder.append(substring);
                stringBuilder.append(')');
                stringBuilder.append(substring2);
                substring2 = stringBuilder.toString();
                substring3 = parseFieldsInGeneralPurpose(substring3);
                if (substring3 == null) {
                    return substring2;
                }
                stringBuilder = new StringBuilder();
                stringBuilder.append(substring2);
                stringBuilder.append(substring3);
                return stringBuilder.toString();
            }
            throw NotFoundException.getNotFoundInstance();
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private static String processVariableAI(int i, int i2, String str) throws NotFoundException {
        String substring = str.substring(0, i);
        i2 += i;
        if (str.length() < i2) {
            i2 = str.length();
        }
        String substring2 = str.substring(i, i2);
        String substring3 = str.substring(i2);
        StringBuilder stringBuilder = new StringBuilder("(");
        stringBuilder.append(substring);
        stringBuilder.append(')');
        stringBuilder.append(substring2);
        substring2 = stringBuilder.toString();
        substring3 = parseFieldsInGeneralPurpose(substring3);
        if (substring3 == null) {
            return substring2;
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append(substring2);
        stringBuilder.append(substring3);
        return stringBuilder.toString();
    }
}
