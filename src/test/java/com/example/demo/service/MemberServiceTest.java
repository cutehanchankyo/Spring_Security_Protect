package com.example.demo.service;

import com.example.demo.domain.Member;
import com.example.demo.domain.presentation.dto.request.MemberRequest;
import com.example.demo.domain.presentation.dto.request.SignIn;
import com.example.demo.domain.repository.MemberRepository;
import com.example.demo.domain.service.MemberService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import java.util.Map;
import java.util.Objects;

@Profile("dev")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class MemberServiceTest {
    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    public void init(){
        memberRepository.deleteAll();
        MemberRequest memberReqDto = new MemberRequest("test@gmail.com","1234","정찬교");
        memberService.join(memberReqDto);
    }
    @Test
    @Order(1)
    public void joinTest(){
        //given
        MemberRequest memberReqDto = new MemberRequest("test1@gmail.com","1234","정찬교");

        //whem
        Long join = memberService.join(memberReqDto);

        //then
        Assertions.assertThat(join).isEqualsTo(2L);
        org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, ()->
                memberService.join(memberReqDto))
    }

    @Test
    public void loginTest(){
        SignIn signInDto = new SignIn("test@gmail.com", "1234");
        Map<String, Objects> login = memberService.login(signInDto);
        Member member = memberRepository.findById((Long) login.get("member_id")).get();
        Assertions.assertThat(login.get("member_id")).isEqualTo(member.getId());
    }


}
