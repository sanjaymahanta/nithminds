package com.testing;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Trial1 {

	WebDriver driver;
	
	
	@BeforeMethod
	public void setup() {
		System.setProperty("webdriver.chrome.driver", "D://chromedriver132.exe");
		driver = new ChromeDriver();
		driver.get("https://testfunctionality.myshopify.com/");
		driver.manage().window().maximize();
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
		passwordField.sendKeys("abc");
		passwordField.submit();
		
	}
	
	@Test
	public void test_001() {
		
		
	}
	
}
