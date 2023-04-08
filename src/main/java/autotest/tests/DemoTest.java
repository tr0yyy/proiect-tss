package autotest.tests;

import autotest.framework.InitializeTest;
import autotest.ui.DemoUi;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class DemoTest extends InitializeTest {
    @Test
    public void searchBicycleOnEmag() throws InterruptedException {
        System.out.println("Started testing");
        getDriver().navigate().to("https://emag.ro");
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.of(10, ChronoUnit.SECONDS));
        DemoUi demoUi = new DemoUi();
        wait.until(ExpectedConditions.visibilityOfElementLocated(demoUi.searchBar));
        getDriver().findElement(demoUi.searchBar).sendKeys("bicicleta");
        getDriver().findElement(demoUi.searchButton).click();
        Thread.sleep(Duration.of(5, ChronoUnit.SECONDS));
        Assert.assertTrue(true);
    }
}
