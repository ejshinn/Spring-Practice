package com.bitc.jwtserver.service;

import com.bitc.jwtserver.database.entity.MemberEntity;
import com.bitc.jwtserver.database.repo.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public MemberEntity loadUserByUsername(String userId) throws UsernameNotFoundException {
        return memberRepository.findByUserId(userId).orElseThrow(() -> new IllegalArgumentException("아이디가 없습니다."));
    }
}