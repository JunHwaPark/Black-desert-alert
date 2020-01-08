package com.junhwa.blackdesertalert.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.tabs.TabLayout;
import com.junhwa.blackdesertalert.R;
import com.junhwa.blackdesertalert.ui.fragment.ArmorFragment;
import com.junhwa.blackdesertalert.ui.fragment.WeaponFragment;

public class CalculatorActivity extends AppCompatActivity {
    Fragment weapon, armor;
    FragmentManager manager = null;
    TabLayout tabs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        final InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        weapon = new WeaponFragment();
        armor = new ArmorFragment();

        try {
            manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.container, weapon).commit();
        } catch (Exception e) {
            Log.e("error", e.toString());
        }

        tabs = findViewById(R.id.tabLayout);
        tabs.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment selected = null;

                switch (tab.getPosition()) {
                    case 0:
                        selected = weapon;
                        break;
                    case 1:
                        selected = armor;
                        break;
                }
                View view = getCurrent();
                if (view != null)
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                manager.beginTransaction().replace(R.id.container, selected).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdView adView = findViewById(R.id.adView2);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

    public View getCurrent() {
        View view = this.getCurrentFocus();
        return view;
    }
}
