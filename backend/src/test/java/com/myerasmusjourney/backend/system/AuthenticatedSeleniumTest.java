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
        authenticateUser("test@email.com");
    }

    private void authenticateUser(String username){
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

        email.sendKeys(username);
        password.sendKeys("password");

        WebElement button = driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/form/button"));
        button.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("profileTitle")
        ));
    }

    public void logOutUser(){
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

        authenticateUser("testadmin@email.com");
    }

    private void createUserToDelete(){
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
        email.sendKeys("delete@email.com");
        password.sendKeys("password");
        passwordConfirmation.sendKeys("password");
        submit.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("loginTitle")
        ));
    }

    protected void authenticateDeleteUserTest(){
        logOutUser();

        createUserToDelete();

        authenticateUser("delete@email.com");
    }

    protected void authenticateWithSpecificUser(String email){
        logOutUser();

        authenticateUser(email);
    }
}
