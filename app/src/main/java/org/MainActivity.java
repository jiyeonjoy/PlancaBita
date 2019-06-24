package org;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import org.Login.JoinActivity;
import org.Login.LoginRequest;
import org.Login.RegisterRequest;
import org.Login.ValidateRequest;
import org.R;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.LoginButton;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.exception.KakaoException;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_JOIN = 101;
    SessionCallback callback;

    private EditText loginId;
    private EditText loginPassword;

    private AlertDialog dialog;

    private Context mContext;
    private Button kakaoLogin;
    private LoginButton btn_kakao_login;

    String kakaoNickname;
    String kakaoEmail;
    String kakaoProfileImagePath;
    String kakaoThumnailPath;
    long kakaoID;
    String kakaoId;


    SharedPreferences pref;

    private OAuthLoginButton naverLogInButton;
    private static OAuthLogin naverLoginInstance;
    Button naverLogin;

    static final String CLIENT_ID = "ckSIzxbh_oJxGkS1JdIl";
    static final String CLIENT_SECRET = "CWpc8kH6e8";
    static final String CLIENT_NAME = "네이버 아이디로 로그인 테스트";

    static Context context;

    String naverEmail;
    String naverName;
    String naverImage;
    String at;     // naver 로그인 토큰값

    String loginCheck;     // 자동로그인 체크


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = getApplicationContext();

        callback = new SessionCallback();
        Session.getCurrentSession().addCallback(callback);

        kakaoLogin = (Button) findViewById(R.id.kakaoLogin);
        btn_kakao_login = (LoginButton) findViewById(R.id.btn_kakao_login);
        kakaoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_kakao_login.performClick();

            }
        });


