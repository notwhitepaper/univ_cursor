package com.univcursor.support;

import com.univcursor.domain.Course;

import java.util.Collection;

public final class CreditCalculator {

    private CreditCalculator() {
    }

    public static int totalCredits(Collection<Course> courses) {
        return courses.stream().mapToInt(Course::getCredit).sum();
    }
}
