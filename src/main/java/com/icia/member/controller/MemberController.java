package com.icia.member.controller;

import com.icia.member.dto.MemberDTO;
import com.icia.member.entity.MemberEntity;
import com.icia.member.service.MemberService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Member;
import java.util.List;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    @GetMapping("/save")
    public String saveForm(){
        return "memberPages/MemberSave";
    }
    @PostMapping("/save")
    public String save(@ModelAttribute MemberDTO memberDTO){
        memberService.save(memberDTO);
        return "index";
    }
    @GetMapping("/list")
    public String findAll(Model model){
        List<MemberDTO> memberDTOList = memberService.findAll();
        model.addAttribute("MemberList", memberDTOList);
        return "memberPages/MemberList";
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id){
        memberService.deleteById(id);
        return "redirect:/member/list";
    }
    @GetMapping("/login")
    public String loginForm(){
        return "memberPages/MemberLogin";
    }
    @PostMapping("/login")
    public String login(@ModelAttribute MemberDTO memberDTO, HttpSession session){
        MemberDTO loginResult = memberService.login(memberDTO);
        if(loginResult != null){
            session.setAttribute("loginMember", loginResult);
            return "memberPages/MemberMain";
        }else{
            return "memberPages/MemberLogin";
        }
    }
    @GetMapping("/detail")
    public String detail(){
        return "memberPages/MemberDetail";
    }

}
