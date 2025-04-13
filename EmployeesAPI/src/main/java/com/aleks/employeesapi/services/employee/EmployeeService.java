package com.aleks.employeesapi.services.employee;

import com.aleks.employeesapi.data.CSVData;
import com.aleks.employeesapi.data.LongestLastingColleagues;
import com.aleks.employeesapi.data.ProjectData;

import java.util.List;

public interface EmployeeService {
    List<ProjectData> convertCSVDataToEmployeeData(List<CSVData> csvDataList);

    LongestLastingColleagues findLongestLastingColleagues(List<ProjectData> employees);
}
