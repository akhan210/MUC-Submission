package com.example.administrator.trafficscotlandroadworks;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

/************************************************
 * Developer Name: Asif Khan                    *
 * Student ID:S1435029                          *
 * Module:Mobile And Ubiquitous Computing       *
 * Lecturer: Bobby Law                          *
 * Date:13/12/2015                              *
 ************************************************/

//This Class is used for drawing name of the developer to the
//canvas to demonstrate the understanding of programming an android app canvas
public class CanvasDrawing extends AppCompatActivity {

    private Context icontext;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(new MynewView(this));
        icontext=this;
    }

    public class MynewView extends View {

       /* class Pt{

            float x, y;
            Pt(float _x, float _y){
                x = _x;
                y = _y;
            }

        }



        Pt[] myPath = { new Pt(100, 100),
                new Pt(200, 200),
                new Pt(200, 500),
                new Pt(400, 700),
                new Pt(400, 200)

        };*/

        public MynewView(Context context) {

            super(context);
        }


//This Method is used to draw the text to the canvas
        @Override
        protected void onDraw(Canvas mycanvas) {

            //drawing canvas to the screen
            super.onDraw(mycanvas);
            //Creating a new instance of paint
            Paint mypaint = new Paint();
            mypaint.setColor(Color.BLUE);
            mypaint.setStyle(Paint.Style.FILL);
            mycanvas.drawPaint(mypaint);

            mypaint.setColor(Color.YELLOW);
            mypaint.setTextSize(69);
            //Saving the canvas
            mycanvas.save();
            //I used this to rotate my text to a certain degree on the canvas
            mycanvas.rotate((float) 65, 50, 50);
            //Drawing the text below to the canvas
            mycanvas.drawText("Designed By Asif Khan", 99, 75, mypaint);
            //Restoring the canvas to its previous state
            mycanvas.restore();

        }

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
