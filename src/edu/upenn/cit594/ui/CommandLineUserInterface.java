package edu.upenn.cit594.ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.Collections;

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

	public void start() throws Exception {
		prompt();
		int choice = in.nextInt();

		if (choice == 0) {
			System.exit(0);
		}
		
		while (choice != 0) {
			if (choice == 1) {
				System.out.println(totalPopulation());
			} else if (choice == 2) {
				System.out.println(totalParkingFinesPerCapita());
			} else if (choice == 3) {
				System.out.println(averageMarketValueForResidences());
			} else if (choice == 4) {
				System.out.println(averageLivableAreaForResidences());
			} else if (choice == 5) {
				System.out.println(totalMarketValuePerCapita());
			} else if (choice == 6) {
				// TO DO: custom feature
			} else if (choice != 0){
				System.out.println("Invalid input");
			}
			prompt();
			choice = in.nextInt();
		}
		in.close();
	}

	public void prompt() {
		System.out.println("Enter 0 to exit the program, 1 to the total population for all ZIP Codes,\n"
				+ "2 to show the total parking fines per capita for each ZIP Code,\n"
				+ "3 to show the average market value for residences in a specified ZIP Code,\n"
				+ "4 to show the average total livable area for residences in a specified ZIP Code,\n"
				+ "5 to show the total residential market value per capita for a specified ZIP Code,\n"
				+ "6 to show the results of your custom feature");
	}
	
	public void display() {
		// TO DO
	}

	public int totalParkingFinesPerCapita() {
		// TO DO
		return 1;
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
