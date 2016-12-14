package com.edu.feicui.dincan.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.LruCache;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Created by user on 2016/11/29.
 */

public class LoadImage {

    private Context context;
    private LruCache<String,Bitmap> cache = new LruCache<>(4 * 1024 * 1024);

    public LoadImage(Context context) {
        this.context = context;
    }

    public void displayBitmap(String url, ImageView imageView){//iv用于显示url的图片 把三个步骤结合起来
        if(TextUtils.isEmpty(url)){
            return ;
        }

        //先从内存缓冲中查看是否有该图片，有就使用，没有就看缓冲文件查找，有就显示，没有就网络下载
        Bitmap bitmap = cache.get(url);
        if(bitmap != null){
            imageView.setImageBitmap(bitmap);
            return ;
        }

        bitmap = getBitmapFromFileCache(url);
        if(bitmap != null){
            cache.put(url,bitmap);
            imageView.setImageBitmap(bitmap);
            return;
        }

        VolleyUtils volleyUtils = new VolleyUtils(context);
        volleyUtils.sendImageRequest(url,imageCache,imageView);

    }

    private ImageLoader.ImageCache imageCache = new ImageLoader.ImageCache() {
        @Override
        public Bitmap getBitmap(String s) {
            return cache.get(s);
        }

        @Override
        public void putBitmap(String s, Bitmap bitmap) {//将图片缓冲在文件和内存中  缓存在内存中用的是lrucache
            cache.put(s,bitmap);
            saveBitmapToFileCache(s,bitmap);
        }
    };

    private void saveBitmapToFileCache(String url,Bitmap bitmap){//保存到sd卡
        String filename = url.substring(url.lastIndexOf("/") + 1);
        File cacheDir  = context.getExternalCacheDir();
        if(cacheDir == null){//不存在context.getExternalCacheDir() = null
            return;
        }else if(!cacheDir.exists()){//不为空目录没有创建
            cacheDir.mkdir();
        }

        try {
            OutputStream os = new FileOutputStream(new File(cacheDir,filename));
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,os); // 1.图片类型2.图片质量3.输出流
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Bitmap getBitmapFromFileCache(String url){//1.内存2.缓存文件3.网络     这里是从文件读取
        String filename = url.substring(url.lastIndexOf("/") + 1);
        //获取缓冲文件目录
        File cacheDir = context.getExternalCacheDir();
        if(cacheDir != null && cacheDir.exists()){
            File bitmapFile = new File(cacheDir,filename);
            if(bitmapFile != null && bitmapFile.exists()){
               return BitmapFactory.decodeFile(bitmapFile.getAbsolutePath());
            }
        }
        return null;
    }
}
