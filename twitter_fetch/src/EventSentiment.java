import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EventSentiment {
	
	public static void main(String[] args) throws IOException {
		
		TweetAggregation.main(args);
		
		ArrayList<AggregatedTweets> at = TweetAggregation.getAggregatedTweetsObjects();
		int totalCount = TweetAggregation.getTotalCount();
		int averageCount = TweetAggregation.getAverageCount();
		
		// Use average count to create threshold to check for spikes
		double threshold = averageCount*1.5;
		System.out.println("\nSpike threshold: " +threshold);
		
		// Loop through the aggregated Tweet objects and determine if they are above the stated threshold: 150 at the moment
		int spikeCount = 0;
		for(int i = 0; i < at.size(); i++) {
			
			if (at.get(i).getCount() > threshold) {
				// Pass spikes into method for analysis
				analyseSpikeSentiment(at.get(i));
				spikeCount++;
			}
		}
		
		System.out.println("Total Spikes: " + spikeCount);
	}
	
	// Analyse spike here
	public static void analyseSpikeSentiment(AggregatedTweets at) throws IOException {
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
			System.out.println("Overall sentiment of Tweet: "+ tweetSentiment);
			overallMinuteSentiment += tweetSentiment;
			
		}
		
		System.out.println("Overall sentiment of Minute: " + overallMinuteSentiment);
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
}
