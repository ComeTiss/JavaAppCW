package com.example.myapp;

public class Training {

    private String title;
    private String category;
    private String time;

    // Constructor
    public Training (String title, String category, String time) {
        this.title = title;
        this.category = category;
        this.time = time;
    }

    // Get methods
    public String getTitle () {
        return this.title;
    }

    public String getCategory () {
        return this.category;
    }

    public String getTime () {
        return this.time;
    }
}
