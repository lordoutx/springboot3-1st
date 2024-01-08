package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Slf4j          //simple logging facade for java
@Controller
public class ArticleController {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private CommentService commentService;

    @GetMapping("/articles/new")
    public String newArticleForm(){
        return "articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form){
        log.info(form.toString());

        //1.DTO를 통해 받은 폼데이터를 엔티티로 변환
        Article article = form.toEntity();
        log.info(article.toString());

        //2.엔티티를 레파지토리에 저장 -> 나중에 DB에 저장
        Article saved = articleRepository.save(article);
        log.info(saved.toString());

        return "redirect:/articles/"+saved.getId();
    }

    @GetMapping("/articles/{id}")   //URL에서 요청을 받음
    public String show(@PathVariable Long id, Model model){      //매개변수로 id 받아오기
        log.info("id = " + id);
        //1. DB에서 id를 조회해 데이터 가져오기
        Article articleEntity = articleRepository.findById(id)
                .orElse(null);
        List<CommentDto> commentsDtos = commentService.comments(id);
        //2. 모델에 데이터 등록하기
        model.addAttribute("article",articleEntity);
        model.addAttribute("commentDtos",commentsDtos);
        //3. 뷰페이지 만들어 반환하기
        return "articles/show";
    }

    @GetMapping("/articles")
    public String index(Model model){
        //1. DB에서 모든 article data 가져오기
        ArrayList<Article> articleEntityList = articleRepository.findAll();
        //2. 가져온 article을 모델에 등록
        model.addAttribute("articleList",articleEntityList);
        //3. 뷰페이지 설정
        return "articles/index";
    }

    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        //1. DB에서 모든 article data 가져오기
        Article articleEntity = articleRepository.findById(id)
                .orElse(null);
        //2. 모델에 데이터 등록하기
        model.addAttribute("article", articleEntity);
        //3. 뷰페이지 설정
        return "articles/edit";
    }

    @PostMapping("/articles/update")
    public String update(ArticleForm form) {
        log.info(form.toString());
        //1.DTO를 엔티티로 변환
        Article articleEntity = form.toEntity();
        log.info(articleEntity.toString());
        //2.엔티티를 DB에 저장
        Article target = articleRepository.findById(articleEntity.getId())
                .orElse(null);
        if(target != null){
            articleRepository.save(articleEntity);
        }
        //3.수정결과 페이지로 리다이렉트
        return "redirect:/articles/"+articleEntity.getId();
    }

    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr) {
        log.info("삭제 요청이 들어왔습니다.");
        //1.삭제할 대상 가져오기
        Article target = articleRepository.findById(id).orElse(null);
        log.info(target.toString());
        //2.대상 엔티티 삭제하기
        if(target !=null){
            articleRepository.delete(target);
            rttr.addFlashAttribute("msg","삭제되었습니다!");
        }
        //3.결과 페이지로 리다이렉트하기
        return "redirect:/articles";
    }
}
