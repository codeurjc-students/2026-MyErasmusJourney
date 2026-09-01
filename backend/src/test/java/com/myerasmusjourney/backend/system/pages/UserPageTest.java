package com.myerasmusjourney.backend.system.pages;

import com.myerasmusjourney.backend.system.AuthenticatedSeleniumTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("system")
public class UserPageTest extends AuthenticatedSeleniumTest {

    private void createExperience(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        WebElement addExperienceButton = driver.findElement(By.xpath("/html/body/div/div/div[1]/div[1]/div[2]/button[3]"));

        addExperienceButton.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("title")
        ));

        WebElement title = driver.findElement(By.id("title"));
        WebElement description = driver.findElement(By.id("description"));
        WebElement rating = driver.findElement(By.id("rating"));
        WebElement date = driver.findElement(By.id("date"));
        WebElement location = driver.findElement(By.id("location"));
        Select select = new Select(location);
        WebElement documentation = driver.findElement(By.xpath("/html/body/div/div/form/div[1]/div[2]/div/label[2]"));
        WebElement submit = driver.findElement(By.xpath("/html/body/div/div/form/div[1]/div[5]/button"));

        title.sendKeys("Selenium test");
        description.sendKeys("This is an experience created by a Selenium test");
        date.sendKeys("01/13/2023");
        rating.sendKeys("5.3");
        select.selectByIndex(1);
        documentation.click();
        submit.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("/html/body/div/div/div/main/div/div[2]/h3")
        ));
    }

    private void postComment(){
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
    }

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

    @Test
    void testRenderingUserExperiences(){
        authenticateWithSpecificUser("exampleuser1@email.com");

        WebElement experienceTitle = driver.findElement(By.xpath("/html/body/div/div/div[2]/div[1]/div/div/div[1]/p"));

        assertEquals("Example experience 1", experienceTitle.getText());
    }

    @Test
    void testRendersNewExperiences(){
        authenticateWithSpecificUser("exampleuser1@email.com");

        createExperience();

        WebElement userLink = driver.findElement(By.xpath("/html/body/div/header/nav/div/a"));

        userLink.click();

        WebElement firstExperienceTitle = driver.findElement(By.xpath("/html/body/div/div/div[2]/div[1]/div/div/div[1]/p"));

        assertEquals("Selenium test", firstExperienceTitle.getText());
    }

    @Test
    void testRenderingUserComments(){
        authenticateWithSpecificUser("exampleuser1@email.com");

        WebElement commentDescription = driver.findElement(By.xpath("/html/body/div/div/div[2]/div[2]/div/div/div[1]/p"));

        assertEquals("My opinion or point of view regarding the experience", commentDescription.getText());
    }

    @Test
    void testRendersNewComments(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        authenticateWithSpecificUser("exampleuser1@email.com");

        postComment();

        WebElement userLink = driver.findElement(By.xpath("/html/body/div/header/nav/div/a"));

        userLink.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("/html/body/div/div/div[2]/div[2]/div/div[1]/div[1]/p")
        ));

        WebElement firstCommentDescription = driver.findElement(By.xpath("/html/body/div/div/div[2]/div[2]/div/div[1]/div[1]/p"));

        assertEquals("New comment", firstCommentDescription.getText());
    }
}
