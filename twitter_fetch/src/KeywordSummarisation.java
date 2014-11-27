import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class KeywordSummarisation {
	
	public static void main(String[] args) {
		
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
				analyseSpike(at.get(i));
				spikeCount++;
			}
		}
		
		System.out.println("Total Spikes: " + spikeCount);
	}
	
	// Analyse spike here
	public static void analyseSpike(AggregatedTweets at) {
		System.out.println("SPIKE - " + at.getCount() + " @ " + at.getDate());
		
		// Analyse each tweet, word by word
		
		// Count term frequencies per spike
		Map<String, Integer> termFrequency = new HashMap<String, Integer>();
		
		// Analyse each Tweet
		ArrayList<Tweet> agTweets = at.getTweets();
		for (int i=0; i<agTweets.size(); i++) {
			String tweet = agTweets.get(i).getText();
			
			// Split tweet into words
			String[] tweetWords = tweet.split(" ");
			
			for (int k=0; k<tweetWords.length; k++) 
				System.out.println(tweetWords[k]);
			
			// For each word count frequency
			for (int j=0; j<tweetWords.length; j++) {
//				if (i>0)
//					System.out.println("tweetWords[i-1]" +tweetWords[i-1]);
				
				// Count word frequency, adding new words to map
				Integer occurrences = termFrequency.get(tweetWords[i]);
		        if (occurrences == null) {
		        	termFrequency.put(tweetWords[i], 1);
		        } else {
		        	termFrequency.put(tweetWords[i], occurrences.intValue() + 1);
		        }
			}
		}
		
		System.out.println("\n"+termFrequency);
	}
}




















