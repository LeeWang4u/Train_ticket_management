package features;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TrainSearchTestTo {
    public static void main(String[] args) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        options.setExperimentalOption("useAutomationExtension", false);
        WebDriver driver = new ChromeDriver(options);

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Access the main page
            driver.get("http://localhost:5173");

            // Enter "From" station
            WebElement fromInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//label[contains(text(),'Ga đi')]/following-sibling::input")
            ));
            fromInput.sendKeys("Hà Nội");

            // Enter "To" station
            WebElement toInput = driver.findElement(
                    By.xpath("//label[contains(text(),'Ga đến')]/following-sibling::input")
            );

            toInput.sendKeys("Sài Gònn");


            // Enter date
            WebElement dateInput = driver.findElement(By.cssSelector("input[type='date']"));
//            dateInput.sendKeys("2025-07-15"); // Format: yyyy-MM-dd
            dateInput.sendKeys("07-15-2025"); // Format: yyyy-MM-dd

            // Click Search button
            WebElement searchButton = driver.findElement(
                    By.xpath("//button[contains(text(),'Tìm kiếm')]")
            );
            searchButton.click();

            // Verify navigation to booking page
            wait.until(ExpectedConditions.urlContains("/booking"));

            // Wait for booking page title
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("booking-title")));

            System.out.println("✅ Test passed: Successfully navigated to booking page.");

            // Pause for 3 seconds
            Thread.sleep(3000);

        } catch (Exception e) {
            System.out.println("❌ Test failed: " + e.getMessage());
        } finally {
            driver.quit();
        }
    }
}

