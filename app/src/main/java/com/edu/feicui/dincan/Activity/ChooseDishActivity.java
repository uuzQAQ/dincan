package com.edu.feicui.dincan.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.PopupWindow;
import android.widget.Switch;
import android.widget.TextView;

import com.edu.feicui.dincan.R;
import com.edu.feicui.dincan.adapter.DishTypeAdapter;
import com.edu.feicui.dincan.db.DianCanDBManager;
import com.edu.feicui.dincan.entity.DishMenu;
import com.edu.feicui.dincan.entity.MessageEvent;
import com.edu.feicui.dincan.fragment.DishTypeFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by user on 2016/12/5.
 */

public class ChooseDishActivity extends FragmentActivity {

    @BindView(R.id.elv)
    ExpandableListView expandableListView;
    @BindView(R.id.btn_table_number)
    Button btnLeft;
    @BindView(R.id.btn_dish_type)
    Button btnRight;

    private PopupWindow popupWindowLeft;
    private PopupWindow popupWindowRight;
    private TextView[] textViewArrayLeft = new TextView[9];
    private TextView[] textViewArrayRight = new TextView[7];
    private FragmentManager fm;
    private DishTypeFragment dishTypeFragment;
    private List<DishMenu> list = new ArrayList<>();
    private DianCanDBManager dbManager;
    private DishTypeAdapter adapter;
    private String currentTableNum;
    private List<List<DishMenu>> endList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choosedish);
        ButterKnife.bind(this);
        initPwLeft();
        initPwRight();
        dbManager = new DianCanDBManager(this);
        fm = getSupportFragmentManager();
        dishTypeFragment = new DishTypeFragment();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.fl_contain,dishTypeFragment,"dishTypeFragment");
        ft.commit();

        SharedPreferences sharedPreferences = this.getSharedPreferences("first", Context.MODE_PRIVATE);
        Boolean isFirstRun = sharedPreferences.getBoolean("isFirstRun", true);//第一次是true，之后是false
        if(isFirstRun){
            dbManager.initDishDB();
            saveSpFirst();
        }

        list = dbManager.getDishMenu();
        adapter = new DishTypeAdapter(getGroupList(),getChildList(),this);

        expandableListView.setGroupIndicator(null);
