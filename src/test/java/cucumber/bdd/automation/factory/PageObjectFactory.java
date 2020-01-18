package cucumber.bdd.automation.factory;

import java.lang.reflect.Constructor;

import org.openqa.selenium.WebDriver;

import cucumber.bdd.automation.context.TestRunContext;
import cucumber.bdd.automation.objects.PageObject;




public class PageObjectFactory {
    public PageObjectFactory() {
    }

    public static PageObject byName(String poName) throws Exception {
        return byName(poName, (WebDriver)null);
    }

    public static PageObject byName(String poName, WebDriver customBrowser) throws Exception {
        String className = "cucumber.bdd.automation";
        TestRunContext.Browser browserType = TestRunContext.getInstance().getBrowserType();
        switch(browserType) {
        case iphoneApp:
            className = className + ".ios.";
            break;
        case androidApp:
            className = className + ".android.";
            break;
        default:
            className = className + ".web.";
        }

        className = className + poName;
        Class poClass = Class.forName(className);
        Class[] types = new Class[]{WebDriver.class};
        Constructor constructor = null;

        try {
            constructor = poClass.getConstructor(types);
        } catch (Exception var9) {
        }

        if (constructor == null) {
            constructor = poClass.getSuperclass().getConstructor(types);
        }

        Object[] parameters = new Object[]{customBrowser};
        Object poInstance = constructor.newInstance(parameters);
        return (PageObject)poInstance;
    }
}