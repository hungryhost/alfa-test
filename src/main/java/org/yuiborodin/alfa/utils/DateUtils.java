package org.yuiborodin.alfa.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    public static String getPreviousDate(){
        LocalDate today = LocalDate.now();
        ZoneId timezone = ZoneId.of("Europe/Moscow");
        ZonedDateTime zonedDateTime = today.atStartOfDay(timezone);
        ZonedDateTime yesterday = zonedDateTime.minusDays(1);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return yesterday.format(formatter);
    }
}
