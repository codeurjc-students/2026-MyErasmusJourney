package com.myerasmusjourney.backend.system.pages;

import com.myerasmusjourney.backend.system.AuthenticatedSeleniumTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.Assert.assertTrue;

@Tag("system")
public class DetailedExperiencePageTest extends AuthenticatedSeleniumTest {

    private void goToExperience(){

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.linkText("Experiences")
        ));

        WebElement linkToExperience = driver.findElement(By.linkText("Experiences"));

        linkToExperience.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("experience-1")
        ));

        WebElement linkToFirstExperience = driver.findElement(By.xpath("/html/body/div/div/div/main/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]/a"));

        linkToFirstExperience.click();
    }

    @Test
    void testRendersData(){

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        goToExperience();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("/html/body/div/div/div/main/div/div[2]/h3")
        ));
    }

    @Test
    void testPostAndRenderComments(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        goToExperience();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("/html/body/div/div/div/main/div/div[2]/h3")
        ));

        WebElement commentInput = driver.findElement(By.xpath("/html/body/div/div/div/aside/div[2]/input"));
        WebElement postButton = driver.findElement(By.xpath("/html/body/div/div/div/aside/div[2]/button"));

        commentInput.sendKeys("New comment");
        postButton.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("/html/body/div/div/div/aside/div[1]/div/div[1]")
        ));

        WebElement newComment = driver.findElement(By.xpath("/html/body/div/div/div/aside/div[1]/div/div[1]"));
        assertTrue(newComment.getText().contains("New comment"));
    }

    @Test
    void testLogInLink(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        logOutUser();

        goToExperience();

        WebElement signInLink = driver.findElement(By.linkText("Sign in"));
        signInLink.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("email")
        ));
    }
}
