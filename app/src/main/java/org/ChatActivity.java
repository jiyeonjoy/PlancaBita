package org;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import org.ChatItem;
import org.ChatMemberRequest;
import org.ChatRecyclerAdapter;
import org.DbOpenHelperChat;
import org.DbOpenHelperChatList;

import org.appspot.apprtc.main.AppRTCMainActivity;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView.Adapter adapter;

    ArrayList<ChatItem> items = new ArrayList<>();

    EditText editText;
    Button button;
    RecyclerView mRecyclerView;

    SharedPreferences pref;
    String nowId;               // 현재 로그인된 아이디
    String chatId;              // 채팅하는 아이디

    static ArrayList<String> arrayIndex = new ArrayList<String>();
    static ArrayList<ChatItem> item3 = new ArrayList<>();

    private DbOpenHelperChat mDbOpenHelper;
    private DbOpenHelperChatList dbOpenHelperChatList;

    Socket socket = null;            //Server와 통신하기 위한 Socket
    DataInputStream in = null;        //Server로부터 데이터를 읽어들이기 위한 입력스트림
    BufferedReader in2 = null;        //키보드로부터 읽어들이기 위한 입력스트림
    DataOutputStream out = null;    //서버로 내보내기위한 스트림

    boolean aaa;
    String stText;  // 채팅입력한 메시지
    boolean bbb=true;

    String username;
    String userimage;
    String username2;
    String userimage2;

    String str;
    String str2;

    int a;        // 채팅리스트에 아이디 있는지 없는지 확인 값
    int b;        // 아이디 있을때 포지션 값

    boolean chch;  // 쓰레드 체크값

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

       // stopService(new Intent(ChatActivity.this, MyService.class)); // 서비스 종료

        if(bbb) {
            chch=true;
            Client thread = new Client();
            thread.start();
            bbb=false;
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);                                          // 백키
        editText = findViewById(R.id.editText);

        pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        nowId = pref.getString("nowId", "");
        chatId = pref.getString("chatId", "abc");

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    username = jsonResponse.getString("username");
                    userimage = jsonResponse.getString("userimage");

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "aaaaaaaaaaa", Toast.LENGTH_SHORT).show();
                }
            }
        };

        ChatMemberRequest chatMemberRequest = new ChatMemberRequest(chatId, responseListener);
        RequestQueue queue = Volley.newRequestQueue(ChatActivity.this);
        queue.add(chatMemberRequest);


        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stText = editText.getText().toString().trim();


                if (stText.equals("") || stText.isEmpty()) {
                    Toast.makeText(ChatActivity.this, "내용을 입력해 주세요.", Toast.LENGTH_SHORT).show();
                } else {

                    aaa=true;
                    items.add(new ChatItem(nowId, chatId, nowId, username, userimage, stText));
                    mDbOpenHelper.open();
                    mDbOpenHelper.insertColumn(nowId, chatId, nowId, username, userimage, stText);
                    showDatabase();
                    mRecyclerView.scrollToPosition(item3.size()-1);

//                    dbOpenHelperChatList.open();
//                    dbOpenHelperChatList.updateColumn(chatId, stText);

                    Cursor iCursor = dbOpenHelperChatList.selectColumns();
                    Log.d("showDatabase", "DB Size: " + iCursor.getCount());
                    a=0;
                    while (iCursor.moveToNext()) {
                        String tempLOGINID = iCursor.getString(iCursor.getColumnIndex("loginId"));
                        String tempID = iCursor.getString(iCursor.getColumnIndex("id"));
                        if(tempLOGINID.equals(nowId)) {
                            if(tempID.equals(chatId)) {
                                a = 1;
                                b = Integer.parseInt(iCursor.getString(iCursor.getColumnIndex("_id")));
                                Log.w("bbbbbbbbbbbbbbbbbbbbbb", b+"");
                            }
                        }
                    }
                    Log.d("ststststststststst", stText);
                    if(a==1) {
                        dbOpenHelperChatList.open();
                        dbOpenHelperChatList.deleteColumn(b);
                        dbOpenHelperChatList.insertColumn(nowId, chatId, username, userimage, stText);
                    }else {
                        dbOpenHelperChatList.open();
                        dbOpenHelperChatList.insertColumn(nowId, chatId, username, userimage, stText);
                    }
                }
                editText.setText("");
            }

        });

        mRecyclerView = findViewById(R.id.recycler_view);

        setRecyclerView();
        mDbOpenHelper = new DbOpenHelperChat(this);
        mDbOpenHelper.open();
        mDbOpenHelper.create();

        dbOpenHelperChatList = new DbOpenHelperChatList(this);
        dbOpenHelperChatList.open();
        dbOpenHelperChatList.create();
        showDatabase();
        mRecyclerView.scrollToPosition(item3.size()-1);                                              // 리사이클러뷰 맨 아래 보이게 해줌


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
// 각 Item 들이 RecyclerView 의 전체 크기를 변경하지 않는 다면
// setHasFixedSize() 함수를 사용해서 성능을 개선할 수 있습니다.
// 변경될 가능성이 있다면 false 로 , 없다면 true를 설정해주세요.
        mRecyclerView.setHasFixedSize(true);

