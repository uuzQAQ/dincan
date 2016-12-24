package com.edu.feicui.dincan.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.edu.feicui.dincan.R;
import com.edu.feicui.dincan.biz.BroadCastManager;
import com.edu.feicui.dincan.entity.DishMenu;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by user on 2016/12/6.
 */

public class DishTypeAdapter extends BaseExpandableListAdapter {

    private List<String> groupList = new ArrayList<>();
    private List<List<DishMenu>> childList = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;

    private List<List<List<DishMenu>>> sevenList = new ArrayList<>();
    private int mode;

    private void initSevenList(){
        List<List<DishMenu>> list0 = new ArrayList<>();
        List<List<DishMenu>> list1 = new ArrayList<>();
        List<List<DishMenu>> list2 = new ArrayList<>();
        List<List<DishMenu>> list3 = new ArrayList<>();
        List<List<DishMenu>> list4 = new ArrayList<>();
        List<List<DishMenu>> list5 = new ArrayList<>();
        List<List<DishMenu>> list6 = new ArrayList<>();
        sevenList.add(list0);
        sevenList.add(list1);
        sevenList.add(list2);
        sevenList.add(list3);
        sevenList.add(list4);
        sevenList.add(list5);
        sevenList.add(list6);
    }

    public DishTypeAdapter(List<String> groupList, List<List<DishMenu>> childList, Context context) {
        this.groupList = groupList;
        this.childList = childList;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        initSevenList();
        this.sevenList.get(0).addAll(childList);
    }

    public List<List<DishMenu>> getChildList() {
        return childList;
    }
    public List<List<DishMenu>> getList(){
        return sevenList.get(0);
    }

    public void appendDataToAdapter(List<String> data, List<List<DishMenu>> data2,int mode){
        if(data == null || data.size() == 0){
            return;
        }

        if(data2 == null || data2.size() == 0){
            return;
        }

        groupList.clear();
        groupList.addAll(data);
        if(sevenList.get(mode).size() == 0 || sevenList.get(mode) == null){
            sevenList.get(mode).addAll(data2);
        }

        childList.clear();
        childList.addAll(sevenList.get(mode));
        this.mode = mode;
        this.notifyDataSetChanged();
    }



    @Override
    public int getGroupCount() {
        return groupList.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return childList.get(i).size();
    }

    @Override
    public Object getGroup(int i) {
        return groupList.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return childList.get(i).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        ViewHolderGroup holderGroup;
        if(view == null){
            view = inflater.inflate(R.layout.group_item,null);
            holderGroup = new ViewHolderGroup();
            ButterKnife.bind(holderGroup,view);
            view.setTag(holderGroup);
        }else{
            holderGroup = (ViewHolderGroup) view.getTag();
        }
        holderGroup.tvDishType.setText(groupList.get(i) + "");

        return view;
    }

    @Override
    public View getChildView(final int i, final int i1, boolean b, View view, ViewGroup viewGroup) {
        ViewHolderChild holderChild;
//        if(view == null){
//            view = inflater.inflate(R.layout.child_item,null);
//            holderChild = new ViewHolderChild();
//            ButterKnife.bind(holderChild,view);
//            view.setTag(holderChild);
//        }else{
//            holderChild = (ViewHolderChild) view.getTag();
//        }
        view = inflater.inflate(R.layout.child_item,null);
        holderChild = new ViewHolderChild();
        ButterKnife.bind(holderChild,view);
        List<DishMenu> list = childList.get(i);
        holderChild.tvChild.setText(list.get(i1).getDish_name() + "");
        holderChild.checkBox.setChecked(list.get(i1).isCheck());
        holderChild.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, final boolean b) {
                childList.get(i).get(i1).setCheck(b);
                if(mode > 0){
                    //0 然后 匹配
                    for(int k = 0;k < sevenList.get(0).size();k++){
                        for(int j = 0;j < sevenList.get(0).get(k).size();j++){
                            if(sevenList.get(0).get(k).get(j).getDish_name().equals(childList.get(i).get(i1).getDish_name())){
                                sevenList.get(0).get(k).get(j).setCheck(b);
                            }
                        }
                    }
                }
            }
        });
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    class ViewHolderGroup{
        @BindView(R.id.tv_dish_type_item)
        TextView tvDishType;
    }
    class ViewHolderChild{
        @BindView(R.id.tv_child_item)
        TextView tvChild;
        @BindView(R.id.cb_child_item)
        CheckBox checkBox;

    }


}
