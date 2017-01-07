package com.edu.feicui.dincan.entity;

/**
 * Created by user on 2016/12/9.
 */

public class DishMenu {

    //dish_id,dish_name,price,introduction,dish_class,img_download,img_path
    private int dish_id;
    private String dish_name;
    private String price;
    private String introduction;
    private String dish_class;
    private String img_download;
    private String img_path;
    private boolean isCheck;





    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public DishMenu() {
    }

    public DishMenu(int dish_id, String dish_name, String price, String introduction, String dish_class,String img_path) {
        this.dish_id = dish_id;
        this.dish_name = dish_name;
        this.price = price;
        this.introduction = introduction;
        this.dish_class = dish_class;
        this.img_path = img_path;
    }

    public int getDish_id() {
        return dish_id;
    }

    public void setDish_id(int dish_id) {
        this.dish_id = dish_id;
    }

    public String getDish_name() {
        return dish_name;
    }

    public void setDish_name(String dish_name) {
        this.dish_name = dish_name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getDish_class() {
        return dish_class;
    }

    public void setDish_class(String dish_class) {
        this.dish_class = dish_class;
    }

    public String getImg_download() {
        return img_download;
    }

    public void setImg_download(String img_download) {
        this.img_download = img_download;
    }

    public String getImg_path() {
        return img_path;
    }

    public void setImg_path(String img_path) {
        this.img_path = img_path;
    }
}
