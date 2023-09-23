package com.example.firaqr;

public class NameModel{
    private int id;
    private String name;
    private String time;

    public NameModel(int id, String names, String time) {
        this.id = id;
        this.name = names;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String names) {
        this.name = names;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
