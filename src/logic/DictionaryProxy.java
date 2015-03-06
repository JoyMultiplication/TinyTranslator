package logic;

import java.io.UnsupportedEncodingException;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class DictionaryProxy implements IDictionaryProxy {

	private AsyncHttpClient client = new AsyncHttpClient();
	private String responseString;
	
	private void setResponse(String response)
	{
		responseString = response;
	}
	
	@Override
	public String getTranslation(String request) {
		
		StringBuilder responseString = new StringBuilder();
		
		if (request.matches("[a-zA-Z\\s]+")) {
			
			client.get("http://api.mymemory.translated.net/get?q=cat&langpair=en%7Cru", new AsyncHttpResponseHandler() {

			    @Override
			    public void onStart() {
			    }

			    @Override
			    public void onSuccess(int statusCode, Header[] headers, byte[] response) {
			        // called when response HTTP status is "200 OK"
			    	
			    		JSONObject obj;
						try {
							obj = new JSONObject(new String(response, "UTF-8"));
							setResponse(obj.getJSONObject("responseData").getString("translatedText"));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 		    		
			    }

			    @Override
			    public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
			    	tv.setText("fail");
			    }

			    @Override
			    public void onRetry(int retryNo) {
			        // called when request is retried
				}
			});
			
		}
		
			
		
		return null;
	}

}
