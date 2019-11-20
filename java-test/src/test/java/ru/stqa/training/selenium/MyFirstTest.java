package ru.stqa.training.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;


public class MyFirstTest {

    public static void main(String[] args) {

        WebDriver driver = new ChromeDriver();

        //open seleniumhq.org
        driver.get("https://www.seleniumhq.org/");

        driver.close();
    }
}



