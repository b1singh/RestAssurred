package com.RestApi.BuildingBlocks;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XLUtilities {
	
	String path;
	public FileInputStream fis;
	public FileOutputStream fo;
	public XSSFWorkbook workbook;
	public XSSFSheet sheet;
	public XSSFRow row;
	public XSSFCell cell;
	public CellStyle style;
	public XLUtilities(String path) {
		// TODO Auto-generated constructor stub
		this.path=path;
	}
	public int getRowCount(String sheetName) throws IOException {
		fis=new FileInputStream(path);
		workbook=new XSSFWorkbook(fis);
		sheet=workbook.getSheet(sheetName);
		int rowcount=sheet.getLastRowNum();
		workbook.close();
		return rowcount;
		
	}
	public int getCellCount(String sheetName, int rownum) throws IOException {
		fis=new FileInputStream(path);
		workbook=new XSSFWorkbook(fis);
		sheet=workbook.getSheet(sheetName);
		row=sheet.getRow(rownum);
		int cellcount=row.getLastCellNum();
		workbook.close();
		fis.close();
		return cellcount;
		
	}
	public String getCellData(String sheetname, int rownum,int colnum) throws IOException {
		fis=new FileInputStream(sheetname);
		workbook=new XSSFWorkbook(fis);
		sheet=workbook.getSheet(sheetname);
		row=sheet.getRow(rownum);
		cell=row.getCell(colnum);
		DataFormatter formatter=new DataFormatter();
		String data=null;
		try {
			data=formatter.formatCellValue(cell); //returns the formatted value of a cell.
		}catch (Exception e) {
			// TODO: handle exception
		}
		workbook.close();
		fis.close();
		return data;
	
	}
	public void setCellData(String sheetname, int rownum,int colnum, String data) throws IOException {
		File xlfile=new File(path);
		if(!xlfile.exists())//if the file is not exist then create new file
		{
			workbook=new XSSFWorkbook();
			fo=new FileOutputStream(path);
			workbook.write(fo);;
		}
		fis=new FileInputStream(path);
		workbook=new XSSFWorkbook(fis);
		if(workbook.getSheetIndex(sheetname)==-1)// if sheet not exist then create new sheet
		{
			workbook.createSheet(sheetname);
			sheet=workbook.getSheet(sheetname);
			
		}
		if(sheet.getRow(rownum)==null) //if row not exist then create new row
		{
			sheet.createRow(rownum);
			row=sheet.getRow(rownum);
		}
		cell=row.createCell(colnum);
		cell.setCellValue(data);
		fo=new FileOutputStream(path);
		workbook.write(fo);
		workbook.close();
		fis.close();
		fo.close();
		
		
		
		
	}
	
	

}
