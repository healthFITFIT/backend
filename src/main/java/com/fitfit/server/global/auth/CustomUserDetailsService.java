package com.fitfit.server.global.auth;

import com.fitfit.server.api.user.repository.MemberRepository;
import com.fitfit.server.api.user.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    public CustomUserDetailsService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 사용자 정보를 이메일로 조회
        Member member = memberRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username));

        // 사용자의 권한을 설정
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + member.getRole()));

        // 비밀번호는 빈 문자열로 설정 (비밀번호는 JWT 기반 인증에서 사용될 수 있으므로)
        return new org.springframework.security.core.userdetails.User(
                member.getEmail(),
                "",  // 비밀번호를 빈 문자열로 설정
                authorities
        );
    }
}