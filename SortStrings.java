public class SortStrings {
    public static void main(String[] args) {
        String[] words = { "Kword", "Pword", "Aword", "Dword", "Xword", "Mword", "Jword", "Kaord" };
        
        mergeSort(words);
        
        for (String ClassThree : words) {
            System.out.println(ClassThree);
        }
    }

    public static void mergeSort(String[] words) {
        if (words.length >= 2) {
            String[] left = new String[words.length / 2];
            String[] right = new String[words.length - words.length / 2];

            for (int i = 0; i < left.length; i++) {
                left[i] = words[i];
            }

            for (int i = 0; i < right.length; i++) {
                right[i] = words[i + words.length / 2];
            }

            mergeSort(left);
            mergeSort(right);
            merge(words, left, right);
        }
    }

    public static void merge(String[] words, String[] left, String[] right) {
        int a = 0;
        int b = 0;
        for (int i = 0; i < words.length; i++) {
            if (b >= right.length || (a < left.length && compareStrings(left[a],right[b]) < 0)) {
            	words[i] = left[a];
                a++;
            } else {
            	words[i] = right[b];
                b++;
            }
        }
    }
    
    public static int compareStrings(String a, String b){    	
    	int aLength = a.length();
    	int bLength = b.length();
    	for(int i=0;i<aLength;i++){
    		for(int j=0;j<bLength;j++){
    			if(a.charAt(i)==b.charAt(j)){
    				i++;
    				j++;
    			}
    			else if(a.charAt(i)>b.charAt(j)){
    				return 1;
    			}
    			else return -1;
    		}
    	}
		return 0;
    }
}
