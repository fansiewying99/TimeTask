package com.g3;

public class Tag {

    public String Name;
    public int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Tag(String name) {
        Name = name;
    }

    public Tag(int id, String name) {
        this.id = id;
        this.Name = name;
    }

    public Tag() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
