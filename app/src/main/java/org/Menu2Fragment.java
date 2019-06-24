package org;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import org.Adopt.Adopt1Request;
import org.Adopt.Adopt2Activity;
import org.Adopt.Adopt3Activity;
import org.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class Menu2Fragment extends Fragment {

    private RecyclerView.Adapter adapter;
    ArrayList<AdoptItem> items = new ArrayList<>();
    RecyclerView recyclerView;

    AdoptRecyclerAdabter adoptRecyclerAdabter;

    String TAG = "phptest_MainActivity";

    String TAG_JSON="adoptlist";
    String TAG_ABAND_ID = "aband_id";
    String TAG_TITLE = "title";
    String TAG_TYPE = "type";
    String TAG_NAME ="name";
    String TAG_GENDER = "gender";
    String TAG_PRICE = "price";
    String TAG_AREA ="area";
    String TAG_IMG = "img";
    private ProgressBar progressBar;                // 데이터 로딩중을 표시할 프로그레스바
    int progress;                                   // progressBar 도는지 확인값

    int listCount;             // 전체 리스트 갯수
    int pageNum=1;             // 현재 페이지 넘버
    int pageCount;             // 전체 페이지 갯수

    String mJsonString;

    ArrayList <String> aband_id_array;
    ArrayList <String> title_array;
    ArrayList <String> type_array;
    ArrayList <String> name_array;
    ArrayList <String> gender_array;
    ArrayList <String> price_array;
    ArrayList <String> area_array;
    ArrayList <String> img_array;

    public Menu2Fragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_menu2, container, false);

        recyclerView = rootView.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new AdoptRecyclerAdabter(getActivity(), items, R.layout.fragment_menu2);
        recyclerView.setAdapter(adapter);
        adoptRecyclerAdabter = new AdoptRecyclerAdabter(getActivity(), items, R.layout.fragment_menu2);;

        LayoutInflater inflater2 =(LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater2.inflate(R.layout.adopt_cardview, null);

        aband_id_array = new ArrayList<>();
        title_array = new ArrayList<>();
        type_array = new ArrayList<>();
        name_array = new ArrayList<>();
        gender_array = new ArrayList<>();
        price_array = new ArrayList<>();
        area_array = new ArrayList<>();
        img_array = new ArrayList<>();

        progressBar = rootView.findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);
      //  Log.i("AAAAAAAAAAAAAA", title_array.get(1));


        LinearLayout writeButton = rootView.findViewById(R.id.writeButton);
        writeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Adopt2Activity.class);
                startActivity(intent);

            }
        });


        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (!recyclerView.canScrollVertically(-1)) {
                    Log.i(TAG, "Top of list");

                } else if (!recyclerView.canScrollVertically(1)) {
                    Log.i(TAG, "End of list");
                    if(pageNum==pageCount){

                    }else {
                        if(progress==0){
                            progress=1;
                            progressBar.setVisibility(View.VISIBLE);
                            listView2(pageNum+1);
                        }
                    }
                } else {
                 //   Log.i(TAG, "idle");
                }
            }
        });


        return rootView;

    }  // onCreateView 끝


    public void listView(int pageNum2) {

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject item = jsonArray.getJSONObject(i);
                        listCount = item.getInt("listCount");
                        pageCount = (listCount+2)/5;
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
                    Toast.makeText(getContext(), "aaaaaaaaaaa", Toast.LENGTH_SHORT).show();
                }
            }
        };

        AdoptListRequest adoptListRequest = new AdoptListRequest(pageNum2+"", responseListener);
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(adoptListRequest);

    }



    public void listView2(int pageNum2) {

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


                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            pageNum++;
                            adapter.notifyDataSetChanged();
                            progressBar.setVisibility(View.GONE);
                            progress=0;
                        }
                    },1000);

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "aaaaaaaaaaa", Toast.LENGTH_SHORT).show();
                }
            }
        };

        AdoptListRequest adoptListRequest = new AdoptListRequest(pageNum2+"", responseListener);
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(adoptListRequest);

    }

    @Override
    public void onResume() {
        super.onResume();
        pageNum=1;
        adoptRecyclerAdabter.itemclear();
        listView(1);
    }
}
