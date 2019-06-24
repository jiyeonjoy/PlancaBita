package org.Adopt;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class AdoptImgUpdateRequest extends StringRequest {

    final static private String URL = "http://ec2-15-164-102-182.ap-northeast-2.compute.amazonaws.com/planb/adoptimgupdate.php";
    private Map<String, String> parameters;

    public AdoptImgUpdateRequest(String adopt_id, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("adopt_id", adopt_id);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
