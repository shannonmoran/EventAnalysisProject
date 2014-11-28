import java.util.ArrayList;
import java.util.Date;


public class Event {
	private String name;
	private String homeTeam;
	private String awayTeam;
	private String headline;
	private Date date;
	private ArrayList<Moment> moments;
	private String overallSentiment;
	
	// Constructors
	public Event(String n, String h, Date d, ArrayList m, String s, String ht, String at) {
		setName(n);
		setHeadline(h);
		setDate(d);
		setMoments(m);
		setOverallSentiment(s);
		setHomeTeam(ht);
		setAwayTeam(at);
	}

	public Event(String n, String ht, String at) {
		setName(n);
		setHomeTeam(ht);
		setAwayTeam(at);
	}
	
	// Getters and Setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHeadline() {
		return headline;
	}

	public void setHeadline(String headline) {
		this.headline = headline;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public ArrayList<Moment> getMoments() {
		return moments;
	}

	public void setMoments(ArrayList<Moment> moments) {
		this.moments = moments;
	}

	public String getOverallSentiment() {
		return overallSentiment;
	}

	public void setOverallSentiment(String overallSentiment) {
		this.overallSentiment = overallSentiment;
	}

	public String getHomeTeam() {
		return homeTeam;
	}

	public void setHomeTeam(String homeTeam) {
		this.homeTeam = homeTeam;
	}

	public String getAwayTeam() {
		return awayTeam;
	}

	public void setAwayTeam(String awayTeam) {
		this.awayTeam = awayTeam;
	}
	
	//TODO Modified toString method to summarise event
	public String toString() {
		return "Change this toString";
	}
}
