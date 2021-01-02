package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SqlRuParse {

    public static void urlToParse(String url) throws Exception {
        Document doc = Jsoup.connect(url).get();
        Elements row = doc.select(".postslisttopic");
        Elements dates = doc.select(".altCol");
        int index = 1;
        for (Element td : row) {
            Element href = td.child(0);
            System.out.println(href.attr("href"));
            System.out.println(href.text());
            System.out.println(DateUtils.formatDate(dates.get(index).text()));
            index += 2;
        }
    }

    public static void main(String[] args) throws Exception {

        String url = "https://www.sql.ru/forum/job-offers/";
        for (int i = 1; i <= 5; i++) {
            urlToParse(url + i);
        }
    }
}
