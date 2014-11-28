import java.util.ArrayList;
import java.util.Date;


public class Event {
	private String name;
	private String headline;
	private Date date;
	private ArrayList<Moment> moments;
	private String overallSentiment;
	
	// Constructors
	public Event(String n, String h, Date d, ArrayList m, String s) {
		setName(n);
		setHeadline(h);
		setDate(d);
		setMoments(m);
		setOverallSentiment(s);
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
	
	// Modified toString method to summarise event
	public String toString() {
		return "Change this toString";
	}
}
