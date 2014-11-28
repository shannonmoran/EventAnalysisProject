import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AnalyseEvent {

	public static void main(String[] args) throws IOException {
		// Run by passing in the name of the sports match followed by the name of the home team and away team.
		Event event = new Event(args[0], args[1], args[2]);
		
		 //ks = new KeywordSummarisation();
		KeywordSummarisation.main(args);
		
		// Analyse moments
		ArrayList<Moment> moments = KeywordSummarisation.getMoments();
		ArrayList<Moment> aggregatedMoments = new ArrayList<Moment>();
		
		int lastAddedIndex = -1;
		
		for(int i=moments.size()-2; i>=0; i-- ) {
			// Compare sequential moments, comparing this moment to the previous moment
			// Aim is to determine if moments are related		
			if(i==moments.size()-2) {
				aggregatedMoments.add(moments.get(i+1));
				lastAddedIndex = i+1;
			} else if (!moments.get(i+2).getType().equals(moments.get(i+1).getType())) {
				// Moments of diff types, either sequential or not - considered unrelated moments
				aggregatedMoments.add(moments.get(i));
				lastAddedIndex = i;
			} else if (moments.get(i+2).getDate().getMinutes() - moments.get(i+1).getDate().getMinutes() > 1) {
				System.out.println(moments.get(i-1).getDate().getMinutes() - moments.get(i).getDate().getMinutes());
				// Moments of the same type, once NOT sequential - considered unrelated moments
				aggregatedMoments.add(moments.get(i));
				lastAddedIndex = i;
			}
			
			// Add sentiment value of all moments considered the same
			if(i!=moments.size()-2) {
				if(moments.get(i+2).getType().equals(moments.get(i+1).getType()) && (moments.get(i+1).getDate().getMinutes() - moments.get(i+2).getDate().getMinutes()) == 1) {
					moments.get(lastAddedIndex).setSentiment(moments.get(lastAddedIndex).getSentiment() + moments.get(i).getSentiment());
				}
			}	
		}
		
		for(int i = 0; i < aggregatedMoments.size(); i++ ) {
			System.out.println("Moment " + aggregatedMoments.get(i).toString());
		}
	}

}
