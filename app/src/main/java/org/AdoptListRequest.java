package org;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.Utils.Common;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdoptListRequest extends StringRequest {

    final static private String URL = "http://ec2-15-164-102-182.ap-northeast-2.compute.amazonaws.com/planb/adoptlist.php";
    private Map<String, String> parameters;

    public AdoptListRequest(String pageNum, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("pageNum", pageNum);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }


}
