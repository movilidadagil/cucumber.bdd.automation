package cucumber.bdd.automation;
import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;




@RunWith(Cucumber.class)
@CucumberOptions(
		monochrome=true,
		features = "src/test/java/features",
		glue = "cucumber.bdd.automation.steps",
	    tags="@api",
		plugin = { 
					"pretty",
					"html:target/cucumber",
				} 
		
		)
public class RunCukeTest {
}
