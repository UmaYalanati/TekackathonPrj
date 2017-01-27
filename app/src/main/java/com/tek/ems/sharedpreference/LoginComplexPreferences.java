package com.tek.ems.sharedpreference;

/**
 * Created by uyalanat on 21-11-2016.
 */

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;


/**
 * This class helps to store and retrive class object in the shared preferences
 */

public class LoginComplexPreferences {

    private static LoginComplexPreferences loginComplexPreferences;
    private static Gson GSON = new Gson();
    Type typeOfObject = new TypeToken<Object>() {
    }.getType();
    private Context context;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private LoginComplexPreferences(Context context, String namePreferences, int mode) {
        this.context = context;
        if (namePreferences == null || namePreferences.equals("")) {
            namePreferences = "complex_preferences";
        }
        preferences = context.getSharedPreferences(namePreferences, mode);
        editor = preferences.edit();
    }

    public static LoginComplexPreferences getComplexPreferences(Context context, String namePreferences, int mode) {

//      if (loginComplexPreferences == null) {
        loginComplexPreferences = new LoginComplexPreferences(context,
                namePreferences, mode);
//      }

        return loginComplexPreferences;
    }

    public void putObject(String key, Object object) {
        if (object == null) {
            throw new IllegalArgumentException("object is null");
        }

        if (key.equals("") || key == null) {
            throw new IllegalArgumentException("key is empty or null");
        }

        editor.putString(key, GSON.toJson(object));
    }

    public void commit() {
        editor.commit();
    }

    public void clearObject() {
        editor.clear();
    }

    public <T> T getObject(String key, Class<T> a) {

        String gson = preferences.getString(key, null);
        if (gson == null) {
            return null;
        } else {
            try {
                return GSON.fromJson(gson, a);
            } catch (Exception e) {
                throw new IllegalArgumentException("Object storaged with key " + key + " is instanceof other class");
            }
        }
    }


}
