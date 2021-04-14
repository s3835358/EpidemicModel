import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("serial")
public class IndexMap extends HashMap<String, Integer>{
	
	private final int VERTEX = 1;
	
	public IndexMap() {
		
	}
	/**
	 * Deletes entry containing key within map. As values represent 
	 * array indices, all entries with an index greater than the 
	 * value of key are assigned value = value - 1.
	 *  
	 * @param key String to delete entry of
	 * 
	 * @param isVertex denotes whether the key represents a vertex or edge
	 */
	// Deletes key entry and adjusts index of other keys as neccessary
	public void adjustKeys(String key, boolean isVertex) {
		for(Entry<String, Integer> entry : this.entrySet()) {
			int eValue = entry.getValue(),
			kValue = get(key);
			// Assumes all edges are of length one
			boolean vertex = (entry.getKey().length() == VERTEX);
			if(isVertex == vertex) {
				if(eValue > kValue) {
					replace(entry.getKey(), eValue - 1);
				}
			}
		}
		remove(key);
	}	
}
