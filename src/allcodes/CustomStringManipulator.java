package allcodes;

public class CustomStringManipulator {
	 public static void main(String[] args) {
	        String sentence = "Assigment one has ten programs";
	        
	        
	        String longestWord = getLongestWord(sentence);
	        int vowelCount = countVowels(sentence);
	        String reversedSentence = reverseSentence(sentence);
	        
	       
	        System.out.println("Longest Word: " + longestWord);
	        System.out.println("Vowel Count: " + vowelCount);
	        System.out.println("Reversed Sentence: " + reversedSentence);
	    }

	    public static String getLongestWord(String sentence) {
	        String[] words = sentence.split(" ");
	        String longestWord = "";
	        
	        for (String word : words) {
	            if (word.length() > longestWord.length()) {
	                longestWord = word;
	            }
	        }
	        return longestWord;
	    }

	    public static int countVowels(String sentence) {
	        int count = 0;
	        sentence = sentence.toLowerCase();
	        
	        for (int i = 0; i < sentence.length(); i++) {
	            char c = sentence.charAt(i);
	            if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
	                count++;
	            }
	        }
	        return count;
	    }

	   
	    public static String reverseSentence(String sentence) {
	        StringBuilder reversed = new StringBuilder(sentence);
	        return reversed.reverse().toString();
	    }
	}

