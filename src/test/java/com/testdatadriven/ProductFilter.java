package com.testdatadriven;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class ProductFilter {

    WebDriver driver;
    WebDriverWait wait;

    @Test(priority = 1)
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "D:/chromedriver134.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Set global wait

        driver.get("https://www.douglas.de/de");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        // Handling cookies
        WebElement shadowHost = driver.findElement(By.cssSelector("#usercentrics-root"));
        SearchContext shadowRoot = shadowHost.getShadowRoot();
        WebElement button = shadowRoot.findElement(By.cssSelector(".sc-dcJsrY.eIFzaz"));
        wait.until(ExpectedConditions.elementToBeClickable(button)).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='PARFUM']"))).click();
    }

    @Test(priority = 2)
    public void listOfProducts() throws Exception {
    	String path = System.getProperty("user.dir") + "/src/test/resources/ExternalFile/TestData.xlsx";

        int rows = ExcelUtils.getRowCount(path, "Test");

        for (int i = 1; i <= rows; i++) {
            String price = ExcelUtils.getCellData(path, "Test", i, 0);
            String productart = ExcelUtils.getCellData(path, "Test", i, 1);
            String marke = ExcelUtils.getCellData(path, "Test", i, 2);
            String furWen = ExcelUtils.getCellData(path, "Test", i, 3);

            // Select Preis
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[text()='Preis']"))).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#preis-to"))).sendKeys("500");

            // Select Produktart
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[text()='Produktart']"))).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//div[@class='facet-option__label'])[1]//div"))).click();

            // Select Marke
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[text()='Marke']"))).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[text()='4711']"))).click();

            // Select Für Wen
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[text()='Für Wen']"))).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[text()='Unisex']"))).click();

            // Wait for products to load
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".link.link--no-decoration[data-testid='details-link']")));

            // Get the list of products
            List<WebElement> products = driver.findElements(By.cssSelector(".link.link--no-decoration[data-testid='details-link']"));

            // Write the product names to the Excel file
            for (int j = 0; j < products.size(); j++) {
                String productName = products.get(j).getText();
                ExcelUtils.setCellData(path, "Test", j + 1, 8, productName);
            }

            // Print the names to console for verification
            products.forEach(product -> System.out.println("Product Name: " + product.getText()));
        }
    }
}
