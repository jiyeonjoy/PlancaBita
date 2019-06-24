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

import org.Adopt.Adopt3Activity;
import org.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class AdoptRecyclerAdabter extends RecyclerView.Adapter<AdoptRecyclerAdabter.ViewHolder> {
    Context context;
    ArrayList<AdoptItem> items;
    int item_layout;

    Bitmap bitmap;
    SharedPreferences pref;

    public AdoptRecyclerAdabter(Context context, ArrayList<AdoptItem> items, int item_layout) {
        this.context = context;
        this.items = items;
        this.item_layout=item_layout;
    }


    public void itemclear() {
        items.clear();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.adopt_cardview,parent,false);

        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final AdoptItem item = items.get(position);
        // Drawable drawable= context.getResources().getDrawable(item.getWeather());
        holder.title.setText(item.getTitle());
        holder.dogType.setText(item.getDogType());
        holder.dogName.setText(item.getDogName());
        holder.dogPrice.setText(item.getDogPrice()+"원");
        holder.location.setText(item.getLocation());

        if(item.getGender().equals("남아")) {
            holder.gender.setImageResource(R.drawable.male);
        } else {
            holder.gender.setImageResource(R.drawable.female);
        }

        Thread mThread = new Thread() {
            @Override
            public void run() {
                try {
                    String adoptimg = "http://ec2-15-164-102-182.ap-northeast-2.compute.amazonaws.com/planb/"+item.getDogImg();
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
            holder.dogImg.setImageBitmap(bitmap);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String idid = item.getAdopt_id();
                pref = context.getSharedPreferences("pref", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("adoptView", idid );                              // 몇번째 눌렀는지 확인값
                editor.commit();

                Intent intent = new Intent(context, Adopt3Activity.class);
                context.startActivity(intent);

            }
        });

    }



    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView dogType;
        TextView dogName;
        ImageView gender;
        TextView dogPrice;
        ImageView dogImg;
        TextView location;
        CardView cardView;


        public ViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            dogType = itemView.findViewById(R.id.dogType);
            dogName = itemView.findViewById(R.id.dogName);
            gender = itemView.findViewById(R.id.gender);
            dogPrice = itemView.findViewById(R.id.dogPrice);
            dogImg = itemView.findViewById(R.id.dogImg);
            location = itemView.findViewById(R.id.location);
            cardView = itemView.findViewById(R.id.cardView);

        }
    }
}

