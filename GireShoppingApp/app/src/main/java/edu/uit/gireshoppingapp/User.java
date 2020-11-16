package edu.uit.gireshoppingapp;

import androidx.annotation.NonNull;

public class User {
    private String name;
    private String imgURL;
    private String balance;

    public User()
    {
    }

    public String getName() {
        return name;
    }

    public String getImgURL() {
        return imgURL;
    }

    public String getBalance() {
        return balance;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    @NonNull
    @Override
    public String toString() {
        String userToString = this.name + "\n" + this.balance + "\n" + this.imgURL + "\n";
        return userToString;
    }
}
