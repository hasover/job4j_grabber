package ru.job4j.grabber;

public class Post {
    private String link;
    private String title;
    private String description;
    private String created;

    public Post(String link, String title, String description, String created) {
        this.link = link;
        this.title = title;
        this.description = description;
        this.created = created;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "link="
                + link
                + '\n'
                + "title="
                + title
                + '\n'
                + "description=" + description + '\n'
                + "created=" + created + '\n';
    }
}
