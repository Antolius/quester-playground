package com.example.josip.jstest.activities;

import android.app.Activity;
import android.os.Bundle;

import com.example.josip.jstest.MyApplication;

/**
 * Created by Josip on 24/08/2014!
 */
public abstract class InjectionActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);

        //inject dependencies:
        ((MyApplication) getApplication()).inject(this);
    }

}