package ru.stqa.training.selenium;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class SortTests {

    private WebDriver driverChrome;

    @Before
    public void start() {
        driverChrome = new ChromeDriver();
    }

    @Test
    public void sortCountries() {
        driverChrome.get("http://localhost:8080/litecart/admin/?app=countries&doc=countries");
        driverChrome.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driverChrome.findElement(By.name("username")).sendKeys("admin");
        driverChrome.findElement(By.name("password")).sendKeys("admin");
        driverChrome.findElement(By.name("login")).click();

        //create a list of all country elements
        List<WebElement> countriesElements = driverChrome.findElements(By.cssSelector("tr.row"));

        //create a list of all country names
        List<String> countries = new ArrayList<>();
        for (WebElement country : countriesElements) {
            countries.add(country.getAttribute("textContent"));
        }

        //check that items in a list are in alphabetical order
        List<String> copy = countries;
        Collections.sort(countries);
        assertEquals(countries, copy);

        //create a list of every country with zones
        List<String> countriesWithZones = new ArrayList<>();
        for (WebElement country : countriesElements) {
            if (!country.findElement(By.cssSelector("tr.row > td:nth-child(6)")).getText().equals("0")) {
                countriesWithZones.add(country.findElement(By.cssSelector("td:nth-child(5)")).getAttribute("textContent"));
            }
        }

        //open every country with zones and check order of zones
        for (String country : countriesWithZones) {
            driverChrome.findElement(By.linkText(country)).click();
            List<WebElement> zoneElements = driverChrome.findElements(By.cssSelector("#table-zones tr > td:nth-child(3) > input"));
            List<String> zones = new ArrayList<>();
            for (WebElement zone : zoneElements) {
                zones.add(zone.getAttribute("value"));
            }
            List<String> copyZone = zones;
            Collections.sort(zones);
            assertEquals(zones, copyZone);
            driverChrome.get("http://localhost:8080/litecart/admin/?app=countries&doc=countries");
            System.out.println(zones);
            System.out.println(copyZone);

        }

    }

    @Test
    public void sortGeoZone() {
        driverChrome.get("http://localhost:8080/litecart/admin/?app=geo_zones&doc=geo_zones");
        driverChrome.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driverChrome.findElement(By.name("username")).sendKeys("admin");
        driverChrome.findElement(By.name("password")).sendKeys("admin");
        driverChrome.findElement(By.name("login")).click();


        //create a list of country elements
        List<WebElement> countryElements = driverChrome.findElements(By.cssSelector("tr.row td:nth-of-type(3) a"));

        //create a list of all country names
        List<String> countries = new ArrayList<>();
        for (WebElement country : countryElements) {
            countries.add(country.getAttribute("textContent"));
        }

        //open every country and check order of zones
        for (String country : countries) {
            driverChrome.findElement(By.linkText(country)).click();
            List<WebElement> zoneElements = driverChrome.findElements(By.cssSelector("#table-zones td:nth-of-type(3) option[selected]"));
            List<String> zones = new ArrayList<>();
            for (WebElement zone : zoneElements) {
                zones.add(zone.getAttribute("text"));
            }
            List<String> copy = zones;
            Collections.sort(zones);
            Assert.assertEquals(zones, copy);
            driverChrome.get("http://localhost:8080/litecart/admin/?app=geo_zones&doc=geo_zones");
        }

    }


    @After
    public void stop() {
        driverChrome.quit();
        driverChrome = null;
    }
}



