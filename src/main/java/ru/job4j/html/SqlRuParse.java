package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.Post;

import java.io.IOException;

public class SqlRuParse {

    public static void urlToParse(String url) throws Exception {
        Document doc = Jsoup.connect(url).get();
        Elements row = doc.select(".postslisttopic");
        Elements dates = doc.select(".altCol");
        int index = 1;
        for (Element td : row) {
            Element href = td.child(0);
            String postUrl = href.attr("href");
            Post post = getPostDetails(postUrl);
            System.out.println(post);
        }
    }

    public static Post getPostDetails(String url) throws Exception {
        Document doc = Jsoup.connect(url).get();
        String title = doc.select(".messageHeader").get(0).text();
        String description = doc.select(".msgbody").get(1).text();
        String time = doc.select(".msgFooter").text().split("\\s\\[")[0];
        return new Post(
                url,
                title,
                description,
                DateUtils.formatDate(time));
    }

    public static void main(String[] args) throws Exception {

        String url = "https://www.sql.ru/forum/job-offers/";
        for (int i = 1; i <= 5; i++) {
            urlToParse(url + i);
        }
    }
}
