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
public class JSONFileReader implements Reader<String, Double> {
	protected String fileName;

	public JSONFileReader(String name) {
		fileName = name;
	}

	@Override
	public Map<String, Double> buildAMap() throws Exception {
		JSONParser parser = new JSONParser();

		// TO DO
		return null;
	}
}
