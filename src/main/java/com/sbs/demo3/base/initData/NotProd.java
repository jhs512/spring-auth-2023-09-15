package com.sbs.demo3.base.initData;

import com.sbs.demo3.domain.member.service.MemberService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.stream.IntStream;

@Configuration
@Profile("!prod")
public class NotProd {
    @Bean
    public ApplicationRunner init(MemberService memberService) {
        return args -> {
            memberService.join("admin", "1234", "admin");

            IntStream.rangeClosed(1, 3).forEach(i -> {
                memberService.join("user" + i, "1234", "nickname" + i);
            });
        };
    }
}
