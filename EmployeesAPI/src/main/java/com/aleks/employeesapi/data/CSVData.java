package com.aleks.employeesapi.data;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import org.springframework.lang.NonNull;

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
        String startDate,

        @CsvDate
        @CsvBindByName(column = "DateTo")
        String endDate
) {
}
