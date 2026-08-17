package com.myerasmusjourney.backend.system;

import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AuthenticatedSeleniumTest extends BaseSeleniumTest{

    @Override
    @BeforeEach
    void setUpWebDriver(){
        startWebDriver();
        driver.get("http://localhost:" + FRONTEND_PORT);
        authenticateTest();
    }

    private void authenticateTest(){
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

    private void logOutUser(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("profileTitle")
        ));

        WebElement logOutButton = driver.findElement(By.xpath("/html/body/div/div/button"));

        logOutButton.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.linkText("Log in")
        ));
    }

    protected void authenticateAdminTest(){
        logOutUser();

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

        email.sendKeys("testadmin@email.com");
        password.sendKeys("password");

        WebElement button = driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/form/button"));
        button.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("profileTitle")
        ));
    }
}
