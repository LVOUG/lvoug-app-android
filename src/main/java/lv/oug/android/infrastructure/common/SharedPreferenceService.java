package lv.oug.android.infrastructure.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Inject;

public class SharedPreferenceService {

    @Inject
    Context context;

    public String loadPreferenceString(String pref) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPrefs.getString(pref, null);
    }

    public Long loadPreferenceLong(String pref) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPrefs.getLong(pref, 0);
    }

    public boolean loadPreferenceBoolean(String pref) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPrefs.getBoolean(pref, false);
    }

    public void savePreference(String pref, String s) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString(pref, s);
        editor.commit();
    }

    public void savePreference(String pref, Boolean b) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putBoolean(pref, b);
        editor.commit();
    }

    public void savePreference(String pref, Long l) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putLong(pref, l);
        editor.commit();
    }
}
