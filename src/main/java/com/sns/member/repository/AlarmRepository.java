package com.sns.member.repository;

import com.sns.member.entity.Alarm;
import com.sns.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AlarmRepository extends JpaRepository<Alarm, Long> {

    Page<Alarm> findAllByMember(Member member, Pageable pageable);
}
