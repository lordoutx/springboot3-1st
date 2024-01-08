package com.example.firstproject.dto;

import com.example.firstproject.entity.Article;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor             //생성자
@ToString                       //toString 메서드
public class ArticleForm {
    private Long id;
    private String title;       //폼에서 넘어오는 제목
    private String content;     //폼에서 넘어오는 내용

    //폼 데이터를 DTO로 받아 엔티티로 변환 -> 레파지토리를 이용해 DB에 저장
    public Article toEntity() {
        return new Article(id,title,content);
    }
}
