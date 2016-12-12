package com.edu.feicui.dincan.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.edu.feicui.dincan.R;
import com.edu.feicui.dincan.adapter.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by user on 2016/11/26.
 */

public class HomeActivity extends FragmentActivity {

    @BindView(R.id.viewPager)
    ViewPager viewPager;



    private ViewPagerAdapter adapter;
    private List<View> list = new ArrayList<>();
    private int[] imageArray = {
            R.mipmap.gallery1,
            R.mipmap.gallery2,
            R.mipmap.gallery3,
            R.mipmap.gallery4,
            R.mipmap.gallery5,
            R.mipmap.gallery6
    };

    private Handler handler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            int currentPage = viewPager.getCurrentItem();
            viewPager.setCurrentItem(currentPage + 1);

            Message message = handler.obtainMessage();
            handler.sendMessageDelayed(message, 2000);
        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        ButterKnife.bind(this);
        initViewPager();
        viewPager.setCurrentItem(imageArray.length * 1000);

        Message msg = handler.obtainMessage();
        handler.sendMessageDelayed(msg, 2000);
    }

    @OnClick(R.id.btn_choosedish) public void choosedish(){
        startActivity(new Intent(HomeActivity.this,ChooseDishActivity.class));

    }
    @OnClick(R.id.btn_checkout) public void checkout(){

    }
    @OnClick(R.id.btn_order_schedule) public void orderSchedule(){

    }
    @OnClick(R.id.btn_add) public void add(){

    }
    @OnClick(R.id.btn_more) public void more(){

    }

    public void initViewPager(){
        for(int i = 0;i < imageArray.length;i++){
            ImageView imageView = (ImageView) getLayoutInflater().inflate(R.layout.image_item, null);
            imageView.setImageResource(imageArray[i]);
            list.add(imageView);
        }

        adapter = new ViewPagerAdapter(HomeActivity.this,list);
        viewPager.setAdapter(adapter);
    }
}
