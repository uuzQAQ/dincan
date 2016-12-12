package com.edu.feicui.dincan.utils;

import android.content.Context;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.edu.feicui.dincan.R;


/**
 * Created by user on 2016/11/28.
 */

public class VolleyUtils {
    private Context context;
    private static RequestQueue requestQueue;

    public VolleyUtils(Context context) {
        this.context = context;
        if(this.requestQueue == null){
            this.requestQueue = Volley.newRequestQueue(context);
        }
    }



    public void sendImageRequest(String url, ImageLoader.ImageCache imageCache, ImageView imageView){
        ImageLoader imageLoader = new ImageLoader(requestQueue,imageCache);
        ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(imageView, R.mipmap.ic_launcher,R.mipmap.dish_no_image);
        imageLoader.get(url,imageListener);
    }
}
