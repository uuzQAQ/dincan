package com.edu.feicui.dincan.biz;

import android.content.Context;
import android.content.SharedPreferences;

import com.edu.feicui.dincan.entity.DishMenu;

/**
 * Created by user on 2016/12/12.
 */

public class SharePreferencesManager {

    public static void saveDish(Context context, DishMenu dishMenu,String tableNum){
        SharedPreferences sharedPreferences = context.getSharedPreferences("choose_dish",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("tableNum",tableNum);
        editor.putString("dishName",dishMenu.getDish_name());
        editor.putString("dishName",dishMenu.getPrice());
        editor.commit();
    }

    public static void clearDish(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("choose_dish",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }
}
