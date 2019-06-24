package org;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.zomato.photofilters.imageprocessors.Filter;
import com.zomato.photofilters.imageprocessors.subfilters.BrightnessSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.ContrastSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.SaturationSubfilter;

import org.Adapter.ViewPagerAdapter;
import org.Adopt.AbandAdoptRequest;
import org.Adopt.Adopt2Activity;
import org.Interface.EditImageFragmentListener;
import org.Interface.FiltersListFragmentListener;
import org.Utils.BitmapUtils;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static com.gun0912.tedpicker.util.Util.exifOrientationToDegrees;
import static com.gun0912.tedpicker.util.Util.rotate;

public class ImgFilterActivity extends AppCompatActivity implements FiltersListFragmentListener,EditImageFragmentListener {

    public static final String pictureName = "dog1.png";
    public static final int PERMISSION_PICK_IMAGE = 1000;

    ImageView img_preview;
    TabLayout tabLayout;
    ViewPager viewPager;
    CoordinatorLayout coordinatorLayout;

    Bitmap originalBitmap, filteredBitmap, finalBitmap;
    FiltersListFragment filtersListFragment;
    EditImageFragment editImageFragment;

    int brightnessFinal=0;
    float saturationFinal=1.0f;
    float constrantFinal = 1.0f;

    SharedPreferences pref;
    String nowId;

    ProgressDialog dialog = null;

    String upLoadServerUri1 = null;
    int serverResponseCode = 0;

    String updateImgUri;

    private final int REQUEST_IMAGE_FILTER = 5555;

    // Load native image filters lib
    static {
        System.loadLibrary("NativeImageProcessor");
    }


    Bitmap bitmap;
//    Bitmap bitmap33;

    int aaa =0;          // 사진 갤러리에서 가져왔는지 아닌지 확인값 -- 0이면 안가져온거임
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imgfilter);

        upLoadServerUri1 = "http://ec2-15-164-102-182.ap-northeast-2.compute.amazonaws.com/planb/profilImg.php";       // 서버컴퓨터의 ip주소

        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("이미지 필터");

        // View
        img_preview = findViewById(R.id.image_preview);
        tabLayout = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.viewpager);
        coordinatorLayout = findViewById(R.id.coordinator);


//        Intent intent = getIntent();
//        String imgUriString = intent.getExtras().getString("imgUri");
//        Uri imgUri = Uri.parse(imgUriString);
//
//        Log.i("AAAAAAAAAAAAAAAAAAAA", imgUriString);

        pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        nowId = pref.getString("nowId", "");


        if(aaa==0) {
            Intent intent = new Intent(getApplicationContext(), TestActivity.class);
            startActivityForResult(intent, REQUEST_IMAGE_FILTER);
        }


//        Uri imgUri = Uri.parse(imgUriString);


//        String[] filePathColumn = {MediaStore.Images.Media.DATA};
//        Cursor cursor = getApplication().getContentResolver().query(imgUri, filePathColumn, null, null, null);
//        cursor.moveToFirst();
//        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//        String picturePath = cursor.getString(columnIndex);
//        cursor.close();
//
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;
//        BitmapFactory.decodeFile(picturePath, options);
//      //  options.inSampleSize = calculateInSampleSize(options, width, height);
//        options.inJustDecodeBounds = false;
//
//        Bitmap bitmap3 = BitmapFactory.decodeFile(picturePath, options);
//
//        // clear bitmap memory
//        originalBitmap.recycle();
//        finalBitmap.recycle();
//        filteredBitmap.recycle();
//
//        originalBitmap = bitmap3.copy(Bitmap.Config.ARGB_8888, true);
//        finalBitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);
//        filteredBitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);
//        img_preview.setImageBitmap(originalBitmap);
//        bitmap3.recycle();
//
//        // Render selected img thumbnail
//        filtersListFragment.displayThumbnail(originalBitmap);



//        try {
//
//            bitmap33 = MediaStore.Images.Media.getBitmap(getContentResolver(), imgUri);
//            img_preview.setImageBitmap(bitmap33);
//            //ImageView에 bmp 뿌리기
//        } catch (FileNotFoundException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            Log.i("AAAAAAAAAAAAAAAAAAAA", e.toString());
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            Log.i("AAAAAAAAAAAAAAAAAAAA", e.toString());
//        }
//









//        String imagePath = getRealPathFromURI(imgUri); // path 경로
//        ExifInterface exif = null;
//        try {
//            exif = new ExifInterface(imagePath);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        int exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
//        int exifDegree = exifOrientationToDegrees(exifOrientation);
//
//        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);//경로를 통해 비트맵으로 전환
//        img_preview.setImageBitmap(rotate(bitmap, exifDegree));//이미지 뷰에 비트맵 넣기






     //   Bitmap bitmap33 = BitmapUtils.getBitmapFromGallery(this, imgUri, 800, 800);
//
        // clear bitmap memory
//        originalBitmap.recycle();
//        finalBitmap.recycle();
//        filteredBitmap.recycle();





