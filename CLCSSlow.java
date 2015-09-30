import java.util.*;



public class CLCSSlow {
	
	public static String cut(String word, int k){
		if (k < 0 || k >= word.length()){
			System.out.println("bad k value");
			return word;
		}
		String newString = word.substring(k, word.length()) + word.substring(0,k);
		return newString;

	}

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
    	int T = s.nextInt();
    	for (int tc = 0; tc < T; tc++) {

     		String AS = s.next();
            char A[] = AS.toCharArray();
      		String BS = s.next();
            char B[] = BS.toCharArray();
      		int biggestSubstring = 0;

      		String BC = cut(BS, 0);
      		for (int i = 0 ; i < A.length; i++){
      			String AC = cut(AS, i);
      			int curr = LCS.LCS(AC.toCharArray(),BC.toCharArray());
      			if (curr > biggestSubstring){
      				biggestSubstring = curr;
      			}
      		}
      		System.out.println(biggestSubstring);
      	}
    } 		
	
}