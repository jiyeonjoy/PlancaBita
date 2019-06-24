package org;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

public class ShopPaymentActivity extends AppCompatActivity {


    private WebView mainWebView;
    private final String APP_SCHEME = "iamportkakao://";

    SharedPreferences pref;
    String nowId;               // 현재 로그인된 아이디
    String strPrice;

    int orderCheck=0;            // 결제체크 0 이면 결제 전, 1 이면 결제 후


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_payment);

        pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        nowId = pref.getString("nowId", "");
        strPrice = String.format("%,d", ShopCartActivity.price );


        mainWebView = (WebView) findViewById(R.id.mainWebView);
        mainWebView.setWebViewClient(new KakaoWebViewClient(this));
        WebSettings settings = mainWebView.getSettings();
        settings.setJavaScriptEnabled(true);

        mainWebView.loadUrl("http://ec2-15-164-102-182.ap-northeast-2.compute.amazonaws.com/planb/shoppayment.php");


    } // onCreate 끝


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();
        if (intent != null) {
            Uri intentData = intent.getData();

            if (intentData != null) {
                //카카오페이 인증 후 복귀했을 때 결제 후속조치
                String url = intentData.toString();

                if (url.startsWith(APP_SCHEME)) {
                    String path = url.substring(APP_SCHEME.length());
                    if ("process".equalsIgnoreCase(path)) {


                    } else {
                        Toast.makeText(getApplicationContext(), "결제에 실패 하였습니다!", Toast.LENGTH_SHORT).show();
                        mainWebView.loadUrl("javascript:IMP.communicate({result:'cancel'})");
                    }
                }
            } else {
                if(orderCheck ==0) {
                    orderCheck=1;
                }else {
                    orderCheck =0;

                    Response.Listener<String> responseListener3 = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    ShopOrderYesRequest shopOrderYesRequest = new ShopOrderYesRequest(nowId, ShopCartActivity.orderName, ShopCartActivity.orderCount, strPrice, responseListener3);
                    RequestQueue queue3 = Volley.newRequestQueue(getApplicationContext());
                    queue3.add(shopOrderYesRequest);

                    Intent intent2 = new Intent(ShopPaymentActivity.this, ShopOrderActivity.class);
                    startActivity(intent2);
                    finish();

                }
            }

        }

    }

}
