package com.aleks.employeesapi.services.employee;

import com.aleks.employeesapi.data.CSVData;
import com.aleks.employeesapi.data.EmployeeProjectData;
import com.aleks.employeesapi.data.ProjectData;
import com.aleks.employeesapi.data.LongestLastingColleagues;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class DefaultEmployeeService implements EmployeeService {
    @Override
    public List<ProjectData> convertCSVDataToEmployeeData(List<CSVData> csvDataList) {
        List<ProjectData> allProjects = new ArrayList<>();

        csvDataList.forEach(csvData -> {
            Optional<ProjectData> projectData = allProjects.stream().filter(e -> e.id() == csvData.projectId()).findFirst();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
            LocalDate from = LocalDate.parse(csvData.startDate(), formatter);
            LocalDate to = csvData.endDate() != null ? LocalDate.parse(csvData.endDate(), formatter) : LocalDate.now();

            if (projectData.isPresent()) {
                ProjectData project = projectData.get();
                //TODO what happens if there is already the same project?
                project.employees().add(new EmployeeProjectData(csvData.employeeId(), from, to));
            } else {
                allProjects.add(new ProjectData(csvData.projectId(), new ArrayList<>(List.of(new EmployeeProjectData(csvData.employeeId(), from, to)))));
            }
        });

        return allProjects;
    }

    @Override
    public LongestLastingColleagues findLongestLastingColleagues(List<ProjectData> employees) {

        return null;
    }
}
