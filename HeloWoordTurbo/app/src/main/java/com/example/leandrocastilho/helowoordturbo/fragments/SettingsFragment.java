package com.example.leandrocastilho.helowoordturbo.fragments;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.example.leandrocastilho.helowoordturbo.R;

public class SettingsFragment extends PreferenceFragment {

    private String title;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.fragment_preferences);
        getActivity().setTitle("Configurações");
    }
}
