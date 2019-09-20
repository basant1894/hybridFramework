package com.inetbanking.testCases;

import java.io.IOException;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.inetbanking.pageObjects.AddCustomerPage;
import com.inetbanking.pageObjects.LoginPage;

public class TC_AddCustomerTest_003 extends BaseClass {
	
	@Test
	public void addNewCustomer() throws InterruptedException, IOException {
		
		LoginPage lp=new LoginPage(driver);
		lp.setUserName(username);
		logger.info("User name is provided");
		lp.setPassword(password);
		logger.info("Password is provided");
		lp.clickSubmit();
		
		Thread.sleep(3000);
		
		AddCustomerPage addcust=new AddCustomerPage(driver);
		addcust.clickAddNewCustomer();
		
		logger.info("Providing customer details...");
		
		addcust.custName("Basant");
		addcust.custgender("Male");
		addcust.custdob("07", "09", "2019");
		Thread.sleep(3000);
		addcust.custaddress("India");
		addcust.custcity("Bangalore");
		addcust.custstate("Karnataka");
		addcust.custpinno("560037");
		addcust.custtelephoneno("8888888888");
		
		String email = randomstring()+"gmail.com";
		addcust.custemailid(email);
		addcust.custpassword("abcde");
		addcust.custsubmit();
		Thread.sleep(3000);
		logger.info("Validation started");
		
		boolean res = driver.getPageSource().contains("Customer Registered Successfully!!!");
		if(res==true) {
			Assert.assertTrue(true);
			logger.info("Test Case passed...");
		}else {
			captureScreenShot(driver, "addNewCustomer");
			Assert.assertTrue(false);
			logger.info("Test Case failed...");
		}
		
	}
	public String randomstring() {
		String generatedstring = RandomStringUtils.randomAlphabetic(8);
		return(generatedstring);
	}
    public static String randomNum() {
    	String generatedstring2= RandomStringUtils.randomNumeric(4);
		return (generatedstring2);
    }
}
