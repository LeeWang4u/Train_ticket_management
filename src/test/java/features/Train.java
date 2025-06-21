package features;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class Train {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeAll
    void setupDriver() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://localhost:5173/");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterAll
    void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private void searchTrips(String from, String to) {
        WebElement fromInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//input[@type='text'])[1]")));
        WebElement toInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//input[@type='text'])[2]")));
        fromInput.clear();
        toInput.clear();
        fromInput.sendKeys(from);
        toInput.sendKeys(to);
    }

    private void selectDate(String dateString) {
        String day = dateString.split(" ")[1].replace(",", "");
        String xpath = String.format("//td[@title='%s']//div[@class='rc-calendar-date'][normalize-space()='%s']", dateString, day);
        WebElement dateElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
        dateElement.click();
    }

    private void clickSearch() {
        WebElement searchBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@class='css-1usc8hj']//a[@type='button'][contains(text(),'Tìm kiếm')]")));
        searchBtn.click();
    }

    private void clickTrainCard() {
        WebElement card = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("(//div[@class='chakra-card__root css-1d6rh2z'])[1]")));
        card.click();
    }

    private void clickSeat() {
        WebElement seat = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("(//button[@type='button'])[10]")));
        seat.click();
    }

    private void clickCart() {
        WebElement cart = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("(//button[@class='chakra-button css-1fzw16o'])[1]")));
        cart.click();
    }

    private void assertToastMessage(String expected) {
        WebElement toast = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[contains(text(), '" + expected + "')]")));
        String toastText = toast.getText();
        Assertions.assertTrue(toastText.contains(expected), "Expected toast message to contain: " + expected);
    }

    @Test
    void testSearchTripWithValidDate() {
        searchTrips("Hà Nội", "Sài Gòn");
        selectDate("April 10, 2025");
        clickSearch();
        assertToastMessage("Tải danh sách chuyến tàu thành công!");
    }

    @Test
    void testSearchTripWithInvalidDate() {
        searchTrips("Hà Nội", "Sài Gòn");
        selectDate("April 12, 2025");
        clickSearch();
        assertToastMessage("Lỗi khi tải chuyến tàu");
    }

    @Test
    void testAddTicketToCart() throws InterruptedException {
        searchTrips("Hà Nội", "Sài Gòn");
        selectDate("April 10, 2025");
        clickSearch();
        clickTrainCard();
        clickSeat();
        clickCart();
        Thread.sleep(10000); // Wait for visual check
    }
}
