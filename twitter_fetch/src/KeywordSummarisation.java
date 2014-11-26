import java.util.ArrayList;

public class KeywordSummarisation {
	
	public static void main(String[] args) {
		
		String [] arguments = {"SCOIRL1.csv"};
		TweetAggregation.main(arguments);
		
		ArrayList<AggregatedTweets> at = TweetAggregation.getAggregatedTweetsObjects();
		System.out.println("\nNumber of minutes analysed: "+at.size());
		
		// Loop through the aggregated tweet objects and determine if they are above the stated threshold: 150 at the moment
		for(int i = 0; i < at.size(); i++) {
			if (at.get(i).getCount() > 150) {
				// Pass spikes into method for analysis
				analyseSpike(at.get(i));
			}
		}
	}
	
	// Analyse spike here
	public static void analyseSpike(AggregatedTweets at) {
		
	}
}
