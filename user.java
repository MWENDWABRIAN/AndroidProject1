package com.example.barbercustomer;


public class User {

    private String name;
    private String address;
    private String contact;
    private String date;
    private String time;


    public User() {

    }

    public User(String name, String address, String contact, String date,String time) {
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.date = date;
        this.time=time;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getContact() {
        return contact;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
