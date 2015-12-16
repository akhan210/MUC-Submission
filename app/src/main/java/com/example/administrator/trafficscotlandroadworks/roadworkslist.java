package com.example.administrator.trafficscotlandroadworks;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/************************************************
 * Developer Name: Asif Khan                    *
 * Student ID:S1435029                          *
 * Module:Mobile And Ubiquitous Computing       *
 * Lecturer: Bobby Law                          *
 * Date:13/12/2015                              *
 ************************************************/

//This class is used to display the roadworks from the xml and it will display it in a list view
public class roadworkslist extends AppCompatActivity {

    //Declaring variables
    private Context icontext;
    PullParser mynewparser= new PullParser();
    RoadWorks myroads = new RoadWorks();
    List<RoadWorks> mynewlist=new ArrayList<RoadWorks>();
    public String websiteurl = "https://trafficscotland.org/rss/feeds/roadworks.aspx";
    private ListView roadslistview;
    public String webrss;

    //Oncreate Method for this class which loads when the activity is called
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Setting the contens of this actiivyt to the appropriate layout
        setContentView(R.layout.activity_roadworkslist);

        icontext=this;
        //List View
        roadslistview = (ListView) findViewById(R.id.roadlist);
        //Try this code
        try {
            //calling the Method and passing the url as a string
            webrss = sourceListingString(websiteurl);
            //Parsing the data and putting it in a list of objects
            mynewlist=  mynewparser.parseData(webrss);
            //Removing 2 null entries in the list
            mynewlist.remove(0);
            mynewlist.remove(0);
            //Putting the list into an array adapter
            final ArrayAdapter<RoadWorks> myadapter = new ArrayAdapter<RoadWorks>(icontext, android.R.layout.simple_list_item_1, mynewlist);
           //Setting the adapter to the list view
            roadslistview.setAdapter(myadapter);
            //On item click listener for the list view which will handle the clicks on the items in the list view
            roadslistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //Creating a new object ad making it equal to the selected object in list to get the object and position
                    RoadWorks newobj =(RoadWorks) myadapter.getItem(position);
                    //Creating an intent to pass the object to the activity below
                    Intent i = new Intent(roadworkslist.this, MapActivity.class);
                    //Putting the object in the intent
                    i.putExtra("RoadObject", newobj);
                    //Starting the activity
                    startActivity(i);

                }
            });
            //Catch Exception
        }
        catch (Exception ex){
            System.out.print("Exception for Parsing and Adding to ListView" + ex);
        }

    }

    //This method takes in a string parameter which will be the url to be connected, it is used to connect to the url provided
    //this is done through HTTPurl connection
    public static String sourceListingString(String urlString)throws IOException
    {
        String result = "";
        InputStream anInStream = null;
        int response = -1;
        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();

        // Check that if this is a HTTP connection
        if (!(conn instanceof HttpURLConnection))
            throw new IOException("Not an HTTP connection");
        try
        {
            // Open connection
            HttpURLConnection httpConn = (HttpURLConnection) conn;
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.connect();
            response = httpConn.getResponseCode();
            // Check that connection is Ok
            if (response == HttpURLConnection.HTTP_OK)
            {
                // Connection is Ok so open an input stream reader
                anInStream = httpConn.getInputStream();
                InputStreamReader in= new InputStreamReader(anInStream);
                BufferedReader bin= new BufferedReader(in);

                // Read in the data from the XML stream
                String line = new String();
                while (( (line = bin.readLine())) != null)
                {
                    result = result + "\n" + line;
                }
            }
        }
        catch (Exception ex)
        {
            //Return this error if there is any error while connecting
            throw new IOException("Error connecting" + ex);
        }
        result = result.trim();
        // Return result as a string for further processing
        return result;
    }

    //This method is used to inflate the menu on the activity bar and to display the items which are in the menu layout file
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // This will adds items to the action bar from the file
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
                //This will handle click action if the user clicks but currently i am not doing with it anything so by clicking Close will exit just the dialogue box
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
                //This fill finally create and display the dialgoue box
            }).create().show();

            return true;
        }
        //else If statement to handle the item which is called exit
        else if (id==R.id.action_Exit){

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


            //If statement to handle the item which is called back
        }else if (id==R.id.action_Back){
            finish();
        }
        // Return statement
        return super.onOptionsItemSelected(item);
    }

}
