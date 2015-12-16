package com.example.administrator.trafficscotlandroadworks;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilderFactory;

/************************************************
 * Developer Name: Asif Khan                    *
 * Student ID:S1435029                          *
 * Module:Mobile And Ubiquitous Computing       *
 * Lecturer: Bobby Law                          *
 * Date:13/12/2015                              *
 ************************************************/


//This Class will parse the xml data and return the result in a list of objects and the objects will be of RoadWorks
public class PullParser {

    //Declaring a List of RoadWorks
    List<RoadWorks> mylist=new ArrayList<RoadWorks>();

    //This method will parse the xml and return it in a list it also takes in a string which will be the url of xml feed
    public List<RoadWorks> parseData(String rssdata)
    {
        //Decalring Strings
        String R_title = null;
        String R_description = null;
        String R_georsspoint = null;
        String newtitle=null;
        String newpoint=null;
        String newdesc=null;
        //Try and catch dor the code below
        try
        {
            //creating a new instance of XmlPullParserFactory
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            //Setting the parser to be aware of namespaces as one of the tag in xml feed contains a namespace
            factory.setNamespaceAware(true);
            //creating a new instance of pullparser and ake it equal to the factory new pullparser
            XmlPullParser xpp = factory.newPullParser();
            //setting the input to be a string using a string reader
            xpp.setInput(new StringReader(rssdata));
            //Again setting it to process namespaces
            xpp.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, true);
            int eventType = xpp.getEventType();
           //Creating a road object
            RoadWorks myroadobj ;
            //While Loop which will run till the xml feed is finished
            while (eventType != XmlPullParser.END_DOCUMENT)
            {

                // if the start tag si found
                if(eventType == XmlPullParser.START_TAG)
                {
                    //Create/initialise  a new road object which is declared above using an overloaded constructor
                    myroadobj= new RoadWorks( newtitle,newdesc,newpoint);
                      // Check which Tag we have
                    if (xpp.getName().equalsIgnoreCase("title"))
                    {
                        //Checking to see if the road object is null
                        if(myroadobj.equals(null)){

                        }
                        //Otherwise add it to the list
                        else
                        {
                        mylist.add(myroadobj);
                        }
                        // Now just get the associated text
                        R_title = xpp.nextText();
                        // Do something with text
                       if( R_title.equals("Traffic Scotland - Roadworks"))
                        {
                        }
                        else {
                            Log.e("MyTag", "title is  " + R_title);
                           newtitle = R_title;

                        }
                    }
                    else
                        // Check which Tag we have
                        if (xpp.getName().equalsIgnoreCase("description"))
                        {
                            // Now just get the associated text
                            R_description = xpp.nextText();
                            if( R_description.equals("Roadworks currently being undertaken on the road network."))
                            {

                            }
                            else {
                                // Do something with text
                                 newdesc = R_description;
                                Log.e("MyTag", "Desc is  " + R_description);
                               // String nd= myroadobj.setdescription(R_description);
                            }
                        }
                        else
                            // Check which Tag we have
                            if (xpp.getName().equalsIgnoreCase("point"))
                            {
                               R_georsspoint = xpp.nextText();
                                //Check if this is null
                                if( R_georsspoint.equals("null"))
                                {
                                }
                                else {
                                    // Do something with text
                                     Log.e("MyTag", "Point is  " + R_georsspoint);
                                     newpoint = R_georsspoint;
                                }
                            }
                }
                // Get the next event
                eventType = xpp.next();
            } // End of while

            mylist.removeAll(Collections.singleton(null));
            for(int i=0; i<mylist.size();i++){
                Log.e("MyTag", "List is  " + mylist.get(i));
            }

        }
        //Catch Exception for pullparser
        catch (XmlPullParserException ae1)
        {
            Log.e("MyTag","Parsing error" + ae1.toString());
        }
        catch (IOException ae1)
        {
            Log.e("MyTag","IO error during parsing");
        }

        Log.e("MyTag", "End document");
        //Return the list of objects
                   return mylist;
    }


}
