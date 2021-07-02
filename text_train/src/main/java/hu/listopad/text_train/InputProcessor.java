package hu.listopad.text_train;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;

/**
 * Created by Noemi Czuczy on 2021. 03. 23.
 */

/**
 * Loads data from file as characters, and stores it a string. Removes line breaks,
 * numeric strings and excess white spaces. Transforms all uppercase string to capitalised string.
 */
public class InputProcessor {


	/**
	 * Loads data from file. File should be in src/main/resources folder.
	 * File name is in inputFileName variable.
	 * Replaces line breaks with whitespace.
	 *
	 * @return loaded string.
	 */
	public static String loadSource(String inputFileName) {

		StringBuilder sb = new StringBuilder();
		Path p = Path.of("text_train", "src", "main", "resources", inputFileName);
		try {
			p = p.toRealPath();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(String.valueOf(p)))) {
			String s;
			while((s = bufferedReader.readLine()) != null) {
				s = s.substring(0, s.length()).concat(" ");
				sb.append(s);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String result = sb.toString();
		return processLoadedString(result);
	}


	/**
	 * Removes numeric strings, single quotes, ZWNBS characters and excess line breaks from String passed as parameter.
	 * Transforms all uppercase string to capitalised string.
	 *
	 * @param raw String to process.
	 * @return processed string
	 */

	private static String processLoadedString(String raw) {

		String rawArray[] = raw.split("\\s+");
		StringBuilder compose = new StringBuilder();

		for (String s : rawArray) {
			if (!(s.isBlank() || s.matches("\\d+") || s.indexOf('\'') >=0  || s.indexOf('\uFEFF') >=0)){
				if (s.matches("[A-Z]{2,}[.,;!?]?")) {
					String s1 = s.substring(0, 1);
					String s2 = s.substring(1);
					s = s1.concat(s2.toLowerCase());


				}
				compose.append(s).append(" ");
			}
		}

		return compose.toString();


	}
}



