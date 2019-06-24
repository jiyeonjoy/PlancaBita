package org;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.nex3z.notificationbadge.NotificationBadge;

import org.Adopt.Adopt3Activity;
import org.Adopt.AdoptPickCheckRequest;
import org.Adopt.AdoptPickDeleteRequest;
import org.Adopt.AdoptPickRequest;
import org.Utils.Common;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ShopActivity extends AppCompatActivity {

    ImageView menuImage;
    TextView menu_name;
    TextView menu_price;
    ImageView cartButton;
    ImageView wishButton;

    SharedPreferences pref;
    String nowId;               // 현재 로그인된 아이디

    int shopId;

    Bitmap bitmap;

    int shoppick=0;             // 찜했으면 1, 아니면 0

    NotificationBadge badge;
    int cartCount;             // 카트에 들어있는 상품 갯수
    ImageView cart_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        menuImage = findViewById(R.id.menuImage);
        menu_name = findViewById(R.id.menu_name);
        menu_price = findViewById(R.id.menu_price);
        cartButton = findViewById(R.id.cartButton);
        wishButton = findViewById(R.id.wishButton);

//        Toolbar toolbar = findViewById(R.id.toolBar);                                                // 위에 툴바 이름 바꾸기
//        setSupportActionBar(toolbar);                                                                // 위에 툴바 이름 바꾸기
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);                                       // 위에 툴바 이름 바꾸기
        getSupportActionBar().setTitle("Planca & Bita 수제간식");                                    // 위에 툴바 이름 바꾸기

        pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        nowId = pref.getString("nowId", "");
//        shopId = pref.getString("shopId", "");
        shopId = Common.shopId;

        shopMenuView();


        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    String okay = jsonResponse.getString("okay");
                    cartCount = jsonResponse.getInt("count");
                    updateCartCount();
                    if (okay.equals(nowId)) {
                        shoppick=1;
                        wishButton.setImageResource(R.drawable.love2);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "aaaaaaaaaaa", Toast.LENGTH_SHORT).show();
                }
            }
        };

        ShopWishCheckRequest shopWishCheckRequest = new ShopWishCheckRequest(nowId, shopId + "", responseListener);
        RequestQueue queue = Volley.newRequestQueue(ShopActivity.this);
        queue.add(shopWishCheckRequest);



        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            String okay = jsonResponse.getString("okay");

                            if (okay.equals(nowId)) {
                                Toast.makeText(getApplicationContext(), "이미 장바구니에 추가되었습니다 :)", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(getApplicationContext(), "장바구니에 추가되었습니다 :)", Toast.LENGTH_SHORT).show();
                                cartCount +=1;
                                updateCartCount();
                                Response.Listener<String> responseListener4 = new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                };

                                ShopCartAddRequest shopCartAddRequest = new ShopCartAddRequest(nowId, shopId+"", responseListener4);
                                RequestQueue queue4 = Volley.newRequestQueue(ShopActivity.this);
                                queue4.add(shopCartAddRequest);


                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "aaaaaaaaaaa", Toast.LENGTH_SHORT).show();
                        }
                    }
                };

                ShopCartCheckRequest shopCartCheckRequest = new ShopCartCheckRequest(nowId, shopId + "", responseListener);
                RequestQueue queue = Volley.newRequestQueue(ShopActivity.this);
                queue.add(shopCartCheckRequest);



            }
        });


        wishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(shoppick==1){      // 찜상태  -찜해제해야됨

                    Toast.makeText(getApplicationContext(), "위시리스트에 삭제되었습니다 :)", Toast.LENGTH_SHORT).show();
                    shoppick=0;
                    wishButton.setImageResource(R.drawable.shopzzim);
                    Response.Listener<String> responseListener3 = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    ShopWishDeleteRequest shopWishDeleteRequest = new ShopWishDeleteRequest(nowId, shopId+"", responseListener3);
                    RequestQueue queue3 = Volley.newRequestQueue(ShopActivity.this);
                    queue3.add(shopWishDeleteRequest);


                }else {                // 찜안된상태    -찜해야됨

                    Toast.makeText(getApplicationContext(), "위시리스트에 추가되었습니다 :)", Toast.LENGTH_SHORT).show();
                    shoppick=1;
                    wishButton.setImageResource(R.drawable.love2);

                    Response.Listener<String> responseListener4 = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    ShopWiskRequest shopWiskRequest = new ShopWiskRequest(nowId, shopId+"", responseListener4);
                    RequestQueue queue4 = Volley.newRequestQueue(ShopActivity.this);
                    queue4.add(shopWiskRequest);
                }



            }
        });

    } // onCreate 끝

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{                      //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_action_bar, menu);
        View view = menu.findItem(R.id.cart_menu).getActionView();
        badge = view.findViewById(R.id.badge);
        cart_icon = view.findViewById(R.id.cart_icon);
        cart_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShopActivity.this, ShopCartActivity.class);
                startActivity(intent);
                finish();
            }
        });
        //updateCartCount();
        return true;
    }

    private void updateCartCount() {
        if(badge == null) return;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(cartCount==0)
                    badge.setVisibility(View.INVISIBLE);
                else {
                    badge.setVisibility(View.VISIBLE);
                    badge.setText(String.valueOf(cartCount));
                }
            }
        });
    }


    public void shopMenuView() {

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    String menuname = jsonResponse.getString("menuname");
                    String menuimg = jsonResponse.getString("menuimg");
                    String menuprice = jsonResponse.getString("menuprice");
                    menu_name.setText(menuname);
                    menu_price.setText(menuprice);

                    Thread mThread = new Thread() {
                        @Override
                        public void run() {
                            try {
                                URL url = new URL(menuimg);

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
                        menuImage.setImageBitmap(bitmap);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "aaaaaaaaaaa", Toast.LENGTH_SHORT).show();
                }
            }
        };

        ShopDetailRequest shopDetailRequest = new ShopDetailRequest(shopId, responseListener);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(shopDetailRequest);

    }




}
