package com.example.passwordlayout;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.Toast;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import static android.content.Context.MODE_PRIVATE;

public class SettingFragment extends PreferenceFragmentCompat {

    public static final String ARG_ROOT_SCREEN = "root";


    public static SettingFragment newInstance() {
        return new SettingFragment();
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.prefs, null);

        Preference copyPreference = findPreference(getString(R.string.pref_copy_key));
        if (copyPreference != null){
            copyPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    Toast.makeText(requireContext(), "Задан режим для копирования в буфер", Toast.LENGTH_SHORT).show();
                    return true;
                }
            });
        }
    }
}
