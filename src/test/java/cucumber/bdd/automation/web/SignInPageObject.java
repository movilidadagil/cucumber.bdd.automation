package cucumber.bdd.automation.web;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import cucumber.bdd.automation.interfaces.SignInPageObjectInterface;
import cucumber.bdd.automation.objects.PageObject;
import cucumber.bdd.automation.utils.WebDriverUtils;



/* Created by JavaUnifiedTester   hasanaligul  2019-12-01  */
public class SignInPageObject extends PageObject implements SignInPageObjectInterface {

	public SignInPageObject(WebDriver browser) throws Exception {
	        super(browser);
	}

	public SignInPageObject() throws Exception {
	        super();
    }

	  

    public boolean checkForgotMessage() throws Exception {
		// TODO Auto-generated method stub
		return browser.findElement(By.xpath("//div[@class='password-recovery-result js-password-recovery-result is-hidden']"))
				.getText().contentEquals(" You will receive an e-mail from us with instructions for resetting your password.");
	}

	public void enterInvalidPassword(int numOfInvalidPassword) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void forgotButton() throws Exception {
		WebDriverUtils.sleep(5000);
		WebDriverUtils.waitForElementExists(browser, By.xpath("//a[@class='login__forgot-password js-forgot-password']"));
		browser.findElement(By.xpath("//a[@class='login__forgot-password js-forgot-password']")).click();
	}

	public String getErrorMessage() throws Exception {
		WebDriverUtils.sleep(3000);
		WebDriverUtils.waitForElementExists(browser, By.xpath("//div[@class='modal-body']"));
		String information= browser.findElement(By.xpath("//div[@class='modal-body']")).getText();
		browser.findElement(By.xpath("//button[@class='btn btn-primary']")).click();

		return information;
	}

	public boolean isLoggedIn(String userName) throws Exception {
		// TODO Auto-generated method stub
		return browser.findElements(By.xpath("//div[@class='header__right-col']"
				+ "//span[@class='user-menu__title'][contains(text(),"+"'"+userName+"'"+")]")).size()>0;
	}

	@Override
	public void navigateTo() throws Exception {
		// TODO Auto-generated method stub
		browser.get("https://www.lolaflora.com/login");
		waitForPresent();
	}

	@Override
	public boolean isPresent() {
		
		return browser.findElement(By.xpath("//h2[@class='login__heading']")).getText().contentEquals("Sign In");
	}
	
	@Override
	public void checkPopUp() {
		browser.findElement(By.xpath("/html/body/main/div/div[1]/div[1]/div[1]/a/span")).click();
	}

	public void signIn(String userName, String password){
	    		
	    	CharSequence passwordSeq = new StringBuffer(password);
	    	waitForPresent();
	    	
	        
	        WebDriverUtils.sendKeysWithDelay(browser.findElement(
	        		By.xpath("//input[@id='Password']")), passwordSeq, 100L);
	        
	    	browser.findElement(By.xpath("//button[@class='btn btn-primary btn-lg pull-right login__btn js-login-button']")).click();
	    	WebDriverUtils.sleep(5000);
	    	
     }

	public String getUserAccountTitle() throws Exception {
		// TODO Auto-generated method stub
		WebDriverUtils.sleep(5000);
		return browser.findElement(By.xpath("//div[@class='header__right-col']"
				+ "//span[@class='user-menu__title'][contains(text(),'My Account')]")).getText();
	}

	@Override
	public void enterValidUserName(String userName) throws Exception {
		// TODO Auto-generated method stub
		WebDriverUtils.sendKeysWithDelay(browser.findElement(By.xpath("//input[@id='EmailLogin']")), userName, 100L);
	}

	@Override
	public void enterValidUserEmail(String userEmail) throws Exception {
		// TODO Auto-generated method stub
		WebDriverUtils.sendKeysWithDelay(browser.findElement(By.xpath("//input[@id='Mail']")), userEmail, 200);
		browser.findElement(By.xpath("//button[@class='btn btn-lg btn-primary form-password-recovery__btn js-password-recovery-button']")).click();
	}

	@Override
	public void logOut() throws Exception {
		// TODO Auto-generated method stub
		
		browser.findElement(By.xpath("//div[@class='header__right-col']//a[@class='user-menu__log-out']")).click();
		WebDriverUtils.sleep(5000);
	}
	

    
}
