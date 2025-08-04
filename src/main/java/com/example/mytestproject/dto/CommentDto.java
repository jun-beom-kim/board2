package com.example.mytestproject.dto;

import com.example.mytestproject.entity.Comment;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
public class CommentDto {
    private Long id;
    @JsonProperty("article_id")
    private Long articleId;
    private String body;
    private String nickname;

    public CommentDto(Long id, Long articleId, String body, String nickname) {
        this.id = id;
        this.articleId = articleId;
        this.body = body;
        this.nickname = nickname;
    }

    public static CommentDto createCommentDto(Comment c) {
        return new CommentDto(
                c.getId(),
                c.getArticle().getId(),
                c.getNickname(),
                c.getBody()
        );
    }
}
