
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;


public class TweetPreprocessing {
	
	
	
	public static void main(String[] args) {
		String line = "";
		try {
			
			// Load stop word file
			ArrayList<String> stopWords = loadStopWordFile(args);
			
			// Load file to aggregate
			BufferedReader br = new BufferedReader(new FileReader(args[0]));

			// Read header line
			line = br.readLine();
			
			// Create a file to store formatted tweets
			File csv = new File("FormattedTweets.csv");
					
					
			// Deletes file if already exists
			if(csv.exists()) {
				csv.delete();
			}
			
			csv.createNewFile();
			
			FileWriter fw = new FileWriter(csv);
			BufferedWriter bw = new BufferedWriter(fw);	
			
			// Write the headers
			bw.write("User Screen Name,");
			bw.write("Formatted Tweet,");
			bw.write("Time Created,");
			bw.newLine();
			
			// Read each tweet
			while ((line = br.readLine()) != null) {
		        // use comma as separator
				String[] tweetString = line.split(",");
				
				// Guard against errors in csv file
				if(tweetString.length == 3) {
					
					// Get raw tweet text String
					String tweet = tweetString[1].toLowerCase();
					
					// Remove all links and usernames
					String tweetNoLinks = "";
					String[] tweetWord1 = tweet.split(" ");
					if (tweet.contains("http") || tweet.contains("@")) {

						for (int i =0; i<tweetWord1.length; i++) {
							if (tweetWord1[i].startsWith("http")) {
								tweetWord1[i] = null; 
							} else if (tweetWord1[i].startsWith("@")) {
								tweetWord1[i] = null;
							}
						}
						
						
					}
					
					for (int k=0; k<tweetWord1.length; k++) {
						if (tweetWord1[k]!=null)
							tweetNoLinks += (tweetWord1[k] + " ");
					}
					
					// Split String into words, removing non-alphanumeric characters
					String[] tweetWord = tweetNoLinks.split("[^a-zA-Z0-9']");
					
					// For each word in the tweet, check if it is a stop word
					// If so it is removed
					String formattedTweet = "";
					for (int i =0; i < tweetWord.length; i++) {
						// 
						boolean stopWord = false;	
						for (String s : stopWords) {
							if(s.equals(tweetWord[i])) {
								stopWord = true;
								break;
							}
						}
						if (!stopWord) {
							formattedTweet += " "+tweetWord[i];
						}
					}
					
					// Write Formatted tweet to file
					bw.write(tweetString[0] + ",");
					bw.write(formattedTweet + ",");
					bw.write(tweetString[2] + ",");
					bw.newLine();
					
				}
			}
			
			// Close Resources
	        bw.flush();
	        bw.close();
	        fw.close();
			br.close();
			
		} catch (IOException e)	{
			e.printStackTrace();
		} 
		
	}
	
	private static ArrayList<String> loadStopWordFile(String[] args) throws IOException {
		// Load Stop word removal file
		BufferedReader br1 = new BufferedReader(new FileReader(args[1]));
		
		String line = "";
		// Read header line
		line = br1.readLine();
		
		// In memory representation of stop word file
		ArrayList<String> stopWords = new ArrayList<String>();
		
		while ((line = br1.readLine()) != null) {
	        // Add line as the word is the only item in the line
			stopWords.add(line.toLowerCase());
		}
		// Close reader
		br1.close();
		
		return stopWords;
	}
}
