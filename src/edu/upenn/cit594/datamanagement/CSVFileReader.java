package edu.upenn.cit594.datamanagement;

import java.util.*;
import org.json.simple.parser.ParseException;
import java.io.*;

public class CSVFileReader implements Reader<String, Double> {
	protected String fileName;

	public CSVFileReader(String name) {
		fileName = name;
	}

	public Map<String, Double> buildAMap() throws FileNotFoundException, IOException, ParseException {
		Map<String, Double> totalFineEachZip = new HashMap<String, Double>();
		Scanner in = null;
		String line = "";

		try {
			File file = new File(fileName);
			assert (file.exists());
			in = new Scanner(file);
			while (in.hasNextLine()) {
				line = in.nextLine();
				String[] info = line.split(",");
				String aFine = info[1];
				String zip = info[6];
				double aFineNum = Double.parseDouble(aFine);

				if (totalFineEachZip.get(zip) == null) {
					totalFineEachZip.put(zip, aFineNum);
				} else {
					totalFineEachZip.put(zip, totalFineEachZip.get(zip) + aFineNum);
				}
			}
		} catch (Exception e) {
			throw new IllegalStateException(e);
		} finally {
			if (in != null) {
				in.close();
			}
		}
		return totalFineEachZip;
	}
}
