package com.aleks.employeesapi.services.employee;

import com.aleks.employeesapi.data.*;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class DefaultProjectService implements ProjectService {
    @Override
    public List<ProjectData> convertCSVDataToProjectData(List<CSVData> csvDataList, String datePattern) {
        List<ProjectData> allProjects = new ArrayList<>();

        csvDataList.forEach(csvData -> {
            Optional<ProjectData> projectData = allProjects.stream().filter(e -> e.id() == csvData.getProjectId()).findFirst();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(datePattern, Locale.ENGLISH);
            LocalDate from = LocalDate.parse(csvData.getStartDate(), formatter);
            LocalDate to = !csvData.getEndDate().equals("NULL") ? LocalDate.parse(csvData.getEndDate(), formatter) : LocalDate.now();

            if (from.isAfter(to)) {
                throw new DateTimeException("Start date cannot be after current date or end date");
            }

            EmployeeProjectData employeeProjectData = new EmployeeProjectData(csvData.getEmployeeId(), from, to);

            if (projectData.isPresent()) {
                ProjectData project = projectData.get();
                project.employees().add(employeeProjectData);
            } else {
                allProjects.add(new ProjectData(csvData.getProjectId(), new ArrayList<>(List.of(employeeProjectData))));
            }
        });

        return allProjects;
    }

    @Override
    public ColleaguesWithCommonProjects findLongestLastingColleagues(List<ProjectData> projects) {
        List<ColleaguesWithCommonProjects> colleaguesWithCommonProjects = new ArrayList<>();

        projects.forEach(project -> {
            List<Pair<EmployeeProjectData, EmployeeProjectData>> allEmployeePairsForProject = getPairsFromProjectEmployees(project.employees());
            if (!allEmployeePairsForProject.isEmpty()) {
                findAllColleaguesWithCommonProjectsFromProjectPairs(allEmployeePairsForProject, project.id(), colleaguesWithCommonProjects);
            }
        });

        Optional<ColleaguesWithCommonProjects> longestLastingColleagues = colleaguesWithCommonProjects.stream()
                .max(Comparator.comparing(cp -> cp.commonProjectData().stream().mapToLong(CommonProjectData::days).sum()));

        return longestLastingColleagues.orElse(null);
    }

    private void findAllColleaguesWithCommonProjectsFromProjectPairs(List<Pair<EmployeeProjectData, EmployeeProjectData>> allEmployeePairsForProject, int projectID, List<ColleaguesWithCommonProjects> colleaguesWithCommonProjects) {
        allEmployeePairsForProject.forEach(pair -> {
            EmployeeProjectData firstEmployee = pair.getLeft();
            EmployeeProjectData secondEmployee = pair.getRight();

            long daysWorkedTogether = calculateDaysBetween(firstEmployee.from(), firstEmployee.to(), secondEmployee.from(), secondEmployee.to());
            if (daysWorkedTogether > 1) {
                Optional<ColleaguesWithCommonProjects> existing = colleaguesWithCommonProjects.stream()
                        .filter(c -> (c.firstColleagueId() == firstEmployee.id() && c.secondColleagueId() == secondEmployee.id()) || (c.firstColleagueId() == secondEmployee.id() && c.secondColleagueId() == firstEmployee.id()))
                        .findFirst();

                CommonProjectData commonProjectData = new CommonProjectData(projectID, daysWorkedTogether);

                if (existing.isPresent()) {
                    existing.get().commonProjectData().add(commonProjectData);
                } else {
                    colleaguesWithCommonProjects.add(new ColleaguesWithCommonProjects(firstEmployee.id(), secondEmployee.id(), new ArrayList<>(List.of(commonProjectData))));
                }
            }
        });
    }

    private List<Pair<EmployeeProjectData, EmployeeProjectData>> getPairsFromProjectEmployees(List<EmployeeProjectData> employees) {
        List<Pair<EmployeeProjectData, EmployeeProjectData>> allPairs = new ArrayList<>();
        for (int i = 0; i < employees.size(); ++i) {
            for (int j = i + 1; j < employees.size(); ++j) {
                allPairs.add(Pair.of(employees.get(i), employees.get(j)));
            }
        }
        return allPairs;
    }

    private long calculateDaysBetween(LocalDate start1, LocalDate end1, LocalDate start2, LocalDate end2) {
        if (end1.isBefore(start2) || end2.isBefore(start1)) {
            return 0;
        }

        LocalDate laterStart = Collections.max(Arrays.asList(start1, start2));
        LocalDate earlierEnd = Collections.min(Arrays.asList(end1, end2));

        return ChronoUnit.DAYS.between(laterStart, earlierEnd);
    }
}
