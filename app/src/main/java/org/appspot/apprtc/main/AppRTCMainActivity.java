/**
 *  Copyright 2014 The WebRTC Project Authors. All rights reserved.
 *
 *  Use of this source code is governed by a BSD-style license
 *  that can be found in the LICENSE file in the root of the source
 *  tree. An additional intellectual property rights grant can be found
 *  in the file PATENTS.  All contributing project authors may
 *  be found in the AUTHORS file in the root of the source tree.
 */

package org.appspot.apprtc.main;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.CallActivity2;
import org.Main2Activity;
import org.MainActivity;
import org.R;
import org.databinding.ActivityApprtcmainBinding;
import org.appspot.apprtc.main.CallActivity;
import org.appspot.apprtc.util.Constants;
import org.appspot.apprtc.util.L;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static org.appspot.apprtc.util.Constants.EXTRA_ROOMID;

/**
 * Handles the initial setup where the user selects which room to join.
 */
public class AppRTCMainActivity extends AppCompatActivity {
    private static final String LOG_TAG = "AppRTCMainActivity";
    private static final int CONNECTION_REQUEST = 1;
    private static final int RC_CALL = 111;
    private ActivityApprtcmainBinding binding;

    public static final int REQUEST_CODE_CALL = 113;

    boolean aaa;
    boolean bbb = true;
    boolean chch;  // 쓰레드 체크값
    Socket socket = null;            //Server와 통신하기 위한 Socket
    DataInputStream in = null;        //Server로부터 데이터를 읽어들이기 위한 입력스트림
    BufferedReader in2 = null;        //키보드로부터 읽어들이기 위한 입력스트림
    DataOutputStream out = null;    //서버로 내보내기위한 스트림
    String ididid;   // 소켓 아이디

    String str;
    String str2;

    String callId;              // 전화걸 아이디
    SharedPreferences pref;
    String nowId;               // 현재 로그인 된 아이디
    String zzz;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_apprtcmain);
        binding.connectButton.setOnClickListener(v -> connect());
        binding.roomEdittext.requestFocus();

        pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        nowId = pref.getString("nowId", "");
        callId = pref.getString("callId", "");
        zzz =pref.getString("zz", "yy");

        if(zzz.equals("zz")) {
            finish();
        }


        binding.roomEdittext.setText("plancaandbita999");

            if (bbb) {
                chch = true;
                Client thread = new Client();
                thread.start();
                bbb = false;
                connect();
            }




    }  // onCreate 끝

//private void zzz() {}

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(RC_CALL)
    private void connect() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            Log.w("pppppppppp", "yyyyyyyyyyyyyyyyy");
            aaa=true;
            connectToRoom("plancaandbita999");
        } else {
            EasyPermissions.requestPermissions(this, "Need some permissions", RC_CALL, perms);
            Log.w("pppppppppp", "nnnnnnnnnnnnnnnnnnnnnn");
        }

    }

    private void connectToRoom(String roomId) {
        L.d(getClass(), "RoomId: %s", roomId);
        pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("zz", "zz");                       // chatId 입력
        editor.commit();

//        Log.w("zzzzzzzzzzzzzzz", str2);
        Intent intent = new Intent(this, CallActivity.class);
        intent.putExtra(EXTRA_ROOMID, roomId);
        startActivityForResult(intent, CONNECTION_REQUEST);
        finish();
    }

    public class Client extends Thread {
        public void run() {

            try {
                socket = new Socket("13.209.177.177", 5003);     //서버로 접속
                in = new DataInputStream(socket.getInputStream());            //서버로부터 데이터 읽어들이기 위한 스트림 생성
                in2 = new BufferedReader(new InputStreamReader(System.in)); //채팅을 위해 사용자 키보드로부터 데이터 읽어들이기 위한 스트림 생성
                out = new DataOutputStream(socket.getOutputStream());        //채팅 내용을 서버로 전송하기 위한 출력 스트림

                out.writeUTF(nowId);
                Log.w("nowIddddddddddd", nowId);

                Thread th = new Thread(new Send(out));
                th.start();

            } catch (IOException e) {
                e.printStackTrace();
                Log.w("eeeeeeeeeeeeeeeeeee", e);
            }

        }
    }


    public class Send implements Runnable {
        DataOutputStream out;
        BufferedReader in2 = new BufferedReader(new InputStreamReader(System.in));

        public Send(DataOutputStream out) {
            this.out = out;
        }

        public void run() {
            while (chch) {
                // 클라이언트으이 서브 쓰레드는 키보드로부터 입력받은 내용 서버로 전송하는 것만 계속해서 반복.
                try {
                    while (aaa) {
                        out.writeUTF("전화왔어요");                                    //서버로 전송
                        out.writeUTF(callId);
                        aaa = false;
                    }
                } catch (Exception e) {
                    Log.w("errrrrrrrrrrrrrrrrrrrrr", e);
                }
            }
        }
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
//        Log.w("fffffffffffffffff", str2);
        chch = false;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==CONNECTION_REQUEST) {
            finish();
        }

    }
}
