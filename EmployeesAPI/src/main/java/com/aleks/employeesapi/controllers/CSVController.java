package com.aleks.employeesapi.controllers;

import com.aleks.employeesapi.data.*;
import com.aleks.employeesapi.services.csv.CSVService;
import com.aleks.employeesapi.services.employee.ProjectService;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/csv")
public class CSVController {

    @Resource(name = "defaultCSVService")
    private CSVService csvService;

    @Resource(name = "defaultProjectService")
    private ProjectService projectService;

    @PostMapping("/longest-lasting-colleagues")
    public ResponseEntity<JsonPayload> extractLongestLastingColleagues(@RequestParam("file") MultipartFile file, @RequestParam("format") String datePattern) {
        try {
            List<CSVData> csvDataList = csvService.extractCSVDataFromFile(file);
            List<ProjectData> projects = projectService.convertCSVDataToProjectData(csvDataList, datePattern);
            ColleaguesWithCommonProjects colleaguesWithCommonProjects = projectService.findLongestLastingColleagues(projects);
            return ResponseEntity.ok(new JsonPayload(colleaguesWithCommonProjects, null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new JsonPayload(null, e.getMessage()));
        }
    }
}
