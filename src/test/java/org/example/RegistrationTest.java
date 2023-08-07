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

public class RegistrationTest {
    public static WebDriver webDriver;
    private static final String myAccUrl = "https://practice.automationtesting.in/my-account/";
    private static final String regSection = "//*[@id=\"customer_login\"]/div[2]";
    private static final String regEmail = "//*[@id=\"reg_email\"]";
    private static final String regPass = "//*[@id=\"reg_password\"]";
    private static final String regButton = "//*[@id=\"customer_login\"]/div[2]/form/p[3]/input[3]";
    private static final String error = "//*[@id=\"page-36\"]/div/div[1]/ul";
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

    @Given("browser is open")
    public void browser_is_open() {
        Assertions.assertNotNull(webDriver,
                "The browser is not open!");
    }

    @Then("the My Account page is reached")
    public void the_my_account_page_is_reached() {
        Assertions.assertEquals(myAccUrl,
                webDriver.getCurrentUrl().trim(),
                "Still on the Home Page!");

        }

    @Given("Register section is available on page")
    public void register_section_is_available_on_page() {
        WebElement registerSection = webDriver.findElement(By.xpath(regSection));
        Assertions.assertTrue(registerSection.isDisplayed(),
                "Register section is not available!");
    }
    @When("Register input fields are present")
    public void register_input_fields_are_present() {
        WebElement emailField = webDriver.findElement(By.xpath(regEmail));
        WebElement passwordField = webDriver.findElement(By.xpath(regPass));
        Assertions.assertTrue(emailField.isDisplayed() && passwordField.isDisplayed(),
                "Register input fields are not present!");
    }

    @Then("send existing input for Email address")
    public void send_existing_input_for_email_address() {
        WebElement emailField = webDriver.findElement(By.xpath(regEmail));
        emailField.sendKeys("ivan.ivanov@email.com");
    }
    @Then("send existing input for Password")
    public void send_existing_input_for_password() {
        WebElement passwordField = webDriver.findElement(By.xpath(regPass));
        passwordField.sendKeys("@Test-Test-12345");
    }

    @Then("Click on Register button")
    public void click_on_register_button() {
        WebElement registerButton = webDriver.findElement(By.xpath(regButton));
        registerButton.click();

    }

    @Then("User is on My Account page")
    public void user_is_on_my_account_page() {
        try {
            WebElement dashboardElement = webDriver.findElement(By.className(myAccContent));
            Assertions.assertTrue(dashboardElement.isDisplayed(),
                "User is not registered and logged in, or My dashboard page is not displayed!");
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
    }
    }
}