package org;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ShopPayActivity extends AppCompatActivity {

    private static final int SEARCH_ADDRESS_ACTIVITY = 10000;

    TextView address;
    TextView addressButton;
    EditText addressdetail;
    EditText phone;
    EditText msg;
    TextView price;
    TextView pricedetail;
    Button payButton;

    int delivery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_pay);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);                                       // 위에 툴바 이름 바꾸기
        getSupportActionBar().setTitle("주문 / 결제");                                               // 위에 툴바 이름 바꾸기

        address = findViewById(R.id.address);
        addressButton = findViewById(R.id.addressButton);
        addressdetail = findViewById(R.id.addressdetail);
        phone = findViewById(R.id.phone);
        msg = findViewById(R.id.msg);
        price = findViewById(R.id.price);
        pricedetail = findViewById(R.id.pricedetail);
        payButton = findViewById(R.id.payButton);


        String strPrice = String.format("%,d", ShopCartActivity.price);

   //     총 상품 가격 20,000원 + 배송비 5,000원

        if(ShopCartActivity.price<50000) {

            delivery=5000;
            int allP = ShopCartActivity.price+delivery;
            String strAllP = String.format("%,d", allP);
            price.setText(strAllP+"원");
            pricedetail.setText("총 상품 가격 "+strPrice+"원 + 배송비 5,000원");

        }else{

            delivery=0;
            int allP = ShopCartActivity.price+delivery;
            String strAllP = String.format("%,d", allP);
            price.setText(strAllP+"원");
            pricedetail.setText("총 상품 가격 "+strPrice+"원 + 배송비 0원");

        }


        addressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(ShopPayActivity.this, AddressActivity.class);
                startActivityForResult(i, SEARCH_ADDRESS_ACTIVITY);


            }
        });


        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(address.getText().equals("주소를 입력해 주세요 :)")) {

                    Toast.makeText(getApplicationContext(), "주소를 입력해 주세요 :)", Toast.LENGTH_SHORT).show();

                } else {
                    if (addressdetail.getText().equals("")) {

                        Toast.makeText(getApplicationContext(), "상세 주소를 입력해 주세요 :)", Toast.LENGTH_SHORT).show();

                    } else {
                        if (phone.getText().equals("")) {

                            Toast.makeText(getApplicationContext(), "핸드폰 번호를 입력해 주세요 :)", Toast.LENGTH_SHORT).show();

                        } else {
                            Intent intent = new Intent(ShopPayActivity.this, ShopPaymentActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                }
            }
        });


    }  // onCreate 끝


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{                      //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }



    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        super.onActivityResult(requestCode, resultCode, intent);

        switch(requestCode){
            case SEARCH_ADDRESS_ACTIVITY:
                if(resultCode == RESULT_OK){
                    String data = intent.getExtras().getString("data");
                    if (data != null)
                        address.setText(data);
                }
                break;
        }

    }


}
