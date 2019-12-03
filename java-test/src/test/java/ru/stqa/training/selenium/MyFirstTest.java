package ru.stqa.training.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


public class MyFirstTest {

    public static void main(String[] args) {

        WebDriver driver = new ChromeDriver();

        //open seleniumhq.org
        driver.get("https://www.seleniumhq.org/");

        driver.close();
    }
}



