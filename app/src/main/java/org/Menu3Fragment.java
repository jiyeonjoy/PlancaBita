package org;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.Retrofit.IDrinkShopAPI;
import org.Utils.Common;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class Menu3Fragment extends Fragment {


    private RecyclerView.Adapter adapter;
    ArrayList<ShopItem> items = new ArrayList<>();
    RecyclerView recycler_shop;

    ShopAdapter shopAdapter;

    SharedPreferences pref;
    String nowId;               // 현재 로그인된 아이디




    public Menu3Fragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_menu3, container, false);

        pref = getActivity().getSharedPreferences("pref", Activity.MODE_PRIVATE);
        nowId = pref.getString("nowId", "");

        recycler_shop = rootView.findViewById(R.id.recycler_shop);
        recycler_shop.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recycler_shop.setHasFixedSize(true);



        adapter = new ShopAdapter(getContext(), items, R.layout.shop_item_layout, nowId);
        recycler_shop.setAdapter(adapter);
        shopAdapter = new ShopAdapter(getContext(), items, R.layout.fragment_menu3, nowId);

        LayoutInflater inflater2 =(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater2.inflate(R.layout.wishitem_cardview, null);


        listView();


        return rootView;
    }  // onCreateView 끝



    public void listView() {

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("shoplist");

                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject item = jsonArray.getJSONObject(i);

                        String shopId = item.getString("ID");
                        String shopName = item.getString("Name");
                        String shopImg = item.getString("Link");
                        String shopPrice = item.getString("Price");
                        items.add(new ShopItem(shopId, shopName, shopImg, shopPrice));
                    }

                    adapter.notifyDataSetChanged();

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "aaaaaaaaaaa", Toast.LENGTH_SHORT).show();
                }
            }
        };

        ShopListRequest shopListRequest = new ShopListRequest(responseListener);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(shopListRequest);

    }


}
