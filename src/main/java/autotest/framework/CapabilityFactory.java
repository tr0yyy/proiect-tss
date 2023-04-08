package autotest.framework;

import org.openqa.selenium.Capabilities;

public class CapabilityFactory {
    public Capabilities capabilities;

    /**
     * Gets browser capabilities using declared ChromeOptions from OptionsManager
     * @return browser capabilities
     */
    public Capabilities getCapabilities() {
        capabilities = OptionsManager.getChromeOptions();
        return capabilities;
    }
}
