package com.aleksandrm.mynotions.article;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleResponse {

    private Integer id;
    private String title;
    private String author;
    private String isbn;
    private String description;
    private byte[] cover;
    private double rate;
    private boolean published;
    private boolean archived;
}
