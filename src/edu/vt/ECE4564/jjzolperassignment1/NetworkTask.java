package edu.vt.ECE4564.jjzolperassignment1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import android.os.AsyncTask;
import android.util.Log;

class NetworkTask extends AsyncTask<String, Place, String>
{

	protected String doInBackground(String... searchquery)
	{

		String toReturn = "";
		
		toReturn = queryGooglePlaceSearch(searchquery[0], searchquery[1]);
		
		return toReturn;

	}
	
	protected void onPostExecute(String XMLToParseStr)
	{

		final NetworkTask2 myTask = new NetworkTask2();
		
		myTask.execute(XMLToParseStr);

	}
	
	// This function takes the current values of the member variables
	// It then executes the HTTP query against Google Places
	public static String queryGooglePlaceSearch(String searchQlocation, String searchQradius)
	{
		
		String temp = "";
		
		try
		{
			
			searchQlocation = searchQlocation.replace(" ", "");
			HttpClient client = new DefaultHttpClient();
			Log.i("!", "HTTPClient");
		    String queryRequestURL = "https://maps.googleapis.com/maps/api/place/nearbysearch/xml?location=" + searchQlocation.replaceAll("\n", "") + "&radius=" + searchQradius.replaceAll("\n", "") + "&types=cafe&sensor=false&key=AIzaSyAdOcXxrIz0YOO8LS6Sy0Djb7GDFz6w2w4";
		    Log.i("!", "HTTP URL");
		    Log.i("!", queryRequestURL.toString());
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
		    String finalStringResultToParse = builder.toString();
		    Log.i("!", "XML Response in a String");
		    
		    temp = finalStringResultToParse;
		    
		}
		catch (IOException e)
		{
			
			// The query failed, and has errors.
			e.printStackTrace();
			Log.i("!", e.toString());
			
		}
		
		return temp;
		
	}

}
