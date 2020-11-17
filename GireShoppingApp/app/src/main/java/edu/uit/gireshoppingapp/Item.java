package edu.uit.gireshoppingapp;

public class Item {
    private String desc;
    private String id;
    private String imgURL;
    private String name;
    private String price;

    public Item() {
    }

    public Item(String desc, String id, String imgURL, String name, String price) {
        this.desc = desc;
        this.id = id;
        this.imgURL = imgURL;
        this.name = name;
        this.price = price;
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
}
