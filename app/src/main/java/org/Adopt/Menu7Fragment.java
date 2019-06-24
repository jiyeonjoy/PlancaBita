package org.Adopt;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import org.Adopt.Adopt4Request;
import org.R;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class Menu7Fragment extends Fragment {

    ImageView profileImg;
    TextView userName;
    TextView area;
    TextView price;
    TextView phone;

    SharedPreferences pref;
    String adopt_id;

    Bitmap bitmap;
    String img111;

    public Menu7Fragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_menu7, container, false);


        profileImg = rootView.findViewById(R.id.profileImg);
        userName = rootView.findViewById(R.id.userName);
        area = rootView.findViewById(R.id.area);
        price = rootView.findViewById(R.id.price);
        phone = rootView.findViewById(R.id.phone);

        pref = getActivity().getSharedPreferences("pref", Activity.MODE_PRIVATE);
        adopt_id = pref.getString("adoptView", "");


        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    String profileImg2 = jsonResponse.getString("userimg");
                    String userName2 = jsonResponse.getString("username");
                    String area2 = jsonResponse.getString("area");
                    String price2 = jsonResponse.getString("price");
                    String phone2 = jsonResponse.getString("phone");

                    img111 = profileImg2;

                    userName.setText(userName2);
                    area.setText(area2);
                    price.setText(price2+ " 원");
                    phone.setText(phone2);


                    Thread mThread = new Thread() {
                        @Override
                        public void run() {
                            try {
                                URL url = new URL(img111);

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
                        profileImg.setImageBitmap(bitmap);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                } catch (Exception e) {
                    e.printStackTrace();

                }


            }
        };

        Adopt4Request adopt4Request = new Adopt4Request(adopt_id, responseListener);
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(adopt4Request);

        profileImg.setBackgroundDrawable(new ShapeDrawable(new OvalShape()));                           // 사진 동그랗게 해주는 거임!!

        if (Build.VERSION.SDK_INT >= 21) {
            profileImg.setClipToOutline(true);
        }





        return rootView;
    }

}
