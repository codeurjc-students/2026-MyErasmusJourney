package com.myerasmusjourney.backend.system.pages;

import com.myerasmusjourney.backend.system.BaseSeleniumTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@Tag("system")
public class AboutUsPageTest extends BaseSeleniumTest {

    @Test
    void testAboutUsPage(){

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.linkText("About Us")
        ));

        WebElement linkToAboutUs = driver.findElement(By.linkText("About Us"));

        linkToAboutUs.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.className("logo")
        ));
    }
}
