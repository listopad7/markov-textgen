package hu.listopad.text_train;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

/**
 * Created by Noemi Czuczy on 2021. 03. 24.
 */
public class OutputGenerator {



	public static void writeTableToFile(Map<String, List<Character>> result){

		String resultString = generateJson(result);
		Path p = Path.of("text_train","src","main","generatedMaps");
		try {
			p = p.toRealPath();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Path pathOfFile = Path.of(p.toString(), "horatius.json");
		System.out.println(pathOfFile);
		try (BufferedWriter writer = Files.newBufferedWriter(pathOfFile)) {
			writer.write(resultString);
		} catch (IOException e) {
			e.printStackTrace();
		} ;
	}



	private static String generateJson(Map<String, List<Character>> result){

		Gson gson = new Gson();
		String resultString = gson.toJson(result);

		return resultString;

	}


}
