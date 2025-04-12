package com.aleks.employeesapi.services.employee;

import com.aleks.employeesapi.data.CSVData;
import com.aleks.employeesapi.data.EmployeeData;
import com.aleks.employeesapi.data.ProjectData;
import com.aleks.employeesapi.data.LongestLastingColleagues;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class DefaultEmployeeService implements EmployeeService {
    @Override
    public List<EmployeeData> convertCSVDataToEmployeeData(List<CSVData> csvDataList) {
        List<EmployeeData> allEmployees = new ArrayList<>();

        csvDataList.forEach(csvData -> {
            Optional<EmployeeData> employeeData = allEmployees.stream().filter(e -> e.id() == csvData.employeeId()).findFirst();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
            LocalDate from = LocalDate.parse(csvData.startDate(), formatter);
            LocalDate to = LocalDate.parse(csvData.endDate(), formatter);

            if (employeeData.isPresent()) {
                EmployeeData employee = employeeData.get();
                //TODO what happens if there is already the same project?
                employee.projects().add(new ProjectData(csvData.projectId(), from, to));
            } else {
                allEmployees.add(new EmployeeData(csvData.employeeId(), new ArrayList<>(List.of(new ProjectData(csvData.projectId(), from, to)))));
            }
        });

        return allEmployees;
    }

    @Override
    public LongestLastingColleagues findLongestLastingColleagues(List<EmployeeData> employees) {

        return null;
    }
}
