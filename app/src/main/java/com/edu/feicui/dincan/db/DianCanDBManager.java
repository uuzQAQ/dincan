package com.edu.feicui.dincan.db;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.edu.feicui.dincan.entity.DishMenu;
import com.edu.feicui.dincan.entity.Order;
import com.edu.feicui.dincan.entity.TempOrder;

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
            int dish_id = cursor.getInt(cursor.getColumnIndex("dish_id"));
            String dish_name = cursor.getString(cursor.getColumnIndex("dish_name"));
            String price = cursor.getString(cursor.getColumnIndex("price"));
            String introduction = cursor.getString(cursor.getColumnIndex("introduction"));
            String dish_class = cursor.getString(cursor.getColumnIndex("dish_class"));
            String img_path = cursor.getString(cursor.getColumnIndex("img_path"));
            DishMenu dishMenu = new DishMenu(dish_id,dish_name,price,introduction,dish_class,img_path);
            list.add(dishMenu);
        }
        cursor.close();
        cursor = null;
        return list;
    }

    public void saveOrder(Order order){
        SQLiteDatabase database = helper.getWritableDatabase();
        Cursor cursor = database.rawQuery("select * from orders where order_id = ?",new String[]{String.valueOf(order.getSerialNum())});
        if(cursor.moveToFirst()){
            return;
        }

        ContentValues values = new ContentValues();
        values.put("order_id",order.getSerialNum());
        values.put("table_num",order.getTableNum());
        if(order.ischeck()){
            values.put("checkout",1);
        }else{
            values.put("checkout",0);
        }


        database.insert("orders",null,values);
        database.close();
    }

    public void saveTempOrder(TempOrder tempOrder){
        SQLiteDatabase database = helper.getWritableDatabase();
        Cursor cursor = database.rawQuery("select * from temp_orders where dish_id = ?",new String[]{String.valueOf(tempOrder.getDishId())});
        if(cursor.moveToFirst()){
            return;
        }
        cursor.close();
        ContentValues values = new ContentValues();
        values.put("table_num",tempOrder.getTableNum());
        values.put("dish_id",tempOrder.getDishId());
        values.put("count",tempOrder.getCount());
        values.put("remark",tempOrder.getRemark());
        database.insert("temp_orders",null,values);
        database.close();
    }

    public List<Order> getOrder(){
        SQLiteDatabase database = helper.getWritableDatabase();
        Cursor cursor = database.rawQuery("select * from orders",null);
        List<Order> list = new ArrayList<>();
        while(cursor.moveToNext()){
            String serialNum = cursor.getString(cursor.getColumnIndex("order_id"));
            String tableNum = cursor.getString(cursor.getColumnIndex("table_num"));
            boolean ischeck;
            if(cursor.getLong(cursor.getColumnIndex("checkout")) == 0){
                ischeck = false;
            }else{
                ischeck = true;
            }

            Order order = new Order(serialNum,tableNum,ischeck);
            list.add(order);
        }
        cursor.close();
        database.close();
        return list;
    }

    public List<TempOrder> getTempOrder(){
        SQLiteDatabase database = helper.getWritableDatabase();
        Cursor cursor = database.rawQuery("select * from temp_orders",null);
        List<TempOrder> list = new ArrayList<>();
        while(cursor.moveToNext()){
            String tableNum = cursor.getString(cursor.getColumnIndex("table_num"));
            String dishId = cursor.getString(cursor.getColumnIndex("dish_id"));
            int count = cursor.getInt(cursor.getColumnIndex("count"));
            String remark = cursor.getString(cursor.getColumnIndex("remark"));
            TempOrder tempOrder = new TempOrder(tableNum,dishId,count,remark);
            list.add(tempOrder);
        }
        cursor.close();
        database.close();
        return list;
    }

    public void deleteTempOrderItem(String tableNum,String dishId){
        SQLiteDatabase database = helper.getWritableDatabase();
        database.delete("temp_orders","table_num=? and dish_id=?",new String[]{tableNum,dishId});
        database.close();
    }

    public void updateTempOrderRemark(String tableNum,String dishId,String remark){
        SQLiteDatabase database = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("remark",remark);
        database.update("temp_orders",values,"table_num=? and dish_id=?",new String[]{tableNum,dishId});
        database.close();
    }

    public void updateTempOrderCount(String tableNum,String dishId,int count){
        SQLiteDatabase database = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("count",count);
        database.update("temp_orders",values,"table_num=? and dish_id=?",new String[]{tableNum,dishId});
        database.close();
    }

    public void clearTempAndOrder(){
        SQLiteDatabase database = helper.getWritableDatabase();
        database.delete("temp_orders",null,null);
        database.delete("orders",null,null);
        database.close();
    }

}
