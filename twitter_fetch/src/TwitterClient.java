import java.util.Calendar;
import java.util.Date;
import java.util.List; 
import java.io.*;

import twitter4j.Paging;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.OAuth2Token;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;


public class TwitterClient {
	private String CONSUMER_KEY;
	private String CONSUMER_KEY_SECRET;
	private String ACCESS_TOKEN;
	private String ACCESS_TOKEN_SECRET;
	
	// Constructor
	public TwitterClient(String KEY, String KEY_SECRET, String TOKEN, String TOKEN_SECRET){
		CONSUMER_KEY = KEY;
		CONSUMER_KEY_SECRET = KEY_SECRET;
		ACCESS_TOKEN = TOKEN;
		ACCESS_TOKEN_SECRET = TOKEN_SECRET;
	}
	
	// Search query
	public void startSeachAPI(String qstr) throws TwitterException, IOException{
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		  .setOAuthConsumerKey(CONSUMER_KEY)
		  .setOAuthConsumerSecret(CONSUMER_KEY_SECRET)
		  .setApplicationOnlyAuthEnabled(true);
		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();
		
		OAuth2Token token = twitter.getOAuth2Token();		
	    if (token != null) {
	        System.out.println("Token Type  : " + token.getTokenType());
	        System.out.println("Access Token: " + token.getAccessToken());
	    }
	    
	    // Create a file to store tweets
		File csv = new File("PHIvsDAL.csv");
		
		// Deletes file if already exists
		if(csv.exists()) {
			csv.delete();
		}
		
		csv.createNewFile();
		
		FileWriter fw = new FileWriter(csv);
		BufferedWriter bw = new BufferedWriter(fw);
		
		// Write the headers
		bw.write("User Screen Name,");
		bw.write("Tweet,");
		bw.write("Time Created,");
		bw.newLine();
		
		// Create Query and set variables
	    Query query = new Query(qstr);
		query.setCount(100); // sets the number of tweets to return per page, up to a max of 100
		long id = 533345391362969600L;// We will find all tweets until this I.D is reached. 
		// 533333542491525120L => 7:00 14/11/2014 #SCOIRL
		// 533344008043786241L => 7:41 14/11/2014 #SCOIRL
		// 533345391362969600L => kickoff 14/11/2014 #SCOIRL
		// 533357263311097857L => half time 14/11/2014 #SCOIRL
		// 533360966126542848L => start of 2nd half time 14/11/2014 #SCOIRL
		// 533373399968796672L => full time 14/11/2014 #SCOIRL
		Calendar fromDate = Calendar.getInstance();
		Calendar toDate = Calendar.getInstance();
		fromDate.set(2014, 10, 27, 21, 25);
		toDate.set(2014, 10, 28, 01, 0);
		query.setSince("2014-11-27");
		query.setUntil("2014-11-28");
		
		//query.setSinceId(id);
		QueryResult result = twitter.search(query);
		
		outerloop:
		while(result.hasNext()) {
			// For each Tweet create a new line in file with content of tweet.
		    for (Status status : result.getTweets()) {
		    	if(toDate.after(status.getCreatedAt())) {
			        System.out.println(" @" + status.getUser().getScreenName() + ":" + status.getText() + ":" + status.getCreatedAt() + " " + status.getId());
			        String screenName = status.getUser().getScreenName().replaceAll("\"", "\\\"");
			        screenName = screenName.replaceAll("\n", ". ");
			        screenName = screenName.replaceAll(",", " ");
			        String tweetText = status.getText().replaceAll("\"", "\\\"");
			        tweetText = tweetText.replaceAll("\n", ". ");
			        tweetText = tweetText.replaceAll(",", " ");
					bw.write(screenName + ",");
					bw.write(tweetText + ",");
					bw.write(status.getCreatedAt().toString() + ",");
					bw.newLine();
		    	}
				// Break out of loop if id of tweet is less than the range you want.
			    if(fromDate.after(status.getCreatedAt())) {
			    	break outerloop;
			    }
			    
		    }

		    query = result.nextQuery();
		    result = twitter.search(query);
		}   

		// Close Resources
        bw.flush();
        bw.close();
        fw.close();
	}
	
	// Get user's timeline 
	public void getUserTimeLine(String user) throws TwitterException{
			ConfigurationBuilder cb = new ConfigurationBuilder();
			cb.setDebugEnabled(true)
			  .setOAuthConsumerKey(CONSUMER_KEY)
			  .setOAuthConsumerSecret(CONSUMER_KEY_SECRET)
			  .setApplicationOnlyAuthEnabled(true);
			TwitterFactory tf = new TwitterFactory(cb.build());
			Twitter twitter = tf.getInstance();			
			OAuth2Token token = twitter.getOAuth2Token();
			if (token != null) {
		        System.out.println("Token Type  : " + token.getTokenType());
		        System.out.println("Access Token: " + token.getAccessToken());
		    }
			List<Status> statuses;
            statuses = twitter.getUserTimeline(user);
            System.out.println("Showing @" + user + "'s user timeline.");
            for (Status status : statuses) {
                System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText());
            }
        } 
	
	// Post a Tweet
	public void postTweet(String tweetContent, String handle) throws TwitterException{
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		  .setOAuthConsumerKey(CONSUMER_KEY)
		  .setOAuthConsumerSecret(CONSUMER_KEY_SECRET)
		  .setOAuthAccessToken(ACCESS_TOKEN)
		  .setOAuthAccessTokenSecret(ACCESS_TOKEN_SECRET);
		
		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();
		
		Status stat = twitter.updateStatus(tweetContent);
		System.out.println("Successfully posted: \""+stat.getText()+"\" for @"+handle);
		
		List<Status> statuses;
        statuses = twitter.getUserTimeline(handle);
        System.out.println("\nShowing @" + handle + "'s user timeline.");
        for (Status status : statuses) {
            System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText());
        }
    } 
}