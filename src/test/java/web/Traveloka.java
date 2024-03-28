package web;

import org.mozilla.javascript.serialize.ScriptableOutputStream;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

public class Traveloka {
    WebDriver driver = new ChromeDriver();
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    Random random = new Random();

    Actions action = new Actions(driver);

    @Test
    public void go() {
        driver.manage().window().maximize();

        driver.get("https://www.traveloka.com");

        WebElement CarRental = driver.findElement(By.xpath("//div[@dir='auto' and text()= 'Car Rental']"));
        CarRental.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Enter city or region']")));
        WebElement addressInput = driver.findElement(By.xpath("//input[@placeholder='Enter city or region']"));
        addressInput.sendKeys("Jakarta");

//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@data-testid='rental-search-form-location-input-dropdown']")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@dir='auto' and contains(text(), 'Jakarta Special')]")));
        WebElement locationRec = driver.findElement(By.xpath("//div[@dir='auto' and contains(text(), 'Jakarta Special')]"));
        locationRec.click();

        WebElement datePickerAwal = driver.findElement(By.xpath("//input[@dir='auto' and @data-testid = 'rental-search-form-date-input-start']"));
        datePickerAwal.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@data-testid= 'mds-calendar']")));

        LocalDate today = LocalDate.now();
        int dayAwal = today.getDayOfMonth() + 2;
        int dayAkhir = today.getDayOfMonth() + 5;
        int month = today.getMonthValue();

        WebElement dateAwal = driver.findElement(By.xpath("(//div[@data-testid= 'date-cell-" + dayAwal + "-" + month + "-2024'])[1]"));
        dateAwal.click();


        WebElement datePickerAkhir = driver.findElement(By.xpath("//input[@dir='auto' and @data-testid = 'rental-search-form-date-input-end']"));
        datePickerAkhir.click();


        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@data-testid= 'date-cell-" + dayAkhir + "-" + month + "-2024'])[2]")));
        WebElement dateAkhir = driver.findElement(By.xpath("(//div[@data-testid= 'date-cell-" + dayAkhir + "-" + month + "-2024'])[2]"));
        dateAkhir.click();

        WebElement jamAkhir = driver.findElement(By.xpath("//input[@data-testid = 'rental-search-form-time-input-end']"));
        jamAkhir.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[contains(@class, 'css-1dbjc4n r-1l31rp8 r-kdyh1x r-rs99b7')])[1]/child::div/child::div/child::div/child::div[text() = '11']/..")));

        WebElement jam = driver.findElement(By.xpath("(//div[contains(@class, 'css-1dbjc4n r-1l31rp8 r-kdyh1x r-rs99b7')])[1]/child::div/child::div/child::div/child::div[text() = '11']/.."));
        jam.click();

        WebElement menit = driver.findElement(By.xpath("((//div[contains(@class, 'css-1dbjc4n r-1l31rp8 r-kdyh1x r-rs99b7')])[2]/child::div/child::div/child::div)[2]"));
        menit.click();

        WebElement buttonEndTime = driver.findElement(By.xpath("//div[contains(@class, 'css-18t94o4 css-1dbjc4n r-173mn98 r-')]"));
        buttonEndTime.click();

        WebElement btnSearch = driver.findElement(By.xpath("//div[@data-testid = 'rental-search-form-cta']"));
        btnSearch.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@role = 'button']//div[contains(text(),'Contin')])[2]/../..")));
        driver.findElement(By.xpath("(//div[@role = 'button']//div[contains(text(),'Contin')])[2]/../..")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@role = 'button']//div[contains(text(),'Contin')])[2]/../..")));

        List<WebElement> pilihanRent = driver.findElements(By.xpath("//h3[@role='heading' and contains(text(), 'mCC Jakar')]"));

        if (!pilihanRent.isEmpty()) {
            driver.findElement(By.xpath("//h3[@role='heading' and contains(text(), 'mCC Jakar')]/../../../div[2]/div[2]/div")).click();
        } else {
            driver.findElement(By.xpath("//h3[@role='heading' and contains(text(), 'Moovby Dri')]/../../../div[2]/div[2]/div")).click();
        }

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[@role='heading' and contains(text(), 'mCC Jakar')]")));
        driver.findElement(By.xpath("//h3[@role='heading' and contains(text(), 'mCC Jakar')]")).isDisplayed();


    }

    @Test
    public void get() {
        System.out.println("ini udah mulai");
        go();
        System.out.println("ini udah selesai");
    }
}
