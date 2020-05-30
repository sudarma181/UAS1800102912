package com.va181.sudarma.model;

import com.google.gson.annotations.SerializedName;

public class Contact {
    @SerializedName("id")
    private int id;
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    @SerializedName("number")
    private String phoneNumber;
    @SerializedName("name_book")
    private String nameBook;
    @SerializedName("email")
    private String email;
    @SerializedName("photo")
    private String photo;
    @SerializedName("gender")
    private String gender;

    public Contact(int id, String firstName, String lastName, String phoneNumber, String nameBook , String email, String photo, String gender) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.nameBook = nameBook;
        this.email = email;
        this.photo = photo;
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getNameBook() {
        return nameBook;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoto() {
        return photo;
    }

    public String getGender() {
        return gender;
    }
}
