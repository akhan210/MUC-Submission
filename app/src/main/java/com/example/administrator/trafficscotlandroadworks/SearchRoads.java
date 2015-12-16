package com.example.administrator.trafficscotlandroadworks;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/************************************************
 * Developer Name: Asif Khan                    *
 * Student ID:S1435029                          *
 * Module:Mobile And Ubiquitous Computing       *
 * Lecturer: Bobby Law                          *
 * Date:13/12/2015                              *
 ************************************************/

    //This is a Class Which is used for reading from the database and display the results to the user
    // as well as saving user preferences
public class SearchRoads extends ActionBarActivity {

    //Declaring Variables and Views
    private EditText inputbox;
    private RoadWorks newroad;
    private GridView mygrid;
    private Context icontext;
    private Button searchbutton;
    public static final String UserPREFERENCES = "MyPrefs" ;
    public static final String Title = "titleKey";
    public static  SharedPreferences saveuserprefs=null;
    private Button savebutton;

    //Oncreate Method for this activity class
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Setting the appropriate layout
        setContentView(R.layout.search_input);
        icontext=this;
        //Initialising the Views
        inputbox=(EditText)findViewById(R.id.ettitle);
        searchbutton=(Button)findViewById(R.id.btnsearch);
        //New Roadworks object
        newroad= new RoadWorks();
        savebutton=(Button)findViewById(R.id.btnsave);
        //Initialising the SharedPreferences and alo using Private mode Context
        saveuserprefs = icontext.getApplicationContext().getSharedPreferences(UserPREFERENCES, icontext.MODE_PRIVATE);
        //Caretaing a new object of SharedPReferecnes to get the data back
        SharedPreferences sp = getSharedPreferences("key", 0);
        //Getting the data back from saved prefs
        String tValue = sp.getString("usertext","");
        //Checking if the value if null
        if(tValue==null){

        }
        //Otherwise make the edit text equal to the saved prefs
        else {
            inputbox.setText(tValue);
        }
        //On click lsitener for search button to handle click events
        searchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Initialising grid view
                mygrid= (GridView)findViewById(R.id.gdsearchroads);
                //new object of the databasereadhelp
                Databasereadhelp mydb= new Databasereadhelp(icontext,"roadworks.s3db",null,1);
                //try catch
                try {
                    //this will run the method dbcreate which is in datbasereadhelp class
                    mydb.dbCreate();

                } catch (IOException ioe) {
                    //this will give the error if there is an error while creating the database
                    throw new Error("Unable to create database");

                }
                //Creating a new list of roadwork objects as a String
                List<String> newlist = new ArrayList<>();

                //Creating a string which is being recived from the user through edit text
                String titlex = inputbox.getText().toString();
                 //Checking if the edit text is empty
                 if(titlex.matches("")){
                //Getting all the records from the database and putting into the list

                newlist=mydb.searchroads();
                //Creating an array adapter for the data grid view
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(icontext,
                        android.R.layout.simple_list_item_1,newlist );
                //Setting the array adapter of the data grid view which will fill the data grid view.
                mygrid.setAdapter(adapter);
                }
                 else{
                     //Creating a new object
                    RoadWorks mynewroad = new RoadWorks();
                   //Searching and making the object equal to the result which will be recvied form the method
                    mynewroad = mydb.searchroad(titlex);
                     String newroad = mynewroad.toString2(true);
                     //checking if the obejct is null
                     if(mynewroad==null ){
                         //A Toast Message will be received if the record is not found
                         Toast.makeText(icontext, "Record Not Found",
                                 Toast.LENGTH_SHORT).show();
                     }
                     else{
                         //Creating a new List
                         List<String> mynewarray = new ArrayList<String>();
                         //Add the record to the list
                         mynewarray.add(newroad);
                         //Add the list to the adapter
                         ArrayAdapter<String> myadapter = new ArrayAdapter<String>(icontext,
                                 android.R.layout.simple_list_item_1, mynewarray);
                         //Setting the array adapter of the data grid view which will fill the data grid view.
                         mygrid.setAdapter(myadapter);
                     }

                }
            }
        });
        //Save preferences click lsitener to handle the click event
        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //New string of edit text
                String titlex = inputbox.getText().toString();
                //Saving Prefs
                saveuserprefs = getSharedPreferences("key", 0);
                SharedPreferences.Editor myeditor = saveuserprefs.edit();
                //Clearing old prefs
                myeditor.clear();
                //Putting new Pref using edit text string
                myeditor.putString("usertext",titlex);
                //Apply the changes which means save the prefs in MyPrefs
                myeditor.apply();
                //Toas Message to show the its saved
                Toast.makeText(icontext, "Preference Saved",
                        Toast.LENGTH_SHORT).show();
            }
        });



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
            finish();
        }
        // Return statement
        return super.onOptionsItemSelected(item);
    }




}
