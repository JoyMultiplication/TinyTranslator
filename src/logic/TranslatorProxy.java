package logic;

import java.net.URLEncoder;
import org.apache.http.Header;
import org.json.JSONObject;
import android.widget.TextView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class TranslatorProxy{

	private AsyncHttpClient client = new AsyncHttpClient();
	private TextView responseTV;
	
	private void setResponse(String response)
	{
		responseTV.setText(response);
	}
	
	public TranslatorProxy(TextView responseTV) {
		this.responseTV = responseTV;
	}
	
	
	public void getTranslation(String requestWord) throws Exception {
		
		requestWord = URLEncoder.encode(requestWord,"UTF8");
		String path = "";
		
		if (requestWord.matches("[a-zA-Z\\s]+")) {
				path = "http://api.mymemory.translated.net/get?q=" + requestWord + "&langpair=en%7Cru";
		}
		else
		{
			path = "http://api.mymemory.translated.net/get?q=" + requestWord + "&langpair=ru%7Cen";
		}
		
		client.get(path, new AsyncHttpResponseHandler() {

		    @Override
		    public void onSuccess(int statusCode, Header[] headers, byte[] response) {
		    		JSONObject obj;
					try {
						obj = new JSONObject(new String(response, "UTF-8"));
						String re = obj.getJSONObject("responseData").getString("translatedText");
						TranslatorProxy.this.setResponse(re);
					} catch (Exception e) {
						setResponse("Something went wrong :(");
					} 		    		
		    }

		    @Override
		    public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
		    	setResponse("There is no internet connection :(");
		    }
		});			
	}

}
