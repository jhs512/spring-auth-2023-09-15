package com.sbs.demo3.domain.member.controller;

import com.sbs.demo3.base.rq.Rq;
import com.sbs.demo3.domain.member.entity.Member;
import com.sbs.demo3.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/usr/member") // 액션 URL의 공통 접두어
@RequiredArgsConstructor
public class MemberController {
    private final Rq rq;

    private final MemberService memberService;

    @GetMapping("/login")
    public String showLogin() {
        return "usr/member/login";
    }

    @PostMapping("/login")
    public String login(String username, String password) {
        Optional<Member> opMember = memberService.findByUsername(username);

        if ( opMember.isEmpty() ) {
            return "redirect:/usr/member/login?error";
        }

        Member member = opMember.get();

        if ( member.getPassword().equals(password) == false ) {
            return "redirect:/usr/member/login?error";
        }

        rq.setCookie("loginedMemberId", member.getId() + "");

        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logout() {
        rq.removeCookie("loginedMemberId");

        return "redirect:/";
    }

    @GetMapping("/me")
    public String showMe() {
        return "usr/member/me";
    }
}