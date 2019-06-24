package org;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import org.Adopt.Adopt1Request;
import org.Adopt.Adopt3Activity;
import org.ChatListItem;
import org.ChatListRecyclerAdapter;
import org.ChatMemberRequest;
import org.DbOpenHelperChat;
import org.DbOpenHelperChatList;
import org.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;

public class ChatListActivity extends AppCompatActivity {

    private RecyclerView.Adapter adapter;

    ArrayList<ChatListItem> items = new ArrayList<>();
    RecyclerView mRecyclerView;

    SharedPreferences pref;
    String nowId;               // 현재 로그인된 아이디
    String chatId;              // 채팅하는 아이디

    static ArrayList<String> arrayIndex = new ArrayList<String>();
    static ArrayList<ChatListItem> item3 = new ArrayList<>();

    private DbOpenHelperChatList mDbOpenHelper;
    private DbOpenHelperChat dbOpenHelperChat;

    Socket socket = null;            //Server와 통신하기 위한 Socket
    DataInputStream in = null;        //Server로부터 데이터를 읽어들이기 위한 입력스트림
    BufferedReader in2 = null;        //키보드로부터 읽어들이기 위한 입력스트림
    DataOutputStream out = null;    //서버로 내보내기위한 스트림

    boolean bbb=true;

    String username;
    String userimage;

    String str;
    String str2;

    int a;        // 채팅리스트에 아이디 있는지 없는지 확인 값
    int b;        // 아이디 있을때 포지션 값

