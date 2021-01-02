package ru.job4j.html;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class DateUtils {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static Map<String, String> monthsMap = new HashMap<>();

    static {
        monthsMap.put("янв", "01");
        monthsMap.put("фев", "02");
        monthsMap.put("мар", "03");
        monthsMap.put("апр", "04");
        monthsMap.put("май", "05");
        monthsMap.put("июн", "06");
        monthsMap.put("июл", "07");
        monthsMap.put("авг", "08");
        monthsMap.put("сен", "09");
        monthsMap.put("окт", "10");
        monthsMap.put("ноя", "11");
        monthsMap.put("дек", "12");

    }

    public static String formatDate(String strToFormat) throws ParseException {
        String[] strData = strToFormat.trim().split(",\\s");
        String time = strData[1];
        String dateToFormat = strData[0];

        String formattedDate = "";
        if (dateToFormat.equals("сегодня")) {
            formattedDate = LocalDate.now().format(DATE_FORMAT);
        } else if (dateToFormat.equals("вчера")) {
            formattedDate = LocalDate.now().minusDays(1).format(DATE_FORMAT);
        } else {
            String[] dateInfo = dateToFormat.trim().split("\\s");

            if (dateInfo[0].trim().length() == 1) {
                formattedDate += "0" + dateInfo[0];
            } else {
                formattedDate += dateInfo[0];
            }

            formattedDate += "-" + monthsMap.get(dateInfo[1]);
            formattedDate += "-20" + dateInfo[2];
        }
        return formattedDate + " " + time;
    }
}
