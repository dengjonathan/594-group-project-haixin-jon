package edu.upenn.cit594.datamanagement;

import java.io.FileReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import edu.upenn.cit594.data.Violation;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class ViolationJSONFileReader extends ViolationFileReader {
    public List<Violation> parse(String filename) {
        JSONParser parser = new JSONParser();
        try {
            FileReader fr = new FileReader(filename);
            JSONArray violations = (JSONArray)parser.parse(fr);
            Iterator it = violations.iterator();
            List<Violation> result = new ArrayList<>();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
            while (it.hasNext()) {
                JSONObject violationJSON = (JSONObject)it.next();
                int ticketNumber = (int)violationJSON.get("ticket_number");
                String plateId = (String)violationJSON.get("plate_id");
                Date date = dateFormat.parse((String)violationJSON.get("date"));
                String zip = (String)violationJSON.get("zip_code");
                String description = (String)violationJSON.get("violation");
                int fine = (int)violationJSON.get("fine");
                String state = (String)violationJSON.get("state");
                Violation violation = createViolation(ticketNumber, plateId, date, zip, description, fine, state);
                if (violation != null) {
                    result.add(violation);
                }
            }
            return result;
        } catch (Exception e) {
            System.out.println("Exception thrown opening file");
        }
        return null;
    }
}
