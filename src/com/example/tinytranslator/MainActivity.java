package com.example.tinytranslator;

import logic.TranslatorProxy;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

	private TranslatorProxy translator;
	
	private EditText RequestET;
	private Button SubmitBT;
	private TextView ResultTV;
	
	private String getRequest()
	{
		return RequestET.getText().toString();
	}

	private Boolean isRequestEmpty()
	{
		return getRequest().isEmpty();
	}

	
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_main);
		RequestET = (EditText) findViewById(R.id.requestET);
		SubmitBT = (Button) findViewById(R.id.submitBT);
		ResultTV = (TextView) findViewById(R.id.resultTV);
		translator = new TranslatorProxy(ResultTV);
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
			showDialog("Enter a word!");
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
	
}