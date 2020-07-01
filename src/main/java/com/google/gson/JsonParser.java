package com.google.gson;

import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import java.io.Reader;
import java.io.StringReader;

public final class JsonParser {
    public JsonElement parse(String str) throws JsonSyntaxException {
        return parse(new StringReader(str));
    }

    public JsonElement parse(Reader reader) throws JsonIOException, JsonSyntaxException {
        try {
            JsonReader jsonReader = new JsonReader(reader);
            JsonElement parse = parse(jsonReader);
            if (parse.isJsonNull() || jsonReader.peek() == JsonToken.END_DOCUMENT) {
                return parse;
            }
            throw new JsonSyntaxException("Did not consume the entire document.");
        } catch (Throwable e) {
            throw new JsonSyntaxException(e);
        } catch (Throwable e2) {
            throw new JsonIOException(e2);
        } catch (Throwable e22) {
            throw new JsonSyntaxException(e22);
        }
    }

    public JsonElement parse(JsonReader jsonReader) throws JsonIOException, JsonSyntaxException {
        StringBuilder stringBuilder;
        String str = " to Json";
        String str2 = "Failed parsing JSON source: ";
        boolean isLenient = jsonReader.isLenient();
        jsonReader.setLenient(true);
        try {
            str = Streams.parse(jsonReader);
            jsonReader.setLenient(isLenient);
            return str;
        } catch (Throwable e) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(str2);
            stringBuilder.append(jsonReader);
            stringBuilder.append(str);
            throw new JsonParseException(stringBuilder.toString(), e);
        } catch (Throwable e2) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(str2);
            stringBuilder.append(jsonReader);
            stringBuilder.append(str);
            throw new JsonParseException(stringBuilder.toString(), e2);
        } catch (Throwable th) {
            jsonReader.setLenient(isLenient);
        }
    }
}
