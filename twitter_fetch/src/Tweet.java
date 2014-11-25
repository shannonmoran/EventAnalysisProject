import java.util.Date;

public class Tweet {
	private String ScreenName;
	private String text;
	private Date created;
	
	// Constructor
	public Tweet(String s, String t, Date d) {
		ScreenName = s;
		text = t;
		created = d;
	}
	
	//Setters and Getters
	public void setScreenName(String s) {
		ScreenName = s;
	}
	
	public void setText(String t) {
		text = t;
	}
	
	public void setCreated(Date d) {
		created = d;
	}
	
	public String getScreenName() {
		return ScreenName;
	}
	
	public String getText() {
		return text;
	}
	
	public Date getCreated() {
		return created;
	}
	
}
