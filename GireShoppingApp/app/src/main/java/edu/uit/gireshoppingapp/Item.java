package edu.uit.gireshoppingapp;

public class Item {
    private int id;
    private String name;
    private int price;
    private String desc;
    private String imgURL;

    public Item(int id, String name, int price, String desc, String imgURL) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.desc = desc;
        this.imgURL = imgURL;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getDesc() {
        return desc;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }
}
