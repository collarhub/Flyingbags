package net.flyingbags.flyingapps.etc;

import java.util.ArrayList;

/**
 * Created by User on 2017-11-23.
 */

public class Shop {
    private int id;
    private String name;
    private String address;
    private String simpleAddress;
    private int drawable;
    private String tel;
    private ArrayList<Integer> image;

    public Shop(int id, String name, String address, String simpleAddress, int drawable, String tel, ArrayList<Integer> image) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.simpleAddress = simpleAddress;
        this.drawable = drawable;
        this.tel = tel;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSimpleAddress() {
        return simpleAddress;
    }

    public void setSimpleAddress(String simpleAddress) {
        this.simpleAddress = simpleAddress;
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public ArrayList<Integer> getImage() {
        return image;
    }

    public void setImage(ArrayList<Integer> image) {
        this.image = image;
    }
}
