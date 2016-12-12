package com.edu.feicui.dincan.db;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.edu.feicui.dincan.entity.DishMenu;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2016/11/28.
 */

public class DianCanDBManager {

    private Context context;
    private MySQLiteHelper helper;
    public static final String path = "db/dish_db.sql" ;
    public DianCanDBManager(Context context) {
        this.context = context;
        helper = new MySQLiteHelper(context);
    }

    public void initDishDB(){
        SQLiteDatabase database = helper.getWritableDatabase();
        AssetManager assetManager = context.getAssets();
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {

            isr = new InputStreamReader(assetManager.open(path),"GBK");
            br = new BufferedReader(isr);
            
            //// TODO: 2016/12/9
            String str = null;
            while ((str = br.readLine()) != null){
                if(str.length() > 1){
                    database.execSQL(str);
                    System.out.println(str);
                }
            }
            database.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(br != null){
                try{
                    br.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(isr != null){
                try {
                    isr.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }


    }


//    this.dish_name = dish_name;
//    this.price = price;
//    this.introduction = introduction;
//    this.dish_class = dish_class;
//    this.img_path = img_path;


    public List<DishMenu> getDishMenu(){
        SQLiteDatabase database = helper.getWritableDatabase();
        Cursor cursor = database.rawQuery("select * from dish_list",null);
        List<DishMenu> list = new ArrayList<>();
        while(cursor.moveToNext()){
            String dish_name = cursor.getString(cursor.getColumnIndex("dish_name"));
            String price = cursor.getString(cursor.getColumnIndex("price"));
            String introduction = cursor.getString(cursor.getColumnIndex("introduction"));
            String dish_class = cursor.getString(cursor.getColumnIndex("dish_class"));
            String img_path = cursor.getString(cursor.getColumnIndex("img_path"));
            DishMenu dishMenu = new DishMenu(dish_name,price,introduction,dish_class,img_path);
            list.add(dishMenu);
        }
        cursor.close();
        cursor = null;
        return list;
    }

}