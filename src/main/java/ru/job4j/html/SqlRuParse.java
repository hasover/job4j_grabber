package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SqlRuParse {
    public static String formatDate(String strToFormat) throws ParseException {

        String[] strData = strToFormat.trim().split(",\\s");
        String time = strData[1];
        String dateToFormat = strData[0];

        String formattedDate = "";
        if (dateToFormat.equals("сегодня")) {
            formattedDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        } else if (dateToFormat.equals("вчера")) {
            formattedDate = LocalDate.now().minusDays(1).
                    format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        } else {
            String[] dateInfo = dateToFormat.trim().split("\\s");

            if (dateInfo[0].trim().length() == 1) {
                formattedDate += "0" + dateInfo[0];
            } else {
                formattedDate += dateInfo[0];
            }

            switch (dateInfo[1]) {
                case "янв" : formattedDate += "-01"; break;
                case "фев" : formattedDate += "-02"; break;
                case "мар" : formattedDate += "-03"; break;
                case "апр" : formattedDate += "-04"; break;
                case "май" : formattedDate += "-05"; break;
                case "июн" : formattedDate += "-06"; break;
                case "июл" : formattedDate += "-07"; break;
                case "авг" : formattedDate += "-08"; break;
                case "сен" : formattedDate += "-09"; break;
                case "окт" : formattedDate += "-10"; break;
                case "ноя" : formattedDate += "-11"; break;
                case "дек" : formattedDate += "-12"; break;
                default: break;
            }

            formattedDate += "-20" + dateInfo[2];
        }

        return formattedDate + " " + time;
    }

    public static void main(String[] args) throws IOException, ParseException {
        Document doc = Jsoup.connect("https://www.sql.ru/forum/job-offers").get();
        Elements row = doc.select(".postslisttopic");
        Elements dates = doc.select(".altCol");
        int index = 1;
        for (Element td : row) {
            Element href = td.child(0);
            System.out.println(href.attr("href"));
            System.out.println(href.text());
            System.out.println(formatDate(dates.get(index).text()));
            index += 2;
        }
    }
}
