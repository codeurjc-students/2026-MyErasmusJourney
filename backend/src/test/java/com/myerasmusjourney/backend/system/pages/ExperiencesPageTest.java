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
                By.id("experience-1")
        ));

        List<Integer> ids = List.of(1,2,3,4);
        for (int id: ids){
            WebElement webElement = driver.findElement(By.id("experience-"+id)); //grabs experience from experiences page
            String textContent = webElement.getText();
            assertTrue(textContent.contains("Experiencia " + id));
            assertTrue(textContent.contains(LocalDate.now().toString()));
            assertTrue(textContent.contains("Descripcion " + id));
        }
    }
}
