package com.aleks.employeesapi.data;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import org.springframework.lang.NonNull;

public class CSVData {
    @NonNull
    @CsvBindByName(column = "EmpID")
    int employeeId;

    @NonNull
    @CsvBindByName(column = "ProjectID")
    int projectId;

    @NonNull
    @CsvBindByName(column = "DateFrom")
    String startDate;

    @CsvBindByName(column = "DateTo")
    String endDate;

    public CSVData() {

    }

    public CSVData(int employeeId, int projectId, @NonNull String startDate, String endDate) {
        this.employeeId = employeeId;
        this.projectId = projectId;
        this.startDate = startDate;
        this.endDate = endDate;
    }


    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    @NonNull
    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(@NonNull String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}