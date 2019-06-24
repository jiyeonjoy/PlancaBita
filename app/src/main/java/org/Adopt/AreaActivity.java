package org.Adopt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.Adopt.Adopt2Activity;
import org.R;


public class AreaActivity extends AppCompatActivity {

    LinearLayout linear;
    LinearLayout linear1;
    LinearLayout linear2;
    LinearLayout linear3;
    LinearLayout linear4;
    LinearLayout linear5;
    LinearLayout linear6;
    LinearLayout linear7;
    LinearLayout linear8;
    LinearLayout linear9;
    LinearLayout linear10;
    LinearLayout linear11;

    TextView area1;
    TextView area2;
    TextView area3;
    TextView area4;
    TextView area5;
    TextView area6;
    TextView area7;
    TextView area8;
    TextView area9;
    TextView area10;
    TextView area11;
    TextView area12;
    TextView area13;
    TextView area14;
    TextView area15;
    TextView area16;
    TextView area17;
    TextView area18;
    TextView area19;
    TextView area20;
    TextView area21;
    TextView area22;
    TextView area23;
    TextView area24;
    TextView area25;
    TextView area26;
    TextView area27;
    TextView area28;
    TextView area29;
    TextView area30;
    TextView area31;
    TextView area32;
    TextView area33;
    TextView areaarea;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);                                          // 백키

        linear = findViewById(R.id.linear);
        linear1 = findViewById(R.id.linear1);
        linear2 = findViewById(R.id.linear2);
        linear3 = findViewById(R.id.linear3);
        linear4 = findViewById(R.id.linear4);
        linear5 = findViewById(R.id.linear5);
        linear6 = findViewById(R.id.linear6);
        linear7 = findViewById(R.id.linear7);
        linear8 = findViewById(R.id.linear8);
        linear9 = findViewById(R.id.linear9);
        linear10 = findViewById(R.id.linear10);
        linear11 = findViewById(R.id.linear11);

        area1 = findViewById(R.id.area1);
        area2 = findViewById(R.id.area2);
        area3 = findViewById(R.id.area3);
        area4 = findViewById(R.id.area4);
        area5 = findViewById(R.id.area5);
        area6 = findViewById(R.id.area6);
        area7 = findViewById(R.id.area7);
        area8 = findViewById(R.id.area8);
        area9 = findViewById(R.id.area9);
        area10 = findViewById(R.id.area10);
        area11 = findViewById(R.id.area11);
        area12 = findViewById(R.id.area12);
        area13 = findViewById(R.id.area13);
        area14 = findViewById(R.id.area14);
        area15 = findViewById(R.id.area15);
        area16 = findViewById(R.id.area16);
        area17 = findViewById(R.id.area17);
        area18 = findViewById(R.id.area18);
        area19 = findViewById(R.id.area19);
        area20 = findViewById(R.id.area20);
        area21 = findViewById(R.id.area21);
        area22 = findViewById(R.id.area22);
        area23 = findViewById(R.id.area23);
        area24 = findViewById(R.id.area24);
        area25 = findViewById(R.id.area25);
        area26 = findViewById(R.id.area26);
        area27 = findViewById(R.id.area27);
        area28 = findViewById(R.id.area28);
        area29 = findViewById(R.id.area29);
        area30 = findViewById(R.id.area30);
        area31 = findViewById(R.id.area31);
        area32 = findViewById(R.id.area32);
        area33 = findViewById(R.id.area33);
        areaarea = findViewById(R.id.areaarea);



            area1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(area1.getText().equals("서울시")) {
                        linear7.setVisibility(View.VISIBLE);
                        linear8.setVisibility(View.VISIBLE);
                        linear9.setVisibility(View.VISIBLE);

                        area1.setText("서울시 전체");
                        area2.setText("강남구");
                        area3.setText("강동구");
                        area4.setText("강북구");
                        area5.setText("강서구");
                        area6.setText("관악구");
                        area7.setText("광진구");
                        area8.setText("구로구");
                        area9.setText("금천구");
                        area10.setText("노원구");
                        area11.setText("도봉구");
                        area12.setText("동대문구");
                        area13.setText("동작구");
                        area14.setText("마포구");
                        area15.setText("서대문구");
                        area16.setText("서초구");
                        area17.setText("성동구");
                        area18.setText("성북구");
                        area19.setText("송파구");
                        area20.setText("양천구");
                        area21.setText("영등포구");
                        area22.setText("용산구");
                        area23.setText("은평구");
                        area24.setText("종로구");
                        area25.setText("중구");
                        area26.setText("중랑구");
                        area27.setText("");
                        areaarea.setText("서울시");
                    } else {
                        String areaName = area1.getText().toString();
                        Intent intent = new Intent(getApplicationContext(), Adopt2Activity.class);
                        intent.putExtra("area", areaName);
                        setResult(RESULT_OK, intent);
                        finish();
                    }

                }
            });



        area2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(area2.getText().equals("경기도")) {
                    linear7.setVisibility(View.VISIBLE);
                    linear8.setVisibility(View.VISIBLE);
                    linear9.setVisibility(View.VISIBLE);
                    linear10.setVisibility(View.VISIBLE);
                    linear11.setVisibility(View.VISIBLE);

                    area1.setText("경기도 전체");
                    area2.setText("가평군");
                    area3.setText("고양시");
                    area4.setText("과천시");
                    area5.setText("광명시");
                    area6.setText("광주시");
                    area7.setText("구리시");
                    area8.setText("군포시");
                    area9.setText("김포시");
                    area10.setText("남양주시");
                    area11.setText("동두천시");
                    area12.setText("부천시");
                    area13.setText("성남시");
                    area14.setText("수원시");
                    area15.setText("시흥시");
                    area16.setText("안산시");
                    area17.setText("안성시");
                    area18.setText("안양시");
                    area19.setText("양주시");
                    area20.setText("양평군");
                    area21.setText("여주시");
                    area22.setText("연천군");
                    area23.setText("오산시");
                    area24.setText("용인시");
                    area25.setText("의왕시");
                    area26.setText("의정부시");
                    area27.setText("이천시");
                    area28.setText("파주시");
                    area29.setText("평택시");
                    area30.setText("포천시");
                    area31.setText("하남시");
                    area32.setText("화성시");
                    area33.setText("");
                    areaarea.setText("경기도");
                } else {
                    String areaName = area2.getText().toString();
                    String areaName2 = areaarea.getText().toString();
                    String areaName3 = areaName2+" "+areaName;
                    Intent intent = new Intent(getApplicationContext(), Adopt2Activity.class);
                    intent.putExtra("area", areaName3);
                    setResult(RESULT_OK, intent);
                    finish();
                }

            }
        });


        area3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(area3.getText().equals("강원도")) {
                    linear7.setVisibility(View.VISIBLE);

                    area1.setText("강원도 전체");
                    area2.setText("강릉시");
                    area3.setText("고성군");
                    area4.setText("동해시");
                    area5.setText("삼척시");
                    area6.setText("속초시");
                    area7.setText("양구군");
                    area8.setText("양양군");
                    area9.setText("영월군");
                    area10.setText("원주시");
                    area11.setText("인제군");
                    area12.setText("정선군");
                    area13.setText("철원군");
                    area14.setText("춘천시");
                    area15.setText("태백시");
                    area16.setText("평창군");
                    area17.setText("홍천군");
                    area18.setText("화천군");
                    area19.setText("횡성군");
                    area20.setText("");
                    area21.setText("");
                    areaarea.setText("강원도");
                } else {
                    String areaName = area3.getText().toString();
                    String areaName2 = areaarea.getText().toString();
                    String areaName3 = areaName2+" "+areaName;
                    Intent intent = new Intent(getApplicationContext(), Adopt2Activity.class);
                    intent.putExtra("area", areaName3);
                    setResult(RESULT_OK, intent);
                    finish();
                }

            }
        });

        area4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(area4.getText().equals("인천시")) {
                    linear5.setVisibility(View.GONE);
                    linear6.setVisibility(View.GONE);

                    area1.setText("인천시 전체");
                    area2.setText("강화군");
                    area3.setText("계양구");
                    area4.setText("남구");
                    area5.setText("남동구");
                    area6.setText("동구");
                    area7.setText("부평구");
                    area8.setText("서구");
                    area9.setText("연수구");
                    area10.setText("웅진군");
                    area11.setText("중구");
                    area12.setText("");
                    areaarea.setText("인천시");
                } else {
                    String areaName = area4.getText().toString();
                    String areaName2 = areaarea.getText().toString();
                    String areaName3 = areaName2+" "+areaName;
                    Intent intent = new Intent(getApplicationContext(), Adopt2Activity.class);
                    intent.putExtra("area", areaName3);
                    setResult(RESULT_OK, intent);
                    finish();
                }

            }
        });


        area5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(area5.getText().equals("충청북도")) {
                    linear6.setVisibility(View.GONE);
                    area1.setText("충청북도 전체");
                    area2.setText("괴산군");
                    area3.setText("단양군");
                    area4.setText("보은군");
                    area5.setText("영동군");
                    area6.setText("옥천군");
                    area7.setText("음성군");
                    area8.setText("제천시");
                    area9.setText("증평군");
                    area10.setText("진천군");
                    area11.setText("청원군");
                    area12.setText("청주시");
                    area13.setText("충주시");
                    area14.setText("");
                    area15.setText("");
                    areaarea.setText("충청북도");
                } else {
                    String areaName = area5.getText().toString();
                    String areaName2 = areaarea.getText().toString();
                    String areaName3 = areaName2+" "+areaName;
                    Intent intent = new Intent(getApplicationContext(), Adopt2Activity.class);
                    intent.putExtra("area", areaName3);
                    setResult(RESULT_OK, intent);
                    finish();
                }

            }
        });

        area6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(area6.getText().equals("충청남도")) {

                    area1.setText("충청남도 전체");
                    area2.setText("계룡시");
                    area3.setText("공주시");
                    area4.setText("금산군");
                    area5.setText("논산시");
                    area6.setText("당진시");
                    area7.setText("보령시");
                    area8.setText("부여군");
                    area9.setText("서산시");
                    area10.setText("서천군");
                    area11.setText("마산시");
                    area12.setText("연기군");
                    area13.setText("예산군");
                    area14.setText("천안시");
                    area15.setText("청양군");
                    area16.setText("태안군");
                    area17.setText("홍성군");
                    area18.setText("");
                    areaarea.setText("충청남도");
                } else {
                    String areaName = area6.getText().toString();
                    String areaName2 = areaarea.getText().toString();
                    String areaName3 = areaName2+" "+areaName;
                    Intent intent = new Intent(getApplicationContext(), Adopt2Activity.class);
                    intent.putExtra("area", areaName3);
                    setResult(RESULT_OK, intent);
                    finish();
                }

            }
        });



        area7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(area7.getText().equals("세종시")) {
                    linear2.setVisibility(View.GONE);
                    linear3.setVisibility(View.GONE);
                    linear4.setVisibility(View.GONE);
                    linear5.setVisibility(View.GONE);
                    linear6.setVisibility(View.GONE);

                    area1.setText("세종시 전체");
                    area2.setText("");
                    area3.setText("");

                    areaarea.setText("");
                } else {
                    String areaName = area7.getText().toString();
                    String areaName2 = areaarea.getText().toString();
                    String areaName3 = areaName2+" "+areaName;
                    Intent intent = new Intent(getApplicationContext(), Adopt2Activity.class);
                    intent.putExtra("area", areaName3);
                    setResult(RESULT_OK, intent);
                    finish();
                }

            }
        });


        area8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(area8.getText().equals("대전시")) {

                    linear3.setVisibility(View.GONE);
                    linear4.setVisibility(View.GONE);
                    linear5.setVisibility(View.GONE);
                    linear6.setVisibility(View.GONE);

                    area1.setText("대전시 전체");
                    area2.setText("대덕구");
                    area3.setText("동구");
                    area4.setText("서구");
                    area5.setText("유성구");
                    area6.setText("중구");
                    areaarea.setText("대전시");
                } else {
                    String areaName = area8.getText().toString();
                    String areaName2 = areaarea.getText().toString();
                    String areaName3 = areaName2+" "+areaName;
                    Intent intent = new Intent(getApplicationContext(), Adopt2Activity.class);
                    intent.putExtra("area", areaName3);
                    setResult(RESULT_OK, intent);
                    finish();
                }

            }
        });


        area9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(area9.getText().equals("경상북도")) {

                    linear7.setVisibility(View.VISIBLE);
                    linear8.setVisibility(View.VISIBLE);

                    area1.setText("경상북도 전체");
                    area2.setText("경산시");
                    area3.setText("경주시");
                    area4.setText("고령군");
                    area5.setText("구미시");
                    area6.setText("군위군");
                    area7.setText("김천시");
                    area8.setText("문경시");
                    area9.setText("봉화군");
                    area10.setText("상주시");
                    area11.setText("성주군");
                    area12.setText("안동시");
                    area13.setText("영덕군");
                    area14.setText("영양군");
                    area15.setText("영주시");
                    area16.setText("영천시");
                    area17.setText("예천군");
                    area18.setText("울릉군");
                    area19.setText("울진군");
                    area20.setText("의성군");
                    area21.setText("청도군");
                    area22.setText("청송군");
                    area23.setText("칠곡군");
                    area24.setText("포항시");
                    areaarea.setText("경상북도");
                } else {
                    String areaName = area9.getText().toString();
                    String areaName2 = areaarea.getText().toString();
                    String areaName3 = areaName2+" "+areaName;
                    Intent intent = new Intent(getApplicationContext(), Adopt2Activity.class);
                    intent.putExtra("area", areaName3);
                    setResult(RESULT_OK, intent);
                    finish();
                }

            }
        });


        area10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(area10.getText().equals("대구시")) {

                    linear4.setVisibility(View.GONE);
                    linear5.setVisibility(View.GONE);
                    linear6.setVisibility(View.GONE);

                    area1.setText("대구시 전체");
                    area2.setText("남구");
                    area3.setText("달서구");
                    area4.setText("달성군");
                    area5.setText("동구");
                    area6.setText("북구");
                    area7.setText("서구");
                    area8.setText("수성구");
                    area9.setText("중구");
                    areaarea.setText("대구시");
                } else {
                    String areaName = area10.getText().toString();
                    String areaName2 = areaarea.getText().toString();
                    String areaName3 = areaName2+" "+areaName;
                    Intent intent = new Intent(getApplicationContext(), Adopt2Activity.class);
                    intent.putExtra("area", areaName3);
                    setResult(RESULT_OK, intent);
                    finish();
                }

            }
        });


        area11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(area11.getText().equals("울산시")) {

                    linear3.setVisibility(View.GONE);
                    linear4.setVisibility(View.GONE);
                    linear5.setVisibility(View.GONE);
                    linear6.setVisibility(View.GONE);

                    area1.setText("울산시 전체");
                    area2.setText("남구");
                    area3.setText("동구");
                    area4.setText("북구");
                    area5.setText("울주군");
                    area6.setText("중구");

                    areaarea.setText("울산시");
                } else {
                    String areaName = area11.getText().toString();
                    String areaName2 = areaarea.getText().toString();
                    String areaName3 = areaName2+" "+areaName;
                    Intent intent = new Intent(getApplicationContext(), Adopt2Activity.class);
                    intent.putExtra("area", areaName3);
                    setResult(RESULT_OK, intent);
                    finish();
                }

            }
        });

        area12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(area12.getText().equals("부산시")) {

                    area1.setText("부산시 전체");
                    area2.setText("강서구");
                    area3.setText("금정구");
                    area4.setText("기장군");
                    area5.setText("남구");
                    area6.setText("동구");
                    area7.setText("동래구");
                    area8.setText("부산진구");
                    area9.setText("북구");
                    area10.setText("사상구");
                    area11.setText("사하구");
                    area12.setText("서구");
                    area13.setText("수영구");
                    area14.setText("연제구");
                    area15.setText("영도구");
                    area16.setText("중구");
                    area17.setText("해운대구");
                    area18.setText("");
                    areaarea.setText("부산시");
                } else {
                    String areaName = area12.getText().toString();
                    String areaName2 = areaarea.getText().toString();
                    String areaName3 = areaName2+" "+areaName;
                    Intent intent = new Intent(getApplicationContext(), Adopt2Activity.class);
                    intent.putExtra("area", areaName3);
                    setResult(RESULT_OK, intent);
                    finish();
                }

            }
        });


        area13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(area13.getText().equals("경상남도")) {

                    linear7.setVisibility(View.VISIBLE);

                    area1.setText("경상남도 전체");
                    area2.setText("거제시");
                    area3.setText("거창군");
                    area4.setText("고성군");
                    area5.setText("김해시");
                    area6.setText("남해군");
                    area7.setText("밀양시");
                    area8.setText("사천시");
                    area9.setText("산청군");
                    area10.setText("양산시");
                    area11.setText("의령군");
                    area12.setText("진주시");
                    area13.setText("창녕군");
                    area14.setText("창원시");
                    area15.setText("통영시");
                    area16.setText("하동군");
                    area17.setText("함안군");
                    area18.setText("함양군");
                    area19.setText("합천군");
                    area20.setText("");
                    area21.setText("");

                    areaarea.setText("경상남도");
                } else {
                    String areaName = area13.getText().toString();
                    String areaName2 = areaarea.getText().toString();
                    String areaName3 = areaName2+" "+areaName;
                    Intent intent = new Intent(getApplicationContext(), Adopt2Activity.class);
                    intent.putExtra("area", areaName3);
                    setResult(RESULT_OK, intent);
                    finish();
                }

            }
        });

        area14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(area14.getText().equals("전라북도")) {

                    linear6.setVisibility(View.GONE);

                    area1.setText("전라북도 전체");
                    area2.setText("고창군");
                    area3.setText("군산시");
                    area4.setText("김제시");
                    area5.setText("남원시");
                    area6.setText("무주군");
                    area7.setText("부안군");
                    area8.setText("순창군");
                    area9.setText("완주군");
                    area10.setText("익산시");
                    area11.setText("임실군");
                    area12.setText("장수군");
                    area13.setText("전주시");
                    area14.setText("정읍시");
                    area15.setText("진안군");

                    areaarea.setText("전라북도");
                } else {
                    String areaName = area14.getText().toString();
                    String areaName2 = areaarea.getText().toString();
                    String areaName3 = areaName2+" "+areaName;
                    Intent intent = new Intent(getApplicationContext(), Adopt2Activity.class);
                    intent.putExtra("area", areaName3);
                    setResult(RESULT_OK, intent);
                    finish();
                }

            }
        });


        area15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(area15.getText().equals("전라남도")) {

                    linear7.setVisibility(View.VISIBLE);
                    linear8.setVisibility(View.VISIBLE);

                    area1.setText("전라남도 전체");
                    area2.setText("강진군");
                    area3.setText("고흥군");
                    area4.setText("곡성군");
                    area5.setText("광양시");
                    area6.setText("구례군");
                    area7.setText("나주시");
                    area8.setText("담양군");
                    area9.setText("목포시");
                    area10.setText("무안군");
                    area11.setText("보성군");
                    area12.setText("순천시");
                    area13.setText("신안군");
                    area14.setText("여수시");
                    area15.setText("영광군");
                    area16.setText("영암군");
                    area17.setText("완도군");
                    area18.setText("장성군");
                    area19.setText("장흥군");
                    area20.setText("진도군");
                    area21.setText("함평군");
                    area22.setText("해남군");
                    area23.setText("화순군");
                    area24.setText("");
                    areaarea.setText("전라남도");
                } else {
                    String areaName = area15.getText().toString();
                    String areaName2 = areaarea.getText().toString();
                    String areaName3 = areaName2+" "+areaName;
                    Intent intent = new Intent(getApplicationContext(), Adopt2Activity.class);
                    intent.putExtra("area", areaName3);
                    setResult(RESULT_OK, intent);
                    finish();
                }

            }
        });

        area16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(area16.getText().equals("광주시")) {

                    linear3.setVisibility(View.GONE);
                    linear4.setVisibility(View.GONE);
                    linear5.setVisibility(View.GONE);
                    linear6.setVisibility(View.GONE);

                    area1.setText("광주시 전체");
                    area2.setText("광산구");
                    area3.setText("남구");
                    area4.setText("동구");
                    area5.setText("북구");
                    area6.setText("서구");
                    areaarea.setText("광주시");
                } else {
                    String areaName = area16.getText().toString();
                    String areaName2 = areaarea.getText().toString();
                    String areaName3 = areaName2+" "+areaName;
                    Intent intent = new Intent(getApplicationContext(), Adopt2Activity.class);
                    intent.putExtra("area", areaName3);
                    setResult(RESULT_OK, intent);
                    finish();
                }

            }
        });

        area17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(area17.getText().equals("제주시")) {

                    linear2.setVisibility(View.GONE);
                    linear3.setVisibility(View.GONE);
                    linear4.setVisibility(View.GONE);
                    linear5.setVisibility(View.GONE);
                    linear6.setVisibility(View.GONE);

                    area1.setText("제주시 전체");
                    area2.setText("서귀포시");
                    area3.setText("제주시");

                    areaarea.setText("제주시");
                } else {
                    String areaName = area17.getText().toString();
                    String areaName2 = areaarea.getText().toString();
                    String areaName3 = areaName2+" "+areaName;
                    Intent intent = new Intent(getApplicationContext(), Adopt2Activity.class);
                    intent.putExtra("area", areaName3);
                    setResult(RESULT_OK, intent);
                    finish();
                }

            }
        });


        area18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(area18.getText().equals("")) {
                } else {
                    String areaName = area18.getText().toString();
                    String areaName2 = areaarea.getText().toString();
                    String areaName3 = areaName2+" "+areaName;
                    Intent intent = new Intent(getApplicationContext(), Adopt2Activity.class);
                    intent.putExtra("area", areaName3);
                    setResult(RESULT_OK, intent);
                    finish();
                }

            }
        });

        area19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(area19.getText().equals("")) {
                } else {
                    String areaName = area19.getText().toString();
                    String areaName2 = areaarea.getText().toString();
                    String areaName3 = areaName2+" "+areaName;
                    Intent intent = new Intent(getApplicationContext(), Adopt2Activity.class);
                    intent.putExtra("area", areaName3);
                    setResult(RESULT_OK, intent);
                    finish();
                }

            }
        });

        area20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(area20.getText().equals("")) {
                } else {
                    String areaName = area20.getText().toString();
                    String areaName2 = areaarea.getText().toString();
                    String areaName3 = areaName2+" "+areaName;
                    Intent intent = new Intent(getApplicationContext(), Adopt2Activity.class);
                    intent.putExtra("area", areaName3);
                    setResult(RESULT_OK, intent);
                    finish();
                }

            }
        });

        area21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(area21.getText().equals("")) {
                } else {
                    String areaName = area21.getText().toString();
                    String areaName2 = areaarea.getText().toString();
                    String areaName3 = areaName2+" "+areaName;
                    Intent intent = new Intent(getApplicationContext(), Adopt2Activity.class);
                    intent.putExtra("area", areaName3);
                    setResult(RESULT_OK, intent);
                    finish();
                }

            }
        });

        area22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(area22.getText().equals("")) {
                } else {
                    String areaName = area22.getText().toString();
                    String areaName2 = areaarea.getText().toString();
                    String areaName3 = areaName2+" "+areaName;
                    Intent intent = new Intent(getApplicationContext(), Adopt2Activity.class);
                    intent.putExtra("area", areaName3);
                    setResult(RESULT_OK, intent);
                    finish();
                }

            }
        });

        area23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(area23.getText().equals("")) {
                } else {
                    String areaName = area23.getText().toString();
                    String areaName2 = areaarea.getText().toString();
                    String areaName3 = areaName2+" "+areaName;
                    Intent intent = new Intent(getApplicationContext(), Adopt2Activity.class);
                    intent.putExtra("area", areaName3);
                    setResult(RESULT_OK, intent);
                    finish();
                }

            }
        });

        area24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(area24.getText().equals("")) {
                } else {
                    String areaName = area24.getText().toString();
                    String areaName2 = areaarea.getText().toString();
                    String areaName3 = areaName2+" "+areaName;
                    Intent intent = new Intent(getApplicationContext(), Adopt2Activity.class);
                    intent.putExtra("area", areaName3);
                    setResult(RESULT_OK, intent);
                    finish();
                }

            }
        });

        area25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(area25.getText().equals("")) {
                } else {
                    String areaName = area25.getText().toString();
                    String areaName2 = areaarea.getText().toString();
                    String areaName3 = areaName2+" "+areaName;
                    Intent intent = new Intent(getApplicationContext(), Adopt2Activity.class);
                    intent.putExtra("area", areaName3);
                    setResult(RESULT_OK, intent);
                    finish();
                }

            }
        });

        area26.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(area26.getText().equals("")) {
                } else {
                    String areaName = area26.getText().toString();
                    String areaName2 = areaarea.getText().toString();
                    String areaName3 = areaName2+" "+areaName;
                    Intent intent = new Intent(getApplicationContext(), Adopt2Activity.class);
                    intent.putExtra("area", areaName3);
                    setResult(RESULT_OK, intent);
                    finish();
                }

            }
        });

        area27.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(area27.getText().equals("")) {
                } else {
                    String areaName = area27.getText().toString();
                    String areaName2 = areaarea.getText().toString();
                    String areaName3 = areaName2+" "+areaName;
                    Intent intent = new Intent(getApplicationContext(), Adopt2Activity.class);
                    intent.putExtra("area", areaName3);
                    setResult(RESULT_OK, intent);
                    finish();
                }

            }
        });

        area28.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(area28.getText().equals("")) {
                } else {
                    String areaName = area28.getText().toString();
                    String areaName2 = areaarea.getText().toString();
                    String areaName3 = areaName2+" "+areaName;
                    Intent intent = new Intent(getApplicationContext(), Adopt2Activity.class);
                    intent.putExtra("area", areaName3);
                    setResult(RESULT_OK, intent);
                    finish();
                }

            }
        });

        area29.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(area29.getText().equals("")) {
                } else {
                    String areaName = area29.getText().toString();
                    String areaName2 = areaarea.getText().toString();
                    String areaName3 = areaName2+" "+areaName;
                    Intent intent = new Intent(getApplicationContext(), Adopt2Activity.class);
                    intent.putExtra("area", areaName3);
                    setResult(RESULT_OK, intent);
                    finish();
                }

            }
        });

        area30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(area30.getText().equals("")) {
                } else {
                    String areaName = area30.getText().toString();
                    String areaName2 = areaarea.getText().toString();
                    String areaName3 = areaName2+" "+areaName;
                    Intent intent = new Intent(getApplicationContext(), Adopt2Activity.class);
                    intent.putExtra("area", areaName3);
                    setResult(RESULT_OK, intent);
                    finish();
                }

            }
        });

        area31.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(area31.getText().equals("")) {
                } else {
                    String areaName = area31.getText().toString();
                    String areaName2 = areaarea.getText().toString();
                    String areaName3 = areaName2+" "+areaName;
                    Intent intent = new Intent(getApplicationContext(), Adopt2Activity.class);
                    intent.putExtra("area", areaName3);
                    setResult(RESULT_OK, intent);
                    finish();
                }

            }
        });

        area32.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(area32.getText().equals("")) {
                } else {
                    String areaName = area32.getText().toString();
                    String areaName2 = areaarea.getText().toString();
                    String areaName3 = areaName2+" "+areaName;
                    Intent intent = new Intent(getApplicationContext(), Adopt2Activity.class);
                    intent.putExtra("area", areaName3);
                    setResult(RESULT_OK, intent);
                    finish();
                }

            }
        });

    }   // onCreate 끝

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

}
