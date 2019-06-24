package org;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import org.ServiceThread;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class MyService extends Service {

    NotificationManager Notifi_M;
    ServiceThread thread;
    Notification Notifi;

    static Socket socket;
    static DataInputStream in = null;        // Server로부터 데이터를 읽어들이기 위한 입력스트림
    BufferedReader in2 = null;        // 키보드로부터 읽어들이기 위한 입력스트림
    static DataOutputStream out = null;      // 서버로 내보내기위한 스트림

    String name;
    String msg;

    int a;
    String nowId;

    private DbOpenHelperChat mDbOpenHelper;
    private DbOpenHelperChatList dbOpenHelperChatList;

        @Override
        public IBinder onBind(Intent intent) {    // 서비스 객체와 화면 통신때 사용하는 메서드 (데이터를 전달할 필요가 없으면 return null)
            return null;
        }

    @Override
    public void onCreate() {     // 서비스에서 가장 먼저 호출됨(최초에 한번만)
    }


    @Override
        public int onStartCommand(Intent intent, int flags, int startId) {      // 서비스가 호출될 때마다 실행

        if(intent==null){
            //return Service.START_STICKY;
        } else {
            nowId = intent.getStringExtra("nowId");
            Client thread = new Client();
            thread.start();
        }
//            Notifi_M = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//            myServiceHandler handler = new myServiceHandler();
//            thread = new ServiceThread(handler);
//            thread.start();
            return Service.START_STICKY;
        }


        public void onDestroy() {     // 서비스가 종료될 때 실행
//            thread.stopForever();
//            thread = null;//쓰레기 값을 만들어서 빠르게 회수하라고 null을 넣어줌.
//            try {
//                socket.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
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

            }catch(IOException e) {
                Log.w("AAAAAAAAAAAAAAAAAA", e);
            }
//            try {
//                //클라이언트의 메인 쓰레드는 서버로부터 데이터 읽어들이는 것만 반복.
//                while(true) {
////                    name = "798457043";               // 아이디 받기
//                    msg = in.readUTF();              // 메시지 받기
//       //             Log.w("sssssssssssssssssssss", name);
//                    Log.w("ttttttttttttttttttttt", msg);
//                    if (msg.equals("")) {
//                    } else {
//
//                    Notifi_M = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//                        if(a==0) {
//                            com.example.choi0.plancabita.NotificationManager.createChannel(MyService.this);
//                            a=1;
//                        }
//                        com.example.choi0.plancabita.NotificationManager.sendNotification(MyService.this, 1, com.example.choi0.plancabita.NotificationManager.Channel.MESSAGE, "메세지 옴", msg.toString());
//
//                       // Toast.makeText(MyService.this, "뜸?", Toast.LENGTH_LONG).show();
//
//                    }
//                }
//            }catch(IOException e) {}
        }
    }




        class myServiceHandler extends Handler {
            @Override
            public void handleMessage(android.os.Message msg) {



            }
            }
        }



