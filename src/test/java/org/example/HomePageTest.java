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

public class HomePageTest {

    public static WebDriver webDriver;
    private static final String homePageUrl = "https://practice.automationtesting.in/";
    private static final String myAcc = "//*[@id=\"menu-item-50\"]/a";
    private static final String slider = "//*[@id=\"n2-ss-6\"]/div[1]/div/div";
    private static final String children = "./child::*";



    @BeforeAll
    public static void initiate() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("Start-Maximized");
        options.addExtensions(new File("src/test/java/resources/chromeExtensions/Adblocker2.crx"));
        /* Original code:
        ChromeDriver webDriver = new ChromeDriver(options);

        Due to an error line changed to the following:
        */
        webDriver = new ChromeDriver(options);
        webDriver.get(homePageUrl);
    }

    @AfterAll
    public static void close() {
        webDriver.close();
        webDriver.quit();
    }

    @Given("the browser is open")
    public void the_browser_is_open() {
        /*
        Original code:
        boolean browserIsOpen;
        try {
            webDriver.getWindowHandles();
            browserIsOpen = true;
        } catch (NullPointerException e) {
            browserIsOpen = false;
        }
        Assertions.assertTrue(browserIsOpen, "The browser is not open!");

        For less code lines but not secured code the above code is replaced by the following:
        */
        Assertions.assertNotNull(webDriver, "The browser is not open!");
    }

    @Given("the home page is reached")
    public void the_home_page_is_reached() {
        Assertions.assertEquals(
                webDriver.getCurrentUrl().trim(),
                homePageUrl,
                "The wrong page is open!"
        );
    }

    @When("there are {int} slides")
    /*
     Original code:
     public void there_are_slides(Integer int1) {
            WebElement sliderFrame = webDriver.findElement(By.xpath(slider));

     Changed the variable name to something more meaningful.
    */
    public void there_are_slides(Integer expectedSliderSize) {
        WebElement sliderFrame = webDriver.findElement(By.xpath(slider));
        /*
        Original code:
                List<WebElement> sliderChildren = sliderFrame.findElements(By.xpath(children));
                Assertions.assertEquals(sliderChildren.size(), int1,
                "Incorrect number of slides was found!"
        Found a mistake in the given code as the Assertion structure should be first Expected value,
        followed by Actual value, and lastly by the string message.
        In the given code above variables are listed: Actual value, Expected value, string message.
        The code above was replaced by the following solution:
        */
        int actualSize = sliderFrame.findElements(By.xpath(children)).size();
        Assertions.assertEquals(expectedSliderSize, actualSize,
                "Incorrect number of slides was found!"
        );
    }

    @When("my account button is clicked")
    public void my_account_button_is_clicked() {
        boolean buttonIsActive;
        WebElement myAccount = webDriver.findElement(By.xpath(myAcc));
        try {
            WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.elementToBeClickable(myAccount));
            buttonIsActive = true;
        } catch (WebDriverException e) {
            buttonIsActive = false;
        }
        Assertions.assertTrue(buttonIsActive, "The button is not clickable!");
        myAccount.click();
    }

    @Then("we move to the next page")
    public void we_move_to_the_next_page() {
        /*
        Original code:
           Assertions.assertNotEquals(
                webDriver.getCurrentUrl().trim(),
                homePageUrl,
                "Still on the Home Page!"
        Found a mistake in the given code as the Assertion structure should be first Expected value,
        followed by Actual value, and lastly by the string message.
        In the given code above variables are listed: Actual value, Expected value, string message.

        To be more precise that the user is actually on My Account page after clicking on the My Account button
        introducing the variable myAccUrl with it's value, and checking that user is on this exact page.
        The code above was Replaced by the following solution:
        */
        String myAccUrl = "https://practice.automationtesting.in/my-account/";
        Assertions.assertEquals(myAccUrl,
                webDriver.getCurrentUrl().trim(),
                "Still on the Home Page!");
    }
}
