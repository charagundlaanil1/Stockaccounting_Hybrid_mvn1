package CommonFunLibrary;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import Utilities.PropertyFileUtil;

public class FunctionLibrary {
public static WebDriver StartBrowser(WebDriver driver) throws Throwable{
	

		
		if(PropertyFileUtil.getValueForkey("browser").equalsIgnoreCase("chrome")){
			System.setProperty("webdriver.chrome.driver", "D:\\anilch\\Stockaccounting_Hybrid_mvn\\drivers\\chromedriver.exe");
			driver=new ChromeDriver();
		}else if(PropertyFileUtil.getValueForkey("browser").equalsIgnoreCase("firefox")){
			System.setProperty("webdriver.gecko.driver", "D:\\anilch\\Stockaccounting_Hybrid_mvn\\drivers\\geckodriver.exe");
			driver=new FirefoxDriver();
		}else if(PropertyFileUtil.getValueForkey("browser").equalsIgnoreCase("ie")){
			System.setProperty("webdriver.chrome.driver", "D:\\anilch\\Stockaccounting_Hybrid_mvn\\drivers\\internetexplorerdriver.exe");
			driver=new InternetExplorerDriver();
		}else{
			System.out.println("browser not valid");
		}
		
		return driver;
	}
	
	
	public static void openApplication(WebDriver driver) throws Throwable{
		driver.get(PropertyFileUtil.getValueForkey("url"));
		driver.manage().window().maximize();
		
	}
	
	public static void waitForElement(WebDriver driver, String locatortype,String locatorvalue,String testdata){
		WebDriverWait mywait=new WebDriverWait(driver,Integer.parseInt(testdata));
		
		if(locatortype.equalsIgnoreCase("id")){
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locatorvalue)));
		}else if(locatortype.equalsIgnoreCase("name")){
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.name(locatorvalue)));
		}else if(locatortype.equalsIgnoreCase("xpath")){
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locatorvalue)));
		}else{
			System.out.println("unable to locate for waitForElement method");
		}

	}
	public static void clickAction(WebDriver driver, String locatortype,String locatorvalue){
		
		if(locatortype.equalsIgnoreCase("id")){
			driver.findElement(By.id(locatorvalue)).click();
		}else if(locatortype.equalsIgnoreCase("name")){
			driver.findElement(By.name(locatorvalue)).click();
		}else if(locatortype.equalsIgnoreCase("xpath")){
			driver.findElement(By.xpath(locatorvalue)).click();
		}else{
			System.out.println("Unable to locate for ClickAction method");
		}
		
	}
	public static void typeAction(WebDriver driver,String locatortype,String locatorvalue,String testdata){
		   if(locatortype.equalsIgnoreCase("id")){
		   driver.findElement(By.id(locatorvalue)).clear();
			   driver.findElement(By.id(locatorvalue)).sendKeys(testdata);
		   }else if (locatortype.equalsIgnoreCase("name")){
			   driver.findElement(By.name(locatorvalue)).clear();
			   driver.findElement(By.name(locatorvalue)).sendKeys(testdata);
			   
		   }else if(locatortype.equalsIgnoreCase("xpath")){
			   driver.findElement(By.xpath(locatorvalue)).clear();
			   driver.findElement(By.xpath(locatorvalue)).sendKeys(testdata);  
		   }
		   else{
				System.out.println("Locator not matching for typeAction method");
			}
	}
	public static void closeBrowser(WebDriver driver){
		 driver.close();
	}
	
	public static String generatedata(){
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy.mm.dd.hh.mm.ss");
		return sdf.format(date);
	}
	
	public static void captureData(WebDriver driver,String locatortytpe,String locatorvalue) throws Throwable{
		String suppilerdata="";
		if(locatortytpe.equalsIgnoreCase("id")){
			suppilerdata=driver.findElement(By.id(locatorvalue)).getAttribute("value");
		}else if(locatortytpe.equalsIgnoreCase("xpath")){
			suppilerdata=driver.findElement(By.xpath(locatorvalue)).getAttribute("value");
		}
		FileWriter fw=new FileWriter("./CaptureData/suppnumber.txt");
		BufferedWriter bw=new BufferedWriter(fw);
		bw.write(suppilerdata);
		bw.flush();
		bw.close();
	}
	public static void tableValidation(WebDriver driver,String column) throws Throwable{
		FileReader fr=new FileReader("./CaptureData/suppnumber.txt");
		BufferedReader br=new BufferedReader(fr);
		String Exp_data=br.readLine();
		System.out.println(Exp_data);
		
		Thread.sleep(5000);
		
		if(driver.findElement(By.xpath(PropertyFileUtil.getValueForkey("search_box"))).isDisplayed()){
			System.out.println("is displayed");
			driver.findElement(By.xpath(PropertyFileUtil.getValueForkey("search_box"))).clear();
			driver.findElement(By.xpath(PropertyFileUtil.getValueForkey("search_box"))).sendKeys(Exp_data);
			driver.findElement(By.xpath(PropertyFileUtil.getValueForkey("click_search"))).click();
		}else{
			System.out.println("not displayed");
			driver.findElement(By.xpath(PropertyFileUtil.getValueForkey("click_searchpanel"))).click();
			driver.findElement(By.xpath(PropertyFileUtil.getValueForkey("search_box"))).clear();
			driver.findElement(By.xpath(PropertyFileUtil.getValueForkey("search_box"))).sendKeys(Exp_data);
			driver.findElement(By.xpath(PropertyFileUtil.getValueForkey("click_search"))).click();
		}
		 WebElement table= driver.findElement(By.xpath(PropertyFileUtil.getValueForkey("sup_table")));
		 List<WebElement>rows=table.findElements(By.tagName("tr"));
		 
			 for(int i=1;i<rows.size();i++){
				String act_data= driver.findElement(By.xpath("//table[@id='tbl_a_supplierslist']/tbody/tr["+i+"]/td["+column+"]/div/span/span")).getText();
				Assert.assertEquals(act_data, Exp_data);
				
				System.out.println(act_data+"   "+Exp_data);
				break;		
			 
			 }
		 
	}
	
}
