package features;


import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.logging.Logger;

public class TrainSearchResultTest {
    private static WebDriver driver;
    private static WebDriverWait wait;
    private static final Logger logger = Logger.getLogger(TrainSearchResultTest.class.getName());
    private static final int MAX_RETRIES = 3;

    @BeforeClass
    public static void setUp() {
        // Thiết lập ChromeDriver
        //System.setProperty("webdriver.chrome.driver", "path/to/chromedriver"); // Cập nhật đường dẫn
        System.setProperty("webdriver.chrome.driver", "src/test/drivers/chromedriver.exe");
        // Cấu hình ChromeOptions để tăng độ ổn định
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-extensions");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("your_website_url"); // Thay bằng URL thực tế
    }

    @AfterClass
    public static void tearDown() {
        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception e) {
                logger.severe("Error closing driver: " + e.getMessage());
            }
        }
    }

    private WebElement findElementWithRetry(By locator) {
        int attempts = 0;
        while (attempts < MAX_RETRIES) {
            try {
                return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            } catch (WebDriverException e) {
                attempts++;
                logger.warning("Attempt " + attempts + " failed for locator " + locator + ": " + e.getMessage());
                if (attempts == MAX_RETRIES) {
                    throw e;
                }
                try {
                    Thread.sleep(1000); // Wait before retry
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        return null;
    }

    private void switchToCorrectWindow() {
        String originalWindow = driver.getWindowHandle();
        Set<String> allWindows = driver.getWindowHandles();
        if (allWindows.size() > 1) {
            for (String window : allWindows) {
                if (!window.equals(originalWindow)) {
                    driver.switchTo().window(window);
                    logger.info("Switched to new window: " + window);
                    break;
                }
            }
        }
    }

    private void performSearch(String from, String to, String date) {
        try {
            // Kiểm tra và chuyển đổi window nếu cần
            switchToCorrectWindow();

            // Nhập ga đi
            WebElement fromInput = findElementWithRetry(By.cssSelector("input[placeholder='Ga đi']"));
            fromInput.clear();
            fromInput.sendKeys(from);
            WebElement fromSuggestion = findElementWithRetry(By.xpath("//li[contains(text(), '" + from + "')]"));
            fromSuggestion.click();

            // Nhập ga đến
            WebElement toInput = findElementWithRetry(By.cssSelector("input[placeholder='Ga đến']"));
            toInput.clear();
            toInput.sendKeys(to);
            WebElement toSuggestion = findElementWithRetry(By.xpath("//li[contains(text(), '" + to + "')]"));
            toSuggestion.click();

            // Chọn ngày
            WebElement datePicker = findElementWithRetry(By.cssSelector("input[type='date']"));
            datePicker.clear();
            datePicker.sendKeys(date);

            // Nhấn nút tìm kiếm
            WebElement searchButton = findElementWithRetry(By.xpath("//button[contains(text(), 'Tìm kiếm')]"));
            searchButton.click();
        } catch (Exception e) {
            logger.severe("Error performing search: " + e.getMessage());
            throw e;
        }
    }

    @Test
    public void testValidSearch() {
        String date = LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        performSearch("Hà Nội", "Sài Gòn", date);

        wait.until(ExpectedConditions.urlContains("/booking"));
        assertTrue("Should redirect to booking page", driver.getCurrentUrl().contains("/booking"));

        WebElement resultsTable = findElementWithRetry(By.cssSelector(".train-results"));
        assertTrue("Train results should be displayed", resultsTable.isDisplayed());

        WebElement trainItem = findElementWithRetry(By.cssSelector(".train-item"));
        assertNotNull("At least one train should be listed", trainItem);
    }

    @Test
    public void testInvalidFromStation() {
        String date = LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        performSearch("Invalid Station", "Sài Gòn", date);

        WebElement fromError = findElementWithRetry(
                By.xpath("//div[contains(text(), 'Vui lòng chọn Ga đi hợp lệ')]")
        );
        assertTrue("Should show error for invalid from station", fromError.isDisplayed());
        assertFalse("Should not redirect to booking page", driver.getCurrentUrl().contains("/booking"));
    }

    @Test
    public void testInvalidToStation() {
        String date = LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        performSearch("Hà Nội", "Invalid Station", date);

        WebElement toError = findElementWithRetry(
                By.xpath("//div[contains(text(), 'Vui lòng chọn Ga đến hợp lệ')]")
        );
        assertTrue("Should show error for invalid to station", toError.isDisplayed());
        assertFalse("Should not redirect to booking page", driver.getCurrentUrl().contains("/booking"));
    }

    @Test
    public void testSameStation() {
        String date = LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        performSearch("Hà Nội", "Hà Nội", date);

        WebElement fromError = findElementWithRetry(
                By.xpath("//div[contains(text(), 'Ga đi và Ga đến không được trùng nhau')]")
        );
        assertTrue("Should show error for same stations", fromError.isDisplayed());
        assertFalse("Should not redirect to booking page", driver.getCurrentUrl().contains("/booking"));
    }

    @Test
    public void testPastDate() {
        String pastDate = LocalDate.now().minusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        performSearch("Hà Nội", "Sài Gòn", pastDate);

        WebElement dateError = findElementWithRetry(
                By.xpath("//div[contains(text(), 'Ngày đi không hợp lệ')]")
        );
        assertTrue("Should show error for past date", dateError.isDisplayed());
        assertFalse("Should not redirect to booking page", driver.getCurrentUrl().contains("/booking"));
    }

    @Test
    public void testNoResults() {
        String futureDate = LocalDate.now().plusDays(30).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        performSearch("Hà Nội", "Sài Gòn", futureDate);

        wait.until(ExpectedConditions.urlContains("/booking"));
        WebElement noResultsMessage = findElementWithRetry(
                By.xpath("//div[contains(text(), 'Không tìm thấy chuyến tàu')]")
        );
        assertTrue("Should show no results message", noResultsMessage.isDisplayed());
    }
}




//
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.chrome.ChromeOptions;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
//import org.junit.AfterClass;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import static org.junit.Assert.*;
//
//import java.time.Duration;
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//
//public class TrainSearchResultTest {
//    private static WebDriver driver;
//    private static WebDriverWait wait;
//
//    @BeforeClass
//    public static void setUp() {
//        // Thiết lập ChromeDriver
////        System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");
////        System.setProperty("webdriver.chrome.driver", "src/test/drivers/chromedriver.exe");
////        driver = new ChromeDriver();
//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--remote-allow-origins=*"); // tránh lỗi bảo mật
//        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
//        options.setExperimentalOption("useAutomationExtension", false);
//         driver = new ChromeDriver(options);
//
//        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
//        driver.get("http://localhost:5173"); // Thay bằng URL thực tế của trang web
//    }
//
//    @AfterClass
//    public static void tearDown() {
//        if (driver != null) {
//            driver.quit();
//        }
//    }
//
//    private void performSearch(String from, String to, String date) {
//        // Nhập ga đi
//        WebElement fromInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
//                By.cssSelector("input[placeholder='Ga đi']")
//        ));
//        fromInput.clear();
//        fromInput.sendKeys(from);
//        WebElement fromSuggestion = wait.until(ExpectedConditions.elementToBeClickable(
//                By.xpath("//li[contains(text(), '" + from + "')]")
//        ));
//        fromSuggestion.click();
//
//        // Nhập ga đến
//        WebElement toInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
//                By.cssSelector("input[placeholder='Ga đến']")
//        ));
//        toInput.clear();
//        toInput.sendKeys(to);
//        WebElement toSuggestion = wait.until(ExpectedConditions.elementToBeClickable(
//                By.xpath("//li[contains(text(), '" + to + "')]")
//        ));
//        toSuggestion.click();
//
//        // Chọn ngày
//        WebElement datePicker = wait.until(ExpectedConditions.elementToBeClickable(
//                By.cssSelector("input[type='date']")
//        ));
//        datePicker.clear();
//        datePicker.sendKeys(date);
//
//        // Nhấn nút tìm kiếm
//        WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(
//                By.xpath("//button[contains(text(), 'Tìm kiếm')]")
//        ));
//        searchButton.click();
//    }
//
//    @Test
//    public void testValidSearch() {
//        // Test case 1: Tìm kiếm hợp lệ với ga đi, ga đến và ngày hợp lệ
//        String date = LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//        performSearch("Hà Nội", "Sài Gòn", date);
//
//        // Kiểm tra chuyển hướng đến trang booking
//        wait.until(ExpectedConditions.urlContains("/booking"));
//        assertTrue("Should redirect to booking page", driver.getCurrentUrl().contains("/booking"));
//
//        // Kiểm tra hiển thị danh sách chuyến tàu
//        WebElement resultsTable = wait.until(ExpectedConditions.visibilityOfElementLocated(
//                By.cssSelector(".train-results") // Điều chỉnh selector dựa trên cấu trúc thực tế
//        ));
//        assertTrue("Train results should be displayed", resultsTable.isDisplayed());
//
//        // Kiểm tra có ít nhất một chuyến tàu
//        WebElement trainItem = wait.until(ExpectedConditions.presenceOfElementLocated(
//                By.cssSelector(".train-item") // Điều chỉnh selector
//        ));
//        assertNotNull("At least one train should be listed", trainItem);
//    }
//
//    @Test
//    public void testInvalidFromStation() {
//        // Test case 2: Ga đi không hợp lệ
//        performSearch("Invalid Station", "Sài Gòn",
//                LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
//
//        // Kiểm tra thông báo lỗi ga đi
//        WebElement fromError = wait.until(ExpectedConditions.visibilityOfElementLocated(
//                By.xpath("//div[contains(text(), 'Vui lòng chọn Ga đi hợp lệ')]")
//        ));
//        assertTrue("Should show error for invalid from station", fromError.isDisplayed());
//
//        // Kiểm tra không chuyển hướng
//        assertFalse("Should not redirect to booking page", driver.getCurrentUrl().contains("/booking"));
//    }
//
//    @Test
//    public void testInvalidToStation() {
//        // Test case 3: Ga đến không hợp lệ
//        performSearch("Hà Nội", "Invalid Station",
//                LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
//
//        // Kiểm tra thông báo lỗi ga đến
//        WebElement toError = wait.until(ExpectedConditions.visibilityOfElementLocated(
//                By.xpath("//div[contains(text(), 'Vui lòng chọn Ga đến hợp lệ')]")
//        ));
//        assertTrue("Should show error for invalid to station", toError.isDisplayed());
//
//        // Kiểm tra không chuyển hướng
//        assertFalse("Should not redirect to booking page", driver.getCurrentUrl().contains("/booking"));
//    }
//
//    @Test
//    public void testSameStation() {
//        // Test case 4: Ga đi và ga đến trùng nhau
//        performSearch("Hà Nội", "Hà Nội",
//                LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
//
//        // Kiểm tra thông báo lỗi trùng ga
//        WebElement fromError = wait.until(ExpectedConditions.visibilityOfElementLocated(
//                By.xpath("//div[contains(text(), 'Ga đi và Ga đến không được trùng nhau')]")
//        ));
//        assertTrue("Should show error for same stations", fromError.isDisplayed());
//
//        // Kiểm tra không chuyển hướng
//        assertFalse("Should not redirect to booking page", driver.getCurrentUrl().contains("/booking"));
//    }
//
//    @Test
//    public void testPastDate() {
//        // Test case 5: Ngày đi trong quá khứ
//        String pastDate = LocalDate.now().minusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//        performSearch("Hà Nội", "Sài Gòn", pastDate);
//
//        // Kiểm tra thông báo lỗi ngày không hợp lệ (giả định trang web có kiểm tra này)
//        WebElement dateError = wait.until(ExpectedConditions.visibilityOfElementLocated(
//                By.xpath("//div[contains(text(), 'Ngày đi không hợp lệ')]") // Điều chỉnh dựa trên thông báo thực tế
//        ));
//        assertTrue("Should show error for past date", dateError.isDisplayed());
//
//        // Kiểm tra không chuyển hướng
//        assertFalse("Should not redirect to booking page", driver.getCurrentUrl().contains("/booking"));
//    }
//
//    @Test
//    public void testNoResults() {
//        // Test case 6: Tìm kiếm hợp lệ nhưng không có chuyến tàu
//        String futureDate = LocalDate.now().plusDays(30).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//        performSearch("Hà Nội", "Sài Gòn", futureDate);
//
//        // Kiểm tra chuyển hướng đến trang booking
//        wait.until(ExpectedConditions.urlContains("/booking"));
//
//        // Kiểm tra thông báo không có chuyến tàu
//        WebElement noResultsMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
//                By.xpath("//div[contains(text(), 'Không tìm thấy chuyến tàu')]") // Điều chỉnh dựa trên thông báo thực tế
//        ));
//        assertTrue("Should show no results message", noResultsMessage.isDisplayed());
//    }
//}