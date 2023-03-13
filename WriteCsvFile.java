package com.testsigma.addons.web;

import com.testsigma.sdk.WebAction;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.testsigma.sdk.ApplicationType;
import com.testsigma.sdk.annotation.Action;
import com.testsigma.sdk.annotation.TestData;
import com.testsigma.sdk.annotation.RunTimeData;
import lombok.Data;
import org.openqa.selenium.NoSuchElementException;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.util.List;

@Data
@Action(actionText = "Write row number and column number into CSV file with testdata where directory is test_data",
        description = "Write a particular row and column into CSV file",
        applicationType = ApplicationType.WEB)
public class WriteCsvFile extends WebAction {

  @TestData(reference = "row")
  private com.testsigma.sdk.TestData row;
  
  @TestData(reference = "column")
  private com.testsigma.sdk.TestData column;
  
  @TestData(reference = "test_data")
  private com.testsigma.sdk.TestData filePath;
  
  @TestData(reference = "testdata")
  private com.testsigma.sdk.TestData testData;
 


  @Override
  public com.testsigma.sdk.Result execute() throws NoSuchElementException {
    //Your Awesome code starts here
    logger.info("Initiating execution");
    
    com.testsigma.sdk.Result result = com.testsigma.sdk.Result.SUCCESS;

    try {
    	File s1 = getLastModified(filePath.getValue().toString());
        logger.info("Name of the file is "+s1.getName());
        Reader reader = new FileReader(s1.getAbsolutePath());
		String replace = testData.getValue().toString();
		CSVReader csvreader = new CSVReader(reader);
		List<String[]> data = csvreader.readAll();
		
		data.get(Integer.parseInt(row.getValue().toString()))[Integer.parseInt(column.getValue().toString())] = replace;
		csvreader.close();
		CSVWriter writer = new CSVWriter(new FileWriter(s1.getAbsolutePath()));
		writer.writeAll(data);
		writer.flush();
		writer.close();
    	setSuccessMessage("Replaced Successfully in the CSV file . Replaced data is "+replace);
    }
    catch(Exception e) {
    	result = com.testsigma.sdk.Result.FAILED;
    	setErrorMessage("Operation Failed "+e.getMessage());
    	
    }
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
