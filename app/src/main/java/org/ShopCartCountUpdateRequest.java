package org;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ShopCartCountUpdateRequest extends StringRequest {

    final static private String URL = "http://ec2-15-164-102-182.ap-northeast-2.compute.amazonaws.com/planb/shopcartcountupdate.php";
    private Map<String, String> parameters;

    public ShopCartCountUpdateRequest(String userId, String shopId, String count, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userId", userId);
        parameters.put("shopId", shopId);
        parameters.put("count", count);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
