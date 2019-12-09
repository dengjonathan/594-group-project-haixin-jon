package edu.upenn.cit594.datamanagement;

import java.util.*;
import java.util.concurrent.Callable;

import edu.upenn.cit594.data.ZipPropertyData;
import edu.upenn.cit594.logging.Logger;

import java.io.*;

public class PropertyValueCSVFileReader {
	protected String fileName;

	public PropertyValueCSVFileReader(String name) {
		fileName = name;
	}
	
	public Map<String, ZipPropertyData> buildPropertyDataForEachZipMap() throws FileNotFoundException, IOException {
		Map<String, ZipPropertyData> propertyDataMap = new HashMap<>();
		Scanner in = null;
		String line = "";
		String zip = "";
		ZipPropertyData zipData;
		int zipIndex = 0;
		int marketValueIndex = 0;
		int livableAreaIndex = 0;

		try {
			File file = new File(fileName);
			assert (file.exists());
			in = new Scanner(file);
			// call log
			Logger.getInstance().log(fileName);
			
			String[] header = in.nextLine().split(",");
			String livableArea;
			double livableAreaNum;
			String marketValue;
			double marketValueNum;

			for (int i = 0; i < header.length; i++) {
				if (header[i].equalsIgnoreCase("zip_code")) {
					zipIndex = i;
				}
				if (header[i].equalsIgnoreCase("total_livable_area")) {
					livableAreaIndex = i;
				}
				if (header[i].equalsIgnoreCase("market_value")) {
					marketValueIndex = i;
				}
			}
			
			while (in.hasNextLine()) {
				line = in.nextLine();
			
				try {
					String[] info = line.split(",");
					zip = info[zipIndex].substring(0, 5);

					if (!propertyDataMap.containsKey(zip)) {
						zipData = new ZipPropertyData();
						propertyDataMap.put(zip,  zipData);
					} else {
						zipData = propertyDataMap.get(zip);
					}

					try {
						marketValue = info[marketValueIndex];
						marketValueNum = Double.parseDouble(marketValue);
						zipData.addMarketValue(marketValueNum);
					} catch (Exception e) {
						// skip the invalid market value, but process the rest of the row
					}

					try {
						livableArea = info[livableAreaIndex];
						livableAreaNum = Double.parseDouble(livableArea);
						zipData.addLivableArea(livableAreaNum);

					} catch (Exception e) {
						// skip the invalid livable area data, but process the rest of the row
					}

				} catch (Exception e) {
					// skip the whole row if zip input is invalid
					continue;
				}
			}
		} catch (Exception e) {
			throw new IllegalStateException(e);
		} finally {
			if (in != null) {
				in.close();
			}
		}
		return propertyDataMap;
	}
}
