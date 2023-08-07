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
import java.time.Instant;

public class UserDetailsChangeTest {
    public static WebDriver webDriver;
    private static final String myAccUrl = "https://practice.automationtesting.in/my-account/";
    public static final String userDetailsUrl = "https://practice.automationtesting.in/my-account/edit-account/";
    private static final String logButton = "//*[@id=\"customer_login\"]/div[1]/form/p[3]/input[3]";
    private static final String error = "//*[@id=\"page-36\"]/div/div[1]/ul";
    private static final String accDetailsButton = "//*[@id=\"page-36\"]/div/div[1]/nav/ul/li[5]/a";
    private static final String accEmailField = "//*[@id=\"account_email\"]";
    private static final String saveChangesButton = "woocommerce-Button";
    private static final String successMessage= "woocommerce-message";

    @BeforeAll
    public static void initiate() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("Start-Maximized");
        options.addExtensions(new File("src/test/java/resources/chromeExtensions/Adblocker2.crx"));
        webDriver = new ChromeDriver(options);
        webDriver.get(myAccUrl);

        try {
            // Perform setup
            WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("username")));
            webDriver.findElement(By.id("username")).sendKeys("ivan.ivanov@email.com");
            webDriver.findElement(By.id("password")).sendKeys("@Test-Test-12345");
            webDriver.findElement(By.xpath(logButton)).click();
            webDriver.findElement(By.xpath(accDetailsButton)).click();
            wait.until(ExpectedConditions.urlToBe(userDetailsUrl));

            boolean loginError = webDriver.findElements(By.xpath(error)).size() > 0;
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

    @Given("the browser opened")
    public void the_browser_has_been_opened() {
        Assertions.assertNotNull(webDriver,
                "The browser is not open!");
    }

    @When("the My Details page is reached")
    public void the_my_details_page_is_reached() {
        Assertions.assertEquals(userDetailsUrl,
                webDriver.getCurrentUrl().trim(),
                "Still on the Home Page!");
    }

    @Then("email address field is present")
    public void email_address_field_is_present() {
        WebElement emailField = webDriver.findElement(By.xpath(accEmailField));
        Assertions.assertTrue(emailField.isDisplayed(),
                "Register input fields are not present!");
    }

    @Then("check the field is filled with correct address")
    public void check_the_field_is_filled_with_correct_address() {
       try {
            String loggedInEmail = "ivan.ivanov@email.com";
            WebElement emailElement = webDriver.findElement(By.xpath(accEmailField));
            String displayedEmail = emailElement.getAttribute("value");
            Assertions.assertEquals(loggedInEmail, displayedEmail,
                    "Displayed email not the same as logged in email");
            // Thread sleep is used to have more time to see the actions of the automated test
            Thread.sleep(1000);
       } catch (InterruptedException ignored) {
       }
    }

    @Given("the fields for First and Last name are present")
    public void the_fields_for_first_and_last_name_are_present() {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        WebElement firstNameField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("account_first_name")));
        WebElement lastNameField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("account_last_name")));
        Assertions.assertTrue(firstNameField.isDisplayed() && lastNameField.isDisplayed(),
                "First and Last name fields are not present");
    }
    @When("fields are cleared")
    public void fields_are_cleared() {
        try {
            By firstNameLocator = By.id("account_first_name");
            By lastNameLocator = By.id("account_last_name");

            WebElement firstNameField = webDriver.findElement(firstNameLocator);
            WebElement lastNameField = webDriver.findElement(lastNameLocator);

            firstNameField.clear();
            lastNameField.clear();
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
    }

    @Then("the user enters data in the fields")
    public void the_user_enters_data_in_the_fields() {
        // Assuming the IDs of the First and Last Name fields are "first_name" and "last_name"
        try {
            WebElement firstNameField = webDriver.findElement(By.id("account_first_name"));
            WebElement lastNameField = webDriver.findElement(By.id("account_last_name"));

            firstNameField.sendKeys("Ivan1");
            lastNameField.sendKeys("Ivanov1");
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
    }

    @Then("save button is actioned")
    public void save_button_is_actioned() {
        try {
            WebElement saveButton = webDriver.findElement(By.className(saveChangesButton));
            saveButton.click();
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
    }

    @Then("data is saved")
    public void data_is_saved() {
        try {
            WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
            By successMessageLocator = By.className(successMessage);

            WebElement successMessage = wait.until(ExpectedConditions.presenceOfElementLocated(successMessageLocator));
            Assertions.assertTrue(successMessage.isDisplayed(),
                "Data was not saved successfully");

            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
    }
}