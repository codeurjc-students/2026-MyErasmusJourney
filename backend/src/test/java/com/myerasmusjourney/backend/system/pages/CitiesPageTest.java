package com.myerasmusjourney.backend.system.pages;


import com.myerasmusjourney.backend.system.BaseSeleniumTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.Assert.assertTrue;

@Tag("system")
public class CitiesPageTest extends BaseSeleniumTest {

    @Test
    void testTrendingCities(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        WebElement linkToCitiesPage = driver.findElement(By.linkText("Cities"));

        linkToCitiesPage.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("/html/body/div/div/div/aside/div/div[1]")
        ));

        assertTrue(
                driver.findElement(By.xpath("/html/body/div/div/div/aside/div/div[2]")).isDisplayed()
                &&
                        driver.findElement(By.xpath("/html/body/div/div/div/aside/div/div[3]")).isDisplayed()
        );
    }

}