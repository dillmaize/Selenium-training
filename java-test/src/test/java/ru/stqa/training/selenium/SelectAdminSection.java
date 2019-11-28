package ru.stqa.training.selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class SelectAdminSection {

    private WebDriver driverChrome;
    private WebDriverWait wait;

    @Before
    public void start() {
        driverChrome = new ChromeDriver();
        wait = new WebDriverWait(driverChrome, 10);

        driverChrome.get("http://localhost:8080/litecart/admin/");
        driverChrome.findElement(By.name("username")).sendKeys("admin");
        driverChrome.findElement(By.name("password")).sendKeys("admin");
        driverChrome.findElement(By.name("login")).click();
    }

    @Test
    public void selectAllSections() throws InterruptedException {
        driverChrome.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
         int section = driverChrome.findElements(By.xpath("//ul[@id='box-apps-menu']/li")).size();
        System.out.println(section);

        int eSection = 1;
        while (eSection <= section) {
            Thread.sleep(200);
            driverChrome.findElement(By.cssSelector("ul#box-apps-menu > li:nth-child(" + eSection + ")")).click();
            try {
                WebElement header = driverChrome.findElement(By.id("content"));
                header.findElement(By.tagName("h1"));
            } catch (NoSuchElementException ex) {
                System.out.println("Element <h1> not found");
            }

            int subMenu = driverChrome.findElements(By.cssSelector("ul#box-apps-menu > li:nth-child(" + eSection + ") li")).size();
            int subSection = 2;
            while (subSection <= subMenu) {
                Thread.sleep(200);
                driverChrome.findElement(By.cssSelector("ul#box-apps-menu > li:nth-child(" + eSection + ") li:nth-child(" + subSection + ")")).click();
                System.out.println(subSection);
                try {
                    WebElement header = driverChrome.findElement(By.id("content"));
                    header.findElement(By.tagName("h1"));
                } catch (NoSuchElementException ex) {
                    System.out.println("Element <h1> not found");
                }
                subSection++;
            }
            eSection++;

        }

    }

    @After
    public void stop(){
        driverChrome.quit();
        driverChrome = null;
    }

}
