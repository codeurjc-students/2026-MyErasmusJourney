package com.myerasmusjourney.backend.system.pages;

import com.myerasmusjourney.backend.system.AuthenticatedSeleniumTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@Tag("system")
public class ExperiencesFormPageTest extends AuthenticatedSeleniumTest {

    @Test
    void testPostingExperience(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("profileTitle")
        ));

        WebElement buttonToAddExperience = driver.findElement(By.xpath("/html/body/div/div/div[1]/div[1]/div[2]/button[3]"));

        buttonToAddExperience.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("experienceFormTitle")
        ));

        WebElement title = driver.findElement(By.id("title"));
        WebElement description = driver.findElement(By.id("description"));
        WebElement rating = driver.findElement(By.id("rating"));
        WebElement date = driver.findElement(By.id("date"));
        WebElement location = driver.findElement(By.id("location"));
        Select select = new Select(location);
        WebElement culture = driver.findElement(By.xpath("/html/body/div/div/form/div[1]/div[2]/div/label[2]"));
        WebElement documentation = driver.findElement(By.xpath("/html/body/div/div/form/div[1]/div[2]/div/label[3]"));
        WebElement submit = driver.findElement(By.xpath("/html/body/div/div/form/div[1]/div[5]/button"));

        title.sendKeys("Selenium test");
        description.sendKeys("This is an experience created by a Selenium test");
        date.sendKeys("01/13/2023");
        rating.sendKeys("5.3");
        select.selectByIndex(1);
        culture.click();
        documentation.click();
        submit.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("/html/body/div/div/button")
        ));
    }
}
