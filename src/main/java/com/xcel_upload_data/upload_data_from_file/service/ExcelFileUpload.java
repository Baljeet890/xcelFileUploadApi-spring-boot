package com.xcel_upload_data.upload_data_from_file.service;
import com.xcel_upload_data.upload_data_from_file.model.Product;
import com.xcel_upload_data.upload_data_from_file.repository.XcelUploadRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ExcelFileUpload {


    @Autowired
    private  final XcelUploadRepository xcelUploadRepository;

    public ExcelFileUpload(XcelUploadRepository xcelUploadRepository) {
        this.xcelUploadRepository = xcelUploadRepository;
    }


    public  void getExcelData(MultipartFile file) throws IOException{

        try(InputStream inputStream = file.getInputStream();
            Workbook workbook = new XSSFWorkbook(inputStream)){
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            List<Product> productList = new ArrayList<>();
            rowIterator.next();

            while(rowIterator.hasNext()){
                Row row = rowIterator.next();
                Product data = new Product();
                data.setProductId(Integer.valueOf((String)getCellData(row,0)));
                data.setProductName(getCellData(row,1));
                data.setProductDesc(getCellData(row ,2));
                data.setProductPrice(Double.valueOf((String)getCellData(row, 3)));
                productList.add(data);

            }
//            fileUploadRepository.saveAll(productList);
           xcelUploadRepository.saveAll(productList);
        }

    }


    private String getCellData(Row row, int cellIndex) {
        Cell cell = row.getCell(cellIndex);
        if (cell != null) {
            switch (cell.getCellType()) {
                case STRING:
                    return cell.getStringCellValue();
                case NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        // Handle date cells
                        return cell.getDateCellValue().toString(); // Or format the date as needed
                    } else {
                        // Handle numeric cells (integer or double)
                        double numericValue = cell.getNumericCellValue();
                        if (numericValue == (int) numericValue) {
                            return String.valueOf((int) numericValue); // Integer
                        } else {
                            return String.valueOf(numericValue);      // Double
                        }
                    }
                case BOOLEAN:
                    return String.valueOf(cell.getBooleanCellValue());
                case FORMULA:
                    try {
                        return String.valueOf(cell.getNumericCellValue()); //Handle formulas returning numbers.
                    }
                    catch(IllegalStateException ise){
                        return cell.getStringCellValue(); // Handle formulas returning text.
                    }
                default:
                    return ""; // Or handle other cell types as needed

            }
        }
        return "";
    }
}
