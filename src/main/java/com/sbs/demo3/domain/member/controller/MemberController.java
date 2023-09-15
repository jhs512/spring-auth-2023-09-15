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
        if (rq.isLogin()) {
            throw new RuntimeException("이미 로그인 되었습니다.");
        }

        return "usr/member/login";
    }

    @PostMapping("/login")
    public String login(String username, String password) {
        if (rq.isLogin()) {
            throw new RuntimeException("이미 로그인 되었습니다.");
        }

        Optional<Member> opMember = memberService.findByUsername(username);

        if (opMember.isEmpty()) {
            return "redirect:/usr/member/login?error";
        }

        Member member = opMember.get();

        if (member.getPassword().equals(password) == false) {
            return "redirect:/usr/member/login?error";
        }

        rq.setCookie("loginedMemberId", member.getId() + "");

        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logout() {
        if (rq.isLogout()) {
            return "redirect:/";
        }

        rq.removeCookie("loginedMemberId");

        return "redirect:/";
    }

    @GetMapping("/me")
    public String showMe() {
        if (rq.isLogout()) {
            return "redirect:/usr/member/login";
        }

        return "usr/member/me";
    }
}