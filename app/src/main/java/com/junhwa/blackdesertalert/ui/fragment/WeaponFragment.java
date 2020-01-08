package com.junhwa.blackdesertalert.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.junhwa.blackdesertalert.R;

public class WeaponFragment extends Fragment implements TextWatcher {
    EditText kzarka, nouver, dandelion, coin_have;
    TextView k_need, n_need, d_need, coin_need;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_weapon, container, false);

        kzarka = rootView.findViewById(R.id.editText);
        dandelion = rootView.findViewById(R.id.editText5);
        nouver = rootView.findViewById(R.id.editText6);
        coin_have = rootView.findViewById(R.id.editText8);

        k_need = rootView.findViewById(R.id.textView19);
        d_need = rootView.findViewById(R.id.textView17);
        n_need = rootView.findViewById(R.id.textView18);
        coin_need = rootView.findViewById(R.id.textView20);

        kzarka.addTextChangedListener(this);
        dandelion.addTextChangedListener(this);
        nouver.addTextChangedListener(this);
        coin_have.addTextChangedListener(this);

        return rootView;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        int k, d, n, have;
        try {
            k = Integer.parseInt(kzarka.getText().toString());
            if (k > 900)
                k = 900;
        } catch (Exception e) {
            k = 0;
        }
        try {
            d = Integer.parseInt(dandelion.getText().toString());
            if (d > 900)
                d = 900;
        } catch (Exception e) {
            d = 0;
        }
        try {
            n = Integer.parseInt(nouver.getText().toString());
            if (n > 900)
                n = 900;
        } catch (Exception e) {
            n = 0;
        }
        try {
            have = Integer.parseInt(coin_have.getText().toString());
        } catch (Exception e) {
            have = 0;
        }
        k_need.setText((900 - k) + "");
        d_need.setText((900 - d) + "");
        n_need.setText((900 - n) + "");
        int need = (2700 - k + d + n) * 920;
        coin_need.setText(need - have > 0 ? (need - have) + "" : "0");
    }
}
