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
public class DetailedExperiencePage extends BaseSeleniumTest {

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

        WebElement linkToFirstExperience = driver.findElement(By.xpath("/html/body/div/div/div/main/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]/a"));

        linkToFirstExperience.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("/html/body/div/div/div/main/div/div[2]/h3")
        ));
    }
}
