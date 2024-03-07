package com.example.workshop;

public class UserData {

    String username;
    String dob;
    String contact;

    public UserData(String username, String dob, String contact) {
        this.username = username;
        this.dob = dob;
        this.contact = contact;
    }
    public UserData()
    {

    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
