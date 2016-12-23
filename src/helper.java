import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class helper {

	public int charCount(String text){
		int charCount = 0;
		char[] chars = text.toCharArray();
		for (char c : chars){
			if (Character.isLetter(c)){
				charCount++;
			}
			else if (Character.isDigit(c)){
				charCount++;
			}
		}
		return charCount;
	}
	
	public int wordCount(String text){
		Pattern pattern = Pattern.compile("\\d+\\.\\d+\\.");
		Matcher matcher = pattern.matcher(text);
		int wordCount = 0;
		while (matcher.find()){
			wordCount++;
		}
		text = text.replaceAll("[0-9]+\\.[0-9]+\\.","");
		if (text.isEmpty()){
			wordCount = 0;
		}
		else {
			String [] words = text.split("[\\p{IsPunctuation}\\p{IsWhite_Space}]+");
			for (int i = 0; i < words.length; i++){
				wordCount++;
			}
		}
		return wordCount;
	}
	
	public int sentenceCount(String text){
		Pattern pattern = Pattern.compile("\\d+\\.\\d+\\.");
		Matcher matcher = pattern.matcher(text);
		int sentenceCount = 0;
		while (matcher.find()){
			sentenceCount++;
		}
		text = text.replaceAll("[0-9]+\\.[0-9]+\\.","");
		for (int i = 0; i < text.length(); i++){
			char c = text.charAt(i);
			if (c == '?'){
				sentenceCount++;
			}
			else if (c == '!'){
				sentenceCount++;
			}
			else if (c == '?'){
				sentenceCount++;
			}
			else if (c == '.'){
				sentenceCount++;
			}
		}
		return sentenceCount;
	}
}
