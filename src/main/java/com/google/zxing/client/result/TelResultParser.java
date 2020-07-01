package com.google.zxing.client.result;

import com.google.zxing.Result;

public final class TelResultParser extends ResultParser {
    public TelParsedResult parse(Result result) {
        String massagedText = ResultParser.getMassagedText(result);
        String str = "tel:";
        String str2 = "TEL:";
        if (!massagedText.startsWith(str) && !massagedText.startsWith(str2)) {
            return null;
        }
        if (massagedText.startsWith(str2)) {
            StringBuilder stringBuilder = new StringBuilder(str);
            stringBuilder.append(massagedText.substring(4));
            str = stringBuilder.toString();
        } else {
            str = massagedText;
        }
        int indexOf = massagedText.indexOf(63, 4);
        return new TelParsedResult(indexOf < 0 ? massagedText.substring(4) : massagedText.substring(4, indexOf), str, null);
    }
}
