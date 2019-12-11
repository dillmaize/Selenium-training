package ru.stqa.training.selenium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Set;

public class CountriesLinkTest extends TestBase {
    private WebDriverWait wait;

    @Test
    public void linkTest() {
        wait = new WebDriverWait(app.wd, 10);
        app.wd.get("http://localhost:8080/litecart/admin/?app=countries&doc=edit_country&country_code=AU");
        ApplicationManager.login();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[contains (.,'Edit Country')]")));

        String sourceWindow = app.wd.getWindowHandle();
        System.out.println(sourceWindow);

        List<WebElement> externalLinks = app.wd.findElements(By.className("fa-external-link"));
        int numberOfLinks = externalLinks.size();
        for (int i = 0; i < numberOfLinks; i++) {
            externalLinks.get(i).click();
            wait.until(ExpectedConditions.numberOfWindowsToBe(2));
            Set<String> externalWindows = app.wd.getWindowHandles();
            externalWindows.remove(sourceWindow);
            String newWindow = externalWindows.iterator().next();
            app.wd.switchTo().window(newWindow);
            app.wd.close();
            app.wd.switchTo().window(sourceWindow);
        }
    }

}
