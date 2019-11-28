package edu.upenn.cit594.ui;

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
	
	public void start() {
		System.out.print("Enter 0 to exit the program, 1 to the total population for all ZIP Codes, "
				+ "2 to show the total parking fines per capita for each ZIP Code, "
				+ "3 to show the average market value for residences in a specified ZIP Code, "
				+ "4 to show the average total livable area for residences in a specified ZIP Code, "
				+ "5 to show the total residential market value per capita for a specified ZIP Code, "
				+ "6 to show the results of your custom feature");
	    int choice = in.nextInt();
	    
	  //  if (!validInput(choice)) {
	  //  	System.out.println("Invalid input");
	  //  }
	  
	    if (choice == 0) {
			return; 
		}
	    else if (choice == 1) {
	    	System.out.println(totalPopulationAllZips()); 
		}
		else if (choice == 2) {
			System.out.println(totalParkingFinesPerCapita()); 
		}
		else if (choice == 3) {
			System.out.println(averageMarketValueForResidences()); 
		}
		else if (choice == 4) {
			System.out.println(averageLivableAreaForResidences()); 
		}
		else if (choice == 5) {
			System.out.println(totalMarketValuePerCapita()); 
		}
		else if (choice == 6) {
			//TO DO: custom feature
		}
		else {
			 System.out.println("Invalid input");
		}    
	    in.close();
	}
	
	public void display() {
	    // TO DO
	}
	
	public int totalPopulationAllZips() {
		// TO Do
		return 1;
	} 
	
	public int totalParkingFinesPerCapita() {
		// TO DO
		return 1;
	}
	
	public double averageMarketValueForResidences() {
		System.out.print("Please enter the zip: "); 
		String course = in.next();
		// TO DO
		return 1;
	}

	public double averageLivableAreaForResidences() {
		System.out.print("Please enter the zip: "); 
		String course = in.next();
		// TO DO
		return 1;
	}
	
	public double totalMarketValuePerCapita() {
		System.out.print("Please enter the zip: "); 
		String course = in.next();
	    // TO DO
		return 1;
	}
}
