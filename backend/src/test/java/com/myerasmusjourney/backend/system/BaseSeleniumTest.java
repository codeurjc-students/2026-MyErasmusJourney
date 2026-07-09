package com.myerasmusjourney.backend.system;


import com.myerasmusjourney.backend.TestDataBase;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
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

    private void waitForFrontend(Process process) throws IOException {

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream()));

        String line;

        while ((line = reader.readLine()) != null) {

            System.out.println(line);

            if (line.contains("Local:")) {
                return;
            }
        }

        throw new IllegalStateException("Frontend terminated before becoming ready.");
    }

    private void startWebDriver(){
        try {
            FirefoxOptions options = new FirefoxOptions();
            options.addArguments("--headless");
            driver = new FirefoxDriver(options);
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
            frontendProcess = processBuilder.start();
            waitForFrontend(frontendProcess);
        } catch (IOException e) {
            throw new RuntimeException("Unable to start frontend.", e);
        }
    }

    @BeforeEach
    void setUpWebDriver(){
        startWebDriver();
        driver.get("http://localhost:" + FRONTEND_PORT);
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
