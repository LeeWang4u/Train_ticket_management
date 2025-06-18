package features;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TrainSearchTest {
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

            toInput.sendKeys("Sài Gòn");


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










//package features;
//
//import org.openqa.selenium.By;
//import org.openqa.selenium.JavascriptExecutor;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.chrome.ChromeOptions;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
//
//import java.time.Duration;
//
//public class TrainSearchTest {
//    public static void main(String[] args) {
//        // Thiết lập đường dẫn đến ChromeDriver nếu chưa có trong PATH
//        // System.setProperty("webdriver.chrome.driver", "/path/to/chromedriver");
//
////        ChromeOptions options = new ChromeOptions();
////        options.addArguments("--start-maximized");
////        WebDriver driver = new ChromeDriver(options);
//
//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--remote-allow-origins=*"); // tránh lỗi bảo mật
//        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
//        options.setExperimentalOption("useAutomationExtension", false);
//        WebDriver driver = new ChromeDriver(options);
//
//        try {
//            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//
//            // Truy cập trang chính
//            driver.get("http://localhost:5173"); // hoặc thay bằng domain thật nếu có
//
//            // Nhập Ga đi
//            WebElement fromInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[contains(text(),'Ga đi')]/following-sibling::input")));
//            fromInput.sendKeys("Hà Nội");
//
//            // Chọn suggestion đầu tiên nếu có
//            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul/li[contains(text(),'Hà Nội')]"))).click();
//
//            // Nhập Ga đến
//            WebElement toInput = driver.findElement(By.xpath("//label[contains(text(),'Ga đến')]/following-sibling::input"));
//            toInput.sendKeys("Sài Gòn");
//
//            // Chọn suggestion đầu tiên nếu có
//            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul/li[contains(text(),'Sài Gòn')]"))).click();
//
//            // Chọn ngày đi - ví dụ chọn ngày hiện tại hoặc sau đó nếu DatePicker hoặc Calendar có xử lý
//            // Nếu bạn có selector cụ thể cho Calendar hoặc DatePicker, thay thế đoạn này bằng thao tác phù hợp
//            // VD: chọn ô có class="selected-date" hoặc tương tự
//            try {
////                WebElement dateInput = driver.findElement(By.cssSelector("input[type='date']"));
////                dateInput.sendKeys("2025-06-20"); // định dạng yyyy-MM-dd
//
//                WebElement dateInput = driver.findElement(By.id("date"));
//                dateInput.sendKeys("15-07-2025");
//
//            } catch (Exception e) {
//                System.out.println("⚠️ Không tìm thấy input[type='date'], thử xử lý rc-calendar ở desktop nếu cần");
//                // TODO: thêm xử lý với rc-calendar nếu cần thiết
//            }
//
//            // Click nút Tìm kiếm
//            WebElement searchButton = driver.findElement(By.xpath("//button[contains(text(),'Tìm kiếm')]"));
//            searchButton.click();
//
//            // Kiểm tra đã điều hướng tới trang /booking
//            wait.until(ExpectedConditions.urlContains("/booking"));
//
//
//
//// Chờ phần tử có id là 'booking-title' xuất hiện
//            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("booking-title")));
//
//
//            System.out.println("✅ Test passed: Điều hướng đến trang booking thành công.");
//
//            driver.get("http://localhost:5173/booking");
//
//// Dừng lại 3 giây (3000 milliseconds)
//            Thread.sleep(3000);
//
//        } catch (Exception e) {
//            System.out.println("❌ Test failed: " + e.getMessage());
//        } finally {
//            driver.quit();
//        }
//    }
//}













//
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
//import java.time.Duration;
//
//public class TrainSearchTest {
//    public static void main(String[] args) {
//        // Thiết lập đường dẫn đến ChromeDriver
////        System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");
//        System.setProperty("webdriver.chrome.driver", "src/test/drivers/chromedriver.exe");
//        // Khởi tạo WebDriver
//        WebDriver driver = new ChromeDriver();
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
//
//        try {
//            // Mở trang web
//            driver.get("http://localhost:5173/"); // Thay bằng URL thực tế của trang web
//
//            // Tìm và nhập ga đi
//            WebElement fromInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
//                    By.cssSelector("input[placeholder='Ga đi']") // Điều chỉnh selector nếu cần
//            ));
//            fromInput.clear();
//            fromInput.sendKeys("Hà Nội");
//
//            // Chờ và chọn gợi ý ga đi
//            WebElement fromSuggestion = wait.until(ExpectedConditions.elementToBeClickable(
//                    By.xpath("//li[contains(text(), 'Hà Nội')]")
//            ));
//            fromSuggestion.click();
//
//            // Tìm và nhập ga đến
//            WebElement toInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
//                    By.cssSelector("input[placeholder='Ga đến']") // Điều chỉnh selector nếu cần
//            ));
//            toInput.clear();
//            toInput.sendKeys("Sài Gòn");
//
//            // Chờ và chọn gợi ý ga đến
//            WebElement toSuggestion = wait.until(ExpectedConditions.elementToBeClickable(
//                    By.xpath("//li[contains(text(), 'Sài Gòn')]")
//            ));
//            toSuggestion.click();
//
//            // Chọn ngày đi
//            WebElement datePicker = wait.until(ExpectedConditions.elementToBeClickable(
//                    By.cssSelector("input[type='date']") // Điều chỉnh selector nếu cần
//            ));
//            datePicker.click();
//            // Chọn ngày cụ thể (ví dụ: ngày mai)
//            WebElement tomorrow = wait.until(ExpectedConditions.elementToBeClickable(
//                    By.xpath("//div[contains(@class, 'react-datepicker__day') and text()='15']") // Điều chỉnh theo ngày cụ thể
//            ));
//            tomorrow.click();
//
//            // Nhấn nút tìm kiếm
//            WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(
//                    By.xpath("//button[contains(text(), 'Tìm kiếm')]")
//            ));
//            searchButton.click();
//
//            // Chờ kết quả hiển thị
//            wait.until(ExpectedConditions.urlContains("/booking"));
//
//            // Kiểm tra xem đã chuyển hướng đến trang booking chưa
//            String currentUrl = driver.getCurrentUrl();
//            if (currentUrl.contains("/booking")) {
//                System.out.println("Test Passed: Successfully redirected to booking page");
//            } else {
//                System.out.println("Test Failed: Did not redirect to booking page");
//            }
//
//        } catch (Exception e) {
//            System.out.println("Test Failed: " + e.getMessage());
//        } finally {
//            // Đóng trình duyệt
//            driver.quit();
//        }
//    }
//}