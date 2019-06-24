package org;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.gun0912.tedpicker.Config;
import com.gun0912.tedpicker.ImagePickerActivity;

import org.Utils.BitmapUtils;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MyInfoActivity extends AppCompatActivity {

    private final int INTENT_REQUEST_GET_IMAGES = 13;


    ImageView imageView;
    TextView nameTv;
    TextView introTv;
    TextView logoutB;
    TextView passwordB;
    TextView outB;
    ImageButton imageButton;

    int mSelectdContentArray;
    int mChoicedArrayItem;

    private static final int REQUEST_TAKE_PHOTO = 2222;
    private static final int REQUEST_TAKE_ALBUM = 3333;
    private static final int REQUEST_IMAGE_CROP = 4444;
    private static final int REQUEST_IMAGE_FILTER = 5555;

    String mCurrentPhotoPath;

    ArrayList<Uri> image_uris = new ArrayList<Uri>();
    Uri imageUri;
    Uri photoURI, albumURI;

    SharedPreferences pref;
    Bitmap bitmap;
    String nowId;
    String userImg2;    // 서버에서 불러온 지금 현재 로그인된 아이디 프로필사진 url

    FiltersListFragment filtersListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);

        Toolbar toolbar = findViewById(R.id.toolBar);                                                // 위에 툴바 이름 바꾸기
        setSupportActionBar(toolbar);                                                                // 위에 툴바 이름 바꾸기
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);                                       // 위에 툴바 이름 바꾸기
        getSupportActionBar().setTitle("내 정보");                                                   // 위에 툴바 이름 바꾸기

        imageView = findViewById(R.id.imageView);

        imageView.setBackgroundDrawable(new ShapeDrawable(new OvalShape()));                           // 사진 동그랗게 해주는 거임!!

        if (Build.VERSION.SDK_INT >= 21) {
            imageView.setClipToOutline(true);
        }

        nameTv = findViewById(R.id.nameTv);
        introTv = findViewById(R.id.introTv);
        logoutB = findViewById(R.id.logoutB);
        passwordB = findViewById(R.id.passwordB);
        outB = findViewById(R.id.outB);


        imageButton = findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // filtersListFragment.displayThumbnail2(bitmap);

                Intent intent = new Intent(getApplicationContext(), ImgFilterActivity.class);
                startActivityForResult(intent, REQUEST_IMAGE_FILTER);

            }
        });


    }  // onCreate 끝

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }



    public void profileView() {

        pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        nowId = pref.getString("nowId", "");

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    userImg2 = jsonResponse.getString("userImg");

                    Thread mThread = new Thread() {
                        @Override
                        public void run() {
                            try {
                                URL url = new URL(userImg2);

                                //Web에서 이미지를 가져온 뒤 ImageView에 지정할 Bitmap을 만든다
                                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                conn.setDoInput(true);  // 서버로 부터 응답 수신
                                conn.connect();

                                InputStream is = conn.getInputStream();  //InputStream 값 가져오기
                                bitmap = BitmapFactory.decodeStream(is); //Bitmap 으로 변환

                            } catch (MalformedURLException e) {
                                e.printStackTrace();

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    mThread.start(); // Thread 실행
                    try {
                        mThread.join();
                        imageView.setImageBitmap(bitmap);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "aaaaaaaaaaa", Toast.LENGTH_SHORT).show();
                }
            }
        };

        ProfileRequest profileRequest = new ProfileRequest(nowId, responseListener);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(profileRequest);

    }


    @Override
    public void onResume() {
        super.onResume();

        profileView();

    }



}


