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
public class ErrorPageTest extends BaseSeleniumTest {

    @Test
    void testErrorPage(){
        driver.get("http://localhost:" + FRONTEND_PORT + "/pagethatdoesntexist");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("/html/body/div/div/button")
        ));
    }

    @Test
    void redirectToHomePage(){
        driver.get("http://localhost:" + FRONTEND_PORT + "/pagethatdoesntexist");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("/html/body/div/div/button")
        ));

        WebElement backHomeButton = driver.findElement(By.xpath("/html/body/div/div/button"));

        backHomeButton.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.className("mainInfo")
        ));
    }
}
