package com.myerasmusjourney.backend.system.pages;

import com.myerasmusjourney.backend.system.AuthenticatedSeleniumTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("system")
public class UserPageTest extends AuthenticatedSeleniumTest {

    @Test
    void testUserPage(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("profileTitle")
        ));

        WebElement displayName = driver.findElement(By.xpath("/html/body/div/div/div[1]/div[1]/div[1]/div[1]/p[1]"));
        WebElement fullName = driver.findElement(By.xpath("/html/body/div/div/div[1]/div[1]/div[1]/div[1]/p[2]"));
        WebElement email = driver.findElement(By.xpath("/html/body/div/div/div[1]/div[1]/div[1]/div[2]/p[1]"));

        assertTrue(displayName.getText().contains("testUser"));
        assertTrue(fullName.getText().contains("test"));
        assertTrue(email.getText().contains("test@email.com"));
    }

    @Test
    void testLogOutBtn(){
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

    @Test
    void deleteUserBtn(){
        authenticateDeleteUserTest();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("profileTitle")
        ));

        WebElement deleteButton = driver.findElement(By.xpath("/html/body/div/div/div[1]/div[1]/div[2]/button[2]"));

        deleteButton.click();

        wait.until(ExpectedConditions.alertIsPresent());

        Alert alert = driver.switchTo().alert();

        assertEquals(
                "This account is going to be deleted. This action cannot be undone. Are you certain?",
                alert.getText()
        );

        alert.accept();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.className("mainInfo")
        ));
    }

    @Test
    void deleteUserBtnAndCancellingDelete(){

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("profileTitle")
        ));

        WebElement deleteButton = driver.findElement(By.xpath("/html/body/div/div/div[1]/div[1]/div[2]/button[2]"));

        deleteButton.click();

        wait.until(ExpectedConditions.alertIsPresent());

        Alert alert = driver.switchTo().alert();

        assertEquals(
                "This account is going to be deleted. This action cannot be undone. Are you certain?",
                alert.getText()
        );

        alert.dismiss();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("profileTitle")
        ));
    }
}
