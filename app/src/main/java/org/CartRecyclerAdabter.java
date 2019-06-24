package org;

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

import org.Utils.Common;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class CartRecyclerAdabter extends RecyclerView.Adapter<CartRecyclerAdabter.ViewHolder> {
    Context context;
    ArrayList<ShopCartItem> items;
    int item_layout;

    Bitmap bitmap;
    SharedPreferences pref;
    String nowId;

    public CartRecyclerAdabter(Context context, ArrayList<ShopCartItem> items, int item_layout, String nowId) {
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
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.cartitem_cardview,parent,false);

        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final ShopCartItem item = items.get(position);
        // Drawable drawable= context.getResources().getDrawable(item.getWeather());

        int goodsCount = item.getShopCount();

        holder.shopPrice.setText(item.getShopPrice());
        holder.count.setText(goodsCount+"");
        holder.shopName.setText(item.getShopName());
        holder.shopPrice.setText(item.getShopPrice());

        final int[] zzimCheck = {0};    // 0이면 위시리스트에 안들어있고 1이면 들어있음!!

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    String okay = jsonResponse.getString("okay");

                    if (okay.equals(nowId)) {
                        zzimCheck[0] =1;
                        holder.wishButton.setImageResource(R.drawable.love2);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, "aaaaaaaaaaa", Toast.LENGTH_SHORT).show();
                }
            }
        };

        ShopWishCheckRequest shopWishCheckRequest = new ShopWishCheckRequest(nowId, item.getShopId(), responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(shopWishCheckRequest);








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




        holder.plusButton.setOnClickListener(new View.OnClickListener() {
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

                ShopCartCountUpdateRequest shopCartCountUpdateRequest = new ShopCartCountUpdateRequest(nowId, item.getShopId(), item.getShopCount()+1+"", responseListener3);
                RequestQueue queue3 = Volley.newRequestQueue(context);
                queue3.add(shopCartCountUpdateRequest);

                int countupdate = item.getShopCount()+1;
                //holder.count.setText(countupdate+"");
                int priceupdate = item.getShopPriceInt()*countupdate;
                String strPriceupdate =  String.format("%,d", priceupdate);
                //holder.shopPrice.setText(strPriceupdate+"원");

                items.set(position, new ShopCartItem(item.getShopImg(), item.getShopName(), strPriceupdate+"원", countupdate, item.getShopId(), item.getShopPriceInt()));
                notifyDataSetChanged();

                ShopCartActivity.price =  ShopCartActivity.price+item.getShopPriceInt();
                String strPrice = String.format("%,d", ShopCartActivity.price);
                ShopCartActivity.goodsPrice.setText(strPrice+"원");

                int delivery;

                if(ShopCartActivity.price==0) {
                    delivery=0;
                    ShopCartActivity.deliveryPrice.setText("0원");
                }else  if(ShopCartActivity.price<50000) {
                    delivery=5000;
                    ShopCartActivity.deliveryPrice.setText("5,000원");

                }else {
                    delivery=0;
                    ShopCartActivity.deliveryPrice.setText("0원");
                }
                int allP = ShopCartActivity.price+delivery;
                String strAllP = String.format("%,d", allP);
                ShopCartActivity.allPrice.setText(strAllP+"원");





            }
        });

        holder.minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(item.getShopCount()>1) {


                    Response.Listener<String> responseListener3 = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    ShopCartCountUpdateRequest shopCartCountUpdateRequest = new ShopCartCountUpdateRequest(nowId, item.getShopId(), item.getShopCount() - 1 + "", responseListener3);
                    RequestQueue queue3 = Volley.newRequestQueue(context);
                    queue3.add(shopCartCountUpdateRequest);

                    int countupdate = item.getShopCount() - 1;
                   // holder.count.setText(countupdate + "");
                    int priceupdate = item.getShopPriceInt() * countupdate;
                    String strPriceupdate = String.format("%,d", priceupdate);
                   // holder.shopPrice.setText(strPriceupdate + "원");

                    items.set(position, new ShopCartItem(item.getShopImg(), item.getShopName(), strPriceupdate+"원", countupdate, item.getShopId(), item.getShopPriceInt()));
                    notifyDataSetChanged();

                    ShopCartActivity.price = ShopCartActivity.price - item.getShopPriceInt();
                    String strPrice = String.format("%,d", ShopCartActivity.price);
                    ShopCartActivity.goodsPrice.setText(strPrice + "원");

                    int delivery;

                    if (ShopCartActivity.price == 0) {
                        delivery = 0;
                        ShopCartActivity.deliveryPrice.setText("0원");
                    } else if (ShopCartActivity.price < 50000) {
                        delivery = 5000;
                        ShopCartActivity.deliveryPrice.setText("5,000원");

                    } else {
                        delivery = 0;
                        ShopCartActivity.deliveryPrice.setText("0원");
                    }
                    int allP = ShopCartActivity.price + delivery;
                    String strAllP = String.format("%,d", allP);
                    ShopCartActivity.allPrice.setText(strAllP + "원");

                }else {

                }


            }
        });




        holder.wishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (zzimCheck[0] == 1) {
                    Toast.makeText(context, "위시리스트에 삭제되었습니다 :)", Toast.LENGTH_SHORT).show();
                    zzimCheck[0]=0;
                    holder.wishButton.setImageResource(R.drawable.shopzzim);
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
                } else {

                    Toast.makeText(context, "위시리스트에 추가되었습니다 :)", Toast.LENGTH_SHORT).show();
                    zzimCheck[0]=1;
                    holder.wishButton.setImageResource(R.drawable.love2);

                    Response.Listener<String> responseListener4 = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    ShopWiskRequest shopWiskRequest = new ShopWiskRequest(nowId, item.getShopId(), responseListener4);
                    RequestQueue queue4 = Volley.newRequestQueue(context);
                    queue4.add(shopWiskRequest);

                }
            }
        });




        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int delivery;
                ShopCartActivity.price =  ShopCartActivity.price-item.getShopCount()*item.getShopPriceInt();
                String strPrice = String.format("%,d", ShopCartActivity.price);
                ShopCartActivity.goodsPrice.setText(strPrice+"원");

            if(ShopCartActivity.price==0) {
                delivery=0;
                ShopCartActivity.deliveryPrice.setText("0원");
            }else  if(ShopCartActivity.price<50000) {
                    delivery=5000;
                    ShopCartActivity.deliveryPrice.setText("5,000원");

                }else {
                    delivery=0;
                    ShopCartActivity.deliveryPrice.setText("0원");
                }
                int allP = ShopCartActivity.price+delivery;
                String strAllP = String.format("%,d", allP);
                ShopCartActivity.allPrice.setText(strAllP+"원");

                Response.Listener<String> responseListener3 = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };

                ShopCartDeleteRequest shopCartDeleteRequest = new ShopCartDeleteRequest(nowId, item.getShopId(), responseListener3);
                RequestQueue queue3 = Volley.newRequestQueue(context);
                queue3.add(shopCartDeleteRequest);

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
        ImageView plusButton;
        TextView count;
        ImageView minusButton;
        TextView shopPrice;
        ImageView wishButton;
        ImageView deleteButton;


        public ViewHolder(View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cardView);
            shopImg = itemView.findViewById(R.id.shopImg);
            shopName = itemView.findViewById(R.id.shopName);
            plusButton = itemView.findViewById(R.id.plusButton);
            count = itemView.findViewById(R.id.count);
            minusButton = itemView.findViewById(R.id.minusButton);
            shopPrice = itemView.findViewById(R.id.shopPrice);
            wishButton = itemView.findViewById(R.id.wishButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);

        }
    }
}

