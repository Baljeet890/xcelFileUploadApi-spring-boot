package com.xcel_upload_data.upload_data_from_file.controller;

import com.xcel_upload_data.upload_data_from_file.UploadDataFromFileApplication;
import com.xcel_upload_data.upload_data_from_file.service.ExcelFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class fileUploadController {

    @Autowired
    private ExcelFileUpload excelFileUpload;

    @PostMapping("/uploadExcel")
    public ResponseEntity<?> uploadExcel(@RequestParam("file")MultipartFile file)
    {
        try{
            excelFileUpload.getExcelData(file);
            return new ResponseEntity<>("Excel Data uploaded successfully", HttpStatus.OK);
        }
        catch (IOException e){
            return  new ResponseEntity<>("Error uploading Excel data", HttpStatus.BAD_REQUEST);
        }
    }

}
