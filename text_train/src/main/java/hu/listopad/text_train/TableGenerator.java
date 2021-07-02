package hu.listopad.text_train;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Noemi Czuczy on 2021. 03. 24.
 */

/**
 * Creates map from the training text passed to its only method.
 * For every three character long substring occurring in the training text it finds following character.
 * To the three character long strings it maps the list of following characters.
 * If the same character follows the string at multiple occurrences, the list contains the character multiple times.
 *
 */
public class TableGenerator {

	/**
	 * Generates map from training text
	 * @param trainingText the String used for training
	 * @return generated HashMap
	 */

	public static Map<String, List<Character>> generateTable(String trainingText){
		Map<String, List<Character>> result = new HashMap<>();
		if (trainingText.length()<4){
			return result;
		}
		for (int i=0; i<trainingText.length()-4; i++){
			String s = trainingText.substring(i, i+3);
			Character c = trainingText.charAt(i+3);
			/*Character ch ='\'';
			Character zsp = '\uFEFF';
			if (s.indexOf(ch) >= 0 || c.equals(ch) || s.indexOf(zsp) >= 0 || c.equals(zsp)){
				continue;
			}*/
			if (result.containsKey(s)){
				result.get(s).add(c);
			}
			else {
				ArrayList<Character> nextChars= new ArrayList<>();
				nextChars.add(trainingText.charAt(i+3));
				result.put(s, nextChars);
			}

		}

		System.out.println(result.get("Ira"));
		System.out.println(result.get("CCa"));


		return result;

	}
}
