package com.edu.feicui.dincan.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

import com.edu.feicui.dincan.R;
import com.edu.feicui.dincan.view.fancycoverflow.FancyCoverFlow;
import com.edu.feicui.dincan.view.fancycoverflow.FancyCoverFlowAdapter;
import com.edu.feicui.dincan.view.fancycoverflow.ImageAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by user on 2016/11/26.
 */

public class HomeActivity extends FragmentActivity {

    @BindView(R.id.fcf)
    FancyCoverFlow mFancyCoverFlow;
    @BindView(R.id.image1)
    ImageView mImageView1;
    @BindView(R.id.image2)
    ImageView mImageView2;
    @BindView(R.id.image3)
    ImageView mImageView3;
    @BindView(R.id.image4)
    ImageView mImageView4;
    @BindView(R.id.image5)
    ImageView mImageView5;

    private FancyCoverFlowAdapter adapter;
    private int curPosition = 0;
    private ImageView[] mImageViews;
    private Handler handler = new Handler(){
        public void handleMessage(android.os.Message msg) {

            mFancyCoverFlow.setSelection((curPosition++) % 5);
            Message message = handler.obtainMessage();
            handler.sendMessageDelayed(message, 2000);

        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        ButterKnife.bind(this);
        mImageViews = new ImageView[]{mImageView1, mImageView2, mImageView3, mImageView4, mImageView5};
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        adapter = new ImageAdapter(this);

        // item之间的间隙可以近似认为是imageview的宽度与缩放比例的乘积的一半
        mFancyCoverFlow.setScaleX(1.2f);
        mFancyCoverFlow.setScaleY(1.2f);
        mFancyCoverFlow.setSpacing(-72);
        mFancyCoverFlow.setAdapter(adapter);
        mFancyCoverFlow.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                curPosition = position;
                for(int i = 0;i < mImageViews.length;i++){
                    mImageViews[i].setImageResource(R.drawable.null_point);
                }
                mImageViews[position].setImageResource(R.drawable.point);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // 点击事件
        mFancyCoverFlow.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

            }
        });


        Message msg = handler.obtainMessage();
        handler.sendMessageDelayed(msg, 2000);
    }

    @OnClick(R.id.btn_choosedish) public void choosedish(){
        startActivity(new Intent(HomeActivity.this,NewChooseDishActivity.class));

    }
    @OnClick(R.id.btn_checkout) public void checkout(){
        startActivity(new Intent(HomeActivity.this,OrderActivity.class));
    }
    @OnClick(R.id.btn_order_schedule) public void orderSchedule(){
        startActivity(new Intent(HomeActivity.this,QueryEvolverActivity.class));
    }
    @OnClick(R.id.btn_add) public void add(){
        Toast.makeText(this, "该功能未开放", Toast.LENGTH_SHORT).show();
    }
    @OnClick(R.id.btn_more) public void more(){
        startActivity(new Intent(HomeActivity.this,MoreActivity.class));
    }

}
