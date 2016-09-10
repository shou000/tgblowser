package com.example.owner.tgblowser;

import android.os.Bundle;

/**
 * Created by owner on 2016/09/11.
 */
public class PreferenceFragment extends android.preference.PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference);
    }
}
