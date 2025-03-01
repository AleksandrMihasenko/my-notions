package com.aleksandrm.mynotions.model;

import java.time.Instant;

public class Note {
    private int id;
    private String title;
    private String content;
    private String author;
    private Instant creationDate;

    public Note() {}

    public Note(int id, String title, String content, String author, Instant creationDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.creationDate = creationDate;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getAuthor() { return author; }
    public Instant getCreationDate() { return creationDate; }

    public void setId(int id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setContent(String content) { this.content = content; }
    public void setAuthor(String author) { this.author = author; }
    public void setCreationDate(Instant creationDate) { this.creationDate = creationDate; }
}
