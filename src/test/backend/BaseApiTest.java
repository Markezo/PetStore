package backend;

import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import config.EnvironmentConfig;

public class BaseApiTest {

    @BeforeClass
    public void setupEnvironment(ITestContext context) {
        EnvironmentConfig.init(context);
    }
}
