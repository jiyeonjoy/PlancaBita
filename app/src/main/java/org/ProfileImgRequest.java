package org;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ProfileImgRequest extends StringRequest {

    final static private String URL = "http://ec2-15-164-102-182.ap-northeast-2.compute.amazonaws.com/planb/adoptlist.php";
    private Map<String, String> parameters;

    public ProfileImgRequest(String pageNum, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("pageNum", pageNum);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
