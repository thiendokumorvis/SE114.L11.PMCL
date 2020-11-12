package edu.uit.gireshoppingapp;

public class Item {
    private String desc;
    private String id;
    private String imgURL;
    private String name;
    private String price;

    public Item() {
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
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

    public void setPrice(String price) {
        this.price = price;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }
}
