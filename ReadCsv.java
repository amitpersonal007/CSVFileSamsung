package com.testsigma.addons.web;

import com.testsigma.sdk.WebAction;
import com.opencsv.CSVReader;
import com.testsigma.sdk.ApplicationType;
import com.testsigma.sdk.annotation.Action;
import com.testsigma.sdk.annotation.TestData;
import com.testsigma.sdk.annotation.RunTimeData;
import lombok.Data;
import org.openqa.selenium.NoSuchElementException;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.List;

@Data
@Action(actionText = "Read row count from CSV file where directory is test_data and store in into a runtimevariable var1",
        description = "Reading row count from CSV file",
        applicationType = ApplicationType.WEB)
public class ReadCsv extends WebAction {

  @TestData(reference = "test_data")
  private com.testsigma.sdk.TestData testData;
  
  @TestData(reference = "var")
  private com.testsigma.sdk.TestData testData1;
 
  @RunTimeData
  private com.testsigma.sdk.RunTimeData runTimeData;

  @Override
  public com.testsigma.sdk.Result execute() throws NoSuchElementException {
    //Your Awesome code starts here
    logger.info("Initiating execution");
    
    com.testsigma.sdk.Result result = com.testsigma.sdk.Result.SUCCESS;

    try {
    	File s1 = getLastModified(testData.getValue().toString());
        logger.info("Name of the file is "+s1.getName());
        
    	System.out.println(s1.getName());
    	Reader reader = new FileReader(s1.getAbsolutePath());
    	
    	CSVReader csvreader = new CSVReader(reader);
    	List<String[]> data = csvreader.readAll();
    	String s2 = String.valueOf(data.size());
    	
    	runTimeData.setKey(testData1.getValue().toString());
    	runTimeData.setValue(s2);
    	
    }
    catch(Exception e) {
    	result = com.testsigma.sdk.Result.FAILED;
    	setErrorMessage("Operation Failed "+e.getMessage());
    	
    }
    
    setSuccessMessage("Success in execution count of the data is "+runTimeData);
    return result;
  }
  
  public static File getLastModified(String directoryFilePath)
	{
	    File directory = new File(directoryFilePath);
	    File[] files = directory.listFiles(File::isFile);
	    long lastModifiedTime = Long.MIN_VALUE;
	    File chosenFile = null;

	    if (files != null)
	    {
	        for (File file : files)
	        {
	            if (file.lastModified() > lastModifiedTime)
	            {
	                chosenFile = file;
	                lastModifiedTime = file.lastModified();
	            }
	        }
	    }

	    return chosenFile;
	}
}