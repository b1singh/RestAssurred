package com.Payment.CreateAPI.test;

import java.io.IOException;

import org.testng.annotations.DataProvider;

import com.RestApi.BuildingBlocks.XLUtilities;

public class DataProvidersCreate {

	@DataProvider(name = "data")
	public Object[][] getAllData() throws IOException{
		String path=System.getProperty("user.dir")+"/src/main/resources/TestData/CreatePaymentDataSources.xlsx";
		XLUtilities xl=new XLUtilities(path);
		int rownum=xl.getRowCount("amountdata");
		int colcount=xl.getCellCount("amountdata", 1);
		
		String apidata[][]=new String[rownum][colcount];
		for(int i=1;i<=colcount;i++)
		{
			for(int j=0;j<colcount;j++)
			{
				apidata[i-1][j]=xl.getCellData("amountdata", i, j);
			}
		}
		return apidata;
		
	}
}
