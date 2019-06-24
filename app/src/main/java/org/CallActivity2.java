package org;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import org.appspot.apprtc.main.AppRTCMainActivity;

public class CallActivity2 extends AppCompatActivity {


    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call2);

        imageView = findViewById(R.id.aaaaa);

        imageView.setBackgroundDrawable(new ShapeDrawable(new OvalShape()));                           // 사진 동그랗게 해주는 거임!!

        if (Build.VERSION.SDK_INT >= 21) {
            imageView.setClipToOutline(true);
        }

        ImageButton button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AppRTCMainActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }  // onCreate 끝



}

