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
			System.out.println("Program ends");
		}
		
		while (choice != 0) {
			if (choice == 1) {
				totalPopulation();
			} else if (choice == 2) {
			    totalParkingFinesPerCapita();
			} else if (choice == 3) {
				averageMarketValueForResidences();
			} else if (choice == 4) {
				averageLivableAreaForResidences();
			} else if (choice == 5) {
				totalMarketValuePerCapita();
			} else if (choice == 6) {
				// TO DO: custom feature
			} else if (choice != 0){
				System.out.println("Invalid input");
			}
			System.out.println();
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

	public void totalPopulation() throws Exception {
		System.out.println("The total population for all zip codes are " + processor.totalPopulationAllZips());
	}
	public void averageMarketValueForResidences() throws FileNotFoundException, IOException {
		System.out.println("Please enter the zip: ");
		String zip = in.next();
		System.out.println("The average market value for residences in " + zip + " is " + processor.averageMarketValueInOneZip(zip));
	}

	public void averageLivableAreaForResidences() throws FileNotFoundException, IOException {
		System.out.println("Please enter the zip: ");
		String zip = in.next();
		System.out.println("The average total livable area for residences in " + zip + " is " + processor.averageLivableAreaInOneZip(zip));
	}

	public void totalMarketValuePerCapita() throws Exception {
		System.out.println("Please enter the zip: ");
		String zip = in.next();
		System.out.println("The total residential market value per capita in " + zip + " is " + processor.marketValuePerCapitaInOneZip(zip));
	}
}
