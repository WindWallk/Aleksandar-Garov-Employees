package com.aleks.employeesapi.data;

import java.util.List;

public record ColleaguesWithCommonProjects(int firstColleagueId, int secondColleagueId,
                                           List<CommonProjectData> commonProjectData) {
}
