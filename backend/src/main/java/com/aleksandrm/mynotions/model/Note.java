package com.aleksandrm.mynotions.model;

import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Note {
    private int id;
    private String title;
    private String content;
    private String author;
    private Timestamp createdAt;
    private List<Tag> tags;

    public Note(int id, String title, String content, String author, Timestamp createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.createdAt = createdAt;
    }
}
