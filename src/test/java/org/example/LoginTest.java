package org.example;

import io.cucumber.java.AfterAll;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.*;
import io.cucumber.java.en_scouse.An;
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

import java.time.Duration;

public class LoginTest {
    public static WebDriver webDriver;
    private static final String myAccUrl = "https://practice.automationtesting.in/my-account/";
    private static final String logSection = "//*[@id=\"customer_login\"]/div[1]";
    private static final String logEmail = "//*[@id=\"username\"]";
    private static final String logPass = "//*[@id=\"password\"]";
    private static final String logButton = "//*[@id=\"customer_login\"]/div[1]/form/p[3]/input[3]";
    private static final String myAccContent = "woocommerce-MyAccount-content";


    @BeforeAll
    public static void initiate() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("Start-Maximized");
        webDriver = new ChromeDriver(options);
        webDriver.get(myAccUrl);
    }

    @AfterAll
    public static void close() {
        webDriver.close();
        webDriver.quit();
    }

    @Given("browser has been open")
    public void browser_has_been_open() {
        Assertions.assertNotNull(webDriver,
                "The browser is not open!");
    }

    @Then("the My Account page reached")
    public void the_my_account_page_is_reached() {
        Assertions.assertEquals(myAccUrl,
                webDriver.getCurrentUrl().trim(),
                "Not on My Account Page!");
    }

    @Given("Login section is available on page")
    public void login_section_is_available_on_page() {
        WebElement loginSection = webDriver.findElement(By.xpath(logSection));
        Assertions.assertTrue(loginSection.isDisplayed(),
                "Register section is not available!");
    }

    @When("Login input fields are present")
    public void login_input_fields_are_present() {
        WebElement emailField = webDriver.findElement(By.xpath(logEmail));
        WebElement passwordField = webDriver.findElement(By.xpath(logPass));
        Assertions.assertTrue(emailField.isDisplayed() && passwordField.isDisplayed(),
                "Register input fields are not present!");
    }

    @When("Login button is clickable")
    public void login_button_is_clickable() {
        WebElement logInButton = webDriver.findElement(By.xpath(logButton));
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(logInButton));
    }

    @Then("send input for Email address")
    public void send_input_for_email_address() {
        WebElement emailField = webDriver.findElement(By.xpath(logEmail));
        emailField.sendKeys("ivan.ivanov@email.com");
    }

    @Then("send input for Password")
    public void send_input_for_password() {
            WebElement passwordField = webDriver.findElement(By.xpath(logPass));
            passwordField.sendKeys("@Test-Test-12345");
    }

    @Then("Click on Login button")
    public void click_on_login_button() {
        WebElement logInButton = webDriver.findElement(By.xpath(logButton));
        logInButton.click();
    }

    @Then("User is on My Account page")
    public void user_is_on_my_account_page() {
        WebElement dashboardElement = webDriver.findElement(By.className(myAccContent));
        Assertions.assertTrue(dashboardElement.isDisplayed(),
                "User is not registered and logged in, or My dashboard page is not displayed!");
    }
}