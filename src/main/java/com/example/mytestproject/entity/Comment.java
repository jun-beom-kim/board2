package com.example.mytestproject.entity;

import com.example.mytestproject.dto.CommentDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@ToString
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //PK

    @ManyToOne
    @JoinColumn(name = "article_id") //FK 생성, 컬럼 이름을 article_id  로 지정, 자연스럽게 article 의 PK(id)에 매핑
    private Article article; //참조하는 게시글

    @Column
    private String nickname; // 사용자 이름
    @Column
    private String body; // 댓글 내용

    public static Comment createComment(CommentDto dto, Article article) {
        // 예외가 발생한다면 예외처리
        if(dto.getId() != null) {
            throw new IllegalArgumentException("댓글 생성에 실패하였습니다. id는 전달되어서는 안됩니다.");
        }
        if(dto.getArticleId() != article.getId()) {
            throw new IllegalArgumentException("게시글의 ID와 생성할 댓글 내 ID 가 서로 다릅니다");
        }
        return new Comment(
                dto.getId(),
                article,
                dto.getNickname(),
                dto.getBody()
        );
    }

    public Long getId() {
        return id;
    }

    public Article getArticle() {
        return article;
    }

    public String getNickname() {
        return nickname;
    }

    public String getBody() {
        return body;
    }

    public Comment(Long id, Article article, String nickname, String body) {
        this.id = id;
        this.article = article;
        this.nickname = nickname;
        this.body = body;
    }

    public void patch(CommentDto dto) {
        if(this.id != dto.getId()) {
            throw new IllegalArgumentException("댓글 수정 실패, 아이디 다름");
        }

        // 객체 갱신하기
        if(dto.getNickname() != null) {
            this.nickname = dto.getNickname();
        }
        if(dto.getBody() != null) {
            this.body = dto.getBody();
        }
    }
}
