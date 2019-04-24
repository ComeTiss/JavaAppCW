package com.example.myapp;

public class Category {
    /*** This class provides:

     * 3 types of constructor
     * Getters & Setters methods for global variables (id, type, name)

     ***/

    private int _id;
    private String _type; // 1 (training), 2 (meal)
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
