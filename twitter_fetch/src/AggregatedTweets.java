import java.util.Date;

public class AggregatedTweets {
	private Date date;
	private int count;
	
	// Constructors
	public AggregatedTweets(Date d, int c) {
		date = d;
		count = c;
	}

	public AggregatedTweets() {
		count = 1;
	}
	
	//Setters and Getters
	public void setDate(Date d) {
		date = d;
	}
	
	public void setCount(int c) {
		count = c;
	}
	
	public Date getDate() {
		return date;
	}
	
	public int getCount() {
		return count;
	}
	
}
