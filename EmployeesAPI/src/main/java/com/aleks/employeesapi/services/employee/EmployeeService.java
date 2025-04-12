package com.aleks.employeesapi.services.employee;

import com.aleks.employeesapi.data.CSVData;
import com.aleks.employeesapi.data.EmployeeData;
import com.aleks.employeesapi.data.LongestLastingColleagues;

import java.util.List;

public interface EmployeeService {
    List<EmployeeData> convertCSVDataToEmployeeData(List<CSVData> csvDataList);

    LongestLastingColleagues findLongestLastingColleagues(List<EmployeeData> employees);
}
