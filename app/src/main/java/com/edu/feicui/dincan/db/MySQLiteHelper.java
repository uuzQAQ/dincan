package com.edu.feicui.dincan.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by user on 2016/12/9.
 */

public class MySQLiteHelper extends SQLiteOpenHelper {

    public MySQLiteHelper(Context context) {
        super(context, "dish.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table if not exists table_list(_id integer primary key autoIncrement,table_num text,table_name text,table_add text,table_size text,staff_num text)");
        sqLiteDatabase.execSQL("create table if not exists dish_list(_id integer primary key autoIncrement,dish_id text,dish_name text,price text,introduction text,dish_class text,dish_unit text,img_path text,load_position text default 0,img_download boolean default 0)");
        sqLiteDatabase.execSQL("create table if not exists temp_orders(_id integer primary key autoIncrement,table_num text,dish_id text,count text default 1,remark text default '')");//id,座号，菜id，数量，备注
        sqLiteDatabase.execSQL("create table if not exists orders(_id integer primary key autoIncrement,order_id text,table_num text,checkout boolean default 0)");//id,流水号，座号，是否选中
        sqLiteDatabase.execSQL("create table if not exists notemp_orders(_id integer primary key autoIncrement,table_num text,dish_id text,count text default 1,remark text default '')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
