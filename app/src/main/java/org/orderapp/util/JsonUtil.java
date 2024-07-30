package org.orderapp.util;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * JSON工具类
 */
public class JsonUtil {
    private static Gson mGson = null;

    static {
        mGson = new Gson();
    }

    public static Gson getmGson() {
        return mGson;
    }

    public static <T> T fromJson(String json, Class<T> classOfT) {
        return mGson.fromJson(json, classOfT);
    }

    public static String toJson(Object object) {
        return mGson.toJson(object);
    }

}
