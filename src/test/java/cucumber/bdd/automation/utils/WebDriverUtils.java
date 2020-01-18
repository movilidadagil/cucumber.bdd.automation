package cucumber.bdd.automation.utils;

/* Created by JavaUnifiedTester   hasanaligul  2020-12-01  */

import cucumber.api.Scenario;
import cucumber.bdd.automation.context.TestRunContext;
import cucumber.bdd.automation.objects.PageObject;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class WebDriverUtils {

    public static final String MODE_LOCAL = "local";

    public static final String MODE_DEFAULT = "local";
    public static TestRunContext context = TestRunContext.getInstance();
    public static boolean javascriptEnabledExpected = true;
    public static boolean javascriptEnabledActual = true;
    private static WebDriver browser = null;
    private static boolean shutDownHookConfigured = false;
    public static long DEFAULT_TIMEOUT = 10000L;
    public static boolean uploadToSauceLabsComplete = false;

    public WebDriverUtils() {
    }

    public static WebDriver getBrowser() throws Exception {
        if (browser != null && javascriptEnabledActual != javascriptEnabledExpected) {
            shutDown();
        }

        if (browser == null) {
            if (context.getMaxThreads() > 1) {
                Random r = new Random();
                int sleepSecs = r.nextInt(10);
                System.out.println("Sleeping for " + sleepSecs + " seconds before launching browser");
                sleep((long)(sleepSecs * 1000));
            }

            TestRunContext.Browser whichBrowser = context.getBrowserType();
            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setJavascriptEnabled(javascriptEnabledExpected);
            caps.setCapability("takesScreenshot", true);
            javascriptEnabledActual = javascriptEnabledExpected;
            if (whichBrowser == TestRunContext.Browser.chrome) {
                browser = launchChrome(caps, false);
            } else if (whichBrowser == TestRunContext.Browser.chromeHeadless) {
                browser = launchChrome(caps, true);
            } else if (whichBrowser == TestRunContext.Browser.firefox) {
                browser = launchFirefox(caps);
            }

           
        }

        setShutDownHook();
        return browser;
    }

   
    public static WebDriver launchHeadlessBrowser() throws Exception {
        DesiredCapabilities caps = new DesiredCapabilities();
        boolean useHeadless = System.getProperty("ignoreHeadless") == null;
        WebDriver browser = launchChrome(caps, useHeadless);
        browser.manage().window().setSize(new Dimension(1280, 768));
        return browser;
    }

    protected static WebDriver launchFirefox(DesiredCapabilities caps) {
        FirefoxProfile profile = new FirefoxProfile();
        return new FirefoxDriver((Capabilities) profile);
    }

    public static WebDriver launchChrome(DesiredCapabilities caps, boolean headless) throws Exception {
        String chromeDriverLocation = null;
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            chromeDriverLocation = System.getProperty("user.dir")+"/binaries/chromedriver/chromedriver.exe";
        } else if (System.getProperty("os.name").toLowerCase().contains("mac os")) {
            chromeDriverLocation = "target/test-classes/binaries/chromedriver/chromedriver";
        } else {
            chromeDriverLocation = "target/test-classes/binaries/chromedriver/chromedriver";
        }

        ChromeOptions result;
        if (headless) {
            result = new ChromeOptions();
            result.addArguments(new String[]{"headless"});
            result.addArguments(new String[]{"no-sandbox"});
            result.addArguments(new String[]{"test-type"});
            result.addArguments(new String[]{"ignore-certificate-errors"});
            result.addArguments(new String[]{"window-size=1280x768"});
            caps.setCapability("chromeOptions", result);
        }

        System.setProperty("webdriver.chrome.driver", chromeDriverLocation);
        result = null;
        Object result1;
         
            result1 = new ChromeDriver(caps);
            //((WebDriver)result1).manage().window().setSize(new Dimension(1440, 900));
        ((WebDriver)result1).manage().window().maximize();

        return (WebDriver)result1;
    }

    public static String getSessionId() throws Exception {
        return browser != null ? ((RemoteWebDriver)browser).getSessionId().toString() : null;
    }

   
    

    

   

    private static void setShutDownHook() {
        if (!shutDownHookConfigured) {
            context.addShutdownHook(new Thread() {
                public void run() {
                    WebDriverUtils.shutDown();
                }
            });
            shutDownHookConfigured = true;
        }

    }

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (Exception var3) {
        }

    }

   

    public static void waitForPresent(WebDriver browser, PageObject pageObject) throws Exception {
        long deadline = System.currentTimeMillis() + DEFAULT_TIMEOUT;
        if (browser == null) {
            browser = getBrowser();
        }

        while(!pageObject.isPresent() && System.currentTimeMillis() < deadline) {
            sleep(500L);
        }

        if (!pageObject.isPresent()) {
            String msg = "Violated expectation: Page not present: " + pageObject.getClass().getName();
            throw new Exception(msg);
        }
    }

    public static void waitForElementExists(WebDriver browser, By locator) throws Exception {
        WebDriverWait wait = new WebDriverWait(browser, DEFAULT_TIMEOUT / 1000L);
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public static void waitForElementExists(WebDriver browser, By locator, int timeOutInSeconds) throws Exception {
        WebDriverWait wait = new WebDriverWait(browser, (long)timeOutInSeconds);
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public static void waitForElementVisible(WebDriver browser, By locator) throws Exception {
        WebDriverWait wait = new WebDriverWait(browser, DEFAULT_TIMEOUT / 1000L);
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        wait.until(ExpectedConditions.visibilityOf(browser.findElement(locator)));
    }


   
    public static void shutDown() {
        if (browser != null) {
            browser.quit();
            browser = null;
        }

        context.save();
    }

    public static void sendKeysWithDelay(WebDriver browser, WebElement element, CharSequence keys, long delay) {
        if (delay == -1L) {
            delay = 100L;
        }

        new Actions(browser);

        for(int i = 0; i < keys.length(); ++i) {
            sleep(delay);
            element.sendKeys(new CharSequence[]{keys.subSequence(i, i + 1)});
        }

    }

    public static void sendKeysWithDelay(WebElement element, CharSequence keys, long delay) {
        sendKeysWithDelay(browser, element, keys, delay);
    }

    public static void goBack() {
        browser.navigate().back();
    }

    public static void goForward() {
        browser.navigate().forward();
    }

    public static String getCurrentUrl() {
        return browser.getCurrentUrl();
    }

    public static void swipeUpTillElementVisible(WebDriver browser, By locator) {
        WebElement element = browser.findElement(locator);

        while(!element.isDisplayed()) {
         //   ((AppiumDriver)browser).swipe(200, 500, 200, 100, 250);
        }

    }

    public static Alert getAlert() {
        Alert result;
        try {
            WebDriverWait wait = new WebDriverWait(browser, 2L);
            wait.until(ExpectedConditions.alertIsPresent());
            result = browser.switchTo().alert();
        } catch (Exception var2) {
            result = null;
        }

        return result;
    }

   

    public static enum actions {
        DO_NOT_WAIT,
        WAIT_UNTIL_TRUE,
        WAIT_UNTIL_FALSE;

        private actions() {
        }
    }
}
