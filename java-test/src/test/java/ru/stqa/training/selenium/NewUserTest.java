package ru.stqa.training.selenium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import java.util.Random;

public class NewUserTest extends TestBase {

    @Test

    public void newUserLoginLogout() throws InterruptedException {

        app.wd.get("http://localhost:8080/litecart/en/create_account");

        Select selectCountry = new Select(app.wd.findElement(By.cssSelector("select[name=country_code]")));
        selectCountry.selectByVisibleText("United States");

        Account newUserAccount = new Account();
        newUserAccount.setFirstName(generateRandomString());
        newUserAccount.setLastName(generateRandomString());
        newUserAccount.setAddress1(generateRandomString());
        newUserAccount.setPostcode(generateRandomIntString(5));
        newUserAccount.setCity(generateRandomString());
        newUserAccount.setCountry("United States");
        newUserAccount.setZone(generateRandomInt(0, 64));
        newUserAccount.setEmail(generateRandomEmail());
        newUserAccount.setPhone(generateRandomIntString(10));
        newUserAccount.setPassword(generateRandomString());

        app.wd.findElement(By.cssSelector("[name=firstname")).sendKeys(newUserAccount.getFirstName());
        app.wd.findElement(By.cssSelector("[name=lastname]")).sendKeys(newUserAccount.getLastName());
        app.wd.findElement(By.cssSelector("[name=address1]")).sendKeys(newUserAccount.getAddress1());
        app.wd.findElement(By.cssSelector("[name=postcode]")).sendKeys(newUserAccount.getPostcode());
        app.wd.findElement(By.cssSelector("[name=city]")).sendKeys(newUserAccount.getCity());

        Select selectZone = new Select(app.wd.findElement(By.cssSelector("select[name=zone_code]")));
        selectZone.selectByIndex(generateRandomInt(0, selectZone.getOptions().size() - 1));

        app.wd.findElement(By.cssSelector("[name=email]")).sendKeys(newUserAccount.getEmail());
        app.wd.findElement(By.cssSelector("[name=phone]")).sendKeys(newUserAccount.getPhone());
        app.wd.findElement(By.cssSelector("[name=password]")).sendKeys(newUserAccount.getPassword());
        app.wd.findElement(By.cssSelector("[name=confirmed_password]")).sendKeys(newUserAccount.getPassword());
        app.wd.findElement(By.cssSelector("[name=create_account]")).click();
        Thread.sleep(3000);

        logout();

        login(newUserAccount.getEmail(), newUserAccount.getPassword());

        logout();
    }


    public void login(String login, String password) throws InterruptedException {
        app.wd.findElement(By.cssSelector("input[name=email]")).sendKeys(login);
        app.wd.findElement(By.cssSelector("input[name=password]")).sendKeys(password);
        app.wd.findElement(By.cssSelector("[name=login]")).click();
        Thread.sleep(3000);
    }

    public void logout() throws InterruptedException {
        app.wd.findElement(By.cssSelector("a[href*=logout]")).click();
        Thread.sleep(3000);
    }

    public static String generateRandomString() {
        Random rnd = new Random();
        return "test-" + (rnd.nextInt(1000));
    }

    public static String generateRandomEmail() {
        Random rnd = new Random();
        return "email-" + (rnd.nextInt(1000)) + "@mail.ru";
    }

    public static String generateRandomIntString(int length) {
        Random rnd = new Random();
        String str = "";
        for (int i = 0; i < length; i++) {
            str = str + (rnd.nextInt(10));
        }
        return str;
    }

    public static int generateRandomInt(int start, int end) {
        return start + (int) (Math.random() * end);
    }
}

