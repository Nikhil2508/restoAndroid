package com.example.administrator.restoapp.utils;
// Created by kamlesh on 8/7/16, 12:13 PM 


import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefUtils {


	private static final String PREF_DEFAULT = "user_data";

	public static void put(Context context, String key, boolean value) {
		SharedPreferences preferences = context.getSharedPreferences(PREF_DEFAULT, 0);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putBoolean(key, value);
		editor.apply();
	}

	public static void put(Context context, String key, int value) {
		SharedPreferences preferences = context.getSharedPreferences(PREF_DEFAULT, 0);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putInt(key, value);
		editor.apply();
	}

	public static void put(Context context, String key, String value) {
		SharedPreferences preferences = context.getSharedPreferences(PREF_DEFAULT, 0);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString(key, value);
		editor.apply();
	}

	public static boolean getBoolean(Context context, String key) {
		SharedPreferences preferences = context.getSharedPreferences(PREF_DEFAULT, 0);
		return preferences.getBoolean(key, false);
	}

	public static int getInt(Context context, String key) {
		SharedPreferences preferences = context.getSharedPreferences(PREF_DEFAULT, 0);
		return preferences.getInt(key, -1);
	}

	/* returns empty string if given key has no value */
	public static String getString(Context context, String key) {
		SharedPreferences preferences = context.getSharedPreferences(PREF_DEFAULT, 0);
		return preferences.getString(key, "");
	}

	public static String getString(Context context, String key, String defaultValue) {
		SharedPreferences preferences = context.getSharedPreferences(PREF_DEFAULT, 0);
		return preferences.getString(key, defaultValue);
	}

	public static void remove(Context context, String key) {
		SharedPreferences preferences = context.getSharedPreferences(PREF_DEFAULT, 0);
		SharedPreferences.Editor editor = preferences.edit();
		editor.remove(key);
		editor.apply();
	}

	public static void clear(Context context, String prefName) {
		SharedPreferences preferences = context.getSharedPreferences(prefName, 0);
		SharedPreferences.Editor editor = preferences.edit();
		editor.clear();
		editor.apply();
	}
public static void clear(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(PREF_DEFAULT, 0);
		SharedPreferences.Editor editor = preferences.edit();
		editor.clear();
		editor.apply();
	}

}
