package ru.job4j.grabber;

import ru.job4j.html.SqlRuParse;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PsqlStore implements Store, AutoCloseable {
    private Connection connection;

    public PsqlStore(Properties cfg) {
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
            connection = DriverManager.getConnection(
                    cfg.getProperty("jdbc.url"),
                    cfg.getProperty("jdbc.username"),
                    cfg.getProperty("jdbc.password")
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(Post post) {
        try (PreparedStatement statement = connection.prepareStatement(
                "insert into post(name, description, link, created)"
                        + "values (?, ?, ?, ?)")) {
            statement.setString(1, post.getTitle());
            statement.setString(2, post.getDescription());
            statement.setString(3, post.getLink());

            Long millis = new SimpleDateFormat("dd-MM-yyyy HH:mm").
                    parse(post.getCreated()).getTime();
            statement.setTimestamp(4, new Timestamp(millis));
            statement.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<Post> getAll() {
        List<Post> posts = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            String sql = "select * from post";
            ResultSet result = statement.executeQuery(sql);
            while (result.next()) {
                posts.add(new Post(
                        result.getString("link"),
                        result.getString("name"),
                        result.getString("description"),
                        result.getString("created")
                ));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return posts;
    }

    @Override
    public Post findById(String id) {
        Post post = null;
        try (PreparedStatement statement = connection.prepareStatement(
                "select * from post where id = ?")) {
            statement.setInt(1, Integer.parseInt(id));
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                post =  new Post(
                        result.getString("link"),
                        result.getString("name"),
                        result.getString("description"),
                        result.getString("created"));

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return post;
    }

    @Override
    public void close() throws Exception {
        if (connection != null) {
            connection.close();
        }
    }

    public static void main(String[] args) throws Exception {
        Properties properties = new Properties();
        properties.load(PsqlStore.class.getClassLoader().getResourceAsStream("app.properties"));
        Store store = new PsqlStore(properties);
        SqlRuParse parse = new SqlRuParse();
        Post post = parse.detail("https://www.sql.ru/forum/1332112/"
                + "programmist-prikladnogo-po-c-po-produktu-polator");
        store.save(post);
        post = parse.detail("https://www.sql.ru/forum/1332197/v-poiskah-full-stack-razrabotchika");
        store.save(post);
        for (Post post1 : store.getAll()) {
            System.out.println(post1);
        }
    }
}
