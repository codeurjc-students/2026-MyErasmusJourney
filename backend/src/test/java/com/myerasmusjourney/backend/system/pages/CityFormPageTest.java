package com.myerasmusjourney.backend.system.pages;

import com.myerasmusjourney.backend.system.AuthenticatedSeleniumTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@Tag("system")
public class CityFormPageTest extends AuthenticatedSeleniumTest {

    @Test
    void testAddCity(){
        authenticateAdminTest();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("profileTitle")
        ));

        WebElement buttonToCityForm = driver.findElement(By.xpath("/html/body/div/div/div[1]/div[1]/div[2]/button[1]"));

        buttonToCityForm.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("city")
        ));

        WebElement name = driver.findElement(By.id("city"));
        WebElement country = driver.findElement(By.id("country"));
        WebElement description = driver.findElement(By.id("description"));

        name.sendKeys("Munich");
        country.sendKeys("Germany");
        description.sendKeys("A city of germany");

        WebElement submit = driver.findElement(By.xpath("/html/body/div/div/form/div[1]/div[3]/button"));

        submit.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("/html/body/div/div/p")
        ));
    }
}
