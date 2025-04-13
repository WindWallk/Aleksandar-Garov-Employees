package com.aleks.employeesapi.data;

import java.time.LocalDate;

public record EmployeeProjectData(int id, LocalDate from, LocalDate to) {
}