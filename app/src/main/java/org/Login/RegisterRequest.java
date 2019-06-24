package org.Login;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {

    final static private String URL = "http://ec2-15-164-102-182.ap-northeast-2.compute.amazonaws.com/planb/join.php";
    private Map<String, String> parameters;

    public RegisterRequest(String joinId, String joinName, String joinPassword, String joinType, String joinImage, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("joinId", joinId);
        parameters.put("joinName", joinName);
        parameters.put("joinPassword", joinPassword);
        parameters.put("joinType", joinType);
        parameters.put("joinImage", joinImage);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
