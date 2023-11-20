package org.benevolat.models;

public class Request {
    private User asker;
    private String title;
    private String content;

    public Request(User asker, String title, String content) {
        this.title = title;
        this.content = content;
        this.asker = asker;
    }

    public User getAsker() {
        return asker;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "Request{" +
                "asker=" + asker +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
