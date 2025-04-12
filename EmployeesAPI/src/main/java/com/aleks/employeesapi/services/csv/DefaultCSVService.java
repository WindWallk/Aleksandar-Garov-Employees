package com.aleks.employeesapi.services.csv;

import com.aleks.employeesapi.data.CSVData;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Service
public class DefaultCSVService implements CSVService {
    @Override
    public List<CSVData> extractCSVDataFromFile(MultipartFile file) throws IOException {
        try (InputStreamReader reader = new InputStreamReader(file.getInputStream())) {
            CsvToBean<CSVData> csvToBean = new CsvToBeanBuilder<CSVData>(reader)
                    .withType(CSVData.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            return csvToBean.parse();
        }
    }
}
