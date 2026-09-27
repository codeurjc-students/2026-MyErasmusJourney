package com.myerasmusjourney.backend.system.pages;

import com.myerasmusjourney.backend.system.BaseSeleniumTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("system")
public class ExperiencesPageTest extends BaseSeleniumTest {

    @Test
    void testRendersData(){

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.linkText("Experiences")
        ));

        WebElement linkToExperience = driver.findElement(By.linkText("Experiences"));

        linkToExperience.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("experience-1")
        ));
        for (int id = 1; id<7; id++){
            assertTrue(driver.findElement(By.id("experience-"+id)).isDisplayed());
        }
    }

    @Test
    void testFilterExperiences(){

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.linkText("Experiences")
        ));

        WebElement linkToExperience = driver.findElement(By.linkText("Experiences"));

        linkToExperience.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("/html/body/div/div/div/div/form/div[4]/div/label[7]/input")
        ));

        WebElement studies = driver.findElement(By.xpath("/html/body/div/div/div/div/form/div[4]/div/label[7]/input"));

        studies.click();

        List<WebElement> experiences = driver.findElements(By.cssSelector("[id^='experience-']"));

        for (WebElement experienceDiv: experiences){
            experienceDiv.getText().contains("Studies");
        }
    }


}
