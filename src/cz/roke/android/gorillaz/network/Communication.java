package cz.roke.android.gorillaz.network;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.net.URLConnection;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class Communication {

	private static BufferedReader postJsonAndReceiveAnswer(String url, JsonObject json) throws IOException {
		URLConnection connection = new URL(url).openConnection();
		connection.setDoOutput(true);
		connection.setRequestProperty("Content-Type", "application/json");

		String content = new Gson().toJson(json);
		//Log.d(TAG, "Send to the " + url + ": " + content);

		Writer post = new OutputStreamWriter(connection.getOutputStream());
		post.write(content);
		post.close();
		connection.connect();

		return new BufferedReader(new InputStreamReader(new DataInputStream(connection.getInputStream())));
	}
	
}
