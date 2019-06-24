package org.Adopt;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import org.Adopt.Adopt2Request;
import org.R;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class Menu5Fragment extends Fragment {

    TextView text;
    ImageView img1;
    ImageView img2;
    ImageView img3;

    SharedPreferences pref;
    String adopt_id;

    Bitmap bitmap1;
    Bitmap bitmap2;
    Bitmap bitmap3;

    String img111;
    String img222;
    String img333;
    public Menu5Fragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_menu5, container, false);

        text = rootView.findViewById(R.id.text);
        img1 = rootView.findViewById(R.id.img1);
        img2 = rootView.findViewById(R.id.img2);
        img3 = rootView.findViewById(R.id.img3);

        pref = getActivity().getSharedPreferences("pref", Activity.MODE_PRIVATE);
        adopt_id = pref.getString("adoptView", "");


        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    String text2 = jsonResponse.getString("contents");
                    String img11 = jsonResponse.getString("img1");
                    String img22 = jsonResponse.getString("img2");
                    String img33 = jsonResponse.getString("img3");

                    img111 = "http://ec2-15-164-102-182.ap-northeast-2.compute.amazonaws.com/planb/"+img11;
                    img222 = "http://ec2-15-164-102-182.ap-northeast-2.compute.amazonaws.com/planb/"+img22;
                    img333 = "http://ec2-15-164-102-182.ap-northeast-2.compute.amazonaws.com/planb/"+img33;

                    text.setText(text2);

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
                                bitmap1 = BitmapFactory.decodeStream(is); //Bitmap 으로 변환

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
                        img1.setImageBitmap(bitmap1);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                    Thread mThread2 = new Thread() {
                        @Override
                        public void run() {
                            try {
                                URL url = new URL(img222);

                                //Web에서 이미지를 가져온 뒤 ImageView에 지정할 Bitmap을 만든다
                                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                conn.setDoInput(true);  // 서버로 부터 응답 수신
                                conn.connect();

                                InputStream is = conn.getInputStream();  //InputStream 값 가져오기
                                bitmap2 = BitmapFactory.decodeStream(is); //Bitmap 으로 변환

                            } catch (MalformedURLException e) {
                                e.printStackTrace();

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    mThread2.start(); // Thread 실행

                    try {
                        mThread2.join();
                        img2.setImageBitmap(bitmap2);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }




                    Thread mThread3 = new Thread() {
                        @Override
                        public void run() {
                            try {
                                URL url = new URL(img333);

                                //Web에서 이미지를 가져온 뒤 ImageView에 지정할 Bitmap을 만든다
                                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                conn.setDoInput(true);  // 서버로 부터 응답 수신
                                conn.connect();

                                InputStream is = conn.getInputStream();  //InputStream 값 가져오기
                                bitmap3 = BitmapFactory.decodeStream(is); //Bitmap 으로 변환

                            } catch (MalformedURLException e) {
                                e.printStackTrace();

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    mThread3.start(); // Thread 실행

                    try {
                        mThread3.join();
                        img3.setImageBitmap(bitmap3);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                } catch (Exception e) {
                    e.printStackTrace();

                }


            }
        };

        Adopt2Request adopt2Request = new Adopt2Request(adopt_id, responseListener);
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(adopt2Request);




        return rootView;
    }

}