    boolean chch;  // 쓰레드 체크값

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);

      //  stopService(new Intent(ChatListActivity.this, MyService.class)); // 서비스 종료

        Toolbar toolbar = findViewById(R.id.toolBar);                                                // 위에 툴바 이름 바꾸기
        setSupportActionBar(toolbar);                                                                // 위에 툴바 이름 바꾸기
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);                                       // 위에 툴바 이름 바꾸기
        getSupportActionBar().setTitle("채팅 리스트");                                               // 위에 툴바 이름 바꾸기





    }  // onCreate 끝



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void setRecyclerView(){

        mRecyclerView.setHasFixedSize(true);

// RecyclerView에 Adapter를 설정해줍니다.
        adapter = new ChatListRecyclerAdapter(getApplicationContext(), items, nowId);
        mRecyclerView.setAdapter(adapter);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }




    public class Client extends Thread{
        public  void run()
        {
            try {
                socket =  new Socket("13.209.177.177", 5003);     //서버로 접속
                in = new DataInputStream(socket.getInputStream());            //서버로부터 데이터 읽어들이기 위한 스트림 생성
                in2 = new BufferedReader(new InputStreamReader(System.in)); //채팅을 위해 사용자 키보드로부터 데이터 읽어들이기 위한 스트림 생성
                out = new DataOutputStream(socket.getOutputStream());        //채팅 내용을 서버로 전송하기 위한 출력 스트림

                out.writeUTF(nowId);

            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                //클라이언트의 메인 쓰레드는 서버로부터 데이터 읽어들이는 것만 반복.
                while(chch) {
                    //str = "798457043";               // 아이디 받기
                    str2 = in.readUTF();              // 메시지 받기
                    str = in.readUTF();                // 아이디 받기
                    Log.w("sssssssssssssssssssss", str);
                     Log.w("ttttttttttttttttttttt", str2);
                    if (str2.equals("")) {
                    } else if(str2.equals("전화왔어요")) {

                        pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("callid", str);                       // 전화온아이디 쉐어드에 저장
                        editor.commit();

                        Intent intent = new Intent(ChatListActivity.this, CallActivity2.class);
                        startActivity(intent);

                    } else {

                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    username = jsonResponse.getString("username");
                                    userimage = jsonResponse.getString("userimage");

                                    Cursor iCursor = mDbOpenHelper.selectColumns();
                                    Log.d("showDatabase", "DB Size: " + iCursor.getCount());
                                    a=0;
                                    while (iCursor.moveToNext()) {
                                        String tempLOGINID = iCursor.getString(iCursor.getColumnIndex("loginId"));
                                        String tempID = iCursor.getString(iCursor.getColumnIndex("id"));
                                        if(tempLOGINID.equals(nowId)) {
                                            if(tempID.equals(str)) {
                                              a = 1;
                                              b = Integer.parseInt(iCursor.getString(iCursor.getColumnIndex("_id")));
                                                Log.w("bbbbbbbbbbbbbbbbbbbbbb", b+"");

                                            }
                                        }
                                    }

                                    if(a==1) {
                                    //    items.set(b-1, new ChatListItem(nowId, str, username, userimage, str2));
                                        items.add(new ChatListItem(nowId, str, username, userimage, str2));
                                        mDbOpenHelper.open();
                                        mDbOpenHelper.deleteColumn(b);
                                        mDbOpenHelper.insertColumn(nowId, str, username, userimage, str2);
                                    }else {
                                        items.add(new ChatListItem(nowId, str, username, userimage, str2));
                                        mDbOpenHelper.open();
                                        mDbOpenHelper.insertColumn(nowId, str, username, userimage, str2);
                                    }

                                    showDatabase();
                                    dbOpenHelperChat.open();
                                    dbOpenHelperChat.insertColumn(nowId, str, str, username, userimage, str2);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                    Log.w("eeeeeeeeeeeeeeeeee", e);
                                    Toast.makeText(getApplicationContext(), "aaaaaaaaaaa", Toast.LENGTH_SHORT).show();
                                }
                            }
                        };

                        ChatMemberRequest chatMemberRequest = new ChatMemberRequest(str, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(ChatListActivity.this);
                        queue.add(chatMemberRequest);


//                        mRecyclerView.scrollToPosition(item3.size()-1);
                    }
                }
            }catch(IOException e) {}
        }
    }


    public void showDatabase() {

        //Cursor iCursor = mDbOpenHelper.sortColumn(sort);
        Cursor iCursor = mDbOpenHelper.selectColumns();

        Log.d("showDatabase", "DB Size: " + iCursor.getCount());

        item3.clear();

        arrayIndex.clear();

        while (iCursor.moveToNext()) {

            String tempIndex = iCursor.getString(iCursor.getColumnIndex("_id"));

            String tempLOGINID = iCursor.getString(iCursor.getColumnIndex("loginId"));

            String tempID = iCursor.getString(iCursor.getColumnIndex("id"));

            String tempNAME = iCursor.getString(iCursor.getColumnIndex("name"));

            String tempIMAGE = iCursor.getString(iCursor.getColumnIndex("image"));

            String tempCHAT = iCursor.getString(iCursor.getColumnIndex("chat"));

            if(tempLOGINID.equals(nowId)) {
                item3.add(new ChatListItem(tempLOGINID, tempID, tempNAME, tempIMAGE, tempCHAT));
            }
            arrayIndex.add(tempIndex);

        }

        ChatListRecyclerAdapter.setItems(item3);
        adapter.notifyDataSetChanged();
    }




    @Override
    protected void onPause() {
        super.onPause();
//        try {
//            socket.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Intent intent = new Intent(ChatListActivity.this,MyService.class);
//        intent.putExtra("nowId", nowId);
//        startService(intent);
        chch=false;
    }

    @Override
    protected void onResume() {
        super.onResume();

        pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        nowId = pref.getString("nowId", "");
        mRecyclerView = findViewById(R.id.recyclerView);
        setRecyclerView();


        mDbOpenHelper = new DbOpenHelperChatList(this);
        mDbOpenHelper.open();
        mDbOpenHelper.create();


        dbOpenHelperChat = new DbOpenHelperChat(this);
        dbOpenHelperChat.open();
        dbOpenHelperChat.create();

        showDatabase();

        if(bbb) {
            chch=true;
            Client thread = new Client();
            thread.start();
            bbb=false;
        }
    }
}