package com.aleks.employeesapi.data;

import java.util.List;

public record ProjectData(int id, List<EmployeeProjectData> employees) {
}
