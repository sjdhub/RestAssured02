package test0131;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;

public class TestExamples {
	
	ExtentReports extent;
	
	@BeforeTest
	public void config()
	{
		//ExtentReports, ExtentSparkReporter
		String path = System.getProperty("user.dir")+"\\reports\\index3.html";
		ExtentSparkReporter reporter = new ExtentSparkReporter(path);
		reporter.config().setReportName("My first Reports");
		reporter.config().setDocumentTitle("MyDoc Titles");
		
		//ExtentReports extent = new ExtentReports();
		extent = new ExtentReports();
		extent.attachReporter(reporter);
		extent.setSystemInfo("Testers: ", "Tester01a");
			
	}
	
	@Test
	public void test_01a() {
		extent.createTest("First Demo");
		Response response = get("https://reqres.in/api/users?page=2");
		System.out.println(response.getStatusCode());
		System.out.println(response.getTime());
		System.out.println(response.getBody().asString());
		System.out.println(response.getStatusLine());
		System.out.println(response.getHeader("content-type"));
		System.out.println(response.getHeader("Date"));
		
		int statusCode = response.getStatusCode();
		SoftAssert softAssert = new SoftAssert();
		softAssert.assertEquals(statusCode, 201);
		softAssert.assertAll(); 
	//	Assert.assertEquals(statusCode, 201);
	//	ResponseBody resbody = response.getBody();
	//	Assert.assertEquals(resbody, "michael.lawson@reqres.in");
	
		//extent.flush();
	}
	
	@Test
	public void test_02a()
	{
		extent.createTest("Second Demo");
		baseURI = "https://reqres.in/api";
		given().
			get("/users?page=2").
		then().
			statusCode(200).
			//body("data[1].email", equalTo("lindsay.ferguson@reqres.in")).
			body("data.first_name", hasItems("Lindsay", "Tobias", "Byron")).
			log().all();
		
	}
	@AfterTest
	public void tearDown()
	{
		extent.flush();
	}

}
