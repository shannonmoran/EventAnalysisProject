import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

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
		System.out.println("SPIKE - @ " + at.getDate());
		
		// Analyse each tweet, word by word
		
		// Count term frequencies per spike
		Map<String, Integer> termFrequency = new HashMap<String, Integer>();
		
		// Analyse each Tweet
		ArrayList<Tweet> agTweets = at.getTweets();
		for (int i=0; i<agTweets.size(); i++) {
			String tweet = agTweets.get(i).getText();
			
			// Split tweet into words
			String[] tweetWords = tweet.split(" ");
			
			// For each word count frequency
			for (int j=0; j<tweetWords.length; j++) {
				// Count word frequency, adding new words to map
				Integer occurrences = termFrequency.get(tweetWords[j]);
		        if (occurrences == null) {
		        	termFrequency.put(tweetWords[j], 1);
		        } else {
		        	termFrequency.put(tweetWords[j], occurrences.intValue() + 1);
		        }
			}
		}

		System.out.println("Unsorted: "+termFrequency);
		
		// Organise the freqency map entries from highest to lowest
		List<Map.Entry<String,Integer>> entries = new LinkedList<Map.Entry<String,Integer>>(termFrequency.entrySet());
	
		Collections.sort(entries, new Comparator<Map.Entry<String,Integer>>() {
			@Override
            public int compare(Entry<String, Integer> value1, Entry<String, Integer> value2) {
				// Compare the second value to the first to get list from highest to lowest
                return value2.getValue().compareTo(value1.getValue());
            }
        });
		
		System.out.println("  Sorted: "+entries+"\n");
		
		// Sort values
		
		/*for (int k=0; k<values.size(); k++) {
			// Check if this value is higher than the highest value so far
			if (values.get(k)>highestOccurring.indexOf(highestPos)) {
				
				// Add to highestOccurring ArrayList
				highestOccurring.add(highestOccurring.indexOf(highestPos), values.get(k));
				
				// update highest position
				highestPos = values.get(k);
			} else {
				
			}
		}*/
	}
}




















