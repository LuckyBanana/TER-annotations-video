package com.annotations.client;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import models.Annotation;
import models.Video;

public class HttpClient {
	
	public static final String BASE_URL = "http://url_du_server/";
	
	
	/*
	 * POST Video
	 * mode = 0 : hors connexion (data + stream + annotations)
	 * mode = 1 : data only
	 * mode = 2 : stream only
	 */
	public static void post(String path, Video donnees, int mode) throws IOException {
		System.setProperty("http.keepAlive", "false");
		URL url = new URL(BASE_URL+path);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		
		String boundary = Long.toHexString(System.currentTimeMillis()); // Just generate some unique random value.
		String CRLF = "\r\n"; // Line separator required by multipart/form-data.
		String charset = "UTF-8";

		connection.setDoOutput(true);
		connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
		PrintWriter writer = null;
		try {
		    OutputStream output = connection.getOutputStream();
		    writer = new PrintWriter(new OutputStreamWriter(output, charset), true); // true = autoFlush, important!
		    
		    switch (mode) {
		    
		    	case 1 :
		    		// Send normal param.
				    writer.append("--" + boundary).append(CRLF);
				    writer.append("Content-Disposition: form-data; name=\"nom\"").append(CRLF);
				    writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
				    writer.append(CRLF);
				    writer.append(donnees.getNom()).append(CRLF).flush();
				    break;
		    	case 2 :
		    		// Send binary file.
				    writer.append("--" + boundary).append(CRLF);
				    writer.append("Content-Disposition: form-data; name=\"stream\"; filename=\"" + donnees.getStream().getName() + "\"").append(CRLF);
				    writer.append("Content-Type: " + URLConnection.guessContentTypeFromName(donnees.getStream().getName())).append(CRLF);
				    writer.append("Content-Transfer-Encoding: binary").append(CRLF);
				    writer.append(CRLF).flush();
				    InputStream input = null;
				    try {
				        input = new FileInputStream(donnees.getStream());
				        byte[] buffer = new byte[1024];
				        for (int length = 0; (length = input.read(buffer)) > 0;) {
				            output.write(buffer, 0, length);
				        }
				        output.flush(); // Important! Output cannot be closed. Close of writer will close output as well.
				    } finally {
				        if (input != null) try { input.close(); } catch (IOException logOrIgnore) {}
				    }
				    writer.append(CRLF).flush(); // CRLF is important! It indicates end of binary boundary.
				    break;
		    }

		    // End of multipart/form-data.
		    writer.append("--" + boundary + "--").append(CRLF);
		} finally {
		    if (writer != null) writer.close();
		}
		
		// Recupérer id --> return string
		

	}
	
	/*
	 * POST Annotation
	 * Video id ?
	 */
	
	public static void post(String path, Annotation donnees) throws IOException {
		System.setProperty("http.keepAlive", "false");
		URL url = new URL(BASE_URL+path);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		String boundary = Long.toHexString(System.currentTimeMillis()); // Just generate some unique random value.
		String CRLF = "\r\n"; // Line separator required by multipart/form-data.
		String charset = "UTF-8";

		connection.setDoOutput(true);
		connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
		PrintWriter writer = null;
		try {
			OutputStream output = connection.getOutputStream();
			writer = new PrintWriter(new OutputStreamWriter(output, charset), true); // true = autoFlush, important!

			// Send normal param.
			writer.append("--" + boundary).append(CRLF);
			writer.append("Content-Disposition: form-data; name=\"nom\"").append(CRLF);
			writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
			writer.append(CRLF);
			writer.append(donnees.getNom()).append(CRLF).flush();
			
			writer.append("--" + boundary).append(CRLF);
			writer.append("Content-Disposition: form-data; name=\"commentaire\"").append(CRLF);
			writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
			writer.append(CRLF);
			writer.append(donnees.getCommentaire()).append(CRLF).flush();
			
			writer.append("--" + boundary).append(CRLF);
			writer.append("Content-Disposition: form-data; name=\"timecodeDebut\"").append(CRLF);
			writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
			writer.append(CRLF);
			writer.append(donnees.getTimecodeDebut()).append(CRLF).flush();
			
			writer.append("--" + boundary).append(CRLF);
			writer.append("Content-Disposition: form-data; name=\"timecodeFin\"").append(CRLF);
			writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
			writer.append(CRLF);
			writer.append(donnees.getTimecodeFin()).append(CRLF).flush();
			
			// End of multipart/form-data.
			writer.append("--" + boundary + "--").append(CRLF);
			
		} finally {
			if (writer != null) writer.close();
		}
	}
	
	/*
	 * GET (list)
	 */
	
	public static String get(String path) throws IOException {
		InputStream is = null;
	    // Only display the first 1000 characters of the retrieved
	    // web page content.
	    int len = 1000; // ?
	        
	    try {
	        URL url = new URL(BASE_URL+path);
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setReadTimeout(10000 /* milliseconds */);
	        conn.setConnectTimeout(15000 /* milliseconds */);
	        conn.setRequestMethod("GET");
	        conn.setDoInput(true);
	        // Starts the query
	        conn.connect();
	        int response = conn.getResponseCode();
	        is = conn.getInputStream();

	        // Convert the InputStream into a string
	        Reader reader = null;
	        reader = new InputStreamReader(is, "UTF-8");        
	        char[] buffer = new char[len];
	        reader.read(buffer);
	        String contentAsString =  new String(buffer);
	        
	        return contentAsString;

	        
	    // Makes sure that the InputStream is closed after the app is
	    // finished using it.
	    } finally {
	        if (is != null) {
	            is.close();
	        } 
	    }
	}
	
	/*
	 * GET 
	 */
	
	public static String getOne(String path, String id) throws IOException {
		InputStream is = null;
	    // Only display the first 1000 characters of the retrieved
	    // web page content.
	    int len = 1000; // ?
	        
	    try {
	        URL url = new URL(BASE_URL+path+"/id="+id); // A vérifier !!
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setReadTimeout(10000 /* milliseconds */);
	        conn.setConnectTimeout(15000 /* milliseconds */);
	        conn.setRequestMethod("GET");
	        conn.setDoInput(true);
	        // Starts the query
	        conn.connect();
	        int response = conn.getResponseCode();
	        is = conn.getInputStream();

	        // Convert the InputStream into a string
	        Reader reader = null;
	        reader = new InputStreamReader(is, "UTF-8");        
	        char[] buffer = new char[len];
	        reader.read(buffer);
	        String contentAsString =  new String(buffer);
	        
	        return contentAsString;

	        
	    // Makes sure that the InputStream is closed after the app is
	    // finished using it.
	    } finally {
	        if (is != null) {
	            is.close();
	        } 
	    }
	}
	
	
	
	
	public static void test() throws IOException {

	}
	
	

}
