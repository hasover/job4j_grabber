package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.Post;
import ru.job4j.grabber.Parse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SqlRuParse implements Parse {

    @Override
    public List<Post> list(String url) {
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Post> postList = new ArrayList<>();
        Elements row = doc.select(".postslisttopic");
        Elements dates = doc.select(".altCol");
        for (Element td : row) {
            Element href = td.child(0);
            String postUrl = href.attr("href");
            postList.add(detail(postUrl));
        }
        return postList;
    }

    @Override
    public Post detail(String url) {
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String title = doc.select(".messageHeader").get(0).text();
        String description = doc.select(".msgbody").get(1).text();
        String time = doc.select(".msgFooter").text().split("\\s\\[")[0];
        return new Post(
                url,
                title,
                description,
                DateUtils.formatDate(time));
    }

    public static void main(String[] args) {

        Parse parser = new SqlRuParse();
        String url = "https://www.sql.ru/forum/job-offers/";
        for (int i = 1; i <= 5; i++) {
            for (Post post : parser.list(url + i)) {
                System.out.println(post);
            }
        }
    }
}
