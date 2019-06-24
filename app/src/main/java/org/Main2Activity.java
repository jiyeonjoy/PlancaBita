package org;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.Adopt.Adopt1Request;
import org.Adopt.Adopt2Activity;
import org.Adopt.Adopt3Activity;
import org.Adopt.AdoptWriteRequest;
import org.MainActivity;
import org.Menu1Fragment;
import org.Menu2Fragment;
import org.Menu3Fragment;
import org.Menu4Fragment;
import org.R;
import org.json.JSONArray;
import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.nhn.android.naverlogin.OAuthLogin;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;

public class Main2Activity extends AppCompatActivity {

    ImageView profileImage;
    Bitmap bitmap;
    TextView userName;


    SharedPreferences pref;
    String nowId;               // 현재 로그인 된 아이디


    private static OAuthLogin naverLoginInstance;

    static final String CLIENT_ID = "ckSIzxbh_oJxGkS1JdIl";
    static final String CLIENT_SECRET = "CWpc8kH6e8";
    static final String CLIENT_NAME = "네이버 아이디로 로그인 테스트";

    static Context context;

    private static final int MY_PERMISSION_CAMERA = 1111;

    TextView menu1;
    TextView menu2;
    TextView menu3;
    TextView menu4;

    Menu1Fragment menu1Fragment;
    Menu2Fragment menu2Fragment;
    Menu3Fragment menu3Fragment;
    Menu4Fragment menu4Fragment;

    ViewPager vp;
    LinearLayout ll;

    private DrawerLayout mDrawerLayout;


  //  static Socket socket = null;            //Server와 통신하기 위한 Socket
 //   DataInputStream in = null;        //Server로부터 데이터를 읽어들이기 위한 입력스트림abc
 //   BufferedReader in2 = null;        //키보드로부터 읽어들이기 위한 입력스트림
 //   DataOutputStream out = null;    //서버로 내보내기위한 스트림

    long lastPressed;                          // 한번더 누르면 종료됩니다.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        checkPermission();                                                                           // 권한 확인


//        Intent intent = new Intent(Main2Activity.this,MyService.class);
//        startService(intent);


      //  Client thread = new Client();
        //thread.start();

        menu1 = findViewById(R.id.menu1);
        menu2 = findViewById(R.id.menu2);
        menu3 = findViewById(R.id.menu3);
        menu4 = findViewById(R.id.menu4);


        vp = findViewById(R.id.vp);
        ll = (LinearLayout) findViewById(R.id.ll);

        menu1Fragment = new Menu1Fragment();
        menu2Fragment = new Menu2Fragment();
        menu3Fragment = new Menu3Fragment();
        menu4Fragment = new Menu4Fragment();

        vp.setAdapter(new pagerAdapter(getSupportFragmentManager()));
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

        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int i = 0;
                while (i < 4) {
                    if (position == i) {
                        ll.findViewWithTag(i).setSelected(true);
                    } else {
                        ll.findViewWithTag(i).setSelected(false);
                    }
                    i++;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();

                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.adoptZzim:
                        Intent intent1 = new Intent(Main2Activity.this, AdoptPickActivity.class);
                        startActivity(intent1);
                        break;

                    case R.id.adoptWriteList:
                        Intent intent2 = new Intent(Main2Activity.this, AdoptWriteActivity.class);
                        startActivity(intent2);
                        break;

                    case R.id.chatList:
                        Intent intent3 = new Intent(Main2Activity.this, ChatListActivity.class);
                        startActivity(intent3);
                        break;

                    case R.id.shopZzim:
                        Intent intent4 = new Intent(Main2Activity.this, ShopWishActivity.class);
                        startActivity(intent4);
                        break;

                    case R.id.cart:
                        Intent intent5 = new Intent(Main2Activity.this, ShopCartActivity.class);
                        startActivity(intent5);
                        break;

                    case R.id.shopList:
                        Intent intent6 = new Intent(Main2Activity.this, ShopOrderActivity.class);
                        startActivity(intent6);
                        break;


                    case R.id.logoutButton:
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("nowLoginType", "no");                       // 현재 로그인 타입 - 없음
                        editor.commit();

                        init();
                        UserManagement.requestLogout(new LogoutResponseCallback() {
                            @Override
                            public void onCompleteLogout() {
                                Intent intent = new Intent(Main2Activity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                        break;

                }

                return true;
            }
        });


        View nav_header_view = navigationView.getHeaderView(0);

        profileImage = (ImageView) nav_header_view.findViewById(R.id.imageView);
        profileImage.setBackgroundDrawable(new ShapeDrawable(new OvalShape()));                           // 사진 동그랗게 해주는 거임!!

        if (Build.VERSION.SDK_INT >= 21) {
            profileImage.setClipToOutline(true);
        }
        userName = nav_header_view.findViewById(R.id.userName);




    }   // onCreate 끝


