package org.example;
import java.io.File;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.cucumber.java.BeforeAll;
import io.github.bonigarcia.wdm.WebDriverManager;

public class AppTest {
    @BeforeAll
    public static void initialSetup() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("Start-Maximized");
        // Add block will be added to this repo
        options.addExtensions(new File("src/test/java/resources/chromeExtensions/Adblocker2.crx"));
        WebDriver webDriver = new ChromeDriver(options);
        webDriver.close();
    }
}
