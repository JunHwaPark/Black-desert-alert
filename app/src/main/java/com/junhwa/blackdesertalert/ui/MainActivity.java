package com.junhwa.blackdesertalert.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.junhwa.blackdesertalert.R;
import com.junhwa.blackdesertalert.Token;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;

    Token token = new Token();
    TextView textToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        View headerView = navigationView.getHeaderView(0);

        textToken = headerView.findViewById(R.id.txtToken);
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(this,
                new OnSuccessListener<InstanceIdResult>() {
                    @Override
                    public void onSuccess(InstanceIdResult instanceIdResult) {
                        String newToken = instanceIdResult.getToken();
                        textToken.setText("Token : " + newToken);
                    }
                });
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference dbRederence = database.getReference("Token");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);

        drawerLayout = findViewById(R.id.drawer_layout);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_item_update_token:
                        if (textToken.getText().toString().length() < 9){
                            Toast.makeText(getApplicationContext(), "토큰이 발급될 때 까지 기다려 주세요.", Toast.LENGTH_LONG).show();
                            return false;
                        }
                        token.setToken(textToken.getText().toString().substring(8));

                        token.setMac(getMacAddress());
                        if (token.getMac() == null){
                            Toast.makeText(getApplicationContext(), "앱을 다시 실행해 주세요", Toast.LENGTH_LONG).show();
                            finish();
                        }
                        Log.d("Token", token.getToken());
                        dbRederence.child(token.getMac()).setValue(token);
                        Toast.makeText(getApplicationContext(), "토큰이 업데이트 되었습니다.", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.navigation_item_how_to_use:
                        Toast.makeText(getApplicationContext(), "최초 실행시 Token이 생성된 후 \"토큰 업데이트\"버튼을 누르세요. 이후 포럼 알람이 옵니다.", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.nav_sub_menu_open_source:
                        try {
                            startActivity(new Intent(getApplicationContext(), OssLicensesMenuActivity.class));
                        } catch (Exception e) {
                            Log.e("OpenSource", e.toString());
                        }
                        break;
                }
                return true;
            }
        });

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdView adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        Button btnCalc = findViewById(R.id.btnCalc);
        btnCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CalculatorActivity.class);
                startActivity(intent);
            }
        });
    }

    private String getMacAddress(){
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                byte[] mac = intf.getHardwareAddress();
                if (mac == null) {
                    Log.d("MAC", intf.getName() + " Empty MacAddr");
                    continue;
                }
                StringBuilder buf = new StringBuilder();
                for (int idx = 0; idx < mac.length; idx++)
                    buf.append(String.format("%02X:", mac[idx]));
                if (buf.length() > 0) buf.deleteCharAt(buf.length() - 1);
                Log.d("MAC", intf.getName() + " MacAddr = " + buf.toString());
                if (intf.getName().equals("wlan0"))
                    return  buf.toString();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
