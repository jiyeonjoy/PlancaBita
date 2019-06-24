package org.Adopt;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import org.R;
import com.gun0912.tedpicker.Config;
import com.gun0912.tedpicker.ImagePickerActivity;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Adopt4Activity extends AppCompatActivity {

    private static final int INTENT_REQUEST_GET_IMAGES = 13;
    public static final int REQUEST_CODE_AREA = 33;

    private static final String TAG = "TedPicker";
    ArrayList<Uri> image_uris = new ArrayList<Uri>();
    private ViewGroup mSelectedImagesContainer;


    TextView area;

    EditText title;
    EditText contents;
    EditText name;
    EditText ageYear;
    EditText ageMonth;
    EditText weight;
    EditText dogType;
    EditText character;
    EditText phone;
    EditText price;

    RadioButton gender1;
    RadioButton gender2;
    RadioGroup genderGroup;
    String gender;

    String title1;
    String contents1;
    String name1;
    String ageYear1;
    String ageMonth1;
    String weight1;
    String dogType1;
    String character1;
    String phone1;
    String price1;
    String area1;

    SharedPreferences pref;     // 현재 로그인 된 아이디 확인
    String adopt_id;            // 현재 분양리스트 번호확인
    String nowId;               // 현재 로그인 된 아이디
    String nowIdType;           // 현재 로그인 된 아이디 타입

    String img1;
    String img2;
    String img3;

    ProgressDialog dialog = null;
    String upLoadServerUri1 = null;
    String upLoadServerUri2 = null;
    String upLoadServerUri3 = null;
    int serverResponseCode = 0;

    String img111;
    String img222;
    String img333;
    Bitmap bitmap1;
    Bitmap bitmap2;
    Bitmap bitmap3;

    ImageView imgg1;
    ImageView imgg2;
    ImageView imgg3;
    LinearLayout linearimg;
    int imgupdate;       // 이미지 업데이트 확인값   0, 안함  1, 수정




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adopt4);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);                                          // 백키


        upLoadServerUri1 = "http://ec2-15-164-102-182.ap-northeast-2.compute.amazonaws.com/planb/adandimg4.php";       // 서버컴퓨터의 ip주소
        upLoadServerUri2 = "http://ec2-15-164-102-182.ap-northeast-2.compute.amazonaws.com/planb/adandimg5.php";        // 서버컴퓨터의 ip주소
        upLoadServerUri3 = "http://ec2-15-164-102-182.ap-northeast-2.compute.amazonaws.com/planb/adandimg6.php";        // 서버컴퓨터의 ip주소

        pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        adopt_id = pref.getString("adoptView", "");
        nowId = pref.getString("nowId", "");
        nowIdType = pref.getString("nowLoginType", "");

        // 라디오 버튼 설정
        gender1 = findViewById(R.id.gender1);
        gender2 = findViewById(R.id.gender2);
        gender1.setOnClickListener(radioButtonClickListener);
        gender2.setOnClickListener(radioButtonClickListener);

        // 라디오 그룹 설정
        genderGroup = findViewById(R.id.genderGroup);
        genderGroup.setOnCheckedChangeListener(radioGroupButtonChangeListener);

        title = findViewById(R.id.title);
        contents = findViewById(R.id.contents);
        name = findViewById(R.id.name);
        ageYear = findViewById(R.id.ageYear);
        ageMonth = findViewById(R.id.ageMonth);
        weight = findViewById(R.id.weight);
        dogType = findViewById(R.id.dogType);
        character = findViewById(R.id.character);
        phone = findViewById(R.id.phone);
        price = findViewById(R.id.price);
        area = findViewById(R.id.area);

        imgg1 = findViewById(R.id.imgg1);
        imgg2 = findViewById(R.id.imgg2);
        imgg3 = findViewById(R.id.imgg3);
        linearimg = findViewById(R.id.linearimg);


        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    String title2 = jsonResponse.getString("title");
                    String contents2 = jsonResponse.getString("contents");
                    String img11 = jsonResponse.getString("img1");
                    String img22 = jsonResponse.getString("img2");
                    String img33 = jsonResponse.getString("img3");
                    String name2 = jsonResponse.getString("name");
                    String ageYear2 = jsonResponse.getString("ageyear");
                    String ageMonth2 = jsonResponse.getString("agemonth");
                    String weight2 = jsonResponse.getString("weight");
                    String gender2 = jsonResponse.getString("gender");
                    String type2 = jsonResponse.getString("type");
                    String chara2 = jsonResponse.getString("chara");
                    String phone2 = jsonResponse.getString("phone");
                    String area2 = jsonResponse.getString("area");
                    String price2 = jsonResponse.getString("price");

                    img111 = "http://ec2-15-164-102-182.ap-northeast-2.compute.amazonaws.com/planb/"+img11;
                    img222 = "http://ec2-15-164-102-182.ap-northeast-2.compute.amazonaws.com/planb/"+img22;
                    img333 = "http://ec2-15-164-102-182.ap-northeast-2.compute.amazonaws.com/planb/"+img33;

                    title.setText(title2);
                    title.setSelection(title.getText().length());
                    contents.setText(contents2);

                    name.setText(name2);
                    ageYear.setText(ageYear2);
                    ageMonth.setText(ageMonth2);
                    weight.setText(weight2);

                    if(gender2.equals("남아")) {
                        gender="남아";
                        genderGroup.check(R.id.gender1);
                    } else {
                        gender="여아";
                        genderGroup.check(R.id.gender2);
                    }

                    dogType.setText(type2);
                    character.setText(chara2);
                    phone.setText(phone2);
                    price.setText(price2);
                    area.setText(area2);

                    Thread mThread = new Thread() {
                        @Override
                        public void run() {
                            try {
                                URL url = new URL(img111);

                                //Web에서 이미지를 가져온 뒤 ImageView에 지정할 Bitmap을 만든다
                                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                conn.setDoInput(true);  // 서버로 부터 응답 수신
                                conn.connect();

                                InputStream is = conn.getInputStream();  //InputStream 값 가져오기
                                bitmap1 = BitmapFactory.decodeStream(is); //Bitmap 으로 변환

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
                        imgg1.setImageBitmap(bitmap1);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                    Thread mThread2 = new Thread() {
                        @Override
                        public void run() {
                            try {
                                URL url = new URL(img222);

                                //Web에서 이미지를 가져온 뒤 ImageView에 지정할 Bitmap을 만든다
                                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                conn.setDoInput(true);  // 서버로 부터 응답 수신
                                conn.connect();

                                InputStream is = conn.getInputStream();  //InputStream 값 가져오기
                                bitmap2 = BitmapFactory.decodeStream(is); //Bitmap 으로 변환

                            } catch (MalformedURLException e) {
                                e.printStackTrace();

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    mThread2.start(); // Thread 실행

                    try {
                        mThread2.join();
                        imgg2.setImageBitmap(bitmap2);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }




                    Thread mThread3 = new Thread() {
                        @Override
                        public void run() {
                            try {
                                URL url = new URL(img333);

                                //Web에서 이미지를 가져온 뒤 ImageView에 지정할 Bitmap을 만든다
                                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                conn.setDoInput(true);  // 서버로 부터 응답 수신
                                conn.connect();

                                InputStream is = conn.getInputStream();  //InputStream 값 가져오기
                                bitmap3 = BitmapFactory.decodeStream(is); //Bitmap 으로 변환

                            } catch (MalformedURLException e) {
                                e.printStackTrace();

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    mThread3.start(); // Thread 실행

                    try {
                        mThread3.join();
                        imgg3.setImageBitmap(bitmap3);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                } catch (Exception e) {
                    e.printStackTrace();

                }


            }
        };

        AdoptUpdateRequest adoptUpdateRequest = new AdoptUpdateRequest(adopt_id, responseListener);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(adoptUpdateRequest);







        area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AreaActivity.class);
                startActivityForResult(intent, REQUEST_CODE_AREA);
            }
        });


        mSelectedImagesContainer = (ViewGroup) findViewById(R.id.selected_photos_container);
        View getImages = findViewById(R.id.photoButton);
        getImages.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Config config = new Config();
                config.setCameraHeight(R.dimen.app_camera_height);
                config.setToolbarTitleRes(R.string.custom_title);
                config.setSelectionMin(3);
                config.setSelectionLimit(3);
                config.setSelectedBottomHeight(R.dimen.bottom_height);
                config.setFlashOn(true);
                getImages(config);

            }
        });


        View getImages2 = findViewById(R.id.photoButton2);
        getImages2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getImages(new Config());
            }
        });

        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (title.getText().toString().trim().equals("")) {
                    Toast.makeText(getApplicationContext(), "제목을 입력해 주세요.", Toast.LENGTH_SHORT).show();
                    Log.i("AAAAAAAAAAAAAA", "ddddddddddddddddddddddddd");
                } else {
                    if (contents.getText().toString().trim().equals("")) {
                        Toast.makeText(getApplicationContext(), "내용을 입력해 주세요.", Toast.LENGTH_SHORT).show();
                    } else {
                            if (name.getText().toString().trim().equals("")) {
                                Toast.makeText(getApplicationContext(), "이름을 입력해 주세요.", Toast.LENGTH_SHORT).show();
                            } else {
                                if (dogType.getText().toString().trim().equals("")) {
                                    Toast.makeText(getApplicationContext(), "품종을 입력해 주세요.", Toast.LENGTH_SHORT).show();
                                } else {
                                    if (character.getText().toString().trim().equals("")) {
                                        Toast.makeText(getApplicationContext(), "특징을 입력해 주세요.", Toast.LENGTH_SHORT).show();
                                    } else {
                                        if (phone.getText().toString().trim().equals("")) {
                                            Toast.makeText(getApplicationContext(), "연락처를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                                        } else {
                                            if (area.getText().toString().trim().equals("")) {
                                                Toast.makeText(getApplicationContext(), "지역을 선택해 주세요.", Toast.LENGTH_SHORT).show();
                                            } else {
                                                if (price.getText().toString().trim().equals("")) {
                                                    Toast.makeText(getApplicationContext(), "분양가를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                                                } else {

                                                    if(imgupdate==1) {
                                                        img1 = image_uris.get(0).toString();
                                                        img2 = image_uris.get(1).toString();
                                                        img3 = image_uris.get(2).toString();

                                                        Log.i("AAAAAAAAAAAAAA", "ffffffffffffffffffffffff");
                                                        dialog = ProgressDialog.show(Adopt4Activity.this, "", "Uploading file...", true);

                                                        new Thread(new Runnable() {
                                                            public void run() {
                                                                runOnUiThread(new Runnable() {
                                                                    public void run() {

                                                                    }
                                                                });

                                                                uploadFile1(img1);
                                                                uploadFile2(img2);
                                                                uploadFile3(img3);

                                                                Log.i("AAAAAAAAAAAAAA", "HTTP Response is : " + image_uris.get(0).toString());

                                                            }
                                                        }).start();

                                                    }

                                                    title1 = title.getText().toString().trim();
                                                    contents1 = contents.getText().toString().trim();
                                                    name1 = name.getText().toString().trim();
                                                    dogType1 = dogType.getText().toString().trim();
                                                    character1 = character.getText().toString().trim();
                                                    phone1 = phone.getText().toString().trim();
                                                    area1 = area.getText().toString().trim();
                                                    price1 = price.getText().toString().trim();
                                                    ageYear1 = ageYear.getText().toString().trim();
                                                    ageMonth1 = ageMonth.getText().toString().trim();
                                                    weight1 = weight.getText().toString().trim();

                                                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                                                        @Override
                                                        public void onResponse(String response) {
                                                            try {
                                                                JSONObject jsonResponse = new JSONObject(response);
                                                                boolean success = jsonResponse.getBoolean("success");
                                                                if (success) {
                                                                } else {
                                                                }

                                                            } catch (Exception e) {
                                                                e.printStackTrace();
                                                            }
                                                        }
                                                    };

                                                    AdoptUpdate2Request adoptUpdate2Request = new AdoptUpdate2Request(adopt_id, title1, contents1, name1, dogType1, character1, phone1, area1, price1, ageYear1, ageMonth1, weight1, gender, responseListener);
                                                    RequestQueue queue = Volley.newRequestQueue(Adopt4Activity.this);
                                                    queue.add(adoptUpdate2Request);

                                                    finish();

                                                }
                                            }
                                        }
                                    }
                                }
                            }

                    }
                }
            }
        });


        Button cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }      // onCreate 끝

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()){
                case android.R.id.home:{                                                                 //toolbar의 back키 눌렀을 때 동작
                    finish();
                    return true;
                }
            }
            return super.onOptionsItemSelected(item);
        }


        private void getImages(Config config) {
            ImagePickerActivity.setConfig(config);
            Intent intent = new Intent(this, ImagePickerActivity.class);
            if (image_uris != null) {
                intent.putParcelableArrayListExtra(ImagePickerActivity.EXTRA_IMAGE_URIS, image_uris);
            }
            startActivityForResult(intent, INTENT_REQUEST_GET_IMAGES);
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
            super.onActivityResult(requestCode, resultCode, intent);

            if (resultCode == Activity.RESULT_OK) {
                if (requestCode == INTENT_REQUEST_GET_IMAGES) {
                    image_uris = intent.getParcelableArrayListExtra(ImagePickerActivity.EXTRA_IMAGE_URIS);
                    if (image_uris != null) {
                        linearimg.setVisibility(View.GONE);
                        imgupdate=1;

                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                } catch (Exception e) {
                                    e.printStackTrace();

                                }
                            }
                        };
                        AdoptImgUpdateRequest adoptImgUpdateRequest = new AdoptImgUpdateRequest(adopt_id, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                        queue.add(adoptImgUpdateRequest);

                        showMedia();
                    }
                }
            }
            if (requestCode == REQUEST_CODE_AREA) {
                if (resultCode == RESULT_OK) {
                    String areaName = intent.getExtras().getString("area");
                    area.setText(areaName);
                }
            }
        }

        private void showMedia() {
            // Remove all views before
            // adding the new ones.
            mSelectedImagesContainer.removeAllViews();
            if (image_uris.size() >= 1) {
                mSelectedImagesContainer.setVisibility(View.VISIBLE);
                Log.e("AAAAAAAAAAAAAAAAAA : ", image_uris + "");
            }
            int wdpx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());
            int htpx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());
            for (Uri uri : image_uris) {
                View imageHolder = LayoutInflater.from(this).inflate(R.layout.image_item, null);
                ImageView thumbnail = (ImageView) imageHolder.findViewById(R.id.media_image);
                Glide.with(this)
                        .load(uri.toString())
                        .fitCenter()
                        .into(thumbnail);
                mSelectedImagesContainer.addView(imageHolder);
                thumbnail.setLayoutParams(new FrameLayout.LayoutParams(wdpx, htpx));
            }
        }




        //라디오 버튼 클릭 리스너
        RadioButton.OnClickListener radioButtonClickListener = new RadioButton.OnClickListener(){
            @Override
            public void onClick(View view) {

            }
        };

        //라디오 그룹 클릭 리스너
        RadioGroup.OnCheckedChangeListener radioGroupButtonChangeListener = new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if(i == R.id.gender1){
                    gender ="남아";
                }
                else if(i == R.id.gender2){
                    gender ="여아";
                }
            }
        };

        public int uploadFile1(String sourceFileUri) {

            String fileName = sourceFileUri;

            HttpURLConnection conn = null;
            DataOutputStream dos = null;
            String lineEnd = "\r\n";
            String twoHyphens = "--";
            String boundary = "*****";
            int bytesRead, bytesAvailable, bufferSize;
            byte[] buffer;
            int maxBufferSize = 1 * 1024 * 1024;
            File sourceFile = new File(sourceFileUri);

            if (!sourceFile.isFile()) {

                dialog.dismiss();


                runOnUiThread(new Runnable() {
                    public void run() {

                    }
                });

                return 0;

            }
            else
            {
                try {

                    // open a URL connection to the Servlet
                    FileInputStream fileInputStream = new FileInputStream(sourceFile);
                    URL url = new URL(upLoadServerUri1);

                    // Open a HTTP  connection to  the URL
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true); // Allow Inputs
                    conn.setDoOutput(true); // Allow Outputs
                    conn.setUseCaches(false); // Don't use a Cached Copy
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Connection", "Keep-Alive");
                    conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                    conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                    conn.setRequestProperty("uploaded_file", fileName);

                    dos = new DataOutputStream(conn.getOutputStream());

                    dos.writeBytes(twoHyphens + boundary + lineEnd);
                    dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                            + fileName + "\"" + lineEnd);

                    dos.writeBytes(lineEnd);

                    // create a buffer of  maximum size
                    bytesAvailable = fileInputStream.available();

                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    buffer = new byte[bufferSize];

                    // read file and write it into form...
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                    while (bytesRead > 0) {

                        dos.write(buffer, 0, bufferSize);
                        bytesAvailable = fileInputStream.available();
                        bufferSize = Math.min(bytesAvailable, maxBufferSize);
                        bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                    }

                    // send multipart form data necesssary after file data...
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                    // Responses from the server (code and message)
                    serverResponseCode = conn.getResponseCode();
                    String serverResponseMessage = conn.getResponseMessage();

                    Log.i("uploadFile", "HTTP Response is : "
                            + serverResponseMessage + ": " + serverResponseCode);

                    if(serverResponseCode == 200){

                        runOnUiThread(new Runnable() {
                            public void run() {

//                            Toast.makeText(Adopt2Activity.this, "File Upload Complete.",
//                                    Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    //close the streams //
                    fileInputStream.close();
                    dos.flush();
                    dos.close();

                } catch (MalformedURLException ex) {

                    dialog.dismiss();
                    ex.printStackTrace();

                    runOnUiThread(new Runnable() {
                        public void run() {

//                        Toast.makeText(Adopt2Activity.this, "MalformedURLException",
//                                Toast.LENGTH_SHORT).show();
                        }
                    });

                    Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
                } catch (Exception e) {

                    dialog.dismiss();
                    e.printStackTrace();

                    runOnUiThread(new Runnable() {
                        public void run() {

//                        Toast.makeText(Adopt2Activity.this, "Got Exception : see logcat ",
//                                Toast.LENGTH_SHORT).show();
                        }
                    });
                    Log.e("AAAAAAAAAAAA", "Exception : "
                            + e.getMessage(), e);
                }
                dialog.dismiss();
                return serverResponseCode;

            } // End else block
        }


        public int uploadFile2(String sourceFileUri) {

            String fileName = sourceFileUri;

            HttpURLConnection conn = null;
            DataOutputStream dos = null;
            String lineEnd = "\r\n";
            String twoHyphens = "--";
            String boundary = "*****";
            int bytesRead, bytesAvailable, bufferSize;
            byte[] buffer;
            int maxBufferSize = 1 * 1024 * 1024;
            File sourceFile = new File(sourceFileUri);

            if (!sourceFile.isFile()) {

                dialog.dismiss();


                runOnUiThread(new Runnable() {
                    public void run() {

                    }
                });

                return 0;

            }
            else
            {
                try {

                    // open a URL connection to the Servlet
                    FileInputStream fileInputStream = new FileInputStream(sourceFile);
                    URL url = new URL(upLoadServerUri2);

                    // Open a HTTP  connection to  the URL
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true); // Allow Inputs
                    conn.setDoOutput(true); // Allow Outputs
                    conn.setUseCaches(false); // Don't use a Cached Copy
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Connection", "Keep-Alive");
                    conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                    conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                    conn.setRequestProperty("uploaded_file", fileName);

                    dos = new DataOutputStream(conn.getOutputStream());

                    dos.writeBytes(twoHyphens + boundary + lineEnd);
                    dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                            + fileName + "\"" + lineEnd);

                    dos.writeBytes(lineEnd);

                    // create a buffer of  maximum size
                    bytesAvailable = fileInputStream.available();

                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    buffer = new byte[bufferSize];

                    // read file and write it into form...
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                    while (bytesRead > 0) {

                        dos.write(buffer, 0, bufferSize);
                        bytesAvailable = fileInputStream.available();
                        bufferSize = Math.min(bytesAvailable, maxBufferSize);
                        bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                    }

                    // send multipart form data necesssary after file data...
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                    // Responses from the server (code and message)
                    serverResponseCode = conn.getResponseCode();
                    String serverResponseMessage = conn.getResponseMessage();

                    Log.i("uploadFile", "HTTP Response is : "
                            + serverResponseMessage + ": " + serverResponseCode);

                    if(serverResponseCode == 200){

                        runOnUiThread(new Runnable() {
                            public void run() {

//                            Toast.makeText(Adopt2Activity.this, "File Upload Complete.",
//                                    Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    //close the streams //
                    fileInputStream.close();
                    dos.flush();
                    dos.close();

                } catch (MalformedURLException ex) {

                    dialog.dismiss();
                    ex.printStackTrace();

                    runOnUiThread(new Runnable() {
                        public void run() {

                            Toast.makeText(Adopt4Activity.this, "MalformedURLException",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });

                    Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
                } catch (Exception e) {

                    dialog.dismiss();
                    e.printStackTrace();

                    runOnUiThread(new Runnable() {
                        public void run() {

                            Toast.makeText(Adopt4Activity.this, "Got Exception : see logcat ",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                    Log.e("AAAAAAAAAAAA", "Exception : "
                            + e.getMessage(), e);
                }
                dialog.dismiss();
                return serverResponseCode;

            } // End else block
        }


        public int uploadFile3(String sourceFileUri) {

            String fileName = sourceFileUri;

            HttpURLConnection conn = null;
            DataOutputStream dos = null;
            String lineEnd = "\r\n";
            String twoHyphens = "--";
            String boundary = "*****";
            int bytesRead, bytesAvailable, bufferSize;
            byte[] buffer;
            int maxBufferSize = 1 * 1024 * 1024;
            File sourceFile = new File(sourceFileUri);

            if (!sourceFile.isFile()) {

                dialog.dismiss();


                runOnUiThread(new Runnable() {
                    public void run() {

                    }
                });

                return 0;

            }
            else
            {
                try {

                    // open a URL connection to the Servlet
                    FileInputStream fileInputStream = new FileInputStream(sourceFile);
                    URL url = new URL(upLoadServerUri3);

                    // Open a HTTP  connection to  the URL
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true); // Allow Inputs
                    conn.setDoOutput(true); // Allow Outputs
                    conn.setUseCaches(false); // Don't use a Cached Copy
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Connection", "Keep-Alive");
                    conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                    conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                    conn.setRequestProperty("uploaded_file", fileName);

                    dos = new DataOutputStream(conn.getOutputStream());

                    dos.writeBytes(twoHyphens + boundary + lineEnd);
                    dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                            + fileName + "\"" + lineEnd);

                    dos.writeBytes(lineEnd);

                    // create a buffer of  maximum size
                    bytesAvailable = fileInputStream.available();

                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    buffer = new byte[bufferSize];

                    // read file and write it into form...
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                    while (bytesRead > 0) {

                        dos.write(buffer, 0, bufferSize);
                        bytesAvailable = fileInputStream.available();
                        bufferSize = Math.min(bytesAvailable, maxBufferSize);
                        bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                    }

                    // send multipart form data necesssary after file data...
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                    // Responses from the server (code and message)
                    serverResponseCode = conn.getResponseCode();
                    String serverResponseMessage = conn.getResponseMessage();

                    Log.i("uploadFile", "HTTP Response is : "
                            + serverResponseMessage + ": " + serverResponseCode);

                    if(serverResponseCode == 200){

                        runOnUiThread(new Runnable() {
                            public void run() {

                                Toast.makeText(Adopt4Activity.this, "File Upload Complete.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    //close the streams //
                    fileInputStream.close();
                    dos.flush();
                    dos.close();

                } catch (MalformedURLException ex) {

                    dialog.dismiss();
                    ex.printStackTrace();

                    runOnUiThread(new Runnable() {
                        public void run() {

                            Toast.makeText(Adopt4Activity.this, "MalformedURLException",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });

                    Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
                } catch (Exception e) {

                    dialog.dismiss();
                    e.printStackTrace();

                    runOnUiThread(new Runnable() {
                        public void run() {

                            Toast.makeText(Adopt4Activity.this, "Got Exception : see logcat ",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                    Log.e("AAAAAAAAAAAA", "Exception : "
                            + e.getMessage(), e);
                }
                dialog.dismiss();
                return serverResponseCode;

            } // End else block
        }

}
