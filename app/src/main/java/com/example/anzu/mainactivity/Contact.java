package com.example.anzu.mainactivity;

/**
 * Created by Ashok Sir on 3/24/2018.
 */
public class Contact {
    String id;
    String name;
    String phoneno;
    String email;
    String description;
    public Contact(String name, String phoneno, String email, String description) {
        this.name = name;
        this.phoneno = phoneno;
        this.email = email;
        this.description = description;
    }

    public Contact(String id, String name, String phoneno, String email, String description) {
        this.id = id;
        this.name = name;
        this.phoneno = phoneno;
        this.email = email;
        this.description = description;
    }
    public Contact(String name, String phoneno) {
        this.name = name;
        this.phoneno = phoneno;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }


}

