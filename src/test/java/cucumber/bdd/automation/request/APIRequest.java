package cucumber.bdd.automation.request;

import java.io.IOException;

import org.apache.http.client.HttpClient;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import cucumber.bdd.automation.response.InstallmentResponse;

public class APIRequest{

	
	public static JsonNode execute(String apiEndpoint) throws IOException, ParseException {
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder()
			      .url(apiEndpoint)
			      .build();
			 
			    Call call = client.newCall(request);
			    Response response = call.execute();
				String jsonString = response.body().string();
				
			    JSONParser parser = new JSONParser();
			    JSONObject json = (JSONObject) parser.parse(jsonString);
			    ObjectMapper objectMapper = new ObjectMapper(); 
			    JsonNode jsonNode = objectMapper.readTree(jsonString);
			   // System.out.print(jsonNode.get("result"));
			   // System.out.print(jsonNode.get("error"));
			    JsonNode products = jsonNode.get("result").get("data").get("products");
			    //System.out.print(products.get("installmentText"));
			  
			    return products;
			 
	}
	

	
	
}
