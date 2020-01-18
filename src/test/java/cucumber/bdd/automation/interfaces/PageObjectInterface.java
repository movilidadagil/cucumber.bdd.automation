package cucumber.bdd.automation.interfaces;
public interface PageObjectInterface {
    void navigateTo() throws Exception;

    boolean isPresent();
    void checkPopUp();
}
