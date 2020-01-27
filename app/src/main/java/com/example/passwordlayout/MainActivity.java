package com.example.passwordlayout;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements PasswordFragment.SettingFragmentHolder{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.root_frame_layout, PasswordFragment.newInstance())
                    .commit();
        }
    }

    @Override
    public void showSetting() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.root_frame_layout, SettingFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }
}