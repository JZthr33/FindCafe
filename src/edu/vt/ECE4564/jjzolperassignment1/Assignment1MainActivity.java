package edu.vt.ECE4564.jjzolperassignment1;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Assignment1MainActivity extends Activity {
	private static Button startButton_;
	private static EditText inputLocation_;
	private static EditText inputRadius_;
	static TextView appLabel_;
	static TextView resultingPlaces_;
	//private static String tag = "JJ_Assignment1";
	
	// Instantiate the object here so both the background and on post tasks can access the data
	// that is set in the background task and then updated on the GUI

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_assignment1_main);
		startButton_ = (Button) findViewById(R.id.btn_execute);
		inputLocation_ = (EditText) findViewById(R.id.inputLocation);
		inputRadius_ = (EditText) findViewById(R.id.inputRadius);
		appLabel_ = (TextView) findViewById(R.id.appLabel);
		resultingPlaces_ = (TextView) findViewById(R.id.resulting_places);
		doConcurrencyWithAsyncTask();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.assignment1_main, menu);
		return true;
	}
	
	public static void doConcurrencyWithAsyncTask() {
		
		OnClickListener listener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Get the search query from the UI
				final String searchQueryLocation = inputLocation_.getText().toString();
				final String searchQueryRadius = inputRadius_.getText().toString();

				final NetworkTask myTask = new NetworkTask();
				
				myTask.execute(searchQueryLocation, searchQueryRadius);

			}
			
		};
		
		startButton_.setOnClickListener(listener);

	}
	
}
