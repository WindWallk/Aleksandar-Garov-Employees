package com.aleks.employeesapi.data;

import java.time.LocalDate;

public record ProjectData(int projectId, LocalDate from, LocalDate to) {
}
