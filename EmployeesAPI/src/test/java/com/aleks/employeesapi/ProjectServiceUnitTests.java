package com.aleks.employeesapi;

import com.aleks.employeesapi.data.CSVData;
import com.aleks.employeesapi.data.ColleaguesWithCommonProjects;
import com.aleks.employeesapi.data.EmployeeProjectData;
import com.aleks.employeesapi.data.ProjectData;
import com.aleks.employeesapi.services.employee.ProjectService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ProjectServiceUnitTests {
    private static final String DATE_PATTERN = "yyyy-MM-dd";

    @Autowired
    private ProjectService projectService;

    @Test
    void convertCSVDataToProjectData_ShouldConvertValidDataCorrectly() {
        List<CSVData> csvDataList = List.of(
                new CSVData(1, 101, "2022-01-01", "2022-12-31"),
                new CSVData(2, 101, "2022-02-01", "2022-11-30"),
                new CSVData(3, 102, "2023-01-01", "NULL")
        );

        List<ProjectData> projects = projectService.convertCSVDataToProjectData(csvDataList, DATE_PATTERN);

        assertEquals(2, projects.size());
        assertEquals(2, projects.get(0).employees().size());
        assertEquals(1, projects.get(1).employees().size());
    }

    @Test
    void convertCSVDataToProjectData_ShouldThrowExceptionForInvalidDates() {
        List<CSVData> invalidCsvDataList = List.of(
                new CSVData(1, 101, "2022-12-31", "2022-01-01")
        );

        assertThrows(DateTimeException.class, () -> projectService.convertCSVDataToProjectData(invalidCsvDataList, DATE_PATTERN));
    }

    @Test
    void convertCSVDataToProjectData_ShouldHandleNullEndDateCorrectly() {
        List<CSVData> csvDataList = List.of(
                new CSVData(1, 101, "2022-01-01", "NULL")
        );

        List<ProjectData> projects = projectService.convertCSVDataToProjectData(csvDataList, "yyyy-MM-dd");

        assertEquals(LocalDate.now(), projects.get(0).employees().get(0).to());
    }

    @Test
    void findLongestLastingColleagues_ShouldFindColleaguesCorrectly() {
        List<ProjectData> projects = List.of(
                new ProjectData(101, List.of(
                        new EmployeeProjectData(1, LocalDate.of(2022, 1, 1), LocalDate.of(2022, 12, 31)),
                        new EmployeeProjectData(2, LocalDate.of(2022, 6, 1), LocalDate.of(2022, 12, 31))
                )),
                new ProjectData(102, List.of(
                        new EmployeeProjectData(1, LocalDate.of(2023, 1, 1), LocalDate.of(2023, 6, 30)),
                        new EmployeeProjectData(3, LocalDate.of(2023, 3, 1), LocalDate.of(2023, 9, 30))
                ))
        );

        ColleaguesWithCommonProjects colleagues = projectService.findLongestLastingColleagues(projects);

        assertNotNull(colleagues);
        assertEquals(1, colleagues.firstColleagueId());
        assertEquals(2, colleagues.secondColleagueId());
    }

    @Test
    void findLongestLastingColleagues_ShouldReturnNullWhenNoCommonProjects() {
        List<ProjectData> projects = List.of(
                new ProjectData(101, List.of(
                        new EmployeeProjectData(1, LocalDate.of(2022, 1, 1), LocalDate.of(2022, 12, 31))
                ))
        );

        ColleaguesWithCommonProjects colleagues = projectService.findLongestLastingColleagues(projects);

        assertNull(colleagues);
    }
}
