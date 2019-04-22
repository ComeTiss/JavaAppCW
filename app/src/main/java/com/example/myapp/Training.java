package com.example.myapp;

public class Training {

    // Private variables
    int _id;
    String _title;
    String _category;
    String _time;
    Boolean _isFavorites;

    //empty constructor
    public Training() {}

    // all params constructor
    public Training(int _id, String _title, String _category, String _time, Boolean _isFavorites) {
        this._id = _id;
        this._title = _title;
        this._category = _category;
        this._time = _time;
        this._isFavorites = _isFavorites;
    }

    // without id constructor
    public Training(String _title, String _category, String _time, Boolean _isFavorites) {
        this._title = _title;
        this._category = _category;
        this._time = _time;
        this._isFavorites = _isFavorites;
    }

    /**** GETTERS ****/

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_title() {
        return _title;
    }

    public void set_title(String _title) {
        this._title = _title;
    }

    public String get_category() {
        return _category;
    }

    public Boolean get_isFavorites() {
        return _isFavorites;
    }
    /**** SETTERS ****/

    public void set_category(String _category) {
        this._category = _category;
    }

    public String get_time() {
        return _time;
    }

    public void set_time(String _time) {
        this._time = _time;
    }

    public void set_isFavorites(Boolean _isFavorites) {
        this._isFavorites = _isFavorites;
    }

}
