package com.testsigma.addons.web;

import com.testsigma.sdk.WebAction;
import com.opencsv.CSVReader;
import com.testsigma.sdk.ApplicationType;
import com.testsigma.sdk.annotation.Action;
import com.testsigma.sdk.annotation.TestData;
import lombok.Data;

import java.io.File;
import java.io.FileReader;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;

@Data
@Action(actionText = "Verify the value in the CSV filename with rowNo and columnNo",
        description = "Verifying the value displayed in the csv file",
        applicationType = ApplicationType.WEB)

public class ReadCellValue extends WebAction {

  @TestData(reference = "value")
  private com.testsigma.sdk.TestData testData1;
  @TestData(reference = "rowNo")
  private com.testsigma.sdk.TestData testData2;
  @TestData(reference = "columnNo")
  private com.testsigma.sdk.TestData testData3;
  @TestData(reference = "filename")
  private com.testsigma.sdk.TestData testData4;

  @Override
  public com.testsigma.sdk.Result execute() {
	  
    com.testsigma.sdk.Result result = com.testsigma.sdk.Result.SUCCESS;
    logger.info("Initiating execution");
    try {
    	int column = Integer.parseInt(testData2.getValue().toString());
    	int row = Integer.parseInt(testData3.getValue().toString());
    	
    	String Filecsv = System.getProperty("user.home") +File.separator+ "Downloads" +File.separator+ testData4.getValue().toString() + ".csv";
    	
		CSVReader csvread = new CSVReader(new FileReader(Filecsv));
		List<String[]> csvvalue = csvread.readAll();
		String[] csvRow = csvvalue.get(column);
		String csvColumn = csvRow[row];
		
		if(csvColumn.contains(testData1.getValue().toString())) {
			result = com.testsigma.sdk.Result.SUCCESS;
			setSuccessMessage("The particular value is matched with the value displayed in csv file : " +csvColumn);
			logger.info("The particular value is matched with the value displayed in csv file" +csvColumn);	
		}else {
			result = com.testsigma.sdk.Result.FAILED;
			setErrorMessage("The particular value is not matched with the value displayed in csv file : " +csvColumn);
			logger.warn("The particular value is not matched with the value displayed in csv file : " +csvColumn);	
		}
		
	} catch (Exception e) {
		String errorMessage = ExceptionUtils.getStackTrace(e);
		result = com.testsigma.sdk.Result.FAILED;
		setErrorMessage(errorMessage);
		logger.warn(errorMessage);	
	} 
	return result;
  }
}