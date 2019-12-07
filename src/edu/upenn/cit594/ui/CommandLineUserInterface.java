package edu.upenn.cit594.ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.Collections;

import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.processor.Processor;

// User interface tier: interact with the user and process the data
// Only depends on the processor tier, not the data tier
public class CommandLineUserInterface {
	protected Processor processor;
	protected Scanner in;

	public CommandLineUserInterface(Processor processor) {
		this.processor = processor;
		in = new Scanner(System.in);
	}

	public void start() {
		try {
			recordUserChoice();
		} catch (Exception e) {
			// in this case something in the user script failed
			// we should try to just exit
			in.close();
		}
	}

	private void recordUserChoice() throws Exception {
		prompt();
		int choice = in.nextInt();

		Logger.getInstance().log("user choice: " + choice);

		if (choice == 0) {
			Logger.getInstance().close();
			System.exit(0);
		}

		if (choice != 0) {
			if (choice == 1) {
				System.out.println(totalPopulation());
			} else if (choice == 2) {
				totalParkingFinesPerCapita();
			} else if (choice == 3) {
				System.out.println(averageMarketValueForResidences());
			} else if (choice == 4) {
				System.out.println(averageLivableAreaForResidences());
			} else if (choice == 5) {
				System.out.println(totalMarketValuePerCapita());
			} else if (choice == 6) {
				totalParkingFinesPerCapitaThreshold();
			} else if (choice != 0){
				System.out.println("Invalid input");
			}
			recordUserChoice();
		}
		in.close();
	}

	private void prompt() {
		System.out.println("Enter 0 to exit the program,\n1 to the total population for all ZIP Codes,\n"
				+ "2 to show the total parking fines per capita for each ZIP Code,\n"
				+ "3 to show the average market value for residences in a specified ZIP Code,\n"
				+ "4 to show the average total livable area for residences in a specified ZIP Code,\n"
				+ "5 to show the total residential market value per capita for a specified ZIP Code,\n"
				+ "6 to show the total parking fines per capita for each ZIP Code filtered by threshold market value.");
	}
	
	public void display() {
		// TO DO
	}

	public void totalParkingFinesPerCapita() {
		List<String> lines = processor.totalFinesPerCapita();
		for (String line: lines) {
			System.out.println(line);
		}
	}

	public void totalParkingFinesPerCapitaThreshold() {
		System.out.println(
				"We will display only parking fines per capita for zip codes that meet your threshold average market value."
		);
		System.out.println(
				"This feature can be used to query if average market value of housing affects the amount of parking fines given per capita."
		);
		System.out.println(
				"Please enter a dollar amount for the average market value threshold: "
		);
		double threshold;
		try {
			String dollarAmount = in.next();
			threshold = Double.parseDouble(dollarAmount);
			List<String> lines = processor.totalFinesPerCapitaThreshold(threshold);
			for (String line: lines) {
				System.out.println(line);
			}
		} catch (Exception e) {
			System.out.println("Invalid threshold amount entered. Please try again.");
			totalParkingFinesPerCapitaThreshold();
		}
	}

	public double totalPopulation() throws Exception {
		return (processor.totalPopulationAllZips());
	}

	public double averageMarketValueForResidences() throws FileNotFoundException, IOException {
		System.out.println("Please enter the zip: ");
		String zip = in.next();
		return (processor.averageMarketValueInOneZip(zip));

	}

	public double averageLivableAreaForResidences() throws FileNotFoundException, IOException {
		System.out.println("Please enter the zip: ");
		String zip = in.next();
		return (processor.averageLivableAreaInOneZip(zip));
	}

	public double totalMarketValuePerCapita() throws Exception {
		System.out.println("Please enter the zip: ");
		String zip = in.next();
		return (processor.marketValuePerCapitaInOneZip(zip));
	}
}
