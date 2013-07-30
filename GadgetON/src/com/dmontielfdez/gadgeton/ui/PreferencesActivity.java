package com.dmontielfdez.gadgeton.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;

import com.dmontielfdez.gadgeton.R;

public class PreferencesActivity extends PreferenceActivity{

	EditTextPreference serverDirection;
	
	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    addPreferencesFromResource(R.xml.preferences);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

	    boolean autoStart = sharedPreferences.getBoolean("pref_boot_startup", true);
	    
	    Log.i("auto", autoStart+"");
	}

}
