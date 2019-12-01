package edu.upenn.cit594;
import edu.upenn.cit594.datamanagement.Reader;
import edu.upenn.cit594.datamanagement.CSVFileReader;
import edu.upenn.cit594.datamanagement.JSONFileReader;
import edu.upenn.cit594.datamanagement.TextFileReader;
import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.processor.Processor;
import edu.upenn.cit594.ui.CommandLineUserInterface;

//  The purpose of this class is to create the other objects and their relationships, then start the application via the UI.
public class Main {

	public static void main(String[] args) throws Exception {
	    Reader<String, Double> reader = null;
	    
	    if (args.length != 5) {
    		System.out.println("Wrong input");
    		System.exit(0);
    	}
	    
		String parkingViolationFileFormat = args[0];
    	String parkingViolationFileName = args[1];
    	String propertyValueFileName = args[2];
    	String populationFileName = args[3];
    	String logFile = args[4];
   
        if (!parkingViolationFileFormat.equals("csv") && !parkingViolationFileFormat.equals("json")) {
    		System.out.println("Wrong input");
    		System.exit(0);
    	}
    	
    	if (parkingViolationFileFormat.equals("csv")){
    		reader = new CSVFileReader(parkingViolationFileName);
    	}
    	else if (parkingViolationFileFormat.equals("json")) {
    		reader = new JSONFileReader(parkingViolationFileName);
    	}
    	else {
    		throw new RuntimeException("unrecognized format suffix");
    	}
    	
    	Logger.setFileName(logFile);
		Processor processor = new Processor(reader, propertyValueFileName, populationFileName);
    	CommandLineUserInterface ui = new CommandLineUserInterface(processor);
    	ui.start();
    	ui.display();	    
    }
}