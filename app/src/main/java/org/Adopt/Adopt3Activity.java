package org.Adopt;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import org.ChatActivity;
import org.R;

import org.appspot.apprtc.main.AppRTCMainActivity;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Adopt3Activity extends AppCompatActivity {

    TextView menu1;
    TextView menu2;
    TextView menu3;
    TextView menu4;

    Menu5Fragment menu5Fragment;
    Menu6Fragment menu6Fragment;
    Menu7Fragment menu7Fragment;
    Menu8Fragment menu8Fragment;

    ViewPager vp;
    LinearLayout ll;

    ImageView backKey;
    ImageView loveKey;
    Spinner spinner;
    String[] items = {"수정", "삭제", "선택 안함"};               // spinner 메뉴

    ImageView img1;

    SharedPreferences pref;
    String adopt_id;

    TextView title;
    TextView dogType;
    TextView dogName;
    ImageView gender;
    TextView dogPrice;

    String imgimg;
    Bitmap bitmap;
    String nowId;                // 글쓴아이디
    String nowIdType;            // 글쓴아이디 타입
    String nowId2;               // 현재 로그인 된 아이디
    String nowIdType2;           // 현재 로그인 된 아이디 타입
    int iwrite=0;                // 내가쓴글이면 1, 아니면 0
    int adoptpick=0;             // 찜했으면 1, 아니면 0


    String title2;
    String img2;
    String dogType1;
    String dogName1;
    String gender1;
    String dogPrice1;

    TextView menu5;
    LinearLayout line;

    TextView menu6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adopt3);

        backKey = findViewById(R.id.backKey);
        backKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        line = findViewById(R.id.line);

        pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        adopt_id = pref.getString("adoptView", "");
        nowId2 = pref.getString("nowId", "");
        nowIdType2 = pref.getString("nowLoginType", "");


        title = findViewById(R.id.title);
        img1 = findViewById(R.id.img1);
        dogType = findViewById(R.id.dogType);
        dogName = findViewById(R.id.dogName);
        gender = findViewById(R.id.gender);
        dogPrice = findViewById(R.id.dogPrice);

        loveKey = findViewById(R.id.loveKey);
        spinner = findViewById(R.id.spinner);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(2);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position ==0) {
                    Intent intent = new Intent(getApplicationContext(), Adopt4Activity.class);
                    startActivity(intent);
                    finish();
                } else if(position==1){
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    AdoptDeleteRequest adoptDeleteRequest = new AdoptDeleteRequest(adopt_id, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(Adopt3Activity.this);
                    queue.add(adoptDeleteRequest);

                    finish();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    title2 = jsonResponse.getString("title");
                    img2 = jsonResponse.getString("img");
                    dogType1 = jsonResponse.getString("type");
                    dogName1 = jsonResponse.getString("name");
                    gender1 = jsonResponse.getString("gender");
                    dogPrice1 = jsonResponse.getString("price");
                    nowId = jsonResponse.getString("userid");
                    nowIdType = jsonResponse.getString("useridtype");

                    imgimg = "http://ec2-15-164-102-182.ap-northeast-2.compute.amazonaws.com/planb/"+img2;

                    title.setText(title2);
                    dogType.setText(dogType1);
                    dogName.setText(dogName1);
                    dogPrice.setText(dogPrice1+"원");
                    if(gender1.equals("남아")) {
                        gender.setImageResource(R.drawable.male);
                    } else {
                        gender.setImageResource(R.drawable.female);
                    }

                    if(nowId.equals(nowId2)) {
                        loveKey.setVisibility(View.GONE);
                        spinner.setVisibility(View.VISIBLE);
                        line.setVisibility(View.GONE);
                        iwrite=1;
                    }

                    Thread mThread = new Thread() {
                        @Override
                        public void run() {
                            try {
                                URL url = new URL(imgimg);

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
                        img1.setImageBitmap(bitmap);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "aaaaaaaaaaa", Toast.LENGTH_SHORT).show();
                }
            }
        };

        Adopt1Request adopt1Request = new Adopt1Request(adopt_id, responseListener);
        RequestQueue queue = Volley.newRequestQueue(Adopt3Activity.this);
        queue.add(adopt1Request);


        if(iwrite==0) {

            Response.Listener<String> responseListener2 = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        String okay = jsonResponse.getString("okay");

                        if(okay.equals("okay")) {
                            adoptpick=1;
                            loveKey.setImageResource(R.drawable.love2);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "aaaaaaaaaaa", Toast.LENGTH_SHORT).show();
                    }
                }
            };

            AdoptPickCheckRequest adoptPickCheckRequest = new AdoptPickCheckRequest(adopt_id, nowId2, responseListener2);
            RequestQueue queue2 = Volley.newRequestQueue(Adopt3Activity.this);
            queue2.add(adoptPickCheckRequest);


        }

        img1.setBackgroundDrawable(new ShapeDrawable(new OvalShape()));                           // 사진 동그랗게 해주는 거임!!

        if (Build.VERSION.SDK_INT >= 21) {
            img1.setClipToOutline(true);
        }


        loveKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(adoptpick==1){      // 찜상태  -찜해제해야됨
                    adoptpick=0;
                    loveKey.setImageResource(R.drawable.love);
                    Response.Listener<String> responseListener3 = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    AdoptPickDeleteRequest adoptPickDeleteRequest = new AdoptPickDeleteRequest(adopt_id, nowId2, responseListener3);
                    RequestQueue queue3 = Volley.newRequestQueue(Adopt3Activity.this);
                    queue3.add(adoptPickDeleteRequest);


                }else {                // 찜안된상태    -찜해야됨
                    adoptpick=1;
                    loveKey.setImageResource(R.drawable.love2);

                    Response.Listener<String> responseListener4 = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    AdoptPickRequest adoptPickRequest = new AdoptPickRequest(adopt_id, nowId2, responseListener4);
                    RequestQueue queue4 = Volley.newRequestQueue(Adopt3Activity.this);
                    queue4.add(adoptPickRequest);
                }
            }
        });

        menu1 = findViewById(R.id.menu1);
        menu2 = findViewById(R.id.menu2);
        menu3 = findViewById(R.id.menu3);
        menu4 = findViewById(R.id.menu4);

        vp = findViewById(R.id.vp);
        ll = (LinearLayout)findViewById(R.id.ll);


        menu5Fragment = new Menu5Fragment();
        menu6Fragment = new Menu6Fragment();
        menu7Fragment = new Menu7Fragment();
        menu8Fragment = new Menu8Fragment();


        vp.setAdapter(new Adopt3Activity.pagerAdapter(getSupportFragmentManager()));
        vp.setCurrentItem(0);

        menu1.setOnClickListener(movePageListener);
        menu1.setTag(0);
        menu2.setOnClickListener(movePageListener);
        menu2.setTag(1);
        menu3.setOnClickListener(movePageListener);
        menu3.setTag(2);
        menu4.setOnClickListener(movePageListener);
        menu4.setTag(3);

        menu1.setSelected(true);

        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {

            }

            @Override
            public void onPageSelected(int position)
            {
                int i = 0;
                while(i<4)
                {
                    if(position==i)
                    {
                        ll.findViewWithTag(i).setSelected(true);
                    }
                    else
                    {
                        ll.findViewWithTag(i).setSelected(false);
                    }
                    i++;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state)
            {

            }
        });


        menu5 = findViewById(R.id.menu5);
        menu5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("chatId", nowId);                       // chatId 입력
                editor.commit();

                Log.w("chchchchchchchchchchchc", nowId);

                Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                startActivity(intent);
            }
        });

        menu6 = findViewById(R.id.menu6);
        menu6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("callId", nowId);                       // chatId 입력
                editor.putString("zz", "yy");                       // 통화종료확인
                editor.commit();

                Log.w("chchchchchchchchchchchc", "callllllllll");
                Intent intent = new Intent(getApplicationContext(), AppRTCMainActivity.class);
                startActivity(intent);
            }
        });



    }     // onCreate 끝


    View.OnClickListener movePageListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            int tag = (int) v.getTag();

            int i = 0;
            while(i<4)
            {
                if(tag==i)
                {
                    ll.findViewWithTag(i).setSelected(true);
                }
                else
                {
                    ll.findViewWithTag(i).setSelected(false);
                }
                i++;
            }

            vp.setCurrentItem(tag);
        }
    };

    private class pagerAdapter extends FragmentStatePagerAdapter
    {
        public pagerAdapter(android.support.v4.app.FragmentManager fm)
        {
            super(fm);
        }
        @Override
        public android.support.v4.app.Fragment getItem(int position)
        {
            switch(position)
            {
                case 0:
                    return new Menu5Fragment();
                case 1:
                    return new Menu6Fragment();
                case 2:
                    return new Menu7Fragment();
                case 3:
                    return new Menu8Fragment();
                default:
                    return null;
            }
        }
        @Override
        public int getCount()
        {
            return 4;
        }
    }


}
