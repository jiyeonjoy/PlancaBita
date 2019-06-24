package org;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.Adopt.AdoptPickListRequest;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ShopWishActivity extends AppCompatActivity {


    private RecyclerView.Adapter adapter;
    ArrayList<ShopWishItem> items = new ArrayList<>();
    RecyclerView recyclerView;

    WishRecyclerAdabter wishRecyclerAdabter;

    SharedPreferences pref;
    String nowId;               // 현재 로그인된 아이디





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_wish);

        Toolbar toolbar = findViewById(R.id.toolBar);                                                // 위에 툴바 이름 바꾸기
        setSupportActionBar(toolbar);                                                                // 위에 툴바 이름 바꾸기
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);                                       // 위에 툴바 이름 바꾸기
        getSupportActionBar().setTitle("위시리스트");                                                // 위에 툴바 이름 바꾸기

        pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        nowId = pref.getString("nowId", "");


        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new WishRecyclerAdabter(getApplicationContext(), items, R.layout.activity_shop_wish, nowId);
        recyclerView.setAdapter(adapter);
        wishRecyclerAdabter = new WishRecyclerAdabter(getApplicationContext(), items, R.layout.activity_shop_wish, nowId);

        LayoutInflater inflater2 =(LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater2.inflate(R.layout.wishitem_cardview, null);


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




    public void listView() {

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("shopwishlist");

                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject item = jsonArray.getJSONObject(i);

                        String shopId = item.getString("shopId");
                        String shopName = item.getString("shopName");
                        String shopImg = item.getString("shopImg");
                        String shopPrice = item.getString("shopPrice");
                        items.add(new ShopWishItem(shopImg, shopName, shopPrice, shopId));
                    }

                    adapter.notifyDataSetChanged();

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "aaaaaaaaaaa", Toast.LENGTH_SHORT).show();
                }
            }
        };

        ShopWishListRequest shopWishListRequest = new ShopWishListRequest(nowId, responseListener);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(shopWishListRequest);

    }


    @Override
    public void onResume() {
        super.onResume();

       wishRecyclerAdabter.itemclear();
       listView();
    }


}
