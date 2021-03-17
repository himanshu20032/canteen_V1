package com.mc.mcfirebase.Model;

public class foodData {
    private String Image;
    private String Name;
    private String Desc;
    private String id;
    private String Price;


    public foodData(String image, String name, String desc, String id, String price) {
        Image = image;
        Name = name;
        Desc = desc;
        this.id = id;
        Price = price;

    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public foodData() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
