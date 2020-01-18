package cucumber.bdd.automation.steps;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.bdd.automation.factory.PageObjectFactory;
import cucumber.bdd.automation.interfaces.SignInPageObjectInterface;

public class SignInSteps {
	
	SignInPageObjectInterface signInPage;
	
	public SignInSteps() throws Exception {
		signInPage = (SignInPageObjectInterface) PageObjectFactory.byName("SignInPageObject");
	}
	
	@Given("^I am a \"([^\"]*)\" of Lolaflora\\.com$")
	public void ý_am_a_of_Lolaflora_com(String userName) throws Throwable {
		signInPage.navigateTo();
		signInPage.enterValidUserName(userName);
		
	}

	@When("^I sign in using valid  \"([^\"]*)\"$")
	public void ý_sign_in_using_valid(String credetential) throws Throwable {
		signInPage.signIn("", credetential);
		signInPage.checkPopUp();
		assertEquals(signInPage.getUserAccountTitle(),"My Account");
	}

	@Then("^I should be logged in$")
	public void ý_should_be_logged_in() throws Throwable {
		signInPage.isLoggedIn("My Account");
	}

	@Given("^I am a not \"([^\"]*)\" of Lolaflora\\.com$")
	public void ý_am_a_not_of_Lolaflora_com(String userName) throws Throwable {
		signInPage.logOut();
		signInPage.navigateTo();
		signInPage.enterValidUserName(userName);
	}

	@When("^I sign in using invalid  \"([^\"]*)\"$")
	public void ý_sign_in_using_invalid(String credential) throws Throwable {
		signInPage.signIn("",credential);
	}

	@Then("^I should not be logged in and seen \"([^\"]*)\"$")
	public void ý_should_not_be_logged_in_and_seen(String information) throws Throwable {
		assertEquals(signInPage.getErrorMessage(),information);
		
	}

	@When("^I click forgot password button$")
	public void ý_click_forgot_password_button() throws Throwable {
		signInPage.forgotButton();
	}

	@When("^I fulfill valid \"([^\"]*)\" address$")
	public void ý_fulfill_valid_address(String userEmail) throws Throwable {
		signInPage.enterValidUserEmail(userEmail);
	}

	@Then("^\"([^\"]*)\" should be seen correctly$")
	public void should_be_seen_correctly(String inform) throws Throwable {
		assertTrue(signInPage.checkForgotMessage());
	}
	
}
