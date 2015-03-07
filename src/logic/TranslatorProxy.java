package logic;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.Header;
import org.json.JSONObject;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class TranslatorProxy {

	// fields
	private final String API_URL = "http://api.mymemory.translated.net";	
	private AsyncHttpClient client = new AsyncHttpClient();
	List<ITranslatorListener> listeners;
	
	// events
	public TranslatorProxy() {
		listeners = new ArrayList<ITranslatorListener>();
	}
	
	public void addListener(ITranslatorListener listener) {
		listeners.add(listener);
	}
	
	private void translationLoaded(String translation) {
		for (ITranslatorListener translatorListener : listeners) {
			translatorListener.onTranslationLoaded(translation);
		}
	}
	
	private void loadFailed() {
		for (ITranslatorListener translatorListener : listeners) {
			translatorListener.onLoadFailed();
		}
	}
	
	private void failedConnection() {
		for (ITranslatorListener translatorListener : listeners) {
			translatorListener.onFailedConnection();;
		}
	}
	
	
	// methods
	public void getTranslation(String requestWord) throws Exception {
		
		requestWord = URLEncoder.encode(requestWord,"UTF8");
		String path = "";
		
		if (requestWord.matches("[a-zA-Z\\s]+")) {
				path = API_URL + "/get?q=" + requestWord + "&langpair=en%7Cru";
		}
		else
		{
			path = API_URL + "/get?q=" + requestWord + "&langpair=ru%7Cen";
		} 
		
		client.get(path, new AsyncHttpResponseHandler() {

		    @Override
		    public void onSuccess(int statusCode, Header[] headers, byte[] response) {
		    		JSONObject obj;
					try {
						obj = new JSONObject(new String(response, "UTF-8"));
						String translation = obj.getJSONObject("responseData").getString("translatedText");
						translationLoaded(translation);
					} catch (Exception e) {
						loadFailed();
					} 		    		
		    }

		    @Override
		    public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
		    	failedConnection();
		    }
		});			
	}
}
