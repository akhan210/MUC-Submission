package com.example.administrator.trafficscotlandroadworks;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

/************************************************
 * Developer Name: Asif Khan                    *
 * Student ID:S1435029                          *
 * Module:Mobile And Ubiquitous Computing       *
 * Lecturer: Bobby Law                          *
 * Date:13/12/2015                              *
 ************************************************/

     //This class will handle all the database part of the application for example
    //accessing the database, reading from it and adding records etc. this class
    // and its methods will be used in other part of the application
public class Databasereadhelp extends SQLiteOpenHelper {

    private static final int DB_VER = 1;
    //Default system path of the database in android.
    private static final String DB_PATH = "/data/data/com.example.administrator.trafficscotlandroadworks/databases/";
    //This is the name of the database
    private static final  String DB_NAME = "roadworks.s3db";
    //This is the name of the table in the database
    private static final String DB_TABLE = "Roads";
    //Instance of SQLiteDatabase
    private SQLiteDatabase myDataBase;

    //Column names for the database which will be used to write the database to the device directory
    public static final String C_ROADID = "RoadId";
    public static final String C_TITLE = "Title";
    public static final String C_DESCRIPTION = "Description";
    public static final String C_GEOPOSITION = "GeoPosition";
    //Declaring Context
    private final Context appContext;
    //Constructor
    public Databasereadhelp(Context context, String name,
                            SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
            //Initialising the Context
           this.appContext = context;
    }
    //Oncreate method for database which contains a query for creating the table
    @Override
    public void onCreate(SQLiteDatabase mynewdb) {

        //Sql query to generate a table
        String CREATE_ROADWORKS_TABLE = "CREATE TABLE IF NOT EXISTS " +
                DB_TABLE + "("
                + C_ROADID + " INTEGER PRIMARY KEY," + C_TITLE
                + " TEXT," + C_DESCRIPTION + " TEXT,"
                + C_GEOPOSITION + " TEXT" + ")";
        //executing the query
        mynewdb.execSQL(CREATE_ROADWORKS_TABLE);

    }
    //On upgrade method is used if a table is revised and this method will delete the old table and replace it with the new
    @Override
    public void onUpgrade(SQLiteDatabase mydb, int oldVersiondatabase, int newVersiondatabase) {
        //if statement to check if the new version is bigger for example if it is version 2 than version 1
        if (newVersiondatabase > oldVersiondatabase) {
            //Delete the table if it exits
            mydb.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
            //Create the new table
            onCreate(mydb);
        }
    }

    //this method is used to create a new database in the device directory and it will rewrite it with the database provided
    public void dbCreate() throws IOException {
        //It calls the method dbcheck() to check if the database already exits or not
        boolean dbExist = dbCheck();
        //if statement to check if it doesnot exist
        if (!dbExist) {
            //Getting the database
            this.getReadableDatabase();
            //try the following code
            try {
                //calling the method to copy the database from project to device directory
                copyDBFromAssets();
            //catch if there is an IO exception
            } catch (IOException e) {
                //Display the error
                throw new Error("Error copying database");
            }
        }

    }

    //This method will check if the datasbe already exists in the path provided
    private boolean dbCheck() {
        //creating a new instance
        SQLiteDatabase db = null;
        //try this code
        try {
            //creating a string path of the database for further processing
            String dbPath = DB_PATH + DB_NAME;
            //Using the instance and methods to open the database connection
            db = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
            //setting locale
            db.setLocale(Locale.getDefault());
            //setting the database version to 1
            db.setVersion(1);

            //Catch exception
        } catch (SQLiteException e) {
            //Displays the following in LogCat
            Log.e("SQLDatabaseHelper", "Database does not Exist!");

        }
        //If statement to check the database is not null
        if (db != null) {
            //closing the connection
            db.close();

        }
        //return statement
        return db != null ? true : false;
    }

    //This mwthod will copy the database from assets folder to the directoty of device so that it can be
    //accessed and handled the database is copied using byte stream
    private void copyDBFromAssets() throws IOException {

        //Creating variables and objects
        InputStream dbInput = null;
        OutputStream dbOutput = null;
        String dbFileName = DB_PATH + DB_NAME;
        //try the following code
        try {
            //getting the datbase file from the assets folder
            dbInput = appContext.getAssets().open(DB_NAME);
            dbOutput = new FileOutputStream(dbFileName);
            //transfering data from input to output
            byte[] buffer = new byte[1024];
            int length;
            while ((length = dbInput.read(buffer)) > 0) {
                dbOutput.write(buffer, 0, length);
            }

            //closing the streams
            dbOutput.flush();
            dbOutput.close();
            dbInput.close();
            //catch exception
        } catch (IOException e) {
            //Display Error if there are errors while copying
            throw new Error("Problems copying DB!");
        }
    }

