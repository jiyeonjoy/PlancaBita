package org;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.Utils.Common;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder>{


    Context context;
    ArrayList<ShopItem> items;
    int item_layout;

    Bitmap bitmap;
    String nowId;

    public ShopAdapter(Context context, ArrayList<ShopItem> items, int item_layout, String nowId) {
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
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_item_layout,parent,false);

        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final ShopItem item = items.get(position);
        // Drawable drawable= context.getResources().getDrawable(item.getWeather());


        holder.txt_shop_name.setText(item.getName());
        holder.txt_price.setText(item.getPrice());

        Thread mThread = new Thread() {
            @Override
            public void run() {
                try {
                    String adoptimg =item.getLink();
                    java.net.URL url = new URL(adoptimg);

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
            holder.image_product.setImageBitmap(bitmap);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }





        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Common.shopId = Integer.parseInt(item.getID());
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
        ImageView image_product;
        TextView txt_shop_name;
        TextView txt_price;



        public ViewHolder(View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cardView);
            image_product = itemView.findViewById(R.id.image_product);
            txt_shop_name = itemView.findViewById(R.id.txt_shop_name);
            txt_price = itemView.findViewById(R.id.txt_price);


        }
    }
}