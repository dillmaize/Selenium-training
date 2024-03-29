package ru.stqa.training.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static ru.stqa.training.selenium.TestBase.app;

public class ApplicationManager {
    private final Properties properties;
    WebDriver wd;
    private WebDriverWait wait;
    private int implicitlyWait = 1;


    private String browser;


    public ApplicationManager(String browser) {
        this.browser = browser;
        properties = new Properties();

    }


    public void init() throws IOException {
        // String target = System.getProperty("target", "local");
        // properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));

        if (browser.equals(BrowserType.FIREFOX)) {
            wd = new FirefoxDriver();
        } else if (browser.equals(BrowserType.CHROME)) {
            wd = new ChromeDriver();
        } else if (browser.equals(BrowserType.IE)) {
            wd = new InternetExplorerDriver();
        }
        wd.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        // wd.get(properties.getProperty("web.baseUrl"));
        wait = new WebDriverWait(wd, 10);
    }

    public void stop() {
        wd.quit();
        wd = null;
    }

    public static void login() {
        app.wd.findElement(By.name("username")).sendKeys("admin");
        app.wd.findElement(By.name("password")).sendKeys("admin");
        app.wd.findElement(By.name("login")).click();

    }

}
