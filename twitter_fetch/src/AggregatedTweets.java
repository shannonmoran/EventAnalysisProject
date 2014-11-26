import java.util.ArrayList;
import java.util.Date;

public class AggregatedTweets {
	private Date date;
	private int count;
	private ArrayList<Tweet> tweets;
	
	// Constructors
	public AggregatedTweets(Date d, int c, ArrayList t) {
		date = d;
		count = c;
		tweets = t;
	}

	public AggregatedTweets() {
		count = 0;
	}
	
	// Getters and Setters
	public void setDate(Date d) {
		date = d;
	}
	
	public void setCount(int c) {
		count = c;
	}
	
	public void setTweets(ArrayList t) {
		tweets = t;
	}
	
	public Date getDate() {
		return date;
	}
	
	public int getCount() {
		return count;
	}
	
	public ArrayList getTweets() {
		return tweets;
	}
}
