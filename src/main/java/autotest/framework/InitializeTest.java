package autotest.framework;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class InitializeTest implements IHookable {

    protected static ThreadLocal<RemoteWebDriver> driver = new ThreadLocal<>();
    public CapabilityFactory capabilityFactory = new CapabilityFactory();
    public Results results = new Results();
    private static final String KEY = "softassert";
    public WebDriverWait wait;


    @BeforeClass
    public void setup() throws IOException {
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\grid\\chromedriver.exe");
        driver.set(new RemoteWebDriver(new URL("http://localhost:4444/"),
                capabilityFactory.getCapabilities()));
        FileUtils.deleteDirectory(new File(System.getProperty("user.dir") + "\\test-output"));
        getDriver().navigate().to("https://localhost:44495/");

        // asteapta 10 secunda pana gaseste elementul, daca nu da crash
        wait = new WebDriverWait(getDriver(), Duration.of(10, ChronoUnit.SECONDS));
    }

    public WebDriver getDriver() {
        return driver.get();
    }

    @AfterClass
    public void tearDown() {
        getDriver().quit();
        driver.remove();
    }

    @Override
    public void run(IHookCallBack callBack, ITestResult testResult) {
        SoftAssert softAssert = new SoftAssert();
        testResult.setAttribute(KEY, softAssert);
        callBack.runTestMethod(testResult);
        try {
            softAssert.assertAll();
        } catch (AssertionError e) {
            throw new AssertionError(e.getMessage(), testResult.getThrowable());
        }
    }

    protected class Results {
        Logger logger = LogManager.getLogger(Results.class);
        SimpleDateFormat sdf = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss]");

        public void verifyTrue(boolean condition, String messagePass, String messageFail, boolean makeScreenshot) throws IOException {
            Object object = Reporter.getCurrentTestResult().getAttribute(KEY);
            if (condition) {
                Reporter.log("<p style=\"color: green;\">" + sdf.format(new Date()) + " " + messagePass + "</p>");
            } else {
                Reporter.log("<p style=\"color: red;\">" + sdf.format(new Date()) + " " + messageFail + "</p>");
            }
            SoftAssert softAssert = (SoftAssert) object;
            softAssert.assertTrue(condition, messagePass);
            if (makeScreenshot) {
                takeScreenshot();
            }
        }

        public void assertTrue(boolean condition, String messagePass, String messageFail, boolean makeScreenshot) throws IOException {
            try {
                Assert.assertTrue(condition, messagePass);
                Reporter.log("<p style=\"color: green;\">" + sdf.format(new Date()) + " " + messagePass + "</p>");
                if (makeScreenshot) {
                    takeScreenshot();
                }
            } catch (AssertionError error) {
                Reporter.log("<p style=\"color: red;\">" + sdf.format(new Date()) + " " + messageFail + "</p>");
                takeScreenshot();
                throw error;
            }
        }

        public void info(String message, boolean makeScreenshot) throws IOException {
            Reporter.log("<p style=\"color: blue;\">" + sdf.format(new Date()) + " " + message + "</p>");
            if (makeScreenshot) {
                takeScreenshot();
            }
        }

        public void takeScreenshot() throws IOException {
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS");
            System.setProperty("org.uncommons.reportng.escape-output", "false");
            File scrFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
            String Path = System.getProperty("user.dir") + "\\test-output\\html\\screenshots\\";
            File folder = new File(Path);
            File screenshotName = new File(Path + "wikipediadaw" + sdf2.format(new Date()) + ".png");
            if (folder.mkdirs() || folder.exists()) {
                FileUtils.copyFile(scrFile, screenshotName);
                Reporter.log("<p style=\"color: blue;\">" + sdf.format(new Date()) + " " + screenshotName + "</p> <a href=\"" + screenshotName + "\">" + "<img src='" + screenshotName + "' height='540' width='960' /></a>");
            } else {
                logger.error("Cannot create folder for screenshot " + screenshotName);
            }

        }
    }

}