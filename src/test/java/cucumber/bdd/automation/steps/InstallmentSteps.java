package cucumber.bdd.automation.steps;

import static org.junit.Assert.assertNotNull;

import org.json.simple.JSONObject;

import com.fasterxml.jackson.databind.JsonNode;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.bdd.automation.context.TestRunContext;
import cucumber.bdd.automation.request.APIRequest;

public class InstallmentSteps {
	String apiEndpoint="";
	JsonNode resJsonFromInstallment=null;
	@Given("^InstallmentText-installment api endpoint$")
	public void ýnstallmenttext_installment_api_endpoint() throws Throwable {
		
	 apiEndpoint=TestRunContext.getInstance().getApiEndpoint();
		
	}

	@When("^I request  productGroupId-installmentText-installment api endpoint$")
	public void ý_request_productGroupId_installmentText_installment_api_endpoint() throws Throwable {
		resJsonFromInstallment=(JsonNode) APIRequest.execute(apiEndpoint);
		assertNotNull(resJsonFromInstallment);
	}

	@Then("^Check \"([^\"]*)\" value is \"([^\"]*)\"$")
	public void check_value_is(String arg1, String arg2) throws Throwable {
	
		for(int i=0;i<resJsonFromInstallment.size();i++) {
			if((resJsonFromInstallment.get(i).get("installment")).asBoolean()==true) {
				System.out.print(resJsonFromInstallment.get(i).get("installment")+"  :\n  ");
			System.out.print(resJsonFromInstallment.get(i).get("installmentText")+" :  ");
			System.out.print(resJsonFromInstallment.get(i).get("name")+"\n");
			}
		}
	}


}
