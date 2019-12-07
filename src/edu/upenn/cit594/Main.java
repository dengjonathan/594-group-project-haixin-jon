package edu.upenn.cit594;

import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.processor.Processor;
import edu.upenn.cit594.ui.CommandLineUserInterface;

//  The purpose of this class is to create the other objects and their relationships, then start the application via the UI.
public class Main {

	public static void main(String[] args) throws Exception {
		if (args.length != 5) {
			System.out.println("Wrong input");
			System.exit(0);
		}

		String parkingViolationFileFormat = args[0];
		String parkingViolationFileName = args[1];
		String propertyValueFileName = args[2];
		String populationFileName = args[3];
		String logFile = args[4];
		// log program instantiation with runtime args
		Logger.setFileName(logFile);
		Logger.getInstance().log(parkingViolationFileFormat + " " + parkingViolationFileName + " " + propertyValueFileName + " " + logFile);

		if (!parkingViolationFileFormat.equals("csv") && parkingViolationFileName.indexOf(".csv") < 0) {
			System.out.println("Wrong input");
			System.exit(0);
		}

		System.out.println("Initiating the program, please wait...");

		Processor processor = new Processor(parkingViolationFileName, propertyValueFileName, populationFileName);

		System.out.println("Initiating finished.");
		CommandLineUserInterface ui = new CommandLineUserInterface(processor);
		ui.start();
		ui.display();
	}
}