import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
	
	private static BufferedWriter bw;
	
	public static void main(String[] args) throws IOException {
		
		TweetAggregation.main(args);
		
		ArrayList<AggregatedTweets> at = TweetAggregation.getAggregatedTweetsObjects();
		int averageCount = TweetAggregation.getAverageCount();
		
		// Use average count to create threshold to check for spikes
		double threshold = averageCount*1.5;
		System.out.println("\nSpike threshold: " +threshold);
		
		// Loop through the aggregated tweet objects and determine if they are above the calculated threshold
		int spikeCount = 0;
		for(int i = 0; i < at.size(); i++) {
			
			if (at.get(i).getCount() > threshold) {
				spikeCount++;
				
				// If at least one spike occurs create file to store spike word frequencies
				if (spikeCount==1) {
					// Create a file to store spike frequencies
					File freqCsv = new File("SpikeFrequencies.csv");
								
					// Deletes file if already exists
					if(freqCsv.exists()) {
						freqCsv.delete();
					}
					
					freqCsv.createNewFile();
					
					FileWriter fw = new FileWriter(freqCsv);
					bw = new BufferedWriter(fw);
					
					// Write the headers
					bw.write("Spike Time,");
					bw.write("Spike Sentiment,");
					for (int k=1; k<=6; k++) {
						bw.write( "Word #"+k+",Frequency,");
					}
					bw.newLine();
				}
				
				// Pass spikes into method for analysis
				analyseSpike(at.get(i));
			}
			// Flush Resource
			bw.flush();
		}

		System.out.println("Total Spikes: " + spikeCount);
	}
	
	// Analyse spike here
	public static void analyseSpike(AggregatedTweets at) throws IOException {
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
		
		// Organise the frequency map entries from highest to lowest
		List<Map.Entry<String,Integer>> entries = new LinkedList<Map.Entry<String,Integer>>(termFrequency.entrySet());
	
		Collections.sort(entries, new Comparator<Map.Entry<String,Integer>>() {
			@Override
            public int compare(Entry<String, Integer> value1, Entry<String, Integer> value2) {
				// Compare the second value to the first to get list from highest to lowest
                return value2.getValue().compareTo(value1.getValue());
            }
        });
		
		System.out.println("  Sorted: "+entries);

		List<Map.Entry<String,Integer>> topEntries = new LinkedList<Map.Entry<String,Integer>>();
		
		// Get top 6 words to help summarise event.
		for(int i = 1; i < 7; i++) {
			topEntries.add(entries.get(i));
		}
		
		System.out.println("Top 6 Entries: "+topEntries);
		
		// TODO: Write top 6 entries for this spike to new line in SpikeFrequencies.csv file
		// FORMAT: Date, Sentiment, Frequency Word #1, Word 1 Frequency,...,Frequency Word #6, Word 6 Frequency
		bw.write(at.getDate()+",");
		bw.write(at.getSentiment()+",");
		for (int k=0; k<topEntries.size(); k++) {
			bw.write( topEntries.get(k).getKey()+","+topEntries.get(k).getValue()+",");
		}
		bw.newLine();
		
		// Load key word file
		ArrayList<String> keyWords = loadKeywordWordFile("soccer_key_phrases.txt");
		
		List<Map.Entry<String,Integer>> summaryWords = new LinkedList<Map.Entry<String,Integer>>();
		
		// Loop to compare top 6 words with keywords.
		for(int i = 0; i < topEntries.size(); i++) {

			// Check if the top word in question is a keyword
			if(keyWords.contains(topEntries.get(i).getKey())) {
				summaryWords.add(topEntries.get(i));
			}
		}
		
		System.out.println("Possible Summary words: "+summaryWords+"\n");
	}
	
	private static ArrayList<String> loadKeywordWordFile(String filename) throws IOException {
		// Load Stop word removal file
		BufferedReader br1 = new BufferedReader(new FileReader(filename));
		
		String line = "";
		// Read header line
		line = br1.readLine();
		
		// In memory representation of stop word file
		ArrayList<String> keyWords = new ArrayList<String>();
		
		while ((line = br1.readLine()) != null) {
	        // Add line as the word is the only item in the line
			keyWords.add(line.toLowerCase());
		}
		// Close reader
		br1.close();
		
		return keyWords;
	}
}




















