package autotest.framework;

import org.openqa.selenium.chrome.ChromeOptions;

public class OptionsManager {

    /**
     * Chrome Driver Options applied on test start
     * @return customized chrome options
     */
    public static ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--ignore-certificate-errors");
        options.addArguments("--disable-popup-blocking");
        return options;
    }

}
