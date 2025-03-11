package com.aleksandrm.mynotions.model;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Setter
@Getter
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

}
