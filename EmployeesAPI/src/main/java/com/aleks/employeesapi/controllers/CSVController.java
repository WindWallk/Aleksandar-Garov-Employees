package com.aleks.employeesapi.controllers;

import com.aleks.employeesapi.data.CSVData;
import com.aleks.employeesapi.data.EmployeeData;
import com.aleks.employeesapi.data.JsonPayload;
import com.aleks.employeesapi.data.LongestLastingColleagues;
import com.aleks.employeesapi.services.csv.CSVService;
import com.aleks.employeesapi.services.employee.EmployeeService;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.DateTimeException;
import java.util.List;

@RestController
@RequestMapping("/api/csv")
public class CSVController {

    @Resource(name = "defaultCSVService")
    private CSVService csvService;

    @Resource(name = "defaultEmployeeService")
    private EmployeeService employeeService;

    @GetMapping("/longest-lasting-colleagues")
    public ResponseEntity<JsonPayload> extractLongestLastingColleagues(@RequestParam("file") MultipartFile file) {
        try {
            List<CSVData> csvDataList = csvService.extractCSVDataFromFile(file);
            List<EmployeeData> employeeDataList = employeeService.convertCSVDataToEmployeeData(csvDataList);
            LongestLastingColleagues longestLastingColleagues = employeeService.findLongestLastingColleagues(employeeDataList);
            return ResponseEntity.ok(new JsonPayload(longestLastingColleagues, null));
        } catch (DateTimeException e) {
            return ResponseEntity.badRequest().body(new JsonPayload(null, "One of the dates provided cannot be parsed"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new JsonPayload(null, e.getMessage()));
        }
    }
}
