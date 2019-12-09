package ru.stqa.training.selenium;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.By.tagName;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class RecycleBinTest {
    private WebDriver driverChrome;
    private WebDriverWait wait;

    @Test
    public void workWithTheBasket() {
        driverChrome.navigate().to("http://localhost:8080/litecart/");

        for (int i = 1; i <= 3; i++) {
            driverChrome.findElement(By.className("product")).click();
            if (isElementPresent(driverChrome, tagName("select"))) {
                Select select = new Select(driverChrome.findElement(tagName("select")));
                select.selectByIndex(1);
            }
            try {
                new Select(driverChrome.findElement(By.name("options[Size]"))).selectByValue("Small");
            } catch (Exception e) {
            } finally {
                driverChrome.findElement(By.name("add_cart_product")).click();
                waitUntilCartRefreshed();
            }
            wait.until(textToBe(By.cssSelector("span.quantity"), String.valueOf(i)));
        }
        driverChrome.findElement(By.linkText("Checkout »")).click();
        wait.until(visibilityOfElementLocated(By.name("remove_cart_item")));

        Integer row = driverChrome.findElements(By.cssSelector("td.item")).size();
        for (int i = 1; i <= row; i++) {
            WebElement removeButton = driverChrome.findElement(By.name("remove_cart_item"));
            removeButton.click();
            wait.until(stalenessOf(removeButton));
        }
        Assert.assertTrue(isElementPresent(driverChrome, tagName("em")));
    }

    public boolean isElementPresent(WebDriver driverChrome, By locator) {
        try {
            driverChrome.findElement(locator);
            return true;
        } catch (NoSuchElementException ex) {
            return false;
        }
    }

    private void waitUntilCartRefreshed() {
        WebDriverWait wait = new WebDriverWait(driverChrome, 5);
        WebElement quantity = driverChrome.findElement(By.cssSelector("#cart .quantity"));
        Integer expectedQuantity = Integer.parseInt(quantity.getText()) + 1;
        wait.until(ExpectedConditions.textToBePresentInElement(quantity, expectedQuantity.toString()));
    }


    @Before
    public void initializeDriver() {
        driverChrome = new ChromeDriver();
        wait = new WebDriverWait(driverChrome, 10);
    }

    @After
    public void stop() {
        driverChrome.quit();
        driverChrome = null;
    }

}
