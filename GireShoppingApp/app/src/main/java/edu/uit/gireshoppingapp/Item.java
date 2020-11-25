package edu.uit.gireshoppingapp;

public class Item {
    private String desc;
    private String id;
    private String imgURL;
    private String name;
    private String price;
    private String brand;
    private String number;

    public Item() {
    }

    public Item(String desc, String id, String imgURL, String name, String price, String brand, String number) {
        this.desc = desc;
        this.id = id;
        this.imgURL = imgURL;
        this.name = name;
        this.price = price;
        this.brand = brand;
        this.number = number;
    }

    public String getDesc() {
        return desc;
    }

    public String getId() {
        return id;
    }

    public String getImgURL() {
        return imgURL;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getBrand() {
        return brand;
    }

    public String getNumber() {
        return number;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public boolean isEqual(Item item)
    {
        if(this.getId() == item.getId())
            return true;
        else
            return false;
    }
}
