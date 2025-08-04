package com.example.mytestproject.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@ToString
@NoArgsConstructor
@Getter
@Setter
@Table(name = "myTable")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String content;

    public Long getId() {
        return id;
    }

    public void patch(Article article) {
        if(article.title != null) {
            this.title = article.title;
        }
        if(article.content != null) {
            this.content = article.content;
        }
    }
}
