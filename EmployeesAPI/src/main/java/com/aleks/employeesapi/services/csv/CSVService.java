package com.aleks.employeesapi.services.csv;

import com.aleks.employeesapi.data.CSVData;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CSVService {
    List<CSVData> extractCSVDataFromFile(MultipartFile file) throws IOException;
}
