package com.sbs.demo3.domain.member.controller;

import com.sbs.demo3.base.rq.Rq;
import com.sbs.demo3.domain.member.entity.Member;
import com.sbs.demo3.domain.member.service.MemberService;
import com.sbs.demo3.standard.util.Ut;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/usr/member") // 액션 URL의 공통 접두어
@RequiredArgsConstructor
public class MemberController {
    private final Rq rq;

    private final MemberService memberService;

    @GetMapping("/login")
    @PreAuthorize("isAnonymous()")
    public String showLogin() {
        return "usr/member/login";
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public String showMe() {
        return "usr/member/me";
    }

    @GetMapping("/modify")
    @PreAuthorize("isAuthenticated()")
    public String showModify() {
        return "usr/member/modify";
    }

    @PostMapping("/modify")
    @PreAuthorize("isAuthenticated()")
    public String modify(String password, String nickname) {
        Member member = rq.getMember();
        memberService.modify(member, password, nickname);

        return "redirect:/usr/member/me?msg=" + Ut.url.encode("회원정보가 수정되었습니다.");
    }
}