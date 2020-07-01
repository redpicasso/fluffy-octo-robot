package io.grpc.internal;

import com.google.common.base.Preconditions;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class JsonParser {
    private static final Logger logger = Logger.getLogger(JsonParser.class.getName());

    private JsonParser() {
    }

    public static Object parse(String str) throws IOException {
        String str2 = "Failed to close";
        JsonReader jsonReader = new JsonReader(new StringReader(str));
        try {
            Object parseRecursive = parseRecursive(jsonReader);
            return parseRecursive;
        } finally {
            try {
                jsonReader.close();
            } catch (Throwable e) {
                logger.log(Level.WARNING, str2, e);
            }
        }
    }

    private static Object parseRecursive(JsonReader jsonReader) throws IOException {
        Preconditions.checkState(jsonReader.hasNext(), "unexpected end of JSON");
        switch (jsonReader.peek()) {
            case BEGIN_ARRAY:
                return parseJsonArray(jsonReader);
            case BEGIN_OBJECT:
                return parseJsonObject(jsonReader);
            case STRING:
                return jsonReader.nextString();
            case NUMBER:
                return Double.valueOf(jsonReader.nextDouble());
            case BOOLEAN:
                return Boolean.valueOf(jsonReader.nextBoolean());
            case NULL:
                return parseJsonNull(jsonReader);
            default:
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Bad token: ");
                stringBuilder.append(jsonReader.getPath());
                throw new IllegalStateException(stringBuilder.toString());
        }
    }

    private static Map<String, Object> parseJsonObject(JsonReader jsonReader) throws IOException {
        jsonReader.beginObject();
        Map linkedHashMap = new LinkedHashMap();
        while (jsonReader.hasNext()) {
            linkedHashMap.put(jsonReader.nextName(), parseRecursive(jsonReader));
        }
        boolean z = jsonReader.peek() == JsonToken.END_OBJECT;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Bad token: ");
        stringBuilder.append(jsonReader.getPath());
        Preconditions.checkState(z, stringBuilder.toString());
        jsonReader.endObject();
        return Collections.unmodifiableMap(linkedHashMap);
    }

    private static List<Object> parseJsonArray(JsonReader jsonReader) throws IOException {
        jsonReader.beginArray();
        List arrayList = new ArrayList();
        while (jsonReader.hasNext()) {
            arrayList.add(parseRecursive(jsonReader));
        }
        boolean z = jsonReader.peek() == JsonToken.END_ARRAY;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Bad token: ");
        stringBuilder.append(jsonReader.getPath());
        Preconditions.checkState(z, stringBuilder.toString());
        jsonReader.endArray();
        return Collections.unmodifiableList(arrayList);
    }

    private static Void parseJsonNull(JsonReader jsonReader) throws IOException {
        jsonReader.nextNull();
        return null;
    }
}
