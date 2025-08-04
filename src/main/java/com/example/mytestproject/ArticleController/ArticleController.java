package com.example.mytestproject.ArticleController;

import com.example.mytestproject.dto.ArticleForm;
import com.example.mytestproject.dto.CommentDto;
import com.example.mytestproject.entity.Article;
import com.example.mytestproject.repository.ArticleRepository;
import com.example.mytestproject.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
public class ArticleController {

    private static final Logger log = LogManager.getLogger(ArticleController.class);
    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CommentService commentService;
    @GetMapping("/article/new_form")
    public String newForm(){

        return "articles/new_form";
    }
    @GetMapping("/article/new1")
    public String newForm2(){

        return "articles/new";
    }
    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form){
        //System.out.println(form.toString());
        log.info(form.toString());

        // DTO => Entity 변환
        Article article;
        article = form.toEntity();
        log.info(article.toString());

        // Repo로 Entity를 DB 저장
        Article saved = articleRepository.save(article);
        log.info(article.toString());

        return "redirect:/articles/" + saved.getId();
    }
    @GetMapping("/articles/{id}")
    public String articleId(@PathVariable Long id, Model model){
        log.info("id:{}",id);
        // 1번 - id를 DB에 조회
        //Optional<Article> articleEntity = articleRepository.findById(id);
        Article articleEntity = articleRepository.findById(id).orElse(null);
        List<CommentDto> commentDtos = commentService.comments(id);
        
        // 2번 - 모델에 데이터를 등록
        model.addAttribute("commentDtos", commentDtos);
        model.addAttribute("data", articleEntity);

        // 3번 - 사용자에게 뷰 전달
        return "articles/show_article";
    }

    @GetMapping ("/articles/index")
    public String index(Model model){

        // 1번 DB에 모든 게시글 데이터 가져오기
        //Iterable<Article> articleList = articleRepository.findAll();
        //List<Article> articleEntity = (List<Article>) articleRepository.findAll();
        List<Article> articleList = articleRepository.findAll();

        // 2번 등록하기
        model.addAttribute("articleList", articleList);

        // 3번 화면 리턴
        return "articles/index";
    }
    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        Article articleEntity = articleRepository.findById(id).orElse(null);
        model.addAttribute("article", articleEntity);
        return "articles/edit"; // 수정 폼 HTML
    }
    @PostMapping("/articles/edit")
    public String update(ArticleForm form) {
        // 1. DTO를 Entity로 변환
        Article articleEntity = form.toEntity();

        Article dataDB = articleRepository.findById(articleEntity.getId()).orElse(null);
        // 2. 바로 저장 (id가 같은 값이면 덮어쓰여짐)
        if(dataDB != null){
            articleRepository.save(articleEntity);
        }

        // 3. 결과 페이지로 리다이렉트
        return "redirect:/articles/" + articleEntity.getId();
    }
    //HTTP 메서드 = GET, POST, UPDATE, DELETE
    //HTML 메서드 = GET, POST

    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr) {
        log.info("삭제 요청 확인");

        Article article = articleRepository.findById(id).orElse(null);
        log.info(article.toString());

        if(article != null) {
            articleRepository.delete(article);
            rttr.addFlashAttribute("msg","삭제 완료");
        }

        return "redirect:/articles/index";
    }
}
