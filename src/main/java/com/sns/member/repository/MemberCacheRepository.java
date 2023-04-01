package com.sns.member.repository;

import com.sns.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MemberCacheRepository {

    private final RedisTemplate<String, MemberDto> memberDtoRedisTemplate;
    private final static Duration MEMBER_CACHE_TTL = Duration.ofDays(3);

    public void setMemberDto(MemberDto memberDto) {
        String key = getKey(memberDto.getMemberName());
        log.info("Set Member to Redis {},{}", key ,memberDto);
        memberDtoRedisTemplate.opsForValue().set(key , memberDto, MEMBER_CACHE_TTL);
    }
    public Optional<MemberDto> getMemberDto(String memberName) {
        String key = getKey(memberName);
        MemberDto memberDto = memberDtoRedisTemplate.opsForValue().get(key);
        log.info("get Member from Redis {},{}", key ,memberDto);
        return Optional.ofNullable(memberDto);
    }
    private String getKey(String memberName) {
        return "MEMBER:" + memberName;
    }

}
