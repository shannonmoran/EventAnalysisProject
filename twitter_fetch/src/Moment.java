import java.util.ArrayList;
import java.util.Date;


public class Moment {
	private String type;
	private Date date;
	private int sentiment;
	private String team;
	
	// Constructors
	public Moment(String t, Date d, int s, String teamname) {
		type = t;
		date = d;
		sentiment = s;
		team = teamname;
	}

	// Getters and Setters
	public void setType(String t) {
		type = t;
	}
	
	public void setDate(Date d) {
		date = d;
	}
	
	public void setSentiment(int s) {
		sentiment = s;
	}
	
	public Date getDate() {
		return date;
	}
	
	public String getType() {
		return type;
	}
	
	public int getSentiment() {
		return sentiment;
	}
	
	// Modified toString method to summarise moment
	public String toString() {
		String teamString = "Team: "+team;
		String sentimentString = "Sentiment: ";
		
		// Personalise message based upon moment type
		if (type.equals("goal"))
			teamString = "Goal Scored By: " + team;
		
		// Personalise message based upon sentiment type
		if (sentiment <= 25 && sentiment >= -25) {
			sentimentString += "Neutral";
		} else if (sentiment > 25 && sentiment <= 100) {
			sentimentString += "Positive";
		} else if ( sentiment > 100) {
			sentimentString += "Extremely Positive";
		} else if (sentiment < -25 && sentiment >= -100) {
			sentimentString += "Negative";
		} else if ( sentiment < -100) {
			sentimentString += "Extremely Negative";
		}
			
		return "Event Type: " + type + "\n"+ teamString + "\nTime: " + date.toString() + "\n" + sentimentString;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}
}
