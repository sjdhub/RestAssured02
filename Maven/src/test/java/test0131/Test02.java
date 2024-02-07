package test0131;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.util.List;

public class Test02 {

	ExtentReports extent;
    ExtentTest statusCodeTest;
    ExtentTest emailsTest;
    ExtentTest firstNamesTest;

    @BeforeClass
    public void setup() {
        // Initialize ExtentReports and ExtentSparkReporter
    	String path = System.getProperty("user.dir")+"\\reports\\index5.html";
        ExtentSparkReporter reporter = new ExtentSparkReporter(path);
        extent = new ExtentReports();
        extent.attachReporter(reporter);
        extent.setSystemInfo("Testers: ", "Tester01a");
    }
    
    @Test
    public void testStatusCode() {
    	// Start the ExtentTest for Status Code
        statusCodeTest = extent.createTest("Verify Status Code");

        // Specify base URI   https://reqres.in/api/users?page=2 
        RestAssured.baseURI = "https://reqres.in/api";

        // Log the request details before sending the request
      //  statusCodeTest.log(Status.INFO, "Request Details: GET https://reqres.in/api/users?page=2");
       
        // Perform GET request and capture the response
        Response response = RestAssured.given()
                .queryParam("page", 2)
                .when()
                .get("/users");

        // Verify status code
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200);
        statusCodeTest.log(Status.PASS, "Status Code: " + statusCode);
    }

    @Test
    public void testEmails() {
        // Start the ExtentTest for Emails
        emailsTest = extent.createTest("Verify Emails");

        // Capture the response
        Response response = RestAssured.given()
                .queryParam("page", 2)
                .when()
                .get("/users");

        // Log the request details
      //  emailsTest.log(Status.INFO, "Request Details: GET https://reqres.in/api/users?page=2");

        // Verify emails
        List<String> emails = response.jsonPath().getList("data.email");
        Assert.assertTrue(emails.contains("michael.lawson@reqres.in"));
        Assert.assertTrue(emails.contains("lindsay.ferguson@reqres.in"));
        // Add more email verifications as needed

        emailsTest.log(Status.PASS, "Emails Verified");
    }

    @Test
    public void testFirstNames() {
        // Start the ExtentTest for First Names
        firstNamesTest = extent.createTest("Verify First Names");

        // Capture the response
        Response response = RestAssured.given()
                .queryParam("page", 2)
                .when()
                .get("/users");

        // Log the request details
       // firstNamesTest.log(Status.INFO, "Request Details: GET https://reqres.in/api/users?page=2");

        // Verify first names
        List<String> firstNames = response.jsonPath().getList("data.first_name");
        Assert.assertTrue(firstNames.contains("Michael"));
        Assert.assertTrue(firstNames.contains("Lindsay"));
        // Add more first name verifications as needed

        firstNamesTest.log(Status.PASS, "First Names Verified");
    }

    @AfterClass
    public void tearDown() {
        // Flush the ExtentReports
        extent.flush();
    }
	
	
}
