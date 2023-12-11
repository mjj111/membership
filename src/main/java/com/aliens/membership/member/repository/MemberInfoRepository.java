package com.aliens.membership.member.repository;

import com.aliens.membership.member.entity.MemberInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberInfoRepository extends JpaRepository<MemberInfo,Long> {
    MemberInfo findByIdAndName(long id, String name); // id 와 name 으로 인덱스 생성가능
}
