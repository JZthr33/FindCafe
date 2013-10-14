package edu.vt.ECE4564.jjzolperassignment1;
import edu.vt.ECE4564.jjzolperassignment1.Place;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.util.Log;

public class NetworkTask2 extends AsyncTask<String, Place, ArrayList<Place>> {

	protected ArrayList<Place> doInBackground(String... XMLToParseStr)
	{

		ArrayList<Place> toReturn = new ArrayList<Place>();
		
		toReturn = queryGooglePlaceDetail(XMLToParseStr[0]);
			
		return toReturn;

	}
		
	protected void onPostExecute(ArrayList<Place> A)
	{

		Assignment1MainActivity.resultingPlaces_.setText("");
		
		// Iterate over what we collected and update the UI
		for(int i = 0; i < A.size(); i++)
		{

			Log.i("!", "Print Places Result");
			updateUI(A.get(i));
				
		}

	}
		
	public static ArrayList<Place> queryGooglePlaceDetail(String XMLToParseStr)
	{
			
		ArrayList<Place> places1 = new ArrayList<Place>();
		
		places1 = parsePlacesSearch(XMLToParseStr);
		
		ArrayList<Place> places2 = new ArrayList<Place>();
		
		String PlaceReference = "";

		// Start Here with the 2.0
		ArrayList<String> UltimatePlacesResult = new ArrayList<String>();
		String currentplacedetails = "";
		
		for(int ind = 0; ind < places1.size(); ind++)
		{
			
			PlaceReference = places1.get(ind).reference;
			
			try
			{
				
				currentplacedetails = "";
				HttpClient client = new DefaultHttpClient();
				Log.i("!", "HTTPClient");
			    String queryRequestURL = "https://maps.googleapis.com/maps/api/place/details/xml?reference=" + PlaceReference + "&sensor=false&key=AIzaSyAdOcXxrIz0YOO8LS6Sy0Djb7GDFz6w2w4";
			    Log.i("!", "HTTP URL");
			    HttpGet request = new HttpGet(queryRequestURL);
			    Log.i("!", "HTTPRequest");
			    HttpResponse response = client.execute(request);
			    // do something with the response
			    Log.i("!", "HTTPResponse");
				    
			    // Compile the XML from the response
			    BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
			    StringBuilder builder = new StringBuilder();
			    for (String line = null; (line = reader.readLine()) != null;) {
			        builder.append(line).append("\n");
			    }
			    Log.i("!", "Buffered Reader");
			    currentplacedetails = builder.toString();
			    Log.i("!", "XML Response in a String");
			    //Log.i("!", currentplacedetails);
				    
			    // Parse the XML and return the Place objects and put them into the Array List
			    UltimatePlacesResult.add(currentplacedetails);
			    currentplacedetails = "";
			    
			}
			catch (IOException e)
			{
					
				// The query failed, and has errors.
				e.printStackTrace();
				Log.i("!", e.toString());
					
			}

		}
		
		//Log.i("!", UltimatePlacesResult);
		places2 = parsePlacesDetail(places1, UltimatePlacesResult);
		
		// End 2.0
		
		// need to create an array of references based on the places we have that then is called
		// on the query request url and returns xml and each xml for that one place needs to be appended
		// to an overall xml that will ONLY be sent to the parsing method after all responses of xml
		// have been completed, otherwise we are single handedly querying for every single place
		// and parsing just one xml for the one place at a time and that makes it hard to append
		// to a unififed arraylist as well.
		// 1. doing it as is is slow and just plain bad
		// 2. creating a unified arraylist of places will be hard
		// * if i fix it to be more effective, it will also be easier to parse and display at the end game anyways! :)

		return places2;
			
	}
	
