package com.example.passwordlayout;

import android.os.Bundle;
import android.widget.Toast;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

public class SettingFragment extends PreferenceFragmentCompat {

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
                    Toast.makeText(requireContext(), getString(R.string.message_click_copy_mode), Toast.LENGTH_SHORT).show();
                    return true;
                }
            });
        }
    }
}
