package org;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ShopOrderYesRequest extends StringRequest {

    final static private String URL = "http://ec2-15-164-102-182.ap-northeast-2.compute.amazonaws.com/planb/shoporderyes.php";
    private Map<String, String> parameters;

    public ShopOrderYesRequest(String userId, String shopName, int shopCount, String shopPrice, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userId", userId);
        parameters.put("shopName", shopName);
        parameters.put("shopCount", shopCount+"");
        parameters.put("shopPrice", shopPrice+"원");
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