    public void profileView() {

        pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        nowId = pref.getString("nowId", "");

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    String userName2 = jsonResponse.getString("userName");
                    String userImg2 = jsonResponse.getString("userImg");
                    userName.setText(userName2);

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
                        profileImage.setImageBitmap(bitmap);
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


    View.OnClickListener movePageListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int tag = (int) v.getTag();

            int i = 0;
            while (i < 4) {
                if (tag == i) {
                    ll.findViewWithTag(i).setSelected(true);
                } else {
                    ll.findViewWithTag(i).setSelected(false);
                }
                i++;
            }

            vp.setCurrentItem(tag);
        }
    };

    private class pagerAdapter extends FragmentStatePagerAdapter {
        public pagerAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new Menu1Fragment();
                case 1:
                    return new Menu2Fragment();
                case 2:
                    return new Menu3Fragment();
                case 3:
                    return new Menu4Fragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    }


    // 네이버 로그아웃
    private void init() {
        context = this;
        naverLoginInstance = OAuthLogin.getInstance();
        naverLoginInstance.logout(context);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                profileView();
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_settings:
                Intent intent1 = new Intent(Main2Activity.this, MyInfoActivity.class);
                startActivity(intent1);
                return true;
            case R.id.action_wallet:
                Intent intent2 = new Intent(Main2Activity.this, WalletMainActivity.class);
                startActivity(intent2);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }







//    public class Client extends Thread {
//        public void run() {
//
//            try {
//                 socket = new Socket("13.209.177.177", 5003);    //서버로 접속
//                in = new DataInputStream(socket.getInputStream());            //서버로부터 데이터 읽어들이기 위한 스트림 생성
//                in2 = new BufferedReader(new InputStreamReader(System.in)); //채팅을 위해 사용자 키보드로부터 데이터 읽어들이기 위한 스트림 생성
//                out = new DataOutputStream(socket.getOutputStream());        //채팅 내용을 서버로 전송하기 위한 출력 스트림
//
//                Log.w("AAAAAAAAAAAAAAAAAA", nowId);
//
//                //서버로 닉네임을 전송
//                out.writeUTF(nowId);
//
//            } catch (IOException e) {
//                Log.w("AAAAAAAAAAAAAAAAAA", e);
//            }
//
//        }
//    }





    @Override
    public void onBackPressed() {

        if (System.currentTimeMillis() - lastPressed < 1500){
            finish();
        }else {
            Toast.makeText(this, "한번 더 누르면 종료됩니다.", Toast.LENGTH_LONG).show();
        }
        lastPressed = System.currentTimeMillis();


    }


    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // 처음 호출시엔 if()안의 부분은 false로 리턴 됨 -> else{ {..}의 요청으로 넘어감
            if ((ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA))) {
                new android.app.AlertDialog.Builder(this)
                        .setTitle("알림")
                        .setMessage("저장소 권한이 거부되었습니다. 사용을 원하시면 설정해서 해당 권한을 직접 허용하셔야 합니다.")
                        .setNeutralButton("설정", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                intent.setData(Uri.parse("package:" +getPackageName()));
                                startActivity(intent);
                            }
                        })
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .setCancelable(false)
                        .create()
                        .show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, MY_PERMISSION_CAMERA);


            }

        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_CAMERA:
                for (int i=0; i < grantResults.length; i++) {
                    // grantResults[] : 허용된 권한은 0, 거부한 권한은 -1
                    if(grantResults[i] < 0) {
                        Toast.makeText(Main2Activity.this, "해당 권한을 활성화 하셔야 합니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                // 허용했다면 이부분에서

                break;
        }
    }





//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        Intent intent = new Intent(Main2Activity.this,MyService.class);
//        stopService(intent);
//
//    }
}
