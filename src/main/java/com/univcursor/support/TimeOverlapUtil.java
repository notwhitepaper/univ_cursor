package com.univcursor.support;

import com.univcursor.domain.Course;
import com.univcursor.domain.CourseTime;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public final class TimeOverlapUtil {

    private TimeOverlapUtil() {
    }

    public static boolean hasConflict(Collection<Course> courses, Course candidate) {
        List<CourseTime> existing = courses.stream()
                .flatMap(course -> course.getTimes().stream())
                .collect(Collectors.toList());
        return hasConflict(existing, candidate.getTimes());
    }

    public static boolean hasConflict(Collection<CourseTime> existing, Collection<CourseTime> candidates) {
        for (CourseTime candidate : candidates) {
            if (hasConflict(existing, candidate)) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasConflict(Collection<CourseTime> existing, CourseTime candidate) {
        for (CourseTime item : existing) {
            if (item.getDayOfWeek() == candidate.getDayOfWeek()) {
                int start = Math.max(item.getStartMin(), candidate.getStartMin());
                int end = Math.min(item.getEndMin(), candidate.getEndMin());
                if (start < end) {
                    return true;
                }
            }
        }
        return false;
    }
}
