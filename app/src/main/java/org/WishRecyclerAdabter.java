package org;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.Adopt.Adopt3Activity;
import org.Utils.Common;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class WishRecyclerAdabter extends RecyclerView.Adapter<WishRecyclerAdabter.ViewHolder> {
    Context context;
    ArrayList<ShopWishItem> items;
    int item_layout;

    Bitmap bitmap;
    SharedPreferences pref;
    String nowId;

    public WishRecyclerAdabter(Context context, ArrayList<ShopWishItem> items, int item_layout, String nowId) {
        this.context = context;
        this.items = items;
        this.item_layout=item_layout;
        this.nowId=nowId;
    }


    public void itemclear() {
        items.clear();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.wishitem_cardview,parent,false);

        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final ShopWishItem item = items.get(position);
        // Drawable drawable= context.getResources().getDrawable(item.getWeather());

        holder.shopName.setText(item.getShopName());
        holder.shopPrice.setText(item.getShopPrice());

        Thread mThread = new Thread() {
            @Override
            public void run() {
                try {
                    String adoptimg =item.getShopImg();
                    URL url = new URL(adoptimg);

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
            holder.shopImg.setImageBitmap(bitmap);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        holder.cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            String okay = jsonResponse.getString("okay");

                            if (okay.equals(nowId)) {
                                Toast.makeText(context, "이미 장바구니에 추가되었습니다 :)", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(context, "장바구니에 추가되었습니다 :)", Toast.LENGTH_SHORT).show();


                                Response.Listener<String> responseListener4 = new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                };

                                ShopCartAddRequest shopCartAddRequest = new ShopCartAddRequest(nowId, item.getShopId(), responseListener4);
                                RequestQueue queue4 = Volley.newRequestQueue(context);
                                queue4.add(shopCartAddRequest);


                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(context, "aaaaaaaaaaa", Toast.LENGTH_SHORT).show();
                        }
                    }
                };

                ShopCartCheckRequest shopCartCheckRequest = new ShopCartCheckRequest(nowId, item.getShopId(), responseListener);
                RequestQueue queue = Volley.newRequestQueue(context);
                queue.add(shopCartCheckRequest);

            }
        });


        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
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

                ShopWishDeleteRequest shopWishDeleteRequest = new ShopWishDeleteRequest(nowId, item.getShopId(), responseListener3);
                RequestQueue queue3 = Volley.newRequestQueue(context);
                queue3.add(shopWishDeleteRequest);

                items.remove(position);
                notifyDataSetChanged();

            }
        });



        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Common.shopId = Integer.parseInt(item.getShopId());
                context.startActivity(new Intent(context, ShopActivity.class));
            }
        });


    }



    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        ImageView shopImg;
        TextView shopName;
        TextView shopPrice;
        ImageView cartButton;
        ImageView deleteButton;


        public ViewHolder(View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cardView);
            shopImg = itemView.findViewById(R.id.shopImg);
            shopName = itemView.findViewById(R.id.shopName);
            shopPrice = itemView.findViewById(R.id.shopPrice);
            cartButton = itemView.findViewById(R.id.cartButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);

        }
    }
}

