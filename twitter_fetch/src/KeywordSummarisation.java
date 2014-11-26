import java.util.ArrayList;

public class KeywordSummarisation {
	
	public static void main(String[] args) {
		
		String [] arguments = {"SCOIRL1.csv"};
		TweetAggregation.main(arguments);
		
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
		
	}
}
