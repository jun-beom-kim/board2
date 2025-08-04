package com.example.mytestproject.service;

import com.example.mytestproject.dto.ArticleForm;
import com.example.mytestproject.entity.Article;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*; //사용할 클래스 패키지 예비로 임포트

@SpringBootTest
class ArticleServiceTest {
    @Autowired
    ArticleService articleService;

    @Test
    void findAll() {
        // 예상되는 데이터 작성
        Article first = new Article(1L, "1111","1111");
        Article second = new Article(2L, "2222","2222");
        Article third = new Article(3L, "3333","3333");
        List<Article> expected = new ArrayList<Article>(Arrays.asList(first,second,third));
        // 실제 DB 데이터
        List<Article> articleList = articleService.findAll();

        assertEquals(expected.toString(),articleList.toString());
    }

    @Test
    void articles_success() {
        //예상 데이터
        Long id = 1L;
        Article expected = new Article(id, "1111", "1111");
        //실제 데이터
        Article article = articleService.articles(id);
        //비교

        assertEquals(expected.toString(),article.toString());
    }
    @Test
    void articles_fail() {
        //예상 데이터
        Long id = -1L;
        Article expected = null;
        //실제 데이터
        Article article = articleService.articles(id);
        //비교
        assertEquals(expected,article);
    }

    @Test
    @Transactional
    void create_suc() {
        String title = "4444";
        String content = "4444";
        ArticleForm dto = new ArticleForm(null, title, content);
        Article expected = new Article(4L, title, content);

        Article article = articleService.create(dto);

        assertEquals(expected.toString(),article.toString());
    }

    @Test
    void create_fail() {
        Long id = 4L;
        String title = "1111";
        String content = "1111";
        ArticleForm dto = new ArticleForm(id, title, content);
        Article expected = null;

        Article article = articleService.create(dto);

        assertEquals(expected,article);
    }
}