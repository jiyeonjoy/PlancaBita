package org;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import org.MainActivity;

public class LoadingActivity extends Activity {

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
    }