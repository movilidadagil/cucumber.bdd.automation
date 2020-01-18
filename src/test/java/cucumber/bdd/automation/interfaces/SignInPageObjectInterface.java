package cucumber.bdd.automation.interfaces;
public interface SignInPageObjectInterface extends PageObjectInterface {

    void signIn(String userName, String password);
    
    String getUserAccountTitle() throws Exception;

    void navigateTo() throws Exception;

    boolean checkForgotMessage() throws Exception;

    void enterInvalidPassword(int numOfInvalidPassword) throws Exception;
    
    void enterValidUserEmail(String userEmail) throws Exception;
    
    void enterValidUserName(String userName) throws Exception;

    void forgotButton() throws Exception;

    String getErrorMessage() throws Exception;

	boolean isLoggedIn(String userName) throws Exception;
	void logOut() throws Exception;


}