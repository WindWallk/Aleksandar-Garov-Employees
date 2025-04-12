package com.aleks.employeesapi.services.employee;

import com.aleks.employeesapi.data.CSVData;
import com.aleks.employeesapi.data.EmployeeData;
import com.aleks.employeesapi.data.LongestLastingColleagues;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultEmployeeService implements EmployeeService {
    @Override
    public List<EmployeeData> convertCSVDataToEmployeeData(List<CSVData> csvDataList) {
        return List.of();
    }

    @Override
    public LongestLastingColleagues findLongestLastingColleagues(List<EmployeeData> employees) {
        return null;
    }
}
