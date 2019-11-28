package edu.upenn.cit594.datamanagement;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import edu.upenn.cit594.datamanagement.Reader;

// This class implements Reader interface. 
// It handles the reading of the tweets JSON file and exposes a method to be used by the Processor to get the data.
public class JSONFileReader implements Reader<String, String> {
    protected String fileName;

	public JSONFileReader(String name) {
		fileName = name;
	}

	@Override
	public Map<String, String> buildAMap() throws Exception 
	{
		JSONParser parser = new JSONParser();

		Map<String, String> locationTweetsMap = new HashMap<String, String>();

		JSONArray tweets = (JSONArray)parser.parse(new FileReader(fileName));

		Iterator iter = tweets.iterator();

		while (iter.hasNext()) {
			JSONObject entry = (JSONObject) iter.next();
			JSONArray location = (JSONArray) entry.get("location");
			double latitude = (double)location.get(0);
			double longitude = (double)location.get(1);
			String locations = latitude + "," + longitude;
			String tweet = (String) entry.get("text");
			locationTweetsMap.put(tweet, locations);
		}
		return locationTweetsMap;
	}
}