//     해시키값 로그로 얻기-카카오톡 로그인시 필수정보
//        try {
//            PackageInfo info = getPackageManager().getPackageInfo("com.example.choi0.plancabita", PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }





        Button joinButton = findViewById(R.id.joinButton);
        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), JoinActivity.class);
                startActivityForResult(intent, REQUEST_CODE_JOIN);
            }
        });

        loginId = findViewById(R.id.loginId);
        loginPassword = findViewById(R.id.loginPassword);

        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userId = loginId.getText().toString().trim();
                String userPassword = loginPassword.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success) {
                                Toast.makeText(context,"로그인 성공",Toast.LENGTH_SHORT).show();
                                pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putString("nowLoginType", "planb");                       // 현재 로그인 타입 - 자체로그인
                                editor.putString("nowId", userId);
                                editor.commit();

                                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                                MainActivity.this.startActivity(intent);
                                finish();
                            }else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                dialog = builder.setMessage("아이디 또는 비밀번호를 다시 확인하세요.")
                                        .setNegativeButton("확인", null)
                                        .create();
                                dialog.show();
                            }

                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                LoginRequest loginRequest = new LoginRequest(userId, userPassword, responseListener);
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                queue.add(loginRequest);
            }
        });

        naverLogin = findViewById(R.id.naverLogin);
        naverLogInButton = (OAuthLoginButton)findViewById(R.id.buttonNaverLogin);
        naverLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                naverLogInButton.performClick();

            }
        });

        // 네이버로그인
        init();
        init_View();

    }     // onCreate 끝


    // 네이버로그인
    //초기화
    private void init(){
        context = this;
        naverLoginInstance = OAuthLogin.getInstance();
        naverLoginInstance.init(this,CLIENT_ID,CLIENT_SECRET,CLIENT_NAME);
    }
    //변수 붙이기
    private void init_View(){


        //로그인 핸들러
        OAuthLoginHandler naverLoginHandler  = new OAuthLoginHandler() {
            @Override
            public void run(boolean success) {
                if (success) {//로그인 성공
                    new RequestApiTask().execute();//static이 아니므로 클래스 만들어서 시행.

                } else {//로그인 실패
                    String errorCode = naverLoginInstance.getLastErrorCode(context).getCode();
                    String errorDesc = naverLoginInstance.getLastErrorDesc(context);
                    Toast.makeText(context, "errorCode:" + errorCode + ", errorDesc:" + errorDesc, Toast.LENGTH_SHORT).show();
                }
            }

        };

        naverLogInButton.setOAuthLoginHandler(naverLoginHandler);

    }


    private class RequestApiTask extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {//작업이 실행되기 전에 먼저 실행.

            pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("nowLoginType", "naver");                       // 현재 로그인 타입 - naver
            editor.commit();
        }

        @Override
        protected String doInBackground(Void... params) {//네트워크에 연결하는 과정이 있으므로 다른 스레드에서 실행되어야 한다.
            String url = "https://openapi.naver.com/v1/nid/me";
            at = naverLoginInstance.getAccessToken(context);
            return naverLoginInstance.requestApi(context, at, url);//url, 토큰을 넘겨서 값을 받아온다.json 타입으로 받아진다.
        }

        protected void onPostExecute(String content) {//doInBackground 에서 리턴된 값이 여기로 들어온다.
            try {
                JSONObject jsonObject = new JSONObject(content);
                JSONObject response = jsonObject.getJSONObject("response");
                naverEmail = response.getString("email");
                naverName = response.getString("name");
                naverImage = response.getString("profile_image");


                Log.e("Profile : ", naverEmail + "");
                Log.e("Profile : ", naverName + "");
                Log.e("Profile : ", naverImage  + "");


                pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("nowId", naverEmail);
                editor.commit();


                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {

                                Response.Listener<String> responseListener2 = new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject jsonResponse = new JSONObject(response);
                                            boolean success = jsonResponse.getBoolean("success");
                                            if (success) {
                                            } else {
                                            }

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                 };

                                RegisterRequest registerRequest2 = new RegisterRequest(naverEmail, naverName, "", "naver", naverImage, responseListener2);
                                RequestQueue queue2 = Volley.newRequestQueue(MainActivity.this);
                                queue2.add(registerRequest2);

                            } else {

                            }

                        } catch (Exception e) {
                            e.printStackTrace();

                        }
                    }
                };

                ValidateRequest validateRequest = new ValidateRequest(naverEmail, "naver", responseListener);
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                queue.add(validateRequest);



            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    // 자체 로그인
    @Override
    protected void onStop() {
        super.onStop();
        if(dialog != null)
        {
            dialog.dismiss();
            dialog = null;
        }
    }


    // 카카오톡 로그인
    public class SessionCallback implements ISessionCallback {

        // 로그인에 성공한 상태
        @Override
        public void onSessionOpened() {
            requestMe();
        }

        // 로그인에 실패한 상태
        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            Log.e("SessionCallback :: ", "onSessionOpenFailed : " + exception.getMessage());
        }

        // 사용자 정보 요청
        public void requestMe() {
            // 사용자정보 요청 결과에 대한 Callback
            UserManagement.requestMe(new MeResponseCallback() {
                // 세션 오픈 실패. 세션이 삭제된 경우,
                @Override
                public void onSessionClosed(ErrorResult errorResult) {
                    Log.e("SessionCallback :: ", "onSessionClosed : " + errorResult.getErrorMessage());
                }

                // 회원이 아닌 경우,
                @Override
                public void onNotSignedUp() {
                    Log.e("SessionCallback :: ", "onNotSignedUp");
                }

                // 사용자정보 요청에 성공한 경우,
                @Override
                public void onSuccess(UserProfile userProfile) {

                    Log.e("SessionCallback :: ", "onSuccess");
                    Toast.makeText(context,"로그인 성공",Toast.LENGTH_SHORT).show();

                    kakaoNickname = userProfile.getNickname();
                    kakaoEmail = userProfile.getEmail();
                    kakaoProfileImagePath = userProfile.getProfileImagePath();
                    kakaoThumnailPath = userProfile.getThumbnailImagePath();
                    String UUID = userProfile.getUUID();
                    kakaoID = userProfile.getId();
                    kakaoId = String.valueOf(kakaoID);

                    Log.e("Profile : ", kakaoNickname + "");
                    Log.e("Profile : ", kakaoEmail + "");
                    Log.e("Profile : ", kakaoProfileImagePath  + "");
                    Log.e("Profile : ", kakaoThumnailPath + "");
                    Log.e("Profile : ", UUID + "");
                    Log.e("Profile : ", kakaoID + "");

                    pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("nowLoginType", "kakao");                       // 현재 로그인 타입 - 카카오톡
                    editor.putString("kaName", kakaoNickname);
                    editor.putString("kaImage", kakaoProfileImagePath);
                    editor.putString("kaImage2", kakaoThumnailPath);
                    editor.putString("nowId", kakaoId);
                    editor.commit();



                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                if (success) {

                                    Response.Listener<String> responseListener3 = new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            try {
                                                JSONObject jsonResponse = new JSONObject(response);
                                                boolean success = jsonResponse.getBoolean("success");
                                                if (success) {
                                                } else {
                                                }

                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    };



                                    RegisterRequest registerRequest3 = new RegisterRequest(kakaoId, kakaoNickname, "", "kakao", kakaoProfileImagePath, responseListener3);
                                    RequestQueue queue3 = Volley.newRequestQueue(MainActivity.this);
                                    queue3.add(registerRequest3);



                                } else {

                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    ValidateRequest validateRequest = new ValidateRequest(kakaoId, "kakao", responseListener);
                    RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                    queue.add(validateRequest);


                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(intent);
                finish();
                }

                // 사용자 정보 요청 실패
                @Override
                public void onFailure(ErrorResult errorResult) {
                    Log.e("SessionCallback :: ", "onFailure : " + errorResult.getErrorMessage());
                    Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                    startActivity(intent);
                    finish();

                }
            });


        }
    }

    @Override
    protected void onResume() {                                                                      //체크박스 상태 값 확인 1이면 바로 메인2메뉴로 이동!!
        super.onResume();

        pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);

        if (pref != null) {

            loginCheck = pref.getString("nowLoginType", "");

            if (loginCheck.equals("no")) {


//                pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
//                SharedPreferences.Editor editor = pref.edit();
//                editor.putString("nowLoginType", "dddd");
//                editor.commit();


            }else if(loginCheck.equals("")) {}
            else {

                Intent intent = new Intent(getApplicationContext(), NaverLoginActivity.class);
                startActivity(intent);
                Toast.makeText(context,"로그인 성공",Toast.LENGTH_SHORT).show();
                finish();

                }

            }
        }
}
