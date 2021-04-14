
public class StrArray {
	
	private String[] array;
	
	public StrArray() {
		array = new String[0];
	}
	
	public StrArray(String[] array) {
		this.array = array;
	}
	
	public void addString(String addString) {
		boolean contains = false;
    	for(String string : array) {
    		if (string.equals(addString)) {
    			contains = true;
    		}
    	}
    	if(!contains) {
    		String[] temp = array;
        	array = new String[array.length + 1];
        	for (int i = 0; i < temp.length; i++) {
        		array[i] = temp[i]; 
    		}
        	array[temp.length] = addString;
    	}
	}
	
	public void addStrArr(String[] strArr) {
    	for(String addString : strArr) {
    		addString(addString);
    	}
	}
	
	public void addStrArr(String[] strArr, String except) {
    	for(String addString : strArr) {
    		if(!addString.equals(except)) {
    			addString(addString);
    		}
    	}
	}
	
	public void delString(String delString) {
		boolean found = false;
		int i = 0;
		for(String string : array) {
			if(delString.equals(string)) {
				found = true;
				break;
			}
			i++;
		}
		if (found) {
			String[] temp = array;
			array = new String[temp.length - 1];
			for(int j = 0; j < array.length; j++) {
				if(j >= i) {
					array[j] = temp[j + 1];
				} else {
					array[j] = temp[j];
				}
			}
		}
	}
	
	public void delStrArr(String[] strArr) {
    	for(String delString : strArr) {
    		delString(delString);
    	}
	}
	
	public String getString(int index) {
		return array[index];
	}
	
	public int getLength() {
		return array.length;
	}
	
	public String[] getArr() {
		return array;
	}
	
	public boolean contains(String string) {
		boolean contains = false;
		for(String strArr : array) {
			if(strArr.equals(string)) {
				contains = true;
			}
		}
		return contains;
	}
	
	public String toString() {
		String print = "";
		for(int i = 0; i < array.length; i++) {
			print = print + array[i] + "\n";
		}
		return print;
	}
}
