package com.example.myapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.PreparedStatement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DBHandler extends SQLiteOpenHelper {
    /*** This class provides:

     * APIs to create / restart database
     * Database tables are:
               - Trainings
               - Categories
     * CRUD (Create, Read, Update, Delete) APIs for each database table

    ***/

    // Database environment variables
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "training";

    // Training Table environment variables
    private static final String TABLE_TRAINING_DETAIL = "trainingDetails";
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_CATEGORY = "category";
    private static final String KEY_TIME = "time";
    private static final String KEY_FAVORITE = "isFavorite";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_DATE = "date";

    // Categories Table environment variables
    private static final String TABLE_CATEGORY = "categoryDetails";
    private static final String KEY_CATEG_NAME = "name";
    private static final String KEY_CATEG_TYPE = "type";

    /* Constructor */
    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    /********** Database APIs ************/

    @Override
    public void onCreate(SQLiteDatabase db) {
        /*
           Execute SQL script to create new Tables
        */
        String CREATE_TABLE_TRAINING = "CREATE TABLE " + TABLE_TRAINING_DETAIL + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_TITLE + " TEXT,"
                + KEY_CATEGORY + " TEXT,"
                + KEY_TIME + " TEXT,"
                + KEY_FAVORITE + " BOOLEAN,"
                + KEY_DESCRIPTION + " TEXT,"
                + KEY_DATE + " TEXT" + ")";

        db.execSQL(CREATE_TABLE_TRAINING);

        String CREATE_TABLE_CATEGORIES = "CREATE TABLE " + TABLE_CATEGORY + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_CATEG_NAME + " TEXT,"
                + KEY_CATEG_TYPE + " TEXT" + ")";

        db.execSQL(CREATE_TABLE_CATEGORIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*
            Drop all tables
            Create again all tables
         */
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRAINING_DETAIL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
        onCreate(db);
    }

    /********** Trainings CRUD APIs (Create, Read, Update, Delete) ************/

    public void addNewTraining (Training training) {
        /*
            Parameters: training
            Purpose: insert new training into database
         */

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_TIME, training.get_time());
        values.put(KEY_TITLE, training.get_title());
        values.put(KEY_CATEGORY, training.get_category());
        values.put(KEY_FAVORITE, training.get_isFavorites());
        values.put(KEY_DESCRIPTION, training.get_description());
        values.put(KEY_DATE, training.get_date().format(new Date()));

        db.insert(TABLE_TRAINING_DETAIL, null, values);
        db.close();
    }

    private Training createTrainingFromCursor (Cursor cursor) {
        /*
            Parameters: cursor
            Used: only in DBHandler by 'get' & 'update' methods
            Purpose:
                - retrieve cursor's values
                - create & return a new Training object
         */
        Training t = new Training();
        t.set_id(Integer.parseInt(cursor.getString(0)));
        t.set_title(cursor.getString(1));
        t.set_category(cursor.getString(2));
        t.set_time(cursor.getString(3));
        t.set_description(cursor.getString(5));
        t.set_date(new SimpleDateFormat(cursor.getString(6)));
        if (cursor.getString(4).equals("1")) t.set_isFavorites(true);
        else t.set_isFavorites(false);

        return t;
    }

    public Training getOneTraining (int trainingId) {
        /*
            parameters: trainingId
            Purpose: query database training for the given id
         */
        String selectQuery = "SELECT * FROM " + TABLE_TRAINING_DETAIL
                + " WHERE " + KEY_ID + "=" + trainingId;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Training t = new Training();

        if (cursor.moveToFirst()) {
            t = createTrainingFromCursor (cursor);
        }
        return t;
    }

    public ArrayList<Training> getAllTrainings () {
        /* Purpose: query all trainings in database
         */
        ArrayList<Training> trainingsList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_TRAINING_DETAIL;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
               Training training = createTrainingFromCursor (cursor);
                trainingsList.add(training);
            } while (cursor.moveToNext());
        }
        return trainingsList;
    }

    public ArrayList<Training> getFavoritesTrainings () {
        /*
            Purpose: query all trainings in database with isFavorite = true
         */
        ArrayList<Training> trainingsList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_TRAINING_DETAIL
                + " WHERE " + KEY_FAVORITE + "=" + "1";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Training training = createTrainingFromCursor(cursor);
                trainingsList.add(training);
            } while (cursor.moveToNext());
        }
        return trainingsList;
    }

    public boolean deleteOneTraining (int trainingId) {
        /*
            Parameters: trainingId
            Purpose: delete training from database with given id
         */
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_TRAINING_DETAIL, KEY_ID + "=" + trainingId, null) > 0;
    }

    public boolean updateOneTraining (int trainingId, Training training) {
        /*
            Parameters: trainingId, Training (object)
            Purpose:
                - search in database for Training with given id
                - replace its details with new Training (object)
                - trainingId remains the same
         */
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values  = new ContentValues();

        values.put(KEY_TITLE, training.get_title());
        values.put(KEY_CATEGORY, training.get_category());
        values.put(KEY_TIME, training.get_time());
        values.put(KEY_FAVORITE, training.get_isFavorites());
        values.put(KEY_DESCRIPTION, training.get_description());
        values.put(KEY_DATE, training.get_date().format(new Date()));

        return db.update(TABLE_TRAINING_DETAIL, values, KEY_ID + "=" + trainingId, null) > 0;
    }

    /********** Categories CRUD APIs (Create, Read, Update, Delete) ************/

    public void addNewCategory (Category category) {
        /*
            Parameters: Category (object)
            Purpose: add in database the given Category object
         */
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_CATEG_NAME, category.get_name());
        values.put(KEY_CATEG_TYPE, category.get_type());

        db.insert(TABLE_CATEGORY, null, values);
        db.close();
    }

    public ArrayList<Category> getAllCategoriesByType(String inputType) {
        /*
            Parameters: inputType
            Purpose:
                - search in database for all Category objects with type matching given one
                - returns a list of Category
         */
        ArrayList<Category> categoryList= new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_CATEGORY
                + " WHERE " + KEY_CATEG_TYPE + "=" + inputType;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Category category = new Category();
                category.set_id(Integer.parseInt(cursor.getString(0)));
                category.set_name(cursor.getString(1));
                category.set_type(cursor.getString(2));

                categoryList.add(category);
            } while (cursor.moveToNext());
        }
        return categoryList;
    }

    public boolean deleteOneCategory (int categId) {
         /*
            Parameters: categId
            Purpose: delete category from database with given id
         */
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_CATEGORY, KEY_ID + "=" + categId, null) > 0;
    }

}
