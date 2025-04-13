package com.aleks.employeesapi.services.employee;

import com.aleks.employeesapi.data.CSVData;
import com.aleks.employeesapi.data.ColleaguesWithCommonProjects;
import com.aleks.employeesapi.data.ProjectData;

import java.util.List;

public interface ProjectService {
    List<ProjectData> convertCSVDataToProjectData(List<CSVData> csvDataList, String datePattern);

    ColleaguesWithCommonProjects findLongestLastingColleagues(List<ProjectData> employees);
}
