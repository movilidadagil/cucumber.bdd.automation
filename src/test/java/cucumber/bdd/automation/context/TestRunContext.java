package cucumber.bdd.automation.context;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;


import cucumber.api.Scenario;

public class TestRunContext extends HashMap {
    protected Properties defaults;
    private Properties props = null;
   
    private static TestRunContext instance = null;
    public Map<String, Object> map = new HashMap();
    private final String propsFile = "testframework.properties";
    public List<Thread> shutdownHooks = new ArrayList();
    public String buildID = null;
    public Scenario currentScenario = null;
    public List<String> testLog = new ArrayList();
    public String appPath = null;
    public final String pMode = "mode";
    public final String apiEndpoint="";
    public final String pTags = "cucumberTags";
    public final String pBrowser = "browser";
   
  

    protected TestRunContext() {
        this.init();
    }

    public static TestRunContext getInstance() {
        if (instance == null) {
            instance = new TestRunContext();

        }

        return instance;
    }

    public static void overrideInstance(TestRunContext context) {
        instance = context;
    }

    protected void initDefaults() {
        this.defaults = new Properties();
       
        this.defaults.setProperty("browser", TestRunContext.Browser.chrome.toString());
        this.defaults.setProperty("mode", TestRunContext.Mode.test.toString());
        this.defaults.setProperty("apiEndpoint", TestRunContext.ApiEndpoint.insallment.toString());
      
    }

   
    public TestRunContext.Mode getMode() {
        TestRunContext.Mode result = null;
        String sMode = System.getProperty("mode");
        if (sMode == "") {
            sMode = this.props.getProperty("mode", this.defaults.getProperty("mode"));
        }

        TestRunContext.Mode[] var3 = TestRunContext.Mode.values();
        int var4 = var3.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            TestRunContext.Mode mode = var3[var5];
            if (mode.toString().equalsIgnoreCase(sMode)) {
                result = mode;
            }
        }

        this.setMode(result);
        return result;
    }

   

    

   

    

    public String getBuildID() {
        if (this.buildID == null) {
            String timestamp = (new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss.SSS")).format(new Date());
            String[] tags = this.getTags();
            String tagsString = "(";

            for(int i = 0; i < tags.length; ++i) {
                tagsString = tagsString + tags[i];
                if (i < tags.length - 1) {
                    tagsString = tagsString + ",";
                }
            }

            tagsString = tagsString + ")";
            this.buildID = timestamp + " - " + this.getBrowserType() + " " + tagsString;
        }

        return this.buildID;
    }

    public String[] getTags() {
        String sTags = System.getProperty("cucumberTags");
        if (sTags == null || sTags.equals("")) {
            sTags = this.props.getProperty("cucumberTags", this.defaults.getProperty("cucumberTags"));
        }

        String[] result = new String[0];
        if (sTags != null) {
            StringTokenizer tokenizer = new StringTokenizer(sTags, ",");
            result = new String[tokenizer.countTokens()];

            for(int i = 0; tokenizer.hasMoreTokens(); ++i) {
                result[i] = tokenizer.nextToken();
            }
        }

        return result;
    }

   

   

    public void setMode(TestRunContext.Mode mode) {
        this.props.setProperty("mode", mode.toString());
    }

    public TestRunContext.Browser getBrowserType() {
        TestRunContext.Browser result = TestRunContext.Browser.unknown;
        String sBrowser = System.getProperty("browser");
        if (sBrowser == null || sBrowser.length() == 0) {
            sBrowser = this.props.getProperty("browser", this.defaults.getProperty("env"));
        }

        TestRunContext.Browser[] var3 = TestRunContext.Browser.values();
        int var4 = var3.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            TestRunContext.Browser browser = var3[var5];
            if (browser.toString().equalsIgnoreCase(sBrowser)) {
                result = browser;
                break;
            }
        }

        this.setBrowser(result);
        return result;
    }

    public void setApiEndpoint(TestRunContext.ApiEndpoint newApiEndpoint) {
    	this.props.setProperty("apiEndpoint", newApiEndpoint.toString());
    }

    public String getApiEndpoint() {
    	return this.props.getProperty("apiEndpoint").toString();
    }
    public void setBrowser(TestRunContext.Browser newBrowser) {
        this.props.setProperty("browser", newBrowser.toString());
    }

    public int getMaxThreads() {
        int result = 1;

        try {
            result = Integer.parseInt(System.getProperty("threads", "1"));
        } catch (Exception var3) {
        }

        return result;
    }

    public boolean init() {
        boolean result = true;
        this.props = new Properties();
        this.initDefaults();

        try {
            this.props.load(new FileReader("src/test/java/config/testframework.properties"));
        } catch (FileNotFoundException var3) {
        } catch (IOException var4) {
            var4.printStackTrace();
            result = false;
        }

        this.save();
        return result;
    }

    public void save() {
        Enumeration keys = this.defaults.keys();

        while(keys.hasMoreElements()) {
            String key = (String)keys.nextElement();
            this.props.setProperty(key, this.props.getProperty(key, this.defaults.getProperty(key)));
        }

        try {
            this.props.store(new FileWriter("testframework.properties"), "Properties configuring the Çiçeksepeti Test Framework.");
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }

    public void addShutdownHook(Thread hook) {
        Runtime.getRuntime().addShutdownHook(hook);
        this.shutdownHooks.add(hook);
    }

    public void runShutdownHooks() {
        Iterator var1 = this.shutdownHooks.iterator();

        while(var1.hasNext()) {
            Thread hook = (Thread)var1.next();
            hook.start();
            Runtime.getRuntime().removeShutdownHook(hook);
        }

    }

    public String getWorkingDirectory() {
        String prop = System.getProperty("workingdir");
        if (prop == null) {
            prop = ".";
        }

        return (new File(prop)).getAbsolutePath();
    }

    public String getAbsolutePath(String relativePath) {
        return this.getWorkingDirectory() + File.separator + relativePath;
    }

   
    public void log(String text) {
        this.testLog.add(text);
    }

    public void log(String action, String username, String body) {
        this.testLog.add(action + " for " + username + " with body:\n" + body == null ? "" : body);
    }

   

    public void onStartScenario(Scenario scenario) {
        this.currentScenario = scenario;
        this.testLog = new ArrayList();
    }

    public void onEndScenario() {
        Iterator var1 = this.testLog.iterator();

        while(var1.hasNext()) {
            String logEntries = (String)var1.next();
            this.currentScenario.write(logEntries);
        }

    }

    public static enum Mode {
        test,
        dev,
        custom;

        private Mode() {
        }
    }

    public static enum ApiEndpoint{
    	insallment,
    	defapiepoint;
    	private ApiEndpoint() {
    	
    	}
    }
    public static enum Browser {
        chrome,
        chromeHeadless,
        firefox,
        iphoneBrowser,
        iphoneApp,
        ipadBrowser,
        androidApp,
        unknown;

        private Browser() {
        }
    }
}