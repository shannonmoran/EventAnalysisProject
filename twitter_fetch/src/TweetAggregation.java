import java.io.*;
import java.util.ArrayList;
import java.util.Date;


public class TweetAggregation {
	public static void main(String[] args) {
		
		ArrayList<Tweet> tweetObjects = new ArrayList<Tweet>();
		String line = "";
		try {
			// Load file to aggregate
			BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Bob Naessens\\Desktop\\" + args[0] + ".csv"));

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
		
		Date lastTime = tweetObjects.get(0).getCreated();
		lastTime.setSeconds(0);

		AggregatedTweets at = new AggregatedTweets();
		ArrayList<AggregatedTweets> AggregatedTweetsObjects = new ArrayList<AggregatedTweets>();
		
		for(int i = 1; i < tweetObjects.size(); i++) {
			Date roundedTime = tweetObjects.get(i).getCreated();
			roundedTime.setSeconds(0);

			if(roundedTime.compareTo(lastTime) == 0) {
				at.setCount(at.getCount()+1);
				//System.out.println("here");
			} else {
				//System.out.println("here1");
				at.setDate(lastTime);
				at.setCount(at.getCount()+1);
				AggregatedTweetsObjects.add(at);
				lastTime = roundedTime;
				at = new AggregatedTweets();
			}
		}
		
		for(int i = 0; i < AggregatedTweetsObjects.size(); i++) {
			System.out.println("Date: " + AggregatedTweetsObjects.get(i).getDate().toString() + ". Count: " + AggregatedTweetsObjects.get(i).getCount());
		}
		// Now write the aggregated tweets to a separate csv file
		
	}
}
