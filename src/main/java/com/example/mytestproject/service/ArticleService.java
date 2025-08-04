package com.example.mytestproject.service;

import com.example.mytestproject.dto.ArticleForm;
import com.example.mytestproject.entity.Article;
import com.example.mytestproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ArticleService {
    private static final Logger log = LogManager.getLogger(ArticleService.class);
    @Autowired
    private ArticleRepository articleRepository;


    public List<Article> findAll() {
        return articleRepository.findAll();
    }
    public Article articles(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    public Article create(ArticleForm dto) {
        Article entity = dto.toEntity();
        if(entity.getId() != null) {
            return null;
        }
        return articleRepository.save(entity);
    }

    public Article patch(Long id, ArticleForm dto) {
        Article article = dto.toEntity();
        log.info("id: {}, article : {}", id, article.toString());
        Article dbData = articleRepository.findById(id).orElse(null);
        if(dbData == null || id != article.getId()){
            log.info("잘못된 요청, id : {}, article : {}",id ,article.toString());
            return null;
        }
        dbData.patch(article);

        Article saveData = articleRepository.save(dbData);
        return saveData;
    }

    public Article delete(Long id) {
        Article dbData = articleRepository.findById(id).orElse(null);

        if(dbData == null){
            return null;
        }
        articleRepository.delete(dbData);
        return dbData;
    }
    @Transactional
    public List<Article> createTransaction(List<ArticleForm> dtos) {
        //dtos 를 엔티티 묶음으로 변환
//        List<Article> articles = new ArrayList<>();
//        for(int i = 0; i < dtos.size(); i++){
//            ArticleForm dto = dtos.get(i);
//            Article entity = dto.toEntity();
//            articles.add(entity);
//        }
        // stream 을 이용한 방법
        List<Article> articleStream = dtos.stream()
                .map(dto -> dto.toEntity())
                .collect(Collectors.toUnmodifiableList());
        //DB 저장
        articleStream.stream().forEach(article -> articleRepository.save(article));
        //강제로 에러 발생
        //findById 로 -1인 데이터 찾기 -> 당연히 없지
        // => orElseThrow() - 에러를 발생시키는 메서드 (illegalArgumentException)
        articleRepository.findById(-1L).orElseThrow(() -> new IllegalArgumentException("송금실패"));
        // 결과 리턴
        return articleStream;
    }
}
