package com.example.myapp;

public class Category {

    private int _id;
    private String _type;
    private String _name;

    // Empty Constructor
    public Category() {}

    // All paramas constructor
    public Category (int id, String type, String name) {
        this._id = id;
        this._type = type;
        this._name = name;
    }

    // without ID constructor
    public Category (String type, String name) {
        this._type = type;
        this._name = name;
    }

    /*** Getters & Setters ***/

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_type() {
        return _type;
    }

    public void set_type(String _type) {
        this._type = _type;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }
}
