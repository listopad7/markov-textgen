package hu.listopad.text_train;

/**
 * Created by Noemi Czuczy on 2021. 03. 23.
 */
public class Main {

	public static void main(String[] args) {

		String trainingText = InputProcessor.loadSource("horatius_versek.txt");
		OutputGenerator.writeTableToFile(TableGenerator.generateTable(trainingText));
	}
}
