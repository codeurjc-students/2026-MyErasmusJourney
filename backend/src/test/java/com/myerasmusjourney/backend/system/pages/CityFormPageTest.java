package com.myerasmusjourney.backend.system.pages;

import com.myerasmusjourney.backend.system.AuthenticatedSeleniumTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Tag("system")
public class CityFormPageTest extends AuthenticatedSeleniumTest {

    private void renderCityForm(){
        authenticateAdminTest();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));


        wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.cssSelector("[role='status']")
        ));


        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("profileTitle")
        ));

        WebElement buttonToCityForm = driver.findElement(By.xpath("/html/body/div/div/div[1]/div[1]/div[2]/button[1]"));

        buttonToCityForm.click();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.cssSelector("[role='status']")
        ));

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("city")
        ));
    }

    @Test
    void testAddCity(){
        authenticateAdminTest();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        renderCityForm();

        WebElement name = driver.findElement(By.id("city"));
        WebElement country = driver.findElement(By.id("country"));
        WebElement description = driver.findElement(By.id("description"));

        name.sendKeys("Munich");
        country.sendKeys("Germany");
        description.sendKeys("A city of germany");

        WebElement submit = driver.findElement(By.xpath("/html/body/div/div/form/div[1]/div[3]/button"));

        submit.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("/html/body/div/div/div/main/h3")
        ));

        WebElement cityName = driver.findElement(By.xpath("/html/body/div/div/div/main/h3"));
        assertTrue(cityName.getText().contains("Munich"));
    }

    @Test
    void previewButtonShowsCityMarkerOnMap() {
        authenticateAdminTest();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        renderCityForm();

        // The map exists and the WebGL canvas was created
        WebElement map = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("[data-testid='map']")));
        wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(
                map, By.cssSelector("canvas.maplibregl-canvas")));

        // No markers before previewing
        assertTrue(driver.findElements(By.cssSelector("[data-testid='map-marker']")).isEmpty());

        driver.findElement(By.id("city")).sendKeys("Madrid");
        driver.findElement(By.id("country")).sendKeys("Spain");
        driver.findElement(By.xpath("//button[normalize-space()='Preview City']")).click();

        wait.until(ExpectedConditions.numberOfElementsToBe(
                By.cssSelector("[data-testid='map-marker']"), 1));

        WebElement marker = driver.findElement(By.cssSelector("[data-testid='map-marker']"));
        assertEquals("Madrid", marker.getAttribute("aria-label"));
        assertEquals("preview", marker.getAttribute("data-marker-type"));

        map = driver.findElement(By.cssSelector("[data-testid='map']"));
        assertEquals("7", map.getAttribute("data-zoom"));
        assertEquals("1", map.getAttribute("data-marker-count"));
    }
}
