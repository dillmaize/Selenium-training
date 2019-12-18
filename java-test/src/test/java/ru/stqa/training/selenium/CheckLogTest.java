package ru.stqa.training.selenium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.logging.LogEntry;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static ru.stqa.training.selenium.ApplicationManager.login;

public class CheckLogTest extends TestBase {

    @Test

    public void checkLogs(){
        app.wd.get("http://localhost:8080/litecart/admin/?app=catalog&doc=catalog&category_id=1");
        login();

        List<WebElement> tableRows = app.wd.findElements(By.cssSelector(".dataTable .row"));
        int tableRowCount = tableRows.size();

        for (int i = 0; i < tableRowCount; i++) {

            List<WebElement> tableCells = tableRows.get(i).findElements(By.tagName("td"));

            if (!isElementPresent(By.cssSelector("i[class*='fa fa-folder']"), tableCells.get(2))) {
                tableCells.get(2).findElement(By.cssSelector("a")).click();

                List<LogEntry> logEntries = app.wd.manage().logs().get("browser").getAll();

                if (logEntries.size() > 0) {
                    for (LogEntry logEntry : logEntries) {
                        System.out.println(logEntry);
                    }
                }

                assertEquals(0, logEntries.size());
                app.wd.get("http://localhost:8080/litecart/admin/?app=catalog&doc=catalog&category_id=1");
                tableRows = app.wd.findElements(By.cssSelector(".dataTable .row"));
            }
        }

    }

    public boolean isElementPresent(By locator, WebElement parent) {
        try {
            parent.findElement(locator);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}
