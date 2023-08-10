package com.digginroom.digginroom.service;

import org.springframework.stereotype.Component;

@Component
public class ElapsedTimeConverter {

    private static final int MIN_SECOND = 60;
    private static final int HOUR_SECOND = 3600;
    private static final int DAY_SECOND = 86400;
    private static final int WEEK_SECOND = 604800;
    private static final int MONTH_SECOND = 2629440;
    private static final int YEAR_SECOND = 31536000;

    public String convert(final Long seconds) {
        if (seconds < MIN_SECOND) {
            return seconds + "초 전";
        }
        if (seconds < HOUR_SECOND) {
            long minutes = seconds / 60;
            return minutes + "분 전";
        }
        if (seconds < DAY_SECOND) {
            long hours = seconds / 3600;
            return hours + "시간 전";
        }
        if (seconds < WEEK_SECOND) {
            long days = seconds / 86400;
            return days + "일 전";
        }
        if (seconds < MONTH_SECOND) {
            long weeks = seconds / WEEK_SECOND;
            return weeks + "주 전";
        }
        if (seconds < YEAR_SECOND) {
            long months = seconds / MONTH_SECOND;
            return months + "개월 전";
        }
        long years = seconds / YEAR_SECOND;
        return years + "년 전";
    }
}