// RecyclerView에 Adapter를 설정해줍니다.
        adapter = new ChatRecyclerAdapter(items, nowId);
        mRecyclerView.setAdapter(adapter);

// 다양한 LayoutManager 가 있습니다. 원하시는 방법을 선택해주세요.
// 지그재그형의 그리드 형식
//mainBinding.recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
// 그리드 형식
//mainBinding.recyclerView.setLayoutManager(new GridLayoutManager(this,4));
// 가로 또는 세로 스크롤 목록 형식
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

                out.writeUTF(nowId); // socketout , in2 socketin

                Thread th = new Thread(new Send(out));
                th.start();


            } catch (IOException e) {
                e.printStackTrace();
            }




            try {
                //클라이언트의 메인 쓰레드는 서버로부터 데이터 읽어들이는 것만 반복.
                while(chch) {
//                    if(nowId.equals("798457043")) {
//                        str ="abc";       // 아이디 받기
//                    } else {
//                        str ="798457043";       // 아이디 받기
//                    }
                    str2 = in.readUTF();      // 메시지 받기
                    str = in.readUTF();         // 아이디 받기
                    Log.w("bbbbbbbbbbbbbbbbbbbbbb", str2);
                    Log.w("bbbbbbbbbbbbbbbbbbbbbb", chatId);
                    if (str2.equals("")) {
                    } else if(str2.equals("전화왔어요")) {

                        pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("callid", str);                       // 전화온아이디 쉐어드에 저장
                        editor.commit();

                        Intent intent = new Intent(ChatActivity.this, CallActivity2.class);
                        startActivity(intent);

                    } else {

                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    username2 = jsonResponse.getString("username");
                                    userimage2 = jsonResponse.getString("userimage");

                                    items.add(new ChatItem(nowId, str, str, username2, userimage2, str2));
                                    mDbOpenHelper.open();
                                    mDbOpenHelper.insertColumn(nowId, str, str, username2, userimage2, str2);

//                                    dbOpenHelperChatList.open();
//                                    dbOpenHelperChatList.updateColumn(str, str2);
                                    showDatabase();
                                    mRecyclerView.scrollToPosition(item3.size()-1);

                                    Cursor iCursor = dbOpenHelperChatList.selectColumns();
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
                                        dbOpenHelperChatList.open();
                                        dbOpenHelperChatList.deleteColumn(b);
                                        dbOpenHelperChatList.insertColumn(nowId, str, username2, userimage2, str2);
                                    }else {
                                        dbOpenHelperChatList.open();
                                        dbOpenHelperChatList.insertColumn(nowId, str, username2, userimage2, str2);
                                    }




                                } catch (Exception e) {
                                    e.printStackTrace();
                                    Log.w("eeeeeeeeeeeeeeeeee", e);
                                    Toast.makeText(getApplicationContext(), "aaaaaaaaaaa", Toast.LENGTH_SHORT).show();
                                }
                            }
                        };

                        ChatMemberRequest chatMemberRequest = new ChatMemberRequest(str, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(ChatActivity.this);
                        queue.add(chatMemberRequest);

                    }
                }
            }catch(IOException e) {
                Log.w("에러에러에러에러에러", e);
            }
        }
    }


    public class Send implements Runnable{
        DataOutputStream out;
        BufferedReader in2 = new BufferedReader(new InputStreamReader(System.in));
        public Send(DataOutputStream out)
        {
            this.out = out;
        }
        public void run()
        {
            while(chch)
            {
                // 클라이언트으이 서브 쓰레드는 키보드로부터 입력받은 내용 서버로 전송하는 것만 계속해서 반복.
                try
                {
                    while (aaa) {
                        out.writeUTF(stText);                                    //서버로 전송
                        out.writeUTF(chatId);
                        aaa = false;
                    }
                }catch(Exception e) {}
            }
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

            String tempCHATID = iCursor.getString(iCursor.getColumnIndex("chatId"));

            String tempID = iCursor.getString(iCursor.getColumnIndex("id"));

            String tempNAME = iCursor.getString(iCursor.getColumnIndex("name"));

            //    tempID = setTextLength(tempID, 10);

            String tempIMAGE = iCursor.getString(iCursor.getColumnIndex("image"));

            //   tempName = setTextLength(tempName, 10);

            String tempCHAT = iCursor.getString(iCursor.getColumnIndex("chat"));

            //  tempAge = setTextLength(tempAge, 10);

            if(tempLOGINID.equals(nowId)) {
                if (tempCHATID.equals(chatId)) {
                    item3.add(new ChatItem(tempLOGINID, tempCHATID, tempID, tempNAME, tempIMAGE, tempCHAT));
                }
                //arrayIndex.add(tempIndex);
            }
        }

        ChatRecyclerAdapter.setItems(item3);
        adapter.notifyDataSetChanged();
    }


    @Override
    protected void onStop() {
        super.onStop();
//        try {
//            socket.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        Intent intent = new Intent(ChatActivity.this,MyService.class);
//        intent.putExtra("nowId", nowId);
//        startService(intent);
        chch = false;
    }
}
