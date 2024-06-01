package com.aleksandrm.mynotions.article;

import com.aleksandrm.mynotions.user.User;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity

public class Article {

    @Id
    @GeneratedValue
    private Integer id;
    private String title;
    private String content;
    private String author;
    private String isbn;
    private String articleCover;
    private boolean published;
    private boolean archived;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime modifiedAt;
    @CreatedBy
    @Column(nullable = false, updatable = false)
    private Integer createdBy;
    @LastModifiedBy
    @Column(insertable = false)
    private Integer lastModifiedBy;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;
}
