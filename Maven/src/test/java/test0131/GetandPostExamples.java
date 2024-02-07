package test0131;
//import org.apache.hc.core5.http.ContentType;

import org.json.simple.JSONObject;
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

import java.util.HashMap;
import java.util.Map;

import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.http.ContentType;

public class GetandPostExamples {
	
ExtentReports extent;
	
	@BeforeTest
	public void config()
	{
		//ExtentReports, ExtentSparkReporter
		String path = System.getProperty("user.dir")+"\\reports\\index5.html";
		ExtentSparkReporter reporter = new ExtentSparkReporter(path);
		reporter.config().setReportName("My first Reports");
		reporter.config().setDocumentTitle("MyDoc Titles");
		
		//ExtentReports extent = new ExtentReports();
		extent = new ExtentReports();
		extent.attachReporter(reporter);
		extent.setSystemInfo("Testers: ", "Tester01a");
			
	}
	
	@Test
	public void testGet()
	{
		extent.createTest("Test Verify Names: ");
		baseURI = "https://reqres.in/api";
		given().
			get("/users?page=2").
		then().
			statusCode(200).
			//body("data[1].email", equalTo("lindsay.ferguson@reqres.in")).
			body("data.first_name", hasItems("Lindsay", "Tobias", "Byron"));
			//log().all();
		
	}
	
	@Test
	public void testPost()
	{
		extent.createTest("Test Verify POST: ");
		JSONObject request = new JSONObject();
		//Map<String, Object> map = new HashMap<String, Object>();
		//map.put("name", "Test");
		//map.put("job", "validate");
		//System.out.println(map);
		request.put("name", "Test");
		request.put("job", "validate");
		baseURI = "https://reqres.in/api";
		
		given().
		//header("Content-Type", "application/json").
		contentType(ContentType.JSON).accept(ContentType.JSON).
			body(request.toJSONString()).
		when().
			post("/users").
		then().
			statusCode(201).
			log().all();
		
	
	}
	
	
	@AfterTest
	public void tearDown()
	{
		extent.flush();
	}

}
