package org;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class WalletMainActivity extends AppCompatActivity {

    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);                                       // 위에 툴바 이름 바꾸기
        getSupportActionBar().setTitle("지갑 열기");                                                   // 위에 툴바 이름 바꾸기

        password = findViewById(R.id.password);
        Button walletButton = findViewById(R.id.walletButton);
        walletButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(password.getText().toString().equals("1234"))
                {
                    Intent intent = new Intent(getApplicationContext(), WalletActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "비밀번호가 올바르지 않습니다!", Toast.LENGTH_LONG);
                    toast.show();
                }


            }
        });


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
}