//        originalBitmap = bitmap33.copy(Bitmap.Config.ARGB_8888, true);
//        finalBitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);
//        filteredBitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);
//        img_preview.setImageBitmap(originalBitmap);
//        bitmap33.recycle();
//
//        // Render selected img thumbnail
//        filtersListFragment.displayThumbnail(originalBitmap);






        // setImageURI - Uri 경로에 따른 SDCard에 있는 이미지 파일을 로드한다.
//                    try {
//                        img_preview.setImageURI(imgUri);
//                    }catch (Exception e){
//                        e.printStackTrace();
//                    }

       // loadImage();

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void loadImage() {
     //   originalBitmap = BitmapUtils.getBitmapFromAssets(this, pictureName, 300,300);
        //  originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.dog1);

        filteredBitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);
        finalBitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);
        img_preview.setImageBitmap(originalBitmap);
    }

    private void setupViewPager (ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        filtersListFragment = new FiltersListFragment();
        filtersListFragment.setListener(this);

        editImageFragment = new EditImageFragment();
        editImageFragment.setListener(this);

        adapter.addFragment(filtersListFragment, "FILTERS");
        adapter.addFragment(editImageFragment, "EDIT");

        viewPager.setAdapter(adapter);


    }

    @Override
    public void onBrightnessChanged(int brightness) {
        brightnessFinal = brightness;
        Filter myFilter = new Filter();
        myFilter.addSubFilter(new BrightnessSubFilter(brightness));
        img_preview.setImageBitmap(myFilter.processFilter(finalBitmap.copy(Bitmap.Config.ARGB_8888, true)));

    }

    @Override
    public void onSaturationChanged(float saturation) {
        saturationFinal = saturation;
        Filter myFilter = new Filter();
        myFilter.addSubFilter(new SaturationSubfilter(saturation));
        img_preview.setImageBitmap(myFilter.processFilter(finalBitmap.copy(Bitmap.Config.ARGB_8888, true)));

    }

    @Override
    public void nConstrantChanged(float constrant) {
        constrantFinal = constrant;
        Filter myFilter = new Filter();
        myFilter.addSubFilter(new ContrastSubFilter(constrant));
        img_preview.setImageBitmap(myFilter.processFilter(finalBitmap.copy(Bitmap.Config.ARGB_8888, true)));
    }

    @Override
    public void onEditStarted() {

    }

    @Override
    public void onEditCompleted() {

        Bitmap bitmap = filteredBitmap.copy(Bitmap.Config.ARGB_8888, true);

        Filter myFilter = new Filter();
        myFilter.addSubFilter(new BrightnessSubFilter(brightnessFinal));
        myFilter.addSubFilter(new SaturationSubfilter(saturationFinal));
        myFilter.addSubFilter(new ContrastSubFilter(constrantFinal));

        finalBitmap = myFilter.processFilter(bitmap);

    }

    @Override
    public void onFilterSelected(Filter filter) {
        resetControl();
        filteredBitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);
        img_preview.setImageBitmap(filter.processFilter(filteredBitmap));
        finalBitmap = filteredBitmap.copy(Bitmap.Config.ARGB_8888, true);

    }

    private void resetControl() {
        if(editImageFragment != null)
            editImageFragment.resetControls();
        brightnessFinal=0;
        saturationFinal=1.0f;
        constrantFinal=1.0f;
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_open) {
            openImageFromGallery();
            return true;
          //  return super.onOptionsItemSelected(item);
        }
        else if(id ==R.id.action_save) {
            saveImageToGallery();


//            Menu4Fragment.imageView.setImageBitmap(finalBitmap);
            finish();
            return true;
        }
        else {
            finish();
            return true;
        }
        //   return super.onOptionsItemSelected(item);
    }

    private void saveImageToGallery() {
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if(report.areAllPermissionsGranted()){

                            try {
                                long currentTime = System.currentTimeMillis()/1000;
                                int a =11111;

                                String aaaa = BitmapUtils.insertImage(getContentResolver(), finalBitmap, currentTime + "_profile.jpg", null);
                               // String aaaa = "/storage/emulated/0/Pictures/1532416471009.jpg";
                                Uri dd = Uri.parse(aaaa);
                                updateImgUri = getRealPathFromURI(dd);                                     // 이거임!!!!!!!!!!!!!!!!!!!!!!!!!갤러리 uriㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜ

                                Log.i("bbbbbbbbbbbbbbbbb", updateImgUri);
                               dialog = ProgressDialog.show(ImgFilterActivity.this, "", "Uploading file...", true);


                                new Thread(new Runnable() {
                                    public void run() {
                                        runOnUiThread(new Runnable() {
                                            public void run() {

                                            }
                                        });


                                        uploadFile(updateImgUri);
                                        Log.i("AAAAAAAAAAAAAA", "업로드시작ㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱ");

                                    }
                                }).start();



                                Response.Listener<String> responseListener = new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                };

                                ProfileImgUpdateRequest profileImgUpdateRequest = new ProfileImgUpdateRequest(nowId, updateImgUri, responseListener);
                                RequestQueue queue = Volley.newRequestQueue(ImgFilterActivity.this);
                                queue.add(profileImgUpdateRequest);

//                                Uri dd = Uri.parse(aaaa);
//                                String abc = getRealPathFromURI(dd);
//                                Log.i("bbbbbbbbbbbbb", abc);
         //                       Uri uriiiiii = Uri.parse(path);
                            // String aaaaaa =  "/storage/emulated/0/Pictures/"+aaaa + ".jpg";
                                Log.i("AAAAAAAAAAAAAAAAAAAA", aaaa);

//                                if(!TextUtils.isEmpty(path)) {
//                                    Snackbar snackbar = Snackbar.make(coordinatorLayout, "Image saved to gallery!", Snackbar.LENGTH_LONG).setAction("OPEN", new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View v) {
//                                            openImage(path);
//                                        }
//                                    });
//                                    snackbar.show();
//                                }
//                                else{
//                                    Snackbar snackbar = Snackbar.make(coordinatorLayout, "Unable to save image", Snackbar.LENGTH_LONG);
//                                    snackbar.show();
//                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }else {
                            Toast.makeText(ImgFilterActivity.this, "Permission denied!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();

                    }
                })
                .check();

    }

    private void openImage(String path) {

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse(path), "image/*");
        startActivity(intent);

    }


    private void openImageFromGallery() {
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if(report.areAllPermissionsGranted()){
                            Intent intent = new Intent(Intent.ACTION_PICK);
                            intent.setType("image/*");
                            startActivityForResult(intent, PERMISSION_PICK_IMAGE);
                        }else {
                            Toast.makeText(ImgFilterActivity.this, "Permission denied!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .check();
    }


    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        if(resultCode ==RESULT_OK && requestCode == PERMISSION_PICK_IMAGE) {
            Bitmap bitmap = BitmapUtils.getBitmapFromGallery(this, data.getData(), 800, 800);
            aaa = 1;
            // clear bitmap memory
            originalBitmap.recycle();
            finalBitmap.recycle();
            filteredBitmap.recycle();

            originalBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
            finalBitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);
            filteredBitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);
            img_preview.setImageBitmap(originalBitmap);
            bitmap.recycle();

            // Render selected img thumbnail
            filtersListFragment.displayThumbnail2(originalBitmap);
//        } if(resultCode ==REQUEST_IMAGE_FILTER) {
//            profileView();
//        }
        } else if(resultCode==RESULT_OK && requestCode == REQUEST_IMAGE_FILTER) {
            aaa = 1;
            profileView2();
        }
    }


    public void profileView2() {


        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    String userImg2 = jsonResponse.getString("userImg");

                    Thread mThread = new Thread() {
                        @Override
                        public void run() {
                            try {
                                URL url = new URL(userImg2);

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
                        img_preview.setImageBitmap(bitmap);


                        originalBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
                        finalBitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);
                        filteredBitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);
                        img_preview.setImageBitmap(originalBitmap);
                        bitmap.recycle();

                        // Render selected img thumbnail

                            filtersListFragment.displayThumbnail2(originalBitmap);


                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "aaaaaaaaaaa", Toast.LENGTH_SHORT).show();
                }
            }
        };

        ProfileRequest profileRequest = new ProfileRequest(nowId, responseListener);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(profileRequest);
    }




    public void profileView() {

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    String userImg2 = jsonResponse.getString("userImg");

                    Thread mThread = new Thread() {
                        @Override
                        public void run() {
                            try {
                                URL url = new URL(userImg2);

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
                        img_preview.setImageBitmap(bitmap);


                        originalBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
                        finalBitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);
                        filteredBitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);
                        img_preview.setImageBitmap(originalBitmap);
                        bitmap.recycle();

                        // Render selected img thumbnail
                        filtersListFragment.displayThumbnail2(originalBitmap);


                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "aaaaaaaaaaa", Toast.LENGTH_SHORT).show();
                }
            }
        };

        ProfileRequest profileRequest = new ProfileRequest(nowId, responseListener);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(profileRequest);

    }







//    private String getRealPathFromURI(Uri contentUri) {
//        int column_index=0;
//        String[] proj = {MediaStore.Images.Media.DATA};
//        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
////        if(cursor.moveToFirst()){
//            column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
////        }
//
//        return cursor.getString(column_index);
//    }



    public int uploadFile(String sourceFileUri) {
        Log.i("AAAAAAAAAAAAAA", "sssssssssssssssssssssssss");
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
        Log.i("AAAAAAAAAAAAAA", "업로드시작sssssssssssssssss");
        if (!sourceFile.isFile()) {

            dialog.dismiss();


            runOnUiThread(new Runnable() {
                public void run() {
                    Log.i("AAAAAAAAAAAAAA", "업로드시작sdddddddddddddddssssssssssssssss");
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






    // 이틀동안 찾았던 코드임 ㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜ이것만있으면 갤러리 이미지알수있으무ㅜㅜㅜㅜㅜ아감동
    private String getRealPathFromURI(Uri contentURI) {

        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);

        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();

        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }

        return result;
    }






}
