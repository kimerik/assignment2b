package com.example.eriksson.assignment2b;

/**
 * Created by Eriksson on 2014-09-24.
 */
public class ContactsModel {

    // Min kontakt

    private String name;

    public long getId() {
        return id;
    }

    private long id;

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public Integer getAge() {
        return age;
    }

    private String imgUrl;
    private Integer age;
    private String description;

    public ContactsModel(Long id, String name, String imgUrl, Integer age, String description){
        this.id = id;
        this.name = name;
        this.imgUrl = imgUrl;
        this.age = age;
        this.description = description;
    }

    public ContactsModel(String name, String imgUrl, Integer age, String description){
        this.name = name;
        this.imgUrl = imgUrl;
        this.age = age;
        this.description = description;
    }

}
