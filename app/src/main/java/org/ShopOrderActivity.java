package org;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ShopOrderActivity extends AppCompatActivity {

    private RecyclerView.Adapter adapter;
    ArrayList<ShopOrderItem> items = new ArrayList<>();
    RecyclerView recyclerView;

    OrderRecyclerAdabter orderRecyclerAdabter;

    SharedPreferences pref;
    String nowId;               // 현재 로그인된 아이디


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_order);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);                                       // 위에 툴바 이름 바꾸기
        getSupportActionBar().setTitle("주문리스트");                                                // 위에 툴바 이름 바꾸기

        pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        nowId = pref.getString("nowId", "");

        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new OrderRecyclerAdabter(getApplicationContext(), items, R.layout.activity_shop_order, nowId);
        recyclerView.setAdapter(adapter);
        orderRecyclerAdabter = new OrderRecyclerAdabter(getApplicationContext(), items, R.layout.activity_shop_order, nowId);

        LayoutInflater inflater2 =(LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater2.inflate(R.layout.orderitem_cardview, null);

        listView();

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
                    JSONArray jsonArray = jsonObject.getJSONArray("shoporderlist");

                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject item = jsonArray.getJSONObject(i);

                        String shopDate = item.getString("shopDate");
                        String shopName = item.getString("shopName");
                        String shopPrice = item.getString("shopPrice");
                        String shopReturn = item.getString("shopReturn");
                        String order_id = item.getString("order_id");
                        items.add(new ShopOrderItem(shopDate, shopName, shopPrice, shopReturn, order_id));
                    }

                    adapter.notifyDataSetChanged();

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "aaaaaaaaaaa", Toast.LENGTH_SHORT).show();
                }
            }
        };

        ShopOrderListRequest shopOrderListRequest = new ShopOrderListRequest(nowId, responseListener);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(shopOrderListRequest);

    }


}
