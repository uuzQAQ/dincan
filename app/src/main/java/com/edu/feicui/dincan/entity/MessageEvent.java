package com.edu.feicui.dincan.entity;

import java.util.List;

/**
 * Created by user on 2016/12/10.
 */

public class MessageEvent {

    public static final int TYPE_DISH_FRAGMENT = 1;

    private int type;
    private String fragmentFullName;
    private String imagePath;
    private String dishName;
    private String dishPrice;
    private String dishMessage;
    private List<DishMenu> dishlist;

    public List<DishMenu> getDishlist() {
        return dishlist;
    }

    public void setDishlist(List<DishMenu> dishlist) {
        this.dishlist = dishlist;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getFragmentFullName() {
        return fragmentFullName;
    }

    public void setFragmentFullName(String fragmentFullName) {
        this.fragmentFullName = fragmentFullName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public String getDishPrice() {
        return dishPrice;
    }

    public void setDishPrice(String dishPrice) {
        this.dishPrice = dishPrice;
    }

    public String getDishMessage() {
        return dishMessage;
    }

    public void setDishMessage(String dishMessage) {
        this.dishMessage = dishMessage;
    }
}
