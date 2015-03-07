package com.example.tinytranslator;

import logic.ITranslatorListener;
import logic.TranslatorProxy;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity implements ITranslatorListener {

	// fields
	private TranslatorProxy translator;	
	private EditText RequestET;
	private TextView ResultTV;
	
	
	// getters/setters
	private String getRequest()
	{
		return RequestET.getText().toString();
	}

	private Boolean isRequestEmpty()
	{
		return getRequest().isEmpty();
	}
	
	// ITranslatorListener implementation
	@Override
	public void onTranslationLoaded(String translation) {
		ResultTV.setText(translation);
	}

	@Override
	public void onLoadFailed() {
		showDialog(this.getResources().getString(R.string.something_wrong));
	}

	@Override
	public void onFailedConnection() {
		showDialog(this.getResources().getString(R.string.no_internet));
	}

	// events handling
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_main);
		RequestET = (EditText) findViewById(R.id.requestET);
		ResultTV = (TextView) findViewById(R.id.resultTV);
		translator = new TranslatorProxy();	
		translator.addListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void onSubmitClick(View view)
	{
		if(isRequestEmpty())
		{
			showDialog(getResources().getString(R.string.enter_a_word));
		}
		else
		{
			try {
				translator.getTranslation(getRequest());
			} catch (Exception e) {
				showDialog(e.getMessage());
			}
		}
	} 

	// methods
	@SuppressWarnings("deprecation")
	private void showDialog(String message)
	{
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setTitle("Warning");
		alertDialog.setMessage(message);
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
			dialog.cancel();
		}
		});
		alertDialog.show();
	}
}