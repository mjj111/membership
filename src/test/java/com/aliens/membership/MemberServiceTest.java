package com.aliens.membership;

import com.aliens.membership.member.Member;
import com.aliens.membership.member.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Test
    void 시작너무길다() {
        List<Member> members = memberService.getAllMember();
        if (members.isEmpty()) {
            System.out.println("뭐여");
        }
        for (Member member : members) {
            System.out.println(member);
        }
    }
}
