package com.example.orientation.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.example.orientation.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPref();
        setContentView(R.layout.activity_login);
        SignInFragment fragment = new SignInFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.conta,fragment)
                .commit();
    }
    private void getPref() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String mode = preferences.getString("Mode", null);
        if (mode == null) {
            setTheme(R.style.AppTheme);
        } else if (mode.equals("Dark")) {
            setTheme(R.style.AppTheme);
        } else if (mode.equals("Light")) {
            setTheme(R.style.lightTheme);
        }
    }
}
