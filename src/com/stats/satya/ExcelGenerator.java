package com.stats.satya;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;


public class ExcelGenerator {




	public static void playersExcelGenerator(List<PlayerInfo> playersList, String fileName) throws Exception {
		
		final String[] columns = 
			{ 
				"Name", "Country", "Matches", "Inngs.", 
				"NotOuts", "Runs" , " 50s " , "100s " , 
				"Best " , "Avg " , "Str " , 
				
				"Bow. Inngs", "Wickets " ,"5Wick " ,
				"Best " , "Avg " , " Str" , "Econ ", " Prof.Id" , "Pic "
			};
		
		// Create a Workbook
		Workbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file


		// Create a Sheet
		Sheet sheet = workbook.createSheet(fileName);

		// Create a Font for styling header cells
		Font headerFont = workbook.createFont();
		headerFont.setFontHeightInPoints((short) 14);
		headerFont.setColor(IndexedColors.RED.getIndex());

		// Create a CellStyle with the font
		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);

		// Create a Row
		Row headerRow = sheet.createRow(0);

		// Create cells
		for (int i = 0; i < columns.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(columns[i]);
			cell.setCellStyle(headerCellStyle);
		}

		// Create Other rows and cells with playersList data
		int rowNum = 1;
		for (PlayerInfo player : playersList) {
			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(player.getName());
			row.createCell(1).setCellValue(player.getCountry());
			row.createCell(2).setCellValue(player.getMat());
			row.createCell(3).setCellValue(player.getBatIngs());
			row.createCell(4).setCellValue(player.getNotouts());
			row.createCell(5).setCellValue(player.getRuns());
			row.createCell(6).setCellValue(player.getFiftys());
			row.createCell(7).setCellValue(player.getHundrds());
			row.createCell(8).setCellValue(player.getHigh());
			row.createCell(9).setCellValue(player.getBatAvg());
			row.createCell(10).setCellValue(player.getBatStr());
			
			row.createCell(11).setCellValue(player.getBowIngs());
			row.createCell(12).setCellValue(player.getWik());
			row.createCell(13).setCellValue(player.getWik5());
			row.createCell(14).setCellValue(player.getBowBest());			
			row.createCell(15).setCellValue(player.getBowAvg());
			row.createCell(16).setCellValue(player.getBatStr());
			row.createCell(17).setCellValue(player.getEcon());
			
			row.createCell(18).setCellValue(player.getProfileId());
			row.createCell(19).setCellValue(player.getPic());
			
			
		}

		// Resize all columns to fit the content size
		for (int i = 0; i < columns.length; i++) {
			sheet.autoSizeColumn(i);
		}

		// Write the output to a file
		FileOutputStream fileOut = new FileOutputStream(fileName+(new Date().getTime())+".xlsx");
		workbook.write(fileOut);
		fileOut.close();		
	}

}
