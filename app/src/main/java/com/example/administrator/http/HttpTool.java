package com.example.administrator.http;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

/**
 * Created by kira on 2017/2/27.
 */
public class HttpTool {

    public static void Gethttp(Context context, String url, Response.Listener<String> rl) {
        RequestQueue mQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(url, rl, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }) {
            @Override
            protected Response<String> parseNetworkResponse(
                    NetworkResponse response) {
                // TODO Auto-generated method stub
                String str = null;
                try {
                    str = new String(response.data, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return Response.success(str,
                        HttpHeaderParser.parseCacheHeaders(response));
            }

        };
        mQueue.add(stringRequest);

    }


    /**
     * 不带进度条post
     *
     * @param context
     * @param Url
     * @param map
     * @param rs
     */
    public static void Posthttp(Context context, String Url, final HashMap<String, String> map, Response.Listener<String> rs) {
        RequestQueue mQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url, rs, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }

        }) {
            @Override
            protected HashMap<String, String> getParams() throws AuthFailureError {

                return map;
            }

            @Override
            protected Response<String> parseNetworkResponse(
                    NetworkResponse response) {
                // TODO Auto-generated method stub
                String str = null;
                try {
                    str = new String(response.data, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return Response.success(str,
                        HttpHeaderParser.parseCacheHeaders(response));
            }

        };

        mQueue.add(stringRequest);
    }

}