    //This method will add a record of roads to the database
    public void addroads(RoadWorks myroad) {

        //creating values to put in the database
        ContentValues values = new ContentValues();
        values.put(C_TITLE, myroad.getTitle());
        values.put(C_DESCRIPTION, myroad.getdescription());
        values.put(C_GEOPOSITION, myroad.getGeopoint());

        //Opening connection to get the datasbe for writing the record
        SQLiteDatabase db = this.getWritableDatabase();
        //Inserting the values into the table
        db.insert(DB_TABLE, null, values);
        //Closing connection
        db.close();
    }
    //This method will get all the records in the database and put it in a List which will be returned
    public List<String> searchroads() {
        //Query to select all the rows and columns from Roads table
        String query = "Select * FROM Roads";
        //New instance of SQLiteDatabase and to open and read the database
        SQLiteDatabase db = this.getReadableDatabase();
        //Creating a new cursor to run the query
        Cursor cursor = db.rawQuery(query, null);
        //Creating a new list of RoadWorks
        List <String> mylist =  new ArrayList<>();
        //Creating a new object of RoadWorks
        RoadWorks myroads;// = new RoadWorks();
       //checking if the cursor is on the first row
        if (cursor .moveToFirst()) {
            //While loop which will use the cursor till the last column to return a record and save it in the Roadworks object
            //then put it in the list
            while (cursor.isAfterLast() == false) {
                myroads= new RoadWorks();
                myroads.setTitle(cursor.getString(1));
                myroads.setdescription(cursor.getString(2));
                myroads.setgeopoint(cursor.getString(3));
                String newroad = myroads.toString2(true);
                mylist.add(newroad);
                cursor.moveToNext();
            }
            //Closing database cursor
            cursor.close();
        }
        //closing database connection
        db.close();
        //return List containing all the records
        return mylist;
    }

    //This method is taking a parameter which is the name of the road so this will be used to search through the database and
    //to return the rows which are available in the datasbe
    public RoadWorks searchroad(String title) {

        //Creating a query which will search the database based on the name of the road
        String query = "Select * FROM Roads WHERE Title" +" LIKE  \"" + "%"+title+"%" + "\"";
        //opeinig connection to the database
        SQLiteDatabase newdb = this.getReadableDatabase();
        //cursor instance to execute the query
        Cursor cursor = newdb.rawQuery(query, null);
        //new object of the RoadWork
        RoadWorks myroad = new RoadWorks();
        //if statement to run though the row and get all the values and store it in the object
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            myroad.setTitle(cursor.getString(1));
            myroad.setgeopoint(cursor.getString(2));
            myroad.setdescription(cursor.getString(3));
            //closing cursor
            cursor.close();
            //Else statement to check if the road is null
        } else if (myroad == null) {
           myroad.setTitle("Not Found");
            myroad.toString();
        }
        //closing connection
        newdb.close();

        //returning road object
        return myroad;
    }

    //This method is for delelting a single record from the database based on the road name but currently not in use
    public boolean removeroads(String roadname) {
        //Declaring a variable and making result equals false
        boolean result = false;
        //Selecting a row query
        String query = "Select * FROM " + DB_TABLE + " WHERE " + C_TITLE + " =  \"" + roadname + "\"";
        //SqLite database conenction
        SQLiteDatabase db = this.getWritableDatabase();
        //USing cursor to run the query and run through the records
        Cursor cursor = db.rawQuery(query, null);
        //Creating an object of roadworks
        RoadWorks myroads = new RoadWorks();
        //if statement the code below will look for the record using cursor to check every record and will
        //delete the record which is requested based on the name of the road
        if (cursor.moveToFirst()) {
            myroads.setTitle(cursor.getString(0));
            db.delete(DB_TABLE, C_TITLE + " = ?",
                    new String[]{String.valueOf(myroads.getTitle())});
            //closing the cursor
            cursor.close();
            //making result equals true
            result = true;
        }
        //closing database connection
        db.close();
        //returning result whether the record has been deleted or not.
        return result;

   }
}