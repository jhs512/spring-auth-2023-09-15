package com.sbs.demo3.domain.member.service;

import com.sbs.demo3.domain.member.entity.Member;
import com.sbs.demo3.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Member join(String username, String password, String nickname) {
        Member member = Member
                .builder()
                .username(username)
                .password(password)
                .nickname(nickname)
                .build();

        return memberRepository.save(member);
    }
}
