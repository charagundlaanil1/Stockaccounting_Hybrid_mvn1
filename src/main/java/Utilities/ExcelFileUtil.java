package Utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelFileUtil {
	Workbook wb;
	//to access excel path we write constructor
	public ExcelFileUtil()throws Throwable
	{
		FileInputStream fi=new FileInputStream("D:\\anilch\\Stockaccounting_Hybrid_mvn\\Testinput\\InputSheet.xlsx");
		wb=WorkbookFactory.create(fi);
	}
	//count no of rows in a sheet
	public  int rowCount(String sheetname)
	{
	return wb.getSheet(sheetname).getLastRowNum();	
	}
	//count no of column in row
	public int colCount(String sheetname)
	{
		return wb.getSheet(sheetname).getRow(0).getLastCellNum();
	}
	public String getData(String sheetname,int row,int column)
	{
		String data="";
	if(wb.getSheet(sheetname).getRow(row).getCell(column).getCellType()==Cell.CELL_TYPE_NUMERIC)
	{
	int celldata=(int)wb.getSheet(sheetname).getRow(row).getCell(column).getNumericCellValue();
	data=String.valueOf(celldata);
	}
	else{
		data=wb.getSheet(sheetname).getRow(row).getCell(column).getStringCellValue();
	}
	return data;
	}

	public void setData(String sheetname,int row,int column,String status)
			throws Throwable
	{
		Sheet sh=wb.getSheet(sheetname);
		Row rownum=sh.getRow(row);
		Cell cell=rownum.createCell(column);
		cell.setCellValue(status);
		
		if (status.equalsIgnoreCase("pass"))
		{
			//Create a cell Style
			CellStyle style=wb.createCellStyle();
			//create a font
			Font font=wb.createFont();
			//apply color to the text
			font.setColor(IndexedColors.GREEN.getIndex());
			//apply bold to the text
			font.setBold(true);
			//set font
			style.setFont(font);
			// set cell style
			rownum.getCell(column).setCellStyle(style);
			
		}else
			if(status.equalsIgnoreCase("Fail"))
			{
				//Create a cell Style
				CellStyle style=wb.createCellStyle();
				//create a font
				Font font=wb.createFont();
				//apply color to the text
				font.setColor(IndexedColors.RED.getIndex());
				//app bold to the text
				font.setBold(true);
				//set font
				style.setFont(font);
				// set cell style
				rownum.getCell(column).setCellStyle(style);
			}else
				if(status.equalsIgnoreCase("Not Executed"))
				{
					//Create a cell Style
					CellStyle style=wb.createCellStyle();
					//create a font
					Font font=wb.createFont();
					//apply color to the text
					font.setColor(IndexedColors.BLUE.getIndex());
					//app bold to the text
					font.setBold(true);
					//set font
					style.setFont(font);
					// set cell style
					rownum.getCell(column).setCellStyle(style);
				}
		
		FileOutputStream fos=new FileOutputStream("D:\\anilch\\Stockaccounting_Hybrid_mvn\\Testoutput\\Hybrid.xlsx");
		wb.write(fos);
		fos.close();
	}
}
