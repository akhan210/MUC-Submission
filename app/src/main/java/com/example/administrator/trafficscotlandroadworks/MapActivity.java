package com.example.administrator.trafficscotlandroadworks;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/************************************************
 * Developer Name: Asif Khan                    *
 * Student ID:S1435029                          *
 * Module:Mobile And Ubiquitous Computing       *
 * Lecturer: Bobby Law                          *
 * Date:13/12/2015                              *
 ************************************************/
     //This class will handle the map and the layout is set to activity_map.xml. This class will get the location
    //of an object from the roadworklist and will display its exact location on google map as well as information about the
    //object selected by the user and this class will also calculate a distance between two points
public class MapActivity extends AppCompatActivity {

    //Declaring variables and Views
    private GoogleMap mymap;
    private TextView txttitle;
    private TextView txtpos;
    private TextView txtdesc;
    private TextView txtdistance;
    private Context icontext;
    RoadWorks myroad;
    //Oncreate for this class which will run when this activity is called
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //To keep the activiyt in Portrait mode
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //Setting the actiivyt layout
        setContentView(R.layout.activity_map);
        //Getting the intent which contains a roadobject from the roadworklist activity
        Intent i = getIntent();
        //Making the new road object creating earlier above to the object which is received in the intent
        myroad = (RoadWorks)i.getSerializableExtra("RoadObject");
        icontext=this;
        //Initialising the Views
        txttitle=(TextView)findViewById(R.id.txttitle);
        txtpos=(TextView)findViewById(R.id.txtpos);
        txtdesc=(TextView)findViewById(R.id.txtdescription);
        txttitle.setText(myroad.getTitle());
        txtdesc.setText(myroad.getdescription());
        txtdistance=(TextView)findViewById(R.id.txtdistance);
        txtpos.setText(myroad.getGeopoint());
        Button btndest=(Button)findViewById(R.id.btndistance);
        //String Splitter is used to split the string into two as the longitude and latitude needs to be
        //declared seperateley but its stored in the class in one string becasue its also stored in the live xml
        //in a single string hence needs to be splitted
        String string = myroad.getGeopoint();
        String[] parts = string.split(" ");
        String part1 = parts[0];
        String part2 = parts[1];
        //Creating a new Longtitude and latitude which is recieved from the object
        LatLng newpoint = new LatLng(Double.parseDouble(part1),Double.parseDouble(part2));
       //Try the following code
        try {
            //Checking if map equals null
            if (mymap == null) {
                //Make the map equal to the fragment which is declared in the layout
                mymap = ((MapFragment) getFragmentManager().
                        findFragmentById(R.id.gmap)).getMap();
            }
            //Setting the type of the map to hybrid
            mymap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            //Adding a marker to the map
            Marker TP = mymap.addMarker(new MarkerOptions().
                    position(newpoint).title(myroad.getTitle()));
            //Calling method to zoom in to the location
            zoommyloc(newpoint);
        }
        //catch exception
        catch (Exception e) {
            e.printStackTrace();
        }
        //Onclick lsitener for getting the distance  between two points
        btndest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Again spliiting the string
                String string = myroad.getGeopoint();
                String[] parts = string.split(" ");
                String part1 = parts[0];
                String part2 = parts[1];
                //Creating a new location
                Location getlocation = new Location("Uni");
                //Setting longitude and latitude
                  getlocation.setLatitude(55.866218);
                getlocation.setLongitude(-4.250674);
                //Creating a new location
                Location desloc=new Location("Dest");
                //Setting longitude and latitude
                desloc.setLatitude(Double.parseDouble(part1));
                desloc.setLongitude(Double.parseDouble(part2));
                //Getting the distance and putting it in a double variable
                double distance=getlocation.distanceTo(desloc);
                //Converting meters to miles
                double mydisinmeters = distance*0.000621371;
                //Converting it into string
                String mydis= Double.toString(mydisinmeters);
                //Displaying the result on textview
                txtdistance.setText(mydis);
            }
        });
    }
        //This method will zoom in on the location
       private void zoommyloc(LatLng currentLocation)
             {
           //Zooming in on the map using camera
            mymap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));
        // Animating the camera of google map to show smoothness.
             mymap.animateCamera(CameraUpdateFactory.zoomIn());
        // Animating the camera for zooming out an in  with a duration of 2 seconds.
            mymap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);

             }



    //This method is used to inflate the menu on the activity bar and to display the items which are in the menu layout file
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // This will adds items to the action bar from the file
        getMenuInflater().inflate(R.menu.map_menu, menu);
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
            finish();
        }
        else if (id==R.id.action_Mainscreen){
            finish();
            Intent myIntent = new Intent(MapActivity.this, MainActivity.class);
            //This will start the activity which is selected above
            startActivity(myIntent);
        }
        else if (id==R.id.action_changeview){
            //Alert Diagloue for displaying multiple options to select for chanign map views
            AlertDialog.Builder changeviewdialogue = new AlertDialog.Builder(icontext);
            //Setting the title of the dialogue
            changeviewdialogue.setTitle("Change Map View");
            //Setting clickable texts
            changeviewdialogue.setItems(new CharSequence[]
                            {"Satellite", "Hybrid", "Normal" },
                    //Onclick listener for the buttons
                    new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            //Multiple cases for different options
                            switch (which) {
                                case 0:
                                    //Setting different map view on the map created earlier
                                    mymap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                                    break;
                                case 1:
                                    //Setting different map view on the map created earlier
                                    mymap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                                    break;
                                //Setting different map view on the map created earlier
                                case 2:
                                    mymap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                                    break;

                            }
                        }
                    });
            //Finally creating and displaying the dialogue box
            changeviewdialogue.create().show();


        }
        // Return statement
        return super.onOptionsItemSelected(item);
    }


}
