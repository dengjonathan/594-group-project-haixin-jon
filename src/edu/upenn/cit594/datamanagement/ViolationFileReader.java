package edu.upenn.cit594.datamanagement;

import java.util.Date;
import java.util.List;
import edu.upenn.cit594.data.Violation;

public abstract class ViolationFileReader {
	public ViolationFileReader() {
	}

	protected boolean validateViolation(int ticketNumber, String plateId, Date date, String zip, String description,
			int fine, String state) {
		return ticketNumber > 0 && plateId.length() > 0 && date != null && zip.length() > 0 && state.length() == 2;
	}

	protected Violation createViolation(int ticketNumber, String plateId, Date date, String zip, String description,
			int fine, String state) {
		if (validateViolation(ticketNumber, plateId, date, zip, description, fine, state)) {
			return new Violation(ticketNumber, date, fine, description, plateId, state, zip);
		}
		return null;
	}

	public abstract List<Violation> parse(String filename) throws Exception;
}
