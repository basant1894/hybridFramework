package com.inetbanking.utilities;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import com.aventstack.extentreports.ExtentReporter;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ConfigurableReporter;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class Reporting extends TestListenerAdapter {
	//public ExtentHtmlReporter extentReporter;
	public ExtentReporter htmlReporter;
	public ExtentReports extent;
	public ExtentTest test;
	
	public void onStart(ITestContext context) {
		
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String repName = "Test-Report-"+timeStamp+".html";
		htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir")+ "/test-output/"+repName);
		((ConfigurableReporter) htmlReporter).loadXMLConfig(System.getProperty("user.dir")+"/extent-config.xml");
		
		extent = new ExtentReports();
		
		extent.attachReporter(htmlReporter);
		extent.setSystemInfo("Host Name", "localhost");
		extent.setSystemInfo("Environment", "QA");
		extent.setSystemInfo("User", "Basant");
		
		((ExtentHtmlReporter) htmlReporter).config().setDocumentTitle("HybridFramework Test Project");
		((ExtentHtmlReporter) htmlReporter).config().setReportName("Functional Automation Test Report");
		//((ExtentHtmlReporter) htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
		((ExtentHtmlReporter) htmlReporter).config().setTheme(Theme.DARK);
		
	}
	public void onTestSuccess(ITestResult result) {
		
		test = extent.createTest(result.getName());
		test.log(Status.PASS, MarkupHelper.createLabel(result.getName(), ExtentColor.GREEN));
	}
	public void onTestFailure(ITestResult result) {
		
		test = extent.createTest(result.getName());
		test.log(Status.FAIL, MarkupHelper.createLabel(result.getName(), ExtentColor.RED));
		
		String screenshotpath = System.getProperty("user.dir")+"Screenshots\\"+result.getName()+".png";
		
		File file= new File(screenshotpath);
		
		if(file.exists()) {
			try {
				test.fail("Screenshot is below:" + test.addScreenCaptureFromPath(screenshotpath));
				
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	public void onTestSkipped(ITestResult result) {
		
		test = extent.createTest(result.getName());
		test.log(Status.SKIP, MarkupHelper.createLabel(result.getName(), ExtentColor.ORANGE));
	}
	public void onTestFinish(ITestContext context) {
		extent.flush();	
	}
}




