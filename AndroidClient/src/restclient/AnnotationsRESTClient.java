package restclient;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class AnnotationsRESTClient {

	//private static final String BASE_URL = "http://ter-server.cloudfoundry.com/";
	private static final String BASE_URL = "http://91.121.65.184:9000/";

	private static AsyncHttpClient client = new AsyncHttpClient();

	public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
		client.get(getAbsoluteUrl(url), params, responseHandler);
	}

	public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
		client.post(getAbsoluteUrl(url), params, responseHandler);
	}
	
	public static void delete(String url, AsyncHttpResponseHandler responseHandler) {
		client.delete(url, responseHandler);
	}


	private static String getAbsoluteUrl(String relativeUrl) {
		return BASE_URL + relativeUrl;
	}



}
