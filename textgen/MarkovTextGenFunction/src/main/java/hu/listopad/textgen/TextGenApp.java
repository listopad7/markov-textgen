package hu.listopad.textgen;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.event.S3EventNotification.S3EventNotificationRecord;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;


import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;


/**
 * Created by Noemi Czuczy on 2021. 03. 25.
 */

/**
 * Generates random text based on a training table stored in an aws s3 bucket.
 * Maximum text length is 140 characters.
 */
public class TextGenApp implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent>{
	Gson gson = new Gson();
	Map<String, ArrayList<Character>> characterMap;
	List<String> startList;


	@Override
	public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent apiGatewayProxyRequestEvent, Context context) {

		LambdaLogger logger = context.getLogger();

		APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
		response.setIsBase64Encoded(false);
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		response.setHeaders(headers);

		try {

			if (characterMap == null) {
				characterMap = getMapFromS3("horatius.json", "listopad.maps");
				logger.log("Map created with " + characterMap.values().size() + "entries.");
			}
			if (startList == null) {
				startList = getStartList();
				logger.log("Startlist created with" + startList.size() + "entries.");
			}

			response.setStatusCode(200);
			response.setBody(getResponseBody());
		}catch (Exception e){
			logger.log("Exception: " + e.getMessage());
			response.setStatusCode(500);
			response.setBody("{}");
		}
		return response;
	}

	private String getResponseBody(){

		Random random= new Random();
		int rNum = random.nextInt(startList.size());
		StringBuilder start = new StringBuilder(startList.get(rNum));

		for (int i=0; i<140; i++){
			String str = start.substring(i, i+3);
			List<Character> chList = characterMap.get(str);
			Character ch = chList.get(random.nextInt(chList.size()));
			start.append(ch);
		}

		int lastSpace = start.lastIndexOf(" ");
		List<String> marks = List.of(".", "?", "!");
		start.delete(lastSpace, start.length());
		if (start.substring(start.length()-1, start.length()).matches("[.,!?:;]")){
			start.delete(start.length()-1, start.length());
		}
		start.append(marks.get(random.nextInt(marks.size())));
		start.replace(0,1, String.valueOf(Character.toUpperCase(start.charAt(0))));
		String result = start.toString();

		String resultJson = gson.toJson(result);
		return resultJson;
	}

	private Map<String, ArrayList<Character>> getMapFromS3(String filename, String bucketName){

		StringBuilder sb = new StringBuilder();
		AmazonS3 s3Client = AmazonS3ClientBuilder.defaultClient();
		try(
		S3Object s3Object = s3Client.getObject(new GetObjectRequest(
				bucketName, filename));
		S3ObjectInputStream objectData = s3Object.getObjectContent();
		BufferedReader reader = new BufferedReader((new InputStreamReader(objectData)))){
			String line;
			while((line = reader.readLine()) != null){
				sb.append(line);
			}


		}catch (IOException e){
			throw new RuntimeException();
		}
		String mapString = sb.toString();
		Type trainMap = new TypeToken<Map<String, ArrayList<Character>>>(){}.getType();
		Map<String, ArrayList<Character>> actualTrainMap = gson.fromJson(mapString, trainMap);
		return actualTrainMap;
	}

	private List<String> getStartList(){

		List<String> startList= new ArrayList<>();
		for (String str : characterMap.keySet()){
			if (str.toLowerCase().matches("[a-z]{3}")){
				startList.add(str);
			}
			if (startList.size() > 49){
				break;
			}
		}
		return startList;
	}
}
