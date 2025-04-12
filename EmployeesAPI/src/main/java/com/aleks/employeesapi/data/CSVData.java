package com.aleks.employeesapi.data;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import org.springframework.lang.NonNull;

import java.time.LocalDate;

public record CSVData(
        @NonNull
        @CsvBindByName(column = "EmpID")
        int employeeId,

        @NonNull
        @CsvBindByName(column = "ProjectID")
        int projectId,

        @CsvDate
        @NonNull
        @CsvBindByName(column = "DateFrom")
        LocalDate startDate,

        @CsvDate
        @CsvBindByName(column = "DateTo")
        LocalDate endDate
) {
}
