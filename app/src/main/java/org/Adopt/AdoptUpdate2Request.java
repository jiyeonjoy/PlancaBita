package org.Adopt;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class AdoptUpdate2Request extends StringRequest {

    final static private String URL = "http://ec2-15-164-102-182.ap-northeast-2.compute.amazonaws.com/planb/adopupdate2.php";
    private Map<String, String> parameters;

    public AdoptUpdate2Request(String adopt_id, String title, String contents, String name, String dogType, String character, String phone, String area, String price, String ageYear, String ageMonth, String weight, String gender, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("adopt_id", adopt_id);
        parameters.put("title", title);
        parameters.put("contents", contents);
        parameters.put("name", name);
        parameters.put("dogType", dogType);
        parameters.put("character", character);
        parameters.put("phone", phone);
        parameters.put("area", area);
        parameters.put("price", price);
        parameters.put("ageYear", ageYear);
        parameters.put("ageMonth", ageMonth);
        parameters.put("weight", weight);
        parameters.put("gender", gender);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
