package autotest.utils;

import autotest.framework.InitializeTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * When extending this class, you must create override copy constructor with arguments from base class.
 */
public class BaseUtils {
    public WebDriverWait wait;
    public WebDriver driver;
    public InitializeTest.Results results;
    public BaseUtils(WebDriverWait wait, WebDriver driver, InitializeTest.Results results) {
        this.wait = wait;
        this.driver = driver;
        this.results = results;
    }
}
