package com.sbs.demo3.base.rq;

import com.sbs.demo3.domain.member.entity.Member;
import com.sbs.demo3.domain.member.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class Rq {
    private final MemberService memberService;
    private final HttpServletRequest req;
    private final HttpServletResponse resp;
    private final HttpSession session;
    private Member member = null;

    public Rq(MemberService memberService, HttpServletRequest req, HttpServletResponse resp, HttpSession session) {
        this.memberService = memberService;
        this.req = req;
        this.resp = resp;
        this.session = session;
    }

    public String getAllCookieValuesAsString() {
        StringBuilder sb = new StringBuilder();

        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                sb.append(cookie.getName()).append(": ").append(cookie.getValue()).append("\n");
            }
        }

        return sb.toString();
    }

    public String getAllSessionValuesAsString() {
        StringBuilder sb = new StringBuilder();

        java.util.Enumeration<String> attributeNames = session.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String attributeName = attributeNames.nextElement();
            sb.append(attributeName).append(": ").append(session.getAttribute(attributeName)).append("\n");
        }

        return sb.toString();
    }

    public void setCookie(String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        resp.addCookie(cookie);
    }

    public void removeCookie(String name) {
        Cookie cookie = new Cookie(name, "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        resp.addCookie(cookie);
    }

    private String getCookie(String name, String defaultValue) {
        Cookie[] cookies = req.getCookies();

        if (cookies == null) {
            return defaultValue;
        }

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(name)) {
                return cookie.getValue();
            }
        }

        return defaultValue;
    }

    private int getCookieAsInt(String name, int defaultValue) {
        String cookie = getCookie(name, null);

        if (cookie == null) {
            return defaultValue;
        }

        return Integer.parseInt(cookie);
    }

    private int getLoginedMemberId() {
        return getCookieAsInt("loginedMemberId", 0);
    }

    public boolean isLogin() {
        return getLoginedMemberId() != 0;
    }

    public boolean isLogout() {
        return !isLogin();
    }

    public Member getMember() {
        if ( isLogout() ) {
            return null;
        }

        if (member == null) {
            int loginedMemberId = getLoginedMemberId();

            if (loginedMemberId != 0) {
                member = memberService.findById(loginedMemberId).get();
            }
        }

        return member;
    }

    public boolean isAdmin() {
        if ( isLogout() ) return false;

        return getMember().isAdmin();
    }
}