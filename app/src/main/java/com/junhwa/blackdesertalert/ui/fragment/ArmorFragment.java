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


public class ArmorFragment extends Fragment implements TextWatcher {
    EditText red, geis, beg, muskan, coin_have;
    TextView r_need, g_need, b_need, m_need, coin_need;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_armor, container, false);

        red = rootView.findViewById(R.id.editText);
        geis = rootView.findViewById(R.id.editText5);
        beg = rootView.findViewById(R.id.editText6);
        muskan = rootView.findViewById(R.id.editText7);
        coin_have = rootView.findViewById(R.id.editText8);

        r_need = rootView.findViewById(R.id.textView16);
        g_need = rootView.findViewById(R.id.textView17);
        b_need = rootView.findViewById(R.id.textView18);
        m_need = rootView.findViewById(R.id.textView19);
        coin_need = rootView.findViewById(R.id.textView20);

        red.addTextChangedListener(this);
        geis.addTextChangedListener(this);
        beg.addTextChangedListener(this);
        muskan.addTextChangedListener(this);
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
        int r, g, b, m, have;
        try {
            r = Integer.parseInt(red.getText().toString());
            if (r > 240)
                r = 240;
        } catch (Exception e) {
            r = 0;
        }
        try {
            g = Integer.parseInt(geis.getText().toString());
            if (g > 240)
                g = 240;
        } catch (Exception e) {
            g = 0;
        }
        try {
            b = Integer.parseInt(beg.getText().toString());
            if (b > 240)
                b = 240;
        } catch (Exception e) {
            b = 0;
        }
        try {
            m = Integer.parseInt(muskan.getText().toString());
            if (m > 240)
                m = 240;
        } catch (Exception e) {
            m = 0;
        }
        try {
            have = Integer.parseInt(coin_have.getText().toString());
        } catch (Exception e) {
            have = 0;
        }

        r_need.setText((240 - r) + "");
        g_need.setText((240 - g) + "");
        b_need.setText((240 - b) + "");
        m_need.setText((240 - m) + "");
        int need = (240 - r) * 1360 + (240 - g) * 1200 +
                (240 - b) * 1040 + (240 - m) * 1040;
        coin_need.setText(need - have > 0 ? (need - have) + "" : "0");
    }
}
