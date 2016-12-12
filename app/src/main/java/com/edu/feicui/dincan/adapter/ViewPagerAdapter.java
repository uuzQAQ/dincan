package com.edu.feicui.dincan.adapter;





import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by user on 2016/12/5.
 */

public class ViewPagerAdapter extends PagerAdapter {

    private List<View> list;

    public ViewPagerAdapter (Context context, List<View> list){
        this.list = list;
    }

    @Override
    public int getCount() {

        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {

        return arg0 == arg1;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = list.get(position % list.size());
        container.removeView(view);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = list.get(position % list.size());
        container.addView(view);
        return view;
    }

}
