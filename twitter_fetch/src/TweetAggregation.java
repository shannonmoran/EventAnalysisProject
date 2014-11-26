import java.io.*;
import java.util.ArrayList;
import java.util.Date;


public class TweetAggregation {
	
	public static ArrayList<AggregatedTweets> AggregatedTweetsObjects;
	public static int totalCount;
	public static int averageCount;
	
	public static void main(String[] args) {
		
		ArrayList<Tweet> tweetObjects = new ArrayList<Tweet>();
		String line = "";
		try {
			// Load file to aggregate
			BufferedReader br = new BufferedReader(new FileReader(args[0]));

			// Read header line
			line = br.readLine();
			
			while ((line = br.readLine()) != null) {
		        // use comma as separator
				String[] tweetString = line.split(",");
				
				// Guard against errors in csv file
				if(tweetString.length == 3) {
					Date date = new Date(tweetString[2]);
					Tweet tweet = new Tweet(tweetString[0],tweetString[1],date);
					tweetObjects.add(tweet);
				}
			}
			
			br.close();
		} catch (IOException e)	{
			e.printStackTrace();
		} 
		
		// Get the first instance of a minute
		Date lastTime = tweetObjects.get(0).getCreated();
		lastTime.setSeconds(0);

		// Create an arraylist of aggregated tweet objects. These objects hold the datetime for each minute and the count of occurences of that minute.
		AggregatedTweets at = new AggregatedTweets();
		AggregatedTweetsObjects = new ArrayList<AggregatedTweets>();
		
		// List to store all tweets for a given minute
		ArrayList<Tweet> tweetArray = new ArrayList<Tweet>();
		
		// Loop through the original tweets
		for(int i = 0; i < tweetObjects.size(); i++) {
			// Get time rounded to minute of tweet
			Date roundedTime = tweetObjects.get(i).getCreated();
			roundedTime.setSeconds(0);
			
			// Add Tweet to array
			tweetArray.add(tweetObjects.get(i));
			
			// Check if the minute was the same as the last and add to counter if it is. Otherwise change last time and create new object.
			if(roundedTime.compareTo(lastTime) == 0) {
				at.setCount(at.getCount()+1);
			} else {
				at.setDate(lastTime);
				at.setTweets(tweetArray);
				at.setCount(at.getCount()+1);
				AggregatedTweetsObjects.add(at);
				lastTime = roundedTime;
				at = new AggregatedTweets();
				tweetArray = new ArrayList<Tweet>();
			}
		}
		
		// Print results to console. Used mainly for testing.
		int count = 0;
		for(int i = 0; i < AggregatedTweetsObjects.size(); i++) {
			System.out.println("Date: " + AggregatedTweetsObjects.get(i).getDate().toString()  + ". Number of Tweets this minute: " + AggregatedTweetsObjects.get(i).getTweets().size()+ ". Count: " + AggregatedTweetsObjects.get(i).getCount());
			count += AggregatedTweetsObjects.get(i).getCount();
		}
		System.out.println("Total Count: " + count);
		// Now write the aggregated tweets to a separate csv file
		
		System.out.println("Average Count: " + count/AggregatedTweetsObjects.size());
		
	}
	
	public static ArrayList<AggregatedTweets> getAggregatedTweetsObjects() {
		return AggregatedTweetsObjects;
	}
	
	public static int getTotalCount() {
		return totalCount;
	}
	
	public static int getAverageCount() {
		return averageCount;
	}
}
