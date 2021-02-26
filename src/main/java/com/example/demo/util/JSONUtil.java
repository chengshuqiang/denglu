package com.example.demo.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JSONUtil {

    public static boolean isNull(String json) {
        return json == null || json.equals("");
    }

    public static boolean isFieldNull(String json, String field) {
        if (isNull(json)) {
            return true;
        }
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        return jsonObject.get(field) == null || jsonObject.get(field).isJsonNull();
    }

    public static boolean isIdNull(String json) {
        return isFieldNull(json, "id");
    }
}
