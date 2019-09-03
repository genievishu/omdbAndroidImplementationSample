package com.example.myimdb.utils;

import com.google.gson.Gson;

/**
 * Created by Vishu on 02-09-2019
 */
public class AppUtils {

    private static Gson gson;

    public static Gson getGson() {
        if (null==gson) {
            gson = new Gson();
        }
        return gson;
    }

}
