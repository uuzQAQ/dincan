package com.edu.feicui.dincan.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.edu.feicui.dincan.R;
import com.edu.feicui.dincan.entity.MessageEvent;
import com.edu.feicui.dincan.utils.LoadImage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by user on 2016/12/6.
 */

public class DishTypeFragment extends Fragment {

    private static final String url = "http://10.0.2.2:8080/GourmetOrderServer/";

    @BindView(R.id.iv_first)
    ImageView ivFirst;
    @BindView(R.id.iv_dish_image)
    ImageView ivDish;
    @BindView(R.id.tv_dish_name)
    TextView tvName;
    @BindView(R.id.tv_dish_pirce)
    TextView tvPirce;
    @BindView(R.id.tv_dish_introduction)
    TextView tvIntroduction;

    LoadImage loadImage;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dish_type_fragment,container,false);
        ButterKnife.bind(this,view);
        loadImage = new LoadImage(getActivity());
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showDish(MessageEvent event){
        ivFirst.setVisibility(View.GONE);
        loadImage.displayBitmap(url + event.getImagePath(),ivDish);
        tvName.setText(event.getDishName());
        tvIntroduction.setText(event.getDishMessage());
        tvPirce.setText(event.getDishPrice() + ".0");
    }
}
