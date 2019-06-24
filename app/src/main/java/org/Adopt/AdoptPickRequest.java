package org.Adopt;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class AdoptPickRequest extends StringRequest {

    final static private String URL = "http://ec2-15-164-102-182.ap-northeast-2.compute.amazonaws.com/planb/adoptpick.php";
    private Map<String, String> parameters;

    public AdoptPickRequest(String adopt_id, String userId, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("adopt_id", adopt_id);
        parameters.put("userId", userId);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
