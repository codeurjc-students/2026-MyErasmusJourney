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
public class SignUpPageTest extends BaseSeleniumTest {

    @Test
    void testFormRender(){

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.linkText("Sign up")
        ));

        WebElement linkToSignUp = driver.findElement(By.linkText("Sign up"));

        linkToSignUp.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("signUpTitle")
        ));

        WebElement fullName = driver.findElement(By.id("fullName"));
        WebElement displayName = driver.findElement(By.id("displayName"));
        WebElement email = driver.findElement(By.id("email"));
        WebElement password = driver.findElement(By.id("password"));
        WebElement passwordConfirmation = driver.findElement(By.id("passwordConfirmation"));
        WebElement submit = driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/form/div[9]/button"));

        fullName.sendKeys("TestUser");
        displayName.sendKeys("Test");
        email.sendKeys("seleniumTest@email.com");
        password.sendKeys("password");
        passwordConfirmation.sendKeys("password");
        submit.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("loginTitle")
        ));
    }

    @Test
    void testLogInLink(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.linkText("Sign up")
        ));

        WebElement linkToSignUp = driver.findElement(By.linkText("Sign up"));

        linkToSignUp.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("signUpTitle")
        ));

        WebElement linkToLogIn = driver.findElement(By.linkText("Log in →"));

        linkToLogIn.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("loginTitle")
        ));
    }
}
