package org;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
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

public class ChatListRecyclerAdapter extends RecyclerView.Adapter<ChatListRecyclerAdapter.ItemViewHolder> {

    Context context;
    static ArrayList<ChatListItem> items;
    //  ArrayList<String> IU;   // 나와 남자친구 구분
    String id;
    Bitmap bitmap;
    SharedPreferences pref;

    public ChatListRecyclerAdapter(Context context, ArrayList<ChatListItem> items, String id){
        this.context = context;
        this.items= items;
        this.id=id;
    }
    public static void setItems(ArrayList<ChatListItem> mItems) {
        items = mItems;
    }

    @Override
    public int getItemViewType(int position) {

     return position;

    }

    // 새로운 뷰 홀더 생성

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chatlist_cardview, parent, false);

        return new ItemViewHolder(view);
    }

    // View 의 내용을 해당 포지션의 데이터로 바꿉니다.
    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        final ChatListItem item = items.get(position);
        holder.message.setText(item.getChat());
        holder.name.setText(item.getName());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String idid = item.getId();
                pref = context.getSharedPreferences("pref", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("chatId", idid );                              // 몇번째 눌렀는지 확인값
                editor.commit();

                Intent intent = new Intent(context, ChatActivity.class);
                context.startActivity(intent);

            }
        });

        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {


                return false;
            }
        });

        Thread mThread = new Thread() {
            @Override
            public void run() {
                try {
                    String img = item.getImage();
                    URL url = new URL(img);

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
            holder.imageView.setImageBitmap(bitmap);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    // 데이터 셋의 크기를 리턴해줍니다.
    @Override
    public int getItemCount() {
        return items.size();
    }



// 커스텀 뷰홀더
// item layout 에 존재하는 위젯들을 바인딩합니다.

    class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView message;
        ImageView imageView;
        TextView name;
        CardView cardView;


        public ItemViewHolder(View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.message);
            name = itemView.findViewById(R.id.name);
            imageView = itemView.findViewById(R.id.imageView);
            cardView = itemView.findViewById(R.id.cardView);

            imageView.setBackgroundDrawable(new ShapeDrawable(new OvalShape()));                           // 사진 동그랗게 해주는 거임!!

            if (Build.VERSION.SDK_INT >= 21) {
                imageView.setClipToOutline(true);
            }

        }
    }
}
