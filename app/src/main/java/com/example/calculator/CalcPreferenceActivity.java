package com.example.calculator;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class CalcPreferenceActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_preference2);
        addPreferencesFromResource(R.xml.preferences);
    }
}
