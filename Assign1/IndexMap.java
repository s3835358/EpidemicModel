import java.util.HashMap;

@SuppressWarnings("serial")
public class IndexMap extends HashMap<String, Integer>{
		
	public IndexMap() {
		
	}
	/**
	 * Deletes entry containing key within map. As values represent 
	 * array indices, all entries with an index greater than the 
	 * value of key are assigned value = value - 1.
	 *  
	 * @param key String to delete entry of
	 *
	 */
	public void adjustKeys(String key) {
		for(Entry<String, Integer> entry : this.entrySet()) {
			int eValue = entry.getValue(),
			kValue = get(key);
			if(eValue > kValue) {
				replace(entry.getKey(), eValue - 1);
			}
		}
		remove(key);
	}	
	
	public String getKey(int index) {
		String key = "";
		for(Entry<String, Integer> entry : this.entrySet()) {
			if(entry.getValue() == index) {
				key = entry.getKey();
			}
		}
		return key; 
	}
}
