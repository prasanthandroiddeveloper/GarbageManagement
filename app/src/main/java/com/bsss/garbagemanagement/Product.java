package com.bsss.garbagemanagement;

public class Product {
    private int id;
    private String Image;
    private String name;
    private String Description;
    private String mobile;
    private String email;
    private String Addr_one;
    private String Addr_two;
    private String Landmark;
    private String City;
    private String Pin;
    private String District;


    public Product(int id, String image, String name, String description, String mobile, String email, String addr_one, String addr_two, String landmark, String city, String pin, String district) {
        this.id = id;
        this.Image = image;
        this.name = name;
        this.Description = description;
        //this.Quantity = quantity;
        this.mobile = mobile;
        this.email = email;
        this.Addr_one = addr_one;
        this.Addr_two = addr_two;
        this.Landmark = landmark;
        this.City = city;
        this.Pin = pin;
        this.District = district;
        //this.State = state;
    }

    public int getId() {
        return id;
    }

    public String getImage() {
        return Image;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return Description;
    }

//    public String getQuantity() {
//        return Quantity;
//    }

    public String getMobile() {
        return mobile;
    }

    public String getEmail() {
        return email;
    }

    public String getAddr_one() {
        return Addr_one;
    }

    public String getAddr_two() {
        return Addr_two;
    }

    public String getLandmark() {
        return Landmark;
    }

    public String getCity() {
        return City;
    }

    public String getPin() {
        return Pin;
    }

    public String getDistrict() {
        return District;
    }
}

//    public String getState() {
//        return State;
//    }
