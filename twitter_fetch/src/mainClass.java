import java.io.IOException;

import twitter4j.TwitterException;

public class mainClass {
	// Bob testing his git in the third person
	public static TwitterClient tw;
	
	public static void search(String qstr) {
		try {
			tw.startSeachAPI(qstr); 
			} catch (TwitterException e) {
				// TODO Auto-generated catch block 
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block 
				e.printStackTrace(); 
			}
	}
	
	public static void userTimeline(String user) {
		try {
			tw.getUserTimeLine(user); 
		} catch (TwitterException e) {
			// TODO Auto-generated catch block e.printStackTrace(); }
		}
	}
	
	public static void tweet(String tweetContent, String user) {
		try {
			tw.postTweet(tweetContent, user);
		} catch (TwitterException e) {
			// TODO Auto-generated catch block e.printStackTrace(); }
		}
	}
	
	/** * @param args */ 
	public static void main(String[] args) {
		// Authentication keys and tokens for the app registered as 'EventAnalysisProject'
		
		// Fill in the following fields
		String CONSUMER_KEY = "________________________";
		String CONSUMER_KEY_SECRET = "_______________________";
		String ACCESS_TOKEN = "___________________";
		String ACCESS_TOKEN_SECRET = "__________________";
		
		// Initialise TwitterClient with keys and tokens
		tw = new TwitterClient(CONSUMER_KEY, CONSUMER_KEY_SECRET, ACCESS_TOKEN, ACCESS_TOKEN_SECRET);
		
		//------------------------------------------------------------------------------------------
		// -----------------------------------Test functionality------------------------------------
		//------------------------------------------------------------------------------------------
		
		search("#SCOIRL"); // NB: case sensitive
		
		//userTimeline("shannmran"); // only recent 200
		
		//tweet("Testing using the Twitter API with Twitter4J - please ignore", "shannmran");
	}
}
