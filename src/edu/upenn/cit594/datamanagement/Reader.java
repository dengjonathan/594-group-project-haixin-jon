package edu.upenn.cit594.datamanagement;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import org.json.simple.parser.ParseException;

// This interface is part of the "Data Management" tier.
// It declares a method to get a map. The implementation is determined by the subclass. 
public interface Reader<K, V> {
	public Map<String, Double> buildAMap() throws Exception;
}
