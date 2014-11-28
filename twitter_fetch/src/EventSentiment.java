import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EventSentiment {

	private int sentiment;
	
		public EventSentiment(AggregatedTweets at) throws IOException {
			AggregatedTweets atObject = at;

			analyseSentiment(at);
	}
	
	// Analyse spike here
	public void analyseSentiment(AggregatedTweets at) throws IOException {
		System.out.println("SPIKE - " + at.getCount() + " @ " + at.getDate());
		
		// Analyse each tweet, word by word

		// Load positive word file
		ArrayList<String> positiveWords = loadSentimentWordFile("positive-words.txt");
		
		// Load negative word file
		ArrayList<String> negativeWords = loadSentimentWordFile("negative-words.txt");
		
		int overallMinuteSentiment = 0;
		
		// Analyse each Tweet
		ArrayList<Tweet> agTweets = at.getTweets();
		for (int i=0; i<agTweets.size(); i++) {
			String tweet = agTweets.get(i).getText();
			
			// Split tweet into words
			String[] tweetWords = tweet.split(" ");
			int tweetSentiment = 0;
			
			for (int k=0; k<tweetWords.length; k++) {
				// Check if the word is negative
				if (negativeWords.contains(tweetWords[k])) {
					tweetSentiment--;
				} else if (positiveWords.contains(tweetWords[k])) {
					tweetSentiment++;
				}
			}
			//System.out.println("Overall sentiment of Tweet: "+ tweetSentiment);
			overallMinuteSentiment += tweetSentiment;
			
		}
		
		System.out.println("Overall sentiment of Minute: " + overallMinuteSentiment);
		sentiment = overallMinuteSentiment;
	}
	
	private static ArrayList<String> loadSentimentWordFile(String filename) throws IOException {
		// Load Stop word removal file
		BufferedReader br1 = new BufferedReader(new FileReader(filename));
		
		String line = "";
		// Read header line
		line = br1.readLine();
		
		// In memory representation of stop word file
		ArrayList<String> Words = new ArrayList<String>();
		
		while ((line = br1.readLine()) != null) {
	        // Add line as the word is the only item in the line
			Words.add(line.toLowerCase());
		}
		// Close reader
		br1.close();
		
		return Words;
	}

	public int getSentiment() {
		return sentiment;
	}

	public void setSentiment(int sentiment) {
		this.sentiment = sentiment;
	}
}
