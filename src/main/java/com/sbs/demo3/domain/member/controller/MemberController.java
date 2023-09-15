package com.sbs.demo3.domain.member.controller;

import com.sbs.demo3.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/usr/member") // 액션 URL의 공통 접두어
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/login")
    public String showLogin() {
        return "usr/member/login";
    }

    @GetMapping("/me")
    public String showMe() {
        return "usr/member/me";
    }
}