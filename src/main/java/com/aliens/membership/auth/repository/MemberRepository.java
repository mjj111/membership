package com.aliens.membership.auth.repository;

import com.aliens.membership.auth.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {
    Optional<Member> findByLoginIdAndPassword(String loginId, String password); // loginId, password 로 인덱스 생성 가능
}
