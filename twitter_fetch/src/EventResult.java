import java.util.ArrayList;
import java.util.Date;


public class EventResult {
	private String type;
	private Date date;
	private String sentiment;
	
	// Constructors
	public EventResult(String t, Date d, String s) {
		type = t;
		date = d;
		sentiment = s;
	}

	// Getters and Setters
	public void setType(String t) {
		type = t;
	}
	
	public void setDate(Date d) {
		date = d;
	}
	
	public void setSentiment(String s) {
		sentiment = s;
	}
	
	public Date getDate() {
		return date;
	}
	
	public String getType() {
		return type;
	}
	
	public String getSentiment() {
		return sentiment;
	}
}
