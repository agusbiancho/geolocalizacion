package com.meli.geolocalizacion.util;

import org.json.JSONArray;
import org.json.JSONObject;

public class Parser {

    public static String getValueByKey(String object, String key, boolean isNumeric) {
        JSONObject o = new JSONObject(object);
        if(isNumeric) {
            return Double.toString(o.getDouble(key));
        } else {
            return o.getString(key);
        }
    }

    public static String getValueByKeyFromObject(String object, String keyObject, String key, boolean isNumeric) {
        JSONObject o = new JSONObject(object);
        if(isNumeric) {
            return Double.toString(o.getJSONObject(keyObject).getDouble(key));
        } else {
            return o.getJSONObject(keyObject).getString(key);
        }

    }

    public static String getValueByKeyFromArray(String object, String key) {
        JSONArray a = new JSONArray(object);
        JSONObject o = a.getJSONObject(0).getJSONObject(key);
        return o.keys().next();
    }
}
