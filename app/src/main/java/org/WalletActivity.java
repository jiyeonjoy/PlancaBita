package org;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jFactory;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;
import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

public class WalletActivity extends AppCompatActivity {

    String url = "https://ropsten.infura.io/v3/826ed4e1442f4ebabc3ec4cdb5159170";      // 주소입력
    Web3j web3 = Web3jFactory.build(new HttpService(url));
    String smartContract = "0x1b57225fa13ad35e4d6f1fd7e545dfd2a12e3b4e";               // 내 contract 주소 입력
    String walletAddress = "0x20BB5789f444e47a88c366f0bfE41EcB3c75BD4C";               // 지갑주소수정
    TextView ethAddress, ethBalance, tokenBalance;
    PlanbToken planbToken;
    private String mPriateKey1 = "666A82FC33F8134577A7BEB1BDEAA689BB72740178727691D63032432B83E0FB";   // 불러와야됨!!
    private Credentials credentials;

    ImageView iv_generated_qrcode;


    SharedPreferences pref;
    String nowId;               // 현재 로그인된 아이디


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        nowId = pref.getString("nowId", "");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);                                       // 위에 툴바 이름 바꾸기
        getSupportActionBar().setTitle("내 지갑");                                                   // 위에 툴바 이름 바꾸기

        if(nowId.equals("abc")) {
            walletAddress = "0x20BB5789f444e47a88c366f0bfE41EcB3c75BD4C";               // 지갑주소수정
            mPriateKey1 = "666A82FC33F8134577A7BEB1BDEAA689BB72740178727691D63032432B83E0FB";   // 불러와야됨!!
        }else {
            walletAddress = "0x2DcCa9B61E50D79A90a813fcD6a42c3A3Ac52e6f";               // 지갑주소수정
            mPriateKey1 = "3F0B5C58378DE554534A5A8C630AAC075886E74A6B3229000AE78F4500E153E3";   // 불러와야됨!!
        }

        ethAddress = findViewById(R.id.ethAddress); // Your Ether Address
        ethBalance = findViewById(R.id.ethBalance); // Your Ether Balance
        tokenBalance = findViewById(R.id.tokenBalance); // Token Name

        ethAddress.setText(walletAddress);

        iv_generated_qrcode = findViewById(R.id.iv_generated_qrcode);

        initData();
        getErc20Balance();

        generateRQCode(walletAddress);   // 큐알코드로 지갑주소 생성

        LinearLayout ethSendButton = findViewById(R.id.ethSendButton);
        ethSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SendEthActivity.class);
                startActivity(intent);
                finish();
            }
        });



        LinearLayout tokenSendButton = findViewById(R.id.tokenSendButton);
        tokenSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SendTokenActivity.class);
                startActivity(intent);
                finish();
            }
        });


    } // onCreate 끝


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

    private void initData() {                                   // 처음에 로딩해야됨!!!!!!!!!! KaoPuT
        //账户2的私钥
        credentials = Credentials.create(mPriateKey1);
        //加载智能合约
        planbToken = PlanbToken.load(smartContract, web3, credentials, Contract.GAS_PRICE, Contract.GAS_LIMIT);
        try {
            //       String name = planbToken.name().sendAsync().get();
            String symbol = planbToken.symbol().sendAsync().get();
            ethBalance.setText("symbol: " + symbol);
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println( "eeeeeeeeeeeeeeeeeeeeeeeeeeeee" + e);

        } catch (ExecutionException e) {
            e.printStackTrace();
            System.out.println( "aaaaaaaaaaaaaaaaaaaaaaaaa" + e);

        }
    }

    private void getErc20Balance() {
        EthGetBalance balance = null;
        BigInteger erc20Balance = null;
        try {
            balance = web3.ethGetBalance(walletAddress, DefaultBlockParameterName.LATEST).sendAsync().get();
            erc20Balance = planbToken.balanceOf(walletAddress).sendAsync().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        ethBalance.setText(Convert.fromWei(new BigDecimal(balance.getBalance()), Convert.Unit.ETHER).toPlainString());
        //erc20BalanceTv.setText(Convert.fromWei(new BigDecimal(erc20Balance), Convert.Unit.ETHER).toPlainString());
        tokenBalance.setText(erc20Balance.toString());
        //tokenname.setText("PlanbToken");
    }

    public void generateRQCode(String contents) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        try {
            Bitmap bitmap = toBitmap(qrCodeWriter.encode(contents, BarcodeFormat.QR_CODE, 200, 200));
            ((ImageView) findViewById(R.id.iv_generated_qrcode)).setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
            Log.d("aaaaaaaaaaaaaaaaaaa", e.toString());
        }
    }

    public static Bitmap toBitmap(BitMatrix matrix) {
        int height = matrix.getHeight();
        int width = matrix.getWidth();
        Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                bmp.setPixel(x, y, matrix.get(x, y) ? Color.BLACK : Color.WHITE);
            }
        }
        return bmp;
    }

}