package testpkg09;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ReportDemo1 {
	ExtentReports extent;
	@BeforeTest
	public void config()
	{
		//ExtentReports, ExtentSparkReporter
		String path = System.getProperty("user.dir")+"\\reports\\index.html";
		ExtentSparkReporter reporter = new ExtentSparkReporter(path);
		reporter.config().setReportName("My first Report");
		reporter.config().setDocumentTitle("MyDoc Title");
		
		//ExtentReports extent = new ExtentReports();
		extent = new ExtentReports();
		extent.attachReporter(reporter);
		extent.setSystemInfo("Tester: ", "Tester01");
			
	}
	
	@Test
	public void firstDemo() throws InterruptedException
	{
		
		extent.createTest("First Demo");
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.get("https://opensource-demo.orangehrmlive.com/");
		Thread.sleep(7000);
		System.out.println(driver.findElement(By.xpath("//h5[normalize-space()='Login']")).getText());
		Assert.assertEquals(driver.findElement(By.xpath("//h5[normalize-space()='Login']")).getText(), "Login" );
		extent.flush();
	}

}
