package com.example.administrator.trafficscotlandroadworks;

import java.io.Serializable;

/************************************************
 * Developer Name: Asif Khan                    *
 * Student ID:S1435029                          *
 * Module:Mobile And Ubiquitous Computing       *
 * Lecturer: Bobby Law                          *
 * Date:13/12/2015                              *
 ************************************************/
//This is a Class For RoadWorks which will be used to create objects and use it throughtout the app
public class RoadWorks implements Serializable {

    //Declaring variables
    private String title;
    private String description;
    private String geopoint;

    //Constructor
    public RoadWorks(){

    }
    //Overloaded Constructor
    public RoadWorks(String ntitle,String ndescription,String ngeopoint ){

        title=ntitle;
        description=ndescription;
        geopoint=ngeopoint;
    }
    //Getters
    public String getTitle(){

       return title;
     }

    public String getdescription(){
        return description;
    }

    public String getGeopoint(){
        return geopoint;
    }

    //Setters
    public void setTitle(String titlex){

        this.title=titlex;

    }

    public void setdescription(String descriptionx){

        this.description=descriptionx;

    }
    public void setgeopoint(String geopointx){

        this.geopoint=geopointx;

    }

    //To String method to return the object in a single string
    @Override
    public String toString() {
        return ("Title:"+this.getTitle());/*+
                " Description: "+  this.getdescription() +
                " GeoPoint : " + this.getGeopoint());*/
    }

    public String toString2(boolean x)
    {
        return ("Title:"+this.getTitle() +
                " Description: " +  this.getdescription() +
                " GeoPoint : " + this.getGeopoint());
    }

}
