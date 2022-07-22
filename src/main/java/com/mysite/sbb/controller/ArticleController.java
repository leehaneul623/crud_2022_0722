package com.mysite.sbb.controller;

import com.mysite.sbb.dao.Article;
import com.mysite.sbb.repository.ArticleRepository;
import com.mysite.sbb.ut.Ut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/usr/article")
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;

    //C ========================================
    @RequestMapping("/doWrite")
    @ResponseBody
    public String doWrite(String title, String body){
        if(Ut.empty(title)){
            return "제목을 입력해주세요.";
        }
        if(Ut.empty(body)){
            return "내용을 입력해주세요.";
        }
        Article article = new Article();
        article.setRegDate(LocalDateTime.now());
        article.setUpdateDate(LocalDateTime.now());
        article.setTitle(title);
        article.setBody(body);
        article.setUserId(1L);

        articleRepository.save(article);

        return "%d번 게시물 생성이 완료 되었습니다.".formatted(article.getId());
    }


    //R ========================================
    @RequestMapping("/list")
    @ResponseBody
    public List<Article> showList(){
        return articleRepository.findAll();
    }

    @RequestMapping("/detail")
    @ResponseBody
    public Article showDetail(Long id){
        Article article = articleRepository.findById(id).get();
        return article;
    }

    //U ========================================
    @RequestMapping("/doModify")
    @ResponseBody
    public String doModify(Long id, String title, String body){
        if(id == null){
            return "id를 입력하세요.";
        }
        if(Ut.empty(title)){
            return "제목을 입력하세요.";
        }
        if(Ut.empty(body)){
            return "내용을 입력하세요.";
        }
        Article article = articleRepository.findById(id).get();

        article.setTitle(title);
        article.setBody(body);

        articleRepository.save(article);

        return String.format("%d번 수정이 완료되었습니다.",id);
    }


    //D ========================================
    @RequestMapping("/doDelete")
    @ResponseBody
    public String doDelete(Long id){
        if(!articleRepository.existsById(id)){
            return "%d번 게시물은 이미 삭제 되었거나 존재하지 않습니다.".formatted(id);
        }
        Article article = articleRepository.findById(id).get();
        articleRepository.delete(article);

        return String.format("%d번 게시물이 삭제 되었습니다.", id);
    }

}
