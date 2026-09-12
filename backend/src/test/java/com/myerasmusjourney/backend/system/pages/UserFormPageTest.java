package com.myerasmusjourney.backend.system.pages;

import com.myerasmusjourney.backend.system.AuthenticatedSeleniumTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.Assert.assertTrue;

@Tag("system")
public class UserFormPageTest extends AuthenticatedSeleniumTest {

    @Test
    void testUpdatingUser(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("profileTitle")
        ));

        WebElement buttonToEdit = driver.findElement(By.xpath("/html/body/div/div/div[1]/div[1]/div[2]/button[1]"));

        buttonToEdit.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("studyLocation")
        ));

        WebElement fullName = driver.findElement(By.id("fullName"));

        WebElement submitButton = driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/form/div[6]/button"));

        fullName.clear();
        fullName.sendKeys("New user name");

        submitButton.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("profileTitle")
        ));

        WebElement fullNameChanged = driver.findElement(By.xpath("/html/body/div/div/div[1]/div[1]/div[1]/div[1]/p[2]"));

        assertTrue(fullNameChanged.getText().contains("New user name"));
    }
}
