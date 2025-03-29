package com.aleksandrm.mynotions.model;

import lombok.*;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Note {
    private int id;
    private String title;
    private String content;
    private String author;
    private Instant creationDate;

}
