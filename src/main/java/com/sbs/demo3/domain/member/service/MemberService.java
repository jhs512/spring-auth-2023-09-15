package com.sbs.demo3.domain.member.service;

import com.sbs.demo3.domain.member.entity.Member;
import com.sbs.demo3.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member join(String username, String password, String nickname) {
        Member member = Member
                .builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .nickname(nickname)
                .build();

        return memberRepository.save(member);
    }

    public Optional<Member> findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }

    public Optional<Member> findById(long id) {
        return memberRepository.findById(id);
    }
}
