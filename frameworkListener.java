package com.fw.listener;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.fw.utilities.extentReport;
import com.google.common.io.Files;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class frameworkListener implements ITestListener {
	ExtentReports oExt;
	extentReport oReport;
	ExtentTest oTest;

	public frameworkListener() {
		oReport = new extentReport();
		oExt = oReport.generateReport();

	}

	@Override
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		oTest = oExt.startTest(result.getName());
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
		oTest.log(LogStatus.PASS, result.getMethod() + " Test Passed ");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub
		WebDriver driver = null;
		try {
			driver = (WebDriver) result.getTestClass().getRealClass().getDeclaredField("driver").get(result.getInstance());
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//oTest.log(LogStatus.FAIL, result.getMethod() + " Test Failed ");
		try {
			oTest.log(LogStatus.FAIL, oTest.addScreenCapture(captureScreen(driver)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onTestFailedWithTimeout(ITestResult result) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		oExt.endTest(oTest);
		oExt.flush();
	}

	public String captureScreen(WebDriver driver) throws IOException {
		TakesScreenshot obj_screen = (TakesScreenshot) driver;
		File source = obj_screen.getScreenshotAs(OutputType.FILE);
		String fielPath = "D:\\test_" + System.currentTimeMillis() + ".jpg";
		File dest = new File(fielPath);
		Files.copy(source, dest);
		return fielPath;
	}

}
