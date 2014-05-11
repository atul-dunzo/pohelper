package com.pagerunner.utils;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;


/**
 * Assigns starts the WebDriver, opens the requested browser and loads the test app into it.
 * @return Webdriver Instance
 */
public class DriverManager {
	
	
	// Determines which browser and loads it.  At the end it sends the base URL to retrieve the starting web page.
	public static synchronized WebDriver getDriver() throws TestException
    {
		final Logger logger;
    	
    	WebDriver driver = null;
    	
    	logger = Logger.getLogger(DriverManager.class.getName());

        Configurator configurator = Configurator.get();

    	if (configurator.getBrowser() == Configurator.BROWSER.FIREFOX) {
    		
    		logger.info("Setting WebDriver to FIREFOX.");
    		
    		// We're using an explicit Firefox profile so we can use FireBug when debugging tests.
    		// TODO:  Make the Firefox profile a command-line param.
//    		ProfilesIni allProfiles = new ProfilesIni();
//    		FirefoxProfile profile = allProfiles.getProfile("SeleniumFFoxProfile");
    		// We're not using preferences, but I wanted the example in here just in case we need it later.
    		// profile.setPreference("foo.bar", 23);
//    		driver = new FirefoxDriver(profile);
    		
    		// This was the old way I did this before I added the ability for an FFox profile.
    		// The above method didn't seem to work with FFox 13
    		driver = new FirefoxDriver();
    	}
		else if (configurator.getBrowser() == Configurator.BROWSER.CHROME)
		{
			logger.info("Setting WebDriver to CHROME on LOCALHOST.");
			driver = new ChromeDriver();
        }
    	else {
    		throw new TestException("UNKNOWN BROWSER in Configurator.");
    	}

    	driver.get(configurator.getUrl());
    	
		return driver;
    }
    
}
