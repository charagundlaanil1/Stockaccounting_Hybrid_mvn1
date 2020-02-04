package driverFactory;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import CommonFunLibrary.FunctionLibrary;
import Utilities.ExcelFileUtil;

public class DriverScript {
	ExtentReports report;
	ExtentTest test;
	WebDriver driver;
  @Test
  public void startTest() throws Throwable {
	  
		 //creating reference object for excel util methods
		  ExcelFileUtil excel=new ExcelFileUtil();
		  
		  for(int i=1;i<=excel.rowCount("MasterTestCases");i++){
			  String ModuleStatus="";
			  if(excel.getData("MasterTestCases", i, 2).equalsIgnoreCase("Y")){
				  
					//store module name into TCModule 
					String TCModule=excel.getData("MasterTestCases", i, 1);
				
	                report=new ExtentReports("D:\\anilch\\Stockaccounting_Hybrid_mvn\\Reports\\"+TCModule+FunctionLibrary.generatedata()+".html");				
					
	                for(int j=1;j<=excel.rowCount(TCModule);j++)
	                {
	                    test=report.startTest(TCModule);
	                	
		                String Descriptions=excel.getData(TCModule, j, 0);
		                	
		                String Function_Name=excel.getData(TCModule, j, 1);
		               
		                String Locater_Type=excel.getData(TCModule, j, 2);
		                String Locater_Value=excel.getData(TCModule, j, 3);
		                String Test_Data=excel.getData(TCModule, j, 4);
		                
//		                String Status=excel.getData(TCModule, j, 5);
			               try{ 
			            	   if( Function_Name.equalsIgnoreCase("StartBrowser")){
			                	driver=FunctionLibrary.StartBrowser(driver);
			                	test.log(LogStatus.INFO, Descriptions);
			                     }
				                if( Function_Name.equalsIgnoreCase("openApplication")){
				                	FunctionLibrary.openApplication(driver);
				                	test.log(LogStatus.INFO, Descriptions);
				                 }
				                if(Function_Name.equalsIgnoreCase("waitForElement"))
				                {
				                	FunctionLibrary.waitForElement(driver,Locater_Type , Locater_Value, Test_Data);
				                	test.log(LogStatus.INFO, Descriptions);
				                  }
				                if(Function_Name.equalsIgnoreCase("typeAction")){
				                	FunctionLibrary.typeAction(driver,Locater_Type , Locater_Value, Test_Data);
				                	test.log(LogStatus.INFO, Descriptions);
				                  }
				                if(Function_Name.equalsIgnoreCase("clickAction")){
				                	FunctionLibrary.clickAction(driver, Locater_Type, Locater_Value);
				                	test.log(LogStatus.INFO, Descriptions);
				                 }
				                if(Function_Name.equalsIgnoreCase("closeBrowser")){
				                	FunctionLibrary.closeBrowser(driver);
				                	test.log(LogStatus.INFO, Descriptions);
				                 }
				                if(Function_Name.equalsIgnoreCase("tableValidation")){
				                	FunctionLibrary.tableValidation(driver, Test_Data);
				                	test.log(LogStatus.INFO, Descriptions);
				                 }
				                if(Function_Name.equalsIgnoreCase("captureData")){
				                	FunctionLibrary.captureData(driver, Locater_Type, Locater_Value);
				                	test.log(LogStatus.INFO, Descriptions);
				                 }
				                ModuleStatus="true";
				                excel.setData(TCModule, j, 5, "Pass");
			               }catch(Exception e){
			            	   ModuleStatus="false";
//								System.out.println("the value in driver is "+driver);
			                  File srcFile=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			                  FileUtils.copyFile(srcFile, new File("D:\\anilch\\Stockaccounting_Hybrid_mvn\\Screenshots\\"+TCModule+FunctionLibrary.generatedata()+".png"));
			                  excel.setData(TCModule, j,5,"FAIL");
								break;
			                }
	                
			               if(ModuleStatus.equalsIgnoreCase("true")){
			 				  excel.setData("MasterTestCases", i, 3, "PASS");
			 			   }else if(ModuleStatus.equalsIgnoreCase("false")){
			 				  excel.setData("MasterTestCases", i, 3, "FAIL");
			 			    }
				 		   report.flush();
				 		   report.endTest(test);
	 		        }
			  }      
	          else{   
	                  excel.setData("MasterTestCases", i, 3, "Not Executed");	
	          }
	     }
	}
	}


