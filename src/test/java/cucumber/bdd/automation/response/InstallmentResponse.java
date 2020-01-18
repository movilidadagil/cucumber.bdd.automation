package cucumber.bdd.automation.response;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class InstallmentResponse {

	public Result result;
	public class Result{
		
		public Data data;
		public class Data{
			public String categoryName;
			public String[] products;
			public String code;
			public String name;
			public String[] price;
		}
	}
}
