package com.example.administrator.trafficscotlandroadworks;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

/************************************************
 * Developer Name: Asif Khan                    *
 * Student ID:S1435029                          *
 * Module:Mobile And Ubiquitous Computing       *
 * Lecturer: Bobby Law                          *
 * Date:13/12/2015                              *
 ************************************************/
     //This is the main activity of the app and it will first run when the app is loaded
    //this activity has different button and views and will let the user to naviagte through the app.
public class MainActivity extends ActionBarActivity {

    //Declaring varibales,lists and View such as Buttons and Textviews as well as custom objects
    private ImageView roadsign;
    public String webrss;
    PullParser mynewparser= new PullParser();
    private Button ListRoads;
    private Button canvasbutton;
    private Button searchbutton;
    private Context icontext;
    RoadWorks myroads = new RoadWorks();
    List<RoadWorks> mynewlist=new ArrayList<RoadWorks>();
    public String websiteurl = "https://trafficscotland.org/rss/feeds/roadworks.aspx";
    //Oncreate method for main activity which will run at the start of the project
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if( getIntent().getBooleanExtra("Exit me", false)){
            finish();
            return; // add this to prevent from doing unnecessary stuffs
        }
        setContentView(R.layout.activity_main);
        //This has been used becasue for some reason the connection wasnt possible to be made to the
        //xml feed due to some issues and the code below solved the issue
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
       //Initialising the Views
        ListRoads= (Button)findViewById(R.id.btnsee);
        canvasbutton= (Button)findViewById(R.id.btncanvas);
        searchbutton= (Button)findViewById(R.id.btnsearch);
       icontext=this;
        //Onclick listener for search button which will handle the click event
        searchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating an intent to move to the other activity
                Intent myIntent = new Intent(MainActivity.this, SearchRoads.class);
                //This will start the activity which is selected above
                startActivity(myIntent);
            }
        });
        //Onclick listener for lsitroads button which will handle the click event
        ListRoads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating an intent to move to the other activity
                Intent myIntent = new Intent(MainActivity.this, roadworkslist.class);
                //This will start the activity which is selected above
                startActivity(myIntent);
            }
        });
        //Onclick listener for canvas button which will handle the click event
        canvasbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Creating an intent to move to the other activity
                Intent myIntent = new Intent(MainActivity.this, CanvasDrawing.class);
                //This will start the activity which is selected above
               startActivity(myIntent);
            }
        });
    }


    //This method is used to inflate the menu on the activity bar and to display the items which are in the menu layout file
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // This will adds items to the action bar from the file
        getMenuInflater().inflate(R.menu.frontscreenmenu, menu);
        return true;
    }
    //This Method will handle the work if an item is selected from the menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //Getting the id(name of item) of item and making it equal to id so it can be used for all items
        int id = item.getItemId();
        //If statement to handle the item which is called about
        if (id == R.id.action_About) {
            //creating a new instance of alert dialog which will be used to display about information in a dialogue
            AlertDialog.Builder aboutdialogue = new AlertDialog.Builder(icontext);
            //This will set the title of the dialogue box
            aboutdialogue.setTitle("About This Application");
            //This will display a message to the user
            aboutdialogue.setMessage("This App is Developed by Asif Khan  and it gives information about the live roadworks of scotland" +
                    " as well as its exact location" + " Student ID: S1435029");
            //Setting an ok button on the dialogue box
            aboutdialogue.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                //This will handle click action if the user clicks but currently i am not doing with it anything so by clicking close will exit just the dialogue box
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
                //This fill finally create and display the dialgoue box
            }).create().show();

            return true;
        }
        //else If statement to handle the item which is called exit
        else if (id==R.id.action_Exit) {

            AlertDialog.Builder Exitdialogue = new AlertDialog.Builder(icontext);
            //This will set the title of the dialogue box
            Exitdialogue.setTitle("Exit This Application");
            //This will display a message to the user
            Exitdialogue.setMessage("Are you Sure you want to close the App??");
            Exitdialogue.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                //This will handle click action if the user clicks but currently i am not doing with it anything so by clicking No will exit just the dialogue box
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
                //This fill finally create and display the dialgoue box
            });
            //Setting an ok button on the dialogue box
            Exitdialogue.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                //This will handle click action if the user clicks but currently i am not doing with it anything so by clicking Yes will exit the App
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //Closing the app and bringing up the home application of the device so the app is still
                    //in the background as accroding to android principles an app shouldnot be completely killed
                    Intent intent = new Intent(icontext, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("Exit me", true);
                    startActivity(intent);
                    finish();
                }
                //This fill finally create and display the dialgoue box
            }).create().show();


        }
        // Return statement
        return super.onOptionsItemSelected(item);
    }

}
