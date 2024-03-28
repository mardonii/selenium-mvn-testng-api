package web;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;

public class Hue {

    String query = "SELECT * FROM (SELECT create_date,                            create_by,                            id,                            'BANK BRI' AS bank,                            branch_code,                            mid,                            rekening,                            tid,                            jenis_edc,                            status,                            '002' AS bank_code                            FROM datalake.pgd_tbl_master_edc                            WHERE jenis_edc = '4'                    ) x    where id = '1708398242144'";

    @Test
    void go(){
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Actions actions = new Actions(driver);



        driver.get("https://pgd-sby-bgdt-edge-3.pegadaian.co.id:8889/hue/accounts/login?next=/");

        driver.findElement(By.xpath("//input[@name = 'username']")).sendKeys("poj-qa-mardoni");
        driver.findElement(By.xpath("//input[@name = 'password']")).sendKeys("Peg@daian%2023");


        driver.findElement(By.xpath("//input[@type = 'submit']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class = 'ace_invisible ace_emptyMessage']")));
        driver.findElement(By.xpath("(//textarea[@class = 'ace_text-input'])[9]")).sendKeys(query);

                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@class = 'database-tree assist-tables']")));
        // Send Ctrl + Enter
        actions.keyDown(Keys.CONTROL).sendKeys(Keys.ENTER).keyUp(Keys.CONTROL).perform();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//th[contains(@class, 'sorting sort-')]")));



    }
}
