package com.google.zxing.client.result;

import com.google.zxing.Result;

public final class WifiResultParser extends ResultParser {
    public WifiParsedResult parse(Result result) {
        String massagedText = ResultParser.getMassagedText(result);
        if (!massagedText.startsWith("WIFI:")) {
            return null;
        }
        massagedText = massagedText.substring(5);
        String matchSinglePrefixedField = ResultParser.matchSinglePrefixedField("S:", massagedText, ';', false);
        if (matchSinglePrefixedField == null || matchSinglePrefixedField.isEmpty()) {
            return null;
        }
        String matchSinglePrefixedField2 = ResultParser.matchSinglePrefixedField("P:", massagedText, ';', false);
        String matchSinglePrefixedField3 = ResultParser.matchSinglePrefixedField("T:", massagedText, ';', false);
        if (matchSinglePrefixedField3 == null) {
            matchSinglePrefixedField3 = "nopass";
        }
        String str = matchSinglePrefixedField3;
        matchSinglePrefixedField3 = "H:";
        return new WifiParsedResult(str, matchSinglePrefixedField, matchSinglePrefixedField2, Boolean.parseBoolean(ResultParser.matchSinglePrefixedField(matchSinglePrefixedField3, massagedText, ';', false)), ResultParser.matchSinglePrefixedField("I:", massagedText, ';', false), ResultParser.matchSinglePrefixedField("A:", massagedText, ';', false), ResultParser.matchSinglePrefixedField("E:", massagedText, ';', false), ResultParser.matchSinglePrefixedField(matchSinglePrefixedField3, massagedText, ';', false));
    }
}
