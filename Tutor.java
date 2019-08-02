package com.paneapple.paneapple.models;

public class Tutor {
    public String name, email, username, dob, uid,image,wallet_amount="",onlineStatus="";

    public Tutor() {
    }

    public Tutor(String name, String email, String username, String dob, String uid, String image, String wallet_amount, String onlineStatus) {
        this.name = name;
        this.email = email;
        this.username = username;
        this.dob = dob;
        this.uid = uid;
        this.image = image;
        this.wallet_amount=wallet_amount;
        this.onlineStatus=onlineStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getWallet_amount() {
        return wallet_amount;
    }

    public void setWallet_amount(String wallet_amount) {
        this.wallet_amount = wallet_amount;
    }

    public String getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(String onlineStatus) {
        this.onlineStatus = onlineStatus;
    }
}
