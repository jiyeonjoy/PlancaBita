package org;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class ShopCartActivity extends AppCompatActivity {

    private RecyclerView.Adapter adapter;
    ArrayList<ShopCartItem> items = new ArrayList<>();
    RecyclerView recyclerView;

    CartRecyclerAdabter cartRecyclerAdabter;

    SharedPreferences pref;
    String nowId;               // 현재 로그인된 아이디


    TextView deleteButton;
    static TextView goodsPrice;
    static TextView deliveryPrice;
    static TextView allPrice;
    Button buyButton;

    static String orderName;       // 주문시 대표상품(주문리스트 들어갈 이름)
    static int orderCount;         // 주문시 상품 갯수(주문리스트 들어갈 갯수)

    static int price;
    int delivery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_cart);

        Toolbar toolbar = findViewById(R.id.toolBar);                                                // 위에 툴바 이름 바꾸기
        setSupportActionBar(toolbar);                                                                // 위에 툴바 이름 바꾸기
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);                                       // 위에 툴바 이름 바꾸기
        getSupportActionBar().setTitle("장바구니");                                                  // 위에 툴바 이름 바꾸기


        pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        nowId = pref.getString("nowId", "");

        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new CartRecyclerAdabter(getApplicationContext(), items, R.layout.activity_shop_cart, nowId);
        recyclerView.setAdapter(adapter);
        cartRecyclerAdabter = new CartRecyclerAdabter(getApplicationContext(), items, R.layout.activity_shop_cart, nowId);

        LayoutInflater inflater2 =(LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater2.inflate(R.layout.cartitem_cardview, null);



        deleteButton = findViewById(R.id.deleteButton);
        goodsPrice = findViewById(R.id.goodsPrice);
        deliveryPrice = findViewById(R.id.deliveryPrice);
        allPrice = findViewById(R.id.allPrice);
        buyButton = findViewById(R.id.buyButton);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Response.Listener<String> responseListener3 = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };

                ShopCartAllDeleteRequest shopCartAllDeleteRequest = new ShopCartAllDeleteRequest(nowId, responseListener3);
                RequestQueue queue3 = Volley.newRequestQueue(getApplicationContext());
                queue3.add(shopCartAllDeleteRequest);

                cartRecyclerAdabter.itemclear();
                adapter.notifyDataSetChanged();

                price =0;
                goodsPrice.setText(price+"원");
                deliveryPrice.setText("0원");
                allPrice.setText("0원");


            }
        });

        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (price == 0) {

                    Toast.makeText(getApplicationContext(), "주문할 상품을 담아 주세요 :)", Toast.LENGTH_SHORT).show();

                }else {
                  //  orderName = item
                    final ShopCartItem item = items.get(0);
                    orderName = item.getShopName();
                    orderCount = items.size();
                    Intent intent = new Intent(ShopCartActivity.this, ShopPayActivity.class);
                    startActivity(intent);
                    finish();

                }

            }
        });

        deliveryPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "50,000원 미만의 주문시 배송료가 추가됩니다 :)", Toast.LENGTH_SHORT).show();
            }
        });


        TextView deliveryText = findViewById(R.id.deliveryText);
        deliveryText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "50,000원 미만의 주문시 배송료가 추가됩니다 :)", Toast.LENGTH_SHORT).show();
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



    public void listView() {

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("shopcartlist");

                    price=0;
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject item = jsonArray.getJSONObject(i);

                        String shopId = item.getString("shopId");
                        String shopName = item.getString("shopName");
                        String shopImg = item.getString("shopImg");
                        String shopPrice = item.getString("shopPrice");
                        int shopPriceInt = item.getInt("shopPriceInt");
                        int goodsCount = item.getInt("goodsCount");
                        if(goodsCount >1) {
                         int priceChange = shopPriceInt*goodsCount;
                         shopPrice =  String.format("%,d", priceChange)+"원";
                        }
                        items.add(new ShopCartItem(shopImg, shopName, shopPrice, goodsCount, shopId, shopPriceInt));
                        price = price+shopPriceInt*goodsCount;

                    }

                    String strPrice = String.format("%,d", price);
                    goodsPrice.setText(strPrice+"원");

                    if(price==0) {
                        delivery = 0;
                        deliveryPrice.setText("0원");
                    }else if(price<50000) {
                            delivery=5000;
                            deliveryPrice.setText("5,000원");
                    }else{
                        delivery=0;
                        deliveryPrice.setText("0원");
                    }
                    int allP = price+delivery;
                    String strAllP = String.format("%,d", allP);
                    allPrice.setText(strAllP+"원");

                    adapter.notifyDataSetChanged();

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "aaaaaaaaaaa", Toast.LENGTH_SHORT).show();
                }
            }
        };

        ShopCartListRequest shopCartListRequest = new ShopCartListRequest(nowId, responseListener);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(shopCartListRequest);


    }


    @Override
    public void onResume() {
        super.onResume();

        cartRecyclerAdabter.itemclear();
        listView();
    }



}
