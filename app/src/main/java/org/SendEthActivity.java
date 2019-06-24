package org;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.spongycastle.util.encoders.Hex;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jFactory;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;
import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

public class SendEthActivity extends AppCompatActivity {

    String url = "https://ropsten.infura.io/v3/826ed4e1442f4ebabc3ec4cdb5159170";  // 주소입력
    Web3j web3 = Web3jFactory.build(new HttpService(url));
    String smartContract = "0x1b57225fa13ad35e4d6f1fd7e545dfd2a12e3b4e";               // 내 contract 주소 입력
    String walletAddress = "0x20BB5789f444e47a88c366f0bfE41EcB3c75BD4C";        // 지갑주소수정


    TextView nowEth, tv_gas_limit, tv_gas_price, tv_fee;
    EditText sendToAddress, sendEth;


    //private ProgressDialog mProgressDialog;

    PlanbToken planbToken;
    private String mPriateKey1 = "666A82FC33F8134577A7BEB1BDEAA689BB72740178727691D63032432B83E0FB";
    private Credentials credentials;

   SendingEther se;

    BigInteger GasPrice, GasLimit;


    SharedPreferences pref;
    String nowId;               // 현재 로그인된 아이디

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_eth);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);                                       // 위에 툴바 이름 바꾸기
        getSupportActionBar().setTitle("이더 전송");                                                   // 위에 툴바 이름 바꾸기

        pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        nowId = pref.getString("nowId", "");

        if(nowId.equals("abc")) {
            walletAddress = "0x20BB5789f444e47a88c366f0bfE41EcB3c75BD4C";               // 지갑주소수정
            mPriateKey1 = "666A82FC33F8134577A7BEB1BDEAA689BB72740178727691D63032432B83E0FB";   // 불러와야됨!!
        }else {
            walletAddress = "0x2DcCa9B61E50D79A90a813fcD6a42c3A3Ac52e6f";               // 지갑주소수정
            mPriateKey1 = "3F0B5C58378DE554534A5A8C630AAC075886E74A6B3229000AE78F4500E153E3";   // 불러와야됨!!
        }



        sendToAddress =  findViewById(R.id.sendToAddress); // Address for sending ether or token
        sendEth = findViewById(R.id.sendEth); // Ammount ether for sending
        nowEth = findViewById(R.id.nowEth); // Ammount ether for sending

        tv_gas_limit = findViewById(R.id.tv_gas_limit);
        tv_gas_price = findViewById(R.id.tv_gas_price);
        tv_fee = findViewById(R.id.tv_fee);

        initData();
        getErc20Balance();


        se = new SendingEther();

        Button btn_start_qrcode_reader = findViewById(R.id.btn_start_qrcode_reader);
        btn_start_qrcode_reader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startQRCode();
            }
        });


       Button sendEtherButton = findViewById(R.id.sendEtherButton);
        sendEtherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                se.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        });


        final SeekBar sb_gas_limit = (SeekBar) findViewById(R.id.sb_gas_limit);
        sb_gas_limit.setOnSeekBarChangeListener(seekBarChangeListenerGL);
        final SeekBar sb_gas_price = (SeekBar) findViewById(R.id.sb_gas_price);
        sb_gas_price.setOnSeekBarChangeListener(seekBarChangeListenerGP);

        GetFee();

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


    public void startQRCode() {
        new IntentIntegrator(this).initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == IntentIntegrator.REQUEST_CODE) {
            IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (result == null) {
               // Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
               // Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                sendToAddress.setText(result.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void initData() {                                   // 처음에 로딩해야됨!!!!!!!!!! KaoPuT
        //账户2的私钥
        credentials = Credentials.create(mPriateKey1);
        //加载智能合约
        planbToken = PlanbToken.load(smartContract, web3, credentials, Contract.GAS_PRICE, Contract.GAS_LIMIT);
    }

    private void getErc20Balance() {
        EthGetBalance balance = null;
        try {
            balance = web3.ethGetBalance(walletAddress, DefaultBlockParameterName.LATEST).sendAsync().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        nowEth.setText(Convert.fromWei(new BigDecimal(balance.getBalance()), Convert.Unit.ETHER).toPlainString());
    }


    /////////////////// SeekBar Listener ////////////////////
    /**
     * SeekBar Слушатель
     * SeekBar Listener
     */
    private SeekBar.OnSeekBarChangeListener seekBarChangeListenerGL = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            GetGasLimit(String.valueOf(seekBar.getProgress()*1000+21000));
        }
        @Override public void onStartTrackingTouch(SeekBar seekBar) { }
        @Override public void onStopTrackingTouch(SeekBar seekBar) { }
    };
    private SeekBar.OnSeekBarChangeListener seekBarChangeListenerGP = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            GetGasPrice(String.valueOf(seekBar.getProgress()+4));
        }
        @Override public void onStartTrackingTouch(SeekBar seekBar) { }
        @Override public void onStopTrackingTouch(SeekBar seekBar) { }
    };
    ///////////////// END SeekBar Listener /////////////////

    ///////////////////// Gas View /////////////////////////

    /**
     * Значение присваивается визуальным элементам
     * The value is assigned to the visual elements
     * @param value Value Gas Limit and Gas Price
     */
    public void GetGasLimit(String value) {
        tv_gas_limit.setText(value);
        GetFee();
    }
    public void GetGasPrice(String value) {
        tv_gas_price.setText(value);
        GetFee();
    }
    /////////////////////////////////////////////////////////////////

    /////////////////////////// Get Fee /////////////////////////////

    /**
     * Значение GazLimit и GasPrice конвертируеться в BigInteger и присваиваеться глобальным переменным
     * The value GazLimit and GasPrice converteres in BigInteger and prizhivaetsya global variables
     *
     * Расчет вознагрождения для майнеров
     * calculate the fee for miners
     */

    public void GetFee(){
        GasPrice = Convert.toWei(tv_gas_price.getText().toString(),Convert.Unit.GWEI).toBigInteger();
        GasLimit = BigInteger.valueOf(Integer.valueOf(String.valueOf(tv_gas_limit.getText())));

        // fee
        BigDecimal fee = BigDecimal.valueOf(GasPrice.doubleValue()*GasLimit.doubleValue());
        BigDecimal feeresult = Convert.fromWei(fee.toString(),Convert.Unit.ETHER);
        tv_fee.setText(feeresult.toPlainString() + " ETH");
    }
    ///////////////////////// End Get Fee ///////////////////////////



    ///////////////////////// Sending Ether //////////////////////
    public class SendingEther  extends AsyncTask<Void, Integer, org.json.simple.JSONObject> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
       //     mProgressDialog = ProgressDialog.show(getApplicationContext(), "", "전송중...");

        }

        @Override
        protected org.json.simple.JSONObject doInBackground(Void... param) {

            try {

                EthGetTransactionCount ethGetTransactionCount = web3.ethGetTransactionCount(walletAddress, DefaultBlockParameterName.LATEST).sendAsync().get();
                BigInteger nonce = ethGetTransactionCount.getTransactionCount();

                /**
                 * Convert ammount ether to BigInteger
                 */
                BigInteger value = Convert.toWei(String.valueOf(sendEth.getText()), Convert.Unit.ETHER).toBigInteger();

                /**
                 * Транзакция
                 * Transaction
                 */
                RawTransaction rawTransaction  = RawTransaction.createEtherTransaction(nonce, GasPrice, GasLimit, String.valueOf(sendToAddress.getText()), value);
                byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
                String hexValue = "0x"+ Hex.toHexString(signedMessage);
                EthSendTransaction ethSendTransaction = web3.ethSendRawTransaction(hexValue.toString()).sendAsync().get();

                /**
                 * Get Transaction Error and Hash
                 */
                System.out.println("Error: "+ ethSendTransaction.getError());
                System.out.println("Transaction: " + ethSendTransaction.getTransactionHash());

                /**
                 * Возвращаем из потока, Адрес и Хэш транзакции
                 * Returned from thread, Ether Address and transaction hash
                 */
                org.json.simple.JSONObject JsonResult = new org.json.simple.JSONObject();
                JsonResult.put("Address", walletAddress);
                JsonResult.put("TransactionHash", ethSendTransaction.getTransactionHash());

                return JsonResult;

            }catch (Exception ex) {ex.printStackTrace();}
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

        }

        @Override
        protected void onPostExecute(org.json.simple.JSONObject result) {
            super.onPostExecute(result);
            try {
                /**
                 * Получаем баланс Ethereum
                 * Get balance Ethereum
                 */
                EthGetBalance etherbalance = web3.ethGetBalance(result.get("Address").toString(), DefaultBlockParameterName.LATEST).sendAsync().get();
                String ethbalanceafter = Convert.fromWei(String.valueOf(etherbalance.getBalance()), Convert.Unit.ETHER).toString();
                System.out.println("Eth Balance: " + ethbalanceafter);

            } catch(Exception ex) {System.out.println(ex);}

//            Toast toast = Toast.makeText(getApplicationContext(),result.get("TransactionHash").toString(), Toast.LENGTH_LONG);
//            toast.show();

//            Snackbar snackbar = Snackbar.make(getWindow().getDecorView().getRootView(), result.get("TransactionHash").toString(), Snackbar.LENGTH_LONG);
//            snackbar.show();
            System.out.println("assssssssasasasaasasa");
            System.out.println(result.get("TransactionHash").toString());

            Snackbar.make(getWindow().getDecorView().getRootView(), result.get("TransactionHash").toString(), 10000).setAction("이동", new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {

                    String urll ="https://ropsten.etherscan.io/tx/"+result.get("TransactionHash").toString();
                    Intent intent = new Intent(getApplicationContext(), WebViewActivity.class);
                    intent.putExtra("url", urll);
                    startActivity(intent);
                    finish();

                }
            }).show();

          //  mProgressDialog.dismiss();
         //   finish();
        }

    }
    //////////////////// End Sending Ether ///////////////////////

}
