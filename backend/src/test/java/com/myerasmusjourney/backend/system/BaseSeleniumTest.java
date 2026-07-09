package com.myerasmusjourney.backend.system;


import com.myerasmusjourney.backend.TestDataBase;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class BaseSeleniumTest extends TestDataBase {

    @LocalServerPort
    protected int port;

    protected WebDriver driver;

    private static final int FRONTEND_PORT = 4173;

    private Process frontendProcess;

    private void waitForFrontend() throws InterruptedException {

        String url = "http://127.0.0.1:" + FRONTEND_PORT;

        long timeout = System.currentTimeMillis() + 30_000;

        while (System.currentTimeMillis() < timeout) {
            try {
                HttpURLConnection connection =
                        (HttpURLConnection) new URL(url).openConnection();

                connection.setRequestMethod("GET");
                connection.setConnectTimeout(1000);
                connection.setReadTimeout(1000);

                int responseCode = connection.getResponseCode();
                System.out.println("Frontend response: " + responseCode);

                return;

            } catch (IOException e) {
                System.out.println("Frontend not ready: " + e.getMessage());
            }
            Thread.sleep(250);
        }

        throw new IllegalStateException("Frontend did not start.");
    }

    private void startWebDriver(){
        try {
            System.out.println("4. Creating WebDriver");
            FirefoxOptions options = new FirefoxOptions();
            options.addArguments("--headless");
            driver = new FirefoxDriver(options);
            System.out.println("FirefoxDriver created.");
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    private void waitForApi() {

        String url = "http://localhost:" + port + "/api/experiences/";

        long timeout = System.currentTimeMillis() + 5_000; // 5 seconds

        while (System.currentTimeMillis() < timeout) {
            try {
                HttpURLConnection connection =
                        (HttpURLConnection) new URL(url).openConnection();

                connection.setRequestMethod("GET");
                connection.setConnectTimeout(1000);
                connection.setReadTimeout(1000);

                if (connection.getResponseCode() == 200) {
                    return;
                }

            } catch (IOException ignored) {
                System.out.println("API not yet ready.");
            }

            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        }

        throw new IllegalStateException("API did not start within the timeout.");
    }

    @BeforeAll
    void setUp() {
        ProcessBuilder processBuilder = new ProcessBuilder(
                "bash",
                "-ic",
                "pnpm --filter web dev --port " + FRONTEND_PORT
        );

        processBuilder.directory(new File("../frontend"));
        processBuilder.environment().put(
                "VITE_API_URL",
                "http://127.0.0.1:" + port + "/api"
        );
        processBuilder.redirectErrorStream(true);

        try {
            waitForApi();
            System.out.println("1. API ready");

            frontendProcess = processBuilder.start();
            System.out.println("2. Process started");

            waitForFrontend();
            System.out.println("3. Frontend ready");

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Unable to start frontend.", e);
        }
    }

    @BeforeEach
    void setUpWebDriver(){
        startWebDriver();
        System.out.println("5. Opening page");
        driver.get("http://localhost:" + FRONTEND_PORT);
        System.out.println("6. Page opened");
    }


    @AfterEach
    void quitDriver(){
        driver.quit();
    }

    @AfterAll
    void tearDown() {
        System.out.println("Stopping frontend...");

        if (frontendProcess != null) {

            frontendProcess.toHandle().descendants().forEach(ProcessHandle::destroyForcibly);
            try {
                frontendProcess.waitFor(5, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
