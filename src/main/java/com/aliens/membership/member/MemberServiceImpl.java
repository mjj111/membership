package com.aliens.membership.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    public List<Member> getAllMember() {
        return memberRepository.findAll();
    }
}
