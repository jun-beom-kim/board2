package com.example.mytestproject.repository;

import com.example.mytestproject.entity.Article;
import com.example.mytestproject.entity.Comment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest // 레포지토리 테스트를 하겠다 명시
class CommentRepositoryTest {
    @Autowired
    CommentRepository commentRepository;

    @Test
    @DisplayName("특정 아이디 조회하여 테스트")
    void findByArticleId() {
        // 4번 게시글을 조회하는 댓글 조회
        Long articleId = 4L;
        // 1. expected - 예상 데이터 작성
        Article article = new Article(4L, "나의 최애는?", "ㄱㄱㄱ");
        Comment fir = new Comment(1L, article, "Jun", "파워레인저");
        Comment sec = new Comment(2L, article, "Jun", "파워레인저1");
        Comment thr = new Comment(3L, article, "Jun2", "파워레인저2");
        List<Comment> expected = Arrays.asList(fir, sec, thr);
        // 2. 실제 데이터 수집
        List<Comment> commentList = commentRepository.findByArticleId(articleId);
        // 3. 비교
        assertEquals(expected.toString(), commentList.toString());
    }

    @Test
    @DisplayName("특정 닉네임으로 댓글 조회")
    void findByNickname() {
        String nickname = "Jun";
        // 예상 데이터
        Comment fir = new Comment(1L,
        new Article(4L, "나의 최애는?", "ㄱㄱㄱ"), nickname, "Jun");

        Comment sec = new Comment(2L,
                new Article(4L, "나의 최애는?", "ㄱㄱㄱ"), nickname, "Jun");

        List<Comment> expected = Arrays.asList(fir, sec);

        // 실제 데이터
        List<Comment> commentList = commentRepository.findByNickname(nickname);
        // 비교
    }
}