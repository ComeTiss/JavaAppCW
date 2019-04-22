package com.example.myapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "training";

    // Training Table environment variables
    private static final String TABLE_TRAINING_DETAIL = "trainingDetails";
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_CATEGORY = "category";
    private static final String KEY_TIME = "time";
    private static final String KEY_FAVORITE = "isFavorite";

    // Categories Table environment variables
    private static final String TABLE_CATEGORY = "categoryDetails";
    private static final String KEY_CATEG_NAME = "name";
    private static final String KEY_CATEG_TYPE = "type";

    /* Constructor */
    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /* Creating Tables */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_TRAINING = "CREATE TABLE " + TABLE_TRAINING_DETAIL + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_TITLE + " TEXT,"
                + KEY_CATEGORY + " TEXT,"
                + KEY_TIME + " TEXT,"
                + KEY_FAVORITE + " BOOLEAN" + ")";
        db.execSQL(CREATE_TABLE_TRAINING);

        String CREATE_TABLE_CATEGORIES = "CREATE TABLE " + TABLE_CATEGORY + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_CATEG_NAME + " TEXT,"
                + KEY_CATEG_TYPE + " TEXT" + ")";

        db.execSQL(CREATE_TABLE_CATEGORIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // drop table
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRAINING_DETAIL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);

        // create table again
        onCreate(db);
    }

    /*** CRUD APIs (Create, Read, Update, Delete) for Trainings ***/

    public void addNewTraining (Training training) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_TIME, training.get_time());
        values.put(KEY_TITLE, training.get_title());
        values.put(KEY_CATEGORY, training.get_category());
        values.put(KEY_FAVORITE, training.get_isFavorites());

        db.insert(TABLE_TRAINING_DETAIL, null, values);
        db.close();
    }

    public boolean deleteOneTraining (int trainingId) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_TRAINING_DETAIL, KEY_ID + "=" + trainingId, null) > 0;
    }

    public ArrayList<Training> getAllTrainings () {
        ArrayList<Training> trainingsList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_TRAINING_DETAIL;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Training training = new Training();
                training.set_id(Integer.parseInt(cursor.getString(0)));
                training.set_title(cursor.getString(1));
                training.set_category(cursor.getString(2));
                training.set_time(cursor.getString(3));

                trainingsList.add(training);
            } while (cursor.moveToNext());
        }
        return trainingsList;
    }

    public ArrayList<Training> getFavoritesTrainings () {
        ArrayList<Training> trainingsList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_TRAINING_DETAIL
                + " WHERE " + KEY_FAVORITE + "=" + "1";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Training training = new Training();
                training.set_id(Integer.parseInt(cursor.getString(0)));
                training.set_title(cursor.getString(1));
                training.set_category(cursor.getString(2));
                training.set_time(cursor.getString(3));

                trainingsList.add(training);
            } while (cursor.moveToNext());
        }
        return trainingsList;
    }

    /*** Categories CRUD API (Create, Read, Update, Delete) ***/

    public void addNewCategory (Category category) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_CATEG_TYPE, category.get_type());
        values.put(KEY_CATEG_NAME, category.get_name());

        db.insert(TABLE_CATEGORY, null, values);
        db.close();
    }

    public boolean deleteOneCategory (int categId) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_CATEGORY, KEY_ID + "=" + categId, null) > 0;
    }

    public ArrayList<Category> getAllCategoriesByType(String type) {
        ArrayList<Category> categoryList= new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_CATEGORY;
           //     + " WHERE " + KEY_CATEG_TYPE + "=Training";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Category category = new Category();
                category.set_id(Integer.parseInt(cursor.getString(0)));
                category.set_type(cursor.getString(1));
                category.set_name(cursor.getString(2));

                categoryList.add(category);
            } while (cursor.moveToNext());
        }
        return categoryList;
    }
}
