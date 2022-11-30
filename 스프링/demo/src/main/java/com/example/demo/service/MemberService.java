package com.example.demo.service;

import com.example.demo.domain.Member;
import com.example.demo.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

public class MemberService {

    //    private final MemberRepository memberRepository = new MemoryMemberRepository();
        private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    /**
     * 회원가입
     */
    public Long join(Member member){
        //method extracting: ctrl + alt + m
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }
    //findByName으로 조회해서 만약 존재한다면(ifPresent) 익셉션 발생,
    //optional로 return 해주기 때문에 사용 가능. optional에 대해 공부하면 좋은듯 함.
    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }
}
