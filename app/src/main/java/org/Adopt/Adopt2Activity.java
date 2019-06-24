package org.Adopt;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Adopt2Activity extends AppCompatActivity {

    private static final int INTENT_REQUEST_GET_IMAGES = 13;
    public static final int REQUEST_CODE_AREA = 33;

    private static final String TAG = "TedPicker";
    ArrayList<Uri> image_uris = new ArrayList<Uri>();
    private ViewGroup mSelectedImagesContainer;

    private static final int MY_PERMISSION_CAMERA = 1111;


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
    String gender="남아";

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adopt2);

        Toolbar toolbar = findViewById(R.id.toolBar);                                                // 위에 툴바 이름 바꾸기
        setSupportActionBar(toolbar);                                                                // 위에 툴바 이름 바꾸기
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);                                       // 위에 툴바 이름 바꾸기
        getSupportActionBar().setTitle("분양 등록");                                                 // 위에 툴바 이름 바꾸기


        checkPermission();                                                                           // 권한 확인

//        Intent intent = getIntent();
//        type = intent.getStringExtra("type");

            upLoadServerUri1 = "http://ec2-15-164-102-182.ap-northeast-2.compute.amazonaws.com/planb/abandimg1.php";       // 서버컴퓨터의 ip주소
            upLoadServerUri2 = "http://ec2-15-164-102-182.ap-northeast-2.compute.amazonaws.com/planb/abandimg2.php";        // 서버컴퓨터의 ip주소
            upLoadServerUri3 = "http://ec2-15-164-102-182.ap-northeast-2.compute.amazonaws.com/planb/abandimg3.php";        // 서버컴퓨터의 ip주소

        pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
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
                config.setFlashOn(false);
                getImages(config);

                img1 ="aa";
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


                if(title.getText().toString().trim().equals("")){
                    Toast.makeText(getApplicationContext(), "제목을 입력해 주세요.", Toast.LENGTH_SHORT).show();
                    Log.i("AAAAAAAAAAAAAA", "ddddddddddddddddddddddddd");
                }else {
                    if(contents.getText().toString().trim().equals("")) {
                        Toast.makeText(getApplicationContext(), "내용을 입력해 주세요.", Toast.LENGTH_SHORT).show();
                    }else{
                        if(img1.equals("")) {
                            Toast.makeText(getApplicationContext(), "사진을 선택해 주세요.", Toast.LENGTH_SHORT).show();
                        }else {
                            if(name.getText().toString().trim().equals("")) {
                                Toast.makeText(getApplicationContext(), "이름을 입력해 주세요.", Toast.LENGTH_SHORT).show();
                            } else {
                            if(dogType.getText().toString().trim().equals("")) {
                                Toast.makeText(getApplicationContext(), "품종을 입력해 주세요.", Toast.LENGTH_SHORT).show();
                            }else {
                                if(character.getText().toString().trim().equals("")) {
                                    Toast.makeText(getApplicationContext(), "특징을 입력해 주세요.", Toast.LENGTH_SHORT).show();
                                }else {
                                    if(phone.getText().toString().trim().equals("")) {
                                        Toast.makeText(getApplicationContext(), "연락처를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                                    }else {
                                        if(area.getText().toString().trim().equals("")) {
                                            Toast.makeText(getApplicationContext(), "지역을 선택해 주세요.", Toast.LENGTH_SHORT).show();
                                        }else {
                                            if(price.getText().toString().trim().equals("")) {
                                                Toast.makeText(getApplicationContext(), "분양가를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                                            }else{

                                                img1 = image_uris.get(0).toString();
                                                img2 = image_uris.get(1).toString();
                                                img3 = image_uris.get(2).toString();

                                                Log.i("AAAAAAAAAAAAAA", "ffffffffffffffffffffffff");
                                                dialog = ProgressDialog.show(Adopt2Activity.this, "", "Uploading file...", true);

                                                new Thread(new Runnable() {
                                                    public void run() {
                                                        runOnUiThread(new Runnable() {
                                                            public void run() {

                                                            }
                                                        });

                                                        uploadFile1(img1);
                                                        uploadFile2(img2);
                                                        uploadFile3(img3);

                                                        Log.i("AAAAAAAAAAAAAA", "HTTP Response is : "+image_uris.get(0).toString());

                                                    }
                                                }).start();

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

                                                     AbandAdoptRequest abandAdoptRequest = new AbandAdoptRequest(title1, contents1, name1, dogType1, character1, phone1, area1, price1, ageYear1, ageMonth1, weight1, gender, nowId, nowIdType, responseListener);
                                                     RequestQueue queue = Volley.newRequestQueue(Adopt2Activity.this);
                                                     queue.add(abandAdoptRequest);

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
            }
        });


        Button cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }     // onCreate 끝

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


    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // 처음 호출시엔 if()안의 부분은 false로 리턴 됨 -> else{ {..}의 요청으로 넘어감
            if ((ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA))) {
                new android.app.AlertDialog.Builder(this)
                        .setTitle("알림")
                        .setMessage("저장소 권한이 거부되었습니다. 사용을 원하시면 설정해서 해당 권한을 직접 허용하셔야 합니다.")
                        .setNeutralButton("설정", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                intent.setData(Uri.parse("package:" +getPackageName()));
                                startActivity(intent);
                            }
                        })
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .setCancelable(false)
                        .create()
                        .show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, MY_PERMISSION_CAMERA);


            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_CAMERA:
                for (int i=0; i < grantResults.length; i++) {
                    // grantResults[] : 허용된 권한은 0, 거부한 권한은 -1
                    if(grantResults[i] < 0) {
                        Toast.makeText(Adopt2Activity.this, "해당 권한을 활성화 하셔야 합니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                // 허용했다면 이부분에서

                break;
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

                        Toast.makeText(Adopt2Activity.this, "MalformedURLException",
                                Toast.LENGTH_SHORT).show();
                    }
                });

                Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
            } catch (Exception e) {

                dialog.dismiss();
                e.printStackTrace();

                runOnUiThread(new Runnable() {
                    public void run() {

                        Toast.makeText(Adopt2Activity.this, "Got Exception : see logcat ",
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

                            Toast.makeText(Adopt2Activity.this, "File Upload Complete.",
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

                        Toast.makeText(Adopt2Activity.this, "MalformedURLException",
                                Toast.LENGTH_SHORT).show();
                    }
                });

                Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
            } catch (Exception e) {

                dialog.dismiss();
                e.printStackTrace();

                runOnUiThread(new Runnable() {
                    public void run() {

                        Toast.makeText(Adopt2Activity.this, "Got Exception : see logcat ",
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