	public static ArrayList<Place> parsePlacesSearch(String finalStr) {
		
		ArrayList<Place> toReturn = new ArrayList<Place>();
		
		String[] items = finalStr.split("result");
		Log.i("!", "Split Result");
		
	    for(int j = 0; j < items.length; j++)
	    {
	    	
	    	if(items[j].contains("<name>"))
	    	{
	    	
	    		// Now take the currentitem string, and begin to parse for the individual item
	    		String placename = items[j].substring(items[j].indexOf("<name>") + "<name>".length(), items[j].indexOf("</name>"));
	    		String placevicinity = items[j].substring(items[j].indexOf("<vicinity>") + "<vicinity>".length(), items[j].indexOf("</vicinity>"));
	    		String placereference = items[j].substring(items[j].indexOf("<reference>") + "<reference>".length(), items[j].indexOf("</reference>"));
	    		
	    		Place placeobj = new Place(placename, placevicinity, placereference);
	    		Log.i("!", "New Place Name created: " + placename);
	    		Log.i("!", "New Place Vacinity created: " + placevicinity);
	    		Log.i("!", "New Place Reference created: " + placereference);
	    		toReturn.add(placeobj);
	    		placename = "";
		    	placevicinity = "";
		    	placereference = "";
		    	
		    	
		    }
		    // Finish parsing for this individual item and continue to the next item

	    }
	    
	    return toReturn;
		
	}
	
	public static ArrayList<Place> parsePlacesDetail(ArrayList<Place> places1, ArrayList<String> finalStr) {
		
		ArrayList<Place> places2 = new ArrayList<Place>();
		
		//String[] items;
		
		Log.i("!", "ANSWER");
		
	    for(int i = 0; i < finalStr.size(); i++)
	    {
		
	    	Log.i("!", finalStr.get(i));
	    
	    }
		
	    Log.i("!", "ANSWER");
	    
	    for(int j = 0; j < places1.size(); j++)
	    {
		
			//items = finalStr.get(j).split("result");
			Log.i("!", "Split Result");
			
			//Log.i("!", items[j]);
	    	
	    		// Now take the currentitem string, and begin to parse for the individual item
	    		String placename = finalStr.get(j).substring(finalStr.get(j).indexOf("<name>") + "<name>".length(), finalStr.get(j).indexOf("</name>"));
	    		String placevicinity = finalStr.get(j).substring(finalStr.get(j).indexOf("<vicinity>") + "<vicinity>".length(), finalStr.get(j).indexOf("</vicinity>"));
	    		String placereference = finalStr.get(j).substring(finalStr.get(j).indexOf("<reference>") + "<reference>".length(), finalStr.get(j).indexOf("</reference>"));
	    		String placephonenumber = finalStr.get(j).substring(finalStr.get(j).indexOf("<formatted_phone_number>") + "<formatted_phone_number>".length(), finalStr.get(j).indexOf("</formatted_phone_number>"));
	    		
	    		Place updatedplace = new Place();
	    		// Once I have the place I want to update, do this:
	    		updatedplace.setName(placename);
	    		updatedplace.setVicinity(placevicinity);
	    		updatedplace.setReference(placereference);
	    		updatedplace.setPhoneNumber(placephonenumber);
	    		places2.add(updatedplace);

	    		Log.i("!", "New Place Updated: " + placename);
	    		Log.i("!", "New Place Phone Number created: " + placevicinity);
	    		Log.i("!", "New Place Phone Number created: " + placereference);
	    		Log.i("!", "New Place Phone Number created: " + placephonenumber);

		    // Finish parsing for this individual item and continue to the next item

	    }
	    
	    return places2;
		
	}

	// This function takes the current values of the member variables
	// It then updates the UI based on what the HTTP response returned
	public static void updateUI(Place PlaceA)
	{

		// Update the resulting places text view with the place we found
		Assignment1MainActivity.resultingPlaces_.append("Name: " + PlaceA.name + "\n" +
				 "Vicinity: " + PlaceA.vicinity + "\n" + "Phone Number: " + 
				PlaceA.formatted_phone_number + "\n");

	}
	
}
