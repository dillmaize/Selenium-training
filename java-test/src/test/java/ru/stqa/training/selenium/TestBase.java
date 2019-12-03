package ru.stqa.training.selenium;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.remote.BrowserType;

public class TestBase {

    protected static final ApplicationManager app = new ApplicationManager(System.getProperty("browser", BrowserType.CHROME));

    @BeforeClass
    public static void setUp() throws Exception {
        app.init();
    }

    @AfterClass
    public static void tearDown() {
        app.stop();
    }
}
