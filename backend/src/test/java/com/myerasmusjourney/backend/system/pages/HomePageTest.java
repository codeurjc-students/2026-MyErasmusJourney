package com.myerasmusjourney.backend.system.pages;

import com.myerasmusjourney.backend.system.BaseSeleniumTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@Tag("system")
public class HomePageTest extends BaseSeleniumTest {

    @Test
    void testHomePage(){

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.className("mainInfo")
        ));
    }
}
