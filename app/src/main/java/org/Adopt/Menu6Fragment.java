package org.Adopt;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import org.Adopt.Adopt3Request;
import org.R;

import org.json.JSONObject;


public class Menu6Fragment extends Fragment {

    TextView dogName;
    TextView dogAge;
    TextView dogWeight;
    TextView dogGender;
    TextView dogType;
    TextView dogChara;

    SharedPreferences pref;
    String adopt_id;

    public Menu6Fragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_menu6, container, false);

        dogName = rootView.findViewById(R.id.dogName);
        dogAge = rootView.findViewById(R.id.dogAge);
        dogWeight = rootView.findViewById(R.id.dogWeight);
        dogGender = rootView.findViewById(R.id.dogGender);
        dogType = rootView.findViewById(R.id.dogType);
        dogChara = rootView.findViewById(R.id.dogChara);


        pref = getActivity().getSharedPreferences("pref", Activity.MODE_PRIVATE);
        adopt_id = pref.getString("adoptView", "");



        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    String dogName2 = jsonResponse.getString("name");
                    String dogAgeYear = jsonResponse.getString("ageyear");
                    String dogAgeMonth = jsonResponse.getString("agemonth");
                    String dogWeight2 = jsonResponse.getString("weight");
                    String dogGender2 = jsonResponse.getString("gender");
                    String dogType2 = jsonResponse.getString("type");
                    String dogChara2 = jsonResponse.getString("chara");
                    String dogAge2;
                    if(dogAgeYear.equals("0")) {
                        dogAge2 =dogAgeMonth + " 개월";
                    } else {
                        dogAge2 = dogAgeYear + " 살  " + dogAgeMonth + " 개월";
                    }
                    dogName.setText(dogName2);
                    dogAge.setText(dogAge2);
                    dogWeight.setText(dogWeight2+ " kg");
                    dogGender.setText(dogGender2);
                    dogType.setText(dogType2);
                    dogChara.setText(dogChara2);


                } catch (Exception e) {
                    e.printStackTrace();

                }


            }
        };

        Adopt3Request adopt3Request = new Adopt3Request(adopt_id, responseListener);
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(adopt3Request);

        return rootView;
    }

}
