package com.airbnb.lottie.parser;

import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.FontCharacter;
import com.airbnb.lottie.model.content.ShapeGroup;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.airbnb.lottie.parser.moshi.JsonReader.Options;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class FontCharacterParser {
    private static final Options DATA_NAMES = Options.of("shapes");
    private static final Options NAMES = Options.of("ch", "size", "w", "style", "fFamily", "data");

    private FontCharacterParser() {
    }

    static FontCharacter parse(JsonReader jsonReader, LottieComposition lottieComposition) throws IOException {
        List arrayList = new ArrayList();
        jsonReader.beginObject();
        String str = null;
        String str2 = str;
        double d = 0.0d;
        double d2 = d;
        char c = 0;
        while (jsonReader.hasNext()) {
            int selectName = jsonReader.selectName(NAMES);
            if (selectName == 0) {
                c = jsonReader.nextString().charAt(0);
            } else if (selectName == 1) {
                d = jsonReader.nextDouble();
            } else if (selectName == 2) {
                d2 = jsonReader.nextDouble();
            } else if (selectName == 3) {
                str = jsonReader.nextString();
            } else if (selectName == 4) {
                str2 = jsonReader.nextString();
            } else if (selectName != 5) {
                jsonReader.skipName();
                jsonReader.skipValue();
            } else {
                jsonReader.beginObject();
                while (jsonReader.hasNext()) {
                    if (jsonReader.selectName(DATA_NAMES) != 0) {
                        jsonReader.skipName();
                        jsonReader.skipValue();
                    } else {
                        jsonReader.beginArray();
                        while (jsonReader.hasNext()) {
                            arrayList.add((ShapeGroup) ContentModelParser.parse(jsonReader, lottieComposition));
                        }
                        jsonReader.endArray();
                    }
                }
                jsonReader.endObject();
            }
        }
        jsonReader.endObject();
        return new FontCharacter(arrayList, c, d, d2, str, str2);
    }
}
