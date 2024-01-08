package com.example.firstproject.controller;

import com.example.firstproject.dto.MemberForm;
import com.example.firstproject.entity.Member;
import com.example.firstproject.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@Controller
@Slf4j
public class MemberController {
    @Autowired
    private MemberRepository memberRepository;

    @GetMapping("/signup")
    public String newMemberForm(){
        return "members/new";
    }

    @PostMapping("/join")
    public String joinMember(MemberForm form){
        log.info(form.toString());

        //1.DTO를 통해 받은 폼데이터를 엔티티로 변환
        Member member = form.toEntity();
        log.info(member.toString());

        //2.엔티티를 레파지토리에 저장 -> 나중에 DB에 저장
        Member saved = memberRepository.save(member);
        log.info(saved.toString());

        return "redirect:/members/"+saved.getId();
    }

    @GetMapping("/members/{id}")
    public String show(@PathVariable Long id, Model model){
        log.info("id = " + id);
        //1. DB에서 id를 조회해 데이터 가져오기
        Member memberEntity = memberRepository.findById(id)
                .orElse(null);
        //2. 모델에 데이터 등록하기
        model.addAttribute("member",memberEntity);
        //3. 뷰페이지 만들어 반환하기
        return "members/show";
    }

    @GetMapping("/members")
    public String index(Model model){
        //1. DB에서 모든 article data 가져오기
        ArrayList<Member> memberEntityList = memberRepository.findAll();
        //2. 가져온 article을 모델에 등록
        model.addAttribute("memberList",memberEntityList);
        //3. 뷰페이지 설정
        return "members/index";
    }

    @GetMapping("/members/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        //1. DB에서 모든 article data 가져오기
        Member memberEntity = memberRepository.findById(id)
                .orElse(null);
        //2. 모델에 데이터 등록하기
        model.addAttribute("member", memberEntity);
        //3. 뷰페이지 설정
        return "members/edit";
    }

    @PostMapping("/members/update")
    public String update(MemberForm form) {
        log.info(form.toString());
        //1.DTO를 엔티티로 변환
        Member memberEntity = form.toEntity();
        log.info(memberEntity.toString());
        //2.엔티티를 DB에 저장
        Member target = memberRepository.findById(memberEntity.getId())
                .orElse(null);
        if(target != null){
            memberRepository.save(memberEntity);
        }
        //3.수정결과 페이지로 리다이렉트
        return "redirect:/members/"+memberEntity.getId();
    }

    @GetMapping("/members/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr) {
        log.info("삭제 요청이 들어왔습니다.");
        //1.삭제할 대상 가져오기
        Member target = memberRepository.findById(id).orElse(null);
        log.info(target.toString());
        //2.대상 엔티티 삭제하기
        if(target !=null){
            memberRepository.delete(target);
            rttr.addFlashAttribute("msg","삭제되었습니다!");
        }
        //3.결과 페이지로 리다이렉트하기
        return "redirect:/members";
    }
}
