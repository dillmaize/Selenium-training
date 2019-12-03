package ru.stqa.training.selenium;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class StickerCheck {

    private WebDriver driverChrome;

    @Before
    public void start() {
        driverChrome = new ChromeDriver();
        driverChrome.get("http://localhost:8080/litecart");
    }

    @Test
    public void checkSticker() {
        driverChrome.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

        List<WebElement> products = driverChrome.findElements(By.cssSelector("li.product"));
        for (WebElement p : products) {
            Assert.assertTrue(p.findElements(By.cssSelector("div.sticker")).size() == 1);
        }
    }

    @After
    public void stop() {
        driverChrome.quit();
        driverChrome = null;
    }
}
