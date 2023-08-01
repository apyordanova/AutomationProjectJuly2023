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
import java.util.List;

public class HomePageTest {

    public static WebDriver webDriver;
    private static final String myAcc = "//*[@id=\"menu-item-50\"]/a";
    private static final String slider = "//*[@id=\"n2-ss-6\"]/div[1]/div/div";
    private static final String children = "./child::*";
    private static final String homePageUrl = "https://practice.automationtesting.in/";

    @BeforeAll
    public static void initiate() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("Start-Maximized");
        options.addExtensions(new File("src/test/java/resources/chromeExtensions/Adblocker2.crx"));
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
        boolean browserIsOpen;
        try {
            webDriver.getWindowHandles();
            browserIsOpen = true;
        } catch (NullPointerException e) {
            browserIsOpen = false;
        }
        Assertions.assertTrue(browserIsOpen, "The browser is not open!");
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
    public void there_are_slides(Integer int1) {
        WebElement sliderFrame = webDriver.findElement(By.xpath(slider));
        List<WebElement> sliderChildren = sliderFrame.findElements(By.xpath(children));
        Assertions.assertEquals(sliderChildren.size(), int1,
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
        Assertions.assertNotEquals(
                webDriver.getCurrentUrl().trim(),
                homePageUrl,
                "Still on the Home Page!"
        );
    }
}
