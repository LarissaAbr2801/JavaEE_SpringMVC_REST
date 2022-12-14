package ru.netology.model;

import java.util.Objects;

public class Post {

    private long id;
    private String content;
    private volatile boolean removed = false;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isRemoved() {
        return removed;
    }

    public void setRemoved(boolean removed) {
        this.removed = removed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return id == post.id && content.equals(post.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content);
    }

}
