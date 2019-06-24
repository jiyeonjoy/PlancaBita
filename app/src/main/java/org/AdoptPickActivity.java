package org;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.Adopt.Adopt2Activity;
import org.Adopt.AdoptPickListRequest;
import org.AdoptRecyclerAdabter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class AdoptPickActivity extends AppCompatActivity {


    private RecyclerView.Adapter adapter;
    ArrayList<AdoptItem> items = new ArrayList<>();
    RecyclerView recyclerView;

    AdoptRecyclerAdabter adoptRecyclerAdabter;

    String TAG_JSON="adoptpicklist";
    String TAG_ABAND_ID = "aband_id";
    String TAG_TITLE = "title";
    String TAG_TYPE = "type";
    String TAG_NAME ="name";
    String TAG_GENDER = "gender";
    String TAG_PRICE = "price";
    String TAG_AREA ="area";
    String TAG_IMG = "img";

    String mJsonString;

    ArrayList <String> aband_id_array;
    ArrayList <String> title_array;
    ArrayList <String> type_array;
    ArrayList <String> name_array;
    ArrayList <String> gender_array;
    ArrayList <String> price_array;
    ArrayList <String> area_array;
    ArrayList <String> img_array;

    SharedPreferences pref;
    String nowId;               // 현재 로그인된 아이디



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adopt_pick);

        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("분양 찜목록");

        pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        nowId = pref.getString("nowId", "");


        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new AdoptRecyclerAdabter(getApplicationContext(), items, R.layout.activity_adopt_pick);
        recyclerView.setAdapter(adapter);
        adoptRecyclerAdabter = new AdoptRecyclerAdabter(getApplicationContext(), items, R.layout.activity_adopt_pick);;

        LayoutInflater inflater2 =(LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater2.inflate(R.layout.adopt_cardview, null);

        aband_id_array = new ArrayList<>();
        title_array = new ArrayList<>();
        type_array = new ArrayList<>();
        name_array = new ArrayList<>();
        gender_array = new ArrayList<>();
        price_array = new ArrayList<>();
        area_array = new ArrayList<>();
        img_array = new ArrayList<>();


    }  // onCreate 끝


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



    public void listView() {

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject item = jsonArray.getJSONObject(i);

                        String aband_id = item.getString(TAG_ABAND_ID);
                        String title = item.getString(TAG_TITLE);
                        String type = item.getString(TAG_TYPE);
                        String name = item.getString(TAG_NAME);
                        String gender = item.getString(TAG_GENDER);
                        String price = item.getString(TAG_PRICE);
                        String area = item.getString(TAG_AREA);
                        String img = item.getString(TAG_IMG);
                        items.add(new AdoptItem(aband_id, title, type, name, gender, price, area, img));
                    }

                    adapter.notifyDataSetChanged();

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "aaaaaaaaaaa", Toast.LENGTH_SHORT).show();
                }
            }
        };

        AdoptPickListRequest adoptPickListRequest = new AdoptPickListRequest(nowId, responseListener);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(adoptPickListRequest);

    }


    @Override
    public void onResume() {
            super.onResume();

        adoptRecyclerAdabter.itemclear();
        listView();
        }


    }
