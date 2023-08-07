package org.example;

import io.cucumber.java.AfterAll;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;

public class LogOutTest {
    public static WebDriver webDriver;
    private static final String myAccUrl = "https://practice.automationtesting.in/my-account/";
    private static final String logButton = "//*[@id=\"customer_login\"]/div[1]/form/p[3]/input[3]";
    private static final String logOutButton = "//*[@id=\"page-36\"]/div/div[1]/nav/ul/li[6]";
    private static final String error = "woocommerce-error";

    @BeforeAll
    public static void initiate() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("Start-Maximized");
        webDriver = new ChromeDriver(options);
        webDriver.get(myAccUrl);

        try {
            // Perform your setup
            webDriver.get(myAccUrl);
            WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("username")));
            webDriver.findElement(By.id("username")).sendKeys("ivan.ivanov@email.com");
            webDriver.findElement(By.id("password")).sendKeys("@Test-Test-12345");
            webDriver.findElement(By.xpath(logButton)).click();

            wait.until(ExpectedConditions.urlToBe(myAccUrl));

            boolean loginError = webDriver.findElements(By.className(error)).size() > 0;
            if (loginError) {
                throw new WebDriverException("The login was not successful");
            }
        } catch (Exception e) {
            throw new RuntimeException("The setup was not successful");
        }
    }

    @AfterAll
    public static void close() {
            webDriver.close();
            webDriver.quit();
    }

    @Given("the browser has been opened")
    public void the_browser_has_been_opened() {
        Assertions.assertNotNull(webDriver,
                "The browser is not open!");
    }

    @When("the Dashboard page is reached")
        public void the_dashboard_page_is_reached() {
            Assertions.assertEquals(myAccUrl,
                    webDriver.getCurrentUrl().trim(),
                    "Still on the Home Page!");
    }

    @When("the logout button is clicked")
    public void the_logout_button_is_clicked() {
        boolean logoutButtonIsClicked = true;
        WebElement logoutButton = webDriver.findElement(By.xpath(logOutButton));
        try {
            WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.elementToBeClickable(logoutButton));
            logoutButton.click();
        } catch (WebDriverException e) {
            logoutButtonIsClicked = false;
        }
        Assertions.assertTrue(logoutButtonIsClicked, "The logout button was not clocked!");
    }
    @Then("the user is logged out")
    public void the_user_is_logged_out() {
        boolean loggedOut = true;
        try {
            webDriver.findElement(By.xpath(myAccUrl));
            loggedOut = false;
        } catch (WebDriverException e) {
        }
        Assertions.assertTrue(loggedOut, "The user was not logged out!");
    }
}