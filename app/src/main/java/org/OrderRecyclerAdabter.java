package org;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class OrderRecyclerAdabter extends RecyclerView.Adapter<OrderRecyclerAdabter.ViewHolder> {
    Context context;
    ArrayList<ShopOrderItem> items;
    int item_layout;

    Bitmap bitmap;
    SharedPreferences pref;
    String nowId;

//    int mSelectdContentArray;
//    int mChoicedArrayItem;

    long lastPressed;                          // 한번더 누르면 반품 신청 됩니다.

    public OrderRecyclerAdabter(Context context, ArrayList<ShopOrderItem> items, int item_layout, String nowId) {
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
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.orderitem_cardview,parent,false);

        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final ShopOrderItem item = items.get(position);
        // Drawable drawable= context.getResources().getDrawable(item.getWeather());

        holder.shopDate.setText(item.getShopDate());
        holder.shopName.setText(item.getShopName());
        holder.shopPrice.setText(item.getShopPrice());

        if(item.getShopReturn().equals("1")) {
            holder.returnButton.setText("반품 신청 완료");
            holder.returnButton.setTextColor(Color.parseColor("#D93250"));
            holder.deliveryButton.setVisibility(View.GONE);
        }

        holder.returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(item.getShopReturn().equals("0")) {


//                    AlertDialog.Builder builder = new AlertDialog.Builder(context);                                        // 대화 상자를 만들기 위한 빌더 객체 생성
//                    builder.setMessage("반품 신청하시겠습니까?");
//
//                    builder.setPositiveButton("신청", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//
//                            holder.returnButton.setText("반품 신청 완료");
//                            holder.returnButton.setTextColor(Color.parseColor("#D93250"));
//                            holder.deliveryButton.setVisibility(View.GONE);
//
//                            Toast.makeText(context, "반품 신청이 완료되었습니다 :)", Toast.LENGTH_SHORT).show();
//
//                            Response.Listener<String> responseListener = new Response.Listener<String>() {
//                                @Override
//                                public void onResponse(String response) {
//                                    try {
//
//                                    } catch (Exception e) {
//                                        e.printStackTrace();
//                                        Toast.makeText(context, "aaaaaaaaaaa", Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                            };
//
//                            ShopOrderReturnRequest shopOrderReturnRequest = new ShopOrderReturnRequest(nowId, item.getOrder_id(), responseListener);
//                            RequestQueue queue = Volley.newRequestQueue(context);
//                            queue.add(shopOrderReturnRequest);
//
//                        }
//                    });
//
//                    builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                        }
//                    });
//                    AlertDialog dialog = builder.create();
//                    dialog.show();


                    if (System.currentTimeMillis() - lastPressed < 1500){

                            holder.returnButton.setText("반품 신청 완료");
                            holder.returnButton.setTextColor(Color.parseColor("#D93250"));
                            holder.deliveryButton.setVisibility(View.GONE);

                            Toast.makeText(context, "반품 신청이 완료되었습니다 :)", Toast.LENGTH_SHORT).show();

                            Response.Listener<String> responseListener = new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        Toast.makeText(context, "aaaaaaaaaaa", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            };

                            ShopOrderReturnRequest shopOrderReturnRequest = new ShopOrderReturnRequest(nowId, item.getOrder_id(), responseListener);
                            RequestQueue queue = Volley.newRequestQueue(context);
                            queue.add(shopOrderReturnRequest);

                    }else {
                        Toast.makeText(context, "한번 더 누르면 반품 신청 됩니다.", Toast.LENGTH_LONG).show();
                    }
                    lastPressed = System.currentTimeMillis();

                }

            }
        });

        holder.deliveryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "아직 배송 전입니다 :)", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView shopDate;
        TextView shopName;
        TextView shopPrice;
        TextView returnButton;
        TextView deliveryButton;




        public ViewHolder(View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cardView);
            shopDate = itemView.findViewById(R.id.shopDate);
            shopName = itemView.findViewById(R.id.shopName);
            shopPrice = itemView.findViewById(R.id.shopPrice);
            returnButton = itemView.findViewById(R.id.returnButton);
            deliveryButton = itemView.findViewById(R.id.deliveryButton);


        }
    }
}

