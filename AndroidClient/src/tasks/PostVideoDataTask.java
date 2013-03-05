package tasks;

import java.io.IOException;

import depreciated.HttpClient;

import models.Annotation;
import models.Video;
import android.os.AsyncTask;

public class PostVideoDataTask extends AsyncTask<Video, Void, String>{

	@Override
	protected String doInBackground(Video... params) {
		try {
			String result = HttpClient.post("api/video", params[0], 1);
			return result;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
