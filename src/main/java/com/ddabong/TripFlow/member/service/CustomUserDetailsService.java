package com.ddabong.TripFlow.member.service;

import com.ddabong.TripFlow.member.dao.IMemberRepository;
import com.ddabong.TripFlow.member.dto.CustomUserDetails;
import com.ddabong.TripFlow.member.model.Member;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final IMemberRepository iMemberRepository;

    public CustomUserDetailsService(IMemberRepository iMemberRepository){
        this.iMemberRepository = iMemberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Member member = iMemberRepository.findByUserId(userId);

        if(member != null){
            return new CustomUserDetails(member);
        }
        return null;
    }
}