//        expandableListView.expandGroup(1);
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                String dishName = adapter.getChildList().get(i).get(i1).getDish_name();
                for(DishMenu dishMenus : list){
                    if(dishMenus.getDish_name().equals(dishName)){
                        MessageEvent event = new MessageEvent();
                        event.setDishName(dishMenus.getDish_name());
                        event.setDishMessage(dishMenus.getIntroduction());
                        event.setDishPrice(dishMenus.getPrice());
                        event.setImagePath(dishMenus.getImg_path());
                        EventBus.getDefault().post(event);//发布事件
                    }
                }
                dishName = null;
                return true;
            }
        });

        expandableListView.setAdapter(adapter);

    }

    public void initPwLeft(){
        View view = getLayoutInflater().inflate(R.layout.pw_left,null);

        textViewArrayLeft[0] = (TextView) view.findViewById(R.id.tv_101);
        textViewArrayLeft[1] = (TextView) view.findViewById(R.id.tv_102);
        textViewArrayLeft[2] = (TextView) view.findViewById(R.id.tv_103);
        textViewArrayLeft[3] = (TextView) view.findViewById(R.id.tv_104);
        textViewArrayLeft[4] = (TextView) view.findViewById(R.id.tv_201);
        textViewArrayLeft[5] = (TextView) view.findViewById(R.id.tv_202);
        textViewArrayLeft[6] = (TextView) view.findViewById(R.id.tv_203);
        textViewArrayLeft[7] = (TextView) view.findViewById(R.id.tv_301);
        textViewArrayLeft[8] = (TextView) view.findViewById(R.id.tv_302);

        for(int i = 0;i < textViewArrayLeft.length;i++){
            textViewArrayLeft[i].setOnClickListener(listenerLeft);
        }

        popupWindowLeft = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT,true);
        popupWindowLeft.setBackgroundDrawable(new BitmapDrawable());//点击外面不消失和返回键无效时，设置有Drawable可以解决
    }
    public void initPwRight(){
        View view = getLayoutInflater().inflate(R.layout.pw_right,null);

        textViewArrayRight[0] = (TextView) view.findViewById(R.id.tv_all);
        textViewArrayRight[1] = (TextView) view.findViewById(R.id.tv_recai);
        textViewArrayRight[2] = (TextView) view.findViewById(R.id.tv_liangcai);
        textViewArrayRight[3] = (TextView) view.findViewById(R.id.tv_tanglei);
        textViewArrayRight[4] = (TextView) view.findViewById(R.id.tv_yinliao);
        textViewArrayRight[5] = (TextView) view.findViewById(R.id.tv_zhushi);
        textViewArrayRight[6] = (TextView) view.findViewById(R.id.tv_other);

        for(int i = 0;i < textViewArrayRight.length;i++){
            textViewArrayRight[i].setOnClickListener(listenerRight);
        }

        popupWindowRight = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT,true);
        popupWindowRight.setBackgroundDrawable(new BitmapDrawable());//点击外面不消失和返回键无效时，设置有Drawable可以解决


    }


    @OnClick(R.id.btn_table_number) public void showMenuLeft(){
        if(popupWindowLeft != null && popupWindowLeft.isShowing()){
            popupWindowLeft.dismiss();
        }else if(popupWindowLeft != null){
            popupWindowLeft.showAsDropDown(btnLeft,0,0);
        }
    }

    View.OnClickListener listenerLeft = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            for(int i = 0;i < textViewArrayLeft.length;i++){
                if(textViewArrayLeft[i].getId() == view.getId()){
                    btnLeft.setText(textViewArrayLeft[i].getText().toString());
                    currentTableNum = btnLeft.getText().toString();
                }
            }
            popupWindowLeft.dismiss();
        }
    };

    @OnClick(R.id.btn_dish_type) public void showMenuRight(){
        if(popupWindowRight != null && popupWindowRight.isShowing()){
            popupWindowRight.dismiss();
        }else if(popupWindowRight != null){
            popupWindowRight.showAsDropDown(btnRight,0,0);
        }
    }

    View.OnClickListener listenerRight = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            List<String> list = new ArrayList<>();
            List<List<DishMenu>> child = new ArrayList<>();
            for(int i = 0;i < textViewArrayRight.length;i++){
                if(textViewArrayRight[i].getId() == view.getId()){
                    btnRight.setText(textViewArrayRight[i].getText().toString());
                    if(i == 0){
                        list.addAll(getGroupList());
                        child.addAll(getChildList());
                    }else{
                        list.add(getGroupList().get(i - 1));
                        child.add(getChildList().get(i - 1));
                    }
                }
            }
            popupWindowRight.dismiss();
            adapter.appendDataToAdapter(list,child);
            list.clear();
            child.clear();
        }
    };

   public List<String> getGroupList(){
       List<String> group = new ArrayList<>();
       group.add("热菜");
       group.add("凉菜");
       group.add("汤类");
       group.add("饮料");
       group.add("主食");
       group.add("其他");
       return group;
   }
    public List<List<DishMenu>> getChildList(){
        List<List<DishMenu>> child = new ArrayList<>();

        for(int i = 0;i < 6;i++){
            List<DishMenu> item = new ArrayList<>();
            child.add(item);
        }

        for(DishMenu dishMenus : list){
            switch(dishMenus.getDish_class()){
                case "热菜":
                    child.get(0).add(dishMenus);
                    break;
                case "凉菜":
                    child.get(1).add(dishMenus);
                    break;
                case "汤类":
                    child.get(2).add(dishMenus);
                    break;
                case "饮料":
                    child.get(3).add(dishMenus);
                    break;
                case "主食":
                    child.get(4).add(dishMenus);
                    break;
                case "其他":
                    child.get(5).add(dishMenus);
                    break;
            }
        }

        return child;
    }



    private void saveSpFirst(){
        SharedPreferences sharedPreferences = this.getSharedPreferences("first", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isFirstRun", false);
        editor.commit();//提交
    }

    @OnClick(R.id.btn_choosedish) public void choosedish(){
        endList = adapter.getChildList();
        //currentTableNum,endList 的信息传到 下单界面 传到activity 或者 储存
    }

}
