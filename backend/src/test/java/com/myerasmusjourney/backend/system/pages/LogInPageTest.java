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
public class LogInPageTest extends BaseSeleniumTest {

    @Test
    void testSuccessfulLogIn(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.linkText("Log in")
        ));

        WebElement linkToLogIn = driver.findElement(By.linkText("Log in"));

        linkToLogIn.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("email")
        ));

        WebElement email = driver.findElement(By.id("email"));
        WebElement password = driver.findElement(By.id("password"));

        email.sendKeys("test@email.com");
        password.sendKeys("password");

        WebElement button = driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/form/button"));
        button.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("profileTitle")
        ));
    }

    @Test
    void movingToSignUpPage(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.linkText("Log in")
        ));

        WebElement linkToLogIn = driver.findElement(By.linkText("Log in"));

        linkToLogIn.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("email")
        ));

        WebElement linkToSignUp = driver.findElement(By.linkText("Sign up →"));

        linkToSignUp.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("signUpTitle")
        ));
    }
}
