package com.tek.ems.emsconstants;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


import java.util.HashMap;

public class SharedPreferenceUtils {

	private static SharedPreferenceUtils sharedPreferenceUtils;
	private static HashMap<Object, Object> cache = null;
	private static SharedPreferences sharedPreferencesSettings = null;
	private static SharedPreferences sharedPreferencesLogin = null;
	private static Editor editorSettings = null;
	private static Editor editorSplash = null;
	private static Editor editorLogin = null;
	private static int cacheCapacity = 10;
	private static SharedPreferences sharedPreferencesSplash;
	private static Context context;

	public static SharedPreferenceUtils getInstance(Context context) {

		SharedPreferenceUtils.context = context;

		if (sharedPreferenceUtils == null)
			sharedPreferenceUtils = new SharedPreferenceUtils();

		if (cache == null)
			cache = new HashMap<Object, Object>(cacheCapacity);

		if (sharedPreferencesSettings == null)
			sharedPreferencesSettings = context.getSharedPreferences(EmsConstants.SHARED_PREF_SETTINGS, Activity.MODE_PRIVATE);

		if (sharedPreferencesLogin == null)
			sharedPreferencesLogin = context.getSharedPreferences(EmsConstants.SHARED_PREF_LOGIN, Activity.MODE_PRIVATE);

		if (sharedPreferencesSplash == null)
			sharedPreferencesSplash = context.getSharedPreferences(EmsConstants.SHARED_PREF_NAME, Activity.MODE_PRIVATE);

		return sharedPreferenceUtils;
	}

	//Cache Items
	/*******************************************************************************************************************/

	/**
	 * Method to get Cache Items
	 *
	 * @param cacheKey
	 * @return
	 */
	public Object getCacheItem(Object cacheKey) {
		if (cache != null && cache.get(cacheKey) != null)
			return cache.get(cacheKey);
		else
			return null;
	}


	/**
	 * Method to add Cache Items
	 *
	 * @param cacheKey
	 * @param cacheValue
	 * @return
	 */
	public SharedPreferenceUtils addCacheItem(Object cacheKey, Object cacheValue) {
		if (cache != null)
			cache.put(cacheKey, cacheValue);

		return sharedPreferenceUtils;
	}

	/*******************************************************************************************************************/
	//Settings Cache Items

	/*******************************************************************************************************************/
	public Object getSettingsCacheItem(Object cacheKey) {
		if (sharedPreferencesSettings != null && sharedPreferencesSettings.getString(cacheKey.toString(), "") != null) {
			return sharedPreferencesSettings.getString(cacheKey.toString(), "");
		} else
			return null;
	}


	public SharedPreferenceUtils addSettingsCacheItem(Object cacheKey, Object cacheValue) {
		if (editorSettings == null)
			editorSettings = sharedPreferencesSettings.edit();

		editorSettings.putString(cacheKey.toString(), String.valueOf(cacheValue));

		return sharedPreferenceUtils;
	}

	public SharedPreferenceUtils editSettings() {
		editorSettings = sharedPreferencesSettings.edit();
		return sharedPreferenceUtils;
	}

	public SharedPreferenceUtils commitSettings() {
		editorSettings.commit();

		return sharedPreferenceUtils;
	}

	/*******************************************************************************************************************/
	//Login Cache Items

	/*******************************************************************************************************************/
	public Object getLoginCacheItem(Object cacheKey) {
		if (sharedPreferencesLogin != null && sharedPreferencesLogin.getString(cacheKey.toString(), "") != null)
			return sharedPreferencesLogin.getString(cacheKey.toString(), "");
		else
			return null;
	}

	public SharedPreferenceUtils addLoginCacheItem(Object cacheKey, Object cacheValue) {
		if (editorLogin == null)
			editorLogin = sharedPreferencesLogin.edit();

		if (cacheValue != null)
			editorLogin.putString(cacheKey.toString(), cacheValue.toString());

		else
			editorLogin.putString(cacheKey.toString(), null);

		return sharedPreferenceUtils;
	}

	public SharedPreferenceUtils editLogin() {
		editorLogin = sharedPreferencesLogin.edit();
		return sharedPreferenceUtils;
	}

	public SharedPreferenceUtils commitLogin() {
		editorLogin.commit();
		return sharedPreferenceUtils;
	}

	/*******************************************************************************************************************/
	//Splah Cache Items

	/*******************************************************************************************************************/

	public Object getSplashCacheItem(Object cacheKey) {
		if (sharedPreferencesSplash != null && sharedPreferencesSplash.getString(cacheKey.toString(), "") != null)
			return sharedPreferencesSplash.getString(cacheKey.toString(), "");
		else
			return null;
	}

	public Boolean splashContains(String cacheKey) {
		if (sharedPreferencesSplash != null)
			return sharedPreferencesSplash.contains(cacheKey);
		else
			return false;
	}

	public boolean getBooleanSplashCacheItem(Object cacheKey) {
		if (sharedPreferencesSplash != null)
			return sharedPreferencesSplash.getBoolean(cacheKey.toString(), false);
		else
			return false;
	}


	public SharedPreferenceUtils addSplashCacheItem(Object cacheKey, Object cacheValue) {
		if (editorSplash == null)
			editorSplash = sharedPreferencesSplash.edit();

		if (cacheValue instanceof String)
			editorSplash.putString(cacheKey.toString(), String.valueOf(cacheValue));

		else if (cacheValue instanceof Boolean)
			editorSplash.putBoolean(cacheKey.toString(), (Boolean) cacheValue);

		return sharedPreferenceUtils;
	}

	public SharedPreferenceUtils editSplash() {
		editorSplash = sharedPreferencesSplash.edit();

		return sharedPreferenceUtils;
	}

	public SharedPreferenceUtils commitSplash() {
		editorSplash.commit();

		return sharedPreferenceUtils;
	}

	/*******************************************************************************************************************/




	/*******************************************************************************************************************/
}