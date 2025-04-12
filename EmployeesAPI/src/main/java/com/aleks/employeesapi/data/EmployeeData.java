package com.aleks.employeesapi.data;

import java.util.List;

public record EmployeeData(int id, List<ProjectData> projects) {
}