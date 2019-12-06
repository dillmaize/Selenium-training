package ru.stqa.training.selenium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertTrue;

public class AddNewProductTest extends TestBase {

    @Test
    public void addNewProduct() throws InterruptedException {
        app.wd.get("http://localhost:8080/litecart/admin/?category_id=0&app=catalog&doc=edit_product");
        ApplicationManager.login();
        Thread.sleep(2000);

        //create new product
        String productName = generateRandomString();

        //General
        setStatus(true);
        setRadioBtnStatus(getStatus());
        app.wd.findElement(By.name("name[en]")).sendKeys(productName);
        app.wd.findElement(By.name("code")).sendKeys("123");
        List<WebElement> categories = app.wd.findElements(By.cssSelector("input[name='categories[]']"));
        for (WebElement category : categories) {
            if (category.getAttribute("data-name").equals("Rubber Ducks") && !category.isSelected()) {
                category.click();
            } else if (category.isSelected()) {
                category.click();
            }
        }
        app.wd.findElement(By.cssSelector("select[name='default_category_id']")).sendKeys("Rubber Ducks");
        List<WebElement> productGroups = app.wd.findElements(By.cssSelector("input[name='product_groups[]']"));
        for (WebElement productGroup : productGroups) {
            if (!productGroup.isSelected()) {
                app.wd.findElement(By.cssSelector("div#tab-general input[value='1-3']")).click();
            }
        }
        app.wd.findElement(By.name("quantity")).clear();
        app.wd.findElement(By.name("quantity")).sendKeys("5", Keys.TAB);
        Thread.sleep(2000);
        String png = new File("src/test/resources/friends.png").getAbsolutePath();
        app.wd.findElement(By.cssSelector("input[type=file]")).sendKeys(png);
        app.wd.findElement(By.name("date_valid_from")).clear();
        app.wd.findElement(By.name("date_valid_from")).sendKeys("12/05/2019");
        app.wd.findElement(By.name("date_valid_to")).clear();
        app.wd.findElement(By.name("date_valid_to")).sendKeys("12/05/2021");

        //Information
        app.wd.findElement(By.linkText("Information")).click();
        Thread.sleep(2000);
        app.wd.findElement(By.cssSelector("[name=manufacturer_id] option[value='1']")).click();
        app.wd.findElement(By.name("keywords")).sendKeys("test");
        Thread.sleep(2000);
        app.wd.findElement(By.name("short_description[en]")).sendKeys("test");
        app.wd.findElement(By.cssSelector(".trumbowyg-editor")).sendKeys("test test test");
        app.wd.findElement(By.name("head_title[en]")).sendKeys("test");
        app.wd.findElement(By.name("meta_description[en]")).sendKeys("test");

        //Prices
        Thread.sleep(3000);
        app.wd.findElement(By.linkText("Prices")).click();
        app.wd.findElement(By.name("purchase_price")).clear();
        app.wd.findElement(By.name("purchase_price")).sendKeys("100");
        app.wd.findElement(By.cssSelector("[name=purchase_price_currency_code] option[value='USD']")).click();
        app.wd.findElement(By.name("save")).click();
        Thread.sleep(3000);

        //check that new product was created
        app.wd.get("http://localhost:8080/litecart/admin/?app=catalog&doc=catalog&category_id=1");
        Thread.sleep(2000);
        assertTrue(app.wd.findElement(By.cssSelector(".dataTable")).findElements(By.linkText(productName)).size() == 1);

    }

    private boolean status;

    public void setRadioBtnStatus(Boolean status) {
        if (status) {
            app.wd.findElement(By.cssSelector("input[name='status'][value='1']")).click();
        } else {
            app.wd.findElement(By.cssSelector("input[name='status'][value='0']")).click();
        }
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public static String generateRandomString() {
        Random rnd = new Random();
        return "Test-" + (rnd.nextInt(1000));
    }
}
