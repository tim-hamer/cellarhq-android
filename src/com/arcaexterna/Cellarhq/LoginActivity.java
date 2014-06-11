package com.arcaexterna.Cellarhq;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LoginActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
    }

    public void loginButtonClicked(View view) {
        Intent cellarIntent = new Intent(LoginActivity.this, CellarActivity.class);
        startActivity(cellarIntent);
    }
}
