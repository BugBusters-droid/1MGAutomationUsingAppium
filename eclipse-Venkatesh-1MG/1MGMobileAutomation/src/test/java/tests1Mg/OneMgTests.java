package tests1Mg;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;

import com.google.common.collect.ImmutableMap;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import io.appium.java_client.touch.offset.PointOption;

public class OneMgTests {

	public static void main(String[] args) throws MalformedURLException, InterruptedException {

		
		DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
		desiredCapabilities.setCapability("platformName", "android");
        desiredCapabilities.setCapability("udid", "70fdd8770903");
        desiredCapabilities.setCapability("deviceName", "redmi");
        desiredCapabilities.setCapability("appPackage", "com.aranoah.healthkart.plus");
        desiredCapabilities.setCapability("appActivity", "com.aranoah.healthkart.plus.home.HomeActivity");
        desiredCapabilities.setCapability("noReset", "true"); // stop resetting the app

        AndroidDriver<WebElement> driver = new AndroidDriver<WebElement>(new URL("http://localhost:4723/wd/hub"),desiredCapabilities);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        
        driver.findElementByXPath("//*[@content-desc='Navigate up']").click();
        try {
        driver.findElementByXPath("//*[@text='Login/Sign up']").click();
        if(driver.findElementByXPath("//*[@resource-id='com.aranoah.healthkart.plus:id/email_phone']").getText().isEmpty())
        {
        driver.findElementByXPath("//*[@text='Email ID or Mobile Number']").sendKeys("8400899437");
        } 
        driver.findElementByXPath("//*[@text='SEND OTP']").click();
        //driver.findElementByXPath("//*[@text='DONE']").click(); 
        } catch (Exception e) {
        	driver.findElementByXPath("//*[@text='Welcome User']").click();
        }
        
        //click on medicine icon
        driver.findElementByXPath("//*[@resource-id='com.aranoah.healthkart.plus:id/service_icon']").click();
        
        //scroll to "Health Food & Drinks" icon and then perform click on that
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        while(driver.findElementsByXPath("//*[@text='Health Food & Drinks']").size()==0)
        {
            //scroll
            driver.executeScript("mobile:shell", ImmutableMap.of("command", "input touchscreen swipe 267 1707 250 1500"));
        }
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        driver.findElementByXPath("//*[@text='Health Food & Drinks']").click();
        driver.findElementByXPath("//*[@text='Sort']").click();
        driver.findElementByXPath("//*[@text='Price: High to Low']").click();
        driver.findElementByXPath("//*[@text='Ketofy Flour Keto']").click();
        
        Thread.sleep(2000);
        String itemPrice = driver.findElementByXPath("//*[contains(@text,'MRP')]").getText().replace("MRP ", "")
        		.substring(1, driver.findElementByXPath("//*[contains(@text,'MRP')]").getText().replace("MRP ", "").length());
        int itemOrigonalPrice = Integer.parseInt(itemPrice);
        
        int offerPercentageOnOrigonalPrice = Integer.parseInt(driver.findElementByXPath("//*[@resource-id='com.aranoah.healthkart.plus:id/offer_percent']")
        		.getText().replace("% off", ""));
        System.out.print("itemOrigonalPrice  "+String.valueOf(itemOrigonalPrice));
        System.out.print("offerPercentageOnOrigonalPrice  "+String.valueOf(offerPercentageOnOrigonalPrice));
        
        String salePrice = driver.findElementByXPath("//*[@resource-id='com.aranoah.healthkart.plus:id/best_price']").getText();

        if(driver.findElementsByXPath("//*[@text='GO TO CART']").size()==0) {
        	driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
            while(driver.findElementsByXPath("//*[@text='Add To Cart']").size()==0)
            {
                //scroll
                driver.executeScript("mobile:shell", ImmutableMap.of("command", "input touchscreen swipe 619 844 619 449"));
            }
        	driver.findElementByXPath("//*[@text='Add To Cart']").click();
        	driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        	try {
        	driver.findElementByXPath("//*[@resource-id='com.aranoah.healthkart.plus:id/close_icon']").click();
        	} catch (Exception e)
        	{}
        	Thread.sleep(2000);
        	driver.findElementByXPath("//*[@text='GO TO CART']").click();
        } else {
        	driver.findElementByXPath("//*[@text='GO TO CART']").click();
        }
                
        Thread.sleep(4000);      
        driver.findElementByXPath("//*[@resource-id='com.aranoah.healthkart.plus:id/img_cross']").click();
        driver.findElementByXPath("//*[@text='CHECKOUT']").click();
         
        try {
        if(driver.findElementByXPath("//*[@text='Add Address']").getText().equals("Add Address")) {
        driver.findElementByXPath("//*[@text='Pincode*']").sendKeys("560027");
        driver.findElementByXPath("//*[@text='House Number, Building and Locality*']").sendKeys("#02, Thanu Kuteera, BTM Stage 1");
        driver.findElementByXPath("//*[@text='Name*']").sendKeys("Venkatesh Nandan");
        driver.findElementByXPath("//*[@text='Contact Number*']").sendKeys("9738222817");
        driver.findElementByXPath("//*[@text='SAVE']").click();
        }}
        catch (Exception e){}
        Thread.sleep(3000);
    	driver.findElementByXPath("//*[@text='CONFIRM']").click();
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        while(driver.findElementsByXPath("//*[@text='To be paid']").size()==0)
        {
            //scroll
            driver.executeScript("mobile:shell", ImmutableMap.of("command", "input touchscreen swipe 267 1707 250 1500"));
        }
        
        driver.findElementByXPath("//*[@text='CASH ON DELIVERY']").click();
        
       
        //String priceOnCheckout = driver.findElementByXPath("//*[@resource-id='com.aranoah.healthkart.plus:id/price']").getText();
        List<WebElement> pricesData = driver.findElementsByXPath("//*[@resource-id='com.aranoah.healthkart.plus:id/charge_value']");
        String origonalPriceOnCheckout = pricesData.get(0).getText().substring(1, pricesData.get(0).getText().length());
        String priceDiscountOnCheckout = pricesData.get(1).getText().substring(2, pricesData.get(1).getText().length());
        String shippingFeeOnCheckout = pricesData.get(2).getText().substring(1, pricesData.get(2).getText().length());
        //String compFee = pricesData.get(3).getText().substring(1, pricesData.get(3).getText().length());
        
        System.out.println( origonalPriceOnCheckout +priceDiscountOnCheckout +shippingFeeOnCheckout);
        
        Assert.assertEquals(String.valueOf(itemOrigonalPrice), origonalPriceOnCheckout);
        Assert.assertEquals(String.valueOf((itemOrigonalPrice*offerPercentageOnOrigonalPrice)/100), priceDiscountOnCheckout);
        Assert.assertEquals(String.valueOf(itemOrigonalPrice), origonalPriceOnCheckout);
       
        String toBePaid = driver.findElementByXPath("//*[@resource-id='com.aranoah.healthkart.plus:id/value']").getText();
        
        Assert.assertEquals(toBePaid.substring(1, toBePaid.length()), String.valueOf(itemOrigonalPrice- (itemOrigonalPrice*offerPercentageOnOrigonalPrice)/100 + 
        		Integer.parseInt(shippingFeeOnCheckout)) );
        
        //service.stop();
        

	}

}
